package com.example.rallytimingapp.model;

public class TimingCrew {
    // Object for each entry to Timing Crew database

    private int crewId; // Unique ID for this database
    private String position; // Position, eg: Start, Finish or A Control
    private String postChief; // Post chief's full name
    private String postChiefPhone; // Post chief's contact number

    // Getters and setters for each parameter
    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int id) {
        this.crewId = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPostChief() {
        return postChief;
    }

    public void setPostChief(String postChief) {
        this.postChief = postChief;
    }

    public String getPostChiefPhone() {
        return postChiefPhone;
    }

    public void setPostChiefPhone(String phone) {
        this.postChiefPhone = phone;
    }
}
