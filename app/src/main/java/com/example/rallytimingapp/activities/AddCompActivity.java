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

    private Button saveButton;

    private ScrollView scrollView;

    private PopupWindow savePopup;

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

    private void initViews() {
        saveButton = findViewById(R.id.SaveNewCompButton);
        scrollView = findViewById(R.id.AddCompScrollView);

        usernameET = findViewById(R.id.AddCompUsername);
        passwordET = findViewById(R.id.AddCompPassword);
        carNumET = findViewById(R.id.AddCompCarNum);
        driverET = findViewById(R.id.AddCompDriver);
        codriverET = findViewById(R.id.AddCompCodriver);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabaseHelper(activity);
        compDatabaseHelper = new CompDatabaseHelper(activity);
        stageDatabaseHelper = new StageDatabaseHelper(activity);
        user = new User();
        competitor = new Competitor();
        stage = new Stage();
    }

    private void initListeners() {
        saveButton.setOnClickListener(this);
    }

    public void back(View view) {
        Intent intent = new Intent(this, CompListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveNewCompButton:
                if (verifyInput()) {
                    int compID = saveCompetitor();
                    if (compID != -1) {
                        saveUser(compID);
                    }
                    clearInputs();
                } else {
                    Snackbar.make(scrollView, "Please fill in all details", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    public int saveCompetitor() {
        int compID = -1;
        int carNum = Integer.valueOf(carNumET.getText().toString().trim());
        String driver = driverET.getText().toString().trim();
        String codriver = codriverET.getText().toString().trim();
        if (!compDatabaseHelper.checkCompetitor(driver)) {
            Competitor newComp = new Competitor();
            newComp.setCarNum(carNum);
            newComp.setDriver(driver);
            newComp.setCodriver(codriver);
            newComp.setStage1Id(newStage(carNum,1));
            newComp.setStage2Id(newStage(carNum,2));
            newComp.setStage3Id(newStage(carNum,3));
            newComp.setStage4Id(newStage(carNum,4));
            compDatabaseHelper.addCompetitor(newComp);
            compID = compDatabaseHelper.getCompId(carNum);

        } else {
            Snackbar.make(scrollView, "Competitor already exists", Snackbar.LENGTH_LONG).show();
        }
        return compID;
    }

    public void saveUser(int compID) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        if (!userDatabaseHelper.checkUser(username) && !userDatabaseHelper.checkUser("Competitor", compID)) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setId(compID);
            newUser.setRole("Competitor");
            userDatabaseHelper.addUser(newUser);
            Snackbar.make(scrollView, "Account created", Snackbar.LENGTH_LONG).show();

        } else {
            Snackbar.make(scrollView, "Account already exists", Snackbar.LENGTH_LONG).show();
        }
    }

    private int newStage(int carNum, int stageNum) {
        if (!stageDatabaseHelper.checkStage(carNum, stageNum)) {
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
        int stageID = stageDatabaseHelper.getStageId(carNum, stageNum);
        return stageID;
    }

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