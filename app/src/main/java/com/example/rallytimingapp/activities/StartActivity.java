package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rallytimingapp.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backButton;
    private TextView stageNumTV;
    private TextView stageLabel1;
    private TextView stageLabel2;
    private TextView stageDist;
    private TextView SS;
    private TextView blueTC1;
    private TextView blueTC2;
    private TextView stageKM;
    private TextView finishTC;
    private TextView yellowTC;
    private TextView TTH;
    private TextView TTM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initViews();
        initListeners();

        int stageNum = getIntent().getIntExtra("STAGE", 0);
        switch (stageNum) {
            case 1:
                stageNumTV.setText(R.string.s1start);
                stageLabel1.setText(R.string.stage1);
                stageLabel2.setText(R.string.stage1);
                stageDist.setText(R.string.stage1dist);
                stageKM.setText(R.string.stage1km);
                SS.setText(R.string.SS1);
                blueTC1.setText(R.string.TC1);
                blueTC2.setText(R.string.TC2);
                finishTC.setText(R.string.TC2);
                yellowTC.setText(R.string.TC2);
                TTH.setText(R.string.S1TTH);
                TTM.setText(R.string.S1TTM);
                break;
            case 2:
                stageNumTV.setText(R.string.s2start);
                stageLabel1.setText(R.string.stage2);
                stageLabel2.setText(R.string.stage2);
                stageDist.setText(R.string.stage2dist);
                stageKM.setText(R.string.stage2km);
                SS.setText(R.string.SS2);
                blueTC1.setText(R.string.TC2);
                blueTC2.setText(R.string.TC3);
                finishTC.setText(R.string.TC3);
                yellowTC.setText(R.string.TC3);
                TTH.setText(R.string.S2TTH);
                TTM.setText(R.string.S2TTM);
                break;
            case 3:
                stageNumTV.setText(R.string.s3start);
                stageLabel1.setText(R.string.stage3);
                stageLabel2.setText(R.string.stage3);
                stageDist.setText(R.string.stage3dist);
                stageKM.setText(R.string.stage3km);
                SS.setText(R.string.SS3);
                blueTC1.setText(R.string.TC3);
                blueTC2.setText(R.string.TC4);
                finishTC.setText(R.string.TC4);
                yellowTC.setText(R.string.TC4);
                TTH.setText(R.string.S3TTH);
                TTM.setText(R.string.S3TTM);
                break;
            case 4:
                stageNumTV.setText(R.string.s4start);
                stageLabel1.setText(R.string.stage4);
                stageLabel2.setText(R.string.stage4);
                stageDist.setText(R.string.stage4dist);
                stageKM.setText(R.string.stage4km);
                SS.setText(R.string.SS4);
                blueTC1.setText(R.string.TC4);
                blueTC2.setText(R.string.TC5);
                finishTC.setText(R.string.TC5);
                yellowTC.setText(R.string.TC5);
                TTH.setText(R.string.S4TTH);
                TTM.setText(R.string.S4TTM);
                break;
        }
    }

    private void initViews() {
        backButton = findViewById(R.id.STCBackButton);
        stageNumTV = findViewById(R.id.StartStageNum);
        stageLabel1 = findViewById(R.id.STCStage1);
        stageLabel2 = findViewById(R.id.STCStage2);
        stageDist = findViewById(R.id.STCDist);
        SS = findViewById(R.id.STCSS);
        blueTC1 = findViewById(R.id.STCTC1);
        blueTC2 = findViewById(R.id.STCTC2);
        stageKM = findViewById(R.id.STCKM);
        finishTC = findViewById(R.id.STCTC);
        yellowTC = findViewById(R.id.STCYellowTC);
        TTH = findViewById(R.id.STCTaTH);
        TTM = findViewById(R.id.STCTaTM);
    }

    private void initListeners() {
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ChooseStartActivity.class);
        startActivity(intent);
    }
}