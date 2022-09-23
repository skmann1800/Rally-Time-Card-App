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

public class UpdateAControlActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = UpdateAControlActivity.this;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private TimingCrewDatabaseHelper crewDatabaseHelper;

    private User user;
    private TimingCrew aControlCrew;

    private Button saveButton;
    private Button deleteButton;

    private ScrollView scrollView;

    private PopupWindow deletePopup;
    private PopupWindow updatePopup;

    private EditText usernameET;
    private EditText passwordET;
    private EditText postChiefET;
    private EditText phoneET;

    private final String role = "A Control";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_acontrol);

        initViews();
        initObjects();
        initListeners();

        int crewID = getIntent().getIntExtra("CREW_ID", 0);
        aControlCrew = crewDatabaseHelper.getTimingCrewByID(crewID);
        user = userDatabaseHelper.getUserByRoleID(role, crewID);
        usernameET.setText(user.getUsername());
        passwordET.setText(user.getPassword());
        postChiefET.setText(aControlCrew.getPostChief());
        phoneET.setText(aControlCrew.getPostChiefPhone());
    }

    private void initViews() {
        saveButton = findViewById(R.id.updateACButton);
        deleteButton = findViewById(R.id.deleteACButton);
        scrollView = findViewById(R.id.UpdateACScrollView);

        usernameET = findViewById(R.id.UACUsername);
        passwordET = findViewById(R.id.UACPassword);
        postChiefET = findViewById(R.id.UACPostChief);
        phoneET = findViewById(R.id.UACPhone);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabaseHelper(activity);
        crewDatabaseHelper = new TimingCrewDatabaseHelper(activity);
        user = new User();
        aControlCrew = new TimingCrew();
    }

    private void initListeners() {
        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    public void back(View view) {
        Intent intent = new Intent(this, AControlListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.updateACButton:
                ShowUpdatePopup();
                break;
            case R.id.deleteACButton:
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
                deleteAControl();
                deletePopup.dismiss();
                Intent intent = new Intent(activity, StartListActivity.class);
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
                    int crewID = saveAControl();
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

    public int saveAControl() {
        int crewID = -1;
        String postChief = postChiefET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        if (crewDatabaseHelper.checkTimingCrew(role, postChief)) {
            aControlCrew = crewDatabaseHelper.getTimingCrewByPostChief(role, postChief);
            aControlCrew.setPostChiefPhone(phone);
            crewDatabaseHelper.updateTimingCrew(aControlCrew);
            crewID = aControlCrew.getCrewId();

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

    public void deleteAControl() {
        int crewID = -1;
        String username = usernameET.getText().toString().trim();
        String postChief = postChiefET.getText().toString().trim();
        if (crewDatabaseHelper.checkTimingCrew(role, postChief)) {
            aControlCrew = crewDatabaseHelper.getTimingCrewByPostChief(role, postChief);
            crewID = aControlCrew.getCrewId();
            crewDatabaseHelper.deleteTimingCrew(aControlCrew);
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
        Intent intent = new Intent(this, AddAControlActivity.class);
        startActivity(intent);
    }
}