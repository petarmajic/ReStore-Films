import "./navigation.css";
import React, { useContext, useEffect, useState } from "react";
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
  const { korisnik, setKorisnik } = useContext(LayoutContext);

  const handleHomeClick = () => {
    {
      isAuthenticated ? navigate("/home") : navigate("/");
    }
  };
  useEffect(() => {
    const fetchKorisnik = async () => {
      try {
        const response = await axios.get(
          `${BACKEND_API_URL}/api/korisnik/${userEmail}`
        );
        setKorisnikUloga(response.data.uloga);
        setKorisnik(response.data);
      } catch (error) {
        console.error("Greška pri dohvatu korisnika:", error.message);
      }
    };
    fetchKorisnik();
  }, [isAuthenticated]);
  useEffect(() => {
    console.log(
      `Korisnik: ${userEmail} postoji, uloga navigation.jsx: ${korisnikUloga}`
    );
  }, [korisnikUloga]);

  return (
    <nav className="navbar">
      <div className="nav-homebtn">
        <button onClick={handleHomeClick}>Home</button>
        {/*<div className="role-css">Role: {korisnikUloga}</div>*/}
      </div>
      <div className="nav-title">ReStore-Films</div>
      <div className="nav-signout">
        {isAuthenticated ? (
          <div className="user-info">
            <button
              className="username-button"
              onMouseOver={(e) => {
                e.target.classList.add("hover");
              }}
              onMouseOut={(e) => {
                e.target.classList.remove("hover");
              }}
            >
              {korisnik ? (
                `${korisnik.ime} ${korisnik.prezime.charAt(0)}.`
              ) : (
                <span>...</span>
              )}
              <span className="username-tooltip">
                {korisnik ? (
                  <>
                    {korisnik.ime} {korisnik.prezime}
                    <br />
                    {korisnik.email}
                    <br />
                    {korisnikUloga}
                    <br />
                    <SignOutButton />
                  </>
                ) : (
                  <span>Loading...</span>
                )}
              </span>
            </button>
          </div>
        ) : (
          <SignInButton />
        )}
      </div>
    </nav>
  );
};

export default Navigation;
