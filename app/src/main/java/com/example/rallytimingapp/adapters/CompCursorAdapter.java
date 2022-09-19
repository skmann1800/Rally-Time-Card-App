package com.example.rallytimingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.rallytimingapp.R;

public class CompCursorAdapter extends CursorAdapter {

    public CompCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_competitor, parent, false);
    }

    @Override
    @SuppressLint("Range")
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvDriver = (TextView) view.findViewById(R.id.tvDriver);
        TextView tvCarNum = (TextView) view.findViewById(R.id.tvCarNum);

        String driver = cursor.getString(cursor.getColumnIndex("COLUMN_COMP_DRIVER"));
        int carNum = cursor.getInt(cursor.getColumnIndex("COLUMN_COMP_CARNUM"));
        //int carNum = Integer.parseInt(cursor.getString(cursor.getColumnIndex("COLUMN_COMP_CARNUM")));

        tvDriver.setText(driver);
        tvCarNum.setText(String.valueOf(carNum));
    }
}
