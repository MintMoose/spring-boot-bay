package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.ProductService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = {"http://localhost:3000", "https://checkout.stripe.com/"})
public class PaymentController {

    private final ProductService productService;
    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    public PaymentController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/create-checkout-session")
        public RedirectView createCheckoutSession(@RequestParam("productId") Long productId) {
        if (productId == null) {
            // Return an error response indicating that the product ID is missing
            return new RedirectView("http://localhost:3000/payment/checkout?error=Product id is null");
        }
        Product product = productService.getProductById(productId);
        if (product == null || product.getSold()) {
            // Return an error response indicating that the product is not available
            return new RedirectView("http://localhost:3000/payment/checkout?error=Product is not available");
        }
        try {
            Stripe.apiKey = stripeApiKey;

            String YOUR_DOMAIN = String.format("http://localhost:3000/payment/checkout/%s", productId);

            // Convert the price to cents using a constant conversion factor
            double priceInGBP = product.getPrice();
            long priceInCents = (long) (priceInGBP * 100);

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(YOUR_DOMAIN + "?success=true")
                    .setCancelUrl(YOUR_DOMAIN + "?canceled=true")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("gbp")
                                                    .setUnitAmount(priceInCents)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(product.getName())
                                                                    .build())
                                                    .build())
                                    .build())
                    .build();

            Session session = Session.create(params);
            product.setPaymentId(session.getPaymentIntent());
            productService.updateProductDirectly(product);

            return new RedirectView(session.getUrl());
        } catch (StripeException e) {
            e.printStackTrace();
            // Return an error response indicating a problem with the Stripe API
            return new RedirectView("http://localhost:3000/payment/checkout?error=Error has occurred");
        }
        }
}
