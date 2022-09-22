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

public class AddFinishActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AddFinishActivity.this;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private TimingCrewDatabaseHelper crewDatabaseHelper;

    private User user;
    private TimingCrew finishCrew;

    private Button saveButton;

    private ScrollView scrollView;

    private PopupWindow savePopup;

    private EditText usernameET;
    private EditText passwordET;
    private EditText postChiefET;
    private EditText phoneET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_finish);

        initViews();
        initObjects();
        initListeners();
    }

    private void initViews() {
        saveButton = findViewById(R.id.SaveNewFinishButton);
        scrollView = findViewById(R.id.AddFinishScrollView);

        usernameET = findViewById(R.id.AddFinishUsername);
        passwordET = findViewById(R.id.AddFinishPassword);
        postChiefET = findViewById(R.id.AddFinishPostChief);
        phoneET = findViewById(R.id.AddFinishPhone);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabaseHelper(activity);
        crewDatabaseHelper = new TimingCrewDatabaseHelper(activity);
        user = new User();
        finishCrew = new TimingCrew();
    }

    private void initListeners() {
        saveButton.setOnClickListener(this);
    }

    public void back(View view) {
        Intent intent = new Intent(this, FinishListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveNewFinishButton:
                if (verifyInput()) {
                    int crewID = saveFinish();
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

    public int saveFinish() {
        int crewID = -1;
        String postChief = postChiefET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        if (!crewDatabaseHelper.checkTimingCrew("Finish", postChief)) {
            TimingCrew newFinish = new TimingCrew();
            newFinish.setPostChief(postChief);
            newFinish.setPostChiefPhone(phone);
            newFinish.setPosition("Finish");
            crewDatabaseHelper.addTimingCrew(newFinish);
            crewID = crewDatabaseHelper.getCrewId("Finish", postChief);

        } else {
            Snackbar.make(scrollView, "Finish crew already exists", Snackbar.LENGTH_LONG).show();
        }
        return crewID;
    }

    public void saveUser(int crewID) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        if (!userDatabaseHelper.checkUser(username) && !userDatabaseHelper.checkUser("Finish", crewID)) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setId(crewID);
            newUser.setRole("Finish");
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