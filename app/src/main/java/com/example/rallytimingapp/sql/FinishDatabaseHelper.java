package com.example.rallytimingapp.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rallytimingapp.model.Finish;

import java.util.ArrayList;
import java.util.List;

public class FinishDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FinishManager.db";

    // Finish table name
    private static final String TABLE_FINISH = "finish";

    // FInish Table Columns names
    private static final String COLUMN_FINISH_ID = "finish_id";
    private static final String COLUMN_FINISH_ORDER = "finish_order";
    private static final String COLUMN_FINISH_STAGE = "finish_stage";
    private static final String COLUMN_FINISH_CARNUM = "finish_carNum";
    private static final String COLUMN_FINISH_STAGEID = "finish_stageID";

    // Create table SQL query
    private String CREATE_FINISH_TABLE = "CREATE TABLE " + TABLE_FINISH + "("
            + COLUMN_FINISH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FINISH_ORDER + " INTEGER,"
            + COLUMN_FINISH_STAGE + " INTEGER," + COLUMN_FINISH_CARNUM + " INTEGER," + COLUMN_FINISH_STAGEID
            + " INTEGER" + ")";

    // Drop table SQL query
    private String DROP_FINISH_TABLE = "DROP TABLE IF EXISTS " + TABLE_FINISH;

    public FinishDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FINISH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_FINISH_TABLE);
        onCreate(db);
    }

    // Method to remove all entries from the database
    public void empty() {
        List<Finish> finishList = getAllFinish();
        for (int i = 0; i < finishList.size(); i++) {
            deleteFinish(finishList.get(i));
        }
    }

    // Method to add an entry to the database
    public void addFinish(Finish finish) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FINISH_ORDER, finish.getFinishOrder());
        values.put(COLUMN_FINISH_STAGE, finish.getStage());
        values.put(COLUMN_FINISH_CARNUM, finish.getCarNum());
        values.put(COLUMN_FINISH_STAGEID, finish.getStageID());

        // Inserting Row
        db.insert(TABLE_FINISH, null, values);
        db.close();
    }

    // Method to return the car number of the entry with the given stage number and finish order
    public int getCarNum(int stageNum, int finishOrder) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_FINISH_CARNUM
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_FINISH_STAGE + " = ?" + " AND " + COLUMN_FINISH_ORDER + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stageNum), String.valueOf(finishOrder)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_FINISH, //Table to query
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

    // Method to return the ID of the entry with the given stage and car number
    public int getFinishID(int stage, int carNum) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_FINISH_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_FINISH_STAGE + " = ?" + " AND " + COLUMN_FINISH_CARNUM + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stage), String.valueOf(carNum)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_FINISH, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        int finishID = 0;
        if (cursor.moveToFirst()) {
            finishID = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return finishID;
    }

    // Method to return the entry with the given ID
    @SuppressLint("Range")
    public Finish getFinish(int finishID) {
        String[] columns = {
                COLUMN_FINISH_ID,
                COLUMN_FINISH_ORDER,
                COLUMN_FINISH_STAGE,
                COLUMN_FINISH_CARNUM,
                COLUMN_FINISH_STAGEID
        };

        Finish finish = new Finish();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_FINISH_ID + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(finishID)};

        Cursor cursor = db.query(TABLE_FINISH, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            finish.setFinishID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_ID))));
            finish.setFinishOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_ORDER))));
            finish.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_STAGE))));
            finish.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_CARNUM))));
            finish.setStageID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_STAGEID))));
        }
        cursor.close();
        db.close();

        return finish;
    }

    // Method to return the entry with the given stage number and finish order
    @SuppressLint("Range")
    public Finish getFinish(int stageNum, int finishOrder) {
        String[] columns = {
                COLUMN_FINISH_ID,
                COLUMN_FINISH_ORDER,
                COLUMN_FINISH_STAGE,
                COLUMN_FINISH_CARNUM,
                COLUMN_FINISH_STAGEID
        };

        Finish finish = new Finish();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_FINISH_STAGE + " = ?" + " AND " + COLUMN_FINISH_ORDER + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stageNum), String.valueOf(finishOrder)};

        Cursor cursor = db.query(TABLE_FINISH, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            finish.setFinishID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_ID))));
            finish.setFinishOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_ORDER))));
            finish.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_STAGE))));
            finish.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_CARNUM))));
            finish.setStageID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_STAGEID))));
        }
        cursor.close();
        db.close();

        return finish;
    }

    // Method to return a list of all the entries in the database
    @SuppressLint("Range")
    public List<Finish> getAllFinish() {
        String[] columns = {
                COLUMN_FINISH_ID,
                COLUMN_FINISH_ORDER,
                COLUMN_FINISH_STAGE,
                COLUMN_FINISH_CARNUM,
                COLUMN_FINISH_STAGEID
        };

        String sortOrder = COLUMN_FINISH_ID + " ASC";
        List<Finish> finishList = new ArrayList<Finish>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FINISH, //Table to query
                columns,             //columns to return
                null,        //columns for the WHERE clause
                null,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                Finish finish = new Finish();
                finish.setFinishID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_ID))));
                finish.setFinishOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_ORDER))));
                finish.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_STAGE))));
                finish.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_CARNUM))));
                finish.setStageID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_STAGEID))));
                finishList.add(finish);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return finishList;
    }

    // Method to update an entry in the database
    public void updateFinish(Finish finish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FINISH_ORDER, finish.getFinishOrder());
        values.put(COLUMN_FINISH_STAGE, finish.getStage());
        values.put(COLUMN_FINISH_CARNUM, finish.getCarNum());
        values.put(COLUMN_FINISH_STAGEID, finish.getStageID());
        // updating row
        db.update(TABLE_FINISH, values, COLUMN_FINISH_ID + " = ?",
                new String[]{String.valueOf(finish.getFinishID())});
        db.close();
    }

    // Method to delete an entry in the database
    public void deleteFinish(Finish finish) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FINISH, COLUMN_FINISH_ID + " = ?",
                new String[]{String.valueOf(finish.getFinishID())});
        db.close();
    }

    // Method to return a list of all entries with the given stage number
    @SuppressLint("Range")
    public List<Finish> getStage(int stageNum) {
        String[] columns = {
                COLUMN_FINISH_ID,
                COLUMN_FINISH_ORDER,
                COLUMN_FINISH_STAGE,
                COLUMN_FINISH_CARNUM,
                COLUMN_FINISH_STAGEID
        };

        String sortOrder = COLUMN_FINISH_ORDER + " ASC";
        // selection criteria
        String selection = COLUMN_FINISH_STAGE + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stageNum)};
        List<Finish> finishList = new ArrayList<Finish>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FINISH, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                Finish finish = new Finish();
                finish.setFinishID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_ID))));
                finish.setFinishOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_ORDER))));
                finish.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_STAGE))));
                finish.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_CARNUM))));
                finish.setStageID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_STAGEID))));
                finishList.add(finish);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return finishList;
    }

    // Method to return the finish order of the most recently added database
    // entry with the given stage number
    @SuppressLint("Range")
    public int getCurrFinishOrder(int stage) {
        String[] columns = {
                COLUMN_FINISH_ORDER
        };

        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = COLUMN_FINISH_ORDER + " ASC";
        // selection criteria
        String selection = COLUMN_FINISH_STAGE + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stage)};

        Cursor cursor = db.query(TABLE_FINISH, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        int currFO = 0;

        if (cursor.moveToLast()) {
            currFO = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return currFO;
    }

    // Method to check if an entry exists with the given stage and car number
    public boolean checkFinish(int stage, int carNum) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_FINISH_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_FINISH_STAGE + " = ?" + " AND " + COLUMN_FINISH_CARNUM + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stage), String.valueOf(carNum)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_FINISH, //Table to query
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
