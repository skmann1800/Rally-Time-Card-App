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
    private TextView stageDist;
    private TextView SS;
    private TextView blueTC1;
    private TextView blueTC2;
    private TextView stageKM;
    private TextView finishTC;
    private TextView yellowTC;
    private TextView TTH;
    private TextView TTM;

    private TextView carNumTV;
    private TextView finishOrderTV;

    private TextView startOrder;
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

    private ScrollView scrollView;

    private Finish finish;
    private Stage stage;
    private FinishDatabaseHelper finishDatabaseHelper;
    private StageDatabaseHelper stageDatabaseHelper;

    private int stageNum;
    private int finishOrder;
    private int carNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        initViews();
        initObjects();
        initListeners();

        // Retrieve stage number from intent
        stageNum = getIntent().getIntExtra("STAGE", 0);
        // Set default finish order to be 1
        finishOrder = 1;
        // Fill in the timecards
        fillInCards();
        // Based on the stage number, fill in the labels as follows
        switch (stageNum) {
            case 1:
                stageNumTV.setText(R.string.s1finish);
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
                stageNumTV.setText(R.string.s2finish);
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
                stageNumTV.setText(R.string.s3finish);
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
                stageNumTV.setText(R.string.s4finish);
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
        finishOrderTV = findViewById(R.id.FinishOrder);

        returnTCButton = findViewById(R.id.FReturnButton);
        backButton = findViewById(R.id.FTCBackButton);
        prevButton = findViewById(R.id.FinishPrevButton);
        nextButton = findViewById(R.id.FinishNextButton);

        stageNumTV = findViewById(R.id.FinishStageNum);
        stageLabel1 = findViewById(R.id.FTCStage1);
        stageLabel2 = findViewById(R.id.FTCStage2);
        stageDist = findViewById(R.id.FTCDist);
        SS = findViewById(R.id.FTCSS);
        blueTC1 = findViewById(R.id.FTCTC1);
        blueTC2 = findViewById(R.id.FTCTC2);
        stageKM = findViewById(R.id.FTCKM);
        finishTC = findViewById(R.id.FTCTC);
        yellowTC = findViewById(R.id.FTCYellowTC);
        TTH = findViewById(R.id.FTCTaTH);
        TTM = findViewById(R.id.FTCTaTM);

        startOrder = findViewById(R.id.FTCOval);
        provStartH = findViewById(R.id.FTCPSH);
        provStartM = findViewById(R.id.FTCPSM);
        actualStartH = findViewById(R.id.FTCASH);
        actualStartM = findViewById(R.id.FTCASM);
        finishTimeH = findViewById(R.id.FTCFTH);
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to move onto the next text box, once this one is full
        finishTimeH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The max input length is 2 digits
                if(finishTimeH.getText().toString().length()==2)
                {
                    // This box contains the hours of a time, so this input cannot be
                    // larger than 24
                    if (Integer.valueOf(finishTimeH.getText().toString()) > 24) {
                        // If input is larger than 24, reset the text and display an error message.
                        finishTimeH.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // If the input is valid, move to the next text box
                        finishTimeH.clearFocus();
                        finishTimeM.requestFocus();
                        finishTimeM.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        finishTimeM = findViewById(R.id.FTCFTM);
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to autofill the next box
        finishTimeM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The max input length is 2 digits
                if(finishTimeM.getText().toString().length()==2)
                {
                    // This box contains the minutes of a time, so this input cannot be
                    // larger than 59
                    int fTimeM = Integer.valueOf(finishTimeM.getText().toString());
                    if (fTimeM > 59) {
                        // If input is larger than 59, reset the text and display an error message.
                        finishTimeM.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise, move to the next box.
                        finishTimeM.clearFocus();
                        finishTimeS.requestFocus();
                        finishTimeS.setCursorVisible(true);
                        // Autofill the minutes box of the stage time box by deducting the
                        // actual start time from the finish time
                        int aStartM = Integer.valueOf(actualStartM.getText().toString());
                        int sTimeM = 0;
                        if (fTimeM > aStartM) {
                            sTimeM = fTimeM - aStartM;
                        } else if (fTimeM < aStartM) {
                            // If the time has gone from one hour to the next,
                            // eg start at 12:59, finish at 13:03, then calculate the difference
                            sTimeM = (60 - aStartM) + fTimeM;
                        }
                        stageTimeM.setText(String.valueOf(sTimeM));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        finishTimeS = findViewById(R.id.FTCFTS);
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to move onto the next text box, once this one is full
        finishTimeS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The maximum input length is 2
                if(finishTimeS.getText().toString().length()==2)
                {
                    // This box contains the seconds of a time, so this input cannot be
                    // larger than 59
                    if (Integer.valueOf(finishTimeS.getText().toString()) > 59) {
                        // If input is larger than 59, reset the text and show error message
                        finishTimeS.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // If input is valid, copy the text into the seconds box of the finish time,
                        // and move to the next box
                        stageTimeS.setText(finishTimeS.getText());
                        finishTimeS.clearFocus();
                        finishTimeMS.requestFocus();
                        finishTimeMS.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        finishTimeMS = findViewById(R.id.FTCFTMS);
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to move onto the next text box, once this one is full
        finishTimeMS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This box contains the milliseconds of a time, so there is no numerical
                // limit for this one, but there can only be 2 digits max
                if(finishTimeMS.getText().toString().length()==2)
                {
                    // Once the box is full, copy the text to the milliseconds
                    // box of the stage time and move to the next box
                    stageTimeMS.setText(finishTimeMS.getText());
                    finishTimeMS.clearFocus();
                    stageTimeM.requestFocus();
                    stageTimeM.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        stageTimeM = findViewById(R.id.FTCTTM);
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to autofill the next box
        stageTimeM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Max input length is 2
                if(stageTimeM.getText().toString().length()==2)
                {
                    // This is a minutes box, so input cannot be larger than 59
                    if (Integer.valueOf(stageTimeM.getText().toString()) > 59) {
                        stageTimeM.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        stageTimeM.clearFocus();
                        stageTimeS.requestFocus();
                        stageTimeS.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        stageTimeS = findViewById(R.id.FTCTTS);
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to autofill the next box
        stageTimeS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Max input length is 2
                if(stageTimeS.getText().toString().length()==2)
                {
                    // This is a seconds box, so input cannot be larger than 59
                    if (Integer.valueOf(stageTimeS.getText().toString()) > 59) {
                        stageTimeS.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        stageTimeS.clearFocus();
                        stageTimeMS.requestFocus();
                        stageTimeMS.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        stageTimeMS = findViewById(R.id.FTCTTMS);
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to autofill the next box
        stageTimeMS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This is a milliseconds box, so input can be any number,
                // but has a max length of 2 digits
                if(stageTimeMS.getText().toString().length()==2)
                {
                    stageTimeMS.clearFocus();
                    finishTimeH.requestFocus();
                    finishTimeH.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        actualTimeH = findViewById(R.id.FTCATH);
        actualTimeM = findViewById(R.id.FTCATM);
        dueTimeH = findViewById(R.id.FTCDTH);
        dueTimeM = findViewById(R.id.FTCDTM);
    }

    // Method to initialise the listeners for the buttons
    private void initListeners() {
        backButton.setOnClickListener(this);
        returnTCButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    // On Click Method for the buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // The back button returns to the choose stage page
            case R.id.FTCBackButton:
                Intent intent = new Intent(this, ChooseStageActivity.class);
                intent.putExtra("ROLE", "Finish");
                startActivity(intent);
                break;
            // The return button shows a pop-up
            case R.id.FReturnButton:
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
        // First check if there are any entries in the database for this stage
        if (finishDatabaseHelper.getStage(stageNum).size() != 0) {
            carNum = finishDatabaseHelper.getCarNum(stageNum, finishOrder);
            carNumTV.setText(String.valueOf(carNum));
            finish = finishDatabaseHelper.getFinish(finishDatabaseHelper.getFinishID(stageNum, carNum));
            finishOrderTV.setText(String.valueOf(finishOrder));
            stage = stageDatabaseHelper.getStage(finish.getStageID());
            startOrder.setText(String.valueOf(stage.getStartOrder()));
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
            // Otherwise set everything to null
            finishOrderTV.setText("0");
            carNumTV.setText("");
        }
        finishTimeH.requestFocus();
        finishTimeH.setCursorVisible(true);
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
                String inputFTH = finishTimeH.getText().toString();
                String inputFTM = finishTimeM.getText().toString();
                String inputFTS = finishTimeS.getText().toString();
                String inputFTMS = finishTimeMS.getText().toString();
                stage.setFinishTime(inputFTH, inputFTM, inputFTS, inputFTMS);
                String inputSTM = stageTimeM.getText().toString();
                String inputSTS = stageTimeS.getText().toString();
                String inputSTMS = stageTimeMS.getText().toString();
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