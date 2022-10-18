package com.example.rallytimingapp.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rallytimingapp.model.Start;

import java.util.ArrayList;
import java.util.List;

public class StartDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "StartManager.db";

    // Start table name
    private static final String TABLE_START = "start";

    // Start Table Columns names
    private static final String COLUMN_START_ID = "start_id";
    private static final String COLUMN_START_ORDER = "start_order";
    private static final String COLUMN_START_STAGE = "start_stage";
    private static final String COLUMN_START_CARNUM = "start_carNum";
    private static final String COLUMN_START_STAGEID = "start_stageID";

    // Create table SQL query
    private String CREATE_START_TABLE = "CREATE TABLE " + TABLE_START + "("
            + COLUMN_START_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_START_ORDER + " INTEGER,"
            + COLUMN_START_STAGE + " INTEGER," + COLUMN_START_CARNUM + " INTEGER," + COLUMN_START_STAGEID
            + " INTEGER" + ")";

    // Drop table SQL query
    private String DROP_START_TABLE = "DROP TABLE IF EXISTS " + TABLE_START;

    public StartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_START_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_START_TABLE);
        onCreate(db);
    }

    // Method to remove all entries from the database
    public void empty() {
        List<Start> startList = getAllStarts();
        for (int i = 0; i < startList.size(); i++) {
            deleteStart(startList.get(i));
        }
    }

    // Method to add an entry to the database
    public void addStart(Start start) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_START_ORDER, start.getStartOrder());
        values.put(COLUMN_START_STAGE, start.getStage());
        values.put(COLUMN_START_CARNUM, start.getCarNum());
        values.put(COLUMN_START_STAGEID, start.getStageID());

        // Inserting Row
        db.insert(TABLE_START, null, values);
        db.close();
    }

    // Method to return the car number of the entry with the given stage number and start order
    public int getCarNum(int stageNum, int startOrder) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_START_CARNUM
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_START_STAGE + " = ?" + " AND " + COLUMN_START_ORDER + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stageNum), String.valueOf(startOrder)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_START, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        int carNum = 0;
        if (cursor.moveToFirst()) {
            carNum = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return carNum;
    }

    // Method to return the entry with the given ID
    @SuppressLint("Range")
    public Start getStart(int startID) {
        String[] columns = {
                COLUMN_START_ID,
                COLUMN_START_ORDER,
                COLUMN_START_STAGE,
                COLUMN_START_CARNUM,
                COLUMN_START_STAGEID
        };

        Start start = new Start();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_START_ID + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(startID)};

        Cursor cursor = db.query(TABLE_START, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            start.setStartID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_ID))));
            start.setStartOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_ORDER))));
            start.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_STAGE))));
            start.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_CARNUM))));
            start.setStageID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_STAGEID))));
        }
        cursor.close();
        db.close();

        return start;
    }

    // Method to return the entry with the given stage and car number
    @SuppressLint("Range")
    public Start getStartByCarNum(int stageNum, int carNum) {
        String[] columns = {
                COLUMN_START_ID,
                COLUMN_START_ORDER,
                COLUMN_START_STAGE,
                COLUMN_START_CARNUM,
                COLUMN_START_STAGEID
        };

        Start start = new Start();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_START_STAGE + " = ?" + " AND " + COLUMN_START_CARNUM + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stageNum), String.valueOf(carNum)};

        Cursor cursor = db.query(TABLE_START, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            start.setStartID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_ID))));
            start.setStartOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_ORDER))));
            start.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_STAGE))));
            start.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_CARNUM))));
            start.setStageID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_STAGEID))));
        }
        cursor.close();
        db.close();

        return start;
    }

    // Method to return the entry with the given stage number and start order
    @SuppressLint("Range")
    public Start getStart(int stageNum, int startOrder) {
        String[] columns = {
                COLUMN_START_ID,
                COLUMN_START_ORDER,
                COLUMN_START_STAGE,
                COLUMN_START_CARNUM,
                COLUMN_START_STAGEID
        };

        Start start = new Start();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_START_STAGE + " = ?" + " AND " + COLUMN_START_ORDER + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stageNum), String.valueOf(startOrder)};

        Cursor cursor = db.query(TABLE_START, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            start.setStartID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_ID))));
            start.setStartOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_ORDER))));
            start.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_STAGE))));
            start.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_CARNUM))));
            start.setStageID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_STAGEID))));
        }
        cursor.close();
        db.close();

        return start;
    }

    // Method to return a list of all entries in the database
    @SuppressLint("Range")
    public List<Start> getAllStarts() {
        String[] columns = {
                COLUMN_START_ID,
                COLUMN_START_ORDER,
                COLUMN_START_STAGE,
                COLUMN_START_CARNUM,
                COLUMN_START_STAGEID
        };

        String sortOrder = COLUMN_START_ID + " ASC";
        List<Start> startList = new ArrayList<Start>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_START, //Table to query
                columns,             //columns to return
                null,        //columns for the WHERE clause
                null,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                Start start = new Start();
                start.setStartID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_ID))));
                start.setStartOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_ORDER))));
                start.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_STAGE))));
                start.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_CARNUM))));
                start.setStageID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_STAGEID))));
                startList.add(start);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return startList;
    }

    // Method to update an entry in the database
    public void updateStart(Start start) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_START_ORDER, start.getStartOrder());
        values.put(COLUMN_START_STAGE, start.getStage());
        values.put(COLUMN_START_CARNUM, start.getCarNum());
        values.put(COLUMN_START_STAGEID, start.getStageID());
        // updating row
        db.update(TABLE_START, values, COLUMN_START_ID + " = ?",
                new String[]{String.valueOf(start.getStartID())});
        db.close();
    }

    // Method to delete an entry in the database
    public void deleteStart(Start start) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_START, COLUMN_START_ID + " = ?",
                new String[]{String.valueOf(start.getStartID())});
        db.close();
    }

    // Method to return a list of all entries with the given stage number
    @SuppressLint("Range")
    public List<Start> getStage(int stageNum) {
        String[] columns = {
                COLUMN_START_ID,
                COLUMN_START_ORDER,
                COLUMN_START_STAGE,
                COLUMN_START_CARNUM,
                COLUMN_START_STAGEID
        };

        String sortOrder = COLUMN_START_ORDER + " ASC";
        // selection criteria
        String selection = COLUMN_START_STAGE + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stageNum)};
        List<Start> startList = new ArrayList<Start>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_START, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                Start start = new Start();
                start.setStartID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_ID))));
                start.setStartOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_ORDER))));
                start.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_STAGE))));
                start.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_CARNUM))));
                start.setStageID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_START_STAGEID))));
                startList.add(start);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return startList;
    }

    // Method to return the start order of the most recently added database
    // entry with the given stage number
    @SuppressLint("Range")
    public int getCurrStartOrder(int stage) {
        String[] columns = {
                COLUMN_START_ORDER
        };

        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = COLUMN_START_ORDER + " ASC";
        // selection criteria
        String selection = COLUMN_START_STAGE + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stage)};

        Cursor cursor = db.query(TABLE_START, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        int currSO = 0;

        if (cursor.moveToLast()) {
            currSO = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return currSO;
    }

    // Check if an entry with the given stage and car number exists
    public boolean checkStart(int stage, int carNum) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_START_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_START_STAGE + " = ?" + " AND " + COLUMN_START_CARNUM + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stage), String.valueOf(carNum)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_START, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}
