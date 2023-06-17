import ProductCard from "./product/ProductCard";
import "./Profile.css";
import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { Container, Row, Col, Form, Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUser,
  faShoppingCart,
  faShop,
} from "@fortawesome/free-solid-svg-icons";

function Profile({ authData, userProducts }) {
  const [name, setName] = useState("");
  const [buildingNumber, setBuildingNumber] = useState("");
  const [street, setStreet] = useState("");
  const [city, setCity] = useState("");
  const [country, setCountry] = useState("");
  const [zipcode, setZipcode] = useState("");
  const [selectedTab, setSelectedTab] = useState("my-products");

  const handleSubmit = (e) => {
    e.preventDefault();
    // Perform an API request to update the user's profile with the new data
    // You can use axios or any other HTTP library for this
    // Example: axios.put('/api/profile', { name, address })
    // Upon successful update, you can show a success message or redirect the user to another page
  };

  const handleChangeNameSubmit = (e) => {
    e.preventDefault();
    // Perform an API request to update the user's name with the new data
    // You can use axios or any other HTTP library for this
    // Example: axios.put('/api/profile/name', { name })
    // Upon successful update, you can show a success message or redirect the user to another page
  };

  const handleTabClick = (tab) => {
    setSelectedTab(tab);
  };

  const renderContent = () => {
    if (selectedTab === "update-profile") {
      return (
        <Container className="second-back">
          <Row>
            <Col>
              <h1>Change Address</h1>
              <div className="container">
                <Form onSubmit={handleSubmit}>
                  <Form.Group controlId="buildingNumber" className="mb-3 mt-3">
                    <Form.Label>Building Number</Form.Label>
                    <Form.Control
                      type="text"
                      value={buildingNumber}
                      onChange={(e) => setBuildingNumber(e.target.value)}
                      placeholder="Enter your building number"
                    />
                  </Form.Group>

                  <Form.Group controlId="street" className="mb-3">
                    <Form.Label>Street</Form.Label>
                    <Form.Control
                      type="text"
                      value={street}
                      onChange={(e) => setStreet(e.target.value)}
                      placeholder="Enter your street"
                    />
                  </Form.Group>

                  <Form.Group controlId="city" className="mb-3">
                    <Form.Label>City</Form.Label>
                    <Form.Control
                      type="text"
                      value={city}
                      onChange={(e) => setCity(e.target.value)}
                      placeholder="Enter your city"
                    />
                  </Form.Group>

                  <Form.Group controlId="country" className="mb-3">
                    <Form.Label>Country</Form.Label>
                    <Form.Control
                      type="text"
                      value={country}
                      onChange={(e) => setCountry(e.target.value)}
                      placeholder="Enter your country"
                    />
                  </Form.Group>

                  <Form.Group controlId="zipcode" className="mb-3">
                    <Form.Label>ZIP Code</Form.Label>
                    <Form.Control
                      type="text"
                      value={zipcode}
                      onChange={(e) => setZipcode(e.target.value)}
                      placeholder="Enter your ZIP code"
                    />
                  </Form.Group>

                  <Button variant="primary" type="submit" className="mt-2">
                    Update Profile
                  </Button>
                </Form>
              </div>
            </Col>
          </Row>
        </Container>
      );
    } else if (selectedTab === "my-products") {
      return (
        <Container>
          <Row>
            <Col>
              <h1>My Products</h1>

              <div className="product-list">
                {userProducts && userProducts.length > 0 ? (
                  userProducts.map((product) => (
                    <ProductCard
                      key={product.id}
                      product={product}
                      username={authData.username}
                      size="small"
                    />
                  ))
                ) : (
                  <p>No products found.</p>
                )}
              </div>
            </Col>
          </Row>
        </Container>
      );
    } else if (selectedTab === "my-orders") {
      return (
        <Container>
          <Row>
            <Col>
              <h1>My Orders</h1>

              <div className="order-list"></div>
            </Col>
          </Row>
        </Container>
      );
    } else if (selectedTab === "change-name") {
      return (
        <Container className="second-back">
          <Row>
            <Col>
              <h1>Change Name</h1>

              <div className="container">
                <Form onSubmit={handleChangeNameSubmit}>
                  <Form.Group controlId="name" className="mb-3 mt-3">
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                      type="text"
                      value={name}
                      onChange={(e) => setName(e.target.value)}
                      placeholder="Enter your name"
                    />
                  </Form.Group>
                  <Button variant="primary" type="submit" className="mt-1">
                    Change Name
                  </Button>
                </Form>
              </div>
            </Col>
          </Row>
        </Container>
      );
    }
  };

  return (
    <div className="profile-container">
      <div className="second-nav">
        <ul className="nav-ul">
          <li className="nav-li">
            <NavLink
              className={`nav-button  ${
                selectedTab === "my-products" ? "active" : ""
              }`}
              to="#"
              onClick={() => handleTabClick("my-products")}
            >
              <FontAwesomeIcon icon={faShop} /> My Products
            </NavLink>
          </li>
          <li className="nav-li">
            <NavLink
              className={`nav-button  ${
                selectedTab === "my-orders" ? "active" : ""
              }`}
              to="#"
              onClick={() => handleTabClick("my-orders")}
            >
              <FontAwesomeIcon icon={faShoppingCart} /> My Orders
            </NavLink>
          </li>
          <li className="nav-li">
            <NavLink
              className={`nav-button  ${
                selectedTab === "update-profile" ? "active" : ""
              }`}
              to="#"
              onClick={() => handleTabClick("update-profile")}
            >
              <FontAwesomeIcon icon={faUser} /> Update Address
            </NavLink>
          </li>
          <li className="nav-li">
            <NavLink
              className={`nav-button  ${
                selectedTab === "change-name" ? "active" : ""
              }`}
              to="#"
              onClick={() => handleTabClick("change-name")}
            >
              <FontAwesomeIcon icon={faShoppingCart} /> Change Name
            </NavLink>
          </li>
        </ul>
      </div>

      <div className="profile-inner">
        <h2>{authData.username}</h2>
        <div className="content">{renderContent()}</div>
      </div>
    </div>
  );
}

export default Profile;
