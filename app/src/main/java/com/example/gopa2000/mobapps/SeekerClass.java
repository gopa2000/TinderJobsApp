package com.example.gopa2000.mobapps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.*;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by gopa2000 on 11/4/16.
 */

public class SeekerClass extends CustomCard{
    private String salutation;
    private String fname;
    private String lname;
    private String imgToUpload;
    private String education;
    private String workExp;
    private String skills;
    private String mobNumber;
    private String email;

    public SeekerClass(String salutation, String fname, String lname, Bitmap imgToUpload, String education, String workExp, String skills, String mobNumber, String email){
        this.salutation = salutation;
        this.fname = fname;
        this.lname = lname;
        this.imgToUpload = (imgToUpload != null ? Util.encodeTobase64(imgToUpload) : null);
        this.education = education;
        this.workExp = workExp;
        this.skills = skills;
        this.mobNumber = mobNumber;
        this.email = email;
    }

    public String getSalutation() { return salutation; }

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

    public String getMobNumber() {
        return mobNumber;
    }

    public String getEmail() {
        return email;
    }
}
