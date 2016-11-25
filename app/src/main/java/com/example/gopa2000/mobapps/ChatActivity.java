package com.example.gopa2000.mobapps;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.socket.client.Socket;

public class ChatActivity extends AppCompatActivity implements MessageSender {

    private static String TAG = "ChatActivity";

    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    private String mUsername;
    private Socket mSocket;
    private SocketListener socketService;
    private boolean isBound;

    private static ChatActivity context;
    private SessionManager sessionManager;
    private SessionCache sessionCache;
    private Map<String, ?> userDetails;

    public static String roomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        context = this;
        roomID = getIntent().getStringExtra("room");

        doBindService();

        sessionManager = new SessionManager(getApplicationContext());
        sessionCache = SessionCache.getInstance();
        userDetails = sessionManager.getUserDetails();

        mMessages = sessionCache.getChatMessages(roomID);
        mAdapter = new MessageAdapter(getApplicationContext(), mMessages);

        mUsername = userDetails.get(DbHelper.KEY_EMAIL).toString();
        mMessagesView = (RecyclerView) findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(this));
        mMessagesView.setAdapter(mAdapter);

        mInputMessageView = (EditText) findViewById(R.id.message_input);

        ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("room", roomID);
                    obj.put("sender", mUsername);

                    String message = mInputMessageView.getText().toString().trim();
                    obj.put("message", message);

                    sendMessage("send", obj);
                }

                catch (JSONException e){
                    Log.e(TAG, "onClick: ", e);
                }
            }
        });
    }

    private static void addMessage(String username, String message) {
        context.mMessages.add(new Message(username, message));
        context.mAdapter.notifyItemInserted(context.mMessages.size() - 1);
        context.scrollToBottom();
    }


    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            socketService = ((SocketListener.LocalBinder) iBinder).getService();

            if(socketService != null){
                Log.i("service-bind", "Service bound successfully!");

                //do whatever you want to do after successful binding
                mSocket = socketService.getSocket();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            socketService = null;
        }
    };


    private void doBindService() {
        bindService(new Intent(ChatActivity.this, SocketListener.class), serviceConnection, Context.BIND_AUTO_CREATE);
        isBound = true;
        if(socketService!=null){
            socketService.IsBoundable();
        }
    }


    private void doUnbindService() {
        if (isBound) {
            // Detach our existing connection.
            unbindService(serviceConnection);
            isBound = false;
        }
    }


    @Override
    public void sendMessage(String msg, JSONObject json){
        socketService.sendMessage(msg, json);
    }

    @Override
    protected void onPause() {
        super.onPause();
        doUnbindService();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
        finish();
    }
}
