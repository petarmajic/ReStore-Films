import "./navigation.css";
import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { useIsAuthenticated } from "@azure/msal-react";
import { SignInButton } from "../../SignInButton";
import { SignOutButton } from "../../SignOutButton";

const Navigation = () => {
  const isAuthenticated = useIsAuthenticated();
  const navigate = useNavigate();

  const handleHomeClick = () => {
    {
      isAuthenticated ? navigate("/home") : navigate("/");
    }
  };

  return (
    <nav className="navbar">
      <div className="nav-homebtn">
        <button onClick={handleHomeClick}>Home</button>
      </div>
      <div className="nav-title">ReStore-Films</div>
      <div className="nav-signout">
        {isAuthenticated ? <SignOutButton /> : <SignInButton />}
      </div>
    </nav>
  );
};

export default Navigation;
