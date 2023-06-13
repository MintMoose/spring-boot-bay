import { Outlet } from "react-router-dom";
import "./Layout.css";
import Navbar from "./Navbar";

import React from "react";

const Layout = ({ authData }) => {
  return (
    <div className="final">
      <main>
        <Navbar isLoggedIn={authData.isLoggedIn} />
        <Outlet />
      </main>
      <footer className="footer">
        <p>© 2022-2023, Springbay, Inc. or its affiliates </p>
      </footer>
    </div>
  );
};

export default Layout;
