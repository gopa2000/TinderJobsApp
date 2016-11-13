package com.example.gopa2000.mobapps;

import java.util.ArrayList;

/**
 * Created by gopa2000 on 11/13/16.
 */

public class SessionCache {

    private static SessionCache instance = new SessionCache();


    private ArrayList<CustomCard> sessionCards;

    public static SessionCache getInstance(){
        return instance;
    }

    private SessionCache(){
        this.sessionCards = new ArrayList<>();
    }

    public void setSessionCards(ArrayList<CustomCard> cards){
        this.sessionCards = cards;
    }

    public ArrayList<CustomCard> getSessionCards() {
        return sessionCards;
    }
}
