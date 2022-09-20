package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.model.AControl;
import com.example.rallytimingapp.model.Finish;
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.model.Start;
import com.example.rallytimingapp.sql.FinishDatabaseHelper;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.example.rallytimingapp.sql.StartDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = StartActivity.this;

    private ScrollView scrollView;

    private Button backButton;
    private Button changeSOButton;
    private Button returnTCButton;
    private Button prevButton;
    private Button nextButton;

    private TextView carNumTV;
    private TextView startOrderTV;

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

    private TextView startOrderTC;
    private TextView provStartH;
    private TextView provStartM;
    private TextView actualStartH;
    private TextView actualStartM;
    private TextView finishTimeH;
    private TextView finishTimeM;
    private TextView finishTimeS;
    private TextView finishTimeMS;
    private TextView stageTimeM;
    private TextView stageTimeS;
    private TextView stageTimeMS;
    private TextView actualTimeH;
    private TextView actualTimeM;
    private TextView dueTimeH;
    private TextView dueTimeM;

    private PopupWindow returnTCPopup;
    private PopupWindow changeSOPopup;

    private Start start;
    private Stage stage;
    private StartDatabaseHelper startDatabaseHelper;
    private StageDatabaseHelper stageDatabaseHelper;

    private int stageNum;
    private int startOrder;
    private int carNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initViews();
        initObjects();
        initListeners();

        stageNum = getIntent().getIntExtra("STAGE", 0);
        startOrder = 1;
        fillInCards();
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

    private void initObjects() {
        stageDatabaseHelper = new StageDatabaseHelper(activity);
        startDatabaseHelper = new StartDatabaseHelper(activity);
        stage = new Stage();
        start = new Start();
    }

    private void initViews() {
        scrollView = findViewById(R.id.StartScrollView);

        backButton = findViewById(R.id.STCBackButton);
        changeSOButton = findViewById(R.id.SChangeSOButton);
        returnTCButton = findViewById(R.id.SReturnButton);
        prevButton = findViewById(R.id.StartPrevButton);
        nextButton = findViewById(R.id.StartNextButton);

        carNumTV = findViewById(R.id.StartCarNum);
        startOrderTV = findViewById(R.id.StartOrder);

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

        startOrderTC = findViewById(R.id.STCOval);
        provStartH = findViewById(R.id.STCPSH);
        provStartM = findViewById(R.id.STCPSM);
        actualStartH = findViewById(R.id.STCASH);
        actualStartH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(actualStartH.getText().toString().length()==2)
                {
                    if (Integer.valueOf(actualStartH.getText().toString()) > 24) {
                        actualStartH.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        actualStartH.clearFocus();
                        actualStartM.requestFocus();
                        actualStartM.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        actualStartM = findViewById(R.id.STCASM);
        actualStartM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(actualStartM.getText().toString().length()==2)
                {
                    if (Integer.valueOf(actualStartM.getText().toString()) > 59) {
                        actualStartM.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        actualStartM.clearFocus();
                        startOrderTC.requestFocus();
                        startOrderTC.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        finishTimeH = findViewById(R.id.STCFTH);
        finishTimeM = findViewById(R.id.STCFTM);
        finishTimeS = findViewById(R.id.STCFTS);
        finishTimeMS = findViewById(R.id.STCFTMS);
        stageTimeM = findViewById(R.id.STCTTM);
        stageTimeS = findViewById(R.id.STCTTS);
        stageTimeMS = findViewById(R.id.STCTTMS);
        actualTimeH = findViewById(R.id.STCATH);
        actualTimeM = findViewById(R.id.STCATM);
        dueTimeH = findViewById(R.id.STCDTH);
        dueTimeM = findViewById(R.id.STCDTM);
    }

    private void initListeners() {
        backButton.setOnClickListener(this);
        changeSOButton.setOnClickListener(this);
        returnTCButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.STCBackButton:
                Intent intent = new Intent(this, ChooseStartActivity.class);
                startActivity(intent);
                break;
            case R.id.SChangeSOButton:
                ShowChangeSOPopup();
                break;
            case R.id.SReturnButton:
                ShowReturnTCPopup();
                break;
            case R.id.StartPrevButton:
                previousTC();
                break;
            case R.id.StartNextButton:
                nextTC();
                break;
        }
    }

    private void fillInCards() {
        if (startDatabaseHelper.getStage(stageNum).size() != 0) {
            carNum = startDatabaseHelper.getCarNum(stageNum, startOrder);
            carNumTV.setText(String.valueOf(carNum));
            start = startDatabaseHelper.getStart(stageNum, startOrder);
            startOrderTV.setText(String.valueOf(startOrder));
            stage = stageDatabaseHelper.getStage(start.getStageID());
            startOrderTC.setText(String.valueOf(stage.getStartOrder()));
            provStartH.setText(stage.getProvStartH());
            provStartM.setText(stage.getProvStartM());
            actualStartH.setText(stage.getActualStartH());
            actualStartM.setText(stage.getActualStartM());
            finishTimeH.setText(stage.getFinishTimeH());
            finishTimeM.setText(stage.getFinishTimeM());
            finishTimeS.setText(stage.getFinishTimeS());
            finishTimeMS.setText(stage.getFinishTimeMS());
            stageTimeM.setText(stage.getStageTimeM());
            stageTimeS.setText(stage.getStageTimeS());
            stageTimeMS.setText(stage.getStageTimeMS());
            actualTimeH.setText(stage.getActualTimeH());
            actualTimeM.setText(stage.getActualTimeM());
            dueTimeH.setText(stage.getDueTimeH());
            dueTimeM.setText(stage.getDueTimeM());
        } else {
            startOrderTV.setText("0");
            carNumTV.setText("");
        }
        actualStartH.requestFocus();
        actualStartH.setCursorVisible(true);
    }

    private void ShowChangeSOPopup() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.change_so_popup, null);

        changeSOPopup = new PopupWindow(this);
        changeSOPopup.setContentView(layout);
        changeSOPopup.setWidth(width);
        changeSOPopup.setHeight(height);
        changeSOPopup.setFocusable(true);
        changeSOPopup.setBackgroundDrawable(null);
        changeSOPopup.showAtLocation(layout, Gravity.CENTER, 1, 1);

        Button leftSO = layout.findViewById(R.id.LeftSOButton);
        Button rightSO = layout.findViewById(R.id.RightSOButton);

        Start currStart = startDatabaseHelper.getStart(stageNum, startOrder);

        int currSO = startDatabaseHelper.getCurrStartOrder(stageNum);
        int prevSO = startOrder - 1;
        int nextSO = startOrder + 1;
        if (prevSO < 1) {
            leftSO.setText("-");
        } else {
            leftSO.setText(String.valueOf(prevSO));
            leftSO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Start start2 = startDatabaseHelper.getStart(stageNum, prevSO);
                    currStart.setStartOrder(prevSO);
                    startDatabaseHelper.updateStart(currStart);
                    stage = stageDatabaseHelper.getStage(currStart.getStageID());
                    stage.setStartOrder(prevSO);
                    stageDatabaseHelper.updateStage(stage);

                    start2.setStartOrder(startOrder);
                    startDatabaseHelper.updateStart(start2);
                    stage = stageDatabaseHelper.getStage(start2.getStageID());
                    stage.setStartOrder(startOrder);
                    stageDatabaseHelper.updateStage(stage);

                    changeSOPopup.dismiss();
                    fillInCards();
                }
            });
        }
        if (nextSO > currSO) {
            rightSO.setText("-");
        } else {
            rightSO.setText(String.valueOf(nextSO));
            rightSO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Start start2 = startDatabaseHelper.getStart(stageNum, nextSO);
                    currStart.setStartOrder(nextSO);
                    startDatabaseHelper.updateStart(currStart);
                    stage = stageDatabaseHelper.getStage(currStart.getStageID());
                    stage.setStartOrder(nextSO);
                    stageDatabaseHelper.updateStage(stage);

                    start2.setStartOrder(startOrder);
                    startDatabaseHelper.updateStart(start2);
                    stage = stageDatabaseHelper.getStage(start2.getStageID());
                    stage.setStartOrder(startOrder);
                    stageDatabaseHelper.updateStage(stage);

                    changeSOPopup.dismiss();
                    fillInCards();
                }
            });
        }

        Button cancel = layout.findViewById(R.id.CancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSOPopup.dismiss();
            }
        });
    }

    private void ShowReturnTCPopup() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.return_timecard_popup, null);

        returnTCPopup = new PopupWindow(this);
        returnTCPopup.setContentView(layout);
        returnTCPopup.setWidth(width);
        returnTCPopup.setHeight(height);
        returnTCPopup.setFocusable(true);
        returnTCPopup.setBackgroundDrawable(null);
        returnTCPopup.showAtLocation(layout, Gravity.CENTER, 1, 1);

        start = startDatabaseHelper.getStart(stageNum, startOrder);
        carNum = start.getCarNum();
        String currCarNum = String.valueOf(carNum);
        TextView text = layout.findViewById(R.id.ReturnTC);
        text.setText("Return Time Card to Car " + currCarNum + "?");

        Button yesReturn = layout.findViewById(R.id.YesReturnButton);
        yesReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stage = stageDatabaseHelper.getStage(start.getStageID());
                String inputASH = actualStartH.getText().toString();
                String inputASM = actualStartM.getText().toString();
                stage.setActualStart(inputASH + ":" + inputASM);
                String inputSO = startOrderTC.getText().toString();
                stage.setStartOrder(Integer.valueOf(inputSO));
                stageDatabaseHelper.updateStage(stage);
                returnTCPopup.dismiss();
            }
        });

        Button noReturn = layout.findViewById(R.id.NoReturnButton);
        noReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnTCPopup.dismiss();
            }
        });
    }

    public void previousTC() {
        if (startOrder > 1) {
            startOrder = startOrder - 1;
            fillInCards();
        }
    }

    public void nextTC() {
        int currStartOrder = startDatabaseHelper.getCurrStartOrder(stageNum);
        if ((startOrder + 1) <= currStartOrder) {
            startOrder = startOrder + 1;
            fillInCards();
        }
    }
}