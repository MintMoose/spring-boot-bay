import React from "react";
import "./OrderCard.css";

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
          <p>Order Placed:</p>
          <p>{new Date(orderDate).toLocaleString()}</p>
        </div>
        <div className="price-div">
          <p>Total:</p>
          <p className="price">£{totalPrice}</p>
        </div>
        <div className="name-div">
          <p>{product.name}</p>
        </div>

        {/* <p>Order Placed: {new Date(orderDate).toLocaleString()}</p>
        <p>Total: £{totalPrice}</p>
        <p>{product.name}</p> */}
      </div>
      <hr className="small-hr " />
      <div className="product-details">
        <img src={product.imageUrl} alt={product.name} />
        <div>
          <p>{product.category}</p>
          <p>Seller: {product.customerUsername}</p>
        </div>
      </div>
      <hr />
      <p className="payment-status">Payment Status: {paymentStatus}</p>
    </div>
  );
};

export default OrderCard;
