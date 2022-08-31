package com.example.rallytimingapp.model;

public class Stage {

    private int stageId;
    private int carNum;
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

    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
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

    public String getProvStartH() {
        if(provStart.contains(":")) {
            String[] provStartSplit = provStart.split(":");
            return provStartSplit[0];
        }
        return "";
    }

    public String getProvStartM() {
        if(provStart.contains(":")) {
            String[] provStartSplit = provStart.split(":");
            if (provStartSplit.length > 1) {
                return provStartSplit[1];
            }
        }
        return "";
    }

    public void setProvStart(String provStart) {
        this.provStart = provStart;
    }

    public String getActualStart() {
        return actualStart;
    }

    public String getActualStartH() {
        if(actualStart.contains(":")) {
            String[] actualStartSplit = actualStart.split(":");
            return actualStartSplit[0];
        }
        return "";
    }

    public String getActualStartM() {
        if(actualStart.contains(":")) {
            String[] actualStartSplit = actualStart.split(":");
            if (actualStartSplit.length > 1) {
                return actualStartSplit[1];
            }
        }
        return "";
    }

    public void setActualStart(String actualStart) {
        this.actualStart = actualStart;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public String getFinishTimeH() {
        if(finishTime.contains(":")) {
            String[] finishTimeSplit = finishTime.split(":");
            return finishTimeSplit[0];
        }
        return "";
    }

    public String getFinishTimeM() {
        if(finishTime.contains(":")) {
            String[] finishTimeSplit = finishTime.split(":");
            if (finishTimeSplit.length > 1) {
                return finishTimeSplit[1];
            }
        }
        return "";
    }

    public String getFinishTimeS() {
        if(finishTime.contains(":")) {
            String[] finishTimeSplit = finishTime.split(":");
            if (finishTimeSplit.length > 2) {
                return finishTimeSplit[2];
            }
        }
        return "";
    }

    public String getFinishTimeMS() {
        if(finishTime.contains(":")) {
            String[] finishTimeSplit = finishTime.split(":");
            if (finishTimeSplit.length > 3) {
                return finishTimeSplit[3];
            }
        }
        return "";
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getStageTime() {
        return stageTime;
    }

    public String getStageTimeM() {
        if(stageTime.contains(":")) {
            String[] stageTimeSplit = stageTime.split(":");
            return stageTimeSplit[0];
        }
        return "";
    }

    public String getStageTimeS() {
        if(stageTime.contains(":")) {
            String[] stageTimeSplit = stageTime.split(":");
            if (stageTimeSplit.length > 1) {
                return stageTimeSplit[1];
            }
        }
        return "";
    }

    public String getStageTimeMS() {
        if(stageTime.contains(":")) {
            String[] stageTimeSplit = stageTime.split(":");
            if (stageTimeSplit.length > 2) {
                return stageTimeSplit[2];
            }
        }
        return "";
    }

    public void setStageTime(String stageTime) {
        this.stageTime = stageTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public String getActualTimeH() {
        if(actualTime.contains(":")) {
            String[] actualTimeSplit = actualTime.split(":");
            return actualTimeSplit[0];
        }
        return "";
    }

    public String getActualTimeM() {
        if(actualTime.contains(":")) {
            String[] actualTimeSplit = actualTime.split(":");
            if (actualTimeSplit.length > 1) {
                return actualTimeSplit[1];
            }
        }
        return "";
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getDueTimeH() {
        if(dueTime.contains(":")) {
            String[] dueTimeSplit = dueTime.split(":");
            return dueTimeSplit[0];
        }
        return "";
    }

    public String getDueTimeM() {
        if(dueTime.contains(":")) {
            String[] dueTimeSplit = dueTime.split(":");
            if (dueTimeSplit.length > 1) {
                return dueTimeSplit[1];
            }
        }
        return "";
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
}
