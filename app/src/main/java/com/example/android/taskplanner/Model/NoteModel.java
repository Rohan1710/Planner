package com.example.android.taskplanner.Model;

public class NoteModel {
    private int nid;
    private String ntext, ndate, ntime;

    public NoteModel(int nid, String ntext, String ndate, String ntime) {
        this.nid = nid;
        this.ntext = ntext;
        this.ndate = ndate;
        this.ntime = ntime;
    }

    public NoteModel() {

    }

    public int getNid() {
        return nid;
    }

    public String getNtext() {
        return ntext;
    }

    public String getNdate() {
        return ndate;
    }

    public String getNtime() {
        return ntime;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public void setNtext(String ntext) {
        this.ntext = ntext;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }

    public void setNtime(String ntime) {
        this.ntime = ntime;
    }
}
