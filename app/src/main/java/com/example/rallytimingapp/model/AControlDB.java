package com.example.rallytimingapp.model;

public class AControlDB {

    private int id;
    private int carNum;
    private int provStart;
    private int actualTime;

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

    public int getProvStart() {
        return provStart;
    }

    public void setProvStart(int provStart) {
        this.provStart = provStart;
    }

    public int getActualTime() {
        return actualTime;
    }

    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }
}
