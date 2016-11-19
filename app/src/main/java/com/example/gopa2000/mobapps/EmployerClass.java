package com.example.gopa2000.mobapps;

import android.graphics.Bitmap;

/**
 * Created by Puru on 11/13/2016.
 */

public class EmployerClass extends CustomCard{
    private String companyName;
    private String imgToUpload;
    private String companyInfo;
    private String contactNumber;
    private String email;
    private String likes;
    private String dislikes;
    private String img;

    public EmployerClass(String companyName, String imgToUpload, String companyInfo, String contactNumber, String email, String likes, String dislikes){
        this.companyName = companyName;
        //this.img = (imgToUpload != null ? Util.decodeBase64(imgToUpload) : null);
        this.imgToUpload = img;
        this.companyInfo = companyInfo;
        this.contactNumber = contactNumber;
        this.email = email;
        this.likes = likes;
        this.dislikes = dislikes;
    }


    public String getCompanyName() {
        return companyName;
    }

    public String getImgToUpload() {
        return imgToUpload;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public String getLikes() {
        return likes;
    }

    public String getDislikes() {
        return dislikes;
    }
}
