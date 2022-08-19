package com.example.rallytimingapp.model;

public class Competitor {

    private int id;
    private int carNum;
    private String driver;
    private String codriver;
    private int[] provStarts;
    private int[] actualStarts;
    private int[] finishTimes;
    private int[] stageTimes;
    private int[] startOrders;
    private int[] actualTimes;
    private int[] dueTimes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getProvStart(int stage) {
        return provStarts[stage - 1];
    }

    public int[] getProvStarts() {
        return provStarts;
    }

    public void setProvStart(int stage, int provStart) {
        this.provStarts[stage] = provStart;
    }

    public void setProvStarts(int[] provStarts) {
        this.provStarts = provStarts;
    }

    public int getActualStart(int stage) {
        return actualStarts[stage - 1];
    }

    public int[] getActualStarts() {
        return actualStarts;
    }

    public void setActualStart(int stage, int actualStart) {
        this.actualStarts[stage] = actualStart;
    }

    public void setActualStarts(int[] actualStarts) {
        this.actualStarts = actualStarts;
    }

}
