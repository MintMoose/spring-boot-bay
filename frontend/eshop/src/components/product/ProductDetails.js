import { useParams } from "react-router-dom";
import React, { useState, useEffect } from "react";
import api from "../../api/axiosConfig";
import rob from "../product/rob-potter.jpg";
import "./ProductDetails.css";

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
    <div className="div-center">
      {productDetails ? (
        <div className="product-detail-container">
          <img
            // src={productDetails.imageUrl}
            src={rob}
            alt={productDetails.name}
            style={{ maxWidth: "300px" }}
          />
          <div className="product-middle">
            <h1>{productDetails.name}</h1>

            <p className="product-description">{productDetails.description}</p>
            <p>
              Category {">"} {productDetails.category}
            </p>
          </div>
          <div className="product-end">
            <div>
              <p className="product-detail-price">
                ${productDetails.price.toFixed(2)}
              </p>
            </div>
            <button className="add-to-cart">Buy</button>
            <p className="seller-name">
              Seller: {productDetails.customerUsername}
            </p>
          </div>
        </div>
      ) : (
        <p>Product with {product_id} doest not exist</p>
      )}
    </div>
  );
};

export default ProductDetails;
