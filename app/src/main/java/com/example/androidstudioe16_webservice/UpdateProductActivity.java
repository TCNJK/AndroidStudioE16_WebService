package com.example.androidstudioe16_webservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ApiClient.ProductApiClient;
import ApiService.ProductApiService;
import Models.CastData;
import Models.Post;
import Models.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProductActivity extends AppCompatActivity {
    private EditText name, categoryId, unitsInStock, unitPrice;
    private TextView etId;
    private Button btnSave;
    private ProductApiService productApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etId = findViewById(R.id.etId);
        name = findViewById(R.id.etPdName);
        categoryId = findViewById(R.id.etPdCategoryId);
        unitsInStock = findViewById(R.id.etPdUnitsInStock);
        btnSave = findViewById(R.id.btnSave);
        unitPrice = findViewById(R.id.etPdUnitPrice);
        productApiService = ProductApiClient.getApiService();

        Intent intent = getIntent();
        String productId = intent.getStringExtra("product_id");
        fetchProduct(Integer.parseInt(productId));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = CastData.parseInt(etId.getText().toString());
                String pdName = name.getText().toString();
                int pdCategoryId = CastData.parseInt(categoryId.getText().toString());
                int pdUnitsInStock = CastData.parseInt(unitsInStock.getText().toString());
                double pdUnitPrice = CastData.parseDouble(unitPrice.getText().toString());
                if (pdName.isEmpty() || pdCategoryId == 0) {
                    Toast.makeText(UpdateProductActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                updatePost(new Product(id, pdName, pdCategoryId, pdUnitsInStock, pdUnitPrice, null));
            }
        });
    }

    private void updatePost(Product product) {
        productApiService.update(product.getId(), product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateProductActivity.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(UpdateProductActivity.this, "Failed to update Product", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProduct(int id) {
        productApiService.get(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call,
                                   Response<Product> response) {
                if (response.isSuccessful() && response.body()
                        != null) {
                    Product product = response.body();
                    etId.setText(Integer.toString(product.getId()));
                    name.setText(product.getName());
                    categoryId.setText(Integer.toString(product.getCategoryId()));
                    unitsInStock.setText(Integer.toString(product.getUnitsInStock()));
                    unitPrice.setText(Double.toString(product.getUnitPrice()));
                }
            }

            @Override
            public void onFailure(Call<Product> call,
                                  Throwable t) {
                Toast.makeText(UpdateProductActivity.this, "Failed to fetch product", Toast.LENGTH_SHORT).show();
            }
        });
    }
}