//package com.milkstoremobile_fronend.websocket;
//
//
//import android.util.Log;
//import org.json.JSONObject;
//import io.reactivex.disposables.Disposable;
//import ua.naiksoftware.stomp.Stomp;
//import ua.naiksoftware.stomp.StompClient;
//import ua.naiksoftware.stomp.dto.StompMessage;
//import ua.naiksoftware.stomp.dto.LifecycleEvent;
//
//public class StompClientManager {
//
//    private static final String TAG = "StompClient";
//    private static final String SOCKET_URL = "ws://your-server-ip:8080/ws/websocket"; // ❗ sửa IP thật
//
//    private StompClient stompClient;
//    private Disposable dispLifecycle, dispTopic;
//
//    public interface MessageListener {
//        void onMessage(String messageJson);
//    }
//
//    public void connect(String userId, MessageListener listener) {
//        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL);
//        stompClient.connect();
//
//        dispLifecycle = stompClient.lifecycle().subscribe(event -> {
//            switch (event.getType()) {
//                case OPENED:
//                    Log.d(TAG, "STOMP Connected");
//                    break;
//                case ERROR:
//                    Log.e(TAG, "STOMP Error", event.getException());
//                    break;
//                case CLOSED:
//                    Log.d(TAG, "STOMP Disconnected");
//                    break;
//            }
//        });
//
//        dispTopic = stompClient.topic("/user/" + userId + "/queue/messages")
//                .subscribe(topicMessage -> {
//                    Log.d(TAG, "Message received: " + topicMessage.getPayload());
//                    listener.onMessage(topicMessage.getPayload());
//                });
//    }
//
//    public void sendMessage(String destination, JSONObject jsonMessage) {
//        stompClient.send(destination, jsonMessage.toString()).subscribe();
//    }
//
//    public void disconnect() {
//        if (dispLifecycle != null) dispLifecycle.dispose();
//        if (dispTopic != null) dispTopic.dispose();
//        if (stompClient != null) stompClient.disconnect();
//    }
//}
