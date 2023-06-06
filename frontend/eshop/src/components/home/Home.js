import React from "react";
import Hero from "../hero/Hero";

const Home = ({ products }) => {
  return (
    <div>
      <h1>Home page</h1>
      <Hero products={products} />
    </div>
  );
};

export default Home;
