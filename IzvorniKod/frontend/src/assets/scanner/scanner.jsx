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
  const [displayInputFile, setDisplayInputFile] = useState(false);
  const [displayForm, setDisplayForm] = useState(false);
  const [database, setDatabase] = useState(false);

  const [originalniNaslov, setOriginalniNaslov] = useState("");
  const [jezikOriginala, setJezikOriginala] = useState("");
  const [ton, setTon] = useState("DA");
  const [porijekloZemljaProizvodnje, setPorijekloZemljaProizvodnje] =
    useState("");
  const [godinaProizvodnje, setGodinaProizvodnje] = useState(
    new Date().getFullYear()
  );
  const [duration, setDuration] = useState("00:00:00");
  const [isValid, setIsValid] = useState(false);

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
        const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;
        const url = `${BACKEND_API_URL}/api/filmskaTrakaArhiva/${scannedData}`;
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
              database: true,
            },
          ];

          setScannedBarcodes(updatedBarcodes);
          scannedBarcodesRef.current.add(scannedData);
        } else {
          console.log("Barcode already exists with title:", existingBarcode);
          setError(
            `Barcode ${scannedData} already exists with title: ${existingBarcode.filmTitle}`
          );
          setTimeout(() => {
            setError(null);
          }, 7000);
        }
        setDisplayInputFile(false);
        setError(null);
      } catch (error) {
        console.error("Error fetching film data:", error);
        setError("Error fetching film data from server");
        setDisplayInputFile(true);
      }
    }
  };
  const handleXmlFileChange = (event) => {
    try {
      try {
        const xmlFile = event.target.files[0];
        const reader = new FileReader();

        reader.onload = (event) => {
          try {
            const xmlData = event.target.result;
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(xmlData, "text/xml");
            const filmTitle =
              xmlDoc.getElementsByTagName("OriginalniNaslov")[0].childNodes[0]
                .nodeValue;
            const duration =
              xmlDoc.getElementsByTagName("Duration")[0].childNodes[0]
                .nodeValue;
            const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;

            const newFilm = {
              originalniNaslov: filmTitle,
              jezikOriginala:
                xmlDoc.getElementsByTagName("JezikOriginala")[0].childNodes[0]
                  .nodeValue,
              ton: xmlDoc.getElementsByTagName("Ton")[0].childNodes[0]
                .nodeValue,
              porijekloZemljaProizvodnje: xmlDoc.getElementsByTagName(
                "Porijeklo_ZemljaProizvodnje"
              )[0].childNodes[0].nodeValue,
              godinaProizvodnje:
                xmlDoc.getElementsByTagName("GodinaProizvodnje")[0]
                  .childNodes[0].nodeValue,
              duration: duration,
            };

            axios
              .post(`${BACKEND_API_URL}/api/filmskaTraka/add`, newFilm, {
                headers: { "Content-Type": "application/json" },
              })
              .then((response) => {
                console.log("Film successfully added:", response.data);
              })
              .catch((error) => {
                console.error("Error sending data to server:", error);
              });

            const existingBarcode = scannedBarcodes.find((barcode) => {
              return (
                typeof barcode === "object" && barcode.barcode === scannedData
              );
            });

            if (!existingBarcode) {
              const updatedBarcodes = [
                ...scannedBarcodes,
                {
                  barcode: scannedData,
                  filmTitle,
                  duration,
                  database: false,
                },
              ];

              setScannedBarcodes(updatedBarcodes);
              scannedBarcodesRef.current.add(scannedData);
            } else {
              setError(
                "Barcode already exists with title: " +
                  existingBarcode.filmTitle
              );
              setTimeout(() => {
                setError(null);
              }, 7000);
            }
          } catch (error) {
            console.error("Error parsing XML:", error);
            setError("Error parsing XML");
            setTimeout(() => {
              setError(null);
            }, 7000);
          }
        };

        reader.readAsText(xmlFile);
      } catch (error) {
        console.error("Error reading XML file:", error);
        setError("Error reading XML file");
        setTimeout(() => {
          setError(null);
        }, 7000);
      }
    } catch (error) {
      console.error("Unknown error:", error);
      setError("Unknown error");
      setTimeout(() => {
        setError(null);
      }, 7000);
    } finally {
      setTimeout(() => {
        setError(null);
      }, 7000);
      setDisplayInputFile(false);
    }
  };

  const [filmTitle, setFilmTitle] = useState("");
  const [year, setYear] = useState("");

  const handleManualEntry = () => {
    setDisplayForm(!displayForm);
    setDisplayInputFile(false);
  };

  useEffect(() => {
    const currentYear = new Date().getFullYear(); // Trenutna godina

    if (
      originalniNaslov !== "" &&
      jezikOriginala !== "" &&
      ton !== "" &&
      porijekloZemljaProizvodnje !== "" &&
      godinaProizvodnje !== "" &&
      duration !== "" &&
      /^\d{2}:\d{2}:\d{2}$/.test(duration) &&
      /^\d{4}$/.test(godinaProizvodnje) &&
      godinaProizvodnje > 1900 &&
      godinaProizvodnje < currentYear + 1 &&
      (() => {
        const timeParts = duration.split(":").map(Number);
        return (
          timeParts.length === 3 &&
          timeParts[0] >= 0 &&
          timeParts[0] <= 23 && // Provjera za sate
          timeParts[1] >= 0 &&
          timeParts[1] <= 59 && // Provjera za minute
          timeParts[2] >= 0 &&
          timeParts[2] <= 59 && // Provjera za sekunde
          !(timeParts[0] === 0 && timeParts[1] === 0 && timeParts[2] === 0) // Onemogućuje 00:00:00
        );
      })()
    ) {
      setIsValid(true);
    } else {
      setIsValid(false);
    }
  }, [
    originalniNaslov,
    jezikOriginala,
    ton,
    porijekloZemljaProizvodnje,
    godinaProizvodnje,
    duration,
  ]);
  const handleSubmitFilm = async () => {
    try {
      const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;

      // Priprema podataka za slanje na server
      const newFilm = {
        originalniNaslov,
        jezikOriginala,
        ton,
        porijekloZemljaProizvodnje,
        godinaProizvodnje,
        duration,
      };

      const response = await axios.post(
        `${BACKEND_API_URL}/api/filmskaTraka/add`,
        newFilm,
        {
          headers: { "Content-Type": "application/json" },
        }
      );

      console.log("Film successfully added:", response.data);

      const updatedBarcodes = [
        ...scannedBarcodes,
        {
          barcode: "-",
          filmTitle: originalniNaslov,
          duration: response.data.duration || duration,
          database: false,
        },
      ];

      setScannedBarcodes(updatedBarcodes);

      setDisplayForm(false);
    } catch (error) {
      console.error("Error adding film:", error);

      setError("Failed to add film. Please try again.");
      setTimeout(() => {
        setError(null);
      }, 7000);
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
                  {/*<button className="button-film" onClick={handleFetchFilmData}>
                    Fetch
                  </button>*/}
                  <button onClick={handleBarcodesClick}>List</button>
                  {displayInputFile && (
                    <div>
                      <input
                        type="file"
                        accept=".xml"
                        onChange={handleXmlFileChange}
                      />
                      <p>XML file</p>
                    </div>
                  )}
                  <button onClick={handleManualEntry}>Manual input</button>
                </div>
              </div>
            </div>
          </div>
          {!displayForm && (
            <div className="brc-list-container">
              <div className="scanned-title">Scanned barcodes</div>
              <div className="scan-br-list">
                <ul>
                  {scannedBarcodes.map((barcode, index) => (
                    <li key={index}>
                      {barcode.barcode} - {barcode.filmTitle} (
                      {barcode.duration})
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          )}

          {displayForm && (
            <form onSubmit={handleSubmitFilm}>
              <div className="manual-input">
                <label>
                  Original title:
                  <input
                    className="label-input"
                    type="text"
                    value={originalniNaslov}
                    onChange={(e) => setOriginalniNaslov(e.target.value)}
                  />
                </label>
                <label>
                  Original language:
                  <input
                    className="label-input"
                    type="text"
                    value={jezikOriginala}
                    onChange={(e) => setJezikOriginala(e.target.value)}
                  />
                </label>
                <label>
                  Sound:
                  <select
                    className="label-input"
                    value={ton}
                    onChange={(e) => setTon(e.target.value)}
                  >
                    <option value="DA">Yes</option>
                    <option value="NE">No</option>
                  </select>
                </label>
                <label>
                  Country of origin:
                  <input
                    className="label-input"
                    type="text"
                    value={porijekloZemljaProizvodnje}
                    onChange={(e) =>
                      setPorijekloZemljaProizvodnje(e.target.value)
                    }
                  />
                </label>
                <label>
                  Manufacturing year:
                  <input
                    className="label-input"
                    type="number"
                    value={godinaProizvodnje}
                    onChange={(e) => {
                      const year = e.target.value;
                      if (year.length === 4) {
                        const numericYear = parseInt(year, 10);
                        const currentYear = new Date().getFullYear();

                        if (numericYear < 1900 || numericYear > currentYear) {
                          alert(
                            `Godina mora biti između 1900. i ${currentYear}.`
                          );
                        } else {
                          setGodinaProizvodnje(year);
                        }
                      } else {
                        setGodinaProizvodnje(year);
                      }
                    }}
                    placeholder={`1900-${new Date().getFullYear()}`}
                  />
                </label>
                <label>
                  Duration:
                  <input
                    className="label-input"
                    type="text"
                    value={duration}
                    onChange={(e) => {
                      let inputValue = e.target.value.replace(/[^0-9]/g, "");
                      if (inputValue.length > 2) {
                        inputValue = inputValue.replace(
                          /(\d{2})(?=\d)/g,
                          "$1:"
                        );
                      }
                      if (inputValue.length > 8) {
                        inputValue = inputValue.slice(0, 8);
                      }
                      const timeParts = inputValue.split(":").map(Number);
                      if (
                        timeParts.length === 3 &&
                        (timeParts[0] > 23 ||
                          timeParts[0] < 0 || // Provjera raspona za sate
                          timeParts[1] > 59 ||
                          timeParts[1] < 0 || // Provjera raspona za minute
                          timeParts[2] > 59 ||
                          timeParts[2] < 0) // Provjera za nule
                      ) {
                        alert(
                          "Neispravno trajanje. Vrijeme mora biti u formatu hh:mm:ss s valjanim vrijednostima."
                        );
                        return;
                      }

                      setDuration(inputValue);
                    }}
                    placeholder="Format: hh:mm:ss"
                  />
                </label>
                <button
                  style={{
                    borderRadius: "0.5rem",
                    width: "30%",
                    alignSelf: "center",
                    marginTop: "2rem",
                  }}
                  onClick={(e) => {
                    e.preventDefault();
                    if (isValid) {
                      handleSubmitFilm();
                    }
                  }}
                  disabled={!isValid}
                >
                  Add
                </button>
              </div>
            </form>
          )}
        </div>
      </div>
    </Layout>
  );
};

export default BarcodeScanner;
