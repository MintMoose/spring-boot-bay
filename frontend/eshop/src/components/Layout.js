import { Outlet, Link } from "react-router-dom";
import "./Navbar.css";

import React from "react";

const Layout = ({ isLoggedIn }) => {
  return (
    <div class="final">
      <main>
        <nav className="navbar navbar-expand-lg navbar-dark ">
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <Link className="nav-link" to="/">
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/products">
                  Products
                </Link>
              </li>
              {isLoggedIn && (
                <li className="nav-item">
                  <Link className="nav-link" to="/profile">
                    Profile
                  </Link>
                </li>
              )}
              {!isLoggedIn && (
                <li className="nav-item">
                  <Link className="nav-link" to="/login">
                    Login
                  </Link>
                </li>
              )}
            </ul>
          </div>
        </nav>
        <Outlet />
      </main>
      <footer className="footer">
        <p>© 2022-2023, Springbay, Inc. or its affiliates </p>
      </footer>
    </div>
  );
};

export default Layout;
