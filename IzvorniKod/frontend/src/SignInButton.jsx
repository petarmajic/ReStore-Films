import React from "react";
import { useMsal } from "@azure/msal-react";
import { loginRequest } from "./authConfig";
import Button from "react-bootstrap/Button";
import "./SignInButton.css";

/**
 * Renders a button for logging in with redirect
 * Note the [useMsal] package
 */

export const SignInButton = () => {
  const { instance } = useMsal();

  const handleLogin = () => {
      instance.loginRedirect(loginRequest).catch((e) => {
        console.log(e);
      });
    }
  return (
    <Button  as="button" onClick={() => handleLogin("redirect")}
      variant="secondary"
      className="sign-in">
      SIGN IN
    </Button>
  );
};
