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
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.UserDatabaseHelper;
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
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initViews();
        initObjects();
    }

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
        inputValidation = new InputValidation(activity);
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
                    // If the role is an admin, go to the admin options activity
                    Intent intent = new Intent(this, AdminOptionsActivity.class);
                    clearInputs();
                    startActivity(intent);
                }
            }
            clearInputs();
            // If there was no chip selected, see if the login was an admin
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