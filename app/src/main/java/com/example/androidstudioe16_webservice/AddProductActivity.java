package com.example.androidstudioe16_webservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ApiClient.ProductApiClient;
import ApiService.ProductApiService;
import Models.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    private EditText name, categoryId, unitsInStock, unitPrice;
    private Button btnSave;
    private ProductApiService productApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.etPdName);
        categoryId = findViewById(R.id.etPdCategoryId);
        unitsInStock = findViewById(R.id.etPdUnitsInStock);
        unitPrice = findViewById(R.id.etPdUnitPrice);
        productApiService = ProductApiClient.getApiService();

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdName = name.getText().toString();
                int pdCategoryId = parseInt(categoryId.getText().toString());
                int pdUnitsInStock = parseInt(unitsInStock.getText().toString());
                double pdUnitPrice = parseDouble(unitPrice.getText().toString());

                if (pdName.isEmpty() || pdCategoryId == 0) {
                    Toast.makeText(AddProductActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                createProduct(new Product(pdName, pdCategoryId, pdUnitsInStock, pdUnitPrice));
            }
        });
    }

    private void createProduct(Product product) {
        productApiService.create(product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddProductActivity.this, "Product created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, "Failed to create product", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int parseInt(String value) {
        int result;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }

    private double parseDouble(String value) {
        double result;
        try {
            result = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }
}