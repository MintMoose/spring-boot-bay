import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import OrderCard from "../../components/order/OrderCard";

function MyOrders({ myOrders }) {
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
}

export default MyOrders;
