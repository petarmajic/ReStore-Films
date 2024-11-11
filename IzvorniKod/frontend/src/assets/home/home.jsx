import React from "react";
import { useRef, useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./home.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";
import pozadina from "../images/filmskaVrpca.jpg";

import { callMsGraph } from "../../graph";
import { ProfileData } from "../../ProfileData";

import {
  AuthenticatedTemplate,
  UnauthenticatedTemplate,
  useMsal,
} from "@azure/msal-react";

import Button from "react-bootstrap/Button";

export default function Microsoft() {
  const { instance, accounts } = useMsal();
  const [graphData, setGraphData] = useState(null);
  const { darkMode, handleDarkMode } = useContext(LayoutContext);
  const navigate = useNavigate();

  const handleScannerClick = () => {
    navigate("/scanner");
  };
  const handleBarcodesClick = () => {
    navigate("/barcodes");
  };

  return (
    <>
      <Layout>
        <div className={`home-container ${darkMode ? "dark-mode" : ""}` }>
            <img
        className="home-bg-image"
        src={pozadina}
        alt="background picture"
            ></img>
            <div className="home-wrap">
                <h5 className="card-title">Welcome {accounts[0].name}</h5>
                <br />
                <div>Ime: {accounts[0].name}</div>
                <div>Email: {accounts[0].username}</div>
                <button onClick={handleScannerClick} style={{ marginRight: "10px" }}>
                    Scanner
                </button>
                <button onClick={handleBarcodesClick}>Barcodes</button>
                </div>
        </div>
      </Layout>
    </>
  );
}
