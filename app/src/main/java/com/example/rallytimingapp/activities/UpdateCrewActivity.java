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
import com.example.rallytimingapp.model.TimingCrew;
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.TimingCrewDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class UpdateCrewActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = UpdateCrewActivity.this;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private TimingCrewDatabaseHelper crewDatabaseHelper;

    private User user;
    private TimingCrew crew;

    private Button saveButton;
    private Button deleteButton;
    private Button addNewButton;

    private ScrollView scrollView;

    private PopupWindow deletePopup;
    private PopupWindow updatePopup;

    private TextView display;
    private EditText usernameET;
    private EditText passwordET;
    private EditText postChiefET;
    private EditText phoneET;

    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_crew);

        initViews();
        initObjects();
        initListeners();

        role = getIntent().getStringExtra("ROLE");
        display.setText("Update " + role + " Crew Logins");
        addNewButton.setText("Add New " + role + " Account");

        int crewID = getIntent().getIntExtra("CREW_ID", 0);
        crew = crewDatabaseHelper.getTimingCrewByID(crewID);
        user = userDatabaseHelper.getUserByRoleID(role, crewID);
        usernameET.setText(user.getUsername());
        passwordET.setText(user.getPassword());
        postChiefET.setText(crew.getPostChief());
        phoneET.setText(crew.getPostChiefPhone());
    }

    private void initViews() {
        saveButton = findViewById(R.id.UpdateCrewButton);
        deleteButton = findViewById(R.id.DeleteCrewButton);
        addNewButton = findViewById(R.id.AddNewCrewButton);
        scrollView = findViewById(R.id.UpdateCrewScrollView);

        display = findViewById(R.id.UpdateCrewDisplay);
        usernameET = findViewById(R.id.UpdateCrewUsername);
        passwordET = findViewById(R.id.UpdateCrewPassword);
        postChiefET = findViewById(R.id.UpdateCrewPostChief);
        phoneET = findViewById(R.id.UpdateCrewPhone);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabaseHelper(activity);
        crewDatabaseHelper = new TimingCrewDatabaseHelper(activity);
        user = new User();
        crew = new TimingCrew();
    }

    private void initListeners() {
        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    // Method to return to the previous page, based on which role was passed by the intent
    public void back(View view) {
        Intent intent;
        switch (role) {
            case "Finish":
                intent = new Intent(this, FinishListActivity.class);
                startActivity(intent);
                break;
            case "Start":
                intent = new Intent(this, StartListActivity.class);
                startActivity(intent);
                break;
            case "A Control":
                intent = new Intent(this, AControlListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UpdateCrewButton:
                ShowUpdatePopup();
                break;
            case R.id.DeleteCrewButton:
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
                deleteCrew();
                deletePopup.dismiss();
                back(view);
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

    private void ShowUpdatePopup() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.update_account_popup, null);

        updatePopup = new PopupWindow(this);
        updatePopup.setContentView(layout);
        updatePopup.setWidth(width);
        updatePopup.setHeight(height);
        updatePopup.setFocusable(true);
        updatePopup.setBackgroundDrawable(null);
        updatePopup.showAtLocation(layout, Gravity.CENTER, 1, 1);

        Button yesUpdate = layout.findViewById(R.id.YesUpdateButton);
        yesUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyInput()) {
                    int crewID = saveCrew();
                    if (crewID != -1) {
                        saveUser(crewID);
                    }
                } else {
                    Snackbar.make(scrollView, "Please fill in all details", Snackbar.LENGTH_LONG).show();
                }
                updatePopup.dismiss();
            }
        });

        Button noUpdate = layout.findViewById(R.id.NoUpdateButton);
        noUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePopup.dismiss();
            }
        });
    }

    public int saveCrew() {
        int crewID = -1;
        String postChief = postChiefET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        if (crewDatabaseHelper.checkTimingCrew(role, postChief)) {
            crew = crewDatabaseHelper.getTimingCrewByPostChief(role, postChief);
            crew.setPostChiefPhone(phone);
            crewDatabaseHelper.updateTimingCrew(crew);
            crewID = crew.getCrewId();

        } else {
            Snackbar.make(scrollView, "Account does not exist, please create one", Snackbar.LENGTH_LONG).show();
        }
        return crewID;
    }

    public void saveUser(int crewID) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        if (userDatabaseHelper.checkUser(username)) {
            user = userDatabaseHelper.getUser(username, role);
            user.setPassword(password);
            user.setId(crewID);
            userDatabaseHelper.updateUser(user);
        } else if (userDatabaseHelper.checkUser(role, crewID)) {
            user = userDatabaseHelper.getUserByRoleID(role, crewID);
            user.setUsername(username);
            user.setPassword(password);
            userDatabaseHelper.updateUser(user);
        } else {
            Snackbar.make(scrollView, "Account does not exist, please create one", Snackbar.LENGTH_LONG).show();
        }
    }

    public void deleteCrew() {
        int crewID = -1;
        String username = usernameET.getText().toString().trim();
        String postChief = postChiefET.getText().toString().trim();
        if (crewDatabaseHelper.checkTimingCrew(role, postChief)) {
            crew = crewDatabaseHelper.getTimingCrewByPostChief(role, postChief);
            crewID = crew.getCrewId();
            crewDatabaseHelper.deleteTimingCrew(crew);
        }
        if (userDatabaseHelper.checkUser(username)) {
            user = userDatabaseHelper.getUser(username, role);
            userDatabaseHelper.deleteUser(user);
        } else if (userDatabaseHelper.checkUser(role, crewID)) {
            user = userDatabaseHelper.getUserByRoleID(role, crewID);
            userDatabaseHelper.deleteUser(user);
        }

        usernameET.setText(null);
        passwordET.setText(null);
        postChiefET.setText(null);
        phoneET.setText(null);
        usernameET.requestFocus();
        usernameET.setCursorVisible(true);
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

    public void addNew(View view) {
        Intent intent = new Intent(this, AddCrewActivity.class);
        intent.putExtra("ROLE", role);
        startActivity(intent);
    }
}