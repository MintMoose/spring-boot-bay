import React, { useState } from "react";
import { Container, Row, Col, Form, Button } from "react-bootstrap";
import api from "../../api/axiosConfig";

const ChangeName = ({ dbName, fetchName, authData }) => {
  const [name, setName] = useState("");
  const [nameChange, setNameChange] = useState("");
  const [nameError, setNameError] = useState("");

  const handleChangeNameSubmit = (e) => {
    e.preventDefault();

    const data = {
      name,
    };

    api
      .put(`/customers/${authData.username}`, data)
      .then((response) => {
        setNameChange(response.data.name);
        fetchName();
      })
      .catch((error) => {
        console.error("Name change failed:", error);
        setNameError("Invalid: name changed failed!");
      });
    setTimeout(() => {
      setNameChange(false);
      setNameError(false);
    }, 6000);
  };

  return (
    <Container className="second-back">
      <Row>
        <Col>
          {dbName && (
            <div className="current-address-name">
              <h3>Current Name:</h3>
              <p>{dbName}</p>
            </div>
          )}
          <h1>Change Name</h1>
          {
            <h3
              className={`name-change-success ${
                nameChange ? "visible" : "hidden"
              }`}
            >
              Name successfully changed
            </h3>
          }
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
            {nameChange && (
              <p className="name-feedback">Name Successfully Changed</p>
            )}
            {nameError && (
              <p className="name-feedback">Name Change Failed: {nameError}</p>
            )}
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default ChangeName;
