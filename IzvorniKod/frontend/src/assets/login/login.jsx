import React, { useState } from 'react';
import './login.css';

function LoginSignUp() {
  const [activeForm, setActiveForm] = useState(null);
  const [darkMode, setDarkMode] = useState(false);
  const [role, setRole] = useState('');
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordMatch, setPasswordMatch] = useState(false);
  const [emailValid, setEmailValid] = useState(false);

  const handleButtonClick = (formType) => {
    setActiveForm(formType);
  };

  const handleModeSwitch = () => {
    setDarkMode(!darkMode);
  };

  const handleRoleChange = (event) => {
    setRole(event.target.value);
  };

  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (emailRegex.test(event.target.value)) {
      setEmailValid(true);
    } else {
      setEmailValid(false);
    }
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
    if (event.target.value === confirmPassword && confirmPassword !== "") {
      setPasswordMatch(true);
    } else {
      setPasswordMatch(false);
    }
  };

  const handleConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
    if (event.target.value === password  && password !== "") {
      setPasswordMatch(true);
    } else {
      setPasswordMatch(false);
    }
  };

  return (
    <div className={`center-container ${darkMode ? 'dark-mode' : ''}`}>
      <div className="mode-switch-container">
        <button className="mode-switch-button" onClick={handleModeSwitch}>
          {darkMode ? 'Light Mode' : 'Dark Mode'}
        </button>
      </div>
      <div className="button-container">
        <button
          className={`button ${activeForm === 'login' ? 'active-button' : ''}`}
          onClick={() => handleButtonClick('login')}
        >
          Login
        </button>
        <button
          className={`button ${activeForm === 'signup' ? 'active-button' : ''}`}
          onClick={() => handleButtonClick('signup')}
        >
          Sign Up
        </button>
      </div>
      {activeForm === 'login' ? (
  <div className="form-container show">
    <h2>Login</h2>
    <form>
      <select className="input-field" value={role} onChange={handleRoleChange}>
        <option value="">Select Role</option>
        <option value="administrator">Administrator</option>
        <option value="voditelj">Voditelj</option>
        <option value="pregledavac">Pregledavac</option>
        <option value="djelatnik">Djelatnik</option>
      </select>
      <span className="validation-message">
        {role === "" ? (
          <span style={{ color: 'red' }}>❌</span>
        ) : (
          <span style={{ color: 'green' }}>✅</span>
        )}
      </span>
      <input
        type="email"
        placeholder="Email"
        className="input-field"
        value={email}
        onChange={handleEmailChange}
      />
      <span className="validation-message">
        {emailValid ? (
          <span style={{ color: 'green' }}>✅</span>
        ) : (
          <span style={{ color: 'red' }}>❌</span>
        )}
      </span>
      <input
        type="password"
        placeholder="Password"
        className="input-field"
        value={password}
        onChange={handlePasswordChange}
      />
      <span className="validation-message">
        {password === "" ? (
          <span style={{ color: 'red' }}>❌</span>
        ) : (
          <span style={{ color: 'green' }}>✅</span>
        )}
      </span>
      <button
        className="submit-button"
        disabled={role === "" || !emailValid || password === ""}
      >
        Login
      </button>
    </form>
  </div>
      ) : activeForm === 'signup' ? (
        <div className="form-container show">
           <form>
        <select className="input-field" value={role} onChange={handleRoleChange}>
          <option value="">Select Role</option>
          <option value="administrator">Administrator</option>
          <option value="voditelj">Voditelj</option>
          <option value="pregledavac">Pregledavac</option>
          <option value="djelatnik">Djelatnik</option>
        </select>
        <span className="validation-message">
          {role === "" ? (
            <span style={{ color: 'red' }}>❌</span>
          ) : (
            <span style={{ color: 'green' }}>✅</span>
          )}
        </span>
        <input
          type="text"
          placeholder="Name"
          className="input-field"
          value={name}
          onChange={handleNameChange}
        />
        <span className="validation-message">
          {name === "" ? (
            <span style={{ color: 'red' }}>❌</span>
          ) : (
            <span style={{ color: 'green' }}>✅</span>
          )}
        </span>
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
              <span style={{ color: 'green' }}>✅</span>
            ) : (
              <span style={{ color: 'red' }}>❌</span>
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
              <span style={{ color: 'green' }}>✅</span>
            ) : (
              <span style={{ color: 'red' }}>❌</span>
            )}
          </span>
        </div>
        <button
          className="submit-button"
          disabled={role === "" || name === "" || !emailValid || !passwordMatch}
        >
          Sign Up
        </button>
      </form>
        </div>
      ) : null}
    </div>   
  );
}

export default LoginSignUp;