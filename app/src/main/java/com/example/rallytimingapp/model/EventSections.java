package com.example.rallytimingapp.model;

public class EventSections {
    // Each entry to this database will contain the details for a
    // single section of a timecard and will dictate the UI
    private int sectionId; // Unique ID for this database
    private String blue1Text; // Text to go into the top section of the blue strip
    private String blue2Text; // Text to go into the middle section of the blue strip
    private String blue3Text; // Text to go into the bottom section of the blue strip
    private String sectionLabel1; // Text to be the first line of red text in top left of card
    private String sectionLabel2; // Text to be in the second line of the red text in top left of card
    private String provStartLabel; // Text to go under the prov start boxes. If null box will not be visible
    private String actualStartLabel; // Text to go under the actual start boxes.
    private Boolean isStage; // Determines if the icons, finish time and time taken are visible
    private String timeTakenLabel; // Text to go under the time taken boxes
    private Boolean hasStartOrder; // Determines if start order oval is visible
    private String targetTimeHours; // Text to go in the target time hours box
    private String targetTimeMinutes; // Text to go in the target time minutes box
    private String nextTCLabel; // Text to go under the actual time and due time boxes

    public int getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getBlue1Text() {
        return this.blue1Text;
    }

    public void setBlue1Text(String blue1Text) {
        this.blue1Text = blue1Text;
    }

    public String getBlue2Text() {
        return this.blue2Text;
    }

    public void setBlue2Text(String blue2Text) {
        this.blue2Text = blue2Text;
    }

    public String getBlue3Text() {
        return this.blue3Text;
    }

    public void setBlue3Text(String blue3Text) {
        this.blue3Text = blue3Text;
    }

    public String getSectionLabel1() {
        return this.sectionLabel1;
    }

    public void setSectionLabel1(String sectionLabel1) {
        this.sectionLabel1 = sectionLabel1;
    }

    public String getSectionLabel2() {
        return this.sectionLabel2;
    }

    public void setSectionLabel2(String sectionLabel2) {
        this.sectionLabel2 = sectionLabel2;
    }

    public String getProvStartLabel() {
        return this.provStartLabel;
    }

    public void setProvStartLabel(String provStartLabel) {
        this.provStartLabel = provStartLabel;
    }

    public String getActualStartLabel() {
        return this.actualStartLabel;
    }

    public void setActualStartLabel(String actualStartLabel) {
        this.actualStartLabel = actualStartLabel;
    }

    public Boolean getIsStage() {
        return this.isStage;
    }

    public void setIsStage(Boolean isStage) {
        this.isStage = isStage;
    }

    public String getTimeTakenLabel() {
        return this.timeTakenLabel;
    }

    public void setTimeTakenLabel(String timeTakenLabel) {
        this.timeTakenLabel = timeTakenLabel;
    }

    public Boolean getHasStartOrder() {
        return this.hasStartOrder;
    }

    public void setHasStartOrder(Boolean hasStartOrder) {
        this.hasStartOrder = hasStartOrder;
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

    public String getNextTCLabel() {
        return this.nextTCLabel;
    }

    public void setNextTCLabel(String nextTCLabel) {
        this.nextTCLabel = nextTCLabel;
    }
}
