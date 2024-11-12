import { useRef, useState, useContext, useEffect } from "react";
import axios from "axios";
import BarcodeScannerComponent from "react-qr-barcode-scanner";
import "./scanner.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";
import { useNavigate } from "react-router-dom";
import pozadina from "../images/filmskaVrpca.jpg";

const BarcodeScanner = () => {
  const [scannedData, setScannedData] = useState(null);
  const [error, setError] = useState(null);
  const { scannedBarcodes, setScannedBarcodes } = useContext(LayoutContext);
  const scannedBarcodesRef = useRef(new Set(scannedBarcodes));
  const navigate = useNavigate();

  useEffect(() => {
    scannedBarcodesRef.current = new Set(scannedBarcodes);
  }, [scannedBarcodes]);

  const handleScan = (_err, result) => {
    if (result) {
      const newBarcode = result.text;
      setScannedData(newBarcode);

      if (!scannedBarcodesRef.current.has(newBarcode)) {
        handleFetchFilmData();
      }
    }
  };

  const handleBarcodesClick = () => {
    navigate("/barcodes");
  };

  useEffect(() => {
    if (scannedData) {
      handleFetchFilmData();
    }
  }, [scannedData]);
  const handleFetchFilmData = async () => {
    if (scannedData) {
      try {
        const url = `/api/filmskaTrakaArhiva/${scannedData}`;
        const response = await axios.get(url, {
          headers: { "Content-Type": "application/json" },
        });

        console.log("Film data fetched:", response.data);

        const existingBarcode = scannedBarcodes.find((barcode) => {
          return typeof barcode === "object" && barcode.barcode === scannedData;
        });

        if (!existingBarcode) {
          const updatedBarcodes = [
            ...scannedBarcodes,
            {
              barcode: scannedData,
              filmTitle: response.data.originalniNaslov,
              duration: response.data.duration,
            },
          ];

          setScannedBarcodes(updatedBarcodes);
          scannedBarcodesRef.current.add(scannedData);
        } else {
          console.log("Barcode already exists with title:", existingBarcode);
        }

        setError(null);
      } catch (error) {
        console.error("Error fetching film data:", error);
        setError("Error fetching film data");
      }
    }
  };
  return (
    <Layout>
      <div className="scan-layout">
        <img
          className="scan-bg-image"
          src={pozadina}
          alt="background picture"
        ></img>
        <div className="scan-container">
          <div className="scanner-wrapper">
            <div className="sc-title">Barcode scanner</div>
            <div className="cam-dim">
              <BarcodeScannerComponent onUpdate={handleScan} />
            </div>
            <div className="fetch-div">
              <div>
                <div className="fetch-text">
                  <p>Barcode: {scannedData}</p>
                  {error && <p style={{ color: "red" }}>{error}</p>}
                </div>
                <div className="fetch-btn">
                  <button className="button-film" onClick={handleFetchFilmData}>
                    Fetch
                  </button>
                  <button onClick={handleBarcodesClick}>List</button>
                </div>
              </div>
            </div>
          </div>

          <div className="brc-list-container">
            <div className="scanned-title">Scanned barcodes</div>
            <div className="scan-br-list">
              <ul>
                {scannedBarcodes.map((barcode, index) => (
                  <li key={index}>
                    {barcode.barcode} - {barcode.filmTitle} ({barcode.duration})
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default BarcodeScanner;
