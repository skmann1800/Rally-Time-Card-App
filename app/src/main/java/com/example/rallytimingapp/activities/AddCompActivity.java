package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.helpers.InputValidation;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class AddCompActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AddCompActivity.this;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private CompDatabaseHelper compDatabaseHelper;
    private StageDatabaseHelper stageDatabaseHelper;

    private User user;
    private Competitor competitor;
    private Stage stage;

    private Button backButton;
    private Button saveButton;

    private ScrollView scrollView;

    private EditText usernameET;
    private EditText passwordET;
    private EditText carNumET;
    private EditText driverET;
    private EditText codriverET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comp);

        initViews();
        initObjects();
        initListeners();
    }

    // Method to initialise the views
    private void initViews() {
        backButton = findViewById(R.id.AddCompBackButton);
        saveButton = findViewById(R.id.SaveNewCompButton);
        scrollView = findViewById(R.id.AddCompScrollView);

        usernameET = findViewById(R.id.AddCompUsername);
        passwordET = findViewById(R.id.AddCompPassword);
        carNumET = findViewById(R.id.AddCompCarNum);
        driverET = findViewById(R.id.AddCompDriver);
        codriverET = findViewById(R.id.AddCompCodriver);
    }

    // Method to initialise the objects
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabaseHelper(activity);
        compDatabaseHelper = new CompDatabaseHelper(activity);
        stageDatabaseHelper = new StageDatabaseHelper(activity);
        user = new User();
        competitor = new Competitor();
        stage = new Stage();
    }

    // Method to initialise the listener for the save button
    private void initListeners() {
        backButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    // On Click Method for the save button
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveNewCompButton:
                // First check if all fields have been filled in
                if (verifyInput()) {
                    // If so, create a competitor database entry and get the ID
                    int compID = saveCompetitor();
                    // If a competitor entry already existed, shown by an ID of -1,
                    // then do not save a user
                    if (compID != -1) {
                        // Otherwise save a user account, with the ID given above
                        saveUser(compID);
                    }
                    // Clear all of the text boxes
                    clearInputs();
                } else {
                    // If not all fields have been filled in, return an error message
                    Snackbar.make(scrollView, "Please fill in all details", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.AddCompBackButton:
                // Return to the Competitor List page
                Intent intent = new Intent(this, CompListActivity.class);
                startActivity(intent);
                break;
        }
    }

    // Method to add an entry to the competitor database and return the ID
    public int saveCompetitor() {
        int compID = -1;
        int carNum = Integer.valueOf(carNumET.getText().toString().trim());
        String driver = driverET.getText().toString().trim();
        String codriver = codriverET.getText().toString().trim();
        // Check if there is already an entry in the database with that driver name first
        if (!compDatabaseHelper.checkCompetitor(driver)) {
            // If there isn't, add one
            Competitor newComp = new Competitor();
            newComp.setCarNum(carNum);
            newComp.setDriver(driver);
            newComp.setCodriver(codriver);
            // For each stage for this competitor, make a stage database entry
            newComp.setStage1Id(newStage(carNum,1));
            newComp.setStage2Id(newStage(carNum,2));
            newComp.setStage3Id(newStage(carNum,3));
            newComp.setStage4Id(newStage(carNum,4));
            compDatabaseHelper.addCompetitor(newComp);
            // Get the ID assigned to that entry to return
            compID = compDatabaseHelper.getCompId(carNum);

        } else {
            // If there is already an entry, return an error message and an ID of -1
            Snackbar.make(scrollView, "Competitor already exists", Snackbar.LENGTH_LONG).show();
        }
        return compID;
    }

    // Method to add an entry to the user database
    public void saveUser(int compID) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        // Check if there is already an entry in the database with that username
        // or role and crew ID first
        if (!userDatabaseHelper.checkUser(username) && !userDatabaseHelper.checkUser("Competitor", compID)) {
            // If there isn't, add one
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setId(compID);
            newUser.setRole("Competitor");
            userDatabaseHelper.addUser(newUser);
            // Show a message stating that the account has been created
            Snackbar.make(scrollView, "Account created", Snackbar.LENGTH_LONG).show();

        } else {
            // If an account already exists, return the following error message
            Snackbar.make(scrollView, "Account already exists", Snackbar.LENGTH_LONG).show();
        }
    }

    // Method to add an entry to the stage database, with the given car and stage number
    // and return the ID
    private int newStage(int carNum, int stageNum) {
        // Check if there is already an entry in the database with that car and stage number first
        if (!stageDatabaseHelper.checkStage(carNum, stageNum)) {
            // If there isn't, add one
            stage.setCarNum(carNum);
            stage.setStageNum(stageNum);
            stage.setStartOrder(0);
            stage.setProvStart("");
            stage.setActualStart("");
            stage.setFinishTime("");
            stage.setStageTime("");
            stage.setActualTime("");
            stage.setDueTime("");
            stageDatabaseHelper.addStage(stage);
        }
        // Get the ID for that stage and return it
        int stageID = stageDatabaseHelper.getStageId(carNum, stageNum);
        return stageID;
    }

    // Method to check if all the text fields have been filled
    private boolean verifyInput() {
        if (!inputValidation.isEditTextFilled(usernameET)) {
            return false;
        }
        if (!inputValidation.isEditTextFilled(passwordET)) {
            return false;
        }
        if (!inputValidation.isEditTextFilled(carNumET)) {
            return false;
        }
        if (!inputValidation.isEditTextFilled(driverET)) {
            return false;
        }
        if (!inputValidation.isEditTextFilled(codriverET)) {
            return false;
        }
        return true;
    }

    // Method to clear all of the text fields and reset the focus
    public void clearInputs() {
        usernameET.setText(null);
        passwordET.setText(null);
        carNumET.setText(null);
        driverET.setText(null);
        codriverET.setText(null);
        usernameET.requestFocus();
        usernameET.setCursorVisible(true);
    }
}