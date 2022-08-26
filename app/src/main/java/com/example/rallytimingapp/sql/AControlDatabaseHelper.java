package com.example.rallytimingapp.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rallytimingapp.model.AControl;

import java.util.ArrayList;
import java.util.List;

public class AControlDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "AControlManager.db";

    // User table name
    private static final String TABLE_ACONTROL = "acontrol";

    // User Table Columns names
    private static final String COLUMN_AC_ID = "acontrol_id";
    private static final String COLUMN_AC_SO = "acontrol_start_order";
    private static final String COLUMN_AC_STAGE = "acontrol_stage";
    private static final String COLUMN_AC_CARNUM = "acontrol_carNum";
    private static final String COLUMN_AC_STAGE1ID = "acontrol_stage1_id";
    private static final String COLUMN_AC_STAGE2ID = "acontrol_stage2_id";

    // Create table SQL query
    private String CREATE_AC_TABLE = "CREATE TABLE " + TABLE_ACONTROL + "("
            + COLUMN_AC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_AC_SO + " INTEGER,"
            + COLUMN_AC_STAGE + " INTEGER," + COLUMN_AC_CARNUM + " INTEGER," + COLUMN_AC_STAGE1ID
            + " INTEGER," + COLUMN_AC_STAGE2ID + " INTEGER" + ")";

    // Drop table SQL query
    private String DROP_AC_TABLE = "DROP TABLE IF EXISTS " + TABLE_ACONTROL;

    public AControlDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_AC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_AC_TABLE);
        onCreate(db);
    }

    public void empty() {
        List<AControl> aControlList = getAllAControl();
        for (int i = 0; i < aControlList.size(); i++) {
            deleteAControl(aControlList.get(i));
        }
    }

    public void addAControl(AControl aControl) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_AC_SO, aControl.getStartOrder());
        values.put(COLUMN_AC_STAGE, aControl.getStage());
        values.put(COLUMN_AC_CARNUM, aControl.getCarNum());
        values.put(COLUMN_AC_STAGE1ID, aControl.getStage1ID());
        values.put(COLUMN_AC_STAGE2ID, aControl.getStage2ID());

        // Inserting Row
        db.insert(TABLE_ACONTROL, null, values);
        db.close();
    }

    public int getStartOrder(int stage, int carNum) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_AC_SO
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_AC_STAGE + " = ?" + " AND " + COLUMN_AC_CARNUM + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stage), String.valueOf(carNum)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_ACONTROL, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        int startOrder = 0;
        if (cursor.moveToFirst()) {
            startOrder = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return startOrder;
    }

    public int getCarNum(int stageNum, int startOrder) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_AC_CARNUM
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_AC_STAGE + " = ?" + " AND " + COLUMN_AC_SO + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stageNum), String.valueOf(startOrder)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_ACONTROL, //Table to query
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

    public int getAControlID(int stage, int carNum) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_AC_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_AC_STAGE + " = ?" + " AND " + COLUMN_AC_CARNUM + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stage), String.valueOf(carNum)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_ACONTROL, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        int aControlID = 0;
        if (cursor.moveToFirst()) {
            aControlID = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return aControlID;
    }

    @SuppressLint("Range")
    public AControl getAControl(int aControlID) {
        String[] columns = {
                COLUMN_AC_ID,
                COLUMN_AC_SO,
                COLUMN_AC_STAGE,
                COLUMN_AC_CARNUM,
                COLUMN_AC_STAGE1ID,
                COLUMN_AC_STAGE2ID
        };

        AControl aControl = new AControl();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_AC_ID + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(aControlID)};

        Cursor cursor = db.query(TABLE_ACONTROL, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            aControl.setAControlID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_ID))));
            aControl.setStartOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_SO))));
            aControl.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE))));
            aControl.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_CARNUM))));
            aControl.setStage1ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE1ID))));
            aControl.setStage2ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE2ID))));
        }
        cursor.close();
        db.close();

        return aControl;
    }

    @SuppressLint("Range")
    public AControl getAControl(int stageNum, int startOrder) {
        String[] columns = {
                COLUMN_AC_ID,
                COLUMN_AC_SO,
                COLUMN_AC_STAGE,
                COLUMN_AC_CARNUM,
                COLUMN_AC_STAGE1ID,
                COLUMN_AC_STAGE2ID
        };

        AControl aControl = new AControl();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_AC_STAGE + " = ?" + " AND " + COLUMN_AC_SO + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stageNum), String.valueOf(startOrder)};

        Cursor cursor = db.query(TABLE_ACONTROL, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            aControl.setAControlID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_ID))));
            aControl.setStartOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_SO))));
            aControl.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE))));
            aControl.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_CARNUM))));
            aControl.setStage1ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE1ID))));
            aControl.setStage2ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE2ID))));
        }
        cursor.close();
        db.close();

        return aControl;
    }

    @SuppressLint("Range")
    public List<AControl> getAllAControl() {
        String[] columns = {
                COLUMN_AC_ID,
                COLUMN_AC_SO,
                COLUMN_AC_STAGE,
                COLUMN_AC_CARNUM,
                COLUMN_AC_STAGE1ID,
                COLUMN_AC_STAGE2ID
        };

        String sortOrder = COLUMN_AC_ID + " ASC";
        List<AControl> aControlList = new ArrayList<AControl>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACONTROL, //Table to query
                columns,             //columns to return
                null,        //columns for the WHERE clause
                null,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                AControl aControl = new AControl();
                aControl.setAControlID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_ID))));
                aControl.setStartOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_SO))));
                aControl.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE))));
                aControl.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_CARNUM))));
                aControl.setStage1ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE1ID))));
                aControl.setStage2ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE2ID))));
                aControlList.add(aControl);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return aControlList;
    }

    public void updateAControl(AControl aControl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AC_SO, aControl.getStartOrder());
        values.put(COLUMN_AC_STAGE, aControl.getStage());
        values.put(COLUMN_AC_CARNUM, aControl.getCarNum());
        values.put(COLUMN_AC_STAGE1ID, aControl.getStage1ID());
        values.put(COLUMN_AC_STAGE2ID, aControl.getStage2ID());
        // updating row
        db.update(TABLE_ACONTROL, values, COLUMN_AC_ID + " = ?",
                new String[]{String.valueOf(aControl.getAControlID())});
        db.close();
    }

    public void deleteAControl(AControl aControl) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by username
        db.delete(TABLE_ACONTROL, COLUMN_AC_ID + " = ?",
                new String[]{String.valueOf(aControl.getAControlID())});
        db.close();
    }

    @SuppressLint("Range")
    public List<AControl> getStage(int stageNum) {
        String[] columns = {
                COLUMN_AC_ID,
                COLUMN_AC_SO,
                COLUMN_AC_STAGE,
                COLUMN_AC_CARNUM,
                COLUMN_AC_STAGE1ID,
                COLUMN_AC_STAGE2ID
        };

        String sortOrder = COLUMN_AC_SO + " ASC";
        // selection criteria
        String selection = COLUMN_AC_STAGE + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stageNum)};
        List<AControl> aControlList = new ArrayList<AControl>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACONTROL, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                AControl aControl = new AControl();
                aControl.setAControlID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_ID))));
                aControl.setStartOrder(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_SO))));
                aControl.setStage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE))));
                aControl.setCarNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_CARNUM))));
                aControl.setStage1ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE1ID))));
                aControl.setStage2ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AC_STAGE2ID))));
                aControlList.add(aControl);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return aControlList;
    }

    @SuppressLint("Range")
    public int getCurrStartOrder(int stage) {
        String[] columns = {
                COLUMN_AC_SO
        };

        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = COLUMN_AC_SO + " ASC";
        // selection criteria
        String selection = COLUMN_AC_STAGE + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stage)};

        Cursor cursor = db.query(TABLE_ACONTROL, //Table to query
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

    public boolean checkAControl(int stage, int carNum) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_AC_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_AC_STAGE + " = ?" + " AND " + COLUMN_AC_CARNUM + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(stage), String.valueOf(carNum)};
        // query user table with condition

        Cursor cursor = db.query(TABLE_ACONTROL, //Table to query
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
