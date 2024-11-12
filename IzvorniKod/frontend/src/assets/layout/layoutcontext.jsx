import { createContext, useState } from "react";

const LayoutContext = createContext();

function LayoutProvider({ children }) {
  const [scannedBarcodes, setScannedBarcodes] = useState([]);

  return (
    <LayoutContext.Provider
      value={{scannedBarcodes, setScannedBarcodes }}
    >
      {children}
    </LayoutContext.Provider>
  );
}

export { LayoutContext, LayoutProvider };
