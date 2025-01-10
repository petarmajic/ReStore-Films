import { useState, useEffect, useMemo, useContext } from "react";
import "./djelatnici.css";
import Layout from "../../layout/layout";
import { LayoutContext } from "../../layout/layoutcontext";
import { useNavigate } from "react-router-dom";
import pozadina from "../../images/filmskaVrpca.jpg";
import { useMsal } from "@azure/msal-react";
import axios from "axios";
import jsPDF from "jspdf";

const Djelatnici = () => {
  const [korisnici, setKorisnici] = useState([]);
  const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;
  const { instance, accounts } = useMsal();

  const account = accounts[0];
  let userName =
    account?.name?.replace(/[ČĆ]/g, "C").replace(/[čć]/g, "c") ?? null;
  let userEmail =
    account?.username?.replace(/[ČĆ]/g, "C").replace(/[čć]/g, "c") ?? null;

  useEffect(() => {
    const fetchKorisnici = async () => {
      try {
        const response = await axios.get(`${BACKEND_API_URL}/api/korisnik/all`);
        const filteredKorisnici = response.data.filter(
          (korisnik) => korisnik.uloga === "DJELATNIK"
        );
        const korisniciSaStatistikom = await Promise.all(
          filteredKorisnici.map(async (korisnik) => {
            const outResponse = await axios.get(
              `${BACKEND_API_URL}/api/grupaZaDigitalizaciju/groupsOut/${korisnik.idKorisnika}`
            );
            const returnedResponse = await axios.get(
              `${BACKEND_API_URL}/api/grupaZaDigitalizaciju/groupsReturned/${korisnik.idKorisnika}`
            );
            return {
              ...korisnik,
              iznio: outResponse.data,
              vratio: returnedResponse.data,
            };
          })
        );

        setKorisnici(korisniciSaStatistikom);
        console.log(korisniciSaStatistikom);
      } catch (error) {
        console.error("Error fetching korisnici:", error);
      }
    };

    fetchKorisnici();
  }, []);
  const generatePdf = () => {
    const doc = new jsPDF();
    doc.setFontSize(18); // povećajte veličinu fonta
    const date = new Date();
    const dateString =
      date.toLocaleDateString() + " " + date.toLocaleTimeString();
    doc.text(`Voditelj: ${userName} `, 10, 10, null, null, "left");
    doc.text(dateString, 180, 10, null, null, "right");

    doc.text("Statistika djelatnika", 10, 20); // povećajte y koordinatu za 10

    // Dodajte zaglavlje tablice
    doc.setFontSize(14); // smanjite veličinu fonta za zaglavlje
    doc.text("Ime i Prezime", 10, 30); // povećajte y koordinatu za 10
    doc.text("Email", 80, 30); // povećajte y koordinatu za 10
    doc.text("Iznio", 150, 30); // povećajte y koordinatu za 10
    doc.text("Vratio", 180, 30); // povećajte y koordinatu za 10

    // Dodajte podatke u tablicu
    korisnici
      .sort((a, b) => b.iznio - a.iznio)
      .forEach((korisnici, index) => {
        doc.setFontSize(12);
        doc.text(
          `${korisnici.ime
            .replace(/[ČĆ]/g, "C")
            .replace(/[čć]/g, "c")} ${korisnici.prezime
            .replace(/[ČĆ]/g, "C")
            .replace(/[čć]/g, "c")}`,
          10,
          40 + index * 10 // povećajte y koordinatu za 10
        );
        doc.text(korisnici.email, 80, 40 + index * 10); // povećajte y koordinatu za 10
        doc.text(
          `${korisnici.iznio}`,
          155,
          40 + index * 10 // povećajte y koordinatu za 10
        );
        doc.text(
          `${korisnici.vratio}`,
          185,
          40 + index * 10 // povećajte y koordinatu za 10
        );
      });
    doc.line(10, 31, 200, 31); // linija između zaglavlja i podataka
    korisnici.forEach((korisnici, index) => {
      doc.line(10, 41 + index * 10, 200, 41 + index * 10); // linija između redova
    });
    doc.save("statistika_djelatnika.pdf");
  };

  return (
    <Layout>
      <div className="employee-main">
        <img
          className="employee-bg-image"
          src={pozadina}
          alt="background picture"
        ></img>
        <div className="employee-list-container">
          <div className="employee-list-wrapper">
            <div className="employee-title">Employees list</div>
            <div className="employee-list">
              <div>
                <ul>
                  {korisnici.map((korisnici, index) => (
                    <li key={index} className="employee-item">
                      <strong>
                        {korisnici.ime} {korisnici.prezime}
                      </strong>
                      {"  ("}
                      <strong>Email:</strong> {korisnici.email} {")"}
                      <br></br>
                      <strong>Uloga:</strong>
                      {"  "}
                      {korisnici.uloga ? korisnici.uloga : "Nema uloge"}
                      <br></br>
                      <strong>Digitized:</strong>
                      {"  "}
                      {korisnici.iznio}
                      {"  "}
                      <strong>Archived:</strong>
                      {"  "}
                      {korisnici.vratio}
                      <hr />
                    </li>
                  ))}
                </ul>
              </div>
            </div>
            <div className="employee-btns">
              <button onClick={() => generatePdf()}>Download</button>
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default Djelatnici;
