package com.milkstoremobile_fronend.views.activitys.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Product;
import com.milkstoremobile_fronend.viewmodels.ProductViewModel;

public class ProductFormActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // Mã request chọn ảnh
    private EditText edtProductName, edtProductPrice, edtProductQuantity, edtProductDescription;
    private Spinner spinnerCategory;
    private ImageView imgProduct;
    private Button btnChooseImage, btnSaveProduct;
    private ProductViewModel productViewModel;
    private Uri imageUri; // Biến lưu đường dẫn ảnh đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        // Ánh xạ các view
        edtProductName = findViewById(R.id.edt_product_name);
        edtProductPrice = findViewById(R.id.edt_product_price);
        edtProductQuantity = findViewById(R.id.edt_product_quantity);
        edtProductDescription = findViewById(R.id.edt_product_description);
        spinnerCategory = findViewById(R.id.spinner_category);
        imgProduct = findViewById(R.id.img_product);
        btnChooseImage = findViewById(R.id.btn_choose_image);
        btnSaveProduct = findViewById(R.id.btn_save_product);

        // Khởi tạo ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Xử lý sự kiện chọn ảnh
        btnChooseImage.setOnClickListener(v -> openImageChooser());

        // Xử lý sự kiện lưu sản phẩm
        btnSaveProduct.setOnClickListener(v -> saveProduct());
    }

    // Mở trình chọn ảnh từ thư viện
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*"); // Chỉ chọn ảnh
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Nhận kết quả sau khi chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // Lưu đường dẫn ảnh đã chọn
            imgProduct.setImageURI(imageUri); // Hiển thị ảnh lên ImageView
        }
    }

    // Lưu sản phẩm mới vào ViewModel
    private void saveProduct() {
        String name = edtProductName.getText().toString().trim();
        String priceStr = edtProductPrice.getText().toString().trim();
        String quantityStr = edtProductQuantity.getText().toString().trim();
        String description = edtProductDescription.getText().toString().trim();
        String categoryId = "1"; // Tạm đặt cứng

        if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty() || description.isEmpty()) {
            edtProductName.setError("Vui lòng nhập tên sản phẩm");
            edtProductPrice.setError("Vui lòng nhập giá");
            edtProductQuantity.setError("Vui lòng nhập số lượng");
            edtProductDescription.setError("Vui lòng nhập mô tả");
            return;
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);
        String image = (imageUri != null) ? imageUri.toString() : ""; // Lưu đường dẫn ảnh nếu có

        Product product = new Product(image, categoryId, description, price, quantity, name);
        productViewModel.addProduct(product);

        finish(); // Đóng activity sau khi lưu
    }
}
