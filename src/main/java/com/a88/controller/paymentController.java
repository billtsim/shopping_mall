package com.a88.controller;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class paymentController {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> data) {
        Stripe.apiKey = stripeApiKey;

        Map<String, String> responseData = new HashMap<>();
        try {

            Map<String, String> product = (Map<String, String>) data.get("product");
            String productName = product.get("name");
            String productImage = product.get("image");

            long amountInCents;

            if (data.get("amount") instanceof Double) {
                double amount = (double) data.get("amount");
                amountInCents = Math.round(amount * 100);
            } else if (data.get("amount") instanceof Integer) {
                int amount = (int) data.get("amount");
                amountInCents = amount * 100L;
            } else {
                throw new IllegalArgumentException("Amount must be an integer or a double");
            }

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setReturnUrl("http://localhost:5173/payment-result"+"?session_id={CHECKOUT_SESSION_ID}")
                            .setAutomaticTax(
                                    SessionCreateParams.AutomaticTax.builder()
                                            .setEnabled(true)
                                            .build())
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency("hkd")
                                                            .setUnitAmount(amountInCents) // Amount in cents
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName(productName)
                                                                            .addImage(productImage)
                                                                            .build())
                                                            .build())
                                            .setQuantity(1L)
                                            .build())
                            .build();

            Session session = Session.create(params);
            responseData.put("clientSecret", session.getRawJsonObject().getAsJsonPrimitive("client_secret").getAsString());

        } catch (StripeException e) {
            e.printStackTrace();
            responseData.put("error", e.getMessage());
        }

        return responseData;
    }
    @GetMapping("/status/{sessionId}")
    public Map<String, String> getSessionStatus(@PathVariable String sessionId) {
        Stripe.apiKey = stripeApiKey;

        Map<String, String> responseData = new HashMap<>();
        try {
            Session session = Session.retrieve(sessionId);
            responseData.put("status", session.getStatus());
        } catch (StripeException e) {
            e.printStackTrace();
            responseData.put("error", e.getMessage());
        }

        return responseData;
    }
}
