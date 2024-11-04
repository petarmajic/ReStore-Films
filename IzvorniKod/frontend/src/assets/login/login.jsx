import { useRef, useState, useContext, useEffect } from "react";
import "./login.css";
import Layout from "../layout/layout";
import { LayoutContext } from "../layout/layoutcontext";

function LoginSignUp() {
  const errRef = useRef();
  const { darkMode, handleDarkMode } = useContext(LayoutContext);

  const [activeForm, setActiveForm] = useState(null);
  const [role, setRole] = useState("");
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [passwordMatch, setPasswordMatch] = useState(false);
  const [emailValid, setEmailValid] = useState(false);

  const [errMsg, setErrMsg] = useState("");

  const nameRegex = /^[A-z][A-z0-9-_]{2,23}$/;
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const pswdRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{6,24}$/;

  const handleButtonClick = (formType) => {
    setActiveForm(formType);
  };

  const handleRoleChange = (event) => {
    setRole(event.target.value);
  };

  const handleNameChange = (event) => {
    setName(event.target.value);
  };
  const handleSurnameChange = (event) => {
    setSurname(event.target.value);
  };

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };
  const handleEmailChange = (event) => {
    setEmail(event.target.value);

    if (emailRegex.test(event.target.value)) {
      setEmailValid(true);
    } else {
      setEmailValid(false);
    }
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
    if (
      event.target.value === confirmPassword &&
      confirmPassword !== "" &&
      pswdRegex.test(event.target.value)
    ) {
      setPasswordMatch(true);
    } else {
      setPasswordMatch(false);
    }
  };

  const handleConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
    if (event.target.value === password && password !== "") {
      setPasswordMatch(true);
    } else {
      setPasswordMatch(false);
    }
  };

  const handleSignup = async (e) => {
    e.preventDefault();
    // if button enabled with JS hack
    const v1 = nameRegex.test(username);
    const v2 = pswdRegex.test(password);
    const v3 = emailRegex.test(email);
    if (!v1 || !v2 || !v3) {
      setErrMsg("Invalid Entry");
      return;
    }
    try {
      const response = await axios.post(
        REGISTER_URL,
        JSON.stringify({ role, name, surname, username, email, password }),
        {
          headers: { "Content-Type": "application/json" },
          withCredentials: true,
        }
      );
      console.log(response?.data);
      console.log(response?.accessToken);
      console.log(JSON.stringify(response));
      setSuccess(true);
      //clear state and controlled inputs
      //need value attrib on inputs for this
      setUser("");
      setPwd("");
      setMatchPwd("");
    } catch (err) {
      if (!err?.response) {
        setErrMsg("No Server Response");
      } else if (err.response?.status === 409) {
        setErrMsg("Username Taken");
      } else {
        setErrMsg("Signup Failed");
      }
      errRef.current.focus();
    }
  };
  const handleLogin = async (e) => {
    e.preventDefault();
    // if button enabled with JS hack
    const v2 = pswdRegex.test(password);
    const v3 = emailRegex.test(email);
    if (!v2 || !v3) {
      setErrMsg("Invalid Entry");
      return;
    }
    try {
      const response = await axios.post(
        REGISTER_URL,
        JSON.stringify({ role, email, password }),
        {
          headers: { "Content-Type": "application/json" },
          withCredentials: true,
        }
      );
      console.log(response?.data);
      console.log(response?.accessToken);
      console.log(JSON.stringify(response));
      setSuccess(true);
      //clear state and controlled inputs
      //need value attrib on inputs for this
      setUser("");
      setPwd("");
      setMatchPwd("");
    } catch (err) {
      if (!err?.response) {
        setErrMsg("No Server Response");
      } else if (err.response?.status === 409) {
        setErrMsg("Username Taken");
      } else {
        setErrMsg("Login Failed");
      }
      errRef.current.focus();
    }
  };

  return (
    <Layout>
      <div className={`center-container ${darkMode ? "dark-mode" : ""}`}>
        <div className="mode-switch-container">
          <button className="mode-switch-button" onClick={handleDarkMode}>
            {darkMode ? "Light Mode" : "Dark Mode"}
          </button>
        </div>
        <div className="button-container">
          <button
            className={`button ${
              activeForm === "login" ? "active-button" : ""
            }`}
            onClick={() => handleButtonClick("login")}
          >
            Login
          </button>
          <button
            className={`button ${
              activeForm === "signup" ? "active-button" : ""
            }`}
            onClick={() => handleButtonClick("signup")}
          >
            Sign Up
          </button>
        </div>

        {activeForm === "login" ? (
          <div className="form-container show">
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
              <div className="input-container">
                <select
                  className="input-field"
                  value={role}
                  onChange={handleRoleChange}
                >
                  <option value="">Select Role</option>
                  <option value="administrator">Administrator</option>
                  <option value="voditelj">Voditelj</option>
                  <option value="pregledavac">Pregledavac</option>
                  <option value="djelatnik">Djelatnik</option>
                </select>
                <span className="validation-message">
                  {role === "" ? (
                    <span style={{ color: "red" }}>❌</span>
                  ) : (
                    <span style={{ color: "green" }}>✅</span>
                  )}
                </span>
              </div>
              <div className="input-container">
                <input
                  type="email"
                  placeholder="Email"
                  className="input-field"
                  value={email}
                  onChange={handleEmailChange}
                />
                <span className="validation-message">
                  {emailValid ? (
                    <span style={{ color: "green" }}>✅</span>
                  ) : (
                    <span style={{ color: "red" }}>❌</span>
                  )}
                </span>
              </div>
              <div className="input-container">
                <input
                  type="password"
                  placeholder="Password"
                  className="input-field"
                  value={password}
                  onChange={handlePasswordChange}
                />
                <span className="validation-message">
                  {!pswdRegex.test(password) ? (
                    <span style={{ color: "red" }}>❌</span>
                  ) : (
                    <span style={{ color: "green" }}>✅</span>
                  )}
                </span>
              </div>
              <button
                className="submit-button"
                disabled={role === "" || !emailValid || password === ""}
              >
                Login
              </button>
              <p
                ref={errRef}
                className={errMsg ? "errmsg" : "offscreen"}
                aria-live="assertive"
                style={{ color: "red" }}
              >
                {errMsg}
              </p>
            </form>
          </div>
        ) : //
        //signup
        //
        activeForm === "signup" ? (
          <div className="form-container show">
            <form onSubmit={handleSignup}>
              <div className="input-container">
                <select
                  className="input-field"
                  value={role}
                  onChange={handleRoleChange}
                >
                  <option value="">Select Role</option>
                  <option value="administrator">Administrator</option>
                  <option value="voditelj">Voditelj</option>
                  <option value="pregledavac">Pregledavac</option>
                  <option value="djelatnik">Djelatnik</option>
                </select>
                <span className="validation-message">
                  {role === "" ? (
                    <span style={{ color: "red" }}>❌</span>
                  ) : (
                    <span style={{ color: "green" }}>✅</span>
                  )}
                </span>
              </div>
              <div className="input-container">
                <input
                  type="text"
                  placeholder="Name"
                  className="input-field"
                  value={name}
                  onChange={handleNameChange}
                />
                <span className="validation-message">
                  {!nameRegex.test(name) ? (
                    <span style={{ color: "red" }}>❌</span>
                  ) : (
                    <span style={{ color: "green" }}>✅</span>
                  )}
                </span>
              </div>
              <div className="input-container">
                <input
                  type="text"
                  value={surname}
                  onChange={handleSurnameChange}
                  className="input-field"
                  placeholder="Surname"
                />
                <span className="validation-message">
                  {!nameRegex.test(surname) ? (
                    <span style={{ color: "red" }}>❌</span>
                  ) : (
                    <span style={{ color: "green" }}>✅</span>
                  )}
                </span>
              </div>
              <div className="input-container">
                <input
                  type="text"
                  value={username}
                  onChange={handleUsernameChange}
                  className="input-field"
                  placeholder="Username"
                />
                <span className="validation-message">
                  {!nameRegex.test(username) ? (
                    <span style={{ color: "red" }}>❌</span>
                  ) : (
                    <span style={{ color: "green" }}>✅</span>
                  )}
                </span>
              </div>
              <div className="input-container">
                <input
                  type="email"
                  placeholder="Email"
                  value={email}
                  onChange={handleEmailChange}
                  className="input-field"
                />
                <span className="validation-message">
                  {emailValid ? (
                    <span style={{ color: "green" }}>✅</span>
                  ) : (
                    <span style={{ color: "red" }}>❌</span>
                  )}
                </span>
              </div>
              <div className="input-container">
                <input
                  type="password"
                  placeholder="Password"
                  className="input-field"
                  value={password}
                  onChange={handlePasswordChange}
                />
                <span className="validation-message">
                  {!pswdRegex.test(password) ? (
                    <span style={{ color: "red" }}>❌</span>
                  ) : (
                    <span style={{ color: "green" }}>✅</span>
                  )}
                </span>
              </div>
              <div className="input-container">
                <input
                  type="password"
                  placeholder="Confirm Password"
                  className="input-field"
                  value={confirmPassword}
                  onChange={handleConfirmPasswordChange}
                />
                <span className="validation-message">
                  {passwordMatch ? (
                    <span style={{ color: "green" }}>✅</span>
                  ) : (
                    <span style={{ color: "red" }}>❌</span>
                  )}
                </span>
              </div>
              <button
                className="submit-button"
                disabled={
                  role === "" ||
                  name === "" ||
                  surname === "" ||
                  username === "" ||
                  !emailValid ||
                  !passwordMatch
                }
              >
                Sign Up
              </button>
              <p
                ref={errRef}
                className={errMsg ? "errmsg" : "offscreen"}
                aria-live="assertive"
                style={{ color: "red" }}
              >
                {errMsg}
              </p>
            </form>
          </div>
        ) : null}
      </div>
    </Layout>
  );
}

export default LoginSignUp;
