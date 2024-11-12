import pozadina from "../images/filmskaVrpca.jpg";
import "./unauthorized.css";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";

const Unauthorized = () => {
  const navigate = useNavigate();

  const handleReturnClick = () => {
    navigate("/");
  };

  return (
    <div className="main-div">
      <img
        className="unauth-bg-image"
        src={pozadina}
        alt="background picture"
      ></img>
      <div className="sub-div">
        <h1 className="unauth-title">Unauthorized</h1>
        <p className="unauth-para">
          You do not have permission to access this page.
        </p>
        <div className="back-to-login">
          <Button
            as="button"
            onClick={handleReturnClick}
            className="return-button"
          >
            Return
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Unauthorized;
