import React, { useState } from "react";
import "./ProductCard.css";
import rob from "./rob-potter.jpg";
import { Modal, Button } from "react-bootstrap";

const ProductCard = ({ product, size, username }) => {
  const [showConfirmation, setShowConfirmation] = useState(false);

  const cardClassName =
    size === "large" ? "product-card large" : "product-card small";

  const titleClassName =
    size === "large" ? "product-title large" : "product-title small";

  const priceClassName =
    size === "large" ? "product-info large" : "product-info small";

  const isProductOwnedByUser = product.customerUsername === username;

  const handleDelete = () => {
    setShowConfirmation(true);
  };

  const handleConfirmation = () => {
    // Perform the delete operation here
    console.log("Deleting product", product.id);
    // You can add your delete logic or API call here

    // Reset the confirmation state
    setShowConfirmation(false);
  };

  const handleCancel = () => {
    setShowConfirmation(false);
  };

  return (
    <div className={cardClassName}>
      <img
        // src={product.imageUrl}
        src={rob}
        alt={product.name}
        className="product-image"
      />
      <div className="product-details">
        <div>
          <h2 className={titleClassName}>{product.name}</h2>
        </div>
        <div className={priceClassName}>
          <p className={"product-price"}>£{product.price}</p>
          {isProductOwnedByUser ? (
            <>
              <button className="delete-button" onClick={handleDelete}>
                Delete
              </button>
              <Modal
                show={showConfirmation}
                onHide={handleCancel}
                centered
                animation={false}
              >
                <Modal.Header closeButton>
                  <Modal.Title>Delete Product</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                  Are you sure you want to delete this product?
                </Modal.Body>
                <Modal.Footer>
                  <Button variant="secondary" onClick={handleCancel}>
                    Cancel
                  </Button>
                  <Button variant="danger" onClick={handleConfirmation}>
                    Delete
                  </Button>
                </Modal.Footer>
              </Modal>
            </>
          ) : (
            <button className="add-to-cart">Add to Cart</button>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
