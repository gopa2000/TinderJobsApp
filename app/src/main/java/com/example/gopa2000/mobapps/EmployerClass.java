package com.example.gopa2000.mobapps;

import android.graphics.Bitmap;

/**
 * Created by Puru on 11/13/2016.
 */

public class EmployerClass {
    private String companyName;
    private String imgToUpload;
    private String companyInfo;
    private String contactNumber;
    private String email;

    public EmployerClass(String companyName, Bitmap imgToUpload, String companyInfo, String contactNumber, String email){
        this.companyName = companyName;
        this.imgToUpload = (imgToUpload != null ? Util.encodeTobase64(imgToUpload) : null);
        this.companyInfo = companyInfo;
        this.contactNumber = contactNumber;
        this.email = email;
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

    public String getEmail() {
        return email;
    }
}
