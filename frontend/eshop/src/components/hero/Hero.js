import React from "react";
import "./Hero.css";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "../product/ProductCard";
import ProductCard from "../product/ProductCard";
import "./slick.css";
import "./slick-theme.css";

const settings = {
  dots: true,
  infinite: true,
  speed: 300,
  slidesToShow: 3,
  slidesToScroll: 2,
  responsive: [
    {
      breakpoint: 1100,
      settings: {
        slidesToShow: 2,
        slidesToScroll: 2,
        initialSlide: 2,
      },
    },
    {
      breakpoint: 600,
      settings: {
        slidesToShow: 1,
        slidesToScroll: 1,
      },
    },
  ],
};

const Hero = ({ products, username, setUserProducts, setProducts }) => {
  return (
    <div className="product-carousel-container">
      <Slider {...settings}>
        {products &&
          products.map((product) => {
            return (
              <ProductCard
                key={product.id}
                product={product}
                username={username}
                size="large"
                setUserProducts={setUserProducts}
                setProducts={setProducts}
              />
            );
          })}
      </Slider>
    </div>
  );
};

export default Hero;
