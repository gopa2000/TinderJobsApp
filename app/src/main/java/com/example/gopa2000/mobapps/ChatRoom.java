package com.example.gopa2000.mobapps;

import java.util.ArrayList;

/**
 * Created by gopa2000 on 11/25/16.
 */

public class ChatRoom {
    private String room;
    private ArrayList<Message> messages;

    public ChatRoom(String room, ArrayList<Message> messages){
        this.room = room;
        this.messages = messages;
    }

    public void addMessage(Message m){
        messages.add(m);
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
