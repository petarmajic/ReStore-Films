import React, { useState } from "react";
import { LayoutProvider } from "./assets/layout/layoutcontext";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./assets/login/login";
import Home from "./assets/home/home";
import { useIsAuthenticated } from "@azure/msal-react";
import { Navigate } from "react-router-dom";
import Unauthorized from "./assets/unauthorized/unauthorized";
function App() {
  const isAuthenticated = useIsAuthenticated();

  return (
    <LayoutProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          {isAuthenticated ? (
            <>
              <Route path="/home" element={<Home />} />
              <Route path="*" element={<Navigate to="/home" replace />} />
            </>
          ) : (
            <Route path="*" element={<Unauthorized />} />
          )}
        </Routes>
      </Router>
    </LayoutProvider>
  );
}

export default App;
