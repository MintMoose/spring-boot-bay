import React from "react";
import "./Hero.css";
import Carousel from "react-material-ui-carousel";
import { Paper } from "@mui/material";

const Hero = ({ products }) => {
  return (
    <div className="product-carousel-container">
      <Carousel>
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
      </Carousel>
    </div>
  );
};

export default Hero;
