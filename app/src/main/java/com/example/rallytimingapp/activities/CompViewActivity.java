package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.model.Finish;
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.sql.AControlDatabaseHelper;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.sql.FinishDatabaseHelper;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

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
    private Button saveDueTime1;
    private Button checkIn2;
    private Button reqTime2;
    private Button saveDueTime2;
    private Button checkIn3;
    private Button reqTime3;
    private Button saveDueTime3;
    private Button checkIn4;
    private Button reqTime4;
    private Button saveDueTime4;

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

        // Get the competitor ID which was passed through the intent from the login
        compID = getIntent().getIntExtra("COMP_ID", 0);
        // Get the competitor object associated with that ID
        competitor = compDatabaseHelper.getCompetitorByID(compID);
        // Get the car number and change the display
        carNum = competitor.getCarNum();
        carNumTV.setText(String.valueOf(carNum));
        // Fill in the timecards
        fillInCards();
    }

    // Method to fill in the timecards
    private void fillInCards() {
        // Get the first stage object for that competitor
        stage = stageDatabaseHelper.getStage(competitor.getStage1Id());
        // If the start order is currently set to 0, there are no details to show yet so
        // set the text to nothing
        if (stage.getStartOrder() == 0) {
            startOrder1.setText("");
        } else {
            startOrder1.setText(String.valueOf(stage.getStartOrder()));
        }
        // Fill in the remaining fields for stage 1
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

        // Get the second stage object for that competitor
        stage = stageDatabaseHelper.getStage(competitor.getStage2Id());
        // If the start order is currently set to 0, there are no details to show yet so
        // set the text to nothing
        if (stage.getStartOrder() == 0) {
            startOrder2.setText("");
        } else {
            startOrder2.setText(String.valueOf(stage.getStartOrder()));
        }
        // Fill in the remaining fields for stage 2
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

        // Get the third stage object for that competitor
        stage = stageDatabaseHelper.getStage(competitor.getStage3Id());
        // If the start order is currently set to 0, there are no details to show yet so
        // set the text to nothing
        if (stage.getStartOrder() == 0) {
            startOrder3.setText("");
        } else {
            startOrder3.setText(String.valueOf(stage.getStartOrder()));
        }
        // Fill in the remaining fields for stage 3
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

        // Get the fourth stage object for that competitor
        stage = stageDatabaseHelper.getStage(competitor.getStage4Id());
        // If the start order is currently set to 0, there are no details to show yet so
        // set the text to nothing
        if (stage.getStartOrder() == 0) {
            startOrder4.setText("");
        } else {
            startOrder4.setText(String.valueOf(stage.getStartOrder()));
        }
        // Fill in the remaining fields for stage 4
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

    // Method to initialise the objects
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

    // Method to initialise the views
    private void initViews() {
        scrollView = findViewById(R.id.CompViewScrollView);
        carNumTV = findViewById(R.id.CarNum);

        startOrder1 = findViewById(R.id.S1Oval);
        provStartH1 = findViewById(R.id.S1ProvStartHours);
        provStartM1 = findViewById(R.id.S1ProvStartMinutes);
        actualStartH1 = findViewById(R.id.S1ActualStartHours);
        actualStartM1 = findViewById(R.id.S1ActualStartMinutes);
        finishTimeH1 = findViewById(R.id.S1FinishTimeHours);
        finishTimeM1 = findViewById(R.id.S1FinishTimeMinutes);
        finishTimeS1 = findViewById(R.id.S1FinishTimeSeconds);
        finishTimeMS1 = findViewById(R.id.S1FinishTimeMilliseconds);
        stageTimeM1 = findViewById(R.id.S1TimeTakenMinutes);
        stageTimeS1 = findViewById(R.id.S1TimeTakenSeconds);
        stageTimeMS1 = findViewById(R.id.S1TimeTakenMilliseconds);
        actualTimeH1 = findViewById(R.id.S1ActualTimeHours);
        actualTimeM1 = findViewById(R.id.S1ActualTimeMinutes);
        dueTimeH1 = findViewById(R.id.S1DueTimeHours);
        dueTimeM1 = findViewById(R.id.S1DueTimeMinutes);
        // Add text changed listeners
        dueTimeH1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The input has a max length of 2
                if(dueTimeH1.getText().toString().length()==2)
                {
                    // This box is for hours, so the max input is 24
                    if (Integer.valueOf(dueTimeH1.getText().toString()) > 24) {
                        // If the input is larger than 24, clear it and show an error message
                        dueTimeH1.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise move to the next text box
                        dueTimeH1.clearFocus();
                        dueTimeM1.requestFocus();
                        dueTimeM1.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dueTimeM1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The input has a max length of 2
                if(dueTimeM1.getText().toString().length()==2)
                {
                    // This box is for minutes, so the max input is 59
                    if (Integer.valueOf(dueTimeM1.getText().toString()) > 59) {
                        // If the input is larger than 59, clear it and show an error message
                        dueTimeM1.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise save the inputs to the database
                        stage = stageDatabaseHelper.getStage(carNum, 1);
                        String inputDTH = dueTimeH1.getText().toString();
                        String inputDTM = dueTimeM1.getText().toString();
                        stage.setDueTime(inputDTH, inputDTM);
                        stageDatabaseHelper.updateStage(stage);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        startOrder2 = findViewById(R.id.S2Oval);
        provStartH2 = findViewById(R.id.S2ProvStartHours);
        provStartM2 = findViewById(R.id.S2ProvStartMinutes);
        actualStartH2 = findViewById(R.id.S2ActualStartHours);
        actualStartM2 = findViewById(R.id.S2ActualStartMinutes);
        finishTimeH2 = findViewById(R.id.S2FinishTimeHours);
        finishTimeM2 = findViewById(R.id.S2FinishTimeMinutes);
        finishTimeS2 = findViewById(R.id.S2FinishTimeSeconds);
        finishTimeMS2 = findViewById(R.id.S2FinishTimeMilliseconds);
        stageTimeM2 = findViewById(R.id.S2TimeTakenMinutes);
        stageTimeS2 = findViewById(R.id.S2TimeTakenSeconds);
        stageTimeMS2 = findViewById(R.id.S2TimeTakenMilliseconds);
        actualTimeH2 = findViewById(R.id.S2ActualTimeHours);
        actualTimeM2 = findViewById(R.id.S2ActualTimeMinutes);
        dueTimeH2 = findViewById(R.id.S2DueTimeHours);
        dueTimeM2 = findViewById(R.id.S2DueTimeMinutes);
        // Add text changed listeners
        dueTimeH2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The input has a max length of 2
                if(dueTimeH2.getText().toString().length()==2)
                {
                    // This box is for hours, so the max input is 24
                    if (Integer.valueOf(dueTimeH2.getText().toString()) > 24) {
                        // If the input is larger than 24, clear it and show an error message
                        dueTimeH2.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise move to the next text box
                        dueTimeH2.clearFocus();
                        dueTimeM2.requestFocus();
                        dueTimeM2.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dueTimeM2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The input has a max length of 2
                if(dueTimeM2.getText().toString().length()==2)
                {
                    // This box is for minutes, so the max input is 59
                    if (Integer.valueOf(dueTimeM2.getText().toString()) > 59) {
                        // If the input is larger than 59, clear it and show an error message
                        dueTimeM2.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise save the inputs to the database
                        stage = stageDatabaseHelper.getStage(carNum, 2);
                        String inputDTH = dueTimeH2.getText().toString();
                        String inputDTM = dueTimeM2.getText().toString();
                        stage.setDueTime(inputDTH, inputDTM);
                        stageDatabaseHelper.updateStage(stage);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        startOrder3 = findViewById(R.id.S3Oval);
        provStartH3 = findViewById(R.id.S3ProvStartHours);
        provStartM3 = findViewById(R.id.S3ProvStartMinutes);
        actualStartH3 = findViewById(R.id.S3ActualStartHours);
        actualStartM3 = findViewById(R.id.S3ActualStartMinutes);
        finishTimeH3 = findViewById(R.id.S3FinishTimeHours);
        finishTimeM3 = findViewById(R.id.S3FinishTimeMinutes);
        finishTimeS3 = findViewById(R.id.S3FinishTimeSeconds);
        finishTimeMS3 = findViewById(R.id.S3FinishTimeMilliseconds);
        stageTimeM3 = findViewById(R.id.S3TimeTakenMinutes);
        stageTimeS3 = findViewById(R.id.S3TimeTakenSeconds);
        stageTimeMS3 = findViewById(R.id.S3TimeTakenMilliseconds);
        actualTimeH3 = findViewById(R.id.S3ActualTimeHours);
        actualTimeM3 = findViewById(R.id.S3ActualTimeMinutes);
        dueTimeH3 = findViewById(R.id.S3DueTimeHours);
        dueTimeM3 = findViewById(R.id.S3DueTimeMinutes);
        // Add text changed listeners
        dueTimeH3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The input has a max length of 2
                if(dueTimeH3.getText().toString().length()==2)
                {
                    // This box is for hours, so the max input is 24
                    if (Integer.valueOf(dueTimeH3.getText().toString()) > 24) {
                        // If the input is larger than 24, clear it and show an error message
                        dueTimeH3.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise move to the next text box
                        dueTimeH3.clearFocus();
                        dueTimeM3.requestFocus();
                        dueTimeM3.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dueTimeM3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The input has a max length of 2
                if(dueTimeM3.getText().toString().length()==2)
                {
                    // This box is for minutes, so the max input is 59
                    if (Integer.valueOf(dueTimeM3.getText().toString()) > 59) {
                        // If the input is larger than 59, clear it and show an error message
                        dueTimeM3.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise save the inputs to the database
                        stage = stageDatabaseHelper.getStage(carNum, 3);
                        String inputDTH = dueTimeH3.getText().toString();
                        String inputDTM = dueTimeM3.getText().toString();
                        stage.setDueTime(inputDTH, inputDTM);
                        stageDatabaseHelper.updateStage(stage);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        startOrder4 = findViewById(R.id.S4Oval);
        provStartH4 = findViewById(R.id.S4ProvStartHours);
        provStartM4 = findViewById(R.id.S4ProvStartMinutes);
        actualStartH4 = findViewById(R.id.S4ActualStartHours);
        actualStartM4 = findViewById(R.id.S4ActualStartMinutes);
        finishTimeH4 = findViewById(R.id.S4FinishTimeHours);
        finishTimeM4 = findViewById(R.id.S4FinishTimeMinutes);
        finishTimeS4 = findViewById(R.id.S4FinishTimeSeconds);
        finishTimeMS4 = findViewById(R.id.S4FinishTimeMilliseconds);
        stageTimeM4 = findViewById(R.id.S4TimeTakenMinutes);
        stageTimeS4 = findViewById(R.id.S4TimeTakenSeconds);
        stageTimeMS4 = findViewById(R.id.S4TimeTakenMilliseconds);
        actualTimeH4 = findViewById(R.id.S4ActualTimeHours);
        actualTimeM4 = findViewById(R.id.S4ActualTimeMinutes);
        dueTimeH4 = findViewById(R.id.S4DueTimeHours);
        dueTimeM4 = findViewById(R.id.S4DueTimeMinutes);
        // Add text changed listeners
        dueTimeH4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The input has a max length of 2
                if(dueTimeH4.getText().toString().length()==2)
                {
                    // This box is for hours, so the max input is 24
                    if (Integer.valueOf(dueTimeH4.getText().toString()) > 24) {
                        // If the input is larger than 24, clear it and show an error message
                        dueTimeH4.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise move to the next text box
                        dueTimeH4.clearFocus();
                        dueTimeM4.requestFocus();
                        dueTimeM4.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dueTimeM4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The input has a max length of 2
                if(dueTimeM4.getText().toString().length()==2)
                {
                    // This box is for minutes, so the max input is 59
                    if (Integer.valueOf(dueTimeM4.getText().toString()) > 59) {
                        // If the input is larger than 59, clear it and show an error message
                        dueTimeM4.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise save the inputs to the database
                        stage = stageDatabaseHelper.getStage(carNum, 4);
                        String inputDTH = dueTimeH4.getText().toString();
                        String inputDTM = dueTimeM4.getText().toString();
                        stage.setDueTime(inputDTH, inputDTM);
                        stageDatabaseHelper.updateStage(stage);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checkIn1 = findViewById(R.id.S1CheckIn);
        reqTime1 = findViewById(R.id.S1ReqTime);
        saveDueTime1 = findViewById(R.id.S1SaveDueTime);
        checkIn2 = findViewById(R.id.S2CheckIn);
        reqTime2 = findViewById(R.id.S2ReqTime);
        saveDueTime2 = findViewById(R.id.S2SaveDueTime);
        checkIn3 = findViewById(R.id.S3CheckIn);
        reqTime3 = findViewById(R.id.S3ReqTime);
        saveDueTime3 = findViewById(R.id.S3SaveDueTime);
        checkIn4 = findViewById(R.id.S4CheckIn);
        reqTime4 = findViewById(R.id.S4ReqTime);
        saveDueTime4 = findViewById(R.id.S4SaveDueTime);
    }

    // Method to initialise listeners for the buttons
    private void initListeners() {
        checkIn1.setOnClickListener(this);
        reqTime1.setOnClickListener(this);
        saveDueTime1.setOnClickListener(this);
        checkIn2.setOnClickListener(this);
        reqTime2.setOnClickListener(this);
        saveDueTime2.setOnClickListener(this);
        checkIn3.setOnClickListener(this);
        reqTime3.setOnClickListener(this);
        saveDueTime3.setOnClickListener(this);
        checkIn4.setOnClickListener(this);
        reqTime4.setOnClickListener(this);
        saveDueTime4.setOnClickListener(this);
    }

    // Method to show the confirm check in pop-up
    private void ShowCheckInPopup(int stageNum) {
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

        // Customise the message depending on the stage number
        TextView text = layout.findViewById(R.id.ConfirmCheckIn);
        text.setText("Check In to Stage " + stageNum + "?");

        // Set on click listener for yes button
        Button yesCheckIn = layout.findViewById(R.id.YesCheckInButton);
        yesCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add competitor to A Control database
                addToAControl(stageNum);
                // Dismiss pop-up
                checkInPopup.dismiss();
            }
        });

        // Set on click listener for yes button
        Button noCheckIn = layout.findViewById(R.id.NoCheckInButton);
        noCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss pop-up
                checkInPopup.dismiss();
            }
        });
    }

    // Method to add an entry to the A Control database
    private void addToAControl(int stageNum) {
        // Check if there is already an entry in the database with the
        // given stage and car number
        if (!aControlDatabaseHelper.checkAControl(stageNum, carNum)) {
            // If not get the start order of the most recently added entry
            int lastSO = aControlDatabaseHelper.getCurrStartOrder(stageNum);
            // Set the start order to that plus one
            aControl.setStartOrder(lastSO+1);
            aControl.setStage(stageNum);
            aControl.setCarNum(carNum);
            // Get the stage IDs from the competitor object
            aControl.setStage1ID(competitor.getStageId(stageNum-1));
            aControl.setStage2ID(competitor.getStageId(stageNum));
            // Add the entry to the database
            aControlDatabaseHelper.addAControl(aControl);
        }
    }

    // Method to show the request time pop-up
    private void ShowReqTimePopup(int stageNum) {
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

        // Customise the message depending on the stage number
        TextView text = layout.findViewById(R.id.ConfirmReqTime);
        text.setText("Request Time for Stage " + stageNum + "?");

        // Set an on click listener for the yes button
        Button yesReqTime = layout.findViewById(R.id.YesReqTimeButton);
        yesReqTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add competitor to Finish database
                addToFinish(stageNum);
                // Dismiss pop-up
                reqTimePopup.dismiss();
            }
        });

        // Set an on click listener for the no button
        Button noReqTime = layout.findViewById(R.id.NoReqTimeButton);
        noReqTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss pop-up
                reqTimePopup.dismiss();
            }
        });
    }

    // Method to add an entry to the Finish database
    private void addToFinish(int stageNum) {
        // Check if there is already an entry in the database with the
        // given stage and car number
        if (!finishDatabaseHelper.checkFinish(stageNum, carNum)) {
            // If not get the finish order of the most recently added entry
            int lastFO = finishDatabaseHelper.getCurrFinishOrder(stageNum);
            // Set the finish order to that plus one
            finish.setFinishOrder(lastFO+1);
            finish.setStage(stageNum);
            finish.setCarNum(carNum);
            // Get the stage ID from the competitor object
            finish.setStageID(competitor.getStageId(stageNum));
            // Add the entry to the database
            finishDatabaseHelper.addFinish(finish);
        }
    }

    // On Click method for the buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.S1CheckIn:
                // Show the check in pop-up for stage 1
                ShowCheckInPopup(1);
                break;
            case R.id.S1ReqTime:
                // Show the request time pop-up for stage 1
                ShowReqTimePopup(1);
                break;
            case R.id.S1SaveDueTime:
                // Save the due time entered for stage 1 and show a confirmation message
                stage = stageDatabaseHelper.getStage(carNum, 1);
                String inputDTH1 = dueTimeH1.getText().toString();
                String inputDTM1 = dueTimeM1.getText().toString();
                stage.setDueTime(inputDTH1, inputDTM1);
                stageDatabaseHelper.updateStage(stage);
                Snackbar.make(scrollView, "Due Time Saved", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.S2CheckIn:
                // Show the check in pop-up for stage 2
                ShowCheckInPopup(2);
                break;
            case R.id.S2ReqTime:
                // Show the request time pop-up for stage 2
                ShowReqTimePopup(2);
                break;
            case R.id.S2SaveDueTime:
                // Save the due time entered for stage 2 and show a confirmation message
                stage = stageDatabaseHelper.getStage(carNum, 2);
                String inputDTH2 = dueTimeH2.getText().toString();
                String inputDTM2 = dueTimeM2.getText().toString();
                stage.setDueTime(inputDTH2, inputDTM2);
                stageDatabaseHelper.updateStage(stage);
                Snackbar.make(scrollView, "Due Time Saved", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.S3CheckIn:
                // Show the check in pop-up for stage 3
                ShowCheckInPopup(3);
                break;
            case R.id.S3ReqTime:
                // Show the request time pop-up for stage 3
                ShowReqTimePopup(3);
                break;
            case R.id.S3SaveDueTime:
                // Save the due time entered for stage 3 and show a confirmation message
                stage = stageDatabaseHelper.getStage(carNum, 3);
                String inputDTH3 = dueTimeH3.getText().toString();
                String inputDTM3 = dueTimeM3.getText().toString();
                stage.setDueTime(inputDTH3, inputDTM3);
                stageDatabaseHelper.updateStage(stage);
                Snackbar.make(scrollView, "Due Time Saved", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.S4CheckIn:
                // Show the check in pop-up for stage 4
                ShowCheckInPopup(4);
                break;
            case R.id.S4ReqTime:
                // Show the request time pop-up for stage 4
                ShowReqTimePopup(4);
                break;
            case R.id.S4SaveDueTime:
                // Save the due time entered for stage 4 and show a confirmation message
                stage = stageDatabaseHelper.getStage(carNum, 4);
                String inputDTH4 = dueTimeH4.getText().toString();
                String inputDTM4 = dueTimeM4.getText().toString();
                stage.setDueTime(inputDTH4, inputDTM4);
                stageDatabaseHelper.updateStage(stage);
                Snackbar.make(scrollView, "Due Time Saved", Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}