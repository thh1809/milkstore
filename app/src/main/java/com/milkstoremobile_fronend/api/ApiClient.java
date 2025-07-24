package com.milkstoremobile_fronend.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import com.milkstoremobile_fronend.api.services.MessageApiService;
import com.milkstoremobile_fronend.api.services.PaymentApiService;


public class ApiClient {
    private static Retrofit retrofit;

    // ✅ URL chuẩn có kết thúc bằng "/"
    private static final String BASE_URL = "http://10.0.2.2:8081/api/";

    // ✅ Retrofit instance dùng chung
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // ✅ Service cho Message
    public static MessageApiService getMessageApiService() {
        return getClient().create(MessageApiService.class);
    }

    // ✅ Service cho Payment
    public static PaymentApiService getPaymentApiService() {
        return getClient().create(PaymentApiService.class);
    }


}
