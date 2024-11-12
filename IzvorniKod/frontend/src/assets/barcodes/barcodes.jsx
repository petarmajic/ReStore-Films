import { useRef, useState, useContext } from "react";
import "./barcodes.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";
import { useNavigate } from "react-router-dom";
import pozadina from "../images/filmskaVrpca.jpg";

const Barcodes = () => {
  const { scannedBarcodes, setScannedBarcodes } = useContext(LayoutContext);
  const scannedBarcodesRef = useRef(new Set(scannedBarcodes));
  const navigate = useNavigate();

  const handleScannerClick = () => {
    navigate("/scanner");
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

  const calculateTotalDuration = () => {
    const totalDuration = scannedBarcodes.reduce((acc, barcode) => {
      if (typeof barcode === "object" && barcode.duration) {
        const [hours, minutes, seconds] = barcode.duration
          .split(":")
          .map(Number);
        const durationInSeconds = hours * 3600 + minutes * 60 + seconds;
        return acc + durationInSeconds;
      }
      return acc;
    }, 0);

    const hours = Math.floor(totalDuration / 3600);
    const minutes = Math.floor((totalDuration % 3600) / 60);
    const seconds = totalDuration % 60;

    return `${hours.toString().padStart(2, "0")}:${minutes
      .toString()
      .padStart(2, "0")}:${seconds.toString().padStart(2, "0")}`;
  };
  const groupBarcodesByDuration = (barcodes) => {
    const sortedBarcodes = barcodes.sort((a, b) => {
      const durationA = a.duration
        .split(":")
        .map(Number)
        .reduce((acc, val) => acc * 60 + val, 0);
      const durationB = b.duration
        .split(":")
        .map(Number)
        .reduce((acc, val) => acc * 60 + val, 0);
      return durationA - durationB;
    });

    const groupedBarcodes = {};
    let currentTime = 0;
    let currentGroup = [];

    for (const barcode of sortedBarcodes) {
      const duration = barcode.duration
        .split(":")
        .map(Number)
        .reduce((acc, val) => acc * 60 + val, 0);
      if (currentTime + duration <= 45 * 60) {
        currentGroup.push(barcode);
        currentTime += duration;
      } else {
        const title = `${Math.floor(currentTime / 60)}:${(currentTime % 60)
          .toString()
          .padStart(2, "0")}`;
        groupedBarcodes[title] = currentGroup;
        currentGroup = [barcode];
        currentTime = duration;
      }
    }

    if (currentGroup.length > 0) {
      const title = `${Math.floor(currentTime / 60)}:${(currentTime % 60)
        .toString()
        .padStart(2, "0")}`;
      groupedBarcodes[title] = currentGroup;
    }

    return groupedBarcodes;
  };

  const groupedBarcodes = groupBarcodesByDuration(scannedBarcodes);

  return (
    <Layout>
      <div className="barcode-main">
        <img
          className="barcode-bg-image"
          src={pozadina}
          alt="background picture"
        ></img>
        <div className="barcode-list-container">
          <div className="barcode-scanned">
            <div className="left-title">Scanned barcodes</div>
            <div className="left-list">
              <ul>
                {scannedBarcodes.map((barcode, index) => (
                  <li key={index}>
                    <span style={{ fontSize: "16px" }}>
                      {index + 1}.{" "}
                      {typeof barcode === "object"
                        ? `${barcode.barcode} - ${barcode.filmTitle} - ${barcode.duration}`
                        : barcode}
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
            </div>
            <p className="total-duration">
              Total duration: {calculateTotalDuration()}
            </p>
            <div className="barcode-btns">
              <button onClick={handleClearBarcodes}>Clear</button>
              <button onClick={handleScannerClick}>Scan</button>
            </div>
          </div>

          <div className="barcode-grouped">
            <div className="right-title">Grouped barcodes</div>
            {Object.keys(groupedBarcodes).map((groupKey) => (
              <div key={groupKey} className="wrap-group">
                <p className="group-key">Duration: {groupKey}</p>
                <ul className="grouped-list">
                  {groupedBarcodes[groupKey].map((barcode, index) => (
                    <li key={index}>
                      <span style={{ fontSize: "16px" }}>
                        {index + 1}.{" "}
                        {typeof barcode === "object"
                          ? `${barcode.barcode} - ${barcode.filmTitle} - ${barcode.duration}`
                          : barcode}
                      </span>
                    </li>
                  ))}
                </ul>
              </div>
            ))}
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default Barcodes;
