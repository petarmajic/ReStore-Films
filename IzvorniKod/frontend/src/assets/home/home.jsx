import { useRef, useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./home.css";
import Layout from "../layout/layout";
import pozadina from "../images/filmskaVrpca.jpg";
import barcodeScanner from "../images/barcodeScanner.png";
import barcodeList from "../images/barkodLista.png";
import archivePng from "../images/arhiva.png";
import digitizationPng from "../images/digitalizacija.png";
import employeePng from "../images/employee.png";
import userPng from "../images/user.png";
import { useMsal } from "@azure/msal-react";
import { LayoutContext } from "../layout/layoutcontext";
import axios from "axios";
import { useIsAuthenticated } from "@azure/msal-react";

const Home = () => {
  const { accounts } = useMsal();
  const navigate = useNavigate();
  const account = accounts[0];
  let userName = account?.name ?? null;
  let userEmail = account?.username ?? null;
  const { korisnikUloga, setKorisnikUloga } = useContext(LayoutContext);
  //const korisnikUloga = "DJELATNIK";

  const isAuthenticated = useIsAuthenticated();
  const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;

  const handleScannerClick = () => {
    navigate("/scanner");
  };
  const handleBarcodesClick = () => {
    navigate("/barcodes");
  };
  const handleArhivaClick = () => {
    navigate("/arhiva");
  };

  const handleDigitalizacijaClick = () => {
    navigate("/digitalizacija");
  };
  const handleKorisniciClick = () => {
    navigate("/korisnici");
  };
  const handleDjelatniciClick = () => {
    navigate("/djelatnici");
  };

  const apiUrl = import.meta.env.VITE_APP_API_URL;
  const backendUrl = import.meta.env.VITE_BACKEND_API_URL;

  //console.log("API URL:", apiUrl);
  //console.log("Backend API URL:", backendUrl);

  return (
    <>
      <Layout>
        <div className="home-container">
          <img
            className="home-bg-image"
            src={pozadina}
            alt="background picture"
          ></img>
          <div className="btn-home">
            {korisnikUloga === "DJELATNIK" && (
              <>
                <div className="scanner-list">
                  <button
                    onClick={handleScannerClick}
                    style={{ marginRight: "10px" }}
                  >
                    <img
                      className="scan-img"
                      src={barcodeScanner}
                      alt="barcode scanner"
                    ></img>
                    Scan Barcode
                  </button>
                </div>
                <div className="scanner-list">
                  <button onClick={handleBarcodesClick}>
                    <img
                      className="scan-img"
                      src={barcodeList}
                      alt="barcode list"
                    ></img>
                    Barcode List
                  </button>
                </div>
                <div className="scanner-list">
                  <button onClick={handleArhivaClick}>
                    <img
                      className="scan-img"
                      src={archivePng}
                      alt="archive"
                    ></img>
                    Archive
                  </button>
                </div>
              </>
            )}
            {korisnikUloga === "VODITELJ" && (
              <>
                <div className="scanner-list">
                  <button onClick={handleDigitalizacijaClick}>
                    <img
                      className="scan-img"
                      src={digitizationPng}
                      alt="digitization"
                    ></img>
                    Digitization
                  </button>
                </div>
                <div className="scanner-list">
                  <button onClick={handleDjelatniciClick}>
                    <img
                      className="scan-img"
                      src={employeePng}
                      alt="employees"
                    ></img>
                    Employees
                  </button>
                </div>
              </>
            )}
            {korisnikUloga === "ADMINISTRATOR" && (
              <>
                <div className="scanner-list">
                  <button onClick={handleKorisniciClick}>
                    <img className="scan-img" src={userPng} alt="users"></img>
                    Users
                  </button>
                </div>
              </>
            )}
          </div>
        </div>
      </Layout>
    </>
  );
};
export default Home;
