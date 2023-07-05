import React, { useState, useEffect } from "react";
import { Container, Row, Col, Form, Button } from "react-bootstrap";
import Select from "react-select";
import countryOptions from "../../components/countryOptions";
import api from "../../api/axiosConfig";

function UpdateAddress({ fetchData, authData, dbAddress }) {
  const [buildingNumber, setBuildingNumber] = useState("");
  const [street, setStreet] = useState("");
  const [city, setCity] = useState("");
  const [country, setCountry] = useState("");
  const [postcode, setPostcode] = useState("");
  const [addressChange, setAddressChange] = useState("");
  const [addressError, setAddressError] = useState("");

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    let data = {};

    if (country === "Select your country") {
      data = {
        buildingNumber,
        street,
        city,
        country: "",
        postcode,
      };
    } else {
      data = {
        buildingNumber,
        street,
        city,
        country,
        postcode,
      };
    }

    try {
      const response = await api.get(`/address/user/${authData.username}`);
      if (response.data.id) {
        // Address exists, make an update request
        updateAddressRequest(data);
      } else {
        // Address does not exist, make a create request
        console.log(data);
        createAddressRequest(data);
      }
    } catch (error) {
      console.error("Failed to fetch profile:", error);
      // Handle error
    }

    try {
      const response = await api.get(`/address/user/${authData.username}`);
      if (response.data.id) {
        // Address exists, make an update request
        updateAddressRequest(data);
      } else {
        // Address does not exist, make a create request
        console.log(data);
        createAddressRequest(data);
      }
    } catch (error) {
      console.error("Failed to fetch profile:", error);
      // Handle error
    }
    setTimeout(() => {
      setAddressChange();
      setAddressError();
    }, 6000);
  };

  const createAddressRequest = (data) => {
    api
      .post(`/address/${authData.username}`, data)
      .then(() => {
        console.log("Address create successful");
        setAddressChange("Successfully created an Address");
      })
      .catch((error) => {
        console.error("Address create failed:", error);
        setAddressError("Address creation failed, fill address information.");
      });
  };

  const updateAddressRequest = (data) => {
    api
      .put(`/address/${authData.username}`, data)
      .then((response) => {
        console.log("Address update successful: " + response);
        setAddressChange("Sucessfully updated the Address");
        fetchData(); // Fetch updated address after successful update
      })
      .catch((error) => {
        console.error("Address update failed:", error);
        setAddressError("Updating address failed!");
      });
  };

  return (
    <Container className="second-back">
      <Row>
        <Col>
          {dbAddress && (
            <div className="current-address-name">
              <h3>Current Address:</h3>
              <p>
                {dbAddress.buildingNumber}, {dbAddress.street}
              </p>
              <p>
                {dbAddress.city}, {dbAddress.country}
              </p>
              <p>{dbAddress.postcode}</p>
            </div>
          )}
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

              {/* <Form.Group controlId="country" className="mb-3">
                    <Form.Label>Country</Form.Label>
                    <Form.Control
                      type="text"
                      value={country}
                      onChange={(e) => setCountry(e.target.value)}
                      placeholder="Enter your country"
                    />
                  </Form.Group> */}

              <Form.Group controlId="country" className="mb-3">
                <Form.Label>Country</Form.Label>
                <Select
                  options={countryOptions} // An array of country options
                  value={{ value: country, label: country }} // The selected country value
                  onChange={(selectedOption) =>
                    setCountry(selectedOption.label)
                  } // Update the selected country
                  placeholder="Select your country"
                />
              </Form.Group>

              <Form.Group controlId="zipcode" className="mb-3">
                <Form.Label>Post Code</Form.Label>
                <Form.Control
                  type="text"
                  value={postcode}
                  onChange={(e) => setPostcode(e.target.value)}
                  placeholder="Enter your ZIP code"
                />
              </Form.Group>

              <Button variant="primary" type="submit" className="mt-2">
                Update Profile
              </Button>
            </Form>
            {addressChange && (
              <p className="address-feedback">Address Successfully Changed</p>
            )}
            {addressError && (
              <p className="address-feedback">Address Failed: {addressError}</p>
            )}
          </div>
        </Col>
      </Row>
    </Container>
  );
}

export default UpdateAddress;
