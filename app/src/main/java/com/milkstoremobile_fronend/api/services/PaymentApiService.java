package com.milkstoremobile_fronend.api.services;

import com.milkstoremobile_fronend.models.payment.PaymentRequest;
import com.milkstoremobile_fronend.models.payment.PaymentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentApiService {
    @POST("/api/payment/checkout")
    Call<PaymentResponse> checkout(@Body PaymentRequest paymentRequest);
}
