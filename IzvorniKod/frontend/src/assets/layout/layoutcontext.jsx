import { createContext, useState } from "react";

const LayoutContext = createContext();

function LayoutProvider({ children }) {
  const [scannedBarcodes, setScannedBarcodes] = useState([]);
  const [korisnikUloga, setKorisnikUloga] = useState(null);
  const [korisnik, setKorisnik] = useState(null);

  return (
    <LayoutContext.Provider
      value={{
        scannedBarcodes,
        setScannedBarcodes,
        korisnikUloga,
        setKorisnikUloga,
        korisnik,
        setKorisnik,
      }}
    >
      {children}
    </LayoutContext.Provider>
  );
}

export { LayoutContext, LayoutProvider };
