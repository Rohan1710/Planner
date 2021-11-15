package com.example.android.taskplanner.Model;

public class taskModel {
    private int id,hour,minute,status;
    private String title,date,time;
    public taskModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public taskModel(String title, String date, String time, int id, int hour, int minute,int status){
        this.title = title;
        this.date = date;
        this.time = time;
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.status = status;
    }
}
