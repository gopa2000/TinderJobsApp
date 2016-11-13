package com.example.gopa2000.mobapps;

import android.graphics.Bitmap;

/**
 * Created by gopa2000 on 11/4/16.
 */

public class SeekerClass extends CustomCard {
    private String fname;
    private String lname;
    private String imgToUpload;
    private String education;
    private String workExp;
    private String skills;
    private String contact;
    private String email;

    public SeekerClass(String fname, String lname, Bitmap imgToUpload, String education, String workExp, String skills, String contact, String email){
        super();
        this.fname = fname;
        this.lname = lname;
        this.imgToUpload = (imgToUpload != null ? Util.encodeTobase64(imgToUpload) : null);
        this.education = education;
        this.workExp = workExp;
        this.skills = skills;
        this.contact = contact;
        this.email = email;
    }

    @Override
    public String toString() {
        return this.fname + " " + this.lname + " " + this.education + this.workExp + this.skills + this.contact + this.email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getImgToUpload() {
        return imgToUpload;
    }

    public String getEducation() {
        return education;
    }

    public String getWorkExp() {
        return workExp;
    }

    public String getSkills() {
        return skills;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }
}
