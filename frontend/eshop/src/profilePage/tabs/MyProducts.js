import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import ProductCard from "../../components/product/ProductCard";

function MyProducts({ userProducts, setUserProducts, setProducts, authData }) {
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
                  setUserProducts={setUserProducts}
                  setProducts={setProducts}
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
}

export default MyProducts;
