import React from "react";
import "./OrderCard.css";
import rob from "../product/rob-potter.jpg";

const OrderCard = ({ order }) => {
  const {
    orderId,
    customerId,
    orderDate,
    product,
    totalPrice,
    paymentStatus,
    sellerId,
  } = order;

  return (
    <div className="order-card">
      <div className="order-header">
        <div>
          <p className="grey-thin">Order Placed:</p>
          <p>{new Date(orderDate).toLocaleString()}</p>
        </div>
        <div className="price-div">
          <p className="grey-thin">Total:</p>
          <p className="price-order">£{totalPrice}</p>
        </div>
        <div className="name-div">
          <p>{product.name}</p>
        </div>

        {/* <p>Order Placed: {new Date(orderDate).toLocaleString()}</p>
        <p>Total: £{totalPrice}</p>
        <p>{product.name}</p> */}
      </div>
      <hr className="small-hr " />
      <div className="order-details">
        <img src={rob} alt={product.name} />
        <div>
          <p className="grey-thin">{product.category}</p>
          <p>Seller: {product.customerUsername}</p>
        </div>
      </div>
      <hr />
      <p className="payment-status">
        <span className="grey-thin">Payment Status:</span> {paymentStatus}
      </p>
    </div>
  );
};

export default OrderCard;
