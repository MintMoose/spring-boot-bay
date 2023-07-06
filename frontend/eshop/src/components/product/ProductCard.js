import React, { useState } from "react";
import "./ProductCard.css";
import rob from "./rob-potter.jpg";
import { Modal, Button } from "react-bootstrap";
import api from "../../api/axiosConfig";
import { Link } from "react-router-dom";

const ProductCard = ({
  product,
  size,
  username,
  setUserProducts,
  setProducts,
}) => {
  const [showConfirmation, setShowConfirmation] = useState(false);

  const cardClassName =
    size === "large" ? "product-card large" : "product-card small";

  const titleClassName =
    size === "large" ? "product-title large" : "product-title small";

  const priceClassName =
    size === "large" ? "product-info large" : "product-info small";

  const isProductOwnedByUser =
    product.customerUsername === username && product.sold === false;

  const handleDelete = () => {
    setShowConfirmation(true);
  };

  const handleConfirmation = async () => {
    // Perform the delete operation here
    console.log("Deleting product", product.id);

    //API delete call here
    try {
      const response = await api.delete(`/products/${product.id}`);
      if (response.status === 200) {
        console.log("Delete Successful.");
        setUserProducts((prevProducts) =>
          prevProducts.filter((p) => p.id !== product.id)
        );
        setProducts((prevProducts) =>
          prevProducts.filter((p) => p.id !== product.id)
        );
      } else {
        console.log("Request failed with status code:", response.status);
      }
    } catch (err) {
      console.log(err);
    }

    setShowConfirmation(false);
  };

  const handleCancel = () => {
    setShowConfirmation(false);
  };

  return (
    <div className={cardClassName}>
      <Link to={`/product/${product.id}`}>
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
        </div>
      </Link>
      <div className="product-details">
        <div className={priceClassName}>
          <p className={"product-price"}>£{product.price.toFixed(0)}</p>
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
          ) : product.sold ? (
            <p className="sold-icon">SOLD</p>
          ) : (
            <button className="add-to-cart">Purchase</button>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
