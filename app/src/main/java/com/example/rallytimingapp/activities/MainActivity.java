package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private ChipGroup chips;
    private Chip checkedChip;

    private ScrollView scrollView;

    private InputValidation inputValidation;
    private UserDatabaseHelper UserDatabaseHelper;
    private User user;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initViews();
        initObjects();

        CreateLogin("Russell", "comp", "Competitor", 28);
        CreateLogin("Jared", "start", "Start", 0);
        CreateLogin("Sarah", "finish", "Finish", 0);
        CreateLogin("George", "control", "A Control", 0);
    }

    private void CreateLogin(String username, String password, String role, int carNum) {
        if (!UserDatabaseHelper.checkUser(username)) {
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            user.setCarNum(carNum);
            UserDatabaseHelper.addUser(user);
        }
    }

    private void initViews() {
        scrollView = findViewById(R.id.LoginScroll);

        editTextUsername = findViewById(R.id.UsernameET);
        editTextPassword = findViewById(R.id.PasswordET);
        chips = findViewById(R.id.Chips);
    }

    private void initObjects() {
        UserDatabaseHelper = new UserDatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        user = new User();
        userList = new ArrayList<>();
    }

    public void login(View view) {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        int checkedChipID = chips.getCheckedChipId();
        checkedChip = chips.findViewById(checkedChipID);
        String role = checkedChip.getText().toString().trim();

        if (verifyLogin(username, password, role)) {
            userList.addAll(UserDatabaseHelper.getAllUsers());
            int carNum = 0;
            for (int i = 0; i < userList.size(); i++) {
                User currUser = userList.get(i);
                if (username.equals(currUser.getUsername())) {
                    carNum = currUser.getCarNum();
                }
            }

            if (role.equals("Competitor")) {
                Intent intent = new Intent(this, CompViewActivity.class);
                intent.putExtra("CAR_NUM", carNum);
                clearInputs();
                startActivity(intent);
            } else if (role.equals("Finish")) {
                Intent intent = new Intent(this, ChooseFinishActivity.class);
                clearInputs();
                startActivity(intent);
            } else if (role.equals("Start")) {
                Intent intent = new Intent(this, ChooseStartActivity.class);
                clearInputs();
                startActivity(intent);
            } else if (role.equals("A Control")) {
                Intent intent = new Intent(this, ChooseControlActivity.class);
                clearInputs();
                startActivity(intent);
            }
        }
        clearInputs();
    }

    private boolean verifyLogin(String username, String password, String role) {
        if (!inputValidation.isEditTextFilled(editTextUsername)) {
            return false;
        }
        if (!inputValidation.isEditTextFilled(editTextPassword)) {
            return false;
        }
        if (UserDatabaseHelper.checkUser(username, password, role)) {
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
    }
}