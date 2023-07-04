import React, { useState, useEffect } from "react";
import api from "../../api/axiosConfig";
import ProductCard from "./ProductCard";
import "./Product.css";

function Products({ username, setUserProducts }) {
  const [products, setProducts] = useState();
  const [pageNumber, setPageNumber] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [error, setError] = useState(null);

  const fetchProducts = async () => {
    try {
      const response = await api.get(
        `open/products/sale?pageNumber=${pageNumber}`
      );
      setProducts(response.data.content);
      console.log(response.data.content);
      setTotalPages(response.data.totalPages);
      setError(null); // Clear any previous errors
    } catch (error) {
      console.error("Error fetching products:", error);
      setError(error.message); // Set the error message
    }
  };

  useEffect(() => {
    fetchProducts();
  }, [pageNumber]);

  const goToPreviousPage = () => {
    setTimeout(() => {
      window.scrollTo({ top: 0, behavior: "smooth" }); // Scroll to the top of the page
    }, 80);
    setPageNumber(pageNumber - 1);
  };

  const goToNextPage = () => {
    setTimeout(() => {
      window.scrollTo({ top: 0, behavior: "smooth" }); // Scroll to the top of the page
    }, 120);

    setPageNumber(pageNumber + 1);
  };

  return (
    <div className="product-main">
      <div className="product-list">
        {products && products.length > 0 ? (
          products.map((product) => (
            <ProductCard
              key={product.id}
              product={product}
              username={username}
              size="large"
              setUserProducts={setUserProducts}
              setProducts={setProducts}
            />
          ))
        ) : (
          <p>No products found.</p>
        )}
      </div>

      {error && <p>{error}</p>}

      <div className="button-container">
        <button
          className={`previous-button ${pageNumber === 0 ? "disabled" : ""}`}
          onClick={goToPreviousPage}
          disabled={pageNumber === 0}
        >
          Previous Page
        </button>
        <button
          className={`next-button ${
            pageNumber === totalPages - 1 ? "disabled" : ""
          }`}
          onClick={goToNextPage}
          disabled={pageNumber === totalPages - 1}
        >
          Next Page
        </button>
      </div>
    </div>
  );
}

export default Products;
