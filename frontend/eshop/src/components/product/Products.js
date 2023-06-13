import React from "react";
import api from "../../api/axiosConfig";
import { useState, useEffect } from "react";
import ProductCard from "../ProductCard";

function Products() {
  const [products, setProducts] = useState([]);
  const [pageNumber, setPageNumber] = useState(0);

  const fetchProducts = async () => {
    try {
      const response = await api.get(`/products`);
      console.log("Something.");
      setProducts(response.data);
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, [pageNumber]);

  const goToPreviousPage = () => {
    setPageNumber(pageNumber - 1);
  };

  const goToNextPage = () => {
    setPageNumber(pageNumber + 1);
  };

  return (
    <div>
      {/* Render the products list */}
      {products &&
        products.map((product) => {
          return <ProductCard key={product.id} product={product} />;
        })}

      {/* Render pagination controls */}
      <button onClick={goToPreviousPage} disabled={pageNumber === 0}>
        Previous Page
      </button>
      <button onClick={goToNextPage}>Next Page</button>
    </div>
  );
}

export default Products;
