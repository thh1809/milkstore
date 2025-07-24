package com.milkstoremobile_fronend.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.milkstoremobile_fronend.views.adapters.Product.MilkAdapter;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.viewmodels.ProductViewModel;

import java.util.ArrayList;

public class MilkFragment extends Fragment {
    private RecyclerView recyclerView;
    private MilkAdapter milkAdapter;
    private ProgressBar progressBar;
    private ProductViewModel productViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_milk, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMilk);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        milkAdapter = new MilkAdapter(getContext(), new ArrayList<>(), false);
        recyclerView.setAdapter(milkAdapter);

        // Khởi tạo ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Quan sát dữ liệu sản phẩm
        productViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                milkAdapter.updateData(products);
            }
        });

        // Quan sát trạng thái loading
        productViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        // Luôn fetch data khi mở Fragment
        productViewModel.fetchProducts();

        return view;
    }
}

