import React from "react";
import Hero from "../hero/Hero";
import "./Home.css";
import { useEffect } from "react";
import ProductCard from "../product/ProductCard";
import { useNavigate } from "react-router-dom";

const Home = ({ authData, products, userProducts }) => {
  const { isLoggedIn, username } = authData;

  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/login");
  };

  useEffect(() => {}, [userProducts]);

  console.log(userProducts);

  return (
    <div className="home-container">
      <p>Welcome, {username}</p>
      <h2>Products</h2>
      <Hero products={products} username={username} />
      {isLoggedIn ? (
        <div className="user-products">
          <h2>My Products</h2>
          <div className="product-list2">
            {userProducts && userProducts.length > 0 ? (
              userProducts.map((product) => (
                <ProductCard
                  key={product.id}
                  product={product}
                  username={username}
                  size="small"
                />
              ))
            ) : (
              <p>No products found.</p>
            )}
          </div>
          {/* Render additional content for authenticated users */}
        </div>
      ) : (
        <div className="sign-in-container">
          <p>Please sign in to access more features.</p>
          {/* Render content for non-authenticated users */}
          <button id="sign-in" onClick={handleClick}>
            Sign in
          </button>
        </div>
      )}
    </div>
  );
};

export default Home;
