package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.rallytimingapp.R;

public class AControlActivity extends AppCompatActivity implements View.OnClickListener {

    private HorizontalScrollView timeCard1;
    private Button backButton;
    private TextView stageNumTV;
    private TextView stage1Label1;
    private TextView stage1Label2;
    private TextView stage1Dist;
    private TextView SS1;
    private TextView blue1TC1;
    private TextView blue1TC2;
    private TextView stage1KM;
    private TextView finishTC1;
    private TextView yellowTC1;
    private TextView S1TTH;
    private TextView S1TTM;
    private TextView stage2Label1;
    private TextView stage2Label2;
    private TextView stage2Dist;
    private TextView SS2;
    private TextView blue2TC1;
    private TextView blue2TC2;
    private TextView stage2KM;
    private TextView finishTC2;
    private TextView yellowTC2;
    private TextView S2TTH;
    private TextView S2TTM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acontrol);

        initViews();
        initListeners();

        int stageNum = getIntent().getIntExtra("STAGE", 0);
        switch (stageNum) {
            case 1:
                stageNumTV.setText(R.string.s1control);
                timeCard1.setVisibility(View.GONE);
                stage2Label1.setText(R.string.stage1);
                stage2Label2.setText(R.string.stage1);
                stage2Dist.setText(R.string.stage1dist);
                stage2KM.setText(R.string.stage1km);
                SS2.setText(R.string.SS1);
                blue2TC1.setText(R.string.TC1);
                blue2TC2.setText(R.string.TC2);
                finishTC2.setText(R.string.TC2);
                yellowTC2.setText(R.string.TC2);
                S2TTH.setText(R.string.S1TTH);
                S2TTM.setText(R.string.S1TTM);
                break;
            case 2:
                stageNumTV.setText(R.string.s2control);
                stage1Label1.setText(R.string.stage1);
                stage1Label2.setText(R.string.stage1);
                stage1Dist.setText(R.string.stage1dist);
                stage1KM.setText(R.string.stage1km);
                SS1.setText(R.string.SS1);
                blue1TC1.setText(R.string.TC1);
                blue1TC2.setText(R.string.TC2);
                finishTC1.setText(R.string.TC2);
                yellowTC1.setText(R.string.TC2);
                S1TTH.setText(R.string.S1TTH);
                S1TTM.setText(R.string.S1TTM);
                stage2Label1.setText(R.string.stage2);
                stage2Label2.setText(R.string.stage2);
                stage2Dist.setText(R.string.stage2dist);
                stage2KM.setText(R.string.stage2km);
                SS2.setText(R.string.SS2);
                blue2TC1.setText(R.string.TC2);
                blue2TC2.setText(R.string.TC3);
                finishTC2.setText(R.string.TC3);
                yellowTC2.setText(R.string.TC3);
                S2TTH.setText(R.string.S2TTH);
                S2TTM.setText(R.string.S2TTM);
                break;
            case 3:
                stageNumTV.setText(R.string.s3control);
                stage1Label1.setText(R.string.stage2);
                stage1Label2.setText(R.string.stage2);
                stage1Dist.setText(R.string.stage2dist);
                stage1KM.setText(R.string.stage2km);
                SS1.setText(R.string.SS2);
                blue1TC1.setText(R.string.TC2);
                blue1TC2.setText(R.string.TC3);
                finishTC1.setText(R.string.TC3);
                yellowTC1.setText(R.string.TC3);
                S1TTH.setText(R.string.S2TTH);
                S1TTM.setText(R.string.S2TTM);
                stage2Label1.setText(R.string.stage3);
                stage2Label2.setText(R.string.stage3);
                stage2Dist.setText(R.string.stage3dist);
                stage2KM.setText(R.string.stage3km);
                SS2.setText(R.string.SS3);
                blue2TC1.setText(R.string.TC3);
                blue2TC2.setText(R.string.TC4);
                finishTC2.setText(R.string.TC4);
                yellowTC2.setText(R.string.TC4);
                S2TTH.setText(R.string.S3TTH);
                S2TTM.setText(R.string.S3TTM);
                break;
            case 4:
                stageNumTV.setText(R.string.s4control);
                stage1Label1.setText(R.string.stage3);
                stage1Label2.setText(R.string.stage3);
                stage1Dist.setText(R.string.stage3dist);
                stage1KM.setText(R.string.stage3km);
                SS1.setText(R.string.SS3);
                blue1TC1.setText(R.string.TC3);
                blue1TC2.setText(R.string.TC4);
                finishTC1.setText(R.string.TC4);
                yellowTC1.setText(R.string.TC4);
                S1TTH.setText(R.string.S3TTH);
                S1TTM.setText(R.string.S3TTM);
                stage2Label1.setText(R.string.stage4);
                stage2Label2.setText(R.string.stage4);
                stage2Dist.setText(R.string.stage4dist);
                stage2KM.setText(R.string.stage4km);
                SS2.setText(R.string.SS4);
                blue2TC1.setText(R.string.TC4);
                blue2TC2.setText(R.string.TC5);
                finishTC2.setText(R.string.TC5);
                yellowTC2.setText(R.string.TC5);
                S2TTH.setText(R.string.S4TTH);
                S2TTM.setText(R.string.S4TTM);
                break;
        }
    }

    private void initViews() {
        timeCard1 = findViewById(R.id.ControlTC1);
        backButton = findViewById(R.id.CTCBackButton);
        stageNumTV = findViewById(R.id.ControlStageNum);
        stage1Label1 = findViewById(R.id.CTC1Stage1);
        stage1Label2 = findViewById(R.id.CTC1Stage2);
        stage1Dist = findViewById(R.id.CTC1Dist);
        SS1 = findViewById(R.id.CTC1SS);
        blue1TC1 = findViewById(R.id.CTC1TC1);
        blue1TC2 = findViewById(R.id.CTC1TC2);
        stage1KM = findViewById(R.id.CTC1KM);
        finishTC1 = findViewById(R.id.CTC1TC);
        yellowTC1 = findViewById(R.id.CTC1YellowTC);
        S1TTH = findViewById(R.id.CTC1TaTH);
        S1TTM = findViewById(R.id.CTC1TaTM);
        stage2Label1 = findViewById(R.id.CTC2Stage1);
        stage2Label2 = findViewById(R.id.CTC2Stage2);
        stage2Dist = findViewById(R.id.CTC2Dist);
        SS2 = findViewById(R.id.CTC2SS);
        blue2TC1 = findViewById(R.id.CTC2TC1);
        blue2TC2 = findViewById(R.id.CTC2TC2);
        stage2KM = findViewById(R.id.CTC2KM);
        finishTC2 = findViewById(R.id.CTC2TC);
        yellowTC2 = findViewById(R.id.CTC2YellowTC);
        S2TTH = findViewById(R.id.CTC2TaTH);
        S2TTM = findViewById(R.id.CTC2TaTM);
    }

    private void initListeners() {
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ChooseControlActivity.class);
        startActivity(intent);
    }
}