import { useState, useEffect, useMemo, useContext } from "react";
import "./djelatnici.css";
import Layout from "../../layout/layout";
import { LayoutContext } from "../../layout/layoutcontext";
import { useNavigate } from "react-router-dom";
import pozadina from "../../images/filmskaVrpca.jpg";
import { useMsal } from "@azure/msal-react";
import axios from "axios";

const Djelatnici = () => {
  const [korisnici, setKorisnici] = useState([]);
  const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;

  useEffect(() => {
    const fetchKorisnici = async () => {
      try {
        const response = await axios.get(`${BACKEND_API_URL}/api/korisnik/all`);
        const filteredKorisnici = response.data.filter(
          (korisnik) => korisnik.uloga === "DJELATNIK"
        );
        setKorisnici(filteredKorisnici);
      } catch (error) {
        console.error("Error fetching korisnici:", error);
      }
    };

    fetchKorisnici();
  }, []);

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
            <div className="left-title"> Popis djelatnika</div>
            <div className="left-list">
              <div>
                <ul>
                  {korisnici.map((korisnik, index) => (
                    <li key={index} className="korisnik-item">
                      <strong>
                        {korisnik.ime} {korisnik.prezime}
                      </strong>
                      {"  "}|<strong>Email:</strong> {korisnik.email} |
                      <strong>Uloga:</strong>
                      {"  "}
                      {korisnik.uloga ? korisnik.uloga : "Nema uloge"} |
                      <strong>Iznio na digitalizaciju:</strong>
                      {"  "}
                      {korisnik.statistikaDigitalizacije.brojNaDigitalizaciji}
                      {"  "}|<strong>Vratio u arhivu:</strong>
                      {"  "}
                      {korisnik.statistikaDigitalizacije.brojDigitaliziranih}
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

export default Djelatnici;
