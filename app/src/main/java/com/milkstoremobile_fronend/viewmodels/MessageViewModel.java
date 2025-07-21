package com.milkstoremobile_fronend.viewmodels;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.milkstoremobile_fronend.api.ApiClient;
import com.milkstoremobile_fronend.api.services.MessageApiService;
import com.milkstoremobile_fronend.models.message.MessageRequest;
import com.milkstoremobile_fronend.models.message.MessageResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends ViewModel {
    private final MutableLiveData<List<MessageResponse>> messages = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MessageApiService apiService = ApiClient.getMessageApiService();

    public LiveData<List<MessageResponse>> getMessages() {
        return messages;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    // KHÔNG phân trang, chỉ truyền userId và adminId
    public void fetchMessages(String userId, String adminId) {
        if (userId == null || userId.trim().isEmpty() || adminId == null || adminId.trim().isEmpty()) {
            messages.postValue(null);
            return;
        }

        isLoading.postValue(true);
        Log.d("Chat", "Gọi API lấy tin nhắn với userId: " + userId + ", adminId: " + adminId);
        apiService.getMessages(userId, adminId).enqueue(new Callback<List<MessageResponse>>() {
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                Log.d("Chat", "API lấy tin nhắn: " + response.body());
                isLoading.postValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    messages.postValue(response.body());
                } else {
                    messages.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<MessageResponse>> call, Throwable t) {
                Log.e("Chat", "Lỗi lấy tin nhắn: " + t.getMessage());
                isLoading.postValue(false);
                t.printStackTrace();
                messages.postValue(null);
            }
        });
    }

    public void sendMessage(MessageRequest messageRequest, Runnable onSuccess) {
        if (messageRequest == null) {
            return;
        }

        isLoading.postValue(true);
        apiService.sendMessage(messageRequest).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                isLoading.postValue(false);
                if (response.isSuccessful()) {
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                isLoading.postValue(false);
                t.printStackTrace();
            }
        });
    }
}