import pozadina from "../images/filmskaVrpca.jpg";
import { ReturnButton } from "../../ReturnButton.jsx";
import "./unauthorized.css";

const Unauthorized = () => {
  return (
    <div className="main-div">
      <img
        className="unauth-bg-image"
        src={pozadina}
        alt="background picture"
      ></img>
      <div className="sub-div">
        <h1 clasName="unauth-title">Unauthorized</h1>
        <p className="unauth-para">
          You do not have permission to access this page.
        </p>
        <div className="back-to-login">
          <ReturnButton />
        </div>
      </div>
    </div>
  );
};

export default Unauthorized;
