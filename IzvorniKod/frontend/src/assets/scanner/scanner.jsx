import { useRef, useState, useContext, useEffect } from "react";
import BarcodeScannerComponent from "react-qr-barcode-scanner";
import "./scanner.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";

const BarcodeScanner = () => {
  const [scannedData, setScannedData] = useState(null);
  const { scannedBarcodes, setScannedBarcodes } = useContext(LayoutContext);
  const { darkMode } = useContext(LayoutContext);
  const scannedBarcodesRef = useRef(new Set(scannedBarcodes));

  useEffect(() => {
    scannedBarcodesRef.current = new Set(scannedBarcodes);
  }, [scannedBarcodes]);

  const handleScan = (err, result) => {
    if (result) {
      const newBarcode = result.text;
      setScannedData(newBarcode);

      if (!scannedBarcodesRef.current.has(newBarcode)) {
        setScannedBarcodes((prevBarcodes) => [...prevBarcodes, newBarcode]);
        scannedBarcodesRef.current.add(newBarcode);
      }
    }
  };

  const handleFetchFilmData = () => {
    if (scannedData) {
      window.location.href = `/api/filmovi/qr/${scannedData}`;
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
            </div>
          )}
        </div>
        <div className="barcode-list-container">
          <h3>Scanned Barcodes:</h3>
          <ul>
            {scannedBarcodes.map((barcode, index) => (
              <li key={index}>{barcode}</li>
            ))}
          </ul>
        </div>
      </div>
    </Layout>
  );
};

export default BarcodeScanner;
