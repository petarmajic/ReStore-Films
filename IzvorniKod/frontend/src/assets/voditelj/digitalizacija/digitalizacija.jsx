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
  const [statusi, setStatusi] = useState({});

  const account = accounts[0];
  const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;
  let userName = account?.name?.replace(/[čćČĆ]/g, "C") ?? null;
  let userEmail = account?.username?.replace(/[čćČĆ]/g, "C") ?? null;

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

  const promises = [
    axios.get(
      `${BACKEND_API_URL}/api/grupaZaDigitalizaciju/statusCounts/NA_CEKANJU`
    ),
    axios.get(
      `${BACKEND_API_URL}/api/grupaZaDigitalizaciju/statusCounts/NA_DIGITALIZACIJI`
    ),
    axios.get(
      `${BACKEND_API_URL}/api/grupaZaDigitalizaciju/statusCounts/ZAVRSENO`
    ),
  ];

  Promise.all(promises).then((responses) => {
    const statusiObjekat = {
      NA_CEKANJU: responses[0].data,
      NA_DIGITALIZACIJI: responses[1].data,
      ZAVRSENO: responses[2].data,
    };
    setStatusi(statusiObjekat);
  });

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

  const generatePdf = () => {
    const doc = new jsPDF();
    doc.setFontSize(18); // povećajte veličinu fonta
    const date = new Date();
    const dateString =
      date.toLocaleDateString() + " " + date.toLocaleTimeString();
    doc.text(`Voditelj: ${userName} `, 10, 10, null, null, "left");
    doc.text(dateString, 180, 10, null, null, "right");

    doc.text("Statistika digitalizacije", 10, 20); // povećajte y koordinatu za 10

    doc.setFontSize(12); // smanjite veličinu fonta za ispis podataka
    doc.text("NA CEKANJU:", 10, 30);
    doc.text(`${statusi.NA_CEKANJU}`, 60, 30);
    doc.text("NA DIGITALIZACIJI:", 10, 35);
    doc.text(`${statusi.NA_DIGITALIZACIJI}`, 60, 35);
    doc.text("ZAVRŠENO:", 10, 40);
    doc.text(`${statusi.ZAVRSENO}`, 60, 40);

    doc.save("statistika_digitalizacije.pdf");
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
              <strong>NA ČEKANJU:</strong> {statusi.NA_CEKANJU}
              &nbsp;&nbsp;&nbsp;&nbsp;
              <strong>NA DIGITALIZACIJI:</strong> {statusi.NA_DIGITALIZACIJI}
              &nbsp;&nbsp;&nbsp;&nbsp;
              <strong>ZAVRŠENO:</strong> {statusi.ZAVRSENO}
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
              <button onClick={() => generatePdf()}>PDF statistika</button>
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
