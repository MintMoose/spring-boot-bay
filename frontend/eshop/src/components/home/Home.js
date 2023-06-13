import React from "react";
import Hero from "../hero/Hero";
import "./Home.css";
import api from "../../api/axiosConfig";

const Home = ({ authData, products }) => {
  const { isLoggedIn, username } = authData;
  const getUser = async () => {
    try {
      // todo: check http status code
      const response = await api.get("/customers");
      console.log(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div className="home-container">
      <h2>Products</h2>
      <Hero products={products} />
      {isLoggedIn ? (
        <div className="user-products">
          <p>Welcome, {username}</p>
          <h2>My Products</h2>
          <div></div>
          {/* Render additional content for authenticated users */}
        </div>
      ) : (
        <div className="sign-in-container">
          <p>Please sign in to access more features.</p>
          {/* Render content for non-authenticated users */}
          <button id="sign-in">Sign in</button>
        </div>
      )}
    </div>
  );
};

export default Home;
