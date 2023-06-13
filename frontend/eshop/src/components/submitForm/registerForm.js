import React, { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./registerForm.css";

const RegisterForm = () => {
  const [username, setUsername] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (!username || !name || !email || !password) {
      setErrorMessage("Please fill in all fields.");
      return;
    }

    const data = {
      username,
      name,
      email,
      password,
    };

    axios
      .post("http://localhost:8080/login/register", data)
      .then((response) => {
        console.log("Registration successful:", response.data);
        // Handle successful response, e.g., display a success message
      })
      .catch((error) => {
        console.error("Registration failed:", error);
        // Handle error, e.g., display an error message
      });
  };

  return (
    <div>
      <form className="register-form" onSubmit={handleSubmit}>
        <h2>Create Account</h2>
        {errorMessage && <p className="error-message">{errorMessage}</p>}
        <div>
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={handleUsernameChange}
          />
        </div>
        <div>
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={handleNameChange}
          />
        </div>
        <div>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={handleEmailChange}
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        <button type="submit">Register</button>
      </form>
      <p className="terms-notice">
        By signing in, you agree to the terms and Conditions of Use & Sale.
        Please see our Privacy Notice, our Cookies Notice, and our
        Interest-Based Ads Notice.
      </p>
      <p className="login-link">
        Already have an account? <Link to="/login">Sign in</Link>
      </p>
    </div>
  );
};

export default RegisterForm;
