import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../../api/axiosConfig";
import "./UpdateProduct.css";

const Categories = {
  clothing: "CLOTHING",
  shoes: "SHOES",
  electronics: "ELECTRONICS",
  books_movies_music: "BOOKS_MOVIES_MUSIC",
  hobby_diy: "HOBBY_DIY",
  household: "HOUSEHOLD",
  health_beauty: "HEALTH_BEAUTY",
  sports_outdoors: "SPORTS_OUTDOORS",
  food_grocery: "FOOD_GROCERY",
};

function UpdateProduct() {
  const { product_id } = useParams();
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [category, setCategory] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    // Fetch the product details using the productId from the server
    const fetchProductDetails = async () => {
      try {
        const response = await api.get(`/products/details/${product_id}`);
        if (response.status === 200) {
          const product = response.data;
          setName(product.name);
          setDescription(product.description);
          setPrice(product.price.toFixed(2));
          setCategory(product.category);
          setImageUrl(product.imageUrl);
        } else {
          console.log("Request failed with status code:", response.status);
          setErrorMessage("Failed to fetch product details.");
        }
      } catch (err) {
        console.log(err);
        setErrorMessage("Failed to fetch product details.");
      }
    };

    fetchProductDetails();
  }, [product_id]);

  const handleFormSubmit = async (event) => {
    event.preventDefault();

    const updatedProduct = {
      name,
      description,
      price: parseFloat(price),
      category,
      imageUrl,
    };

    try {
      const response = await api.put(`/products/${product_id}`, updatedProduct);
      if (response.status === 200) {
        console.log("Product updated successfully");
        navigate(`/product/${product_id}`); // Redirect to the updated product page
      } else {
        console.log("Request failed with status code:", response.status);
        setErrorMessage("Failed to update the product. Please try again.");
      }
    } catch (err) {
      console.log(err);
      setErrorMessage("Failed to update the product. Please try again.");
    }
  };

  return (
    <div className="update-product-container">
      <h1>Update Product</h1>
      {errorMessage && <p className="error-message">{errorMessage}</p>}
      <form className="update-product-form" onSubmit={handleFormSubmit}>
        <div className="form-field">
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(event) => setName(event.target.value)}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="description">Description:</label>
          <textarea
            id="description"
            value={description}
            onChange={(event) => setDescription(event.target.value)}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="price">Price:</label>
          <input
            type="number"
            id="price"
            step="0.01"
            value={price}
            onChange={(event) => setPrice(event.target.value)}
            required
          />
        </div>
        <div className="form-field">
          <label htmlFor="category">Category:</label>
          <select
            id="category"
            value={category}
            onChange={(event) => setCategory(event.target.value)}
            required
          >
            <option value="">Select a category</option>
            {Object.keys(Categories).map((key) => (
              <option key={key} value={key}>
                {Categories[key]}
              </option>
            ))}
          </select>
        </div>
        <div className="form-field">
          <label htmlFor="imageUrl">Image URL:</label>
          <input
            type="text"
            id="imageUrl"
            value={imageUrl}
            onChange={(event) => setImageUrl(event.target.value)}
            required
          />
        </div>
        <button type="submit">Update Product</button>
      </form>
    </div>
  );
}

export default UpdateProduct;
