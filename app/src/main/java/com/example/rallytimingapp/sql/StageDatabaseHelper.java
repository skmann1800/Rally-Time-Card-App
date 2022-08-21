package com.example.rallytimingapp.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rallytimingapp.model.Stage;

import java.util.ArrayList;
import java.util.List;

public class StageDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "StageManager.db";

    // User table name
    private static final String TABLE_STAGE = "stage";

    // User Table Columns names
    private static final String COLUMN_STAGE_ID = "stage_id";
    private static final String COLUMN_STAGE_NUM = "stage_num";
    private static final String COLUMN_STAGE_SO = "stage_start_order";
    private static final String COLUMN_STAGE_PS = "stage_prov_start";
    private static final String COLUMN_STAGE_AS = "stage_actual_start";
    private static final String COLUMN_STAGE_FT = "stage_finish_time";
    private static final String COLUMN_STAGE_ST = "stage_stage_time";
    private static final String COLUMN_STAGE_AT = "stage_actual_time";
    private static final String COLUMN_STAGE_DT = "stage_due_time";

    // Create table SQL query
    private String CREATE_STAGE_TABLE = "CREATE TABLE " + TABLE_STAGE + "("
            + COLUMN_STAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_STAGE_NUM + " INTEGER,"
            + COLUMN_STAGE_SO + " TEXT," + COLUMN_STAGE_PS + " TEXT," + COLUMN_STAGE_AS
            + " TEXT," + COLUMN_STAGE_FT + " TEXT," + COLUMN_STAGE_ST + " TEXT,"
            + COLUMN_STAGE_AT + " TEXT," + COLUMN_STAGE_DT + " TEXT" + ")";

    // Drop table SQL query
    private String DROP_STAGE_TABLE = "DROP TABLE IF EXISTS " + TABLE_STAGE;

    public StageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STAGE_TABLE);
        onCreate(db);
    }

    public void addStage(Stage stage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STAGE_NUM, stage.getStageNum());
        values.put(COLUMN_STAGE_SO, stage.getStartOrder());
        values.put(COLUMN_STAGE_PS, stage.getProvStart());
        values.put(COLUMN_STAGE_AS, stage.getActualStart());
        values.put(COLUMN_STAGE_FT, stage.getFinishTime());
        values.put(COLUMN_STAGE_ST, stage.getStageTime());
        values.put(COLUMN_STAGE_AT, stage.getActualTime());
        values.put(COLUMN_STAGE_DT, stage.getDueTime());

        // Inserting Row
        db.insert(TABLE_STAGE, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<Stage> getAllStages() {
        String[] columns = {
                COLUMN_STAGE_ID,
                COLUMN_STAGE_NUM,
                COLUMN_STAGE_SO,
                COLUMN_STAGE_PS,
                COLUMN_STAGE_AS,
                COLUMN_STAGE_FT,
                COLUMN_STAGE_ST,
                COLUMN_STAGE_AT,
                COLUMN_STAGE_DT
        };

        String sortOrder = COLUMN_STAGE_ID + " ASC";
        List<Stage> stageList = new ArrayList<Stage>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STAGE, //Table to query
                columns,             //columns to return
                null,        //columns for the WHERE clause
                null,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                Stage stage = new Stage();
                stage.setStageId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_STAGE_ID))));
                stage.setStageNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_STAGE_NUM))));
                stage.setStartOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_STAGE_SO))));
                stage.setProvStart(cursor.getString(cursor.getColumnIndex(COLUMN_STAGE_PS)));
                stage.setActualStart(cursor.getString(cursor.getColumnIndex(COLUMN_STAGE_AS)));
                stage.setFinishTime(cursor.getString(cursor.getColumnIndex(COLUMN_STAGE_FT)));
                stage.setStageTime(cursor.getString(cursor.getColumnIndex(COLUMN_STAGE_ST)));
                stage.setActualTime(cursor.getString(cursor.getColumnIndex(COLUMN_STAGE_AT)));
                stage.setDueTime(cursor.getString(cursor.getColumnIndex(COLUMN_STAGE_DT)));
                stageList.add(stage);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return stageList;
    }

    public void updateStage(Stage stage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STAGE_NUM, stage.getStageNum());
        values.put(COLUMN_STAGE_SO, stage.getStartOrder());
        values.put(COLUMN_STAGE_PS, stage.getProvStart());
        values.put(COLUMN_STAGE_AS, stage.getActualStart());
        values.put(COLUMN_STAGE_FT, stage.getFinishTime());
        values.put(COLUMN_STAGE_ST, stage.getStageTime());
        values.put(COLUMN_STAGE_AT, stage.getActualTime());
        values.put(COLUMN_STAGE_DT, stage.getDueTime());
        // updating row
        db.update(TABLE_STAGE, values, COLUMN_STAGE_ID + " = ?",
                new String[]{String.valueOf(stage.getStageId())});
        db.close();
    }

    public void deleteStage(Stage stage) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by username
        db.delete(TABLE_STAGE, COLUMN_STAGE_ID + " = ?",
                new String[]{String.valueOf(stage.getStageId())});
        db.close();
    }
}
