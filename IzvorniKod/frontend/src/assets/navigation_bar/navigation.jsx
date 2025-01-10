import "./navigation.css";
import React, { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useIsAuthenticated } from "@azure/msal-react";
import { SignInButton } from "../../SignInButton";
import { SignOutButton } from "../../SignOutButton";
import { LayoutContext } from "../layout/layoutcontext";
import { useMsal } from "@azure/msal-react";
import axios from "axios";

const Navigation = () => {
  const isAuthenticated = useIsAuthenticated();
  const navigate = useNavigate();
  const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;
  const { instance, accounts } = useMsal();
  const account = accounts[0];
  let userEmail = account?.username ?? null;
  const { korisnikUloga, setKorisnikUloga } = useContext(LayoutContext);

  const handleHomeClick = () => {
    {
      isAuthenticated ? navigate("/home") : navigate("/");
    }
  };
  useEffect(() => {
    if (isAuthenticated) {
      const fetchKorisnik = async () => {
        try {
          const response = await axios.get(
            `${BACKEND_API_URL}/api/korisnik/${userEmail}`
          );
          setKorisnikUloga(response.data.uloga);
          console.log(
            `Korisnik: ${userEmail} postoji, uloga: ${korisnikUloga}`
          );
        } catch (error) {
          console.error("Greška pri dohvatu korisnika:", error.response.data);
        }
      };
      fetchKorisnik();
    }
  }, []);

  return (
    <nav className="navbar">
      <div className="nav-homebtn">
        <button onClick={handleHomeClick}>Home</button>
        {/*<div className="role-css">Role: {korisnikUloga}</div>*/}
      </div>
      <div className="nav-title">ReStore-Films</div>
      <div className="nav-signout">
        {isAuthenticated ? <SignOutButton /> : <SignInButton />}
      </div>
    </nav>
  );
};

export default Navigation;
