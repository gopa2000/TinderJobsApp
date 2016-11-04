package com.example.gopa2000.mobapps;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by gopa2000 on 11/4/16.
 */

public class JobListingClass {
    private String logoToUpload;
    private String companyName;
    private String jobDescription;
    private String skillsRequired;

    public JobListingClass(Bitmap logoToUpload, String companyName, ArrayList<String> jobDescription, ArrayList<String> skillsRequired){
        this.logoToUpload = (logoToUpload != null ? Util.encodeTobase64(logoToUpload) : null);
        this.companyName = companyName;
        this.jobDescription = Util.listToString(jobDescription);
        this.skillsRequired = Util.listToString(skillsRequired);
    }

    public String getLogoToUpload() {
        return logoToUpload;
    }

    public void setLogoToUpload(String logoToUpload) {
        this.logoToUpload = logoToUpload;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }
}
