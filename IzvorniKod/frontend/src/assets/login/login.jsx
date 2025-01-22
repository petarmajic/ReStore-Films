import { useRef, useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useIsAuthenticated } from "@azure/msal-react";
import { SignInButton } from "../../SignInButton";
import { SignOutButton } from "../../SignOutButton";
import { useMsal } from "@azure/msal-react";
import axios from "axios";
import "./login.css";
import pozadina from "../images/filmskaVrpca.jpg";
import { LayoutContext } from "../layout/layoutcontext";

const LoginSignUp = () => {
  const {
    scannedBarcodes,
    setScannedBarcodes,
    korisnikUloga,
    setKorisnikUloga,
  } = useContext(LayoutContext);
  const currentYear = new Date().getFullYear();
  const [error, setError] = useState(null);
  const isAuthenticated = useIsAuthenticated();
  const navigate = useNavigate();
  const { instance, accounts } = useMsal();
  const BACKEND_API_URL = import.meta.env.VITE_BACKEND_API_URL;

  useEffect(() => {
    if (accounts.length > 0) {
      const account = accounts[0];
      let userName = account?.name ?? null;
      let userEmail = account?.username ?? null;
      let userId = userEmail.split("@")[0];
      const [name, surname] = userName.split(" ");

      const checkAndAddUser = async () => {
        if (!isAuthenticated || !userEmail) return;

        try {
          setError("Getting user role.");
          const response = await axios.get(
            `${BACKEND_API_URL}/api/korisnik/${userEmail}`
          );
          setKorisnikUloga("DJELATNIK");
          setKorisnikUloga("ADMINISTRATOR");
          console.log("Korisnik postoji:", response.data);
          console.log("ID:", userId);
          console.log("uloga:", korisnikUloga);
          setTimeout(() => {
            navigate("/home");
          }, 2000);
        } catch (error) {
          if (error.response && error.response.status === 404) {
            setError("New user detected.");
            try {
              const newUser = {
                ime: name,
                prezime: surname,
                email: userEmail,
              };

              try {
                const postResponse = await axios.post(
                  `${BACKEND_API_URL}/api/korisnik/add`,
                  newUser,
                  {
                    headers: {
                      "Content-Type": "application/json",
                    },
                  }
                );
                console.log("Korisnik dodan:", postResponse.data);
                setError("Getting user role.");
                setTimeout(() => {
                  navigate("/home");
                }, 2000);
              } catch (error) {
                setError("Error adding new user.");
                console.error(
                  "Greška pri dodavanju korisnika:",
                  error.response.data
                );
              }
            } catch (postError) {
              setError("Error adding new user.");
              console.error("Greška pri dodavanju korisnika:", postError);
            }
          } else {
            setError("Backend server offline.");
            console.error("Greška pri provjeri korisnika:", error);
          }
        }
      };
      checkAndAddUser();
    }
  }, [isAuthenticated, navigate, accounts]);

  return (
    <div className="container">
      <img
        className="background-image"
        src={pozadina}
        alt="background picture"
      ></img>
      <div className="button-container">
        <div>
          <h1 className="title">ReStore Films</h1>
          <h2 className="subtitle">
            Relive the past through digital techonolgy
          </h2>
        </div>
        <div className="sign-in-wrap">
          {isAuthenticated ? <SignOutButton /> : <SignInButton />}
        </div>

        <footer class="app-footer">
          <div>{error && <p style={{ color: "red" }}>{error}</p>}</div>
          &copy; {currentYear} ReStore Films. All rights reserved.
        </footer>
      </div>
    </div>
  );
};

export default LoginSignUp;
