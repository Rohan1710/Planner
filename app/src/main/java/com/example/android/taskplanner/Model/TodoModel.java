package com.example.android.taskplanner.Model;

public class TodoModel {
    private int id, status;
    private String task, ddate;

    public TodoModel(int id, int status, String task, String ddate) {
        this.id = id;
        this.status = status;
        this.task = task;
        this.ddate = ddate;
    }

    public TodoModel(int status, String task, String ddate) {
        this.status = status;
        this.task = task;
        this.ddate = ddate;
    }

    public TodoModel() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }
}
