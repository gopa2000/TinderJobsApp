package com.example.gopa2000.mobapps;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by gopa2000 on 11/4/16.
 */

public class JobListingClass extends CustomCard {
    private String companyName;
    private String jobDescription;
    private String skillsRequired;
    private String tags;
    private String jobTitle;
    private String expRequired;

    public JobListingClass(String companyName, String jobDescription, String skillsRequired, String tags, String jobTitle, String expRequired) {
        this.companyName = companyName;
        this.jobDescription = jobDescription;
        this.skillsRequired = skillsRequired;
        this.tags = tags;
        this.jobTitle = jobTitle;
        this.expRequired = expRequired;
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

    public String getTags() {
        return tags;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getExpRequired() {
        return expRequired;
    }
}
