import React from "react";
import "./Hero.css";
import Carousel from "react-material-ui-carousel";
import { Paper } from "@mui/material";
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
  speed: 500,
  slidesToShow: 3,
  slidesToScroll: 1,
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

const Hero = ({ products, username }) => {
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
              />
            );
          })}
      </Slider>
    </div>
  );
};

export default Hero;
