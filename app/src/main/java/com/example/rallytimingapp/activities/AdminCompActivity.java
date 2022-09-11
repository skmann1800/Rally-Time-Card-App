package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.helpers.InputValidation;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class AdminCompActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AdminCompActivity.this;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private CompDatabaseHelper compDatabaseHelper;
    private StageDatabaseHelper stageDatabaseHelper;

    private User user;
    private Competitor competitor;
    private Stage stage;

    private Button saveButton;

    private EditText usernameET;
    private EditText passwordET;
    private EditText carNumET;
    private EditText driverET;
    private EditText codriverET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_comp);

        initViews();
        initObjects();
        initListeners();

    }

    private void initViews() {
        saveButton = findViewById(R.id.saveComp);

        usernameET = findViewById(R.id.CompUsername);
        passwordET = findViewById(R.id.CompPassword);
        carNumET = findViewById(R.id.CompCarNum);
        driverET = findViewById(R.id.CompDriver);
        codriverET = findViewById(R.id.CompCodriver);
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
        Intent intent = new Intent(this, AdminOptionsActivity.class);
        startActivity(intent);
    }

    private int CreateCompetitor(int carNum, String driver, String codriver) {
        int compID = 0;
        if (!compDatabaseHelper.checkCompetitor(carNum)) {
            competitor.setCarNum(carNum);
            competitor.setDriver(driver);
            competitor.setCodriver(codriver);
            competitor.setStage1Id(CreateStage(carNum, 1));
            competitor.setStage2Id(CreateStage(carNum, 2));
            competitor.setStage3Id(CreateStage(carNum, 3));
            competitor.setStage4Id(CreateStage(carNum, 4));
            compDatabaseHelper.addCompetitor(competitor);
        }
        compID = compDatabaseHelper.getCompId(carNum);
        return compID;
    }

    private void CreateLogin(String username, String password, String role, int id) {
        if (!userDatabaseHelper.checkUser(username)) {
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            user.setId(id);
            userDatabaseHelper.addUser(user);
        }
    }

    private int CreateStage(int carNum, int stageNum) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveComp:
                if (!verifyInput()) {
                    int compID = saveCompetitor();
                    saveUser(compID);
                }
                break;
        }
    }

    public int saveCompetitor() {
        int compID = 0;
        int carNum = Integer.valueOf(carNumET.getText().toString().trim());
        String driver = driverET.getText().toString().trim();
        String codriver = codriverET.getText().toString().trim();
        if (compDatabaseHelper.checkCompetitor(driver)) {
            competitor = compDatabaseHelper.getCompetitorByDriver(driver);
            competitor.setCarNum(carNum);
            competitor.setCodriver(codriver);
            compDatabaseHelper.updateCompetitor(competitor);
            compID = competitor.getCompId();

        } else {
            compID = CreateCompetitor(carNum, driver, codriver);
        }
        return compID;
    }

    public void saveUser(int compID) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        if (userDatabaseHelper.checkUser(username)) {
            user = userDatabaseHelper.getUser(username, "Competitor");
        } else if (userDatabaseHelper.checkUser("Competitor", compID)) {

        }
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
}