package com.example.rallytimingapp.model;

public class Stage {

    private int stageId;
    private int stageNum;
    private int startOrder;
    private String provStart;
    private String actualStart;
    private String finishTime;
    private String stageTime;
    private String actualTime;
    private String dueTime;

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int id) {
        this.stageId = id;
    }

    public int getStageNum() {
        return stageNum;
    }

    public void setStageNum(int stage) {
        this.stageNum = stage;
    }

    public int getStartOrder() {
        return startOrder;
    }

    public void setStartOrder(int startOrder) {
        this.startOrder = startOrder;
    }

    public String getProvStart() {
        return provStart;
    }

    public void setProvStart(String provStart) {
        this.provStart = provStart;
    }

    public String getActualStart() {
        return actualStart;
    }

    public void setActualStart(String actualStart) {
        this.actualStart = actualStart;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getStageTime() {
        return stageTime;
    }

    public void setStageTime(String stageTime) {
        this.stageTime = stageTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
}
