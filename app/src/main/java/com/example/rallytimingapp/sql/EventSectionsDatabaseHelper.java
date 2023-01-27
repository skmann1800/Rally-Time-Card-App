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
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "EventSectionsManager.db";

    // Event table name
    private static final String TABLE_EVENT_SECTIONS = "event_sections";

    // Event Table Columns names
    private static final String COLUMN_EVENT_SECTION_ID = "event_section_id";
    private static final String COLUMN_SECTION_BLUE1 = "section_blue1";
    private static final String COLUMN_SECTION_BLUE2 = "section_blue2";
    private static final String COLUMN_SECTION_BLUE3 = "section_blue3";
    private static final String COLUMN_SECTION_SECTION_LABEL1 = "section_section_label1";
    private static final String COLUMN_SECTION_SECTION_LABEL2 = "section_section_label2";
    private static final String COLUMN_SECTION_PROV_START_LABEL = "section_prov_start_label";
    private static final String COLUMN_SECTION_ACTUAL_START_LABEL = "section_actual_start_label";
    private static final String COLUMN_SECTION_IS_STAGE = "section_is_stage";
    private static final String COLUMN_SECTION_TIME_TAKEN_LABEL = "section_time_taken_label";
    private static final String COLUMN_SECTION_HAS_START_ORDER = "section_has_start_order";
    private static final String COLUMN_SECTION_TARGET_TIME_HOURS = "section_target_time_hours";
    private static final String COLUMN_SECTION_TARGET_TIME_MINUTES = "section_target_time_minutes";
    private static final String COLUMN_SECTION_NEXT_TC_LABEL = "section_next_tc_label";

    // Create table SQL query
    private String CREATE_EVENT_SECTIONS_TABLE = "CREATE TABLE " + TABLE_EVENT_SECTIONS + "("
            + COLUMN_EVENT_SECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SECTION_BLUE1 + " TEXT,"
            + COLUMN_SECTION_BLUE2 + " TEXT," + COLUMN_SECTION_BLUE3 + " TEXT," + COLUMN_SECTION_SECTION_LABEL1 + " TEXT,"
            + COLUMN_SECTION_SECTION_LABEL2 + " TEXT," + COLUMN_SECTION_PROV_START_LABEL + " TEXT,"
            + COLUMN_SECTION_ACTUAL_START_LABEL + " TEXT," + COLUMN_SECTION_IS_STAGE + " BOOLEAN," 
            + COLUMN_SECTION_TIME_TAKEN_LABEL + " TEXT," + COLUMN_SECTION_HAS_START_ORDER + " BOOLEAN,"
            + COLUMN_SECTION_TARGET_TIME_HOURS + " TEXT," + COLUMN_SECTION_TARGET_TIME_MINUTES + " TEXT," 
            + COLUMN_SECTION_NEXT_TC_LABEL + " TEXT" + ")";

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
        values.put(COLUMN_SECTION_BLUE1, section.getBlue1Text());
        values.put(COLUMN_SECTION_BLUE2, section.getBlue2Text());
        values.put(COLUMN_SECTION_BLUE3, section.getBlue3Text());
        values.put(COLUMN_SECTION_SECTION_LABEL1, section.getSectionLabel1());
        values.put(COLUMN_SECTION_SECTION_LABEL2, section.getSectionLabel2());
        values.put(COLUMN_SECTION_PROV_START_LABEL, section.getProvStartLabel());
        values.put(COLUMN_SECTION_ACTUAL_START_LABEL, section.getActualStartLabel());
        values.put(COLUMN_SECTION_IS_STAGE, section.getIsStage());
        values.put(COLUMN_SECTION_TIME_TAKEN_LABEL, section.getTimeTakenLabel());
        values.put(COLUMN_SECTION_HAS_START_ORDER, section.getHasStartOrder());
        values.put(COLUMN_SECTION_TARGET_TIME_HOURS, section.getTargetTimeHours());
        values.put(COLUMN_SECTION_TARGET_TIME_MINUTES, section.getTargetTimeMinutes());
        values.put(COLUMN_SECTION_NEXT_TC_LABEL, section.getNextTCLabel());

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
                COLUMN_SECTION_BLUE1,
                COLUMN_SECTION_BLUE2,
                COLUMN_SECTION_BLUE3,
                COLUMN_SECTION_SECTION_LABEL1,
                COLUMN_SECTION_SECTION_LABEL2,
                COLUMN_SECTION_PROV_START_LABEL,
                COLUMN_SECTION_ACTUAL_START_LABEL,
                COLUMN_SECTION_IS_STAGE,
                COLUMN_SECTION_TIME_TAKEN_LABEL,
                COLUMN_SECTION_HAS_START_ORDER,
                COLUMN_SECTION_TARGET_TIME_HOURS,
                COLUMN_SECTION_TARGET_TIME_MINUTES,
                COLUMN_SECTION_NEXT_TC_LABEL
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
            section.setBlue1Text(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_BLUE1)));
            section.setBlue2Text(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_BLUE2)));
            section.setBlue3Text(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_BLUE3)));
            section.setSectionLabel1(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_SECTION_LABEL1)));
            section.setSectionLabel2(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_SECTION_LABEL2)));
            section.setProvStartLabel(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_PROV_START_LABEL)));
            section.setActualStartLabel(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_ACTUAL_START_LABEL)));
            section.setIsStage(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_IS_STAGE))));
            section.setTimeTakenLabel(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TIME_TAKEN_LABEL)));
            section.setHasStartOrder(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_HAS_START_ORDER))));
            section.setTargetTimeHours(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TARGET_TIME_HOURS)));
            section.setTargetTimeMinutes(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TARGET_TIME_MINUTES)));
            section.setNextTCLabel(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_NEXT_TC_LABEL)));
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
                COLUMN_SECTION_BLUE1,
                COLUMN_SECTION_BLUE2,
                COLUMN_SECTION_BLUE3,
                COLUMN_SECTION_SECTION_LABEL1,
                COLUMN_SECTION_SECTION_LABEL2,
                COLUMN_SECTION_PROV_START_LABEL,
                COLUMN_SECTION_ACTUAL_START_LABEL,
                COLUMN_SECTION_IS_STAGE,
                COLUMN_SECTION_TIME_TAKEN_LABEL,
                COLUMN_SECTION_HAS_START_ORDER,
                COLUMN_SECTION_TARGET_TIME_HOURS,
                COLUMN_SECTION_TARGET_TIME_MINUTES,
                COLUMN_SECTION_NEXT_TC_LABEL
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
                section.setBlue1Text(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_BLUE1)));
                section.setBlue2Text(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_BLUE2)));
                section.setBlue3Text(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_BLUE3)));
                section.setSectionLabel1(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_SECTION_LABEL1)));
                section.setSectionLabel2(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_SECTION_LABEL2)));
                section.setProvStartLabel(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_PROV_START_LABEL)));
                section.setActualStartLabel(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_ACTUAL_START_LABEL)));
                section.setIsStage(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_IS_STAGE))));
                section.setTimeTakenLabel(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TIME_TAKEN_LABEL)));
                section.setHasStartOrder(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_HAS_START_ORDER))));
                section.setTargetTimeHours(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TARGET_TIME_HOURS)));
                section.setTargetTimeMinutes(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_TARGET_TIME_MINUTES)));
                section.setNextTCLabel(cursor.getString(cursor.getColumnIndex(COLUMN_SECTION_NEXT_TC_LABEL)));
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
        values.put(COLUMN_SECTION_BLUE1, section.getBlue1Text());
        values.put(COLUMN_SECTION_BLUE2, section.getBlue2Text());
        values.put(COLUMN_SECTION_BLUE3, section.getBlue3Text());
        values.put(COLUMN_SECTION_SECTION_LABEL1, section.getSectionLabel1());
        values.put(COLUMN_SECTION_SECTION_LABEL2, section.getSectionLabel2());
        values.put(COLUMN_SECTION_PROV_START_LABEL, section.getProvStartLabel());
        values.put(COLUMN_SECTION_ACTUAL_START_LABEL, section.getActualStartLabel());
        values.put(COLUMN_SECTION_IS_STAGE, section.getIsStage());
        values.put(COLUMN_SECTION_TIME_TAKEN_LABEL, section.getTimeTakenLabel());
        values.put(COLUMN_SECTION_HAS_START_ORDER, section.getHasStartOrder());
        values.put(COLUMN_SECTION_TARGET_TIME_HOURS, section.getTargetTimeHours());
        values.put(COLUMN_SECTION_TARGET_TIME_MINUTES, section.getTargetTimeMinutes());
        values.put(COLUMN_SECTION_NEXT_TC_LABEL, section.getNextTCLabel());
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
}
