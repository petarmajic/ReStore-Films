import React from "react";
import { useNavigate } from "react-router-dom";
import "./home.css";
import Layout from "../layout/layout";
import pozadina from "../images/filmskaVrpca.jpg";
import barcodeScanner from "../images/barcodeScanner.png";
import barcodeList from "../images/barcodeList.png";
import { useMsal } from "@azure/msal-react";

export default function Microsoft() {
  const { accounts } = useMsal();
  const navigate = useNavigate();

  const handleScannerClick = () => {
    navigate("/scanner");
  };
  const handleBarcodesClick = () => {
    navigate("/barcodes");
  };

  return (
    <>
      <Layout>
        <div className="home-container">
            <img className="home-bg-image" src={pozadina} alt="background picture"></img>
                <div className="btn-home">
                  <div className="scanner-list">
                    <button onClick={handleScannerClick} style={{ marginRight: "10px" }}>
                    <img className="scan-img" src={barcodeScanner} alt="barcode scanner"></img>
                      Scan Barcode
                    </button>
                  </div>
                  <div className="scanner-list">
                    <button onClick={handleBarcodesClick}>
                    <img className="scan-img" src={barcodeList} alt="barcode list"></img>
                      Barcode List
                    </button>
                  </div>
                </div>
        </div>
      </Layout>
    </>
  );
}
