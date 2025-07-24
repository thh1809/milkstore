package com.milkstoremobile_fronend.views.adapters.Product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Product;
import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final Context context;
    private final List<Product> productList;
    private final OnProductClickListener listener;

    public interface OnProductClickListener {
        void onEditProduct(Product product);
        void onDeleteProduct(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_manage, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.txtProductName.setText(product.getProductName());
        holder.txtProductPrice.setText("Giá: " + product.getPrice() + " VND");
        holder.txtProductQuantity.setText("Số lượng: " + product.getQuantity());
        holder.txtProductDescription.setText("Mô tả: " + product.getDescription());

        // Load ảnh sản phẩm
        Glide.with(context).load(product.getImage()).into(holder.imgProduct);

        // Xử lý sự kiện khi nhấn sửa
        holder.btnEditProduct.setOnClickListener(v -> listener.onEditProduct(product));

        // Xử lý sự kiện khi nhấn xóa
        holder.btnDeleteProduct.setOnClickListener(v -> listener.onDeleteProduct(product));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnEditProduct, btnDeleteProduct;
        TextView txtProductName, txtProductPrice, txtProductQuantity, txtProductDescription;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            txtProductQuantity = itemView.findViewById(R.id.txtProductQuantity);
            txtProductDescription = itemView.findViewById(R.id.txtProductDescription);
            btnEditProduct = itemView.findViewById(R.id.btnEditProduct);
            btnDeleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
        }
    }
}


