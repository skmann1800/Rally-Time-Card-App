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

public class CompViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ScrollView scrollView;

    private TextView carNumTV;
    private Button checkIn1;
    private Button reqTime1;
    private Button checkIn2;
    private Button reqTime2;
    private Button checkIn3;
    private Button reqTime3;
    private Button checkIn4;
    private Button reqTime4;

    private PopupWindow checkInPopup;
    private PopupWindow reqTimePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_view);

        initViews();
        initListeners();

        int carNum = getIntent().getIntExtra("CAR_NUM", 0);
        carNumTV.setText(String.valueOf(carNum));
    }

    private void initViews() {
        scrollView = findViewById(R.id.CompViewScrollView);
        carNumTV = findViewById(R.id.CarNum);
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

    private void ShowCheckInPopup(String message) {
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

        Button confirmCheckIn = layout.findViewById(R.id.ConfirmCheckInButton);
        confirmCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do Something

                checkInPopup.dismiss();
            }
        });
    }

    private void ShowReqTimePopup(String message) {
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

        Button confirmCheckIn = layout.findViewById(R.id.ConfirmReqTimeButton);
        confirmCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do Something

                reqTimePopup.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CheckIn1:
                ShowCheckInPopup("Check In to Stage 1?");
                break;
            case R.id.ReqTime1:
                ShowReqTimePopup("Request Time for Stage 1?");
                break;
            case R.id.CheckIn2:
                ShowCheckInPopup("Check In to Stage 2?");
                break;
            case R.id.ReqTime2:
                ShowReqTimePopup("Request Time for Stage 2?");
                break;
            case R.id.CheckIn3:
                ShowCheckInPopup("Check In to Stage 3?");
                break;
            case R.id.ReqTime3:
                ShowReqTimePopup("Request Time for Stage 3?");
                break;
            case R.id.CheckIn4:
                ShowCheckInPopup("Check In to Stage 4?");
                break;
            case R.id.ReqTime4:
                ShowReqTimePopup("Request Time for Stage 4?");
                break;
        }
    }
}