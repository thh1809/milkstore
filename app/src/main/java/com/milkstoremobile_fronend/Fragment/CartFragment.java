package com.milkstoremobile_fronend.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.milkstoremobile_fronend.R;

public class CartFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        Button btnCheckout = view.findViewById(R.id.btnCheckout);

        btnCheckout.setOnClickListener(v -> {
            // TODO: Lấy thông tin orderId, amount thực tế từ giỏ hàng
            String orderId = "ORDER123"; // Thay bằng dữ liệu thật
            double amount = 100000;      // Thay bằng tổng tiền thực tế

            // Vì không dùng PaymentActivity, nên chỉ hiển thị thông báo
            Toast.makeText(getContext(), "Đang xử lý đơn hàng " + orderId + " - " + amount + " VND", Toast.LENGTH_SHORT).show();

            // Nếu sau này cần mở trang thanh toán hoặc API, thêm ở đây
        });

        return view;
    }
}
