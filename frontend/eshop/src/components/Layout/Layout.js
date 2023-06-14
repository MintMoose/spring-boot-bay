import { Outlet } from "react-router-dom";
import { useState, useEffect } from "react";
import "./Layout.css";
import Navbar from "./Navbar";

import React from "react";

const Layout = ({ authData }) => {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setIsLoading(false);
    }, 500);

    return () => clearTimeout(timer);
  }, []);

  return (
    <div className="final">
      <main>
        <div>
          {isLoading ? (
            <div className="loading">
              <div
                class="spinner-border"
                style={{ width: "3rem", height: "3rem" }}
                role="status"
              >
                <span class="visually-hidden">Loading...</span>
              </div>
            </div>
          ) : (
            <div>
              <Navbar isLoggedIn={authData.isLoggedIn} />
              <Outlet />
            </div>
          )}
        </div>
      </main>
      <footer className="footer">
        <p>© 2022-2023, Springbay, Inc. or its affiliates </p>
      </footer>
    </div>
  );
};

export default Layout;
