package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Spinner;

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
    private Spinner crewRole;
    private EditText postChiefET;
    private EditText phoneET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crew);

        initViews();
        initObjects();
        initListeners();

        // Set-up role dropdown
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        crewRole.setAdapter(adapter);
    }

    // Method to initialise the views
    private void initViews() {
        backButton = findViewById(R.id.AddCrewBackButton);
        saveButton = findViewById(R.id.SaveNewCrewButton);
        scrollView = findViewById(R.id.AddCrewScrollView);

        label = findViewById(R.id.AddCrewDisplay);
        crewRole = findViewById(R.id.AddCrewRole);
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

    // On Click method for the button
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveNewCrewButton:
                // First check if all fields have been filled in
                if (verifyInput()) {
                    // If so, create a crew database entry and get the ID
                    int crewID = saveCrew();
                    // Clear all of the text boxes
                    clearInputs();
                } else {
                    // If not all fields have been filled in, return an error message
                    Snackbar.make(scrollView, "Please fill in all details", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.AddCrewBackButton:
                // Return to the crew list page, passing the role as an extra
                Intent intent = new Intent(this, CrewListActivity.class);
                startActivity(intent);
                break;
        }
    }

    // Method to add an entry to the crew database and return the ID
    public int saveCrew() {
        int crewID = -1;
        String role = crewRole.getSelectedItem().toString();
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

    // Method to check if all the text fields have been filled
    private boolean verifyInput() {
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
        postChiefET.setText(null);
        phoneET.setText(null);
    }
}