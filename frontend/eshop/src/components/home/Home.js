import React from "react";
import Hero from "../hero/Hero";
import "./Home.css";

const Home = ({ products, isLoggedIn }) => {
  return (
    <div className="home-container">
      <h2>Products</h2>
      {isLoggedIn ? (
        <div>
          <p>Welcome, user!</p>
          <h3>My Products</h3>
          <div></div>
          {/* Render additional content for authenticated users */}
        </div>
      ) : (
        <div>
          <p>Please sign in to access more features.</p>
          {/* Render content for non-authenticated users */}
        </div>
      )}
      <Hero products={products} />
      <button id="sign-in">Sign in</button>
    </div>
  );
};

export default Home;
