package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rallytimingapp.R;

public class ChooseCrewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aControlButton;
    private Button startButton;
    private Button finishButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_crew);

        initViews();
        initListeners();
    }

    // Method to initialise views
    private void initViews() {
        aControlButton = findViewById(R.id.AControlButton);
        startButton = findViewById(R.id.StartButton);
        finishButton = findViewById(R.id.FinishButton);
        backButton = findViewById(R.id.CCBackButton);
    }

    // Method to initialise listeners
    private void initListeners() {
        aControlButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    // On Click method for buttons, depending on the role and which button is clicked
    @Override
    public void onClick(View view) {
        // They are sent to the next activity, which will be choosing a stage number,
        // the role, based on which button is clicked, will be passed as an extra
        switch (view.getId()) {
            case R.id.AControlButton:
                chooseStage("A Control");
                break;
            case R.id.StartButton:
                chooseStage("Start");
                break;
            case R.id.FinishButton:
                chooseStage("Finish");
                break;
            case R.id.CCBackButton:
                // Return to the main login page
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void chooseStage(String role) {
        Intent intent = new Intent(this, ChooseStageActivity.class);
        intent.putExtra("ROLE", role);
        startActivity(intent);
    }
}