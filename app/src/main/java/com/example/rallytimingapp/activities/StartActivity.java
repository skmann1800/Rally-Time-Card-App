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
import com.example.rallytimingapp.model.Stage;
import com.example.rallytimingapp.model.Start;
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

        // Retrieve stage number from intent
        stageNum = getIntent().getIntExtra("STAGE", 0);
        // Set default start order to be 1
        startOrder = 1;
        // Fill in the timecards
        fillInCards();
        // Based on the stage number, fill in the labels as follows
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

    // Method to initialise objects
    private void initObjects() {
        stageDatabaseHelper = new StageDatabaseHelper(activity);
        startDatabaseHelper = new StartDatabaseHelper(activity);
        stage = new Stage();
        start = new Start();
    }

    // Method to initialise views
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
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to move onto the next text box, once this one is full
        actualStartH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The max input length is 2 digits
                if(actualStartH.getText().toString().length()==2)
                {
                    // This box contains the hours, so this input cannot be
                    // larger than 24
                    if (Integer.valueOf(actualStartH.getText().toString()) > 24) {
                        // If input is larger than 24, reset the text and display an error message.
                        actualStartH.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // If the input is valid, move to the next text box
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
        // Add a text changed listener to prevent users from inputting an invalid input
        // And to autofill the next box
        actualStartM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // The max input length is 2 digits
                if(actualStartM.getText().toString().length()==2)
                {
                    // This box contains the minutes, so this input cannot be
                    // larger than 59
                    if (Integer.valueOf(actualStartM.getText().toString()) > 59) {
                        // If input is larger than 59, reset the text and display an error message.
                        actualStartM.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Otherwise, move to the next box.
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

    // Method to initialise listeners for the buttons
    private void initListeners() {
        backButton.setOnClickListener(this);
        changeSOButton.setOnClickListener(this);
        returnTCButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    // On Click method for each button
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.STCBackButton:
                // Back button goes back to the choose stage activity
                Intent intent = new Intent(this, ChooseStageActivity.class);
                intent.putExtra("ROLE", "Start");
                startActivity(intent);
                break;
            case R.id.SChangeSOButton:
                // Change start order button shows a pop-up
                ShowChangeSOPopup();
                break;
            case R.id.SReturnButton:
                // Return timecard button shows a pop-up
                ShowReturnTCPopup();
                break;
            case R.id.StartPrevButton:
                // Left arrow button goes to the previous start order
                previousTC();
                break;
            case R.id.StartNextButton:
                // Right arrow button goes to the next start order
                nextTC();
                break;
        }
    }

    // Fill in the text boxes of the timecards, if there is at least one entry in the database
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
            // If there are no entries in the Database, set the start order TV to 0
            // Leave everything else empty
            startOrderTV.setText("0");
            carNumTV.setText("");
        }
        actualStartH.requestFocus();
        actualStartH.setCursorVisible(true);
    }

    // Displays a pop-up to confirm if the user wants to change the start order
    // and what they want to change it to
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

        // Get the start order of the last entry to the Start database,
        // This will be used to ensure that a swap is not made with a start order that doesn't exist yet
        int currSO = startDatabaseHelper.getCurrStartOrder(stageNum);
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
        // Get the Start Database entry for the current start order
        Start currStart = startDatabaseHelper.getStart(stageNum, currSO);
        // Get the Start database entry of the new start order
        Start start2 = startDatabaseHelper.getStart(stageNum, newSO);
        // Change the current entries start order and update the database
        currStart.setStartOrder(newSO);
        startDatabaseHelper.updateStart(currStart);
        // Get the car number of the current entry
        carNum = currStart.getCarNum();
        // Get the stage database entry for the current entry
        stage = stageDatabaseHelper.getStage(currStart.getStageID());
        // Change the start order in the stage database and update it
        stage.setStartOrder(newSO);
        stageDatabaseHelper.updateStage(stage);

        // Set the start order of the other entry to the current start order and
        // update the Start database
        start2.setStartOrder(currSO);
        startDatabaseHelper.updateStart(start2);
        // Get the car number of the other entry
        carNum = start2.getCarNum();
        // Get the stage database entry for the other entry
        stage = stageDatabaseHelper.getStage(start2.getStageID());
        // Change the start order in the stage database and update it
        stage.setStartOrder(currSO);
        stageDatabaseHelper.updateStage(stage);
    }

    private void ShowReturnTCPopup() {
        // Pop-up to confirm if the user wants to return the timecard to the competitor
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
        start = startDatabaseHelper.getStart(stageNum, startOrder);
        carNum = start.getCarNum();
        String currCarNum = String.valueOf(carNum);
        TextView text = layout.findViewById(R.id.ReturnTC);
        // Change the text to include the car number
        text.setText("Return Time Card to Car " + currCarNum + "?");

        Button yesReturn = layout.findViewById(R.id.YesReturnButton);
        // Set on click listener for yes button
        yesReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the stage entry for the current start entry
                stage = stageDatabaseHelper.getStage(start.getStageID());
                String inputASH = actualStartH.getText().toString();
                String inputASM = actualStartM.getText().toString();
                stage.setActualStart(inputASH, inputASM);
                // Save the time that was put into the actual start boxes as a string
                String inputSO = startOrderTC.getText().toString();
                // Save the start order that was entered
                // and update the entry in the stage database
                stage.setStartOrder(Integer.valueOf(inputSO));
                stageDatabaseHelper.updateStage(stage);
                returnTCPopup.dismiss();
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

    // Method to move to the previous start order
    public void previousTC() {
        // If the current start order is 1, it is not possible to go to the
        // previous one, so do nothing
        if (startOrder > 1) {
            // Otherwise change the start order and fill the cards again
            startOrder = startOrder - 1;
            fillInCards();
        }
    }

    // Method to move to the next start order
    public void nextTC() {
        // If the current start order is the same as the most recent entry,
        // it is not possible to go to the next one, so do nothing
        int currStartOrder = startDatabaseHelper.getCurrStartOrder(stageNum);
        if ((startOrder + 1) <= currStartOrder) {
            // Otherwise change the start order and fill the cards again
            startOrder = startOrder + 1;
            fillInCards();
        }
    }
}