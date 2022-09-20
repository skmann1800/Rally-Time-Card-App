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
import android.widget.HorizontalScrollView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.model.AControl;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.model.Start;
import com.example.rallytimingapp.sql.AControlDatabaseHelper;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.example.rallytimingapp.sql.StartDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class AControlActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AControlActivity.this;

    private HorizontalScrollView timeCard1;
    private ScrollView scrollView;

    private Button backButton;
    private Button changeSOButton;
    private Button returnTCButton;
    private Button nextButton;
    private Button prevButton;

    private TextView carNumTV;
    private TextView startOrderTV;

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

    private PopupWindow returnTCPopup;
    private PopupWindow changeSOPopup;

    private AControl aControl;
    private Start start;
    private Stage stage;
    private Competitor competitor;
    private AControlDatabaseHelper aControlDatabaseHelper;
    private StartDatabaseHelper startDatabaseHelper;
    private StageDatabaseHelper stageDatabaseHelper;
    private CompDatabaseHelper compDatabaseHelper;

    private int stageNum;
    private int startOrder;
    private int carNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acontrol);

        initViews();
        initObjects();
        initListeners();

        stageNum = getIntent().getIntExtra("STAGE", 0);
        startOrder = 1;
        fillInCards();
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

    private void initObjects() {
        stageDatabaseHelper = new StageDatabaseHelper(activity);
        aControlDatabaseHelper = new AControlDatabaseHelper(activity);
        startDatabaseHelper = new StartDatabaseHelper(activity);
        compDatabaseHelper = new CompDatabaseHelper(activity);
        competitor = new Competitor();
        stage = new Stage();
        aControl = new AControl();
        start = new Start();
    }

    private void initViews() {
        scrollView = findViewById(R.id.ControlScrollView);

        carNumTV = findViewById(R.id.ControlCarNum);
        startOrderTV = findViewById(R.id.ControlStartOrder);
        timeCard1 = findViewById(R.id.ControlTC1);

        backButton = findViewById(R.id.CTCBackButton);
        changeSOButton = findViewById(R.id.CChangeSOButton);
        returnTCButton = findViewById(R.id.CReturnButton);
        nextButton = findViewById(R.id.ControlNextButton);
        prevButton = findViewById(R.id.ControlPrevButton);

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

        startOrder1 = findViewById(R.id.CTC1Oval);
        provStartH1 = findViewById(R.id.CTC1PSH);
        provStartM1 = findViewById(R.id.CTC1PSM);
        actualStartH1 = findViewById(R.id.CTC1ASH);
        actualStartM1 = findViewById(R.id.CTC1ASM);
        finishTimeH1 = findViewById(R.id.CTC1FTH);
        finishTimeM1 = findViewById(R.id.CTC1FTM);
        finishTimeS1 = findViewById(R.id.CTC1FTS);
        finishTimeMS1 = findViewById(R.id.CTC1FTMS);
        stageTimeM1 = findViewById(R.id.CTC1TTM);
        stageTimeS1 = findViewById(R.id.CTC1TTS);
        stageTimeMS1 = findViewById(R.id.CTC1TTMS);
        actualTimeH1 = findViewById(R.id.CTC1ATH);
        actualTimeH1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(actualTimeH1.getText().toString().length()==2)
                {
                    if (Integer.valueOf(actualTimeH1.getText().toString()) > 24) {
                        actualTimeH1.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        actualTimeH1.clearFocus();
                        actualTimeM1.requestFocus();
                        actualTimeM1.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        actualTimeM1 = findViewById(R.id.CTC1ATM);
        actualTimeM1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(actualTimeM1.getText().toString().length()==2)
                {
                    if (Integer.valueOf(actualTimeM1.getText().toString()) > 59) {
                        actualTimeM1.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        actualTimeM1.clearFocus();
                        provStartH2.requestFocus();
                        provStartH2.setCursorVisible(true);
                        int actualTimeH = Integer.valueOf(actualTimeH1.getText().toString());
                        int actualTimeM = Integer.valueOf(actualTimeM1.getText().toString());
                        int provStartM = actualTimeM + 3;
                        if (provStartM > 59) {
                            provStartH2.setText(String.valueOf(actualTimeH + 1));
                            provStartM2.setText("0" + (provStartM - 60));
                        } else {
                            provStartH2.setText(String.valueOf(actualTimeH));
                            String mins = String.valueOf(provStartM);
                            if (mins.length() < 2) {
                                provStartM2.setText("0" + mins);
                            } else {
                                provStartM2.setText(mins);
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        dueTimeH1 = findViewById(R.id.CTC1DTH);
        dueTimeM1 = findViewById(R.id.CTC1DTM);

        startOrder2 = findViewById(R.id.CTC2Oval);
        provStartH2 = findViewById(R.id.CTC2PSH);
        provStartH2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(provStartH2.getText().toString().length()==2)
                {
                    if (Integer.valueOf(provStartH2.getText().toString()) > 24) {
                        provStartH2.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        provStartH2.clearFocus();
                        provStartM2.requestFocus();
                        provStartM2.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        provStartM2 = findViewById(R.id.CTC2PSM);
        provStartM2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(provStartM2.getText().toString().length()==2)
                {
                    if (Integer.valueOf(provStartM2.getText().toString()) > 59) {
                        provStartM2.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        provStartM2.clearFocus();
                        startOrder2.requestFocus();
                        startOrder2.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        actualStartH2 = findViewById(R.id.CTC2ASH);
        actualStartM2 = findViewById(R.id.CTC2ASM);
        finishTimeH2 = findViewById(R.id.CTC2FTH);
        finishTimeM2 = findViewById(R.id.CTC2FTM);
        finishTimeS2 = findViewById(R.id.CTC2FTS);
        finishTimeMS2 = findViewById(R.id.CTC2FTMS);
        stageTimeM2 = findViewById(R.id.CTC2TTM);
        stageTimeS2 = findViewById(R.id.CTC2TTS);
        stageTimeMS2 = findViewById(R.id.CTC2TTMS);
        actualTimeH2 = findViewById(R.id.CTC2ATH);
        actualTimeM2 = findViewById(R.id.CTC2ATM);
        dueTimeH2 = findViewById(R.id.CTC2DTH);
        dueTimeM2 = findViewById(R.id.CTC2DTM);
    }

    private void initListeners() {
        backButton.setOnClickListener(this);
        changeSOButton.setOnClickListener(this);
        returnTCButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CTCBackButton:
                Intent intent = new Intent(this, ChooseControlActivity.class);
                startActivity(intent);
                break;
            case R.id.CChangeSOButton:
                ShowChangeSOPopup();
                break;
            case R.id.CReturnButton:
                ShowReturnTCPopup();
                break;
            case R.id.ControlPrevButton:
                previousTC();
                break;
            case R.id.ControlNextButton:
                nextTC();
                break;
        }
    }

    private void fillInCards() {
        if (aControlDatabaseHelper.getStage(stageNum).size() != 0) {
            carNum = aControlDatabaseHelper.getCarNum(stageNum, startOrder);
            carNumTV.setText(String.valueOf(carNum));
            aControl = aControlDatabaseHelper.getAControl(aControlDatabaseHelper.getAControlID(stageNum, carNum));
            startOrderTV.setText(String.valueOf(startOrder));
            if (stageNum != 1) {
                actualStartH1.requestFocus();
                actualStartH1.setCursorVisible(true);
                stage = stageDatabaseHelper.getStage(aControl.getStage1ID());
                startOrder1.setText(String.valueOf(stage.getStartOrder()));
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
            } else {
                provStartH2.requestFocus();
                provStartH2.setCursorVisible(true);
            }
            stage = stageDatabaseHelper.getStage(aControl.getStage2ID());
            int sOrder = stage.getStartOrder();
            if (sOrder == 0) {
                startOrder2.setText(String.valueOf(startOrder));
            } else {
                startOrder2.setText(String.valueOf(sOrder));
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
        } else {
            carNumTV.setText("");
            startOrderTV.setText("0");
        }

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

        AControl currAControl = aControlDatabaseHelper.getAControl(stageNum, startOrder);

        int currSO = aControlDatabaseHelper.getCurrStartOrder(stageNum);
        int prevSO = startOrder - 1;
        int nextSO = startOrder + 1;
        if (prevSO < 1) {
            leftSO.setText("-");
        } else {
            leftSO.setText(String.valueOf(prevSO));
            leftSO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AControl aControl2 = aControlDatabaseHelper.getAControl(stageNum, prevSO);
                    currAControl.setStartOrder(prevSO);
                    aControlDatabaseHelper.updateAControl(currAControl);
                    stage = stageDatabaseHelper.getStage(currAControl.getStage2ID());
                    stage.setStartOrder(prevSO);
                    stageDatabaseHelper.updateStage(stage);

                    aControl2.setStartOrder(startOrder);
                    aControlDatabaseHelper.updateAControl(aControl2);
                    stage = stageDatabaseHelper.getStage(aControl2.getStage2ID());
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
                    AControl aControl2 = aControlDatabaseHelper.getAControl(stageNum, nextSO);
                    currAControl.setStartOrder(nextSO);
                    aControlDatabaseHelper.updateAControl(currAControl);
                    stage = stageDatabaseHelper.getStage(currAControl.getStage2ID());
                    stage.setStartOrder(nextSO);
                    stageDatabaseHelper.updateStage(stage);

                    aControl2.setStartOrder(startOrder);
                    aControlDatabaseHelper.updateAControl(aControl2);
                    stage = stageDatabaseHelper.getStage(aControl2.getStage2ID());
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

        aControl = aControlDatabaseHelper.getAControl(stageNum, startOrder);
        carNum = aControl.getCarNum();
        String currCarNum = String.valueOf(carNum);
        TextView text = layout.findViewById(R.id.ReturnTC);
        text.setText("Return Time Card to Car " + currCarNum + "?");

        Button yesReturn = layout.findViewById(R.id.YesReturnButton);
        yesReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stageNum != 1) {
                    stage = stageDatabaseHelper.getStage(aControl.getStage1ID());
                    String inputATH = actualTimeH1.getText().toString();
                    String inputATM = actualTimeM1.getText().toString();
                    stage.setActualTime(inputATH + ":" + inputATM);
                    stageDatabaseHelper.updateStage(stage);
                }
                stage = stageDatabaseHelper.getStage(aControl.getStage2ID());
                String inputPSH = provStartH2.getText().toString();
                String inputPSM = provStartM2.getText().toString();
                stage.setProvStart(inputPSH + ":" + inputPSM);
                String inputSO = startOrder2.getText().toString();
                stage.setStartOrder(Integer.valueOf(inputSO));
                stageDatabaseHelper.updateStage(stage);
                returnTCPopup.dismiss();

                addToStart(stageNum);
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

    private void addToStart(int stageNum) {
        if (!startDatabaseHelper.checkStart(stageNum, carNum)) {
            int lastSO = startDatabaseHelper.getCurrStartOrder(stageNum);
            start.setStartOrder(lastSO+1);
            start.setStage(stageNum);
            start.setCarNum(carNum);
            competitor = compDatabaseHelper.getCompetitorByCarNum(carNum);
            start.setStageID(competitor.getStageId(stageNum));
            startDatabaseHelper.addStart(start);
        }
    }

    public void previousTC() {
        if (startOrder > 1) {
            startOrder = startOrder - 1;
            fillInCards();
        }
    }

    public void nextTC() {
        int currStartOrder = aControlDatabaseHelper.getCurrStartOrder(stageNum);
        if ((startOrder + 1) <= currStartOrder) {
            startOrder = startOrder + 1;
            fillInCards();
        }
    }
}