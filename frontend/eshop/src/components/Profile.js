import ProductCard from "./product/ProductCard";
import "./Profile.css";
import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { Container, Row, Col, Form, Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSignature,
  faUser,
  faShoppingCart,
  faShop,
  faAnchorLock,
} from "@fortawesome/free-solid-svg-icons";
import api from "../api/axiosConfig";
import OrderCard from "./order/OrderCard";
import SellerCard from "./order/SellerCard";

function Profile({ authData, userProducts }) {
  const [name, setName] = useState("");
  const [buildingNumber, setBuildingNumber] = useState("");
  const [street, setStreet] = useState("");
  const [city, setCity] = useState("");
  const [country, setCountry] = useState("");
  const [zipcode, setZipcode] = useState("");
  const [selectedTab, setSelectedTab] = useState("my-products");
  const [myOrders, setMyOrders] = useState("");
  const [mySold, setMySold] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    const data = {
      address: {
        buildingNumber,
        street,
        city,
        country,
        zipcode,
      },
    };

    api
      .put("/api/profile", data)
      .then((response) => {
        console.log("Address update successful:", response.data);
        // Show success message or redirect the user
      })
      .catch((error) => {
        console.error("Profile update failed:", error);
      });
  };

  const handleChangeNameSubmit = (e) => {
    e.preventDefault();

    const data = {
      name,
    };

    api
      .put("/customers", data)
      .then((response) => {
        // Handle success
        console.log("Name change successful:", response.data);
        // Show success message or redirect the user
      })
      .catch((error) => {
        // Handle error
        console.error("Name change failed:", error);
        // Show error message or handle the error
      });
  };

  const handleTabClick = async (tab) => {
    setSelectedTab(tab);
    if (tab === "my-orders") {
      try {
        const response = await api.get("/orders/my-orders");
        if (response.status === 200) {
          setMyOrders(response.data);
          console.log(response.data);
        } else {
          console.log("Request failed with status code:", response.status);
        }
      } catch (error) {
        console.error("Failed to fetch my orders:", error);
      }
    } else if (tab === "my-products") {
      // Fetch userProducts
    } else if (tab === "selling") {
      try {
        const response = await api.get("/orders/selling-orders");
        if (response.status === 200) {
          setMySold(response.data);
        } else {
          console.log("Request failed with status code:", response.status);
        }
      } catch (error) {
        console.error("Failed to fetch my sold products:", error);
      }
    }
  };

  const renderContent = () => {
    if (selectedTab === "update-profile") {
      return (
        <Container className="second-back">
          <Row>
            <Col>
              <h1>Update Address</h1>
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
              <div className="order-list">
                {myOrders && myOrders.length > 0 ? (
                  myOrders.map((order) => <OrderCard order={order} />)
                ) : (
                  <p>No orders found.</p>
                )}
              </div>
            </Col>
          </Row>
        </Container>
      );
    } else if (selectedTab === "selling") {
      return (
        <Container>
          <Row>
            <Col>
              <h1>Sold</h1>
              <div className="selling-list">
                {mySold && mySold.length > 0 ? (
                  mySold.map((sold) => <SellerCard sold={sold} />)
                ) : (
                  <p>
                    You have something to sell? We have 1.35 million buyers!
                  </p>
                )}
              </div>
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
              <FontAwesomeIcon icon={faShop} className="my-product-icon" />{" "}
              <span>My Products</span>
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
              <FontAwesomeIcon
                icon={faShoppingCart}
                className="my-orders-icon"
              />{" "}
              <span>My Orders</span>
            </NavLink>
          </li>
          <li className="nav-li">
            <NavLink
              className={`nav-button  ${
                selectedTab === "selling" ? "active" : ""
              }`}
              to="#"
              onClick={() => handleTabClick("selling")}
            >
              <FontAwesomeIcon icon={faAnchorLock} className="my-orders-icon" />{" "}
              <span>Sold</span>
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
              <FontAwesomeIcon icon={faUser} className="update-address-icon" />{" "}
              <span>Update Address</span>
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
              <FontAwesomeIcon
                icon={faSignature}
                className="change-name-icon"
              />
              <span>Change Name</span>
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
