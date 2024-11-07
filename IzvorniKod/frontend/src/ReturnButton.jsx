import React from "react";
import Button from "react-bootstrap/Button";
import { useNavigate} from "react-router-dom";
import "./ReturnButton.css";

export const ReturnButton = () => {
    let navigate = useNavigate();
    return (
        <Button as="button" onClick={() => navigate(-1)} className="return-button">
          Return
        </Button>
    );
};