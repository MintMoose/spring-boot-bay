import React from "react";
import "./Hero.css";
import Carousel from "react-material-ui-carousel";
import { Paper } from "@mui/material";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "../ProductCard";
import ProductCard from "../ProductCard";
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

const Hero = ({ products }) => {
  return (
    <div className="product-carousel-container">
      {/* <Carousel>
        {products &&
          products.map((product) => {
            return (
              <Paper className="caro">
                <div className="product-card-container">
                  <div className="product-card">
                    <div className="product-image">
                      <img
                        className="center-cropped"
                        src={product.imageUrl}
                        alt={product.name}
                      />
                    </div>
                    <div className="product-title">
                      <h4>{product.name}</h4>
                    </div>
                  </div>
                </div>
              </Paper>
            );
          })}
      </Carousel> */}
      <Slider {...settings}>
        {products &&
          products.map((product) => {
            return (
              // <div className="product-card-container">
              //   <div className="product-card">
              //     <div className="product-image">
              //       <img
              //         className="center-cropped"
              //         src={product.imageUrl}
              //         alt={product.name}
              //       />
              //     </div>
              //     <div className="product-title">
              //       <h4>{product.name}</h4>
              //     </div>
              //   </div>
              // </div>
              <ProductCard product={product} />
            );
          })}
      </Slider>
    </div>
  );
};

export default Hero;
