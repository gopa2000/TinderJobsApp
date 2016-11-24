package com.example.gopa2000.mobapps;

/**
 * Created by gopa2000 on 11/25/16.
 */

public class Like {
    String liker;
    String likee;

    public Like(String liker, String likee){
        this.liker = liker;
        this.likee = likee;
    }

    public String getLiker() {
        return liker;
    }

    public void setLiker(String liker) {
        this.liker = liker;
    }

    public String getLikee() {
        return likee;
    }

    public void setLikee(String likee) {
        this.likee = likee;
    }
}
