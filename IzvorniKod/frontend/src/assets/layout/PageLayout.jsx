/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

import React from "react";
import { useState } from "react";
import Navbar from "react-bootstrap/Navbar";

import { useIsAuthenticated } from "@azure/msal-react";
import { SignInButton } from "../../SignInButton";
import { SignOutButton } from "../../SignOutButton";
import Button from "react-bootstrap/esm/Button";

import Login from "../login/login.jsx";

/**
 * Renders the navbar component with a sign in or sign out button depending on whether or not a user is authenticated
 * @param props
 */

export const PageLayout = (props) => {
  const isAuthenticated = useIsAuthenticated();
  const [ShowHome, setShowHome] = useState(false);

  const handleHomeButtonClick = () => {
    setShowHome(true);
  };
  return (
    <div>
      {!ShowHome ? (
        <>
          <Navbar bg="primary" variant="dark" className="navbarStyle">
            <a className="navbar-brand" href="/">
              Microsoft Identity Platform
            </a>
            <div className="collapse navbar-collapse justify-content-end">
              {isAuthenticated ? <SignOutButton /> : <SignInButton />}
            </div>
          </Navbar>
          <br />
          <Button onClick={handleHomeButtonClick}>Home</Button>
          <br />
          <h5>
            <center>
              Welcome to the Microsoft Authentication Library For JavaScript -
              React SPA
            </center>
          </h5>
          <br />
          <br />
          {props.children}
        </>
      ) : (
        <Login />
      )}
    </div>
  );
};
