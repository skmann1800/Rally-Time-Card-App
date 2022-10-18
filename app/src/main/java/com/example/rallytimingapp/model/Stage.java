package com.example.rallytimingapp.model;

public class Stage {
    // Object for each entry to Stage database

    private int stageId; // Unique ID for this database
    private int carNum; // Car number
    private int stageNum; // Stage number
    private int startOrder; // Start Order for the stage
    private String provStart; // Provisional start time
    private String actualStart; // Actual start time
    private String finishTime; // Finish time
    private String stageTime; // Stage time
    private String actualTime; // Actual time
    private String dueTime; // Due time

    // Getters and setters for each parameter
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

    // Split the provisional start string to get only the hours portion
    public String getProvStartH() {
        if(provStart.contains(":")) {
            String[] provStartSplit = provStart.split(":");
            return provStartSplit[0];
        }
        return "";
    }

    // Split the provisional start string to get only the minutes portion
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

    // Set the provisional start time, with the given hours and minutes
    public void setProvStart(String h, String m) {
        this.provStart = h + ":" + m;
    }

    public String getActualStart() {
        return actualStart;
    }

    // Split the actual start string to get only the hours portion
    public String getActualStartH() {
        if(actualStart.contains(":")) {
            String[] actualStartSplit = actualStart.split(":");
            return actualStartSplit[0];
        }
        return "";
    }

    // Split the actual start string to get only the minutes portion
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

    // Set the actual start time, with the given hours and minutes
    public void setActualStart(String h, String m) {
        this.actualStart = h + ":" + m;
    }

    public String getFinishTime() {
        return finishTime;
    }

    // Split the finish time string to get only the hours portion
    public String getFinishTimeH() {
        if(finishTime.contains(":")) {
            String[] finishTimeSplit = finishTime.split(":");
            return finishTimeSplit[0];
        }
        return "";
    }

    // Split the finish time string to get only the minutes portion
    public String getFinishTimeM() {
        if(finishTime.contains(":")) {
            String[] finishTimeSplit = finishTime.split(":");
            if (finishTimeSplit.length > 1) {
                return finishTimeSplit[1];
            }
        }
        return "";
    }

    // Split the finish time string to get only the seconds portion
    public String getFinishTimeS() {
        if(finishTime.contains(":")) {
            String[] finishTimeSplit = finishTime.split(":");
            if (finishTimeSplit.length > 2) {
                return finishTimeSplit[2];
            }
        }
        return "";
    }

    // Split the finish time string to get only the milliseconds portion
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

    // Set the finish time, with the given hours, minutes, seconds and milliseconds
    public void setFinishTime(String h, String m, String s, String ms) {
        this.finishTime = h + ":" + m + ":" + s + ":" + ms;
    }

    public String getStageTime() {
        return stageTime;
    }

    // Split the stage time string to get only the minutes portion
    // Set the provisional start time, with the given hours and minutes
    public String getStageTimeM() {
        if(stageTime.contains(":")) {
            String[] stageTimeSplit = stageTime.split(":");
            return stageTimeSplit[0];
        }
        return "";
    }

    // Split the stage time string to get only the seconds portion
    public String getStageTimeS() {
        if(stageTime.contains(":")) {
            String[] stageTimeSplit = stageTime.split(":");
            if (stageTimeSplit.length > 1) {
                return stageTimeSplit[1];
            }
        }
        return "";
    }

    // Split the stage time string to get only the milliseconds portion
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

    // Set the stage time, with the given minutes, seconds and milliseconds
    public void setStageTime(String m, String s, String ms) {
        this.stageTime = m + ":" + s + ":" + ms;
    }

    public String getActualTime() {
        return actualTime;
    }

    // Split the actual time string to get only the hours portion
    public String getActualTimeH() {
        if(actualTime.contains(":")) {
            String[] actualTimeSplit = actualTime.split(":");
            return actualTimeSplit[0];
        }
        return "";
    }

    // Split the actual time string to get only the minutes portion
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

    // Set the actual time, with the given hours and minutes
    public void setActualTime(String h, String m) {
        this.actualTime = h + ":" + m;
    }

    public String getDueTime() {
        return dueTime;
    }

    // Split the due time string to get only the hours portion
    public String getDueTimeH() {
        if(dueTime.contains(":")) {
            String[] dueTimeSplit = dueTime.split(":");
            return dueTimeSplit[0];
        }
        return "";
    }

    // Split the due time string to get only the minutes portion
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

    // Set the due time, with the given hours and minutes
    public void setDueTime(String h, String m) {
        this.dueTime = h + ":" + m;
    }
}
