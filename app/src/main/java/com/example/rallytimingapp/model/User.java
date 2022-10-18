package com.example.rallytimingapp.model;

public class User {
    // Object for each entry to Timing Crew database

    private int userId; // Unique ID for this database
    private String username; // Username for logging in with
    private String password; // Password for logging in with
    private String role; // Role, ie Competitor, A Control, Start, Finish or Admin
    private int id; // Unique ID relating to users ID in their roles database

    // Getters and setters for each parameter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
