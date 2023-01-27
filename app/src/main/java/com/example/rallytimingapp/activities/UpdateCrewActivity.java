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

    private Button backButton;
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

        // Get the role passed through the intent
        role = getIntent().getStringExtra("ROLE");
        // Update text
        display.setText("Update " + role + " Crew Logins");
        addNewButton.setText("Add New " + role + " Account");

        // Get the crew ID passed through the intent
        int crewID = getIntent().getIntExtra("CREW_ID", 0);
        // Get the crew and user database entries associated with that ID
        crew = crewDatabaseHelper.getTimingCrewByID(crewID);
        user = userDatabaseHelper.getUserByRoleID(role, crewID);
        // Fill in the text boxes using these objects
        usernameET.setText(user.getUsername());
        passwordET.setText(user.getPassword());
        postChiefET.setText(crew.getPostChief());
        phoneET.setText(crew.getPostChiefPhone());
    }

    // Method to initialise views
    private void initViews() {
        backButton = findViewById(R.id.UpdateCrewBackButton);
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

    // Method to initialise objects
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabaseHelper(activity);
        crewDatabaseHelper = new TimingCrewDatabaseHelper(activity);
        user = new User();
        crew = new TimingCrew();
    }

    // Method to initialise listeners for the buttons
    private void initListeners() {
        backButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        addNewButton.setOnClickListener(this);
    }

    // Method to return to the previous page, based on which role was passed by the intent
    private void back() {
        Intent intent = new Intent(this, CrewListActivity.class);
        intent.putExtra("ROLE", role);
        startActivity(intent);
    }

    // On Click method for the buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UpdateCrewButton:
                // Update button shows the update pop-up
                ShowUpdatePopup();
                break;
            case R.id.DeleteCrewButton:
                // Delete button shows the delete pop-up
                ShowDeletePopup();
                break;
            case R.id.UpdateCrewBackButton:
                back();
                break;
            case R.id.AddNewCrewButton:
                addNew();
                break;
        }
    }

    // Method to show a pop-up asking for confirmation to delete the account
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

        // Add an on click listener for the yes button
        Button yesDelete = layout.findViewById(R.id.YesDeleteButton);
        yesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the delete crew method
                deleteCrew();
                // Dismiss the pop-up
                deletePopup.dismiss();
                // Return to the previous activity
                back();
            }
        });

        // Set on click listener for the no button, which dismisses the pop-up
        Button noDelete = layout.findViewById(R.id.NoDeleteButton);
        noDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePopup.dismiss();
            }
        });
    }

    // Method to show a pop-up asking for confirmation to update the account
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

        // Add an on click listener for the yes button
        Button yesUpdate = layout.findViewById(R.id.YesUpdateButton);
        yesUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if all areas have been filled in
                if (verifyInput()) {
                    // If so, save the crew and get the ID
                    int crewID = saveCrew();
                    // Then save the user, if there was a crew entry to update
                    if (crewID != -1) {
                        saveUser(crewID);
                    }
                } else {
                    // If not, prompt for all details to be filled in
                    Snackbar.make(scrollView, "Please fill in all details", Snackbar.LENGTH_LONG).show();
                }
                // Dismiss the pop-up
                updatePopup.dismiss();
            }
        });

        // Set an on click listener for the no button, with dismisses the pop-up
        Button noUpdate = layout.findViewById(R.id.NoUpdateButton);
        noUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePopup.dismiss();
            }
        });
    }

    // Method to save the crew details
    public int saveCrew() {
        int crewID = -1;
        // Get the inputs relevant to the timing crew database entry
        String postChief = postChiefET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        // If an entry with that role and post chief already exists, update it
        if (crewDatabaseHelper.checkTimingCrew(role, postChief)) {
            crew = crewDatabaseHelper.getTimingCrewByPostChief(role, postChief);
            crew.setPostChiefPhone(phone);
            crewDatabaseHelper.updateTimingCrew(crew);
            // Get the ID to return
            crewID = crew.getCrewId();

        } else {
            // Otherwise show an error message that the account does not exist
            Snackbar.make(scrollView, "Account does not exist, please create one", Snackbar.LENGTH_LONG).show();
        }
        // Return the crew ID
        return crewID;
    }

    // Method to save the user details
    public void saveUser(int crewID) {
        // Get the inputs relevant to the user database entry
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        // If an entry with that username already exists, update it
        if (userDatabaseHelper.checkUser(username)) {
            user = userDatabaseHelper.getUser(username, role);
            user.setPassword(password);
            user.setId(crewID);
            userDatabaseHelper.updateUser(user);
        } else if (userDatabaseHelper.checkUser(role, crewID)) {
            // Or if an entry with that role and crew ID already exists, update it
            user = userDatabaseHelper.getUserByRoleID(role, crewID);
            user.setUsername(username);
            user.setPassword(password);
            userDatabaseHelper.updateUser(user);
        } else {
            // Otherwise show an error message
            Snackbar.make(scrollView, "Account does not exist, please create one", Snackbar.LENGTH_LONG).show();
        }
    }

    // Method to delete a competitor from the databases
    public void deleteCrew() {
        int crewID = -1;
        // Get the username and driver name
        String username = usernameET.getText().toString().trim();
        String postChief = postChiefET.getText().toString().trim();
        // Find the entry in the crew database with that role and post cheif's name
        if (crewDatabaseHelper.checkTimingCrew(role, postChief)) {
            // If found, get the crew ID, then delete it
            crew = crewDatabaseHelper.getTimingCrewByPostChief(role, postChief);
            crewID = crew.getCrewId();
            crewDatabaseHelper.deleteTimingCrew(crew);
        }
        // Find the entry in the user database with that username
        if (userDatabaseHelper.checkUser(username)) {
            // If found, delete it
            user = userDatabaseHelper.getUser(username, role);
            userDatabaseHelper.deleteUser(user);
        } else if (userDatabaseHelper.checkUser(role, crewID)) {
            // Otherwise find the entry in the user database with the compID found above
            // then delete it
            user = userDatabaseHelper.getUserByRoleID(role, crewID);
            userDatabaseHelper.deleteUser(user);
        }

        // Clear all inputs and reset focus
        usernameET.setText(null);
        passwordET.setText(null);
        postChiefET.setText(null);
        phoneET.setText(null);
        usernameET.requestFocus();
        usernameET.setCursorVisible(true);
    }

    // Check if all text boxes are filled
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

    private void addNew() {
        Intent intent = new Intent(this, AddCrewActivity.class);
        intent.putExtra("ROLE", role);
        startActivity(intent);
    }
}