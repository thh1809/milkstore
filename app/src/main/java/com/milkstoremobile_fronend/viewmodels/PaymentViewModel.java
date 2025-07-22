package com.milkstoremobile_fronend.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.milkstoremobile_fronend.api.ApiClient;
import com.milkstoremobile_fronend.api.services.PaymentApiService;
import com.milkstoremobile_fronend.models.payment.PaymentRequest;
import com.milkstoremobile_fronend.models.payment.PaymentResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentViewModel extends ViewModel {
    private final MutableLiveData<PaymentResponse> paymentResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final PaymentApiService apiService = ApiClient.getPaymentApiService();

    public LiveData<PaymentResponse> getPaymentResult() { return paymentResult; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }

    public void checkout(PaymentRequest paymentRequest) {
        if (paymentRequest == null) return;
        isLoading.postValue(true);
        apiService.checkout(paymentRequest).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                isLoading.postValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    paymentResult.postValue(response.body());
                } else {
                    paymentResult.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                isLoading.postValue(false);
                paymentResult.postValue(null);
            }
        });
    }
}
