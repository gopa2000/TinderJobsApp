package com.example.gopa2000.mobapps;

/**
 * Created by gopa2000 on 11/25/16.
 */

public class Match {
    private String employer;
    private String seeker;
    private String chatroom;

    public Match(String seeker, String employer){
        this.employer   = employer;
        this.seeker     = seeker;
        this.chatroom   = generateChatroom();
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getSeeker() {
        return seeker;
    }

    public void setSeeker(String seeker) {
        this.seeker = seeker;
    }

    public String getChatroom() {
        return chatroom;
    }

    public void setChatroom(String chatroom) {
        this.chatroom = chatroom;
    }

    private String generateChatroom(){
        String chatroom = seeker + employer;
        chatroom = chatroom.replaceAll("[^a-zA-Z0-9]+","");

        String newRoom = "";

        for(int i=0; i<chatroom.length(); i+=2){
            newRoom += chatroom.charAt(i);
        }

        return newRoom;
    }

    @Override
    public String toString(){
        return "Seeker: " + seeker + ", Employer: " + employer + ", Chatroom: " + chatroom;
    }
}
