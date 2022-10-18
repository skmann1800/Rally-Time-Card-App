package com.example.rallytimingapp.model;

public class Finish {
    // Object for each entry to Finish database

    private int finishID; // Unique ID for this database
    private int finishOrder; // Finish order for this stage
    private int stage; // Stage number
    private int carNum; // Car number
    private int stageID; // Unique stage ID for the competitor for this stage

    // Getters and setters for each parameter
    public int getFinishID() {
        return finishID;
    }

    public void setFinishID(int finishID) {
        this.finishID = finishID;
    }

    public int getFinishOrder() {
        return finishOrder;
    }

    public void setFinishOrder(int finishOrder) {
        this.finishOrder = finishOrder;
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
