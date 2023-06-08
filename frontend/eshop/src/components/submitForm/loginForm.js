import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useNavigate, useLocation } from "react-router-dom";
import "./loginForm.css";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const navigate = useNavigate();
  const location = useLocation();

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const data = {
      username,
      password,
    };

    axios
      .post("http://localhost:8080/login", data)
      .then((response) => {
        console.log("Login successful:", response.data);
        const { from } = location.state || { from: { pathname: "/" } };
        navigate.replace(from);
      })
      .catch((error) => {
        console.error("Login failed:", error);
        // Handle error, e.g., display an error message
      });
  };

  useEffect(() => {
    if (isLoggedIn) {
      navigate("/"); // Redirect to the home page
    }
  }, [isLoggedIn, navigate]);

  return (
    <div>
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Sign in</h2>
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
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        <button type="submit">Login</button>
      </form>
      <p className="copyright-warning">
        By signing in, you agree to the terms and Conditions of Use & Sale.
        Please see our Privacy Notice, our Cookies Notice, and our
        Interest-Based Ads Notice.
      </p>
      <p className="register-link">
        Don't have an account? <Link to="/register">Register here</Link>
      </p>
    </div>
  );
};

export default LoginForm;
