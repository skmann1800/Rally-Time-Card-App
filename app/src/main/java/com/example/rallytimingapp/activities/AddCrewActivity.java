package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.helpers.InputValidation;
import com.example.rallytimingapp.model.TimingCrew;
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.TimingCrewDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class AddCrewActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AddCrewActivity.this;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private TimingCrewDatabaseHelper crewDatabaseHelper;

    private Button backButton;
    private Button saveButton;

    private ScrollView scrollView;

    private TextView label;
    private EditText usernameET;
    private EditText passwordET;
    private EditText postChiefET;
    private EditText phoneET;

    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crew);

        initViews();
        initObjects();
        initListeners();

        role = getIntent().getStringExtra("ROLE");
        label.setText("Add New " + role + " Crew Login");
    }

    // Method to initialise the views
    private void initViews() {
        backButton = findViewById(R.id.AddCrewBackButton);
        saveButton = findViewById(R.id.SaveNewCrewButton);
        scrollView = findViewById(R.id.AddCrewScrollView);

        label = findViewById(R.id.AddCrewDisplay);
        usernameET = findViewById(R.id.AddCrewUsername);
        passwordET = findViewById(R.id.AddCrewPassword);
        postChiefET = findViewById(R.id.AddCrewPostChief);
        phoneET = findViewById(R.id.AddCrewPhone);
    }

    // Method to initialise the objects
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabaseHelper(activity);
        crewDatabaseHelper = new TimingCrewDatabaseHelper(activity);
    }

    // Method to initialise the listener for the save button
    private void initListeners() {
        backButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    // Method to return to the crew list page, passing the role as an extra
    private void back() {
        Intent intent = new Intent(this, CrewListActivity.class);
        intent.putExtra("ROLE", role);
        startActivity(intent);

    }

    // On Click method for the button
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveNewCrewButton:
                // First check if all fields have been filled in
                if (verifyInput()) {
                    // If so, create a crew database entry and get the ID
                    int crewID = saveCrew();
                    // If a crew entry already existed, shown by an ID of -1,
                    // then do not save a user
                    if (crewID != -1) {
                        // Otherwise save a user account, with the ID given above
                        saveUser(crewID);
                    }
                    // Clear all of the text boxes
                    clearInputs();
                } else {
                    // If not all fields have been filled in, return an error message
                    Snackbar.make(scrollView, "Please fill in all details", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.AddCompBackButton:
                back();
                break;
        }
    }

    // Method to add an entry to the crew database and return the ID
    public int saveCrew() {
        int crewID = -1;
        String postChief = postChiefET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        // Check if there is already an entry in the database with that role and post
        // chief name first
        if (!crewDatabaseHelper.checkTimingCrew(role, postChief)) {
            // If there isn't, add one
            TimingCrew newCrew = new TimingCrew();
            newCrew.setPostChief(postChief);
            newCrew.setPostChiefPhone(phone);
            newCrew.setPosition(role);
            crewDatabaseHelper.addTimingCrew(newCrew);
            // Get the ID assigned to that entry to return
            crewID = crewDatabaseHelper.getCrewId(role, postChief);

        } else {
            // If there is already an entry, return an error message and an ID of -1
            Snackbar.make(scrollView, role + " Crew already exists", Snackbar.LENGTH_LONG).show();
        }
        return crewID;
    }

    // Method to add an entry to the user database
    public void saveUser(int crewID) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        // Check if there is already an entry in the database with that username
        // or role and crew ID first
        if (!userDatabaseHelper.checkUser(username) && !userDatabaseHelper.checkUser(role, crewID)) {
            // If there isn't, add one
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setId(crewID);
            newUser.setRole(role);
            userDatabaseHelper.addUser(newUser);
            // Show a message stating that the account has been created
            Snackbar.make(scrollView, "Account created", Snackbar.LENGTH_LONG).show();

        } else {
            // If an account already exists, return the following error message
            Snackbar.make(scrollView, "Account already exists", Snackbar.LENGTH_LONG).show();
        }
    }

    // Method to check if all the text fields have been filled
    private boolean verifyInput() {
        if (!inputValidation.isEditTextFilled(usernameET)) {
            return false;
        }
        if (!inputValidation.isEditTextFilled(passwordET)) {
            return false;
        }
        if (!inputValidation.isEditTextFilled(postChiefET)) {
            return false;
        }
        if (!inputValidation.isEditTextFilled(phoneET)) {
            return false;
        }
        return true;
    }

    // Method to clear all of the text fields and reset the focus
    public void clearInputs() {
        usernameET.setText(null);
        passwordET.setText(null);
        postChiefET.setText(null);
        phoneET.setText(null);
        usernameET.requestFocus();
        usernameET.setCursorVisible(true);
    }
}