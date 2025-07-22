package com.milkstoremobile_fronend.models.payment;

public class PaymentRequest {
    private String userId;
    private double amount;
    private String method; // e.g., "credit_card", "momo", "cash"
    private String orderId;

    public PaymentRequest(String userId, double amount, String method, String orderId) {
        this.userId = userId;
        this.amount = amount;
        this.method = method;
        this.orderId = orderId;
    }

    public String getUserId() { return userId; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public String getOrderId() { return orderId; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setMethod(String method) { this.method = method; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
}
