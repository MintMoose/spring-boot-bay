import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import SellerCard from "../../components/order/SellerCard";

function Selling({ mySold }) {
  return (
    <Container>
      <Row>
        <Col>
          <h1>Sold</h1>
          <div className="selling-list">
            {mySold && mySold.length > 0 ? (
              mySold.map((sold) => <SellerCard sold={sold} />)
            ) : (
              <p>You have something to sell? We have 1.35 million buyers!</p>
            )}
          </div>
        </Col>
      </Row>
    </Container>
  );
}

export default Selling;
