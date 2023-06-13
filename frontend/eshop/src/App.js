import logo from "./logo.svg";
import "./App.css";
import api from "./api/axiosConfig";
import { useState, useEffect } from "react";
import Layout from "./components/Layout/Layout";
import { Routes, Route } from "react-router-dom";
import Home from "./components/home/Home";
import RegisterForm from "./components/submitForm/registerForm";
import NotFoundPage from "./components/notFoundPage";
import LoginForm from "./components/submitForm/loginForm";
import Products from "./components/product/Products";
import Cookies from "js-cookie";
import Profile from "./components/Profile";

function App() {
  const [products, setProducts] = useState();
  const [authData, setAuthData] = useState({ isLoggedIn: false, username: "" });

  const getProducts = async () => {
    try {
      // todo: check http status code
      const response = await api.get("/products");
      console.log(response.data);
      setProducts(response.data);
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

  return (
    <div>
      <Routes>
        <Route path="/" element={<Layout authData={authData} />}>
          <Route
            path="/"
            element={<Home products={products} authData={authData} />}
          ></Route>
          <Route
            path="/login"
            element={
              <LoginForm setAuthData={setAuthData} authData={authData} />
            }
          />
          <Route path="/register" element={<RegisterForm />} />
          <Route path="/products" element={<Products />} />
          <Route path="/profile" element={<Profile authData={authData} />} />
          <Route path="/*" element={<NotFoundPage />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
