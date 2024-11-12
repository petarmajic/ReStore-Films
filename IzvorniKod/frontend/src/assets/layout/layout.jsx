import React from "react";
import Navigation from "../navigation_bar/navigation";
import Footer from "../footer/footer";

function Layout({ children }) {
  return (
    <div className="layout">
      <Navigation />
      <div>{children}</div>
      <Footer />
    </div>
  );
}

export default Layout;
