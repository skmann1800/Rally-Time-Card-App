package com.example.rallytimingapp.model;

public class EventSections {
    // Each entry to this database will contain the details for a
    // single section of a timecard and will dictate the UI
    private int sectionId; // Unique ID for this database
    private Boolean isStage; // Determines if the icons, finish time and time taken are visible
    private Boolean hasStartOrder; // Determines if start order oval is visible
    private Boolean hasProvStart; // Determines if the provisional start is visible
    private String currentTC; // Current TC number and letter
    private String nextTC; // Next TC number and letter
    private String TCName; // TC name
    private String stageDistance; // Distance of stage
    private String TCDistance; // Total distance of TC
    private String averageSpeed; // Speed to complete distance in target time
    private String targetTimeHours; // Text to go in the target time hours box
    private String targetTimeMinutes; // Text to go in the target time minutes box

    public int getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public Boolean getIsStage() {
        return this.isStage;
    }

    public void setIsStage(Boolean isStage) {
        this.isStage = isStage;
    }

    public Boolean getHasStartOrder() {
        return this.hasStartOrder;
    }

    public void setHasStartOrder(Boolean hasStartOrder) {
        this.hasStartOrder = hasStartOrder;
    }

    public Boolean getHasProvStart() {
        return this.hasProvStart;
    }

    public void setHasProvStart(Boolean hasProvStart) {
        this.hasProvStart = hasProvStart;
    }

    public String getCurrentTC() {
        return this.currentTC;
    }

    public void setCurrentTC(String currentTC) {
        this.currentTC = currentTC;
    }

    public String getNextTC() {
        return this.nextTC;
    }

    public void setNextTC(String nextTC) {
        this.nextTC = nextTC;
    }

    public String getTCName() {
        return this.TCName;
    }

    public void setTCName(String TCName) {
        this.TCName = TCName;
    }

    public String getStageDistance() {
        return this.stageDistance;
    }

    public void setStageDistance(String stageDistance) {
        this.stageDistance = stageDistance;
    }

    public String getTCDistance() {
        return this.TCDistance;
    }

    public void setTCDistance(String TCDistance) {
        this.TCDistance = TCDistance;
    }

    public String getAverageSpeed() {
        return this.averageSpeed;
    }

    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getTargetTimeHours() {
        return this.targetTimeHours;
    }

    public void setTargetTimeHours(String targetTimeHours) {
        this.targetTimeHours = targetTimeHours;
    }

    public String getTargetTimeMinutes() {
        return this.targetTimeMinutes;
    }

    public void setTargetTimeMinutes(String targetTimeMinutes) {
        this.targetTimeMinutes = targetTimeMinutes;
    }
}
