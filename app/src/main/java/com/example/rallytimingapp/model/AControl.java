package com.example.rallytimingapp.model;

public class AControl {
    // Object for each entry to A Control database

    private int aControlID; // Unique ID for this database
    private int startOrder; // Start order for the stage
    private int stage; // Stage
    private int carNum; // Car Number
    private int stage1ID; // Unique stage ID for the competitor for the stage before the current one
    private int stage2ID; // Unique stage ID for the competitor for this stage

    // Getters and Setters for each parameter
    public int getAControlID() {
        return aControlID;
    }

    public void setAControlID(int aControlID) {
        this.aControlID = aControlID;
    }

    public int getStartOrder() {
        return startOrder;
    }

    public void setStartOrder(int startOrder) {
        this.startOrder = startOrder;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    public int getStage1ID() {
        return stage1ID;
    }

    public void setStage1ID(int stage1ID) {
        this.stage1ID = stage1ID;
    }

    public int getStage2ID() {
        return stage2ID;
    }

    public void setStage2ID(int stage2ID) {
        this.stage2ID = stage2ID;
    }
}
