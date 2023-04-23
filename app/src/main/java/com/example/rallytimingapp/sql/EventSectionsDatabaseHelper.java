package com.example.rallytimingapp.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rallytimingapp.model.EventSections;

import java.util.ArrayList;
import java.util.List;

public class EventSectionsDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "EventSectionsManager.db";

    // Event table name
    private static final String TABLE_EVENT_SECTIONS = "event_sections";

    // Event Table Columns names
    private static final String COLUMN_EVENT_SECTION_ID = "event_section_id";
    private static final String COLUMN_SECTION_IS_STAGE = "section_is_stage";
    private static final String COLUMN_SECTION_HAS_START_ORDER = "section_has_start_order";
    private static final String COLUMN_SECTION_HAS_PROV_START = "section_has_prov_start";
    private static final String COLUMN_SECTION_CURRENT_TC = "section_current_tc";
    private static final String COLUMN_SECTION_NEXT_TC = "section_next_tc";
    private static final String COLUMN_SECTION_TC_NAME = "section_tc_name";
    private static final String COLUMN_SECTION_STAGE_DISTANCE = "section_stage_distance";
    private static final String COLUMN_SECTION_TC_DISTANCE = "section_tc_distance";
    private static final String COLUMN_SECTION_AVERAGE_SPEED = "section_average_speed";
    private static final String COLUMN_SECTION_TARGET_TIME_HOURS = "section_target_time_hours";
    private static final String COLUMN_SECTION_TARGET_TIME_MINUTES = "section_target_time_minutes";

    // Create table SQL query
    private String CREATE_EVENT_SECTIONS_TABLE = "CREATE TABLE " + TABLE_EVENT_SECTIONS + "("
            + COLUMN_EVENT_SECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SECTION_IS_STAGE + " BOOLEAN,"
            + COLUMN_SECTION_HAS_START_ORDER + " BOOLEAN," + COLUMN_SECTION_HAS_PROV_START + " BOOLEAN,"
            + COLUMN_SECTION_CURRENT_TC + " TEXT," + COLUMN_SECTION_NEXT_TC + " TEXT," + COLUMN_SECTION_TC_NAME + " TEXT,"
            + COLUMN_SECTION_STAGE_DISTANCE + " TEXT," + COLUMN_SECTION_TC_DISTANCE + " TEXT,"
            + COLUMN_SECTION_AVERAGE_SPEED + " TEXT," + COLUMN_SECTION_TARGET_TIME_HOURS + " TEXT,"
            + COLUMN_SECTION_TARGET_TIME_MINUTES + " TEXT" + ")";

    // Drop table SQL query
    private String DROP_EVENT_SECTIONS_TABLE = "DROP TABLE IF EXISTS " + TABLE_EVENT_SECTIONS;

    public EventSectionsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT_SECTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EVENT_SECTIONS_TABLE);
        onCreate(db);
    }

    // Method to add an entry to the database
    public void addSection(EventSections section) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SECTION_IS_STAGE, section.getIsStage());
        values.put(COLUMN_SECTION_HAS_START_ORDER, section.getHasStartOrder());
        values.put(COLUMN_SECTION_HAS_PROV_START, section.getHasProvStart());
        values.put(COLUMN_SECTION_CURRENT_TC, section.getCurrentTC());
        values.put(COLUMN_SECTION_NEXT_TC, section.getNextTC());
        values.put(COLUMN_SECTION_TC_NAME, section.getTCName());
        values.put(COLUMN_SECTION_STAGE_DISTANCE, section.getStageDistance());
        values.put(COLUMN_SECTION_TC_DISTANCE, section.getTCDistance());
        values.put(COLUMN_SECTION_AVERAGE_SPEED, section.getAverageSpeed());
        values.put(COLUMN_SECTION_TARGET_TIME_HOURS, section.getTargetTimeHours());
        values.put(COLUMN_SECTION_TARGET_TIME_MINUTES, section.getTargetTimeMinutes());

        // Inserting Row
        db.insert(TABLE_EVENT_SECTIONS, null, values);
        db.close();
    }

    // Method to remove all entries from the database
    public void empty() {
        List<EventSections> sectionsList = getAllSections();
        for (int i = 0; i < sectionsList.size(); i++) {
            deleteSection(sectionsList.get(i));
        }
    }

    // Method to return the entry with the given ID
    @SuppressLint("Range")
    public EventSections getSection(int sectionId) {
        String[] columns = {
                COLUMN_EVENT_SECTION_ID,
                COLUMN_SECTION_IS_STAGE,
                COLUMN_SECTION_HAS_START_ORDER,
                COLUMN_SECTION_HAS_PROV_START,
                COLUMN_SECTION_CURRENT_TC,
                COLUMN_SECTION_NEXT_TC,
                COLUMN_SECTION_TC_NAME,
                COLUMN_SECTION_STAGE_DISTANCE,
                COLUMN_SECTION_TC_DISTANCE,
                COLUMN_SECTION_AVERAGE_SPEED,
                COLUMN_SECTION_TARGET_TIME_HOURS,
                COLUMN_SECTION_TARGET_TIME_MINUTES
        };

        EventSections section = new EventSections();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_EVENT_SECTION_ID + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(sectionId)};

        Cursor cursor = db.query(TABLE_EVENT_SECTIONS, //Table to query
                columns,             //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                null);         //The sort order

        if (cursor.moveToFirst()) {
            section.setSectionId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_SECTION_ID))));
            section.setIsStage(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_IS_STAGE))));
            section.setHasStartOrder(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_HAS_START_ORDER))));
            section.setHasProvStart(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_HAS_PROV_START))));
            section.setCurrentTC(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_CURRENT_TC)));
            section.setNextTC(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_NEXT_TC)));
            section.setTCName(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TC_NAME)));
            section.setStageDistance(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_STAGE_DISTANCE)));
            section.setTCDistance(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TC_DISTANCE)));
            section.setAverageSpeed(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_AVERAGE_SPEED)));
            section.setTargetTimeHours(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TARGET_TIME_HOURS)));
            section.setTargetTimeMinutes(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TARGET_TIME_MINUTES)));
        }
        cursor.close();
        db.close();

        return section;
    }

    // Method to return a list of all entries in the database
    @SuppressLint("Range")
    public List<EventSections> getAllSections() {
        String[] columns = {
                COLUMN_EVENT_SECTION_ID,
                COLUMN_SECTION_IS_STAGE,
                COLUMN_SECTION_HAS_START_ORDER,
                COLUMN_SECTION_HAS_PROV_START,
                COLUMN_SECTION_CURRENT_TC,
                COLUMN_SECTION_NEXT_TC,
                COLUMN_SECTION_TC_NAME,
                COLUMN_SECTION_STAGE_DISTANCE,
                COLUMN_SECTION_TC_DISTANCE,
                COLUMN_SECTION_AVERAGE_SPEED,
                COLUMN_SECTION_TARGET_TIME_HOURS,
                COLUMN_SECTION_TARGET_TIME_MINUTES
        };

        String sortOrder = COLUMN_EVENT_SECTION_ID + " ASC";
        List<EventSections> sectionsList = new ArrayList<EventSections>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_SECTIONS, //Table to query
                columns,             //columns to return
                null,        //columns for the WHERE clause
                null,     //The values for the WHERE clause
                null,        //group the rows
                null,         //filter by row groups
                sortOrder);         //The sort order

        if (cursor.moveToFirst()) {
            do {
                EventSections section = new EventSections();
                section.setSectionId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_SECTION_ID))));
                section.setIsStage(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_IS_STAGE))));
                section.setHasStartOrder(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_HAS_START_ORDER))));
                section.setHasProvStart(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_HAS_PROV_START))));
                section.setCurrentTC(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_CURRENT_TC)));
                section.setNextTC(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_NEXT_TC)));
                section.setTCName(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TC_NAME)));
                section.setStageDistance(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_STAGE_DISTANCE)));
                section.setTCDistance(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TC_DISTANCE)));
                section.setAverageSpeed(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_AVERAGE_SPEED)));
                section.setTargetTimeHours(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TARGET_TIME_HOURS)));
                section.setTargetTimeMinutes(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TARGET_TIME_MINUTES)));
                sectionsList.add(section);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return sectionsList;
    }

    // Method to update an entry in the database
    public void updateSection(EventSections section) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SECTION_IS_STAGE, section.getIsStage());
        values.put(COLUMN_SECTION_HAS_START_ORDER, section.getHasStartOrder());
        values.put(COLUMN_SECTION_HAS_PROV_START, section.getHasProvStart());
        values.put(COLUMN_SECTION_CURRENT_TC, section.getCurrentTC());
        values.put(COLUMN_SECTION_NEXT_TC, section.getNextTC());
        values.put(COLUMN_SECTION_TC_NAME, section.getTCName());
        values.put(COLUMN_SECTION_STAGE_DISTANCE, section.getStageDistance());
        values.put(COLUMN_SECTION_TC_DISTANCE, section.getTCDistance());
        values.put(COLUMN_SECTION_AVERAGE_SPEED, section.getAverageSpeed());
        values.put(COLUMN_SECTION_TARGET_TIME_HOURS, section.getTargetTimeHours());
        values.put(COLUMN_SECTION_TARGET_TIME_MINUTES, section.getTargetTimeMinutes());
        // updating row
        db.update(TABLE_EVENT_SECTIONS, values, COLUMN_EVENT_SECTION_ID + " = ?",
                new String[]{String.valueOf(section.getSectionId())});
        db.close();
    }

    // Method to delete an entry in the database
    public void deleteSection(EventSections section) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENT_SECTIONS, COLUMN_EVENT_SECTION_ID + " = ?",
                new String[]{String.valueOf(section.getSectionId())});
        db.close();
    }

    // Method to check if an entry with the given current TC exists
    public boolean checkStage(String currentTC) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_EVENT_SECTION_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_SECTION_CURRENT_TC + " = ?";
        // selection arguments
        String[] selectionArgs = {String.valueOf(currentTC)};
        // query user table with conditions

        Cursor cursor = db.query(TABLE_EVENT_SECTIONS, //Table to query
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

    // Method to return the id (index) of the most recently added database entry
    @SuppressLint("Range")
    public int getLatestIndex() {
        String[] columns = {
                COLUMN_EVENT_SECTION_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = COLUMN_EVENT_SECTION_ID + " ASC";

        Cursor cursor = db.query(TABLE_EVENT_SECTIONS, //Table to query
                columns,             //columns to return
                null,        //columns for the WHERE clause
                null,     //The values for the WHERE clause
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
}
