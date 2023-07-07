import React, { useState } from "react";
import { Modal, Button } from "react-bootstrap";
import api from "../api/axiosConfig";

const DeleteButton = ({ product }) => {
  const [showConfirmation, setShowConfirmation] = useState(false);

  const handleDelete = () => {
    setShowConfirmation(true);
  };

  const handleConfirmation = async () => {
    console.log(product);
    console.log("Deleting product", product.id);

    try {
      const response = await api.delete(`/products/${product.id}`);
      if (response.status === 200) {
        console.log("Delete Successful.");
        window.location.reload();
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
        <Modal.Body>Are you sure you want to delete this product?</Modal.Body>
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
  );
};

export default DeleteButton;
