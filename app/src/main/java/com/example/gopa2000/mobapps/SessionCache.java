package com.example.gopa2000.mobapps;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by gopa2000 on 11/13/16.
 */

public class SessionCache {

    private static SessionCache instance = new SessionCache();

    private ArrayList<CustomCard> sessionCards;
    private ArrayList<SeekerClass> seekers;
    private ArrayList<JobListingClass> listings;
    private ArrayList<Like> likeTable;
    private ArrayList<EmployerClass> employers;
    private ArrayList<Match> matched;
    private ArrayList<Match> sessionMatches;
    private ArrayList<ChatRoom> chatRooms;

    private SessionManager sessionManager;

    private SessionCache(){
        this.sessionCards   = new ArrayList<>();
        this.seekers        = new ArrayList<>();
        this.listings       = new ArrayList<>();
        this.likeTable      = new ArrayList<>();
        this.employers      = new ArrayList<>();
        this.matched        = new ArrayList<>();
        this.sessionMatches = new ArrayList<>();
        this.chatRooms      = new ArrayList<>();
    }

    public static SessionCache getInstance(){
        return instance;
    }

    public void setSessionCards(ArrayList<CustomCard> cards){
        this.sessionCards = cards;
    }

    public ArrayList<CustomCard> getSessionCards() {
        return sessionCards;
    }

    public ArrayList<SeekerClass> getSeekers() {
        return seekers;
    }

    public void setSeekers(ArrayList<SeekerClass> seekers) {
        this.seekers = seekers;
    }

    public ArrayList<JobListingClass> getListings() {
        return listings;
    }

    public void setListings(ArrayList<JobListingClass> listings) {
        this.listings = listings;
    }

    public ArrayList<Like> getLikeTable() { return likeTable; }

    public void setLikeTable(ArrayList<Like> likeTable) { this.likeTable = likeTable; }

    public ArrayList<Match> getMatched() { return matched; }

    public void setMatched(ArrayList<Match> matched) { this.matched = matched; }

    public ArrayList<EmployerClass> getEmployers() {
        return employers;
    }

    public void setEmployers(ArrayList<EmployerClass> employers) {
        this.employers = employers;
    }

    public void addToLikeTable (String Liker, String Likee) { this.likeTable.add(new Like(Liker, Likee)); }

    public void addToMatchTable(String Seeker, String Employer, String sessionUserEmail){
        this.matched.add(new Match(Seeker, Employer));

        if(sessionUserEmail.equals(Seeker) || sessionUserEmail.equals(Employer))
            this.sessionMatches.add(new Match(Seeker, Employer));
    }

    public void generateSessionMatches(String userEmail){
        for(Match m:matched){
            if(m.getSeeker().equals(userEmail) || m.getEmployer().equals(userEmail)){
                this.sessionMatches.add(m);
                Log.e("SessionCache", "generateSessionMatches: " + m.toString());
            }
        }
    }

    public ArrayList<Match> getSessionMatches() {
        return sessionMatches;
    }

    public void setSessionMatches(ArrayList<Match> sessionMatches) {
        this.sessionMatches = sessionMatches;
    }

    public ArrayList<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(ArrayList<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public void addMessagesToChat(String room, String sender, String message){

        Message msg = new Message(sender, message);
        for(ChatRoom c:chatRooms){
            if(c.getRoom().equals(room)){
                c.addMessage(msg);
            }
        }
    }

    public ArrayList<Message> getChatMessages(String room){
        for(ChatRoom c:chatRooms){
            if(c.getRoom().equals(room)){
                return c.getMessages();
            }
        }

        return new ArrayList<Message>();
    }

    public void addToLikeTable(Like like){
        likeTable.add(like);
    }

    public void createChatRoom(String room){
        chatRooms.add(new ChatRoom(room, new ArrayList<Message>()));
    }
}
