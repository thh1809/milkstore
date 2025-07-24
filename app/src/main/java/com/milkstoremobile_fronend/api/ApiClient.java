package com.milkstoremobile_fronend.api;

import com.milkstoremobile_fronend.api.services.AiApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://10.0.2.2:8081/api/";

    private static Retrofit retrofit;

    private static OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)  // Kết nối chờ tối đa 60s
                .readTimeout(60, TimeUnit.SECONDS)     // Đọc dữ liệu tối đa 60s
                .writeTimeout(60, TimeUnit.SECONDS)    // Gửi dữ liệu tối đa 60s
                .build();
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient()) // Dùng client có timeout
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static AiApiService getAiApiService() {
        return getClient().create(AiApiService.class);
    }
}
