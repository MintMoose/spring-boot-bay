import { useParams } from "react-router-dom";
import React, { useState, useEffect } from "react";
import api from "../../api/axiosConfig";
import rob from "../product/rob-potter.jpg";
import "./ProductDetails.css";

const ProductDetails = ({ authData }) => {
  const { product_id } = useParams();
  const [productDetails, setProductDetails] = useState("");
  const [postCode, setPostCode] = useState("");
  const [showDetails, setShowDetails] = useState(false);

  const toggleDetails = () => {
    setShowDetails(!showDetails);
  };

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
    const getAddress = async () => {
      try {
        const response = await api.get(`/address/user/${authData.username}`);
        if (response.data.id) {
          console.log(response.data);
          setPostCode(response.data.postcode);
        }
      } catch (error) {
        console.error("Failed to fetch profile:", error);
        // Handle error
      }
    };

    getDetails();
    getAddress();
  }, []);

  const currentDate = new Date();
  const firstDate = new Date(currentDate);
  const secondDate = new Date(currentDate);

  firstDate.setDate(firstDate.getDate() + 3);
  secondDate.setDate(secondDate.getDate() + 6);

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

            <p className="product-description">
              "{productDetails.description}"
            </p>
            <div className="post-return">
              <span>Postage:</span>
              <p>
                Free 3 day postage Royal Mail 2nd Class Get it between{" "}
                <strong>
                  {firstDate.toLocaleDateString("en-US", {
                    weekday: "short",
                    day: "numeric",
                    month: "short",
                  })}{" "}
                </strong>{" "}
                and{" "}
                <strong>
                  {secondDate.toLocaleDateString("en-US", {
                    weekday: "short",
                    day: "numeric",
                    month: "short",
                  })}{" "}
                </strong>{" "}
                {postCode && (
                  <span>
                    to{" "}
                    <a href="/profile" className="postCode-link">
                      {postCode}
                    </a>
                  </span>
                )}
              </p>
              <span>Returns:</span>
              <p>
                30 days return. Buyer pays for return postage. See{" "}
                <button
                  className={`button-text ${showDetails ? "open" : ""}`}
                  onClick={toggleDetails}
                >
                  Details
                  <span
                    className={`arrow ${showDetails ? "up" : "down"}`}
                  ></span>
                </button>
              </p>
              <div
                className={`additional-details ${showDetails ? "open" : ""}`}
              >
                <h5>Returns policy</h5>
                <p>
                  Refer to the <a href="/legal">return policy</a>. Testing using
                  Stripe no purchases made, if you receive an item that is not
                  as described in the listing. How?
                </p>
                {/* Add more details as needed */}
              </div>
            </div>
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
            <button className="add-to-cart">Buy it now</button>
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
