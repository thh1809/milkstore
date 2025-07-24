package com.milkstoremobile_fronend.api.services;

import com.milkstoremobile_fronend.models.message.MessageAIRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AiApiService {
    @POST("/api/ai/recommend-milk")
    Call<String> recommendMilk(@Body MessageAIRequest request);
}
