package com.example.gopa2000.mobapps;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;

public class ChatActivity extends AppCompatActivity {


    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();
    private String mUsername;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


    }
}
