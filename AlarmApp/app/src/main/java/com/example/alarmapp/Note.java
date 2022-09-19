package com.example.alarmapp;

public class Note {
    int _id;
    String name;
    String date;
    String time;
    String memo;
    String message;
    String phoneNumber;
    String duration;

    public Note(int _id, String name, String date, String time, String memo, String message, String phoneNumber,String duration){
        this.name = name;
        this.date = date;
        this.time = time;
        this.memo = memo;
        this.message = message;
        this.phoneNumber = phoneNumber;
        this.duration = duration;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
