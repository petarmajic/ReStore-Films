import React from "react";
import "./footer.css";

function Footer() {
  const currentYear = new Date().getFullYear();
  return (
    <footer class="f-footer">
      <div>&copy; {currentYear} ReStore Films. All rights reserved.</div>
    </footer>
  );
}
export default Footer;
