import React from "react";
import LoginSignUp from "./assets/login/login";
import { LayoutProvider } from "./assets/layout/layoutcontext";

function App() {
  return (
    <LayoutProvider>
      <LoginSignUp />
    </LayoutProvider>
  );
}

export default App;
