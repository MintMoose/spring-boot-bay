import React from "react";
import ProductCard from "./product/ProductCard";
import "./Profile.css";
function Profile({ authData, userProducts }) {
  return (
    <div className="profile-container">
      <ul class="nav flex-column">
        <li class="nav-item">
          <a class="nav-link active" href="#">
            Active
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">
            Link
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">
            Link
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link disabled" href="#">
            Disabled
          </a>
        </li>
      </ul>
      <div className="profile-inner">
        <h1> {authData.username}</h1>
        <div>
          <h3>Information:</h3>
        </div>

        <div>
          <h3>My Products:</h3>
          <div className="product-list2">
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
        </div>
      </div>
    </div>
  );
}

export default Profile;
