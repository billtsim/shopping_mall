package com.a88.controller;


import com.a88.Pojo.gameLibrary;
import com.a88.Pojo.orderDetail;
import com.a88.service.inter.cartService;
import com.a88.service.inter.gameLibraryService;
import com.a88.service.inter.orderService;
import com.a88.service.inter.productService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.LineItem;
import com.stripe.model.LineItemCollection;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionListLineItemsParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class paymentController {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Autowired
    private orderService OS;

    @Autowired
    private productService PS;

    @Autowired
    private cartService CS;

    @Autowired
    private gameLibraryService GLS;

    static Map<String, String> cartOrNot = new HashMap<>();

    @PostMapping("/create-checkout-session/{cart}")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> data,
                                                     @PathVariable String cart) {
        Stripe.apiKey = stripeApiKey;

        Map<String, String> responseData = new HashMap<>();
        System.out.println(cart);
        try {
            List<Map<String, Object>> products = (List<Map<String, Object>>) data.get("products");
//            Map<String, String> product = (Map<String, String>) data.get("product");
//            String productName = product.get("name");
//            String productImage = product.get("image");
//
//            long amountInCents;
//
//            if (data.get("amount") instanceof Double) {
//                double amount = (double) data.get("amount");
//                amountInCents = Math.round(amount * 100);
//            } else if (data.get("amount") instanceof Integer) {
//                int amount = (int) data.get("amount");
//                amountInCents = amount * 100L;
//            } else {
//                throw new IllegalArgumentException("Amount must be an integer or a double");
//            }

            List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

            for (Map<String, Object> product : products) {
                String productName = (String) product.get("name");
                String productImage = (String) product.get("image");
                long amountInCents;
                if (product.get("amount") instanceof Double) {
                double amount = (double) product.get("amount");
                amountInCents = Math.round(amount * 100);
            } else if (product.get("amount") instanceof Integer) {
                int amount = (int) product.get("amount");
                amountInCents = amount * 100L;
            } else {
                throw new IllegalArgumentException("Amount must be an integer or a double");
            }
                lineItems.add(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("hkd")
                                                .setUnitAmount(amountInCents)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(productName)
                                                                .addImage(productImage)
                                                                .build())
                                                .build())
                                .setQuantity(1L)
                                .build()
                );
            }

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setReturnUrl("http://192.168.1.83:5173/payment-result"+"?session_id={CHECKOUT_SESSION_ID}")
                            .setAutomaticTax(
                                    SessionCreateParams.AutomaticTax.builder()
                                            .setEnabled(true)
                                            .build())
                            .addAllLineItem(lineItems)
                            .build();

            Session session = Session.create(params);
            System.out.println(cart);
            responseData.put("clientSecret", session.getRawJsonObject().getAsJsonPrimitive("client_secret").getAsString());
            cartOrNot.put(session.getId(), cart);
        } catch (StripeException e) {
            e.printStackTrace();
            responseData.put("error", e.getMessage());
        }

        return responseData;
    }
    @PostMapping("/status")
    public Map<String, String> getSessionStatus(@RequestBody Map<String, Object> data) {
        Stripe.apiKey = stripeApiKey;

        String sessionId = (String) data.get("sessionId");
        String userIdStr = (String) data.get("userId");  // Changed to String and will parse to Integer
        Integer userId = null;

        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid user ID format.");
            return errorResponse;
        }

        Map<String, String> responseData = new HashMap<>();
        try {
            Session session = Session.retrieve(sessionId);
            if (session.getStatus().equals("complete")) {

                orderDetail OD = new orderDetail();
                OD.setUserId(userId);
                OD.setOrderNo(session.getId());
                OD.setTotalPrice((session.getAmountTotal() / 100.0));
                OD.setReceiverName(session.getCustomerDetails().getName());
                OD.setReceiverAddress(session.getCustomerDetails().getEmail());
                OD.setOrderStatus("successful");
                OD.setQuantity(1);
                // Get payment intent ID from session
                String paymentIntentId = session.getPaymentIntent();
                PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

                // Get payment method ID from payment intent
                String paymentMethodId = paymentIntent.getPaymentMethod();
                PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);

                // Get payment method type
                String paymentMethodType = paymentMethod.getType();

                OD.setPaymentType(paymentMethodType);
                OD.setPayTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(session.getCreated()), ZoneId.systemDefault()));

                SessionListLineItemsParams params = SessionListLineItemsParams.builder()
                        .setLimit(100L) // Adjust the limit as needed
                        .build();
                LineItemCollection lineItemCollection = session.listLineItems(params);
                System.out.println(session.getId());
                System.out.println(cartOrNot.get(session.getId()));
                if (cartOrNot.get(session.getId()).equals("cart"))  {
                    Integer finalUserId = userId;
                    lineItemCollection.getData().forEach(lineItem -> {
                        Integer productId = PS.getProductByName(lineItem.getDescription());
                        OD.setProductId(productId);
                        CS.removeFromCart(finalUserId, productId);
                        OS.addOrder(OD);
                        gameLibrary gl = new gameLibrary();
                        gl.setProductId(productId);
                        gl.setUserId(finalUserId);
                        GLS.addGameLibrary(gl);
                    });
                } else {
                    Integer finalUserId1 = userId;
                    lineItemCollection.getData().forEach(lineItem -> {
                        Integer productId = PS.getProductByName(lineItem.getDescription());
                        OD.setProductId(productId);
                        OS.addOrder(OD);
                        gameLibrary gl = new gameLibrary();
                        gl.setProductId(productId);
                        gl.setUserId(finalUserId1);
                        GLS.addGameLibrary(gl);
                    });
                }
                cartOrNot.remove(session.getId());
            }
            responseData.put("status", session.getStatus());
        } catch (StripeException e) {
            e.printStackTrace();
            responseData.put("error", e.getMessage());
        }

        return responseData;
    }


}
