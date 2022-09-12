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
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.helpers.InputValidation;
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

import java.util.ArrayList;
import java.util.List;

public class AdminOptionsActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AdminOptionsActivity.this;

    private Button competitorButton;
    private Button aControlButton;
    private Button startButton;
    private Button finishButton;
    private Button signOutButton;
    private Button resetButton;

    private ScrollView scrollView;

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

    private void initViews() {
        competitorButton = findViewById(R.id.CompetitorRoleButton);
        aControlButton = findViewById(R.id.AControlRoleButton);
        startButton = findViewById(R.id.StartRoleButton);
        finishButton = findViewById(R.id.FinishRoleButton);
        signOutButton = findViewById(R.id.AOSignOutButton);
        resetButton = findViewById(R.id.AOResetButton);

        scrollView = findViewById(R.id.LoginScroll);
    }

    private void initListeners() {
        competitorButton.setOnClickListener(this);
        aControlButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

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

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.CompetitorRoleButton:
                intent = new Intent(this, CompListActivity.class);
                startActivity(intent);
                break;
            case R.id.AControlRoleButton:
                intent = new Intent(this, AdminAControlActivity.class);
                startActivity(intent);
                break;
            case R.id.StartRoleButton:
                intent = new Intent(this, AdminStartActivity.class);
                startActivity(intent);
                break;
            case R.id.FinishRoleButton:
                intent = new Intent(this, AdminFinishActivity.class);
                startActivity(intent);
                break;
            case R.id.AOSignOutButton:
                signOut();
                break;
            case R.id.AOResetButton:
                ShowResetPopup();
                break;
        }
    }

    public void signOut() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void resetAll() {
        aControlDatabaseHelper.empty();
        startDatabaseHelper.empty();
        finishDatabaseHelper.empty();
        userDatabaseHelper.empty();
        compDatabaseHelper.empty();
        stageDatabaseHelper.empty();
        timingCrewDatabaseHelper.empty();

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
        if (!timingCrewDatabaseHelper.checkTimingCrew(position, postChief)) {
            crew.setPosition(position);
            crew.setPostChief(postChief);
            crew.setPostChiefPhone(phone);
            timingCrewDatabaseHelper.addTimingCrew(crew);
        }
        crewID = timingCrewDatabaseHelper.getCrewId(position, postChief);
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

        Button yesReset = layout.findViewById(R.id.YesResetButton);
        yesReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetAll();
                resetPopup.dismiss();
            }
        });

        Button noReset = layout.findViewById(R.id.NoResetButton);
        noReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPopup.dismiss();
            }
        });
    }
}
