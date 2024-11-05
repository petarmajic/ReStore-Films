import React from "react";
import { useRef, useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./home.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";

import { callMsGraph } from "../../graph";
import { ProfileData } from "../../ProfileData";

import {
  AuthenticatedTemplate,
  UnauthenticatedTemplate,
  useMsal,
} from "@azure/msal-react";

import "../../App.css";

import Button from "react-bootstrap/Button";

export default function Microsoft() {
  const { instance, accounts } = useMsal();
  const [graphData, setGraphData] = useState(null);
  const { darkMode, handleDarkMode } = useContext(LayoutContext);

  return (
    <>
      <Layout>
        <div className={`center-container ${darkMode ? "dark-mode" : ""}`}>
          <h5 className="card-title">Welcome {accounts[0].name}</h5>
          <br />
          <div>Ime: {accounts[0].name}</div>
          <div>Email: {accounts[0].username}</div>
        </div>
      </Layout>
    </>
  );
}
