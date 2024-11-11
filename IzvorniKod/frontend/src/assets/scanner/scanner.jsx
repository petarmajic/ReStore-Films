import { useRef, useState, useContext, useEffect } from "react";
import axios from "axios";
import BarcodeScannerComponent from "react-qr-barcode-scanner";
import "./scanner.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";
import { useNavigate } from "react-router-dom";

const BarcodeScanner = () => {
  const [scannedData, setScannedData] = useState(null);
  const [error, setError] = useState(null);
  const { scannedBarcodes, setScannedBarcodes } = useContext(LayoutContext);
  const { darkMode } = useContext(LayoutContext);
  const scannedBarcodesRef = useRef(new Set(scannedBarcodes));
  const navigate = useNavigate();

  useEffect(() => {
    scannedBarcodesRef.current = new Set(scannedBarcodes);
  }, [scannedBarcodes]);

  const handleScan = (err, result) => {
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
      <div className={`center-container ${darkMode ? "dark-mode" : ""}`}>
        <div className="scanner-container">
          <h3>Barcode scanner</h3>
          <BarcodeScannerComponent
            onUpdate={handleScan}
            width={500}
            height={300}
          />
          {scannedData && (
            <div>
              <p>Barcode: {scannedData}</p>
              <button className="button" onClick={handleFetchFilmData}>
                Fetch Film Data
              </button>
              {error && <p style={{ color: "red" }}>{error}</p>}
            </div>
          )}
        </div>

        <div className="barcode-list-container">
          <h3>Scanned Barcodes:</h3>
          <ul>
            {scannedBarcodes.map((barcode, index) => (
              <li key={index}>
                {barcode.barcode} - {barcode.filmTitle} ({barcode.duration})
              </li>
            ))}
          </ul>
        </div>
        <div>
          <button onClick={handleBarcodesClick}>Barcodes</button>
        </div>
      </div>
    </Layout>
  );
};

export default BarcodeScanner;
