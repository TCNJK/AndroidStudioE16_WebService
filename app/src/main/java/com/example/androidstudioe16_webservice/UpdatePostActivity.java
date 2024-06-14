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

import java.util.List;

import Adapter.PostAdapter;
import ApiClient.PostApiClient;
import ApiService.PostApiService;
import Models.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePostActivity extends AppCompatActivity {
    private EditText etTitle, etBody;
    private TextView etId;
    private Button btnSave;
    private PostApiService postApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etId = findViewById(R.id.etId);
        etTitle = findViewById(R.id.etTitle);
        etBody = findViewById(R.id.etBody);
        btnSave = findViewById(R.id.btnSave);
        postApiService = PostApiClient.getApiService();

        Intent intent = getIntent();
        String postId = intent.getStringExtra("post_id");
        fetchPost(Integer.parseInt(postId));


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etId.getText().toString());
                String title = etTitle.getText().toString();
                String body = etBody.getText().toString();
                if (title.isEmpty() || body.isEmpty()) {
                    Toast.makeText(UpdatePostActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                updatePost(new Post(id, title, body));
            }
        });
    }

    private void updatePost(Post post) {
        postApiService.updatePost(1, post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(UpdatePostActivity.this, "Post updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(UpdatePostActivity.this, "Failed to update post", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchPost(int id) {
        postApiService.getPost(id).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call,
                                   Response<Post> response) {
                if (response.isSuccessful() && response.body()
                        != null) {
                    Post post = response.body();
                    etId.setText(Integer.toString(post.getId()));
                    etTitle.setText(post.getTitle());
                    etBody.setText(post.getBody());
                }
            }

            @Override
            public void onFailure(Call<Post> call,
                                  Throwable t) {
                Toast.makeText(UpdatePostActivity.this, "Failed to fetch posts", Toast.LENGTH_SHORT).show();
            }
        });
    }
}