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
import com.example.rallytimingapp.model.TimingCrew;
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.TimingCrewDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class AddStartActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AddStartActivity.this;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private TimingCrewDatabaseHelper crewDatabaseHelper;

    private User user;
    private TimingCrew startCrew;

    private Button saveButton;

    private ScrollView scrollView;

    private EditText usernameET;
    private EditText passwordET;
    private EditText postChiefET;
    private EditText phoneET;

    private final String role = "Start";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_start);

        initViews();
        initObjects();
        initListeners();
    }

    private void initViews() {
        saveButton = findViewById(R.id.SaveNewStartButton);
        scrollView = findViewById(R.id.AddStartScrollView);

        usernameET = findViewById(R.id.AddStartUsername);
        passwordET = findViewById(R.id.AddStartPassword);
        postChiefET = findViewById(R.id.AddStartPostChief);
        phoneET = findViewById(R.id.AddStartPhone);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabaseHelper(activity);
        crewDatabaseHelper = new TimingCrewDatabaseHelper(activity);
        user = new User();
        startCrew = new TimingCrew();
    }

    private void initListeners() {
        saveButton.setOnClickListener(this);
    }

    public void back(View view) {
        Intent intent = new Intent(this, StartListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveNewStartButton:
                if (verifyInput()) {
                    int crewID = saveStart();
                    if (crewID != -1) {
                        saveUser(crewID);
                    }
                    clearInputs();
                } else {
                    Snackbar.make(scrollView, "Please fill in all details", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    public int saveStart() {
        int crewID = -1;
        String postChief = postChiefET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        if (!crewDatabaseHelper.checkTimingCrew(role, postChief)) {
            TimingCrew newFinish = new TimingCrew();
            newFinish.setPostChief(postChief);
            newFinish.setPostChiefPhone(phone);
            newFinish.setPosition(role);
            crewDatabaseHelper.addTimingCrew(newFinish);
            crewID = crewDatabaseHelper.getCrewId(role, postChief);

        } else {
            Snackbar.make(scrollView, "Start crew already exists", Snackbar.LENGTH_LONG).show();
        }
        return crewID;
    }

    public void saveUser(int crewID) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        if (!userDatabaseHelper.checkUser(username) && !userDatabaseHelper.checkUser(role, crewID)) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setId(crewID);
            newUser.setRole(role);
            userDatabaseHelper.addUser(newUser);
            Snackbar.make(scrollView, "Account created", Snackbar.LENGTH_LONG).show();

        } else {
            Snackbar.make(scrollView, "Account already exists", Snackbar.LENGTH_LONG).show();
        }
    }

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

    public void clearInputs() {
        usernameET.setText(null);
        passwordET.setText(null);
        postChiefET.setText(null);
        phoneET.setText(null);
        usernameET.requestFocus();
        usernameET.setCursorVisible(true);
    }
}