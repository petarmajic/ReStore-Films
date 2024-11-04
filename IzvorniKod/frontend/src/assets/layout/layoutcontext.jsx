import { createContext, useState } from "react";

const LayoutContext = createContext();

function LayoutProvider({ children }) {
  const [darkMode, setDarkMode] = useState(false);

  const handleDarkMode = () => {
    setDarkMode(!darkMode);
  };

  return (
    <LayoutContext.Provider value={{ darkMode, handleDarkMode }}>
      {children}
    </LayoutContext.Provider>
  );
}

export { LayoutContext, LayoutProvider };
