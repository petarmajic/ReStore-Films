import React from "react";
import { useMsal } from "@azure/msal-react";
import Button from "react-bootstrap/Button";

/**
 * Renders a sign out button
 */
export const SignOutButton = () => {
  const { instance } = useMsal();

  const handleLogout = () => {
      instance.logoutRedirect({
        postLogoutRedirectUri: "/",
      });
    }

  return (
    <Button as="button" onClick={() => handleLogout("redirect")}
      variant="secondary"
      className="ml-auto">
     Sign out
    </Button>
  );
};
