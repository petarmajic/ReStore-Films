import { useState, useEffect, useMemo, useContext } from "react";
import "./digitalizacija.css";
import Layout from "../../layout/layout";
import { LayoutContext } from "../../layout/layoutcontext";
import { useNavigate } from "react-router-dom";
import pozadina from "../../images/filmskaVrpca.jpg";
import jsPDF from "jspdf";
import { useMsal } from "@azure/msal-react";
import axios from "axios";

const Barcodes = () => {
  const { scannedBarcodes, setScannedBarcodes } = useContext(LayoutContext);
  const navigate = useNavigate();
  const { instance, accounts } = useMsal();
  const [filmskeTrake, setFilmskeTrake] = useState([]);
  const [selectedFilm, setSelectedFilm] = useState(null);
  const [formData, setFormData] = useState({
    naslov: "",
    zemlja: "",
    godina: "",
    trajanje: "",
  });

  const account = accounts[0];
  let userName = account?.name ?? null;
  let userEmail = account?.username ?? null;
  const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;
  userName = userName
    .replace(/č/g, "C")
    .replace(/ć/g, "C")
    .replace(/Č/g, "C")
    .replace(/Ć/g, "C");
  userEmail = userEmail
    .replace(/č/g, "C")
    .replace(/ć/g, "C")
    .replace(/Č/g, "C")
    .replace(/Ć/g, "C");

  useEffect(() => {
    const fetchFilmskeTrake = async () => {
      try {
        const response = await axios.get(
          `${BACKEND_API_URL}/api/filmskaTraka/all`
        );

        const filtriraneTrake = response.data.map((traka) => ({
          id: traka.idEmisije,
          naslov: traka.originalniNaslov,
          zemlja: traka.porijekloZemljaProizvodnje,
          godina: traka.godinaProizvodnje,
          trajanje: traka.duration,
        }));

        setFilmskeTrake(filtriraneTrake);
      } catch (error) {
        console.error("Error fetching filmskeTrake:", error);
      }
    };

    fetchFilmskeTrake();
  }, []);

  const handleScannerClick = () => {
    navigate("/scanner");
  };

  const handleClearBarcodes = () => {
    setScannedBarcodes([]);
  };

  const handleRemoveBarcode = (index) => {
    if (index >= 0 && index < scannedBarcodes.length) {
      const newBarcodes = [...scannedBarcodes];
      newBarcodes.splice(index, 1);
      setScannedBarcodes(newBarcodes);
    }
  };

  const handleEditClick = (film) => {
    setSelectedFilm(film);
    setFormData({
      naslov: film.naslov,
      zemlja: film.zemlja,
      godina: film.godina,
      trajanje: film.trajanje,
    });
  };

  const handleUpdateSubmit = async (e) => {
    e.preventDefault();
    if (!selectedFilm) return;

    // Priprema podataka u traženom JSON formatu
    const updatePayload = {
      id: selectedFilm.id, // ID filma
      originalniNaslov: formData.naslov, // Naslov filma
      porijekloZemljaProizvodnje: formData.zemlja, // Zemlja proizvodnje
      godinaProizvodnje: formData.godina, // Godina proizvodnje
      duration: formData.trajanje, // Trajanje filma
    };

    try {
      const response = await axios.put(
        `${BACKEND_API_URL}/api/filmskaTraka/update/${selectedFilm.id}`,
        updatePayload
      );

      // Ažuriranje lokalnog stanja
      setFilmskeTrake((prev) =>
        prev.map((film) =>
          film.id === selectedFilm.id
            ? {
                ...film,
                naslov: updatePayload.originalniNaslov,
                zemlja: updatePayload.porijekloZemljaProizvodnje,
                godina: updatePayload.godinaProizvodnje,
              }
            : film
        )
      );

      setSelectedFilm(null); // Zatvaranje forme za uređivanje
    } catch (error) {
      console.error("Error updating filmskaTraka:", error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };
  const handleHomeClick = () => {
    navigate("/home");
  };

  return (
    <Layout>
      <div className="barcode-main">
        <img
          className="barcode-bg-image"
          src={pozadina}
          alt="background picture"
        ></img>
        <div className="barcode-list-container">
          <div className="barcode-scanned">
            <div className="left-title">Statistika digitalizacije</div>
            <div className="left-list">
              {Array.isArray(filmskeTrake) && (
                <div className="filmske-trake-list">
                  <ul>
                    {filmskeTrake.map((film, index) => (
                      <li key={index}>
                        {index + 1}. {film.naslov} - {film.zemlja} -{" "}
                        {film.godina} - {film.trajanje}
                        <button onClick={() => handleEditClick(film)}>
                          Edit
                        </button>
                      </li>
                    ))}
                  </ul>
                </div>
              )}
            </div>
            <div className="barcode-btns">
              <button onClick={handleHomeClick}>Home</button>
              <button onClick={handleScannerClick}>Scan</button>
            </div>
          </div>
          {selectedFilm && (
            <div className="edit-form">
              <h3>Ažuriraj Filmsku Traku</h3>
              <form onSubmit={handleUpdateSubmit}>
                <label>
                  Naslov:
                  <input
                    type="text"
                    name="naslov"
                    value={formData.naslov}
                    onChange={handleChange}
                  />
                </label>
                <label>
                  Zemlja:
                  <input
                    type="text"
                    name="zemlja"
                    value={formData.zemlja}
                    onChange={handleChange}
                  />
                </label>
                <label>
                  Godina:
                  <input
                    type="number"
                    name="godina"
                    value={formData.godina}
                    onChange={handleChange}
                  />
                </label>
                <label>
                  Trajanje:
                  <input
                    type="text"
                    name="trajanje"
                    value={formData.trajanje}
                    onChange={handleChange}
                  />
                </label>
                <button type="submit">Spremi</button>
                <button type="button" onClick={() => setSelectedFilm(null)}>
                  Odustani
                </button>
              </form>
            </div>
          )}
        </div>
      </div>
    </Layout>
  );
};

export default Barcodes;
