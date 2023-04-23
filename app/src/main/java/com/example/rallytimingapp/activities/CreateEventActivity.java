package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.model.EventSections;
import com.example.rallytimingapp.sql.EventSectionsDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = CreateEventActivity.this;

    private Button backButton;
    private Button prevButton;
    private Button nextButton;
    private Button saveButton;

    private EventSectionsDatabaseHelper sectionDatabaseHelper;
    private EventSections section;

    private TextView blue1;
    private TextView blue2;
    private TextView blue3;
    private TextView TCName;
    private TextView finishTime;
    private TextView stageDistance;
    private TextView finishTimeHoursBox;
    private TextView finishTimeMinutesBox;
    private TextView finishTimeSecondsBox;
    private TextView finishTimeMillisecondsBox;
    private ImageView flag;
    private TextView provStart;
    private TextView provStartHoursBox;
    private TextView provStartMinutesBox;
    private ImageView arrow1;
    private ImageView arrow2;
    private TextView targetTimeHoursBox;
    private TextView targetTimeMinutesBox;
    private TextView provStartLabel;
    private TextView actualStartLabel;
    private TextView timeTaken;
    private TextView oval;
    private ImageView stop;
    private TextView timeTakenMinutesBox;
    private TextView timeTakenSecondsBox;
    private TextView timeTakenMillisecondsBox;
    private TextView startOrder;
    private TextView timeTakenLabel;
    private TextView nextTC;
    private TextView yellowTC;

    private TextView stageDistanceLabel;

    private Switch isStageSwitch;
    private Switch hasStartOrderSwitch;
    private Switch hasProvStartSwitch;
    private EditText currentTCInput;
    private EditText nextTCInput;
    private EditText TCNameInput;
    private EditText stageDistanceInput;
    private EditText TCDistanceInput;
    private EditText averageSpeedInput;
    private EditText targetTimeHoursInput;
    private EditText targetTimeMinutesInput;

    private ScrollView scrollView;

    private Boolean isStage;
    private Boolean hasStartOrder;
    private Boolean hasProvStart;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        initViews();
        initObjects();
        initListeners();

        index = sectionDatabaseHelper.getLatestIndex() + 1;
    }

    // Method to initialise views
    private void initViews() {
        backButton = findViewById(R.id.CEBackButton);
        prevButton = findViewById(R.id.PrevSectionButton);
        nextButton = findViewById(R.id.NextSectionButton);
        saveButton = findViewById(R.id.SaveSectionButton);

        blue1 = findViewById(R.id.Blue1);
        blue2 = findViewById(R.id.Blue2);
        blue3 = findViewById(R.id.Blue3);
        TCName = findViewById(R.id.TCName);
        finishTime = findViewById(R.id.FinishTime);
        stageDistance = findViewById(R.id.StageDistance);
        finishTimeHoursBox = findViewById(R.id.FinishTimeHours);
        finishTimeMinutesBox = findViewById(R.id.FinishTimeMinutes);
        finishTimeSecondsBox = findViewById(R.id.FinishTimeSeconds);
        finishTimeMillisecondsBox = findViewById(R.id.FinishTimeMilliseconds);
        flag = findViewById(R.id.Flag);
        provStart = findViewById(R.id.ProvStart);
        provStartHoursBox = findViewById(R.id.ProvStartHours);
        provStartMinutesBox = findViewById(R.id.ProvStartMinutes);
        arrow1 = findViewById(R.id.Arrow1);
        arrow2 = findViewById(R.id.Arrow2);
        targetTimeHoursBox = findViewById(R.id.TargetTimeHours);
        targetTimeMinutesBox = findViewById(R.id.TargetTimeMinutes);
        provStartLabel = findViewById(R.id.ProvStartLabel);
        actualStartLabel = findViewById(R.id.ActualStartLabel);
        timeTaken = findViewById(R.id.TimeTaken);
        oval = findViewById(R.id.Oval);
        stop = findViewById(R.id.Stop);
        timeTakenMinutesBox = findViewById(R.id.TimeTakenMinutes);
        timeTakenSecondsBox = findViewById(R.id.TimeTakenSeconds);
        timeTakenMillisecondsBox = findViewById(R.id.TimeTakenMilliseconds);
        startOrder = findViewById(R.id.StartOrder);
        timeTakenLabel = findViewById(R.id.TimeTakenLabel);
        nextTC = findViewById(R.id.NextTC);
        yellowTC = findViewById(R.id.YellowTC);

        stageDistanceLabel = findViewById(R.id.StageDistanceLabel);

        isStageSwitch = findViewById(R.id.IsStageSwitch);
        isStage = isStageSwitch.isChecked();
        hasStartOrderSwitch = findViewById(R.id.HasStartOrderSwitch);
        hasStartOrder = hasStartOrderSwitch.isChecked();
        hasProvStartSwitch = findViewById(R.id.HasProvStartSwitch);
        hasProvStart = hasProvStartSwitch.isChecked();
        currentTCInput = findViewById(R.id.CurrentTCInput);
        nextTCInput = findViewById(R.id.NextTCInput);
        TCNameInput = findViewById(R.id.TCNameInput);
        stageDistanceInput = findViewById(R.id.StageDistanceInput);
        TCDistanceInput = findViewById(R.id.TCDistanceInput);
        averageSpeedInput = findViewById(R.id.AverageSpeedInput);
        targetTimeHoursInput = findViewById(R.id.TargetTimeHoursInput);
        targetTimeMinutesInput = findViewById(R.id.TargetTimeMinutesInput);

        scrollView = findViewById(R.id.InputsScrollView);
    }

    // Method to initialise objects
    private void initObjects() {
        sectionDatabaseHelper = new EventSectionsDatabaseHelper(activity);
        section = new EventSections();
    }

    // Method to initialise the listeners for the buttons
    private void initListeners() {
        backButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        isStageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isStage = isChecked;
                if (isStage) {
                    hasStartOrderSwitch.setChecked(true);
                    hasProvStartSwitch.setChecked(true);
                    finishTime.setVisibility(View.VISIBLE);
                    finishTimeHoursBox.setVisibility(View.VISIBLE);
                    finishTimeMinutesBox.setVisibility(View.VISIBLE);
                    finishTimeSecondsBox.setVisibility(View.VISIBLE);
                    finishTimeMillisecondsBox.setVisibility(View.VISIBLE);
                    timeTaken.setVisibility(View.VISIBLE);
                    timeTakenMinutesBox.setVisibility(View.VISIBLE);
                    timeTakenSecondsBox.setVisibility(View.VISIBLE);
                    timeTakenMillisecondsBox.setVisibility(View.VISIBLE);
                    timeTakenLabel.setVisibility(View.VISIBLE);
                    stop.setVisibility(View.VISIBLE);
                    flag.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.drawable.arrow_with_flag);
                    stageDistanceLabel.setVisibility(View.VISIBLE);
                    stageDistanceInput.setVisibility(View.VISIBLE);
                    actualStartLabel.setVisibility(View.INVISIBLE);

                    String text = "SS " + currentTCInput.getText().toString() + " " + TCNameInput.getText().toString();
                    TCName.setText(text);
                    timeTakenLabel.setText(text);
                    text = stageDistanceInput.getText().toString() + " km";
                    stageDistance.setText(text);
                    text = "SS " + currentTCInput.getText().toString() + " (+3\")";
                    provStartLabel.setText(text);
                } else {
                    finishTime.setVisibility(View.INVISIBLE);
                    finishTimeHoursBox.setVisibility(View.INVISIBLE);
                    finishTimeMinutesBox.setVisibility(View.INVISIBLE);
                    finishTimeSecondsBox.setVisibility(View.INVISIBLE);
                    finishTimeMillisecondsBox.setVisibility(View.INVISIBLE);
                    timeTaken.setVisibility(View.INVISIBLE);
                    timeTakenMinutesBox.setVisibility(View.INVISIBLE);
                    timeTakenSecondsBox.setVisibility(View.INVISIBLE);
                    timeTakenMillisecondsBox.setVisibility(View.INVISIBLE);
                    timeTakenLabel.setVisibility(View.INVISIBLE);
                    stop.setVisibility(View.INVISIBLE);
                    flag.setVisibility(View.INVISIBLE);
                    arrow2.setImageResource(R.drawable.arrow4);
                    stageDistanceLabel.setVisibility(View.GONE);
                    stageDistanceInput.setVisibility(View.GONE);
                    actualStartLabel.setVisibility(View.VISIBLE);

                    TCName.setText(TCNameInput.getText().toString());
                    stageDistance.setText("");
                    String text = "TC " + currentTCInput.getText().toString();
                    provStartLabel.setText(text);
                    actualStartLabel.setText(text);
                }
            }
        });
        hasStartOrderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasStartOrder = isChecked;
                if (isChecked) {
                    oval.setVisibility(View.VISIBLE);
                    startOrder.setVisibility(View.VISIBLE);
                } else {
                    oval.setVisibility(View.INVISIBLE);
                    startOrder.setVisibility(View.INVISIBLE);
                    isStageSwitch.setChecked(false);
                }
            }
        });
        hasProvStartSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasProvStart = isChecked;
                if (isChecked) {
                    provStart.setVisibility(View.VISIBLE);
                    provStartHoursBox.setVisibility(View.VISIBLE);
                    provStartMinutesBox.setVisibility(View.VISIBLE);
                    provStartLabel.setVisibility(View.VISIBLE);
                    arrow1.setVisibility(View.VISIBLE);

                    String text = "TC " + currentTCInput.getText().toString();
                    provStartLabel.setText(text);
                } else {
                    provStart.setVisibility(View.INVISIBLE);
                    provStartHoursBox.setVisibility(View.INVISIBLE);
                    provStartMinutesBox.setVisibility(View.INVISIBLE);
                    provStartLabel.setVisibility(View.INVISIBLE);
                    arrow1.setVisibility(View.INVISIBLE);
                    isStageSwitch.setChecked(false);
                }
            }
        });
        currentTCInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = currentTCInput.getText().toString();
                String TCLabel = "TC " + input;
                blue1.setText(TCLabel);
                if (isStage) {
                    String stageLabel = "SS " + input + " " + TCNameInput.getText().toString();
                    TCName.setText(stageLabel);
                    timeTakenLabel.setText(stageLabel);
                    String provStart = "SS " + input + " (+3\")";
                    provStartLabel.setText(provStart);
                } else {
                    actualStartLabel.setText(TCLabel);
                    if (hasProvStart) {
                        provStartLabel.setText(TCLabel);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        nextTCInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = nextTCInput.getText().toString();
                String TCLabel = "TC " + input;
                blue3.setText(TCLabel);
                nextTC.setText(TCLabel);
                yellowTC.setText(TCLabel);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        TCNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = TCNameInput.getText().toString();
                if (isStage) {
                    String stageLabel = "SS " + currentTCInput.getText().toString() + " " + input;
                    TCName.setText(stageLabel);
                    timeTakenLabel.setText(stageLabel);
                } else {
                    TCName.setText(input);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        stageDistanceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = stageDistanceInput.getText().toString() + " km";
                stageDistance.setText(input);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        TCDistanceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = TCDistanceInput.getText().toString() + " km ";
                String label = input + averageSpeedInput.getText().toString() + " km/h";
                blue2.setText(label);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        averageSpeedInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = averageSpeedInput.getText().toString() + " km/h";
                String label = TCDistanceInput.getText().toString() + " km " + input;
                blue2.setText(label);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        targetTimeHoursInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = targetTimeHoursInput.getText().toString();
                // The max input length is 2 digits
                if (input.length() > 0) {
                    if (input.length() <= 2) {
                        // This box contains the hours of a time, so this input cannot be
                        // larger than 24
                        if (Integer.parseInt(input) >= 24) {
                            // If input is larger than 24, reset the text and display an error message.
                            targetTimeHoursInput.setText("");
                            Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                        } else {
                            // If the input is valid, fill in the box
                            targetTimeHoursBox.setText(input);
                        }
                    }
                } else {
                    targetTimeHoursBox.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        targetTimeMinutesInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = targetTimeMinutesInput.getText().toString();
                // The max input length is 2 digits
                if (input.length() > 0) {
                    if (input.length() <= 2) {
                        // This box contains the minutes of a time, so this input cannot be
                        // larger than 59
                        if (Integer.parseInt(input) > 59) {
                            // If input is larger than 59, reset the text and display an error message.
                            targetTimeMinutesInput.setText("");
                            Snackbar.make(scrollView, "Invalid Input", Snackbar.LENGTH_LONG).show();
                        } else {
                            // If the input is valid, fill in the box
                            targetTimeMinutesBox.setText(input);
                        }
                    }
                } else {
                    targetTimeMinutesBox.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // On Click Method for the buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CEBackButton:
                // The back button returns to the admin options page
                Intent intent = new Intent(this, AdminOptionsActivity.class);
                startActivity(intent);
                break;
            // The prev button goes to the previous section
            case R.id.PrevSectionButton:
                if (index >= 1) {
                    goToPrev();
                }
                break;
            // The next button goes to the next section
            case R.id.NextSectionButton:
                break;
            // The save button saves the inputs
            case R.id.SaveSectionButton:
                save();
                break;
        }
    }

    private void goToPrev() {
        index = index - 1;
        section = sectionDatabaseHelper.getSection(index);
        Snackbar.make(scrollView, String.valueOf(section.getIsStage()), Snackbar.LENGTH_LONG).show();
        isStageSwitch.setChecked(section.getIsStage());
        hasStartOrderSwitch.setChecked(section.getHasStartOrder());
        hasProvStartSwitch.setChecked(section.getHasProvStart());
        currentTCInput.setText(section.getCurrentTC());
        nextTCInput.setText(section.getNextTC());
        TCNameInput.setText(section.getTCName());
        if (section.getIsStage()) {
            stageDistanceInput.setText(section.getStageDistance());
        }
        TCDistanceInput.setText(section.getTCDistance());
        averageSpeedInput.setText(section.getAverageSpeed());
        targetTimeHoursInput.setText(section.getTargetTimeHours());
        targetTimeMinutesInput.setText(section.getTargetTimeMinutes());

    }

    private void save() {
        section.setIsStage(isStageSwitch.isChecked());
        section.setHasStartOrder(hasStartOrderSwitch.isChecked());
        section.setHasProvStart(hasProvStartSwitch.isChecked());
        section.setCurrentTC(currentTCInput.getText().toString());
        section.setNextTC(nextTCInput.getText().toString());
        section.setTCName(TCNameInput.getText().toString());
        if (isStage) {
            section.setStageDistance(stageDistanceInput.getText().toString());
        } else {
            section.setStageDistance(null);
        }
        section.setTCDistance(TCDistanceInput.getText().toString());
        section.setAverageSpeed(averageSpeedInput.getText().toString());
        section.setTargetTimeHours(targetTimeHoursInput.getText().toString());
        section.setTargetTimeMinutes(targetTimeMinutesInput.getText().toString());
        sectionDatabaseHelper.addSection(section);
    }
}