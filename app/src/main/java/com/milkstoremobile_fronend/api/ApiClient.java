package com.milkstoremobile_fronend.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import com.milkstoremobile_fronend.api.services.MessageApiService;


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
}

//public class ApiClient {
//    private static Retrofit retrofit = null;
//
//    public static Retrofit getClient() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://10.0.2.2:8081/") // Nếu chạy trên emulator
//                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(g)
//                    .build();
//        }
//        return retrofit;
//    }
//
//    public static MessageApiService getMessageApiService() {
//        return getClient().create(MessageApiService.class);
//    }
//}
//public class ApiClient {
//    private static Retrofit retrofit;
//    // Đúng: base URL phải kết thúc bằng dấu "/"
//    private static final String BASE_URL = "http://10.0.2.2:8081/api/";
//
//    // Hàm này trả về instance Retrofit dùng chung cho mọi service
//    public static Retrofit getClient() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }
//
//    // Service cho Message
//    public static MessageApiService getMessageApiService() {
//        return getClient().create(MessageApiService.class);
//    }
//
//    // Nếu có các service khác, bạn cũng tạo hàm tương tự:
//    // public static ProductService getProductService() { ... }
//}