package com.milkstoremobile_fronend.models.payment;

public class PaymentResponse {
    private boolean success;
    private String message;
    private String transactionId;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getTransactionId() { return transactionId; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
}
