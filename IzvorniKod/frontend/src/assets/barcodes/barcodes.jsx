import { useState, useEffect, useMemo, useContext } from "react";
import "./barcodes.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";
import { useNavigate } from "react-router-dom";
import pozadina from "../images/filmskaVrpca.jpg";
import jsPDF from "jspdf";
import { useMsal } from "@azure/msal-react";

const Barcodes = () => {
  const { scannedBarcodes, setScannedBarcodes } = useContext(LayoutContext);
  const [selectedBarcodes, setSelectedBarcodes] = useState({});

  const navigate = useNavigate();
  const [selectedGroups, setSelectedGroups] = useState({});
  const { instance, accounts } = useMsal();

  const account = accounts[0];
  let userName =
    account?.name?.replace(/[ČĆ]/g, "C").replace(/[čć]/g, "c") ?? null;
  let userEmail =
    account?.username?.replace(/[ČĆ]/g, "C").replace(/[čć]/g, "c") ?? null;

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
  const handleDigitalizacijaClick = () => {
    console.log("Ime i email korisnika:", userName, userEmail);
    setSelectedBarcodes({});
    Object.keys(selectedGroups).forEach((groupKey) => {
      selectedBarcodes[groupKey] = groupedBarcodes[groupKey];
    });
    // Generirajte PDF dokument sa odabranim filmovima
    const pdfDoc = new jsPDF();
    Object.keys(selectedBarcodes).forEach((groupKey, index) => {
      if (index > 0) {
        pdfDoc.addPage();
      }
      const date = new Date();
      const dateString =
        date.toLocaleDateString() + " " + date.toLocaleTimeString();
      pdfDoc.text(`${userName} (${userEmail})`, 10, 10, null, null, "left");
      pdfDoc.text(dateString, 180, 10, null, null, "right");
      pdfDoc.text(
        "Popis filmova na digitalizaciji:",
        10,
        20,
        null,
        null,
        "left"
      );
      const groupDuration = selectedBarcodes[groupKey].reduce(
        (acc, barcode) => {
          const durationInSeconds = durationToSeconds(barcode.duration);
          return acc + durationInSeconds;
        },
        0
      );
      const groupDurationText = secondsToDuration(groupDuration);
      pdfDoc.text(
        `Grupa ${index + 1} (Ukupno trajanje: ${groupDurationText})`,
        10,
        30
      );
      selectedBarcodes[groupKey].forEach((barcode, barcodeIndex) => {
        const text = `${barcodeIndex + 1}. ${
          barcode.barcode
        } - ${barcode.filmTitle.replace(/č/g, "c").replace(/ć/g, "c")} - ${
          barcode.duration
        }`;
        pdfDoc.text(text, 10, 40 + barcodeIndex * 10, null, null, "left", true);
      });
      pdfDoc.text(`Potpis: ___________`, 180, 280, null, null, "right");
    });
    const pdfBlob = new Blob([pdfDoc.output("blob")], {
      type: "application/pdf",
    });
    const pdfUrl = URL.createObjectURL(pdfBlob);
    const link = document.createElement("a");
    link.href = pdfUrl;
    link.download = "digitalizacija.pdf";
    link.click();
    URL.revokeObjectURL(pdfUrl);

    const selectedBarcodesArray = Object.values(selectedBarcodes);
    const newScannedBarcodes = scannedBarcodes.filter((barcode) => {
      return !selectedBarcodesArray.some((group) => {
        return group.some((selectedBarcode) => {
          return selectedBarcode.barcode === barcode.barcode;
        });
      });
    });
    setScannedBarcodes(newScannedBarcodes);
    setSelectedBarcodes({});
    setSelectedGroups({});
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

  // Funkcija za konverziju trajanja u sekunde
  const durationToSeconds = (duration) => {
    return duration
      .split(":")
      .map(Number)
      .reduce((acc, val) => acc * 60 + val, 0);
  };

  // Funkcija za konverziju sekundi u "MM:SS" format
  const secondsToDuration = (seconds) => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes}:${remainingSeconds.toString().padStart(2, "0")}`;
  };
  //
  //
  //
  //
  //
  const groupBarcodesByDuration = (barcodes) => {
    if (barcodes.length === 0) {
      return {};
    }
    const maxDuration = 45 * 60;
    const durationToSeconds = (duration) => {
      const [hours, minutes, seconds] = duration.split(":").map(Number);
      return (hours || 0) * 3600 + (minutes || 0) * 60 + (seconds || 0);
    };
    const secondsToDuration = (seconds) => {
      const hours = Math.floor(seconds / 3600);
      const minutes = Math.floor((seconds % 3600) / 60);
      const remainingSeconds = seconds % 60;
      return [
        hours > 0 ? String(hours).padStart(2, "0") : "00",
        String(minutes).padStart(2, "0"),
        String(remainingSeconds).padStart(2, "0"),
      ].join(":");
    };

    const splitLongBarcodes = (barcode) => {
      const durationInSeconds = durationToSeconds(barcode.duration);
      const segments = [];
      let remainingDuration = durationInSeconds;
      let partNumber = 1;

      while (remainingDuration > maxDuration) {
        segments.push({
          ...barcode,
          duration: secondsToDuration(maxDuration),
          filmTitle: `${barcode.filmTitle} - ${partNumber}. dio`,
        });
        remainingDuration -= maxDuration;
        partNumber++;
      }

      if (remainingDuration > 0) {
        segments.push({
          ...barcode,
          duration: secondsToDuration(remainingDuration),
          filmTitle: `${barcode.filmTitle} - ${partNumber}. dio`,
        });
      }
      return segments;
    };
    const processBarcodes = (barcodes, callback) => {
      let index = 0;
      const chunkSize = 50;
      const processNextChunk = () => {
        const chunk = barcodes.slice(index, index + chunkSize);
        callback(chunk);
        index += chunkSize;

        if (index < barcodes.length) {
          setTimeout(processNextChunk, 0);
        }
      };

      processNextChunk();
    };

    const preparedBarcodes = [];
    processBarcodes(barcodes, (chunk) => {
      chunk.forEach((barcode) => {
        const durationInSeconds = durationToSeconds(barcode.duration);
        if (durationInSeconds > maxDuration) {
          preparedBarcodes.push(...splitLongBarcodes(barcode));
        } else {
          preparedBarcodes.push(barcode);
        }
      });
    });
    preparedBarcodes.sort((a, b) => {
      return durationToSeconds(b.duration) - durationToSeconds(a.duration);
    });

    const groups = [];
    preparedBarcodes.forEach((barcode) => {
      const duration = durationToSeconds(barcode.duration);
      let placed = false;

      for (let j = 0; j < groups.length; j++) {
        const groupDuration = groups[j].reduce(
          (sum, b) => sum + durationToSeconds(b.duration),
          0
        );
        if (groupDuration + duration <= maxDuration) {
          groups[j].push(barcode);
          placed = true;
          break;
        }
      }

      if (!placed) {
        groups.push([barcode]);
      }
    });

    const groupedBarcodes = {};
    groups.forEach((group, index) => {
      const totalDuration = group.reduce(
        (sum, b) => sum + durationToSeconds(b.duration),
        0
      );
      const title = `Group ${index + 1}: ${secondsToDuration(totalDuration)}`;
      groupedBarcodes[title] = group;
    });

    console.log("Grupe filmova: ", groupedBarcodes);
    return groupedBarcodes;
  };
  const groupedBarcodes = useMemo(() => {
    return groupBarcodesByDuration(scannedBarcodes);
  }, [scannedBarcodes]);

  useEffect(() => {
    const newSelectedGroups = {};
    Object.keys(groupedBarcodes).forEach((groupKey) => {
      newSelectedGroups[groupKey] = true;
    });
    setSelectedGroups(newSelectedGroups);
  }, [groupedBarcodes]);

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
                        ? `${barcode.database ? "✅" : "◼️"} ${
                            barcode.barcode
                          } - ${barcode.filmTitle} - ${barcode.duration}`
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
            <div className="barcode-digit-btn">
              <button id="digit" onClick={handleDigitalizacijaClick}>
                Digitization
              </button>
            </div>
          </div>

          <div className="barcode-grouped">
            <div className="right-title">Grouped barcodes</div>
            <div className="grouped-list">
              {Object.keys(groupedBarcodes).map((groupKey) => (
                <div className="wrap-group">
                  <p className="group-key">{groupKey}</p>
                  <div>
                    <ul>
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
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default Barcodes;
