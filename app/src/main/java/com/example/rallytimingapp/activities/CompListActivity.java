package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.sql.CompDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CompListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
    private List<Competitor> competitorList = new ArrayList<Competitor>(); // List to contain all the competitor entries
    private List<String> drivers = new ArrayList<>(); // List to contain the names of the drivers
    private ArrayAdapter<String> adapter;
    private ListView compUserListView;
    private SearchView searchBar;
    private CompDatabaseHelper compDatabaseHelper;
    private Competitor competitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_list);

        // Set up Adapter
        compUserListView = (ListView) findViewById(R.id.CompListView);
        compDatabaseHelper = new CompDatabaseHelper(this);
        if (compUserListView != null) {
            drivers = getDrivers();
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drivers);
            compUserListView.setAdapter(adapter);
            compUserListView.setOnItemClickListener(this);

            // Set up Search Bar
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
        }
    }

    // Method to return a list of the names of all drivers
    public List<String> getDrivers() {
        List<String> currDrivers = new ArrayList<>();
        competitorList = compDatabaseHelper.getAllCompetitors();
        for (int i = 0; i < competitorList.size(); i++) {
            currDrivers.add(competitorList.get(i).getDriver());
        }
        return currDrivers;
    }

    // Method for when a list item is clicked. The relevant competitor object is found and the ID is
    // Passed onto the intent as an extra. Switches to the page where the admin can update a competitor account
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String compName = (String) adapterView.getAdapter().getItem(position);
        competitor = compDatabaseHelper.getCompetitorByDriver(compName);
        int compID = competitor.getCompId();
        Intent intent = new Intent(this, UpdateCompActivity.class);
        intent.putExtra("COMP_ID", compID);
        startActivity(intent);
    }

    // Method for when a search is submitted. Each entry in the database is tested
    // to see if they contain the query and results are displayed
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

    // Method for when a search input is changed. Each entry in the database is tested
    // to see if they contain the new query and the results are displayed
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
    }

    // Method which returns to the admin options page
    public void back(View view) {
        Intent intent = new Intent(this, AdminOptionsActivity.class);
        startActivity(intent);
    }

    // Method which changes to the add competitor activity
    public void addNew(View view) {
        Intent intent = new Intent(this, AddCompActivity.class);
        startActivity(intent);
    }
}