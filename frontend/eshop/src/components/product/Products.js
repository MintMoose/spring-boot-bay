import React from "react";
import api from "../../api/axiosConfig";
import { useState, useEffect } from "react";

function Products() {
  const [products, setProducts] = useState([]);
  const [pageNumber, setPageNumber] = useState(0);

  useEffect(() => {
    fetchProducts();
  }, [pageNumber]);

  const fetchProducts = async () => {
    try {
      const response = await api.get(`/products?page=${pageNumber}`);
      setProducts(response.data);
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  const goToPreviousPage = () => {
    setPageNumber(pageNumber - 1);
  };

  const goToNextPage = () => {
    setPageNumber(pageNumber + 1);
  };

  return (
    <div>
      {/* Render the products list */}
      {products.map((product) => (
        <div key={product.id}>{product.name}</div>
      ))}

      {/* Render pagination controls */}
      <button onClick={goToPreviousPage} disabled={pageNumber === 0}>
        Previous Page
      </button>
      <button onClick={goToNextPage}>Next Page</button>
    </div>
  );
}

export default Products;
