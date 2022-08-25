package com.example.rallytimingapp.model;

public class Finish {

    private int finishID;
    private int finishOrder;
    private int stage;
    private int carNum;
    private int stageID;

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
