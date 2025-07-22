package com.milkstoremobile_fronend.views;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.payment.PaymentRequest;
import com.milkstoremobile_fronend.viewmodels.PaymentViewModel;

public class PaymentActivity extends AppCompatActivity {
    private TextView tvAmount, tvOrderId, tvResult;
    private Spinner spinnerMethod;
    private Button btnPay, btnBackHome;
    private ProgressBar progressBar;
    private PaymentViewModel paymentViewModel;
    private double amount;
    private String orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        tvOrderId = findViewById(R.id.tvOrderId);
        tvAmount = findViewById(R.id.tvAmount);
        spinnerMethod = findViewById(R.id.spinnerMethod);
        btnPay = findViewById(R.id.btnPay);
        progressBar = findViewById(R.id.progressBar);
        tvResult = findViewById(R.id.tvResult);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Nhận dữ liệu từ Intent
        orderId = getIntent().getStringExtra("orderId");
        amount = getIntent().getDoubleExtra("amount", 0);

        tvOrderId.setText("Mã đơn hàng: " + (orderId != null ? orderId : ""));
        tvAmount.setText("Số tiền: " + amount + " đ");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"credit_card", "momo", "cash"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMethod.setAdapter(adapter);

        paymentViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);

        paymentViewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnPay.setEnabled(!isLoading);
        });

        paymentViewModel.getPaymentResult().observe(this, paymentResponse -> {
            if (paymentResponse != null && paymentResponse.isSuccess()) {
                tvResult.setText("Thanh toán thành công! Mã giao dịch: " + paymentResponse.getTransactionId());
                tvResult.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                btnBackHome.setVisibility(View.VISIBLE);
            } else {
                tvResult.setText("Thanh toán thất bại!");
                tvResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                btnBackHome.setVisibility(View.GONE);
            }
        });

        btnBackHome.setOnClickListener(v -> {
            // Quay lại MainActivity (hoặc màn hình chính tuỳ app)
            finishAffinity();
        });

        btnPay.setOnClickListener(v -> {
            String userId = "user1"; // Lấy userId thực tế từ session hoặc intent
            String method = spinnerMethod.getSelectedItem().toString();
            if (userId.isEmpty() || orderId == null || amount == 0) {
                Toast.makeText(this, "Thiếu thông tin thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }
            PaymentRequest request = new PaymentRequest(userId, amount, method, orderId);
            paymentViewModel.checkout(request);
        });
    }
}
