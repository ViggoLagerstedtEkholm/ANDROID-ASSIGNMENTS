package com.example.a61;

/**
 * This class contains all the information we want to display to the user.
 * We also have getters and setters to access this data.
 */
public class HistoryModel {
    private final String number;
    private final String date;
    private final String time;
    private final String type;

    public HistoryModel(String number, String date, String time, String type) {
        this.number = number;
        this.date = date;
        this.type = type;
        this.time = time;
    }

    public String getTime(){
        return time;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }
}
