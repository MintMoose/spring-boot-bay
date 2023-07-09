import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/axiosConfig";
import { ProductContext } from "./ProductContext";
import "./CreateProduct.css";

const Categories = {
  CLOTHING: "CLOTHING",
  SHOES: "SHOES",
  ELECTRONICS: "ELECTRONICS",
  BOOKS_MOVIES_MUSIC: "BOOKS_MOVIES_MUSIC",
  HOBBY_DIY: "HOBBY_DIY",
  HOUSEHOLD: "HOUSEHOLD",
  HEALTH_BEAUTY: "HEALTH_BEAUTY",
  SPORTS_OUTDOORS: "SPORTS_OUTDOORS",
  FOOD_GROCERY: "FOOD_GROCERY",
};

function CreateProduct() {
  const { addProduct } = useContext(ProductContext);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [category, setCategory] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleFormSubmit = async (event) => {
    event.preventDefault();

    const newProduct = {
      name,
      description,
      price: parseFloat(price),
      category: Categories[category],
      imageUrl,
    };

    try {
      const response = await api.post("/products", newProduct);
      if (response.status === 201) {
        console.log("Product created successfully");
        addProduct(newProduct);
        navigate("/");
      } else {
        console.log("Request failed with status code:", response.status);
        setErrorMessage("Failed to create the product. Please try again."); // Set error message
      }
    } catch (err) {
      console.log(err);
      setErrorMessage("Failed to create the product. Please try again."); // Set error message
    }
  };

  return (
    <div className="container-create-product">
      <div>
        <h1>Create Product</h1>
        {errorMessage && <p className="red-font">{errorMessage}</p>}{" "}
        {/* Conditional rendering of error message */}
        <form className="form-create-product" onSubmit={handleFormSubmit}>
          <div>
            <label htmlFor="name">Name:</label>
            <input
              type="text"
              id="name"
              value={name}
              onChange={(event) => setName(event.target.value)}
              required
            />
          </div>
          <div>
            <label htmlFor="description">Description:</label>
            <textarea
              id="description"
              value={description}
              onChange={(event) => setDescription(event.target.value)}
              required
            />
          </div>
          <div>
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
          <div>
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
          <div>
            <label htmlFor="imageUrl">Image URL:</label>
            <input
              type="text"
              id="imageUrl"
              value={imageUrl}
              onChange={(event) => setImageUrl(event.target.value)}
              required
            />
          </div>
          <button type="submit">Create Product</button>
        </form>
      </div>
    </div>
  );
}

export default CreateProduct;
