import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import "./Payment.css";
import api from "../../api/axiosConfig";
import rob from "../product/rob-potter.jpg";

const ProductDisplay = ({ product_id, productDetails }) => (
  <section>
    <div className="center-div">
      <img
        className="checkout-image"
        src={rob}
        alt="The cover of Stubborn Attachments"
      />
      <div className="description-checkout">
        <h3>{productDetails.name}</h3>
        <h5 className="checkout-price">£{productDetails.price}</h5>
        <form
          action={`http://localhost:8080/payment/create-checkout-session?productId=${product_id}`}
          method="POST"
        >
          <button type="submit">Checkout</button>
        </form>
      </div>
    </div>
  </section>
);

const Message = ({ message }) => (
  <section className="center-div">
    <h4>{message}</h4>
  </section>
);

export default function Payment() {
  const [message, setMessage] = useState("");
  const [productDetails, setProductDetails] = useState();
  const { product_id } = useParams();

  useEffect(() => {
    const getDetails = async () => {
      try {
        const response = await api.get(`/open/details/${product_id}`);
        if (response.status === 200) {
          console.log(response.data);
          setProductDetails(response.data);
        } else {
          setMessage("No Product found or accesable.");
          console.log("Request failed with status code:", response.status);
        }
      } catch (err) {
        console.log(err);
      }
    };
    getDetails();
  }, []);

  useEffect(() => {
    // Check to see if this is a redirect back from Checkout
    const query = new URLSearchParams(window.location.search);

    if (query.get("success")) {
      setMessage("Order placed! You will receive an email confirmation.");
    }

    if (query.get("canceled")) {
      setMessage(
        "Order canceled -- continue to shop around and checkout when you're ready."
      );
    }
    // Check if the URL contains an error message from the backend
    const error = query.get("error");
    if (error) {
      setMessage(error);
    }
  }, []);

  if (message) {
    return <Message message={message} />;
  } else if (productDetails) {
    return (
      <ProductDisplay product_id={product_id} productDetails={productDetails} />
    );
  } else {
    // Render a loading state or any other appropriate UI when productDetails is still loading
    return <div>Loading...</div>;
  }
}
