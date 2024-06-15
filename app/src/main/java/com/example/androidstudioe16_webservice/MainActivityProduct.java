package com.example.androidstudioe16_webservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import Adapter.ProductAdapter;
import ApiClient.ProductApiClient;
import ApiService.ProductApiService;
import Models.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityProduct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private FloatingActionButton fab;
    private ProductApiService productApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewProduct);
        fab = findViewById(R.id.fabProduct);

        productApiService = new ProductApiClient().getApiService();

        recyclerView.setLayoutManager(new
                LinearLayoutManager(this));
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityProduct.this,
                    AddProductActivity.class);
            startActivity(intent);
        });
    }

    private void fetchProducts() {
        productApiService.gets().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {
                if (response.isSuccessful() && response.body()
                        != null) {
                    productAdapter = new
                            ProductAdapter(MainActivityProduct.this, response.body(), new
                            ProductAdapter.OnItemClickListener() {
                                @Override
                                public void onEditClick(int id) {
                                    Intent intent = new Intent(MainActivityProduct.this,
                                            UpdateProductActivity.class);

                                    intent.putExtra("product_id", Integer.toString(response.body().get(id).getId()));
                                    startActivity(intent);
                                }

                                @Override
                                public void onDeleteClick(int id) {
                                    deleteProduct(response.body().get(id).getId());
                                }
                            });
                    recyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call,
                                  Throwable t) {
                Toast.makeText(MainActivityProduct.this, "Failed to fetch product", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteProduct(int id) {
        productApiService.delete(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call,
                                   Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivityProduct.this, "Delete to product success", Toast.LENGTH_SHORT).show();
                    fetchProducts();
                }
            }

            @Override
            public void onFailure(Call<Void> call,
                                  Throwable t) {
                Toast.makeText(MainActivityProduct.this, "Failed to fetch product", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        //just call when other layout back to this
        super.onResume();
        fetchProducts();
    }
}