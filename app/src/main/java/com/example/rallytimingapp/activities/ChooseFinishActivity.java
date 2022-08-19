package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rallytimingapp.R;

public class ChooseFinishActivity extends AppCompatActivity implements View.OnClickListener {

    private Button stage1Button;
    private Button stage2Button;
    private Button stage3Button;
    private Button stage4Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_finish);

        initViews();
        initListeners();
    }

    private void initViews() {
        stage1Button = (Button) findViewById(R.id.Finish1Button);
        stage2Button = (Button) findViewById(R.id.Finish2Button);
        stage3Button = (Button) findViewById(R.id.Finish3Button);
        stage4Button = (Button) findViewById(R.id.Finish4Button);
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
            case R.id.Finish1Button:
                stage = 1;
                break;
            case R.id.Finish2Button:
                stage = 2;
                break;
            case R.id.Finish3Button:
                stage = 3;
                break;
            case R.id.Finish4Button:
                stage = 4;
                break;
        }

        Intent intent = new Intent(this, FinishActivity.class);
        intent.putExtra("STAGE", stage);
        startActivity(intent);
    }

    public void signOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
