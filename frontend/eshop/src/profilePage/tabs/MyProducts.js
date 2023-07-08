import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import ProductCard from "../../components/product/ProductCard";

function MyProducts({ userProducts, setUserProducts, setProducts, authData }) {
  const navigate = useNavigate();

  const handleCreateProduct = () => {
    navigate("/product/create");
  };

  return (
    <Container>
      <Row>
        <Col>
          <div className="create-product-div">
            <button onClick={handleCreateProduct}>Create Product</button>
          </div>
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
