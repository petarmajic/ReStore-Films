import { useRef, useState, useContext } from "react";
import BarcodeScannerComponent from "react-qr-barcode-scanner";
import "./barcodes.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";

const BarcodeScanner = () => {
  const [scannedData, setScannedData] = useState(null);
  const { scannedBarcodes, setScannedBarcodes } = useContext(LayoutContext);
  const scannedBarcodesRef = useRef(new Set());
  const { darkMode } = useContext(LayoutContext);

  const handleFetchFilmData = () => {
    if (scannedData) {
      window.location.href = `/api/filmovi/qr/${scannedData}`;
    }
  };

  const handleClearBarcodes = () => {
    setScannedBarcodes([]);
  };

  const handleRemoveBarcode = (index) => {
    if (index >= 0 && index < scannedBarcodes.length) {
      const newBarcodes = [...scannedBarcodes];
      newBarcodes.splice(index, 1);
      setScannedBarcodes(newBarcodes);
    }
  };

  return (
    <Layout>
      <div className={`center-container ${darkMode ? "dark-mode" : ""}`}>
        <div className="barcode-list-container">
          <button className="button" onClick={handleClearBarcodes}>
            Clear barcodes
          </button>
          <h3>Scanned Barcodes:</h3>
          <ul>
            {scannedBarcodes.map((barcode, index) => (
              <li key={index}>
                <span style={{ fontSize: "16px" }}>
                  {index + 1}. {barcode}
                </span>
                <button
                  style={{
                    fontSize: "16px",
                    padding: "0 5px",
                    border: "none",
                    backgroundColor: "transparent",
                    cursor: "pointer",
                  }}
                  onClick={() => handleRemoveBarcode(index)}
                >
                  ❌
                </button>
              </li>
            ))}
          </ul>
          <button className="button" onClick={handleFetchFilmData}>
            Fetch barcodes
          </button>
        </div>
      </div>
    </Layout>
  );
};

export default BarcodeScanner;
