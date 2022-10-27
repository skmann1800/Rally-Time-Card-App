package com.example.rallytimingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button loginButton;
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

        // Ensure that there is always an admin login
        CreateLogin("Admin", "admin", "Admin", -1);
    }

    // Method to create a login.
    private void CreateLogin(String username, String password, String role, int id) {
        // Checks if an entry in the database with the given username already exists
        if (!userDatabaseHelper.checkUser(username)) {
            // If not, add one
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            user.setId(id);
            userDatabaseHelper.addUser(user);
        }
    }

    /* Method to create a competitor account. Not actually used, but kept just in case
    private int CreateCompetitor(int carNum, String driver, String codriver) {
        int compID = 0;
        // Checks if an entry in the database with the given car number already exists
        if (!compDatabaseHelper.checkCompetitor(carNum)) {
            // If not, create one
            competitor.setCarNum(carNum);
            competitor.setDriver(driver);
            competitor.setCodriver(codriver);
            // For each stage, create a stage database entry
            competitor.setStage1Id(CreateStage(carNum, 1));
            competitor.setStage2Id(CreateStage(carNum, 2));
            competitor.setStage3Id(CreateStage(carNum, 3));
            competitor.setStage4Id(CreateStage(carNum, 4));
            compDatabaseHelper.addCompetitor(competitor);
        }
        // Get the competitor ID to return
        compID = compDatabaseHelper.getCompId(carNum);
        return compID;
    }

    // Method to create a timing crew account. Not actually used, but kept just in case
    private int CreateTimingCrew(String position, String postChief, String phone) {
        int crewID = 0;
        // Checks if an entry in the database with the given position and post chief already exists
        if (!crewDatabaseHelper.checkTimingCrew(position, postChief)) {
            crew.setPosition(position);
            crew.setPostChief(postChief);
            crew.setPostChiefPhone(phone);
            crewDatabaseHelper.addTimingCrew(crew);
        }
        // Get the crew ID to return
        crewID = crewDatabaseHelper.getCrewId(position, postChief);
        return crewID;
    }

    // Method to create a stage database entry
    private int CreateStage(int carNum, int stageNum) {
        // Checks if an entry in the database with the given car and stage number already exists
        if (!stageDatabaseHelper.checkStage(carNum, stageNum)) {
            // If not, create one, with all times set to null
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
        // Get the stage ID to return
        int stageID = stageDatabaseHelper.getStageId(carNum, stageNum);
        return stageID;
    }*/

    // Method to initialise views
    private void initViews() {
        scrollView = findViewById(R.id.LoginScroll);

        editTextUsername = findViewById(R.id.UsernameET);
        editTextUsername.setVisibility(View.INVISIBLE);
        editTextPassword = findViewById(R.id.PasswordET);
        editTextPassword.setVisibility(View.INVISIBLE);
        loginButton = findViewById(R.id.LoginButton);
        loginButton.setVisibility(View.INVISIBLE);
        chips = findViewById(R.id.Chips);
        chips.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                int checkedChipID = chips.getCheckedChipId();
                if (checkedChipID != -1) {
                    // If a chip was selected, get the role of the chip
                    checkedChip = chips.findViewById(checkedChipID);
                    String role = checkedChip.getText().toString().trim();
                    switch (role) {
                        case "Competitor":
                        case "Admin":
                            editTextUsername.setVisibility(View.VISIBLE);
                            editTextPassword.setVisibility(View.VISIBLE);
                            loginButton.setVisibility(View.VISIBLE);
                            editTextUsername.requestFocus();
                            editTextUsername.setCursorVisible(true);
                            break;
                        case "Timing Crew":
                            editTextUsername.setVisibility(View.INVISIBLE);
                            editTextPassword.setVisibility(View.INVISIBLE);
                            loginButton.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(activity, ChooseCrewActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            }
        });
    }

    // Method to initialise objects
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

    // Method to login, used when login button clicked
    public void login(View view) {
        // Get the username and password that was inputted
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        // Get the id of the selected chip
        int checkedChipID = chips.getCheckedChipId();
        if (checkedChipID != -1) {
            // If a chip was selected, get the role of the chip
            checkedChip = chips.findViewById(checkedChipID);
            String role = checkedChip.getText().toString().trim();

            // Check if the login is valid
            if (verifyLogin(username, password, role)) {
                // If so, get a list of all of the users in the database and get the
                // id of the matching user
                userList.addAll(userDatabaseHelper.getAllUsers());
                int id = -1;
                for (int i = 0; i < userList.size(); i++) {
                    User currUser = userList.get(i);
                    if (username.equals(currUser.getUsername())) {
                        id = currUser.getId();
                    }
                }

                if (role.equals("Competitor")) {
                    // If the role is a competitor, go to the competitor view activity,
                    // passing the ID as an extra
                    Intent intent = new Intent(this, CompViewActivity.class);
                    intent.putExtra("COMP_ID", id);
                    clearInputs();
                    startActivity(intent);
                } else {
                    // If the role is a timing crew, go to the choose stage activity,
                    // passing the role label and ID as an extra
                    Intent intent = new Intent(this, ChooseStageActivity.class);
                    intent.putExtra("ROLE", role);
                    intent.putExtra("CREW_ID", id);
                    clearInputs();
                    startActivity(intent);
                }
            }
            clearInputs();
            // If there was no chip selected, see if the login was an admin
        } else if (verifyLogin(username, password, "Admin")) {
            // If so, go to the admin options page
            Intent intent = new Intent(this, AdminOptionsActivity.class);
            clearInputs();
            startActivity(intent);

        } else {
            // If not, show an error message
            Snackbar.make(scrollView, "Invalid Login", Snackbar.LENGTH_LONG).show();
        }
    }

    // Method to check if the given login details are in the database
    private boolean verifyLogin(String username, String password, String role) {
        // First check if all the input have been filled in
        if (!inputValidation.isEditTextFilled(editTextUsername)) {
            return false;
        }
        if (!inputValidation.isEditTextFilled(editTextPassword)) {
            return false;
        }
        // Then check if there is a user in the database with the given username,
        // password and role
        if (userDatabaseHelper.checkUser(username, password, role)) {
            // If so, return true
            return true;
        } else {
            // If not, show an error message
            Snackbar.make(scrollView, "Invalid Login", Snackbar.LENGTH_LONG).show();
        }
        // Return false
        return false;
    }

    // Method to clear the inputs and reset focus
    private void clearInputs() {
        chips.clearCheck();
        editTextUsername.setText(null);
        editTextPassword.setText(null);
        editTextUsername.requestFocus();
        editTextUsername.setCursorVisible(true);
    }
}