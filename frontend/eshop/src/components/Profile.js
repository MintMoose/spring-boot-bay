import React from "react";
import api from "../api/axiosConfig";
import { useState, useEffect } from "react";

function Profile({ authData }) {
  const [customer, setCustomer] = useState();
  const fetchCustomer = async () => {
    try {
      const response = await api.get(`/customers/${authData.username}`);
      setCustomer(response.data.username);
      console.log(response.data);
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  useEffect(() => {
    if (authData.username) {
      fetchCustomer();
    }
  }, [authData.username]);

  return <div>Profile, {customer}</div>;
}

export default Profile;
