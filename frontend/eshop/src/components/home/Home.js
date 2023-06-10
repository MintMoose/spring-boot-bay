import React from "react";
import Hero from "../hero/Hero";
import "./Home.css";

const Home = ({ products, isLoggedIn }) => {
  return (
    <div className="home-container">
      <h2>Products</h2>
      <Hero products={products} />
      {isLoggedIn ? (
        <div className="user-products">
          <p>Welcome, user!</p>
          <h2>My Products</h2>
          <div></div>
          {/* Render additional content for authenticated users */}
        </div>
      ) : (
        <div>
          <p>Please sign in to access more features.</p>
          {/* Render content for non-authenticated users */}
          <button id="sign-in">Sign in</button>
        </div>
      )}
    </div>
  );
};

export default Home;
