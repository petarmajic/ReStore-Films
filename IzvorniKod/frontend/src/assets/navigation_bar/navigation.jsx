import "./navigation.css";
import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";

import { LayoutContext } from "../layout/layoutcontext";
import { useState } from "react";
import Navbar from "react-bootstrap/Navbar";

import { useIsAuthenticated } from "@azure/msal-react";
import { SignInButton } from "../../SignInButton";
import { SignOutButton } from "../../SignOutButton";
import Button from "react-bootstrap/esm/Button";

const Navigation = () => {
  const { darkMode, handleDarkMode } = useContext(LayoutContext);
  const isAuthenticated = useIsAuthenticated();
  const navigate = useNavigate();

  const handleHomeClick = () => {
    {
      isAuthenticated ? navigate("/home") : navigate("/");
    }
  };
  const handleScannerClick = () => {
    navigate("/scanner");
  };
  const handleBarcodesClick = () => {
    navigate("/barcodes");
  };

  return (
    <nav className={`navbar ${darkMode ? "dark-mode" : ""}`}>
      <ul style={{ display: "flex", justifyContent: "space-between" }}>
        <li style={{ display: "flex", alignItems: "center" }}>
          <button onClick={handleHomeClick} style={{ marginRight: "10px" }}>
            Home
          </button>
        </li>
        <li style={{ display: "flex", alignItems: "center" }}>
          <button onClick={handleScannerClick} style={{ marginRight: "10px" }}>
            Scanner
          </button>
          <button onClick={handleBarcodesClick}>Barcodes</button>
        </li>
        <li style={{ display: "flex", alignItems: "center" }}>
          <button onClick={handleDarkMode} style={{ marginRight: "10px" }}>
            {darkMode ? "Light Mode" : "Dark Mode"}
          </button>
          <div>{isAuthenticated ? <SignOutButton /> : <SignInButton />}</div>
        </li>
      </ul>
    </nav>
  );
};

export default Navigation;

/*
import React from "react";
import { useState } from "react";
import Navbar from "react-bootstrap/Navbar";

import { useIsAuthenticated } from "@azure/msal-react";
import { SignInButton } from "../../SignInButton";
import { SignOutButton } from "../../SignOutButton";
import Button from "react-bootstrap/esm/Button";

export const PageLayout = (props) => {
  const isAuthenticated = useIsAuthenticated();
  const [ShowHome, setShowHome] = useState(false);
  return (
    <div>
      <Navbar bg="primary" variant="dark" className="navbarStyle">
        <a className="navbar-brand" href="/">
          Microsoft Identity Platform
        </a>
        <div className="collapse navbar-collapse justify-content-end">
          {isAuthenticated ? <SignOutButton /> : <SignInButton />}
        </div>
      </Navbar>
    </div>
  );
};
*/

/*
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
*/
