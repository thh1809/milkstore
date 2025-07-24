//package com.milkstoremobile_fronend.Fragment;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.milkstoremobile_fronend.R;
//
//public class CartFragment extends Fragment {
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_cart, container, false);
//
//        Button btnCheckout = view.findViewById(R.id.btnCheckout);
//
//        btnCheckout.setOnClickListener(v -> {
//            // TODO: Lấy thông tin orderId, amount thực tế từ giỏ hàng
//            String orderId = "ORDER123"; // Lấy orderId thực tế từ dữ liệu giỏ hàng
//            double amount = 100000; // Lấy tổng tiền thực tế từ dữ liệu giỏ hàng
////            Intent intent = new Intent(getActivity(), PaymentActivity.class);
////            intent.putExtra("orderId", orderId);
////            intent.putExtra("amount", amount);
////            startActivity(intent);
//        });
//
//        return view;
//    }
//}