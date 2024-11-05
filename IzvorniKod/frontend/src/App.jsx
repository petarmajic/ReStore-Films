import React, { useState } from "react";
import LoginSignUp from "./assets/login/login";
import { LayoutProvider } from "./assets/layout/layoutcontext";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./assets/login/login";
import Home from "./assets/home/home";
import { useIsAuthenticated } from "@azure/msal-react";

function App() {
  const isAuthenticated = useIsAuthenticated();

  return (
    <LayoutProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          {isAuthenticated && <Route path="/home" element={<Home />} />}
        </Routes>
      </Router>
    </LayoutProvider>
  );
}

export default App;
