import { createContext, useState } from "react";

const LayoutContext = createContext();

function LayoutProvider({ children }) {
  const [darkMode, setDarkMode] = useState(true);
  const [scannedBarcodes, setScannedBarcodes] = useState([]);

  const handleDarkMode = () => {
    setDarkMode(!darkMode);
  };

  return (
    <LayoutContext.Provider
      value={{ darkMode, handleDarkMode, scannedBarcodes, setScannedBarcodes }}
    >
      {children}
    </LayoutContext.Provider>
  );
}

export { LayoutContext, LayoutProvider };
