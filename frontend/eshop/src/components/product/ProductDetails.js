import { useParams } from "react-router-dom";
import React, { useState, useEffect } from "react";
import api from "../../api/axiosConfig";
import rob from "../product/rob-potter.jpg";

const ProductDetails = () => {
  const { product_id } = useParams();
  const [productDetails, setProductDetails] = useState("");

  useEffect(() => {
    const getDetails = async () => {
      try {
        const response = await api.get(`/open/details/${product_id}`);
        if (response.status === 200) {
          console.log(response.data);
          setProductDetails(response.data);
        } else {
          console.log("Request failed with status code:", response.status);
        }
      } catch (err) {
        console.log(err);
      }
    };
    getDetails();
  }, []);

  return (
    <div>
      {productDetails ? (
        <div>
          <h1>{productDetails.name}</h1>
          <img
            // src={productDetails.imageUrl}
            src={rob}
            alt={productDetails.name}
            style={{ maxWidth: "300px" }}
          />
          <p>{productDetails.description}</p>
          <p>Price: ${productDetails.price.toFixed(2)}</p>
          <p>Category: {productDetails.category}</p>
          <p>Sold: {productDetails.sold ? "Yes" : "No"}</p>
          <p>Customer Username: {productDetails.customerUsername}</p>
        </div>
      ) : (
        <p>Product with {product_id} doest not exist</p>
      )}
    </div>
  );
};

export default ProductDetails;
