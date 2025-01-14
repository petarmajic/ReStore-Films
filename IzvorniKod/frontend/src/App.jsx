import { useRef, useState, useContext, useEffect } from "react";
import { LayoutProvider } from "./assets/layout/layoutcontext";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./assets/login/login";
import Home from "./assets/home/home";
import Scanner from "./assets/scanner/scanner";
import Barcodes from "./assets/barcodes/barcodes";
import Arhiva from "./assets/arhiva/arhiva";
import { useIsAuthenticated } from "@azure/msal-react";
import { Navigate } from "react-router-dom";
import Unauthorized from "./assets/unauthorized/unauthorized";
import { useMsal } from "@azure/msal-react";
import axios from "axios";
import { LayoutContext } from "./assets/layout/layoutcontext";
import Digitalizacija from "./assets/voditelj/digitalizacija/digitalizacija";
import Korisnici from "./assets/administrator/korisnici";
import Djelatnici from "./assets/voditelj/djelatnici/djelatnici";

const App = () => {
  const isAuthenticated = useIsAuthenticated();
  const { instance, accounts } = useMsal();
  const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;
  const account = accounts[0];
  let userName = account?.name ?? null;
  let userEmail = account?.username ?? null;
  if (userName !== null && userName !== "undefined") {
    const [name, surname] = userName.split(" ");
    let userId = userEmail.split("@")[0];
  }
  const uloge = [
    { id: 1, naziv: "DJELATNIK" },
    { id: 2, naziv: "VODITELJ" },
    { id: 3, naziv: "ADMINISTRATOR" },
  ];
  const { korisnikUloga, setKorisnikUloga } = useContext(LayoutContext);
  useEffect(() => {
    if (isAuthenticated) {
      const fetchKorisnik = async () => {
        try {
          const response = await axios.get(
            `${BACKEND_API_URL}/api/korisnik/${userEmail}`
          );
          setKorisnikUloga(response.data.uloga);
        } catch (error) {
          console.error("Greška pri dohvatu korisnika:", error.response.data);
        }
      };
      fetchKorisnik();
    }
  }, [isAuthenticated, accounts]);

  return (
    <LayoutProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          {isAuthenticated && korisnikUloga === "DJELATNIK" ? (
            // Koda za prikaz DJELATNIK
            <>
              <Route path="/home" element={<Home />} />
              <Route path="/scanner" element={<Scanner />} />
              <Route path="/barcodes" element={<Barcodes />} />
              <Route path="/arhiva" element={<Arhiva />} />
              <Route path="*" element={<Navigate to="/home" replace />} />
            </>
          ) : isAuthenticated && korisnikUloga === "VODITELJ" ? (
            // Koda za prikaz VODITELJA
            <>
              <Route path="/home" element={<Home />} />
              {/*
              <Route path="/scanner" element={<Scanner />} />
              <Route path="/barcodes" element={<Barcodes />} />
              <Route path="/arhiva" element={<Arhiva />} />
              */}
              <Route path="/digitalizacija" element={<Digitalizacija />} />
              <Route path="/djelatnici" element={<Djelatnici />} />
              <Route path="*" element={<Navigate to="/home" replace />} />
            </>
          ) : isAuthenticated && korisnikUloga === "ADMINISTRATOR" ? (
            // Koda za prikaz ADMINISTRATORA
            <>
              <Route path="/home" element={<Home />} />
              {/*
              <Route path="/scanner" element={<Scanner />} />
              <Route path="/barcodes" element={<Barcodes />} />
              <Route path="/arhiva" element={<Arhiva />} />
              <Route path="/digitalizacija" element={<Digitalizacija />} />
              <Route path="/djelatnici" element={<Djelatnici />} />
              */}
              <Route path="/korisnici" element={<Korisnici />} />

              <Route path="*" element={<Navigate to="/home" replace />} />
            </>
          ) : (
            <Route path="*" element={<Unauthorized />} />
          )}
        </Routes>
      </Router>
    </LayoutProvider>
  );
};

export default App;
