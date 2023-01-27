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

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.helpers.InputValidation;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class UpdateCompActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = UpdateCompActivity.this;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private CompDatabaseHelper compDatabaseHelper;

    private User user;
    private Competitor competitor;

    private Button backButton;
    private Button saveButton;
    private Button deleteButton;
    private Button addNewButton;

    private ScrollView scrollView;

    private PopupWindow deletePopup;
    private PopupWindow updatePopup;

    private EditText usernameET;
    private EditText passwordET;
    private EditText carNumET;
    private EditText driverET;
    private EditText codriverET;

    private final String role = "Competitor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_comp);

        initViews();
        initObjects();
        initListeners();

        // Get the competitor ID passed through the intent
        int compID = getIntent().getIntExtra("COMP_ID", 0);
        // Get the competitor database entry associated with that ID
        competitor = compDatabaseHelper.getCompetitorByID(compID);
        // Get the user entry with that ID
        user = userDatabaseHelper.getUserByRoleID(role, compID);
        // Fill in the text boxes using these objects
        usernameET.setText(user.getUsername());
        passwordET.setText(user.getPassword());
        carNumET.setText(String.valueOf(competitor.getCarNum()));
        driverET.setText(competitor.getDriver());
        codriverET.setText(competitor.getCodriver());
    }

    // Method to initialise views
    private void initViews() {
        backButton = findViewById(R.id.UpdateCompBackButton);
        saveButton = findViewById(R.id.UpdateCompButton);
        deleteButton = findViewById(R.id.DeleteCompButton);
        addNewButton = findViewById(R.id.AddNewCompButton);
        scrollView = findViewById(R.id.UpdateCompScrollView);

        usernameET = findViewById(R.id.CompUsername);
        passwordET = findViewById(R.id.CompPassword);
        carNumET = findViewById(R.id.CompCarNum);
        driverET = findViewById(R.id.CompDriver);
        codriverET = findViewById(R.id.CompCodriver);
    }

    // Method to initialise objects
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabaseHelper(activity);
        compDatabaseHelper = new CompDatabaseHelper(activity);
        user = new User();
        competitor = new Competitor();
    }

    // Method to initialise listeners for the buttons
    private void initListeners() {
        backButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        addNewButton.setOnClickListener(this);
    }

    // On Click method for the buttons
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.UpdateCompButton:
                // Update button shows the update pop-up
                ShowUpdatePopup();
                break;
            case R.id.DeleteCompButton:
                // Delete button shows the delete pop-up
                ShowDeletePopup();
                break;
            case R.id.UpdateCompBackButton:
                // Return to the competitor list activity
                intent = new Intent(this, CompListActivity.class);
                startActivity(intent);
                break;
            case R.id.AddNewCompButton:
                // Go to the Add Competitor Activity
                intent = new Intent(this, AddCompActivity.class);
                startActivity(intent);
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
                // Call the delete competitor method
                deleteComp();
                // Dismiss the pop-up
                deletePopup.dismiss();
                // Return to the competitor list activity
                Intent intent = new Intent(activity, CompListActivity.class);
                startActivity(intent);
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
                    // If so, save the competitor and get the ID
                    int compID = saveCompetitor();
                    // Then save the user, if there was a competitor to update
                    if (compID != -1) {
                        saveUser(compID);
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

    // Method to save the competitor details
    public int saveCompetitor() {
        int compID = -1;
        // Get the inputs relevant to the competitor database entry
        int carNum = Integer.valueOf(carNumET.getText().toString().trim());
        String driver = driverET.getText().toString().trim();
        String codriver = codriverET.getText().toString().trim();
        // If an entry with that driver already exists, update it
        if (compDatabaseHelper.checkCompetitor(driver)) {
            competitor = compDatabaseHelper.getCompetitorByDriver(driver);
            competitor.setCarNum(carNum);
            competitor.setCodriver(codriver);
            compDatabaseHelper.updateCompetitor(competitor);
            // Get the ID to return
            compID = competitor.getCompId();

        } else {
            // Otherwise show an error message that the account does not exist
            Snackbar.make(scrollView, "Account does not exist, please create one", Snackbar.LENGTH_LONG).show();
        }
        // Return the competitor ID
        return compID;
    }

    // Method to save the user details
    public void saveUser(int compID) {
        // Get the inputs relevant to the user database entry
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        // If an entry with that username already exists, update it
        if (userDatabaseHelper.checkUser(username)) {
            user = userDatabaseHelper.getUser(username, role);
            user.setPassword(password);
            user.setId(compID);
            userDatabaseHelper.updateUser(user);
        } else if (userDatabaseHelper.checkUser(role, compID)) {
            // Or if an entry with that role and competitor ID already exists, update it
            user = userDatabaseHelper.getUserByRoleID(role, compID);
            user.setUsername(username);
            user.setPassword(password);
            userDatabaseHelper.updateUser(user);
        } else {
            // Otherwise show an error message
            Snackbar.make(scrollView, "Account does not exist, please create one", Snackbar.LENGTH_LONG).show();
        }
    }

    // Method to delete a competitor from the databases
    public void deleteComp() {
        int compID = -1;
        // Get the username and driver name
        String username = usernameET.getText().toString().trim();
        String driver = driverET.getText().toString().trim();
        // Find the entry in the competitor database with that drivers name
        if (compDatabaseHelper.checkCompetitor(driver)) {
            // If found, get the competitor ID, then delete it
            competitor = compDatabaseHelper.getCompetitorByDriver(driver);
            compID = competitor.getCompId();
            compDatabaseHelper.deleteCompetitor(competitor);
        }
        // Find the entry in the user database with that username
        if (userDatabaseHelper.checkUser(username)) {
            // If found, delete it
            user = userDatabaseHelper.getUser(username, role);
            userDatabaseHelper.deleteUser(user);
        } else if (userDatabaseHelper.checkUser(role, compID)) {
            // Otherwise find the entry in the user database with the compID found above
            // then delete it
            user = userDatabaseHelper.getUserByRoleID(role, compID);
            userDatabaseHelper.deleteUser(user);
        }

        // Clear all inputs and reset focus
        usernameET.setText(null);
        passwordET.setText(null);
        carNumET.setText(null);
        driverET.setText(null);
        codriverET.setText(null);
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