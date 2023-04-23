package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.model.TimingCrew;
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.AControlDatabaseHelper;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.sql.FinishDatabaseHelper;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.example.rallytimingapp.sql.StartDatabaseHelper;
import com.example.rallytimingapp.sql.TimingCrewDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;

public class AdminOptionsActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AdminOptionsActivity.this;

    private Button competitorButton;
    private Button crewButton;
    private Button createEventButton;
    private Button editEventButton;
    private Button signOutButton;
    private Button resetButton;

    private AControlDatabaseHelper aControlDatabaseHelper;
    private StartDatabaseHelper startDatabaseHelper;
    private FinishDatabaseHelper finishDatabaseHelper;
    private UserDatabaseHelper userDatabaseHelper;
    private CompDatabaseHelper compDatabaseHelper;
    private StageDatabaseHelper stageDatabaseHelper;
    private TimingCrewDatabaseHelper timingCrewDatabaseHelper;

    private User user;
    private Competitor competitor;
    private TimingCrew crew;
    private Stage stage;

    private PopupWindow resetPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        initViews();
        initListeners();
        initObjects();
    }

    // Method to initialise views
    private void initViews() {
        competitorButton = findViewById(R.id.CompAccountsButton);
        crewButton = findViewById(R.id.CrewAccountsButton);
        createEventButton = findViewById(R.id.CreateEventButton);
        editEventButton = findViewById(R.id.EditEventButton);
        signOutButton = findViewById(R.id.AOSignOutButton);
        resetButton = findViewById(R.id.AOResetButton);
    }

    // Method to initialise listeners for the buttons
    private void initListeners() {
        competitorButton.setOnClickListener(this);
        crewButton.setOnClickListener(this);
        createEventButton.setOnClickListener(this);
        editEventButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    // Method to initialise objects
    private void initObjects() {
        userDatabaseHelper = new UserDatabaseHelper(activity);
        compDatabaseHelper = new CompDatabaseHelper(activity);
        timingCrewDatabaseHelper = new TimingCrewDatabaseHelper(activity);
        stageDatabaseHelper = new StageDatabaseHelper(activity);
        aControlDatabaseHelper = new AControlDatabaseHelper(activity);
        startDatabaseHelper = new StartDatabaseHelper(activity);
        finishDatabaseHelper = new FinishDatabaseHelper(activity);
        user = new User();
        competitor = new Competitor();
        crew = new TimingCrew();
        stage = new Stage();
    }

    // On Click Method for the buttons
    @Override
    public void onClick(View view) {
        // Switch case for each button
        Intent intent;
        switch (view.getId()) {
            case R.id.CompAccountsButton:
                // Competitor button goes to the Comp List Activity
                intent = new Intent(this, CompListActivity.class);
                startActivity(intent);
                break;
            case R.id.CrewAccountsButton:
                intent = new Intent(this, CrewListActivity.class);
                startActivity(intent);
                break;
            case R.id.CreateEventButton:
                intent = new Intent(this, CreateEventActivity.class);
                startActivity(intent);
                break;
            case R.id.AOSignOutButton:
                // Sign out button returns to the main login page
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.AOResetButton:
                // Reset button shows a pop-up
                ShowResetPopup();
                break;
        }
    }

    // Method which resets all the databases and creates the basic accounts
    public void resetAll() {
        // Empty all the databases
        aControlDatabaseHelper.empty();
        startDatabaseHelper.empty();
        finishDatabaseHelper.empty();
        userDatabaseHelper.empty();
        compDatabaseHelper.empty();
        stageDatabaseHelper.empty();
        timingCrewDatabaseHelper.empty();

        // First create competitor database entries with the below data and save their IDs
        int compID1 = CreateCompetitor(1, "Hayden Paddon", "John Kennard");
        int compID2 = CreateCompetitor(5, "Emma Gilmour", "Mal Peden");
        int compID3 = CreateCompetitor(2, "Ben Hunt", "Tony Rawstorn");
        int compID4 = CreateCompetitor(12, "Jack Hawkeswood", "Sarah Brenna");

        // Then create timing crew database entries with the below data and save their IDs
        CreateTimingCrew("A Control", "George", "0219384756");
        CreateTimingCrew("Start", "Jared", "0212349879");
        CreateTimingCrew("Finish", "Sarah", "0279125769");

        // Then create logins using the below data and IDs from above
        CreateLogin("Hayden", "hayden", "Competitor", compID1);
        CreateLogin("Emma", "emma", "Competitor", compID2);
        CreateLogin("Ben", "ben","Competitor", compID3);
        CreateLogin("Jack", "jack", "Competitor", compID4);
        CreateLogin("Admin", "admin", "Admin", -1);
    }

    // Method to create a competitor database entry
    private int CreateCompetitor(int carNum, String driver, String codriver) {
        int compID = 0;
        // Check if an entry with the given car number already exists
        if (!compDatabaseHelper.checkCompetitor(carNum)) {
            // If not, create one
            competitor.setCarNum(carNum);
            competitor.setDriver(driver);
            competitor.setCodriver(codriver);
            // For each stage for this competitor, create a stage database entry
            competitor.setStage1Id(CreateStage(carNum, 1));
            competitor.setStage2Id(CreateStage(carNum, 2));
            competitor.setStage3Id(CreateStage(carNum, 3));
            competitor.setStage4Id(CreateStage(carNum, 4));
            // Add the competitor to the database
            compDatabaseHelper.addCompetitor(competitor);
        }
        // Get the ID of the database entry and return it
        compID = compDatabaseHelper.getCompId(carNum);
        return compID;
    }

    // Method to create a timing crew database entry
    private void CreateTimingCrew(String position, String postChief, String phone) {
        // Check if an entry with the given position and post chief already exists
        if (!timingCrewDatabaseHelper.checkTimingCrew(position, postChief)) {
            // If not, create one
            crew.setPosition(position);
            crew.setPostChief(postChief);
            crew.setPostChiefPhone(phone);
            // Add the timing crew to the database
            timingCrewDatabaseHelper.addTimingCrew(crew);
        }
    }

    // Method to create a login
    private void CreateLogin(String username, String password, String role, int id) {
        // Check if an entry with the given username already exists
        if (!userDatabaseHelper.checkUser(username)) {
            // If not, create one
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            user.setId(id);
            // Add the login to the database
            userDatabaseHelper.addUser(user);
        }
    }

    // Method to create a stage database entry
    private int CreateStage(int carNum, int stageNum) {
        // Check if an entry with the given car and stage number already exists
        if (!stageDatabaseHelper.checkStage(carNum, stageNum)) {
            // If not, create one. Set all values to 0 or null.
            stage.setCarNum(carNum);
            stage.setStageNum(stageNum);
            stage.setStartOrder(0);
            stage.setProvStart("");
            stage.setActualStart("");
            stage.setFinishTime("");
            stage.setStageTime("");
            stage.setActualTime("");
            stage.setDueTime("");
            // Add the stage to the database
            stageDatabaseHelper.addStage(stage);
        }
        // Get the ID of the database entry and return it
        int stageID = stageDatabaseHelper.getStageId(carNum, stageNum);
        return stageID;
    }

    // Method to show the reset pop-up that is shown when the reset button is clicked
    private void ShowResetPopup() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.reset_popup, null);

        resetPopup = new PopupWindow(this);
        resetPopup.setContentView(layout);
        resetPopup.setWidth(width);
        resetPopup.setHeight(height);
        resetPopup.setFocusable(true);
        resetPopup.setBackgroundDrawable(null);
        resetPopup.showAtLocation(layout, Gravity.CENTER, 1, 1);

        // Set listener for yes button
        Button yesReset = layout.findViewById(R.id.YesResetButton);
        yesReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calls the reset all method then dismisses pop-up
                resetAll();
                resetPopup.dismiss();
            }
        });

        // Set listener for no button, which just dismissed the pop-up
        Button noReset = layout.findViewById(R.id.NoResetButton);
        noReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPopup.dismiss();
            }
        });
    }
}
