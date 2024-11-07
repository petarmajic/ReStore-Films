import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useIsAuthenticated } from "@azure/msal-react";
import { SignInButton } from "../../SignInButton";
import { SignOutButton } from "../../SignOutButton";

import "./login.css";
import "../../SignInButton.css";
import pozadina from "../images/filmskaVrpca.jpg";

function LoginSignUp() {
  const isAuthenticated = useIsAuthenticated();
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/home");
    }
  }, [isAuthenticated, navigate]);

  return (
    <div className="center-container">
      <img
        className="background-image"
        src={pozadina}
        alt="background picture"
      ></img>
      <div className="button-container">
        <div>
          <h1 className="title">ReStore Films</h1>
          <h2 className="subtitle">
            Oživite prošlost kroz digitalnu tehnologiju
          </h2>
        </div>
        <div className="sign-in-wrap">
          {isAuthenticated ? <SignOutButton /> : <SignInButton />}
        </div>
        <footer class="app-footer">
          &copy; 2024 ReStore Films. All rights reserved.
        </footer>
      </div>
    </div>
  );
}

export default LoginSignUp;
