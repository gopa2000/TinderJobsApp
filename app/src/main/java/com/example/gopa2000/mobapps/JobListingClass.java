package com.example.gopa2000.mobapps;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by gopa2000 on 11/4/16.
 */

public class JobListingClass {
    private String companyName;
    private String jobTitle;
    private String experienceRequired;
    private String jobDescription;
    private String skillsRequired;

    public JobListingClass(String companyName, String jobTitle, String experienceRequired, ArrayList<String> jobDescription, ArrayList<String> skillsRequired){
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.experienceRequired = experienceRequired;
        this.jobDescription = Util.listToString(jobDescription);
        this.skillsRequired = Util.listToString(skillsRequired);
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

    public String getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(String experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
