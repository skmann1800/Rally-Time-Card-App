package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import com.example.rallytimingapp.model.Finish;
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.sql.FinishDatabaseHelper;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class FinishActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = FinishActivity.this;

    private Button backButton;
    private Button returnTCButton;
    private Button prevButton;
    private Button nextButton;

    private TextView stageNumTV;
    private TextView stageLabel1;
    private TextView stageLabel2;
    private TextView stageDistance;
    private TextView stageNumber;
    private TextView blueTC1;
    private TextView blueTC2;
    private TextView blueKM;
    private TextView nextTC;
    private TextView yellowTC;
    private TextView targetTimeHours;
    private TextView targetTimeMinutes;

    private TextView carNumTV;
    private TextView finishOrderTV;

    private TextView startOrder;
    private TextView provStartHours;
    private TextView provStartMinutes;
    private TextView actualStartHours;
    private TextView actualStartMinutes;
    private TextView finishTimeHours;
    private TextView finishTimeMinutes;
    private TextView finishTimeSeconds;
    private TextView finishTimeMilliseconds;
    private TextView stageTimeMinutes;
    private TextView stageTimeSeconds;
    private TextView stageTimeMilliseconds;
    private TextView actualTimeHours;
    private TextView actualTimeMinutes;
    private TextView dueTimeHours;
    private TextView dueTimeMinutes;

    private PopupWindow returnTCPopup;

    private ScrollView scrollView;

    private Finish finish;
    private Stage stage;
    private FinishDatabaseHelper finishDatabaseHelper;
    private StageDatabaseHelper stageDatabaseHelper;

    private int stageNum;
    private int finishOrder;
    private int carNum;

    private String[] stageLabels;
    private String[] stageDistances;
    private String[] stageKMs;
    private String[] TCNumbers;
    private String[] stageNumbers;
    private String[] stageTargetTimeHours;
    private String[] stageTargetTimeMinutes;
    private String[] stageFinishLabels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        initViews();
        initObjects();
        initListeners();
        initResources();

        // Retrieve stage number from intent
        stageNum = getIntent().getIntExtra("STAGE", 0);
        // Set default finish order to be 1
        finishOrder = finishDatabaseHelper.getCurrFinishOrder(stageNum) + 1;
        finishOrderTV.setText(finishOrder);
        carNumTV.requestFocus();
        carNumTV.setCursorVisible(true);

        // Based on the stage number, fill in the labels as follows
        stageNumTV.setText(stageFinishLabels[stageNum - 1]);
        stageLabel1.setText(stageLabels[stageNum - 1]);
        stageLabel2.setText(stageLabels[stageNum - 1]);
        stageDistance.setText(stageDistances[stageNum - 1]);
        blueKM.setText(stageKMs[stageNum - 1]);
        stageNumber.setText(stageNumbers[stageNum - 1]);
        blueTC1.setText(TCNumbers[stageNum - 1]);
        blueTC2.setText(TCNumbers[stageNum]);
        nextTC.setText(TCNumbers[stageNum]);
        yellowTC.setText(TCNumbers[stageNum]);
        targetTimeHours.setText(stageTargetTimeHours[stageNum - 1]);
        targetTimeMinutes.setText(stageTargetTimeMinutes[stageNum - 1]);
    }

    // Method to initialise objects
    private void initObjects() {
        stageDatabaseHelper = new StageDatabaseHelper(activity);
        finishDatabaseHelper = new FinishDatabaseHelper(activity);
        stage = new Stage();
        finish = new Finish();
    }

    // Method to initialise views
    private void initViews() {
        scrollView = findViewById(R.id.FinishScrollView);

        carNumTV = findViewById(R.id.FinishCarNum);
        carNumTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        finishOrderTV = findViewById(R.id.FinishOrder);

        returnTCButton = findViewById(R.id.FinishReturnButton);
        backButton = findViewById(R.id.FTCBackButton);
        prevButton = findViewById(R.id.FinishPrevButton);
        nextButton = findViewById(R.id.FinishNextButton);

        stageNumTV = findViewById(R.id.FinishStageNum);
        stageLabel1 = findViewById(R.id.FTCStageLabel1);
        stageLabel2 = findViewById(R.id.FTCStageLabel2);
        stageDistance = findViewById(R.id.FTCDistance);
        stageNumber = findViewById(R.id.FTCStageNumber);
        blueTC1 = findViewById(R.id.FTCTC1);
        blueTC2 = findViewById(R.id.FTCTC2);
        blueKM = findViewById(R.id.FTCKM);
        nextTC = findViewById(R.id.FTCNextTC);
        yellowTC = findViewById(R.id.FTCYellowTC);
        targetTimeHours = findViewById(R.id.FTCTargetTimeHours);
        targetTimeMinutes = findViewById(R.id.FTCTargetTimeMinutes);

        startOrder = findViewById(R.id.FTCOval);
        provStartHours = findViewById(R.id.FTCProvStartHours);
        provStartMinutes = findViewById(R.id.FTCProvStartMinutes);
        actualStartHours = findViewById(R.id.FTCActualStartHours);
        actualStartMinutes = findViewById(R.id.FTCActualStartMinutes);
        finishTimeHours = findViewById(R.id.FTCFinishTimeHours);
        finishTimeMinutes = findViewById(R.id.FTCFinishTimeMinutes);
        finishTimeSeconds = findViewById(R.id.FTCFinishTimeSeconds);
        finishTimeMilliseconds = findViewById(R.id.FTCFinishTimeMilliseconds);
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to move onto the next text box, once this one is full
        finishTimeHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The max input length is 2 digits
                if(finishTimeHours.getText().toString().length()==2)
                {
                    // This box contains the hours of a time, so this input cannot be
                    // larger than 24
                    if (Integer.valueOf(finishTimeHours.getText().toString()) > 24) {
                        // If input is larger than 24, reset the text and display an error message.
                        finishTimeHours.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // If the input is valid, move to the next text box
                        finishTimeHours.clearFocus();
                        finishTimeMinutes.requestFocus();
                        finishTimeMinutes.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to autofill the next box
        finishTimeMinutes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The max input length is 2 digits
                if(finishTimeMinutes.getText().toString().length()==2)
                {
                    // This box contains the minutes of a time, so this input cannot be
                    // larger than 59
                    int fTimeM = Integer.valueOf(finishTimeMinutes.getText().toString());
                    if (fTimeM > 59) {
                        // If input is larger than 59, reset the text and display an error message.
                        finishTimeMinutes.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise, move to the next box.
                        finishTimeMinutes.clearFocus();
                        finishTimeSeconds.requestFocus();
                        finishTimeSeconds.setCursorVisible(true);
                        // Autofill the minutes box of the stage time box by deducting the
                        // actual start time from the finish time
                        int aStartM = Integer.valueOf(actualStartMinutes.getText().toString());
                        int sTimeM = 0;
                        if (fTimeM > aStartM) {
                            sTimeM = fTimeM - aStartM;
                        } else if (fTimeM < aStartM) {
                            // If the time has gone from one hour to the next,
                            // eg start at 12:59, finish at 13:03, then calculate the difference
                            sTimeM = (60 - aStartM) + fTimeM;
                        }
                        stageTimeMinutes.setText(String.valueOf(sTimeM));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to move onto the next text box, once this one is full
        finishTimeSeconds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The maximum input length is 2
                if(finishTimeSeconds.getText().toString().length()==2)
                {
                    // This box contains the seconds of a time, so this input cannot be
                    // larger than 59
                    if (Integer.valueOf(finishTimeSeconds.getText().toString()) > 59) {
                        // If input is larger than 59, reset the text and show error message
                        finishTimeSeconds.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // If input is valid, copy the text into the seconds box of the finish time,
                        // and move to the next box
                        stageTimeSeconds.setText(finishTimeSeconds.getText());
                        finishTimeSeconds.clearFocus();
                        finishTimeMilliseconds.requestFocus();
                        finishTimeMilliseconds.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to move onto the next text box, once this one is full
        finishTimeMilliseconds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This box contains the milliseconds of a time, so there is no numerical
                // limit for this one, but there can only be 2 digits max
                if(finishTimeMilliseconds.getText().toString().length()==2)
                {
                    // Once the box is full, copy the text to the milliseconds
                    // box of the stage time and move to the next box
                    stageTimeMilliseconds.setText(finishTimeMilliseconds.getText());
                    finishTimeMilliseconds.clearFocus();
                    stageTimeMinutes.requestFocus();
                    stageTimeMinutes.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        stageTimeMinutes = findViewById(R.id.FTCTimeTakenMinutes);
        stageTimeSeconds = findViewById(R.id.FTCTimeTakenSeconds);
        stageTimeMilliseconds = findViewById(R.id.FTCTimeTakenMilliseconds);
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to autofill the next box
        stageTimeMinutes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Max input length is 2
                if(stageTimeMinutes.getText().toString().length()==2)
                {
                    // This is a minutes box, so input cannot be larger than 59
                    if (Integer.valueOf(stageTimeMinutes.getText().toString()) > 59) {
                        stageTimeMinutes.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        stageTimeMinutes.clearFocus();
                        stageTimeSeconds.requestFocus();
                        stageTimeSeconds.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to autofill the next box
        stageTimeSeconds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Max input length is 2
                if(stageTimeSeconds.getText().toString().length()==2)
                {
                    // This is a seconds box, so input cannot be larger than 59
                    if (Integer.valueOf(stageTimeSeconds.getText().toString()) > 59) {
                        stageTimeSeconds.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        stageTimeSeconds.clearFocus();
                        stageTimeMilliseconds.requestFocus();
                        stageTimeMilliseconds.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to autofill the next box
        stageTimeMilliseconds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This is a milliseconds box, so input can be any number,
                // but has a max length of 2 digits
                if(stageTimeMilliseconds.getText().toString().length()==2)
                {
                    stageTimeMilliseconds.clearFocus();
                    finishTimeHours.requestFocus();
                    finishTimeHours.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        actualTimeHours = findViewById(R.id.FTCActualTimeHours);
        actualTimeMinutes = findViewById(R.id.FTCActualTimeMinutes);
        dueTimeHours = findViewById(R.id.FTCDueTimeHours);
        dueTimeMinutes = findViewById(R.id.FTCDueTimeMinutes);
    }

    // Method to initialise the listeners for the buttons
    private void initListeners() {
        backButton.setOnClickListener(this);
        returnTCButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    private void initResources() {
        Resources res = getResources();
        stageLabels = res.getStringArray(R.array.stage_labels);
        stageDistances = res.getStringArray(R.array.stage_distances);
        stageKMs = res.getStringArray(R.array.stage_kms);
        TCNumbers = res.getStringArray(R.array.TC_numbers);
        stageNumbers = res.getStringArray(R.array.stage_numbers);
        stageTargetTimeHours = res.getStringArray(R.array.target_time_hours);
        stageTargetTimeMinutes = res.getStringArray(R.array.target_time_minutes);
        stageFinishLabels = res.getStringArray(R.array.stage_finish);
    }

    // On Click Method for the buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.FTCBackButton:
                // The back button returns to the choose stage page
                Intent intent = new Intent(this, ChooseStageActivity.class);
                intent.putExtra("ROLE", "Finish");
                startActivity(intent);
                break;
            // The return button shows a pop-up
            case R.id.FinishReturnButton:
                ShowReturnTCPopup();
                break;
            // The left arrow button goes to the previous finish order
            case R.id.FinishPrevButton:
                previousTC();
                break;
            // The right arrow button goes to the next finish order
            case R.id.FinishNextButton:
                nextTC();
                break;
        }
    }

    // Method to fill in all the fields of the timecard, from various databases
    private void fillInCards() {
        carNum = finishDatabaseHelper.getCarNum(stageNum, finishOrder);
            carNumTV.setText(String.valueOf(carNum));
            finish = finishDatabaseHelper.getFinish(finishDatabaseHelper.getFinishID(stageNum, carNum));
            finishOrderTV.setText(String.valueOf(finishOrder));
            stage = stageDatabaseHelper.getStage(finish.getStageID());
            startOrder.setText(String.valueOf(stage.getStartOrder()));
            provStartHours.setText(stage.getProvStartH());
            provStartMinutes.setText(stage.getProvStartM());
            actualStartHours.setText(stage.getActualStartH());
            actualStartMinutes.setText(stage.getActualStartM());
            finishTimeHours.setText(stage.getFinishTimeH());
            finishTimeMinutes.setText(stage.getFinishTimeM());
            finishTimeSeconds.setText(stage.getFinishTimeS());
            finishTimeMilliseconds.setText(stage.getFinishTimeMS());
            stageTimeMinutes.setText(stage.getStageTimeM());
            stageTimeSeconds.setText(stage.getStageTimeS());
            stageTimeMilliseconds.setText(stage.getStageTimeMS());
            actualTimeHours.setText(stage.getActualTimeH());
            actualTimeMinutes.setText(stage.getActualTimeM());
            dueTimeHours.setText(stage.getDueTimeH());
            dueTimeMinutes.setText(stage.getDueTimeM());
    }

    // Method to show a pop-up
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

        finish = finishDatabaseHelper.getFinish(stageNum, finishOrder);
        carNum = finish.getCarNum();
        String currCarNum = String.valueOf(carNum);
        TextView text = layout.findViewById(R.id.ReturnTC);
        // Customise text on Pop-up
        text.setText("Return Time Card to Car " + currCarNum + "?");

        Button yesReturn = layout.findViewById(R.id.YesReturnButton);
        yesReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save inputs to the database
                stage = stageDatabaseHelper.getStage(finish.getStageID());
                String inputFTH = finishTimeHours.getText().toString();
                String inputFTM = finishTimeMinutes.getText().toString();
                String inputFTS = finishTimeSeconds.getText().toString();
                String inputFTMS = finishTimeMilliseconds.getText().toString();
                stage.setFinishTime(inputFTH, inputFTM, inputFTS, inputFTMS);
                String inputSTM = stageTimeMinutes.getText().toString();
                String inputSTS = stageTimeSeconds.getText().toString();
                String inputSTMS = stageTimeMilliseconds.getText().toString();
                stage.setStageTime(inputSTM, inputSTS, inputSTMS);
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

    // Method to change the display to the previous finish order, if it exists
    public void previousTC() {
        if (finishOrder > 1) {
            finishOrder = finishOrder - 1;
            fillInCards();
        }
    }

    // Method to change the display to the next finish order, if it exists
    public void nextTC() {
        int currFinishOrder = finishDatabaseHelper.getCurrFinishOrder(stageNum);
        if ((finishOrder + 1) <= currFinishOrder) {
            finishOrder = finishOrder + 1;
            fillInCards();
        }
    }
}