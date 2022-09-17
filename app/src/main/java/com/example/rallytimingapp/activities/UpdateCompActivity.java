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
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.helpers.InputValidation;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class UpdateCompActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = UpdateCompActivity.this;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private CompDatabaseHelper compDatabaseHelper;
    private StageDatabaseHelper stageDatabaseHelper;

    private User user;
    private Competitor competitor;
    private Stage stage;

    private Button saveButton;
    private Button deleteButton;

    private ScrollView scrollView;

    private PopupWindow deletePopup;

    private TextView display;
    private EditText usernameET;
    private EditText passwordET;
    private EditText carNumET;
    private EditText driverET;
    private EditText codriverET;

    private int compID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_comp);

        initViews();
        initObjects();
        initListeners();

        compID = getIntent().getIntExtra("COMP_ID", 0);
        competitor = compDatabaseHelper.getCompetitorByID(compID);
        user = userDatabaseHelper.getUserByRoleID("Competitor", compID);
        usernameET.setText(user.getUsername());
        passwordET.setText(user.getPassword());
        carNumET.setText(String.valueOf(competitor.getCarNum()));
        driverET.setText(competitor.getDriver());
        codriverET.setText(competitor.getCodriver());
    }

    private void initViews() {
        saveButton = findViewById(R.id.updateCompButton);
        deleteButton = findViewById(R.id.deleteCompButton);
        scrollView = findViewById(R.id.UpdateCompScrollView);

        display = findViewById(R.id.CompDisplay);
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
        deleteButton.setOnClickListener(this);
    }

    public void back(View view) {
        Intent intent = new Intent(this, CompListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.updateCompButton:
                if (verifyInput()) {
                    int compID = saveCompetitor();
                    if (compID != -1) {
                        saveUser(compID);
                    }
                } else {
                    Snackbar.make(scrollView, "Please fill in all details", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.deleteCompButton:
                ShowDeletePopup();
                break;
        }
    }

    private void ShowDeletePopup() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.delete_account_popup, null);

        deletePopup = new PopupWindow(this);
        deletePopup.setContentView(layout);
        deletePopup.setWidth(width);
        deletePopup.setHeight(height);
        deletePopup.setFocusable(true);
        deletePopup.setBackgroundDrawable(null);
        deletePopup.showAtLocation(layout, Gravity.CENTER, 1, 1);

        Button yesDelete = layout.findViewById(R.id.YesDeleteButton);
        yesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComp();
                deletePopup.dismiss();
                Intent intent = new Intent(activity, CompListActivity.class);
                startActivity(intent);
            }
        });

        Button noDelete = layout.findViewById(R.id.NoDeleteButton);
        noDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePopup.dismiss();
            }
        });
    }

    public int saveCompetitor() {
        int compID = -1;
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
            Snackbar.make(scrollView, "Account does not exist, please create one", Snackbar.LENGTH_LONG).show();
        }
        return compID;
    }

    public void saveUser(int compID) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        if (userDatabaseHelper.checkUser(username)) {
            user = userDatabaseHelper.getUser(username, "Competitor");
            user.setPassword(password);
            user.setId(compID);
            userDatabaseHelper.updateUser(user);
        } else if (userDatabaseHelper.checkUser("Competitor", compID)) {
            user = userDatabaseHelper.getUserByRoleID("Competitor", compID);
            user.setUsername(username);
            user.setPassword(password);
            userDatabaseHelper.updateUser(user);
        } else {
            Snackbar.make(scrollView, "Account does not exist, please create one", Snackbar.LENGTH_LONG).show();
        }
    }

    public void deleteComp() {
        int compID = -1;
        String username = usernameET.getText().toString().trim();
        String driver = driverET.getText().toString().trim();
        if (compDatabaseHelper.checkCompetitor(driver)) {
            competitor = compDatabaseHelper.getCompetitorByDriver(driver);
            compID = competitor.getCompId();
            compDatabaseHelper.deleteCompetitor(competitor);
        }
        if (userDatabaseHelper.checkUser(username)) {
            user = userDatabaseHelper.getUser(username, "Competitor");
            userDatabaseHelper.deleteUser(user);
        } else if (userDatabaseHelper.checkUser("Competitor", compID)) {
            user = userDatabaseHelper.getUserByRoleID("Competitor", compID);
            userDatabaseHelper.deleteUser(user);
        }

        usernameET.setText(null);
        passwordET.setText(null);
        carNumET.setText(null);
        driverET.setText(null);
        codriverET.setText(null);
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