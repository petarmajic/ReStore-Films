import React from "react";
import { useMsal } from "@azure/msal-react";
import { loginRequest } from "./authConfig";
import Button from "react-bootstrap/Button";

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
  };
  return (
    <Button
      as="button"
      onClick={() => handleLogin("redirect")}
      variant="secondary"
      style={{
        width: "100%",
        height: "100%",
      }}
    >
      Sign in
    </Button>
  );
};
