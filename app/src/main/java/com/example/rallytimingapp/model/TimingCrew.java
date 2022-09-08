package com.example.rallytimingapp.model;

public class TimingCrew {

    private int crewId;
    private String position;
    private String postChief;
    private String postChiefPhone;

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
