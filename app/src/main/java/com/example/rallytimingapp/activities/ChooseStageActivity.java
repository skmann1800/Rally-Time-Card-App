package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rallytimingapp.R;

public class ChooseStageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button stage1Button;
    private Button stage2Button;
    private Button stage3Button;
    private Button stage4Button;

    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_stage);

        initViews();
        initListeners();

        // Get role from intent
        role = getIntent().getStringExtra("ROLE");
    }

    // Method to initialise views
    private void initViews() {
        stage1Button = findViewById(R.id.Stage1Button);
        stage2Button = findViewById(R.id.Stage2Button);
        stage3Button = findViewById(R.id.Stage3Button);
        stage4Button = findViewById(R.id.Stage4Button);
    }

    // Method to initialise listeners
    private void initListeners() {
        stage1Button.setOnClickListener(this);
        stage2Button.setOnClickListener(this);
        stage3Button.setOnClickListener(this);
        stage4Button.setOnClickListener(this);
    }

    // On Click method for buttons, depending on the role and which button is clicked
    @Override
    public void onClick(View view) {
        int stage = 0;
        switch (view.getId()) {
            case R.id.Stage1Button:
                stage = 1;
                break;
            case R.id.Stage2Button:
                stage = 2;
                break;
            case R.id.Stage3Button:
                stage = 3;
                break;
            case R.id.Stage4Button:
                stage = 4;
                break;
        }

        // Each role is sent to a different activity, and the stage number is
        // passed as an extra
        Intent intent;
        switch (role) {
            case "A Control":
                intent = new Intent(this, AControlActivity.class);
                intent.putExtra("STAGE", stage);
                startActivity(intent);
                break;
            case "Start":
                intent = new Intent(this, StartActivity.class);
                intent.putExtra("STAGE", stage);
                startActivity(intent);
                break;
            case "Finish":
                intent = new Intent(this, FinishActivity.class);
                intent.putExtra("STAGE", stage);
                startActivity(intent);
                break;
        }
    }

    // Method to return to the main login page
    public void signOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
