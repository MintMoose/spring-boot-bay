import React from "react";
import { Link, useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import "./logoutButton.css";

const LogoutButton = ({ setAuthData }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Clear authentication data
    setAuthData({ username: "", isLoggedIn: false });

    // Remove JWT token from cookies
    Cookies.remove("jwt");
    Cookies.remove("username");

    // Redirect to the login page
    navigate("/login");
  };

  return (
    <button className="logout-button" onClick={handleLogout}>
      Logout
    </button>
  );
};

export default LogoutButton;
