import "./navigation.css";
import React, { useContext } from "react";
import { LayoutContext } from "../layout/layoutcontext";

const Navbar = () => {
  const { darkMode, handleDarkMode } = useContext(LayoutContext);

  const handleLogOut = () => {
    // Add log out functionality here
  };

  return (
    <nav className={`navbar ${darkMode ? "dark-mode" : ""}`}>
      <ul>
        <li>
          <button>
            <a href="#">Home</a>
          </button>
        </li>
        <li>
          <button onClick={handleLogOut}>Log Out</button>
        </li>
        <li>
          <button onClick={handleDarkMode}>
            {darkMode ? "Light Mode" : "Dark Mode"}
          </button>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
