import React from "react";
import Hero from "../hero/Hero";
import "./Home.css";

const Home = ({ products }) => {
  return (
    <div className="home-container">
      <h2>Products</h2>
      <Hero products={products} />
      <button id="sign-in">Sign in</button>
    </div>
  );
};

export default Home;
