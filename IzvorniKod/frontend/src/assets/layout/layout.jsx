import React, { useContext } from "react";
import Navbar from "../navigation_bar/navigation";
import { LayoutContext } from "../layout/layoutcontext";

function Layout({ children }) {
  const { darkMode } = useContext(LayoutContext);

  return (
    <div className={`layout ${darkMode ? "dark-mode" : ""}`}>
      <div className="container">{children}</div>
      <Navbar />
    </div>
  );
}

export default Layout;
