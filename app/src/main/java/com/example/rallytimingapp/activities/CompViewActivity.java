package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.helpers.InputValidation;
import com.example.rallytimingapp.model.AControl;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.model.Finish;
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.model.User;
import com.example.rallytimingapp.sql.AControlDatabaseHelper;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.sql.FinishDatabaseHelper;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;

import java.util.ArrayList;

public class CompViewActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = CompViewActivity.this;

    private ScrollView scrollView;

    private TextView carNumTV;

    private TextView startOrder1;
    private TextView provStartH1;
    private TextView provStartM1;
    private TextView actualStartH1;
    private TextView actualStartM1;
    private TextView finishTimeH1;
    private TextView finishTimeM1;
    private TextView finishTimeS1;
    private TextView finishTimeMS1;
    private TextView stageTimeM1;
    private TextView stageTimeS1;
    private TextView stageTimeMS1;
    private TextView actualTimeH1;
    private TextView actualTimeM1;
    private TextView dueTimeH1;
    private TextView dueTimeM1;

    private TextView startOrder2;
    private TextView provStartH2;
    private TextView provStartM2;
    private TextView actualStartH2;
    private TextView actualStartM2;
    private TextView finishTimeH2;
    private TextView finishTimeM2;
    private TextView finishTimeS2;
    private TextView finishTimeMS2;
    private TextView stageTimeM2;
    private TextView stageTimeS2;
    private TextView stageTimeMS2;
    private TextView actualTimeH2;
    private TextView actualTimeM2;
    private TextView dueTimeH2;
    private TextView dueTimeM2;

    private TextView startOrder3;
    private TextView provStartH3;
    private TextView provStartM3;
    private TextView actualStartH3;
    private TextView actualStartM3;
    private TextView finishTimeH3;
    private TextView finishTimeM3;
    private TextView finishTimeS3;
    private TextView finishTimeMS3;
    private TextView stageTimeM3;
    private TextView stageTimeS3;
    private TextView stageTimeMS3;
    private TextView actualTimeH3;
    private TextView actualTimeM3;
    private TextView dueTimeH3;
    private TextView dueTimeM3;

    private TextView startOrder4;
    private TextView provStartH4;
    private TextView provStartM4;
    private TextView actualStartH4;
    private TextView actualStartM4;
    private TextView finishTimeH4;
    private TextView finishTimeM4;
    private TextView finishTimeS4;
    private TextView finishTimeMS4;
    private TextView stageTimeM4;
    private TextView stageTimeS4;
    private TextView stageTimeMS4;
    private TextView actualTimeH4;
    private TextView actualTimeM4;
    private TextView dueTimeH4;
    private TextView dueTimeM4;

    private Button checkIn1;
    private Button reqTime1;
    private Button checkIn2;
    private Button reqTime2;
    private Button checkIn3;
    private Button reqTime3;
    private Button checkIn4;
    private Button reqTime4;

    private CompDatabaseHelper compDatabaseHelper;
    private StageDatabaseHelper stageDatabaseHelper;
    private AControlDatabaseHelper aControlDatabaseHelper;
    private FinishDatabaseHelper finishDatabaseHelper;
    private Competitor competitor;
    private Stage stage;
    private AControl aControl;
    private Finish finish;

    private PopupWindow checkInPopup;
    private PopupWindow reqTimePopup;

    private int compID;
    private int carNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_view);

        initViews();
        initObjects();
        initListeners();

        compID = getIntent().getIntExtra("COMP_ID", 0);
        competitor = compDatabaseHelper.getCompetitorByID(compID);
        carNum = competitor.getCarNum();
        carNumTV.setText(String.valueOf(carNum));
        fillInCards();
    }

    private void fillInCards() {
        competitor = compDatabaseHelper.getCompetitorByCarNum(carNum);
        carNumTV.setText(String.valueOf(carNum));

        stage = stageDatabaseHelper.getStage(competitor.getStage1Id());
        if (stage.getStartOrder() == 0) {
            startOrder1.setText("");
        } else {
            startOrder1.setText(String.valueOf(stage.getStartOrder()));
        }
        provStartH1.setText(stage.getProvStartH());
        provStartM1.setText(stage.getProvStartM());
        actualStartH1.setText(stage.getActualStartH());
        actualStartM1.setText(stage.getActualStartM());
        finishTimeH1.setText(stage.getFinishTimeH());
        finishTimeM1.setText(stage.getFinishTimeM());
        finishTimeS1.setText(stage.getFinishTimeS());
        finishTimeMS1.setText(stage.getFinishTimeMS());
        stageTimeM1.setText(stage.getStageTimeM());
        stageTimeS1.setText(stage.getStageTimeS());
        stageTimeMS1.setText(stage.getStageTimeMS());
        actualTimeH1.setText(stage.getActualTimeH());
        actualTimeM1.setText(stage.getActualTimeM());
        dueTimeH1.setText(stage.getDueTimeH());
        dueTimeM1.setText(stage.getDueTimeM());


        stage = stageDatabaseHelper.getStage(competitor.getStage2Id());
        if (stage.getStartOrder() == 0) {
            startOrder2.setText("");
        } else {
            startOrder2.setText(String.valueOf(stage.getStartOrder()));
        }
        provStartH2.setText(stage.getProvStartH());
        provStartM2.setText(stage.getProvStartM());
        actualStartH2.setText(stage.getActualStartH());
        actualStartM2.setText(stage.getActualStartM());
        finishTimeH2.setText(stage.getFinishTimeH());
        finishTimeM2.setText(stage.getFinishTimeM());
        finishTimeS2.setText(stage.getFinishTimeS());
        finishTimeMS2.setText(stage.getFinishTimeMS());
        stageTimeM2.setText(stage.getStageTimeM());
        stageTimeS2.setText(stage.getStageTimeS());
        stageTimeMS2.setText(stage.getStageTimeMS());
        actualTimeH2.setText(stage.getActualTimeH());
        actualTimeM2.setText(stage.getActualTimeM());
        dueTimeH2.setText(stage.getDueTimeH());
        dueTimeM2.setText(stage.getDueTimeM());

        stage = stageDatabaseHelper.getStage(competitor.getStage3Id());
        if (stage.getStartOrder() == 0) {
            startOrder3.setText("");
        } else {
            startOrder3.setText(String.valueOf(stage.getStartOrder()));
        }
        provStartH3.setText(stage.getProvStartH());
        provStartM3.setText(stage.getProvStartM());
        actualStartH3.setText(stage.getActualStartH());
        actualStartM3.setText(stage.getActualStartM());
        finishTimeH3.setText(stage.getFinishTimeH());
        finishTimeM3.setText(stage.getFinishTimeM());
        finishTimeS3.setText(stage.getFinishTimeS());
        finishTimeMS3.setText(stage.getFinishTimeMS());
        stageTimeM3.setText(stage.getStageTimeM());
        stageTimeS3.setText(stage.getStageTimeS());
        stageTimeMS3.setText(stage.getStageTimeMS());
        actualTimeH3.setText(stage.getActualTimeH());
        actualTimeM3.setText(stage.getActualTimeM());
        dueTimeH3.setText(stage.getDueTimeH());
        dueTimeM3.setText(stage.getDueTimeM());

        stage = stageDatabaseHelper.getStage(competitor.getStage4Id());
        if (stage.getStartOrder() == 0) {
            startOrder4.setText("");
        } else {
            startOrder4.setText(String.valueOf(stage.getStartOrder()));
        }
        provStartH4.setText(stage.getProvStartH());
        provStartM4.setText(stage.getProvStartM());
        actualStartH4.setText(stage.getActualStartH());
        actualStartM4.setText(stage.getActualStartM());
        finishTimeH4.setText(stage.getFinishTimeH());
        finishTimeM4.setText(stage.getFinishTimeM());
        finishTimeS4.setText(stage.getFinishTimeS());
        finishTimeMS4.setText(stage.getFinishTimeMS());
        stageTimeM4.setText(stage.getStageTimeM());
        stageTimeS4.setText(stage.getStageTimeS());
        stageTimeMS4.setText(stage.getStageTimeMS());
        actualTimeH4.setText(stage.getActualTimeH());
        actualTimeM4.setText(stage.getActualTimeM());
        dueTimeH4.setText(stage.getDueTimeH());
        dueTimeM4.setText(stage.getDueTimeM());
    }

    private void initObjects() {
        compDatabaseHelper = new CompDatabaseHelper(activity);
        stageDatabaseHelper = new StageDatabaseHelper(activity);
        aControlDatabaseHelper = new AControlDatabaseHelper(activity);
        finishDatabaseHelper = new FinishDatabaseHelper(activity);
        competitor = new Competitor();
        stage = new Stage();
        aControl = new AControl();
        finish = new Finish();
    }

    private void initViews() {
        scrollView = findViewById(R.id.CompViewScrollView);
        carNumTV = findViewById(R.id.CarNum);

        startOrder1 = findViewById(R.id.Sec2Oval);
        provStartH1 = findViewById(R.id.S2PSH);
        provStartM1 = findViewById(R.id.S2PSM);
        actualStartH1 = findViewById(R.id.S2ASH);
        actualStartM1 = findViewById(R.id.S2ASM);
        finishTimeH1 = findViewById(R.id.S2FTH);
        finishTimeM1 = findViewById(R.id.S2FTM);
        finishTimeS1 = findViewById(R.id.S2FTS);
        finishTimeMS1 = findViewById(R.id.S2FTMS);
        stageTimeM1 = findViewById(R.id.S2TTM);
        stageTimeS1 = findViewById(R.id.S2TTS);
        stageTimeMS1 = findViewById(R.id.S2TTMS);
        actualTimeH1 = findViewById(R.id.S2ATH);
        actualTimeM1 = findViewById(R.id.S2ATM);
        dueTimeH1 = findViewById(R.id.S2DTH);
        dueTimeM1 = findViewById(R.id.S2DTM);

        startOrder2 = findViewById(R.id.Sec3Oval);
        provStartH2 = findViewById(R.id.S3PSH);
        provStartM2 = findViewById(R.id.S3PSM);
        actualStartH2 = findViewById(R.id.S3ASH);
        actualStartM2 = findViewById(R.id.S3ASM);
        finishTimeH2 = findViewById(R.id.S3FTH);
        finishTimeM2 = findViewById(R.id.S3FTM);
        finishTimeS2 = findViewById(R.id.S3FTS);
        finishTimeMS2 = findViewById(R.id.S3FTMS);
        stageTimeM2 = findViewById(R.id.S3TTM);
        stageTimeS2 = findViewById(R.id.S3TTS);
        stageTimeMS2 = findViewById(R.id.S3TTMS);
        actualTimeH2 = findViewById(R.id.S3ATH);
        actualTimeM2 = findViewById(R.id.S3ATM);
        dueTimeH2 = findViewById(R.id.S3DTH);
        dueTimeM2 = findViewById(R.id.S3DTM);

        startOrder3 = findViewById(R.id.Sec4Oval);
        provStartH3 = findViewById(R.id.S4PSH);
        provStartM3 = findViewById(R.id.S4PSM);
        actualStartH3 = findViewById(R.id.S4ASH);
        actualStartM3 = findViewById(R.id.S4ASM);
        finishTimeH3 = findViewById(R.id.S4FTH);
        finishTimeM3 = findViewById(R.id.S4FTM);
        finishTimeS3 = findViewById(R.id.S4FTS);
        finishTimeMS3 = findViewById(R.id.S4FTMS);
        stageTimeM3 = findViewById(R.id.S4TTM);
        stageTimeS3 = findViewById(R.id.S4TTS);
        stageTimeMS3 = findViewById(R.id.S4TTMS);
        actualTimeH3 = findViewById(R.id.S4ATH);
        actualTimeM3 = findViewById(R.id.S4ATM);
        dueTimeH3 = findViewById(R.id.S4DTH);
        dueTimeM3 = findViewById(R.id.S4DTM);

        startOrder4 = findViewById(R.id.Sec5Oval);
        provStartH4 = findViewById(R.id.S5PSH);
        provStartM4 = findViewById(R.id.S5PSM);
        actualStartH4 = findViewById(R.id.S5ASH);
        actualStartM4 = findViewById(R.id.S5ASM);
        finishTimeH4 = findViewById(R.id.S5FTH);
        finishTimeM4 = findViewById(R.id.S5FTM);
        finishTimeS4 = findViewById(R.id.S5FTS);
        finishTimeMS4 = findViewById(R.id.S5FTMS);
        stageTimeM4 = findViewById(R.id.S5TTM);
        stageTimeS4 = findViewById(R.id.S5TTS);
        stageTimeMS4 = findViewById(R.id.S5TTMS);
        actualTimeH4 = findViewById(R.id.S5ATH);
        actualTimeM4 = findViewById(R.id.S5ATM);
        dueTimeH4 = findViewById(R.id.S5DTH);
        dueTimeM4 = findViewById(R.id.S5DTM);

        checkIn1 = findViewById(R.id.CheckIn1);
        reqTime1 = findViewById(R.id.ReqTime1);
        checkIn2 = findViewById(R.id.CheckIn2);
        reqTime2 = findViewById(R.id.ReqTime2);
        checkIn3 = findViewById(R.id.CheckIn3);
        reqTime3 = findViewById(R.id.ReqTime3);
        checkIn4 = findViewById(R.id.CheckIn4);
        reqTime4 = findViewById(R.id.ReqTime4);
    }

    private void initListeners() {
        checkIn1.setOnClickListener(this);
        reqTime1.setOnClickListener(this);
        checkIn2.setOnClickListener(this);
        reqTime2.setOnClickListener(this);
        checkIn3.setOnClickListener(this);
        reqTime3.setOnClickListener(this);
        checkIn4.setOnClickListener(this);
        reqTime4.setOnClickListener(this);
    }

    private void ShowCheckInPopup(String message, int stageNum) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.checkin_popup, null);

        checkInPopup = new PopupWindow(this);
        checkInPopup.setContentView(layout);
        checkInPopup.setWidth(width);
        checkInPopup.setHeight(height);
        checkInPopup.setFocusable(true);
        checkInPopup.setBackgroundDrawable(null);
        checkInPopup.showAtLocation(layout, Gravity.CENTER, 1, 1);

        TextView text = layout.findViewById(R.id.ConfirmCheckIn);
        text.setText(message);

        Button yesCheckIn = layout.findViewById(R.id.YesCheckInButton);
        yesCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add competitor to AControl database
                addToAControl(stageNum);
                checkInPopup.dismiss();
            }
        });

        Button noCheckIn = layout.findViewById(R.id.NoCheckInButton);
        noCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInPopup.dismiss();
            }
        });
    }

    private void addToAControl(int stageNum) {
        if (!aControlDatabaseHelper.checkAControl(stageNum, carNum)) {
            int lastSO = aControlDatabaseHelper.getCurrStartOrder(stageNum);
            aControl.setStartOrder(lastSO+1);
            aControl.setStage(stageNum);
            aControl.setCarNum(carNum);
            competitor = compDatabaseHelper.getCompetitorByCarNum(carNum);
            aControl.setStage1ID(competitor.getStageId(stageNum-1));
            aControl.setStage2ID(competitor.getStageId(stageNum));
            aControlDatabaseHelper.addAControl(aControl);
        }
    }

    private void ShowReqTimePopup(String message, int stageNum) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.reqtime_popup, null);

        reqTimePopup = new PopupWindow(this);
        reqTimePopup.setContentView(layout);
        reqTimePopup.setWidth(width);
        reqTimePopup.setHeight(height);
        reqTimePopup.setFocusable(true);
        reqTimePopup.setBackgroundDrawable(null);
        reqTimePopup.showAtLocation(layout, Gravity.CENTER, 1, 1);

        TextView text = layout.findViewById(R.id.ConfirmReqTime);
        text.setText(message);

        Button yesReqTime = layout.findViewById(R.id.YesReqTimeButton);
        yesReqTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add competitor to Finish database
                addToFinish(stageNum);
                reqTimePopup.dismiss();
            }
        });

        Button noReqTime = layout.findViewById(R.id.NoReqTimeButton);
        noReqTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reqTimePopup.dismiss();
            }
        });
    }

    private void addToFinish(int stageNum) {
        if (!finishDatabaseHelper.checkFinish(stageNum, carNum)) {
            int lastFO = finishDatabaseHelper.getCurrFinishOrder(stageNum);
            finish.setFinishOrder(lastFO+1);
            finish.setStage(stageNum);
            finish.setCarNum(carNum);
            competitor = compDatabaseHelper.getCompetitorByCarNum(carNum);
            finish.setStageID(competitor.getStageId(stageNum));
            finishDatabaseHelper.addFinish(finish);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CheckIn1:
                ShowCheckInPopup("Check In to Stage 1?", 1);
                break;
            case R.id.ReqTime1:
                ShowReqTimePopup("Request Time for Stage 1?", 1);
                break;
            case R.id.CheckIn2:
                ShowCheckInPopup("Check In to Stage 2?", 2);
                break;
            case R.id.ReqTime2:
                ShowReqTimePopup("Request Time for Stage 2?", 2);
                break;
            case R.id.CheckIn3:
                ShowCheckInPopup("Check In to Stage 3?", 3);
                break;
            case R.id.ReqTime3:
                ShowReqTimePopup("Request Time for Stage 3?", 3);
                break;
            case R.id.CheckIn4:
                ShowCheckInPopup("Check In to Stage 4?", 4);
                break;
            case R.id.ReqTime4:
                ShowReqTimePopup("Request Time for Stage 4?", 4);
                break;
        }
    }
}