import { createContext, useState } from "react";

const LayoutContext = createContext();

function LayoutProvider({ children }) {
  const [scannedBarcodes, setScannedBarcodes] = useState([]);
  const [korisnikUloga, setKorisnikUloga] = useState(null);

  return (
    <LayoutContext.Provider
      value={{
        scannedBarcodes,
        setScannedBarcodes,
        korisnikUloga,
        setKorisnikUloga,
      }}
    >
      {children}
    </LayoutContext.Provider>
  );
}

export { LayoutContext, LayoutProvider };
