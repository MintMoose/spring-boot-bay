import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import "./loginForm.css";
import Cookies from "js-cookie";

const LoginForm = ({ authData, setAuthData }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (!username || !password) {
      setError("Please enter your username and password");
      return;
    }

    const data = {
      username,
      password,
    };

    axios
      .post("http://localhost:8080/login/authenticate", data)
      .then((response) => {
        console.log("Login successful:", response.data);
        console.log(response.data.token);
        setAuthData(() => ({
          username: username,
          isLoggedIn: true,
        }));
        Cookies.set("jwt", response.data.token, {
          secure: true,
          sameSite: "strict",
        });
        Cookies.set("username", username, { secure: true, sameSite: "strict" });
      })
      .catch((error) => {
        console.error("Login failed:", error);
        setError("Invalid username or password");
      });
  };

  useEffect(() => {
    if (authData.isLoggedIn) {
      navigate("/"); // Redirect to the home page
    }
  }, [authData.isLoggedIn, navigate]);

  return (
    <div>
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Sign in</h2>
        <div>
          <label htmlFor="username">Username:</label>
          <input
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
        {error && <p className="error-message">{error}</p>}
        <button type="submit">Login</button>
      </form>
      <p className="register-link">
        Don't have an account? <Link to="/register">Register here</Link>
      </p>
    </div>
  );
};

export default LoginForm;
