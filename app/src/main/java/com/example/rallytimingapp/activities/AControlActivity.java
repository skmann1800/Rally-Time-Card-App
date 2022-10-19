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

        // Retrieve stage number from intent
        stageNum = getIntent().getIntExtra("STAGE", 0);
        // Set default start order to be 1
        startOrder = 1;
        // Fill in the timecards
        fillInCards();
        // Based on the stage number, fill in the labels as follows
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

    // Method to initialise objects
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

    // Method to initialise views
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
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to move onto the next text box, once this one is full
        actualTimeH1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The max input length is 2 digits
                if(actualTimeH1.getText().toString().length()==2)
                {
                    // This box contains the hours of a time, so this input cannot be
                    // larger than 24
                    if (Integer.valueOf(actualTimeH1.getText().toString()) > 24) {
                        // If input is larger than 24, reset the text and display an error message.
                        actualTimeH1.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // If the input is valid, move to the next text box
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
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to autofill the next box
        actualTimeM1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The max input length is 2 digits
                if(actualTimeM1.getText().toString().length()==2)
                {
                    // This box contains the minutes of a time, so this input cannot be
                    // larger than 59
                    if (Integer.valueOf(actualTimeM1.getText().toString()) > 59) {
                        // If input is larger than 59, reset the text and display an error message.
                        actualTimeM1.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise, move to the next box.
                        actualTimeM1.clearFocus();
                        provStartH2.requestFocus();
                        provStartH2.setCursorVisible(true);
                        // Save the values that are now in the actual time boxes
                        int actualTimeH = Integer.valueOf(actualTimeH1.getText().toString());
                        int actualTimeM = Integer.valueOf(actualTimeM1.getText().toString());
                        // Calculate the minutes +3
                        int provStartM = actualTimeM + 3;
                        // If that total is larger than 59, we move to the next hour
                        if (provStartM > 59) {
                            // Fill in the provisional start hours as the next hour
                            provStartH2.setText(String.valueOf(actualTimeH + 1));
                            // Then fill in the minutes, starting with a 0, as this result
                            // will only ever be 0, 1 or 2
                            provStartM2.setText("0" + (provStartM - 60));
                        } else {
                            // If it is less that 59, then fill in the same hour value
                            provStartH2.setText(String.valueOf(actualTimeH));
                            String mins = String.valueOf(provStartM);
                            // If there is less than 1 digit in the minutes, add a 0
                            // to make it 2 digits
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
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to move onto the next text box, once this one is full
        provStartH2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Max input length is 2
                if(provStartH2.getText().toString().length()==2)
                {
                    // This is an hours box, so input can't be larger than 24
                    if (Integer.valueOf(provStartH2.getText().toString()) > 24) {
                        // If input is larger than 24, reset the text and show error message
                        provStartH2.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // If input is valid, move to the next box
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
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to move onto the next text box, once this one is full
        provStartM2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Max input length is 2
                if(provStartM2.getText().toString().length()==2)
                {
                    // This is a minutes box, so input can't be larger than 59
                    if (Integer.valueOf(provStartM2.getText().toString()) > 59) {
                        // If input is larger than 59, reset text and show error message
                        provStartM2.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // If input is valid, move to next box
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
        // Initialise listeners for the buttons
        backButton.setOnClickListener(this);
        changeSOButton.setOnClickListener(this);
        returnTCButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Switch case for each button
        switch (view.getId()) {
            case R.id.CTCBackButton:
                // Back button goes back to the choose stage activity
                Intent intent = new Intent(this, ChooseStageActivity.class);
                intent.putExtra("ROLE", "Finish");
                startActivity(intent);
                break;
            case R.id.CChangeSOButton:
                // Change start order button shows a pop-up
                ShowChangeSOPopup();
                break;
            case R.id.CReturnButton:
                // Return timecard button shows a pop-up
                ShowReturnTCPopup();
                break;
            case R.id.ControlPrevButton:
                // Left arrow button goes to the previous start order
                previousTC();
                break;
            case R.id.ControlNextButton:
                // Left arrow button goes to the next start order
                nextTC();
                break;
        }
    }

    private void fillInCards() {
        // Fill in the text boxes of the timecards, if there is at least one entry in the database
        if (aControlDatabaseHelper.getStage(stageNum).size() != 0) {
            // Get the number of the car in the current start order
            carNum = aControlDatabaseHelper.getCarNum(stageNum, startOrder);
            carNumTV.setText(String.valueOf(carNum));
            // Get the A Control Database entry for the current stage and car number
            aControl = aControlDatabaseHelper.getAControl(aControlDatabaseHelper.getAControlID(stageNum, carNum));
            startOrderTV.setText(String.valueOf(startOrder));

            // If it is stage 1, the first timecard is not visible, so only fill it in
            // if the stage is not stage 1
            if (stageNum != 1) {
                // Set what text box the focus will start on
                actualStartH1.requestFocus();
                actualStartH1.setCursorVisible(true);
                // Get the first stage, which contains all the data, from the aControl object
                stage = stageDatabaseHelper.getStage(aControl.getStage1ID());
                startOrder1.setText(String.valueOf(stage.getStartOrder()));
                // Fill in the timecard with the data from the stage object
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
                // If it is stage 1, set a different focus
                provStartH2.requestFocus();
                provStartH2.setCursorVisible(true);
            }
            // All stages need the 2nd timecard filled in
            // Get the second stage, which contains all the data, from the aControl object
            stage = stageDatabaseHelper.getStage(aControl.getStage2ID());
            // If the start order has not yet been set, it was initialised to be 0.
            // If the start order is 0, change it to the current start order.
            // Otherwise, use the value it is set to.
            int sOrder = stage.getStartOrder();
            if (sOrder == 0) {
                startOrder2.setText(String.valueOf(startOrder));
            } else {
                startOrder2.setText(String.valueOf(sOrder));
            }
            // Fill in the rest of the timecard using the stage object
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
            // If there are no entries in the database, set the car number to
            // null and the start order to 0 and leave everything else blank.
            carNumTV.setText("");
            startOrderTV.setText("0");
        }
    }

    private void ShowChangeSOPopup() {
        // Displays a pop-up to confirm if the user wants to change the start order
        // and what they want to change it to
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

        // Get the A Control Database entry for the current start order
        AControl currAControl = aControlDatabaseHelper.getAControl(stageNum, startOrder);

        // Get the start order of the last entry to the A Control database,
        // This will be used to ensure that a swap is not made with a start order that doesn't exist yet
        int currSO = aControlDatabaseHelper.getCurrStartOrder(stageNum);
        // Get the start order before and after the current one
        int prevSO = startOrder - 1;
        int nextSO = startOrder + 1;
        // If the previous start order is less than one, set the left button text to "-",
        // as it is not possible to switch to start order 0
        if (prevSO < 1) {
            leftSO.setText("-");
        } else {
            // Otherwise set the text of the left button to the value of prevSO
            leftSO.setText(String.valueOf(prevSO));
            // Add an on click listener for this button
            leftSO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Call the method to swap the start orders
                    swap(startOrder, prevSO);

                    // Dismiss the pop-up then fill in the cards again
                    changeSOPopup.dismiss();
                    fillInCards();
                }
            });
        }
        // If the next start order is larger than the most recent start order to be added,
        // then there is no entry in the database, so a swap is not possible, so set right button
        // text to "-"
        if (nextSO > currSO) {
            rightSO.setText("-");
        } else {
            // Otherwise set the text to the next start order.
            rightSO.setText(String.valueOf(nextSO));
            // Set an on click listener for this button
            rightSO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Call the method to swap the start orders
                    swap(startOrder, nextSO);

                    // Dismiss the pop-up then fill in the cards again
                    changeSOPopup.dismiss();
                    fillInCards();
                }
            });
        }

        // Cancel button which closes the pop-up
        Button cancel = layout.findViewById(R.id.CancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSOPopup.dismiss();
            }
        });
    }

    public void swap(int currSO, int newSO) {
        // Get the A Control Database entry for the current start order
        AControl currAControl = aControlDatabaseHelper.getAControl(stageNum, currSO);
        // Get the A Control database entry of the new start order
        AControl aControl2 = aControlDatabaseHelper.getAControl(stageNum, newSO);
        // Change the current entries start order and update the database
        currAControl.setStartOrder(newSO);
        aControlDatabaseHelper.updateAControl(currAControl);
        // Get the car number of the current entry
        carNum = currAControl.getCarNum();
        // Get the stage database entry for the current entry
        stage = stageDatabaseHelper.getStage(currAControl.getStage2ID());
        // Change the start order in the stage database and update it
        stage.setStartOrder(newSO);
        stageDatabaseHelper.updateStage(stage);
        // Call the method to add this car to the start database
        addToStart(carNum);

        // Set the start order of the other entry to the current start order and
        // update the a control database
        aControl2.setStartOrder(currSO);
        aControlDatabaseHelper.updateAControl(aControl2);
        // Get the car number of the other entry
        carNum = aControl2.getCarNum();
        // Get the stage database entry for the other entry
        stage = stageDatabaseHelper.getStage(aControl2.getStage2ID());
        // Change the start order in the stage database and update it
        stage.setStartOrder(currSO);
        stageDatabaseHelper.updateStage(stage);
        // Call the method to add this car to the start database
        addToStart(carNum);
    }

    private void ShowReturnTCPopup() {
        // Pop-up to confirm if the user wants to return the data to the competitor
        // and add them to the start database
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

        // Get the database entry at the current start order and get the car number
        aControl = aControlDatabaseHelper.getAControl(stageNum, startOrder);
        carNum = aControl.getCarNum();
        String currCarNum = String.valueOf(carNum);
        TextView text = layout.findViewById(R.id.ReturnTC);
        // Change the text to include the car number
        text.setText("Return Time Card to Car " + currCarNum + "?");

        Button yesReturn = layout.findViewById(R.id.YesReturnButton);
        // Set on click listener for yes button
        yesReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If it is stage 1, the first timecard is not visible,
                // so doesn't need to be saved
                if (stageNum != 1) {
                    // Get the first stage entry for the current a control entry
                    stage = stageDatabaseHelper.getStage(aControl.getStage1ID());
                    stage.setActualTime(actualTimeH1.getText().toString(), actualTimeM1.getText().toString());
                    // Save the time that was put into the actual time boxes as a string
                    // and update the entry in the stage database
                    stageDatabaseHelper.updateStage(stage);
                }
                // Get the second stage entry for the current a control entry
                stage = stageDatabaseHelper.getStage(aControl.getStage2ID());
                stage.setProvStart(provStartH2.getText().toString(), provStartM2.getText().toString());
                // Save the time that was put into the provisional time boxes as a string
                String inputSO = startOrder2.getText().toString();
                stage.setStartOrder(Integer.valueOf(inputSO));
                // Save the start order that was entered
                // and update the entry in the stage database
                stageDatabaseHelper.updateStage(stage);
                // Dismiss the pop-up then add the car to the start database
                returnTCPopup.dismiss();
                addToStart(carNum);
            }
        });

        Button noReturn = layout.findViewById(R.id.NoReturnButton);
        // Add an on click listener for the no button, which just dismisses the pop-up
        noReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnTCPopup.dismiss();
            }
        });
    }

    private void addToStart(int currCarNum) {
        // Method to add a car to the start database
        // Check if the car has already been added
        if (!startDatabaseHelper.checkStart(stageNum, currCarNum)) {
            Start newStart = new Start();
            // If not, get the start order for that car
            int currSO = aControlDatabaseHelper.getStartOrder(stageNum, currCarNum);
            // Set the start order, stage number and car number
            newStart.setStartOrder(currSO);
            newStart.setStage(stageNum);
            newStart.setCarNum(currCarNum);
            // Get the id of the stage database entry and save that
            competitor = compDatabaseHelper.getCompetitorByCarNum(currCarNum);
            newStart.setStageID(competitor.getStageId(stageNum));
            // Add newStart to the start database
            startDatabaseHelper.addStart(newStart);
        } else {
            // If the car has already been added, get the entry
            start = startDatabaseHelper.getStartByCarNum(stageNum, currCarNum);
            // Get the start order for that car and update it
            int currSO = aControlDatabaseHelper.getStartOrder(stageNum, currCarNum);
            start.setStartOrder(currSO);
            // Update the start entry
            startDatabaseHelper.updateStart(start);
        }
    }

    public void previousTC() {
        // Method to move to the previous start order
        // If the current start order is 1, it is not possible to go to the
        // previous one, so do nothing
        if (startOrder > 1) {
            // Otherwise change the start order and fill the cards again
            startOrder = startOrder - 1;
            fillInCards();
        }
    }

    public void nextTC() {
        // Method to move to the next start order
        // If the current start order is the same as the most recent entry,
        // it is not possible to go to the next one, so do nothing
        int currStartOrder = aControlDatabaseHelper.getCurrStartOrder(stageNum);
        if ((startOrder + 1) <= currStartOrder) {
            // Otherwise change the start order and fill the cards again
            startOrder = startOrder + 1;
            fillInCards();
        }
    }
}