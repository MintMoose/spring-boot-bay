package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.ProductService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

@RestController
@RequestMapping("/webhooks/stripe")
public class StripeWebhookController {

    private final ProductService productService;

    @Value("${stripe.endpointSecret}")
    private String endpointSecret;

    public StripeWebhookController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, signature, endpointSecret);
        }  catch (SignatureVerificationException e) {
            // Invalid signature
            return ResponseEntity.badRequest().build();
        }
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                // Deserialization failed, probably due to an API version mismatch.
                // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
                // instructions on how to handle this case, or return an error here.
                throw new IllegalArgumentException("Deserialization failed");
            }
        if ("payment_intent.succeeded".equals(event.getType())) {// Then define and call a function to handle the event payment_intent.succeeded
            String paymentIntentId = stripeObject.toJson();
            System.out.println("Payment Intent Id;" + paymentIntentId);

            Product product = productService.getProductByPaymentIntentId(paymentIntentId);

            product.setSold(true);
            productService.updateProductDirectly(product);
        } else {
            System.out.println("Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok("Success");
    }
}
