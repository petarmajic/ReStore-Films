import { useState, useEffect, useMemo, useContext } from "react";
import "./korisnici.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";
import { useNavigate } from "react-router-dom";
import pozadina from "../images/filmskaVrpca.jpg";
import jsPDF from "jspdf";
import { useMsal } from "@azure/msal-react";
import axios from "axios";

const KorisniciList = () => {
  const [korisnici, setKorisnici] = useState([]);
  const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;

  useEffect(() => {
    const fetchKorisnici = async () => {
      try {
        const response = await axios.get(`${BACKEND_API_URL}/api/korisnik/all`);
        setKorisnici(response.data);
      } catch (error) {
        console.error("Error fetching korisnici:", error);
      }
    };

    fetchKorisnici();
  }, []);
  const handleDeleteUser = async (email) => {
    try {
      const response = await axios.delete(
        `${BACKEND_API_URL}/api/korisnik/delete/ADMINISTRATOR/${email}`
      );
      console.log("User deleted successfully:", response.data);
      setKorisnici((prevKorisnici) =>
        prevKorisnici.filter((korisnik) => korisnik.email !== email)
      );
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };
  const handleRoleChange = async (event, email) => {
    try {
      const response = await axios.put(
        `${BACKEND_API_URL}/api/korisnik/update-uloga/${email}`,
        { uloga: event.target.value }
      );
      console.log("Uloga updated successfully:", response.data);
      setKorisnici((prevKorisnici) =>
        prevKorisnici.map((korisnik) =>
          korisnik.email === email
            ? { ...korisnik, uloga: event.target.value }
            : korisnik
        )
      );
    } catch (error) {
      console.error("Error updating uloga:", error);
    }
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
            <div className="left-title">Popis korisnika sustava</div>
            <div className="left-list">
              <div>
                <ul>
                  {korisnici.map((korisnik, index) => (
                    <li key={index} className="korisnik-item">
                      <strong>
                        {korisnik.ime} {korisnik.prezime}
                      </strong>{" "}
                      |<strong>Email:</strong> {korisnik.email} |
                      <strong>Uloga:</strong>{" "}
                      {korisnik.uloga ? korisnik.uloga : "Nema uloge"} |
                      <strong>Statistika Digitalizacije:</strong>{" "}
                      {korisnik.statistikaDigitalizacije
                        ? "Postoji"
                        : "Nema podataka"}
                      {"   "}
                      <select
                        value={korisnik.uloga}
                        onChange={(event) =>
                          handleRoleChange(event, korisnik.email)
                        }
                      >
                        <option value="DJELATNIK">DJELATNIK</option>
                        <option value="VODITELJ">VODITELJ</option>
                        <option value="ADMINISTRATOR">ADMINISTRATOR</option>
                      </select>
                      {"   "}
                      <button
                        onClick={() => handleDeleteUser(korisnik.email)}
                        className="delete-button"
                      >
                        Delete
                      </button>
                      <hr />
                    </li>
                  ))}
                </ul>
              </div>
            </div>
            <div className="barcode-btns"></div>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default KorisniciList;
