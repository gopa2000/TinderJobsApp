package com.example.gopa2000.mobapps;

public class Message {

    public static final int TYPE_MESSAGE = 0;

    private String mMessage;
    private String mUsername;

    public Message(String username, String msg) {
        mMessage = msg;
        mUsername = username;
    }

    public String getMessage() {
        return mMessage;
    };

    public String getUsername() {
        return mUsername;
    };

}