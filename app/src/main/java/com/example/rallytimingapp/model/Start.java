package com.example.rallytimingapp.model;

public class Start {
    // Object for each entry to Start database

    private int startID; // Unique ID for this database
    private int startOrder; // Start Order
    private int stage; // Stage Number
    private int carNum; // Car number
    private int stageID; // Unique stage ID for the competitor for this stage

    // Getters and setters for each parameter
    public int getStartID() {
        return startID;
    }

    public void setStartID(int startID) {
        this.startID = startID;
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

    public int getStageID() {
        return stageID;
    }

    public void setStageID(int stageID) {
        this.stageID = stageID;
    }
}
