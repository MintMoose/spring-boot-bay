import "./Profile.css";
import React, { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSignature,
  faUser,
  faShoppingCart,
  faShop,
  faAnchorLock,
} from "@fortawesome/free-solid-svg-icons";
import api from "../api/axiosConfig";
import MyProducts from "./tabs/MyProducts";
import MyOrdersPage from "./tabs/MyOrders";
import Selling from "./tabs/Selling";
import UpdateAddress from "./tabs/UpdateAddress";
import ChangeName from "./tabs/ChangeName";

function Profile({ authData, userProducts, setUserProducts, setProducts }) {
  const [selectedTab, setSelectedTab] = useState("my-products");
  const [myOrders, setMyOrders] = useState("");
  const [mySold, setMySold] = useState("");
  const [dbAddress, setDbAddress] = useState("");
  const [dbName, setDbName] = useState("");

  const fetchName = async () => {
    try {
      // Fetch the name from the API
      const response = await api.get(`/customers/${authData.username}`);
      if (response.status === 200) {
        setDbName(response.data.name);
      } else {
        console.log("Request failed with status code:", response.status);
      }
    } catch (error) {
      console.error("Failed to fetch name:", error);
    }
  };

  const fetchData = async () => {
    try {
      const response = await api.get(`/address/user/${authData.username}`);
      if (response.status === 200) {
        setDbAddress(response.data);
      } else {
        console.log("Request failed with status code:", response.status);
      }
    } catch (error) {
      console.error("Failed to fetch address:", error);
    }
  };

  useEffect(() => {
    if (selectedTab === "update-profile") {
      fetchData();
    } else if (selectedTab === "change-name") {
      fetchName();
    }
    // ...other conditions
  }, [selectedTab, authData.username]);

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
    } else if (tab === "update-profile") {
      try {
        const response = await api.get(`/address/user/${authData.username}`);
        if (response.status === 200) {
          setDbAddress(response.data);
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
        <UpdateAddress
          fetchData={fetchData}
          authData={authData}
          dbAddress={dbAddress}
        />
      );
    } else if (selectedTab === "my-products") {
      return (
        <MyProducts
          userProducts={userProducts}
          setUserProducts={setUserProducts}
          setProducts={setProducts}
          authData={authData}
        />
      );
    } else if (selectedTab === "my-orders") {
      return <MyOrdersPage myOrders={myOrders} />;
    } else if (selectedTab === "selling") {
      return <Selling mySold={mySold} />;
    } else if (selectedTab === "change-name") {
      return (
        <ChangeName dbName={dbName} authData={authData} fetchName={fetchName} />
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
