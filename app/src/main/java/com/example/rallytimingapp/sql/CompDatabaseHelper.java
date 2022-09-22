package com.example.rallytimingapp.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rallytimingapp.model.AControl;
import com.example.rallytimingapp.model.Competitor;

import java.util.ArrayList;
import java.util.List;

public class CompDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 9;

    // Database Name
    private static final String DATABASE_NAME = "CompetitorManager.db";

    // User table name
    private static final String TABLE_COMP = "competitor";

    // User Table Columns names
    private static final String COLUMN_COMP_ID = "_id";
    private static final String COLUMN_COMP_CARNUM = "competitor_carNum";
    private static final String COLUMN_COMP_DRIVER = "competitor_driver";
    private static final String COLUMN_COMP_CODRIVER = "competitor_codriver";
    private static final String COLUMN_COMP_STAGE1ID = "competitor_stage1_id";
    private static final String COLUMN_COMP_STAGE2ID = "competitor_stage2_id";
    private static final String COLUMN_COMP_STAGE3ID = "competitor_stage3_id";
    private static final String COLUMN_COMP_STAGE4ID = "competitor_stage4_id";

    // Create table SQL query
    private String CREATE_COMP_TABLE = "CREATE TABLE " + TABLE_COMP + "("
            + COLUMN_COMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_COMP_CARNUM + " INTEGER,"
            + COLUMN_COMP_DRIVER + " TEXT," + COLUMN_COMP_CODRIVER + " TEXT," + COLUMN_COMP_STAGE1ID
            + " INTEGER," + COLUMN_COMP_STAGE2ID + " INTEGER," + COLUMN_COMP_STAGE3ID + " INTEGER,"
            + COLUMN_COMP_STAGE4ID + " INTEGER" + ")";

    // Drop table SQL query
    private String DROP_COMP_TABLE = "DROP TABLE IF EXISTS " + TABLE_COMP;

    public CompDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_COMP_TABLE);
        onCreate(db);
    }

    public void addCompetitor(Competitor competitor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_COMP_CARNUM, competitor.getCarNum());
        values.put(COLUMN_COMP_DRIVER, competitor.getDriver());
        values.put(COLUMN_COMP_CODRIVER, competitor.getCodriver());
        values.put(COLUMN_COMP_STAGE1ID, competitor.getStage1Id());
        values.put(COLUMN_COMP_STAGE2ID, competitor.getStage2Id());
        values.put(COLUMN_COMP_STAGE3ID, competitor.getStage3Id());
        values.put(COLUMN_COMP_STAGE4ID, competitor.getStage4Id());

        // Inserting Row
        db.insert(TABLE_COMP, null, values);
        db.close();
    }

    public int getCompId(int carNum) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_COMP_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_COMP_CARNUM + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(carNum)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_COMP, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        int compID = 0;
        if (cursor.moveToFirst()) {
            compID = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return compID;
    }

    public void empty() {
        List<Competitor> competitorList = getAllCompetitors();
        for (int i = 0; i < competitorList.size(); i++) {
            deleteCompetitor(competitorList.get(i));
        }
    }

    @SuppressLint("Range")
    public Competitor getCompetitorByID(int compID) {
        String[] columns = {
                COLUMN_COMP_ID,
                COLUMN_COMP_CARNUM,
                COLUMN_COMP_DRIVER,
                COLUMN_COMP_CODRIVER,
                COLUMN_COMP_STAGE1ID,
                COLUMN_COMP_STAGE2ID,
                COLUMN_COMP_STAGE3ID,
                COLUMN_COMP_STAGE4ID
        };

        Competitor competitor = new Competitor();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_COMP_ID + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(compID)};

        Cursor cursor = db.query(TABLE_COMP, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
                competitor.setCompId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_ID))));
                competitor.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_CARNUM))));
                competitor.setDriver(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_DRIVER)));
                competitor.setCodriver(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_CODRIVER)));
                competitor.setStage1Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE1ID))));
                competitor.setStage2Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE2ID))));
                competitor.setStage3Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE3ID))));
                competitor.setStage4Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE4ID))));
        }
        cursor.close();
        db.close();

        return competitor;
    }

    @SuppressLint("Range")
    public Competitor getCompetitorByCarNum(int carNum) {
        String[] columns = {
                COLUMN_COMP_ID,
                COLUMN_COMP_CARNUM,
                COLUMN_COMP_DRIVER,
                COLUMN_COMP_CODRIVER,
                COLUMN_COMP_STAGE1ID,
                COLUMN_COMP_STAGE2ID,
                COLUMN_COMP_STAGE3ID,
                COLUMN_COMP_STAGE4ID
        };

        Competitor competitor = new Competitor();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_COMP_CARNUM + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(carNum)};

        Cursor cursor = db.query(TABLE_COMP, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            competitor.setCompId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_ID))));
            competitor.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_CARNUM))));
            competitor.setDriver(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_DRIVER)));
            competitor.setCodriver(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_CODRIVER)));
            competitor.setStage1Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE1ID))));
            competitor.setStage2Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE2ID))));
            competitor.setStage3Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE3ID))));
            competitor.setStage4Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE4ID))));
        }
        cursor.close();
        db.close();

        return competitor;
    }

    @SuppressLint("Range")
    public Competitor getCompetitorByDriver(String driver) {
        String[] columns = {
                COLUMN_COMP_ID,
                COLUMN_COMP_CARNUM,
                COLUMN_COMP_DRIVER,
                COLUMN_COMP_CODRIVER,
                COLUMN_COMP_STAGE1ID,
                COLUMN_COMP_STAGE2ID,
                COLUMN_COMP_STAGE3ID,
                COLUMN_COMP_STAGE4ID
        };

        Competitor competitor = new Competitor();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_COMP_DRIVER + " = ?";
        // selection argument
        String[] selectionArgs = {driver};

        Cursor cursor = db.query(TABLE_COMP, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            competitor.setCompId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_ID))));
            competitor.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_CARNUM))));
            competitor.setDriver(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_DRIVER)));
            competitor.setCodriver(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_CODRIVER)));
            competitor.setStage1Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE1ID))));
            competitor.setStage2Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE2ID))));
            competitor.setStage3Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE3ID))));
            competitor.setStage4Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE4ID))));
        }
        cursor.close();
        db.close();

        return competitor;
    }

    @SuppressLint("Range")
    public List<Competitor> getAllCompetitors() {
        String[] columns = {
                COLUMN_COMP_ID,
                COLUMN_COMP_CARNUM,
                COLUMN_COMP_DRIVER,
                COLUMN_COMP_CODRIVER,
                COLUMN_COMP_STAGE1ID,
                COLUMN_COMP_STAGE2ID,
                COLUMN_COMP_STAGE3ID,
                COLUMN_COMP_STAGE4ID
        };

        String sortOrder = COLUMN_COMP_DRIVER + " ASC";
        List<Competitor> competitorList = new ArrayList<Competitor>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMP, //Table to query
                columns,             //columns to return
                null,        //columns for the WHERE clause
                null,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                Competitor competitor = new Competitor();
                competitor.setCompId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_ID))));
                competitor.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_CARNUM))));
                competitor.setDriver(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_DRIVER)));
                competitor.setCodriver(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_CODRIVER)));
                competitor.setStage1Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE1ID))));
                competitor.setStage2Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE2ID))));
                competitor.setStage3Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE3ID))));
                competitor.setStage4Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COMP_STAGE4ID))));
                competitorList.add(competitor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return competitorList;
    }

    @SuppressLint("Range")
    public Cursor getCursor() {
        String[] columns = {
                COLUMN_COMP_ID,
                COLUMN_COMP_DRIVER,
                COLUMN_COMP_CARNUM
        };

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMP, //Table to query
                columns,             //columns to return
                null,        //columns for the WHERE clause
                null,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        return cursor;
    }

    public void updateCompetitor(Competitor competitor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMP_CARNUM, competitor.getCarNum());
        values.put(COLUMN_COMP_DRIVER, competitor.getDriver());
        values.put(COLUMN_COMP_CODRIVER, competitor.getCodriver());
        values.put(COLUMN_COMP_STAGE1ID, competitor.getStage1Id());
        values.put(COLUMN_COMP_STAGE2ID, competitor.getStage2Id());
        values.put(COLUMN_COMP_STAGE3ID, competitor.getStage3Id());
        values.put(COLUMN_COMP_STAGE4ID, competitor.getStage4Id());
        // updating row
        db.update(TABLE_COMP, values, COLUMN_COMP_ID + " = ?",
                new String[]{String.valueOf(competitor.getCompId())});
        db.close();
    }

    public void deleteCompetitor(Competitor competitor) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by username
        db.delete(TABLE_COMP, COLUMN_COMP_ID + " = ?",
                new String[]{String.valueOf(competitor.getCompId())});
        db.close();
    }

    public boolean checkCompetitor(int carNum) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_COMP_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_COMP_CARNUM + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(carNum)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_COMP, //Table to query
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

    public boolean checkCompetitor(String driver) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_COMP_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_COMP_DRIVER + " = ?";
        // selection argument
        String[] selectionArgs = {driver};
        // query user table with condition

        Cursor cursor = db.query(TABLE_COMP, //Table to query
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

    public boolean checkCompetitor(String carNum, String driver, String codriver) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_COMP_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_COMP_CARNUM + " = ?" + " AND " + COLUMN_COMP_DRIVER + " = ?" + " AND " + COLUMN_COMP_CODRIVER + " = ?";
        // selection arguments
        String[] selectionArgs = {carNum, driver, codriver};
        // query user table with conditions

        Cursor cursor = db.query(TABLE_COMP, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
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
