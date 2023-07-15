import React from "react";
import "./ProductCard.css";
import rob from "./rob-potter.jpg";
import { Link } from "react-router-dom";
import DeleteButton from "../DeleteButton";

const ProductCard = ({ product, size, username }) => {
  const cardClassName =
    size === "large" ? "product-card large" : "product-card small";

  const titleClassName =
    size === "large" ? "product-title large" : "product-title small";

  const priceClassName =
    size === "large" ? "product-info large" : "product-info small";

  const isProductOwnedByUser =
    product.customerUsername === username && product.sold === false;

  return (
    <div className={cardClassName}>
      <Link to={`/product/${product.id}`}>
        <img src={rob} alt={product.name} className="product-image" />

        <div className="product-details">
          <div>
            <h2 className={titleClassName}>{product.name}</h2>
          </div>
        </div>
      </Link>
      <div className="product-details">
        <div className={priceClassName}>
          <p className={"product-price"}>£{product.price.toFixed(0)}</p>
          {isProductOwnedByUser ? (
            <DeleteButton product={product} />
          ) : product.sold ? (
            <p className="sold-icon">SOLD</p>
          ) : (
            username && <button className="add-to-cart">Purchase</button>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
