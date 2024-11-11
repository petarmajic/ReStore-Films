import React, { useContext } from "react";
import navigation from "../navigation_bar/navigation";
import { LayoutContext } from "../layout/layoutcontext";
import Navigation from "../navigation_bar/navigation";
import Footer from "../footer/footer";


function Layout({ children }) {
  const { darkMode } = useContext(LayoutContext);

  return (
    <div
      className={`layout ${darkMode ? "dark-mode" : ""}`}
      style={{
        width: "100vw",
        height: "93vh",
      }}
    >
      <Navigation />
      <div>{children}</div>
      <Footer />
    </div>
  );
}

export default Layout;
