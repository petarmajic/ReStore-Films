import { useRef, useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./home.css";
import Layout from "../layout/layout";
import pozadina from "../images/filmskaVrpca.jpg";
import barcodeScanner from "../images/barcodeScanner.png";
import barcodeList from "../images/barcodeList.png";
import { useMsal } from "@azure/msal-react";
import { LayoutContext } from "../layout/layoutcontext";
import axios from "axios";

export default function Home() {
  const { accounts } = useMsal();
  const navigate = useNavigate();
  const account = accounts[0];
  let userName = account?.name ?? null;
  let userEmail = account?.username ?? null;
  const { korisnikUloga } = useContext(LayoutContext);
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

  const apiUrl = import.meta.env.VITE_APP_API_URL;
  const backendUrl = import.meta.env.VITE_BACKEND_API_URL;

  console.log("API URL:", apiUrl);
  console.log("Backend API URL:", backendUrl);

  const handleDeleteUser = async () => {
    try {
      const response = await axios.delete(
        `${backendUrl}/api/korisnik/delete/ADMINISTRATOR/${userEmail}`
      );
      console.log("User deleted successfully:", response.data);
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

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
              <button onClick={handleArhivaClick}>Arhiva</button>
            </div>
            <button onClick={handleDeleteUser}>delete user</button>
            {korisnikUloga === "VODITELJ" && (
              <div className="scanner-list">
                <button onClick={handleDigitalizacijaClick}>
                  Digitalizacija
                </button>
                <button onClick={handleKorisniciClick}>Korisnici</button>
              </div>
            )}
          </div>
        </div>
      </Layout>
    </>
  );
}
