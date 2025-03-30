package com.bettertfrrswebsite.dtos;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class MeetDTO {

    public String meetname;
    public String meetdate;
    public String numberofprs;
    public String uniqueprs;
    public List<RecentPrsDTO> prsatmeet;

    public MeetDTO() {
    }

    public MeetDTO(String meetname, String meetdate, String numberofprs, String uniqueprs, List<RecentPrsDTO> prsatmeet) throws UnsupportedEncodingException {
        this.meetname = meetname;
        this.meetdate = meetdate;
        this.numberofprs = numberofprs;
        this.uniqueprs = uniqueprs;
        this.prsatmeet = prsatmeet;
    }

    public String getMeetname() {
        return meetname;
    }

    public void setMeetname(String meetname) {
        this.meetname = meetname;
    }

    public String getMeetdate() {
        return meetdate;
    }

    public void setMeetdate(String meetdate) {
        this.meetdate = meetdate;
    }

    public String getNumberofprs() {
        return numberofprs;
    }

    public void setNumberofprs(String numberofprs) {
        this.numberofprs = numberofprs;
    }

    public String getUniqueprs() {
        return uniqueprs;
    }

    public void setUniqueprs(String uniqueprs) {
        this.uniqueprs = uniqueprs;
    }

    public List<RecentPrsDTO> getPrsatmeet() {
        return prsatmeet;
    }

    public void setPrsatmeet(List<RecentPrsDTO> prsatmeet) {
        this.prsatmeet = prsatmeet;
    }
}
