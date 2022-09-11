package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.helpers.InputValidation;
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.model.TimingCrew;
import com.example.rallytimingapp.sql.TimingCrewDatabaseHelper;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final AppCompatActivity activity = MainActivity.this;

    private EditText editTextUsername;
    private EditText editTextPassword;
    private ChipGroup chips;
    private Chip checkedChip;

    private ScrollView scrollView;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private CompDatabaseHelper compDatabaseHelper;
    private TimingCrewDatabaseHelper crewDatabaseHelper;
    private StageDatabaseHelper stageDatabaseHelper;
    private User user;
    private Competitor competitor;
    private TimingCrew crew;
    private Stage stage;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initViews();
        initObjects();

        int compID1 = CreateCompetitor(1, "Hayden Paddon", "John Kennard");
        int compID2 = CreateCompetitor(5, "Emma Gilmour", "Mal Peden");
        int compID3 = CreateCompetitor(2, "Ben Hunt", "Tony Rawstorn");
        int compID4 = CreateCompetitor(12, "Jack Hawkeswood", "Sarah Brenna");

        int crewID1 = CreateTimingCrew("A Control", "George", "0219384756");
        int crewID2 = CreateTimingCrew("Start", "Jared", "0212349879");
        int crewID3 = CreateTimingCrew("Finish", "Sarah", "0279125769");

        CreateLogin("Hayden", "hayden", "Competitor", compID1);
        CreateLogin("Emma", "emma", "Competitor", compID2);
        CreateLogin("Ben", "ben","Competitor", compID3);
        CreateLogin("Jack", "jack", "Competitor", compID4);
        CreateLogin("Jared", "start", "Start", crewID1);
        CreateLogin("Sarah", "finish", "Finish", crewID2);
        CreateLogin("George", "ac", "A Control", crewID3);
        CreateLogin("Admin", "admin", "Admin", -1);
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

    private int CreateTimingCrew(String position, String postChief, String phone) {
        int crewID = 0;
        if (!crewDatabaseHelper.checkTimingCrew(position, postChief)) {
            crew.setPosition(position);
            crew.setPostChief(postChief);
            crew.setPostChiefPhone(phone);
            crewDatabaseHelper.addTimingCrew(crew);
        }
        crewID = crewDatabaseHelper.getCrewId(position, postChief);
        return crewID;
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

    private void initViews() {
        scrollView = findViewById(R.id.LoginScroll);

        editTextUsername = findViewById(R.id.UsernameET);
        editTextPassword = findViewById(R.id.PasswordET);
        chips = findViewById(R.id.Chips);
    }

    private void initObjects() {
        userDatabaseHelper = new UserDatabaseHelper(activity);
        compDatabaseHelper = new CompDatabaseHelper(activity);
        crewDatabaseHelper = new TimingCrewDatabaseHelper(activity);
        stageDatabaseHelper = new StageDatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        user = new User();
        competitor = new Competitor();
        crew = new TimingCrew();
        stage = new Stage();
        userList = new ArrayList<>();
    }

    public void login(View view) {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        int checkedChipID = chips.getCheckedChipId();
        if (checkedChipID != -1) {
            checkedChip = chips.findViewById(checkedChipID);
            String role = checkedChip.getText().toString().trim();

            if (verifyLogin(username, password, role)) {
                userList.addAll(userDatabaseHelper.getAllUsers());
                int id = -1;
                for (int i = 0; i < userList.size(); i++) {
                    User currUser = userList.get(i);
                    if (username.equals(currUser.getUsername())) {
                        id = currUser.getId();
                    }
                }

                if (role.equals("Competitor")) {
                    Intent intent = new Intent(this, CompViewActivity.class);
                    intent.putExtra("COMP_ID", id);
                    clearInputs();
                    startActivity(intent);
                } else if (role.equals("Finish")) {
                    Intent intent = new Intent(this, ChooseFinishActivity.class);
                    intent.putExtra("CREW_ID", id);
                    clearInputs();
                    startActivity(intent);
                } else if (role.equals("Start")) {
                    Intent intent = new Intent(this, ChooseStartActivity.class);
                    intent.putExtra("CREW_ID", id);
                    clearInputs();
                    startActivity(intent);
                } else if (role.equals("A Control")) {
                    Intent intent = new Intent(this, ChooseControlActivity.class);
                    intent.putExtra("CREW_ID", id);
                    clearInputs();
                    startActivity(intent);
                }
            }
            clearInputs();
        } else if (verifyLogin(username, password, "Admin")) {
            Intent intent = new Intent(this, AdminOptionsActivity.class);
            clearInputs();
            startActivity(intent);

        } else {
            Snackbar.make(scrollView, "Invalid Login", Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean verifyLogin(String username, String password, String role) {
        if (!inputValidation.isEditTextFilled(editTextUsername)) {
            return false;
        }
        if (!inputValidation.isEditTextFilled(editTextPassword)) {
            return false;
        }
        if (userDatabaseHelper.checkUser(username, password, role)) {
            return true;
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(scrollView, "Invalid Login", Snackbar.LENGTH_LONG).show();
        }
        return false;
    }

    private void clearInputs() {
        chips.clearCheck();
        editTextUsername.setText(null);
        editTextPassword.setText(null);
        editTextUsername.requestFocus();
        editTextUsername.setCursorVisible(true);
    }
}