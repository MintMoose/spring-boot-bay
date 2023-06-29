import React from "react";
import "./SellerCard.css";
import rob from "../product/rob-potter.jpg";

const SellerCard = ({ sold }) => {
  return (
    <div className="sold-card">
      <div className="sold-details">
        <p className="sold-text">SOLD</p>
        <p>
          <strong>Date:</strong> {new Date(sold.orderDate).toLocaleString()}
        </p>
      </div>
      <div className="seller-img">
        <img src={rob} alt={sold.product.name} />
      </div>

      <div className="sold-details">
        <p className="sold-name">{sold.product.name}</p>
        <p className="total-price-seller">
          <strong>Total Price:</strong> £{sold.totalPrice}
        </p>
        <p className="payment-status-seller">
          <strong>Payment Status:</strong> {sold.paymentStatus}
        </p>
      </div>
    </div>
  );
};

export default SellerCard;
