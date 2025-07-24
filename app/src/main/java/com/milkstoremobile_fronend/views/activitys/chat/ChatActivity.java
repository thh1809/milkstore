// ChatActivity.java
package com.milkstoremobile_fronend.views.activitys.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.milkstoremobile_fronend.models.message.MessageAIRequest;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.api.ApiClient;
import com.milkstoremobile_fronend.api.services.AiApiService;
import com.milkstoremobile_fronend.views.adapters.Messages.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private AiApiService aiApiService;
    private List<String> messageList = new ArrayList<>();
    private MessageAdapter adapter;
    private RecyclerView recyclerView;
    private EditText edtMessage;
    private Button btnSend;
    private LottieAnimationView loadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chat);

        aiApiService = ApiClient.getAiApiService();

        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        loadingAnimation = findViewById(R.id.loadingAnimation);
        loadingAnimation.setVisibility(View.GONE);

        adapter = new MessageAdapter(messageList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnSend.setOnClickListener(v -> {
            String content = edtMessage.getText().toString().trim();
            if (content.isEmpty()) return;

            // hiển thị câu hỏi người dùng
            messageList.add("Bạn: " + content);
            adapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.scrollToPosition(messageList.size() - 1);
            edtMessage.setText("");

            loadingAnimation.setVisibility(View.VISIBLE);
            loadingAnimation.playAnimation();

            // gọi AI
            aiApiService.recommendMilk(new MessageAIRequest(content)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    loadingAnimation.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null) {
                        messageList.add("AI: " + response.body());
                        adapter.notifyItemInserted(messageList.size() - 1);
                        recyclerView.scrollToPosition(messageList.size() - 1);
                    } else {
                        Toast.makeText(ChatActivity.this, "AI không phản hồi!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loadingAnimation.setVisibility(View.GONE);
                    Toast.makeText(ChatActivity.this, "Lỗi mạng khi gọi AI!", Toast.LENGTH_SHORT).show();
                    Log.e("AI_ERROR", "Lỗi khi gọi API AI", t);  // ➜ Log ra lỗi
                }

            });
        });
    }
}