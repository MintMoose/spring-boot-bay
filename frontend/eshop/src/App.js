import "./App.css";
import api from "./api/axiosConfig";
import { useState, useEffect, useCallback } from "react";
import Layout from "./components/Layout/Layout";
import { Routes, Route } from "react-router-dom";
import Home from "./components/home/Home";
import RegisterForm from "./components/submitForm/registerForm";
import NotFoundPage from "./components/notFoundPage";
import LoginForm from "./components/submitForm/loginForm";
import Products from "./components/product/Products";
import Cookies from "js-cookie";
import Profile from "./profilePage/Profile";
import ProductDetails from "./components/product/ProductDetails";
import Legal from "./components/legal";

function App() {
  const [products, setProducts] = useState();
  const [authData, setAuthData] = useState({ isLoggedIn: false, username: "" });
  const [userProducts, setUserProducts] = useState();

  const getProducts = async () => {
    try {
      const response = await api.get("/open/products/sale");
      if (response.status === 200) {
        console.log(response.data.content);
        setProducts(response.data.content);
      } else {
        console.log("Request failed with status code:", response.status);
      }
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    getProducts();
    const jwtCookie = Cookies.get("jwt");
    const usernameCookie = Cookies.get("username");
    if (jwtCookie) {
      console.log(jwtCookie);
      setAuthData((prevAuthData) => ({
        ...prevAuthData,
        isLoggedIn: true,
      }));
    }
    if (usernameCookie) {
      console.log(usernameCookie);
      setAuthData((prevAuthData) => ({
        ...prevAuthData,
        username: usernameCookie,
      }));
    }
  }, []);

  const getUserProducts = useCallback(async () => {
    try {
      const response = await api.get(`/products/${authData.username}`);
      if (response.status === 200) {
        setUserProducts(response.data.content);
      } else {
        // Handle other status codes here
        console.log("Request failed with status code:", response.status);
      }
    } catch (err) {
      console.log(err);
    }
  }, [authData.username]);

  useEffect(() => {
    if (authData.username) {
      getUserProducts();
    }
  }, [authData.username, getUserProducts]);

  return (
    <div>
      <Routes>
        <Route
          path="/"
          element={
            <Layout
              authData={authData}
              setAuthData={setAuthData}
              setUserProducts={setUserProducts}
              setProducts={setProducts}
            />
          }
        >
          <Route
            path="/"
            element={
              <Home
                products={products}
                userProducts={userProducts}
                authData={authData}
              />
            }
          ></Route>
          <Route
            path="/login"
            element={
              <LoginForm setAuthData={setAuthData} authData={authData} />
            }
          />
          <Route
            path="/register"
            element={<RegisterForm setAuthData={setAuthData} />}
          />
          <Route
            path="/products"
            element={<Products username={authData.username} />}
          />
          <Route
            path="/profile"
            element={
              <Profile
                userProducts={userProducts}
                authData={authData}
                setUserProducts={setUserProducts}
                setProducts={setProducts}
              />
            }
          />
          <Route
            path="/product/:product_id"
            element={<ProductDetails authData={authData} />}
          />
          <Route path="/legal" element={<Legal />} />
          <Route path="/*" element={<NotFoundPage />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
