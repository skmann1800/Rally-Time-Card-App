package com.example.rallytimingapp.model;

public class Competitor {
    // Object for each entry to Competitor database

    private int compId; // Unique ID for this database
    private int carNum; // Car number
    private String driver; // Driver's name
    private String codriver; // Codriver's name
    private int stage1Id; // Unique ID for this competitors stage 1 in the stage database
    private int stage2Id; // Unique ID for this competitors stage 2 in the stage database
    private int stage3Id; // Unique ID for this competitors stage 3 in the stage database
    private int stage4Id; // Unique ID for this competitors stage 4 in the stage database

    // Getters and setters for each parameter
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

    // Get the stage ID for that competitor for the given stage
    public int getStageId(int stage) {
        int stageID = 0;
        switch (stage) {
            case 0:
                stageID = 0;
                break;
            case 1:
                stageID = stage1Id;
                break;
            case 2:
                stageID = stage2Id;
                break;
            case 3:
                stageID = stage3Id;
                break;
            case 4:
                stageID = stage4Id;
                break;
        }
        return stageID;
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
