package com.milkstoremobile_fronend.views.activitys.chat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.api.ApiClient;
import com.milkstoremobile_fronend.api.services.MessageApiService;
import com.milkstoremobile_fronend.models.message.MessageRequest;
import com.milkstoremobile_fronend.models.message.MessageResponse;
import com.milkstoremobile_fronend.views.activitys.MainActivity;
import com.milkstoremobile_fronend.views.adapters.Messages.MessageAdapter;
import com.milkstoremobile_fronend.sharereference.UserInfor.SharedPrefManager;
import com.milkstoremobile_fronend.websocket.StompClientManager;

import android.content.Intent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private MessageApiService apiService;
    private StompClientManager stompClientManager;

    private MessageAdapter adapter;
    private List<MessageResponse> messageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText edtMessage;
    private Button btnSend;


    private String userId;
    private final String adminId = "ebe0aef2-bbc7-406c-97c3-e8ca3d4f247e";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userId = SharedPrefManager.getInstance(this).getUserId();


        if (userId == null) {
            Toast.makeText(this, "Không tìm thấy userId, vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        apiService = ApiClient.getMessageApiService();

        recyclerView = findViewById(R.id.recyclerViewMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(messageList, userId);
        recyclerView.setAdapter(adapter);

        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

// Load tin nhắn ngay sau khi khởi tạo
        loadMessages(userId, adminId);
        stompClientManager = new StompClientManager();
        stompClientManager.connect(userId, messageJson -> {
            // Parse JSON nếu cần (ở đây là plain text hoặc JSON string)
            runOnUiThread(() -> {
                // Tùy định dạng từ server, bạn parse lại thành MessageResponse nếu cần
                MessageResponse newMsg = new MessageResponse(); // ← tuỳ cấu trúc
                newMsg.setMessage(messageJson); // hoặc parse JSON thành đối tượng đúng
                newMsg.setSenderId(adminId); // Giả định là từ admin
                newMsg.setReceiverId(userId);
                newMsg.setTimestamp("Vừa xong"); // Tạm thời, nếu không có thời gian

                messageList.add(newMsg);
                adapter.notifyItemInserted(messageList.size() - 1);
                recyclerView.scrollToPosition(messageList.size() - 1);
            });
        });






        btnSend.setOnClickListener(v -> {
            String content = edtMessage.getText().toString().trim();
            if (content.isEmpty()) return;
            MessageRequest request = new MessageRequest(userId, adminId, content);
            apiService.sendMessage(request).enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        messageList.add(response.body());
                        adapter.notifyItemInserted(messageList.size() - 1);
                        recyclerView.scrollToPosition(messageList.size() - 1);
                        edtMessage.setText("");
                    } else {
                        Toast.makeText(ChatActivity.this, "Gửi tin nhắn thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    Toast.makeText(ChatActivity.this, "Lỗi mạng!", Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    private void loadMessages(String userId, String adminId) {
        apiService.getMessages(userId, adminId).enqueue(new Callback<List<MessageResponse>>() {
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messageList.clear();
                    messageList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messageList.size() - 1);
                }
            }
            @Override
            public void onFailure(Call<List<MessageResponse>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Lỗi tải tin nhắn!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}