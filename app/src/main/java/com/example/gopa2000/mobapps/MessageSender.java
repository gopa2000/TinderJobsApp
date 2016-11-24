package com.example.gopa2000.mobapps;

import org.json.JSONObject;

/**
 * Created by gopa2000 on 11/24/16.
 */

public interface MessageSender {
    void sendMessage(String msg,  JSONObject json);
}
