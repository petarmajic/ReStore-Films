import { useRef, useState, useContext, useEffect } from "react";
import "./login.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";
import { useNavigate } from "react-router-dom";

import { useIsAuthenticated } from "@azure/msal-react";
import { SignInButton } from "../../SignInButton";
import { SignOutButton } from "../../SignOutButton";

function LoginSignUp() {
  const { darkMode, handleDarkMode } = useContext(LayoutContext);
  const isAuthenticated = useIsAuthenticated();
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
        navigate("/home");
    }
}, [isAuthenticated, navigate]);

  return (
          <div className={`center-container ${darkMode ? "dark-mode" : ""}`}>
            <div className="button-container">
              <div>{isAuthenticated ? <SignOutButton /> : <SignInButton />}</div>
            </div>
          </div>
  );
}

export default LoginSignUp;
