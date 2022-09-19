package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.adapters.CompCursorAdapter;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CompListActivity extends AppCompatActivity {
    private List<Competitor> competitorList = new ArrayList<Competitor>();
    private List<String> drivers = new ArrayList<>();
    private ListView compListView;
    private SearchView searchBar;
    private CompDatabaseHelper compDatabaseHelper;
    private Competitor competitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_list);

        // Setup Adapter

        compDatabaseHelper = new CompDatabaseHelper(this);
        Cursor compCursor = compDatabaseHelper.getCursor();
        compListView = (ListView) findViewById(R.id.CompListView);
        CompCursorAdapter compAdapter = new CompCursorAdapter(this, compCursor);
        compListView.setAdapter(compAdapter);

        /*if (compListView != null) {


            searchBar = (SearchView) findViewById(R.id.CompSearchBar);
            searchBar.setOnQueryTextListener(this);
            searchBar.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    drivers = getDrivers();
                    adapter.clear();
                    adapter.addAll(drivers);
                    adapter.notifyDataSetChanged();
                    return false;
                }
            });
        }*/
    }

    public List<String> getDrivers() {
        List<String> currDrivers = new ArrayList<>();
        competitorList = compDatabaseHelper.getAllCompetitors();
        for (int i = 0; i < competitorList.size(); i++) {
            currDrivers.add(competitorList.get(i).getDriver());
        }
        return currDrivers;
    }

    /*@Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String compName = (String) adapterView.getAdapter().getItem(position);
        competitor = compDatabaseHelper.getCompetitorByDriver(compName);
        int compID = competitor.getCompId();
        Intent intent = new Intent(this, UpdateCompActivity.class);
        intent.putExtra("COMP_ID", compID);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String search = query.toLowerCase(Locale.ROOT);
        competitorList = compDatabaseHelper.getAllCompetitors();
        List<String> results = new ArrayList<>();
        for (int i = 0; i < competitorList.size(); i++) {
            String currDriver = competitorList.get(i).getDriver();
            String toMatch = currDriver.toLowerCase(Locale.ROOT);
            if (toMatch.contains(search)) {
                results.add(currDriver);
            }
        }
        adapter.clear();
        adapter.addAll(results);
        adapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String search = newText.toLowerCase(Locale.ROOT);
        competitorList = compDatabaseHelper.getAllCompetitors();
        List<String> results = new ArrayList<>();
        for (int i = 0; i < competitorList.size(); i++) {
            String currDriver = competitorList.get(i).getDriver();
            String toMatch = currDriver.toLowerCase(Locale.ROOT);
            if (toMatch.contains(search)) {
                results.add(currDriver);
            }
        }
        adapter.clear();
        adapter.addAll(results);
        adapter.notifyDataSetChanged();
        return false;
    }*/

    public void back(View view) {
        Intent intent = new Intent(this, AdminOptionsActivity.class);
        startActivity(intent);
    }

    public void addNew(View view) {
        Intent intent = new Intent(this, AddCompActivity.class);
        startActivity(intent);
    }
}