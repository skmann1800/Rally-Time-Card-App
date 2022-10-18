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
import com.example.rallytimingapp.model.TimingCrew;
import com.example.rallytimingapp.sql.TimingCrewDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AControlListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
    private List<TimingCrew> aControlList = new ArrayList<TimingCrew>(); // List to contain all the a control crew entries
    private List<String> postChiefs = new ArrayList<>(); // List to contain the names of the post chiefs
    private ArrayAdapter<String> adapter;
    private ListView aControlListView;
    private SearchView searchBar;
    private TimingCrewDatabaseHelper crewDatabaseHelper;
    private TimingCrew aControlCrew;
    private final String role = "A Control";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acontrol_list);

        // Set up Adapter
        aControlListView = (ListView) findViewById(R.id.AControlListView);
        crewDatabaseHelper = new TimingCrewDatabaseHelper(this);
        if (aControlListView != null) {
            postChiefs = getPostChiefs();
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postChiefs);
            aControlListView.setAdapter(adapter);
            aControlListView.setOnItemClickListener(this);

            // Set up Search Bar
            searchBar = (SearchView) findViewById(R.id.AControlSearchBar);
            searchBar.setOnQueryTextListener(this);
            searchBar.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    postChiefs = getPostChiefs();
                    adapter.clear();
                    adapter.addAll(postChiefs);
                    adapter.notifyDataSetChanged();
                    return false;
                }
            });
        }
    }

    // Method to return a list of the names of all post chiefs of crews in the A Control role
    public List<String> getPostChiefs() {
        List<String> currPostChiefs = new ArrayList<>();
        aControlList = crewDatabaseHelper.getTimingCrewsByPosition(role);
        for (int i = 0; i < aControlList.size(); i++) {
            currPostChiefs.add(aControlList.get(i).getPostChief());
        }
        return currPostChiefs;
    }

    // Method for when a list item is clicked. The relevant crew object is found and the ID is
    // Passed onto the intent as an extra. Switches to the page where the admin can update a crew account
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String postChiefName = (String) adapterView.getAdapter().getItem(position);
        aControlCrew = crewDatabaseHelper.getTimingCrewByPostChief(role, postChiefName);
        int crewID = aControlCrew.getCrewId();
        Intent intent = new Intent(this, UpdateCrewActivity.class);
        intent.putExtra("ROLE", role);
        intent.putExtra("CREW_ID", crewID);
        startActivity(intent);
    }

    // Method for when a search is submitted. Each entry in the database is tested
    // to see if they contain the query
    @Override
    public boolean onQueryTextSubmit(String query) {
        String search = query.toLowerCase(Locale.ROOT);
        aControlList = crewDatabaseHelper.getTimingCrewsByPosition(role);
        List<String> results = new ArrayList<>();
        for (int i = 0; i < aControlList.size(); i++) {
            String currPostChief = aControlList.get(i).getPostChief();
            String toMatch = currPostChief.toLowerCase(Locale.ROOT);
            if (toMatch.contains(search)) {
                results.add(currPostChief);
            }
        }
        adapter.clear();
        adapter.addAll(results);
        adapter.notifyDataSetChanged();
        return false;
    }

    // Method for when a search input is changed. Each entry in the database is tested
    // to see if they contain the new query
    @Override
    public boolean onQueryTextChange(String newText) {
        String search = newText.toLowerCase(Locale.ROOT);
        aControlList = crewDatabaseHelper.getTimingCrewsByPosition(role);
        List<String> results = new ArrayList<>();
        for (int i = 0; i < aControlList.size(); i++) {
            String currPostChief = aControlList.get(i).getPostChief();
            String toMatch = currPostChief.toLowerCase(Locale.ROOT);
            if (toMatch.contains(search)) {
                results.add(currPostChief);
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

    // Method which changes to the add a control activity
    public void addNew(View view) {
        Intent intent = new Intent(this, AddCrewActivity.class);
        intent.putExtra("ROLE", role);
        startActivity(intent);
    }
}