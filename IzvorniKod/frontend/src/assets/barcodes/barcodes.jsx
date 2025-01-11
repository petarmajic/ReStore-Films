import { useState, useEffect, useMemo, useContext } from "react";
import "./barcodes.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";
import { useNavigate } from "react-router-dom";
import pozadina from "../images/filmskaVrpca.jpg";
import jsPDF from "jspdf";
import { useMsal } from "@azure/msal-react";
import axios from "axios";

const Barcodes = () => {
  const { scannedBarcodes, setScannedBarcodes } = useContext(LayoutContext);
  const [selectedBarcodes, setSelectedBarcodes] = useState({});

  const navigate = useNavigate();
  const [selectedGroups, setSelectedGroups] = useState({});
  const { instance, accounts } = useMsal();
  const [korisnikId, setKorisnikId] = useState(null);

  const account = accounts[0];
  let userName =
    account?.name?.replace(/[ČĆ]/g, "C").replace(/[čć]/g, "c") ?? null;
  let userEmail =
    account?.username?.replace(/[ČĆ]/g, "C").replace(/[čć]/g, "c") ?? null;
  const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;
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
  useEffect(() => {
    const fetchKorisnikData = async () => {
      try {
        const url = `${BACKEND_API_URL}/api/korisnik/${userEmail}`;
        const response = await axios.get(url);
        const korisnikData = response.data;
        if (korisnikData) {
          setKorisnikId(korisnikData.idKorisnika);
        } else {
          console.log("No korisnik data found");
        }
      } catch (error) {
        console.error("Error fetching korisnikID:", error);
      }
    };
    fetchKorisnikData();
  }, []);
  const handleDigitalizacijaClick = async () => {
    console.log("Ime i email korisnika:", userName, userEmail, korisnikId);

    // Kreiranje nove kopije selectedBarcodes
    const newSelectedBarcodes = {};
    for (const groupKey of Object.keys(selectedGroups)) {
      newSelectedBarcodes[groupKey] = groupedBarcodes[groupKey];
    }

    const pdfDoc = new jsPDF();
    const date = new Date();
    const dateString =
      date.toLocaleDateString() + " " + date.toLocaleTimeString();
    let pageIndex = 0;

    for (const [groupKey, groupValue] of Object.entries(newSelectedBarcodes)) {
      if (pageIndex > 0) {
        pdfDoc.addPage();
      }

      // Dodaj osnovne informacije u PDF
      pdfDoc.text(`${userName} (${userEmail})`, 10, 10);
      pdfDoc.text(dateString, 180, 10, null, null, "right");
      pdfDoc.text("Popis filmova na digitalizaciji:", 10, 20);

      const groupDuration = groupValue.reduce(
        (acc, barcode) => acc + durationToSeconds(barcode.duration),
        0
      );

      const filmskeTrake = newSelectedBarcodes[groupKey].map(
        (barcode) => barcode.filmTitle
      );

      // Validacija filmskeTrake
      if (!filmskeTrake.length) {
        console.warn(`Grupa ${groupKey} nema ispravnih filmskih traka.`);
        continue;
      }

      console.log(`Slanje zahtjeva sa filmskim trakama:`, filmskeTrake);

      try {
        // Slanje zahtjeva na backend
        const response = await axios.post(
          `${BACKEND_API_URL}/api/grupaZaDigitalizaciju/add`,
          {
            iznioIzSkladistaKorisnikId: korisnikId,
            filmskeTrake,
          },
          {
            headers: { "Content-Type": "application/json" },
          }
        );

        const grupaId = response.data.idGrupe;
        const groupDurationText = secondsToDuration(groupDuration);

        pdfDoc.text(
          `Grupa ${grupaId} (Ukupno trajanje: ${groupDurationText})`,
          10,
          30
        );

        // Dodavanje bar kodova u PDF
        groupValue.forEach((barcode, barcodeIndex) => {
          const text = `${barcodeIndex + 1}. ${
            barcode.barcode
          } - ${barcode.filmTitle
            .replace(/Č/g, "C")
            .replace(/Ć/g, "C")
            .replace(/č/g, "c")
            .replace(/ć/g, "c")}${barcode.part} - ${barcode.duration}`;
          pdfDoc.text(text, 10, 40 + barcodeIndex * 10);
        });

        pdfDoc.text("Potpis: ___________", 180, 280, null, null, "right");
      } catch (error) {
        console.error(`Greška kod slanja grupe ${groupKey}:`, error);
      }
      pageIndex++;
    }

    // Generiraj i preuzmi PDF
    const pdfBlob = new Blob([pdfDoc.output("blob")], {
      type: "application/pdf",
    });
    const pdfUrl = URL.createObjectURL(pdfBlob);
    const link = document.createElement("a");
    link.href = pdfUrl;
    link.download = "digitalizacija.pdf";
    link.click();
    URL.revokeObjectURL(pdfUrl);

    // Ažuriraj stanje bar kodova
    const selectedBarcodesArray = Object.values(newSelectedBarcodes);
    const newScannedBarcodes = scannedBarcodes.filter((barcode) => {
      return !selectedBarcodesArray.some((group) =>
        group.some(
          (selectedBarcode) => selectedBarcode.barcode === barcode.barcode
        )
      );
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
          part: ` - ${partNumber}. dio`,
        });
        remainingDuration -= maxDuration;
        partNumber++;
      }

      if (remainingDuration > 0) {
        segments.push({
          ...barcode,
          duration: secondsToDuration(remainingDuration),
          part: ` - ${partNumber}. dio`,
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
                              ? `${barcode.barcode} - ${barcode.filmTitle}${barcode.part} - ${barcode.duration}`
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
