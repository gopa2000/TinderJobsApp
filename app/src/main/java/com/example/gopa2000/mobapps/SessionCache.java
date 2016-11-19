package com.example.gopa2000.mobapps;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gopa2000 on 11/13/16.
 */

public class SessionCache {

    private static SessionCache instance = new SessionCache();

    private ArrayList<CustomCard> sessionCards;
    private ArrayList<SeekerClass> seekers;
    private ArrayList<JobListingClass> listings;
    private HashMap<String, String> likeTable;
    private ArrayList<EmployerClass> employers;

    private SessionCache(){
        this.sessionCards   = new ArrayList<>();
        this.seekers        = new ArrayList<>();
        this.listings       = new ArrayList<>();
        this.likeTable      = new HashMap<>();
        this.employers      = new ArrayList<>();
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

    public HashMap<String, String> getLikeTable() {
        return likeTable;
    }

    public void setLikeTable(HashMap<String, String> likeTable) {
        this.likeTable = likeTable;
    }

    public ArrayList<EmployerClass> getEmployers() {
        return employers;
    }

    public void setEmployers(ArrayList<EmployerClass> employers) {
        this.employers = employers;
    }
}
