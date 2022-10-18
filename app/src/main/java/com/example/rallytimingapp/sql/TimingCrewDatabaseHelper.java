package com.example.rallytimingapp.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rallytimingapp.model.TimingCrew;

import java.util.ArrayList;
import java.util.List;

public class TimingCrewDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TimingCrewManager.db";

    // Timing Crew table name
    private static final String TABLE_TIMING_CREW = "timing_crew";

    // Timing Crew Table Columns names
    private static final String COLUMN_CREW_ID = "crew_id";
    private static final String COLUMN_CREW_POSITION = "crew_position";
    private static final String COLUMN_CREW_POSTCHIEF = "crew_post_chief";
    private static final String COLUMN_CREW_PHONE = "crew_post_chief_phone";

    // Create table SQL query
    private String CREATE_CREW_TABLE = "CREATE TABLE " + TABLE_TIMING_CREW + "("
            + COLUMN_CREW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CREW_POSITION + " TEXT,"
            + COLUMN_CREW_POSTCHIEF + " TEXT," + COLUMN_CREW_PHONE + " TEXT" + ")";

    // Drop table SQL query
    private String DROP_CREW_TABLE = "DROP TABLE IF EXISTS " + TABLE_TIMING_CREW;

    public TimingCrewDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CREW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_CREW_TABLE);
        onCreate(db);
    }

    // Method to add an entry to the database
    public void addTimingCrew(TimingCrew crew) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CREW_POSITION, crew.getPosition());
        values.put(COLUMN_CREW_POSTCHIEF, crew.getPostChief());
        values.put(COLUMN_CREW_PHONE, crew.getPostChiefPhone());

        // Inserting Row
        db.insert(TABLE_TIMING_CREW, null, values);
        db.close();
    }

    // Method to return the ID of the entry with the given position and post chief name
    public int getCrewId(String position, String postChief) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_CREW_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_CREW_POSITION + " = ?" + " AND " + COLUMN_CREW_POSTCHIEF + " = ?";
        // selection argument
        String[] selectionArgs = {position, postChief};
        // query user table with condition

        Cursor cursor = db.query(TABLE_TIMING_CREW, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        int crewID = 0;
        if (cursor.moveToFirst()) {
            crewID = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return crewID;
    }

    // Method to remove all entries from the database
    public void empty() {
        List<TimingCrew> crewList = getAllTimingCrews();
        for (int i = 0; i < crewList.size(); i++) {
            deleteTimingCrew(crewList.get(i));
        }
    }

    // Method to return the entry with the given ID
    @SuppressLint("Range")
    public TimingCrew getTimingCrewByID(int crewID) {
        String[] columns = {
                COLUMN_CREW_ID,
                COLUMN_CREW_POSITION,
                COLUMN_CREW_POSTCHIEF,
                COLUMN_CREW_PHONE
        };

        TimingCrew crew = new TimingCrew();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_CREW_ID + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(crewID)};

        Cursor cursor = db.query(TABLE_TIMING_CREW, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            crew.setCrewId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_ID))));
            crew.setPosition(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_POSITION)));
            crew.setPostChief(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_POSTCHIEF)));
            crew.setPostChiefPhone(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_PHONE)));
        }
        cursor.close();
        db.close();

        return crew;
    }

    // Method to return the entry with the given position and post chief name
    @SuppressLint("Range")
    public TimingCrew getTimingCrewByPostChief(String role, String postChief) {
        String[] columns = {
                COLUMN_CREW_ID,
                COLUMN_CREW_POSITION,
                COLUMN_CREW_POSTCHIEF,
                COLUMN_CREW_PHONE
        };

        TimingCrew crew = new TimingCrew();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_CREW_POSITION + " = ?" + " AND " + COLUMN_CREW_POSTCHIEF + " = ?";
        // selection argument
        String[] selectionArgs = {role, postChief};

        Cursor cursor = db.query(TABLE_TIMING_CREW, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            crew.setCrewId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_ID))));
            crew.setPosition(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_POSITION)));
            crew.setPostChief(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_POSTCHIEF)));
            crew.setPostChiefPhone(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_PHONE)));
        }
        cursor.close();
        db.close();

        return crew;
    }

    // Method to return a list of all entries in the database
    @SuppressLint("Range")
    public List<TimingCrew> getAllTimingCrews() {
        String[] columns = {
                COLUMN_CREW_ID,
                COLUMN_CREW_POSITION,
                COLUMN_CREW_POSTCHIEF,
                COLUMN_CREW_PHONE
        };

        String sortOrder = COLUMN_CREW_ID + " ASC";
        List<TimingCrew> crewList = new ArrayList<TimingCrew>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TIMING_CREW, //Table to query
                columns,             //columns to return
                null,        //columns for the WHERE clause
                null,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                TimingCrew crew = new TimingCrew();
                crew.setCrewId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_ID))));
                crew.setPosition(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_POSITION)));
                crew.setPostChief(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_POSTCHIEF)));
                crew.setPostChiefPhone(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_PHONE)));
                crewList.add(crew);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return crewList;
    }

    // Method to return a list of all entries with the given position
    @SuppressLint("Range")
    public List<TimingCrew> getTimingCrewsByPosition(String position) {
        String[] columns = {
                COLUMN_CREW_ID,
                COLUMN_CREW_POSITION,
                COLUMN_CREW_POSTCHIEF,
                COLUMN_CREW_PHONE
        };

        String sortOrder = COLUMN_CREW_POSTCHIEF + " ASC";
        List<TimingCrew> crewList = new ArrayList<TimingCrew>();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_CREW_POSITION + " = ?";
        // selection argument
        String[] selectionArgs = {position};

        Cursor cursor = db.query(TABLE_TIMING_CREW, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                TimingCrew crew = new TimingCrew();
                crew.setCrewId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_ID))));
                crew.setPosition(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_POSITION)));
                crew.setPostChief(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_POSTCHIEF)));
                crew.setPostChiefPhone(cursor.getString(cursor.getColumnIndex(COLUMN_CREW_PHONE)));
                crewList.add(crew);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return crewList;
    }

    // Method to update an entry in the database
    public void updateTimingCrew(TimingCrew crew) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CREW_POSITION, crew.getPosition());
        values.put(COLUMN_CREW_POSTCHIEF, crew.getPostChief());
        values.put(COLUMN_CREW_PHONE, crew.getPostChiefPhone());
        // updating row
        db.update(TABLE_TIMING_CREW, values, COLUMN_CREW_ID + " = ?",
                new String[]{String.valueOf(crew.getCrewId())});
        db.close();
    }

    // Method to delete an entry in the database
    public void deleteTimingCrew(TimingCrew timingCrew) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIMING_CREW, COLUMN_CREW_ID + " = ?",
                new String[]{String.valueOf(timingCrew.getCrewId())});
        db.close();
    }

    // Method to check if an entry with the given position and post chief name exists
    public boolean checkTimingCrew(String position, String postChief) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_CREW_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_CREW_POSITION + " = ?" + " AND " + COLUMN_CREW_POSTCHIEF + " = ?";
        // selection argument
        String[] selectionArgs = {position, postChief};
        // query user table with condition

        Cursor cursor = db.query(TABLE_TIMING_CREW, //Table to query
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
