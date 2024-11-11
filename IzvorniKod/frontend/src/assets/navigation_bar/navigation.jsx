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
        <li style={{ display: "flex", alignItems: "center", color: "#D4A373", fontSize: "1.5rem"}}>
          ReStore-Films
        </li>
        <li style={{ display: "flex", alignItems: "center" }}>
          <div>{isAuthenticated ? <SignOutButton /> : <SignInButton />}</div>
        </li>
      </ul>
    </nav>
  );
};

export default Navigation;

