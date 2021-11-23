package com.example.android.taskplanner.Model;

public class NoteModel {
    private int nid, nday, nmonth, nyear, nhour, nminute, ndate, ntime;
    private String ntext;

    public NoteModel(int nid, int nday, int nmonth, int nyear, int nhour, int nminute, int ndate, int ntime, String ntext) {
        this.nid = nid;
        this.nday = nday;
        this.nmonth = nmonth;
        this.nyear = nyear;
        this.nhour = nhour;
        this.nminute = nminute;
        this.ndate = ndate;
        this.ntime = ntime;
        this.ntext = ntext;
    }

    public NoteModel() {

    }

    public void setNdate(int ndate) {
        this.ndate = ndate;
    }

    public void setNtime(int ntime) {
        this.ntime = ntime;
    }

    public int getNdate() {
        return ndate;
    }

    public int getNtime() {
        return ntime;
    }

    public int getNid() {
        return nid;
    }

    public int getNday() {
        return nday;
    }

    public int getNmonth() {
        return nmonth;
    }

    public int getNyear() {
        return nyear;
    }

    public int getNhour() {
        return nhour;
    }

    public int getNminute() {
        return nminute;
    }

    public String getNtext() {
        return ntext;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public void setNday(int nday) {
        this.nday = nday;
    }

    public void setNmonth(int nmonth) {
        this.nmonth = nmonth;
    }

    public void setNyear(int nyear) {
        this.nyear = nyear;
    }

    public void setNhour(int nhour) {
        this.nhour = nhour;
    }

    public void setNminute(int nminute) {
        this.nminute = nminute;
    }

    public void setNtext(String ntext) {
        this.ntext = ntext;
    }
}
