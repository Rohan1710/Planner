package com.example.android.taskplanner.Model;

public class taskModel {
    private int id,hour,maintask,minute,status,check,repeatEndDay,repeatEndMonth,repeateEndYear,repeatEndHour,repeatEndMinute;
    private String title,date,time,priority;
    public taskModel(){

    }

    public int getId() {
        return id;
    }

    public int getMaintask() {
        return maintask;
    }

    public void setMaintask(int maintask) {
        this.maintask = maintask;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getRepeatEndDay() {
        return repeatEndDay;
    }

    public void setRepeatEndDay(int repeatEndDay) {
        this.repeatEndDay = repeatEndDay;
    }

    public int getRepeatEndMonth() {
        return repeatEndMonth;
    }

    public void setRepeatEndMonth(int repeatEndMonth) {
        this.repeatEndMonth = repeatEndMonth;
    }

    public int getRepeateEndYear() {
        return repeateEndYear;
    }

    public void setRepeateEndYear(int repeateEndYear) {
        this.repeateEndYear = repeateEndYear;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getRepeatEndHour() {
        return repeatEndHour;
    }

    public void setRepeatEndHour(int repeatEndHour) {
        this.repeatEndHour = repeatEndHour;
    }

    public int getRepeatEndMinute() {
        return repeatEndMinute;
    }

    public void setRepeatEndMinute(int repeatEndMinute) {
        this.repeatEndMinute = repeatEndMinute;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public taskModel(String title, String date, String time, int id, int hour, int minute,int status,int check,int repeatEndDay,int repeatEndMonth,int repeateEndYear,int repeatEndHour,int repeatEndMinute,String priority,int maintask){
        this.title = title;
        this.date = date;
        this.time = time;
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.status = status;
        this.repeatEndHour = repeatEndHour;
        this.repeatEndMinute = repeatEndMinute;
        this.check = check;
        this.priority = priority;
        this.repeateEndYear = repeateEndYear;
        this.repeatEndMinute = repeatEndMinute;
        this.repeatEndDay = repeatEndDay;
        this.maintask = maintask;
    }
}
