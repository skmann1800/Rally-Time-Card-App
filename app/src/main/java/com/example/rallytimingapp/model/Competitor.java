package com.example.rallytimingapp.model;

public class Competitor {

    private int compId;
    private int carNum;
    private String driver;
    private String codriver;
    private int stage1Id;
    private int stage2Id;
    private int stage3Id;
    private int stage4Id;

    public int getCompId() {
        return compId;
    }

    public void setCompId(int id) {
        this.compId = id;
    }

    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCodriver() {
        return codriver;
    }

    public void setCodriver(String codriver) {
        this.codriver = codriver;
    }

    public int getStage1Id() {
        return stage1Id;
    }

    public void setStage1Id(int id) {
        this.stage1Id = id;
    }

    public int getStage2Id() {
        return stage2Id;
    }

    public void setStage2Id(int id) {
        this.stage2Id = id;
    }

    public int getStage3Id() {
        return stage3Id;
    }

    public void setStage3Id(int id) {
        this.stage3Id = id;
    }

    public int getStage4Id() {
        return stage4Id;
    }

    public void setStage4Id(int id) {
        this.stage4Id = id;
    }
}
