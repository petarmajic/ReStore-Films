import React from "react";
import { useMsal } from "@azure/msal-react";
import { loginRequest } from "./authConfig";
import Button from "react-bootstrap/Button";
import "./SignInButton.css";

/**
 * Renders a drop down button with child buttons for logging in with a popup or redirect
 * Note the [useMsal] package
 */

export const SignInButton = () => {
  const { instance } = useMsal();

  const handleLogin = (loginType) => {
      instance.loginRedirect(loginRequest).catch((e) => {
        console.log(e);
      });
    }
  return (
    <Button  as="button" onClick={() => handleLogin("redirect")}
      variant="secondary"
      className="ml-auto">
      SIGN IN
    </Button>
  );
};
