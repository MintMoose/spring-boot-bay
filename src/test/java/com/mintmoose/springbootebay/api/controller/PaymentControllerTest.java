package com.mintmoose.springbootebay.api.controller;

import com.mintmoose.springbootebay.Controller.PaymentController;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private ProductService productService;

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @Test
    public void testCreateCheckoutSessionWithProductId() {
        // Mock the product and its price
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setSold(false);
        product.setPrice(25.0);

        when(productService.getProductById(productId)).thenReturn(product);

        // Perform the test
        RedirectView response = paymentController.createCheckoutSession(productId);

        // Assert the response
        assertNotNull(response);
    }

    @Test
    public void testCreateCheckoutSessionWithNullProductId() {
        // Perform the test with null product ID
        RedirectView response = paymentController.createCheckoutSession(null);

        // Assert the response
        assertNotNull(response);
    }

    @Test
    public void testCreateCheckoutSessionWithUnavailableProduct() {
        // Mock the product and its availability
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setSold(true);

        when(productService.getProductById(productId)).thenReturn(product);

        // Perform the test
        RedirectView response = paymentController.createCheckoutSession(productId);

        // Assert the response
        assertNotNull(response);
    }

    // more assertions instead of just assertNotNull.

    // more test cases for Stripe session creation scenarios
}
