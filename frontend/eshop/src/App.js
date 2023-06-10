import logo from "./logo.svg";
import "./App.css";
import api from "./api/axiosConfig";
import { useState, useEffect } from "react";
import Layout from "./components/Layout";
import { Routes, Route } from "react-router-dom";
import Home from "./components/home/Home";
import RegisterForm from "./components/submitForm/registerForm";
import NotFoundPage from "./components/notFoundPage";
import LoginForm from "./components/submitForm/loginForm";
import Products from "./components/product/Products";

function App() {
  const [products, setProducts] = useState();
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const getProducts = async () => {
    try {
      // todo: check http status code
      const response = await api.get("/open/products");
      console.log(response.data);
      setProducts(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    getProducts();
  }, []);

  return (
    <div>
      <Routes>
        <Route path="/" element={<Layout isLoggedIn={isLoggedIn} />}>
          <Route
            path="/"
            element={<Home products={products} isLoggedIn={isLoggedIn} />}
          ></Route>
          <Route
            path="/login"
            element={
              <LoginForm
                setIsLoggedIn={setIsLoggedIn}
                isLoggedIn={isLoggedIn}
              />
            }
          />
          <Route path="/register" element={<RegisterForm />} />
          <Route path="/products" element={<Products />} />
          <Route path="/*" element={<NotFoundPage />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
