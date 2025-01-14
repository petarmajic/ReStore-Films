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
      const korisnik = { uloga: event.target.value };
      const response = await axios.patch(
        `${BACKEND_API_URL}/api/korisnik/update/ADMINISTRATOR/${email}`,
        korisnik
      );
      console.log("Uloga updated successfully:", response.data);
      setKorisnici((prevKorisnici) =>
        prevKorisnici.map((korisnik) =>
          korisnik.email === email ? { ...response.data } : korisnik
        )
      );
    } catch (error) {
      console.error("Error updating uloga:", error);
    }
  };

  const generatePdf = () => {
    const doc = new jsPDF();
    doc.setFontSize(18); // povećajte veličinu fonta
    const date = new Date();
    const dateString =
      date.toLocaleDateString() + " " + date.toLocaleTimeString();
    doc.text(`Administrator: ${userName} `, 10, 10, null, null, "left");
    doc.text(dateString, 180, 10, null, null, "right");

    doc.text("Popis korisnika:", 10, 20); // povećajte y koordinatu za 10

    // Dodajte zaglavlje tablice
    doc.setFontSize(14); // smanjite veličinu fonta za zaglavlje
    doc.text("Ime i Prezime", 10, 30); // povećajte y koordinatu za 10
    doc.text("Email", 80, 30); // povećajte y koordinatu za 10
    doc.text("Uloga", 150, 30); // povećajte y koordinatu za 10

    korisnici
      .sort((a, b) => {
        if (a.uloga === b.uloga) {
          return a.ime.localeCompare(b.ime);
        } else {
          return a.uloga.localeCompare(b.uloga);
        }
      })
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
          `${korisnici.uloga}`,
          150,
          40 + index * 10 // povećajte y koordinatu za 10
        );
      });
    doc.line(10, 31, 200, 31); // linija između zaglavlja i podataka
    korisnici.forEach((korisnici, index) => {
      doc.line(10, 41 + index * 10, 200, 41 + index * 10); // linija između redova
    });
    doc.save("korisnici.pdf");
  };

  return (
    <Layout>
      <div className="user-main">
        <img
          className="user-bg-image"
          src={pozadina}
          alt="background picture"
        ></img>
        <div className="user-list-container">
          <div className="user-list-wrapper">
            <div className="user-title">System users list</div>
            <div className="user-list">
              <div>
                <ul>
                  {korisnici.map((korisnik, index) => (
                    <li key={index} className="user-item">
                      <strong>
                        {korisnik.ime} {korisnik.prezime}
                      </strong>
                      {" ("}
                      <strong>Email:</strong> {korisnik.email} {") "}
                      <br></br>
                      <strong>Uloga:</strong>{" "}
                      {korisnik.uloga ? korisnik.uloga : "Nema uloge"}
                      <br></br>
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
            <div className="user-btns">
              {" "}
              <button onClick={() => generatePdf()}>Download</button>
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default KorisniciList;
