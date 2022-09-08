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
import com.example.rallytimingapp.sql.AControlDatabaseHelper;
import com.example.rallytimingapp.sql.FinishDatabaseHelper;
import com.example.rallytimingapp.sql.StageDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

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
    private List<Finish> finishList;

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

        stageNum = getIntent().getIntExtra("STAGE", 0);
        finishOrder = 1;
        fillInCards();
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

    private void initObjects() {
        stageDatabaseHelper = new StageDatabaseHelper(activity);
        finishDatabaseHelper = new FinishDatabaseHelper(activity);
        stage = new Stage();
        finish = new Finish();
        finishList = new ArrayList<>();
    }

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
        finishTimeH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(finishTimeH.getText().toString().length()==2)
                {
                    if (Integer.valueOf(finishTimeH.getText().toString()) > 24) {
                        finishTimeH.setText("");
                        Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                    } else {
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
        finishTimeM.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    int fTimeM = Integer.valueOf(finishTimeM.getText().toString());
                    int aStartM = Integer.valueOf(actualStartM.getText().toString());
                    int sTimeM = 0;
                    if (fTimeM > aStartM) {
                        sTimeM = fTimeM - aStartM;
                    } else if (fTimeM < aStartM) {
                        sTimeM = (60 - aStartM) + fTimeM;
                    }
                    stageTimeM.setText(String.valueOf(sTimeM));
                }
            }
        });
        finishTimeS = findViewById(R.id.FTCFTS);
        finishTimeS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    stageTimeS.setText(finishTimeS.getText());
                }
            }
        });
        finishTimeMS = findViewById(R.id.FTCFTMS);
        finishTimeMS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    stageTimeMS.setText(finishTimeMS.getText());
                }
            }
        });
        stageTimeM = findViewById(R.id.FTCTTM);
        stageTimeS = findViewById(R.id.FTCTTS);
        stageTimeMS = findViewById(R.id.FTCTTMS);
        actualTimeH = findViewById(R.id.FTCATH);
        actualTimeM = findViewById(R.id.FTCATM);
        dueTimeH = findViewById(R.id.FTCDTH);
        dueTimeM = findViewById(R.id.FTCDTM);
    }

    private void initListeners() {
        backButton.setOnClickListener(this);
        returnTCButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.FTCBackButton:
                Intent intent = new Intent(this, ChooseFinishActivity.class);
                startActivity(intent);
                break;
            case R.id.FReturnButton:
                ShowReturnTCPopup();
                break;
            case R.id.FinishPrevButton:
                previousTC();
                break;
            case R.id.FinishNextButton:
                nextTC();
                break;
        }
    }

    private void fillInCards() {
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
            finishOrderTV.setText("0");
            carNumTV.setText("");
        }
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

        finish = finishDatabaseHelper.getFinish(stageNum, finishOrder);
        carNum = finish.getCarNum();
        String currCarNum = String.valueOf(carNum);
        TextView text = layout.findViewById(R.id.ReturnTC);
        text.setText("Return Time Card to Car " + currCarNum + "?");

        Button yesReturn = layout.findViewById(R.id.YesReturnButton);
        yesReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stage = stageDatabaseHelper.getStage(finish.getStageID());
                String inputFTH = finishTimeH.getText().toString();
                String inputFTM = finishTimeM.getText().toString();
                String inputFTS = finishTimeS.getText().toString();
                String inputFTMS = finishTimeMS.getText().toString();
                stage.setFinishTime(inputFTH + ":" + inputFTM + ":" + inputFTS + ":" + inputFTMS);
                String inputSTM = stageTimeM.getText().toString();
                String inputSTS = stageTimeS.getText().toString();
                String inputSTMS = stageTimeMS.getText().toString();
                stage.setStageTime(inputSTM + ":" + inputSTS + ":" + inputSTMS);
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
        if (finishOrder > 1) {
            finishOrder = finishOrder - 1;
            fillInCards();
        }
    }

    public void nextTC() {
        int currFinishOrder = finishDatabaseHelper.getCurrFinishOrder(stageNum);
        if ((finishOrder + 1) <= currFinishOrder) {
            finishOrder = finishOrder + 1;
            fillInCards();
        }
    }
}