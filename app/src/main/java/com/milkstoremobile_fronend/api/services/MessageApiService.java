package com.milkstoremobile_fronend.api.services;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;
import com.milkstoremobile_fronend.models.message.MessageRequest;
import com.milkstoremobile_fronend.models.message.MessageResponse;

public interface MessageApiService {
    @POST("/api/messages")
    Call<MessageResponse> sendMessage(@Body MessageRequest messageRequest);

    @GET("/api/messages")
    Call<List<MessageResponse>> getMessages(
            @Query("userId1") String userId1,
            @Query("userId2") String userId2
    );
}
