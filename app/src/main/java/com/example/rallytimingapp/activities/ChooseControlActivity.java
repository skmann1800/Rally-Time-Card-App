package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rallytimingapp.R;

public class ChooseControlActivity extends AppCompatActivity implements View.OnClickListener {

    private Button stage1Button;
    private Button stage2Button;
    private Button stage3Button;
    private Button stage4Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_control);

        initViews();
        initListeners();
    }

    private void initViews() {
        stage1Button = findViewById(R.id.Control1Button);
        stage2Button = findViewById(R.id.Control2Button);
        stage3Button = findViewById(R.id.Control3Button);
        stage4Button = findViewById(R.id.Control4Button);
    }

    private void initListeners() {
        stage1Button.setOnClickListener(this);
        stage2Button.setOnClickListener(this);
        stage3Button.setOnClickListener(this);
        stage4Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int stage = 0;
        switch (view.getId()) {
            case R.id.Control1Button:
                stage = 1;
                break;
            case R.id.Control2Button:
                stage = 2;
                break;
            case R.id.Control3Button:
                stage = 3;
                break;
            case R.id.Control4Button:
                stage = 4;
                break;
        }

        Intent intent = new Intent(this, AControlActivity.class);
        intent.putExtra("STAGE", stage);
        startActivity(intent);
    }

    public void signOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
