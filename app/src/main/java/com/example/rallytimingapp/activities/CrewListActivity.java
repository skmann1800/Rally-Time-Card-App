package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.model.TimingCrew;
import com.example.rallytimingapp.sql.TimingCrewDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CrewListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
    private List<TimingCrew> crewList = new ArrayList<TimingCrew>(); // List to contain all the crew entries
    private List<String> postChiefs = new ArrayList<>(); // List to contain the names of the post chiefs
    private ArrayAdapter<String> adapter;
    private ListView userListView;
    private SearchView searchBar;
    private TimingCrewDatabaseHelper timingCrewDatabaseHelper;
    private TimingCrew timingCrew;
    private String role;

    private Button addCrewButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_list);

        initViews();
        initListeners();

        // Get role from intent
        role = getIntent().getStringExtra("ROLE");
        addCrewButton.setText("Add New " + role + " Account");

        // Setup Adapter
        userListView = (ListView) findViewById(R.id.CrewListView);
        timingCrewDatabaseHelper = new TimingCrewDatabaseHelper(this);
        if (userListView != null) {
            postChiefs = getPostChiefs();
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postChiefs);
            userListView.setAdapter(adapter);
            userListView.setOnItemClickListener(this);

            // Set up Search Bar
            searchBar = (SearchView) findViewById(R.id.CrewSearchBar);
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

    // Method to initialise views
    private void initViews() {
        addCrewButton = findViewById(R.id.AddCrewButton);
        backButton = findViewById(R.id.CrewListBackButton);
    }

    // Method to initialise listeners
    private void initListeners() {
        addCrewButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    // Method to return a list of the names of all post chiefs of crews in the specified role
    public List<String> getPostChiefs() {
        List<String> currPostChiefs = new ArrayList<>();
        crewList = timingCrewDatabaseHelper.getTimingCrewsByPosition(role);
        for (int i = 0; i < crewList.size(); i++) {
            currPostChiefs.add(crewList.get(i).getPostChief());
        }
        return currPostChiefs;
    }

    // Method for when a list item is clicked. The relevant crew object is found and the ID and role type are
    // Passed onto the intent as an extra. Switches to the page where the admin can update a crew account
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String postChiefName = (String) adapterView.getAdapter().getItem(position);
        timingCrew = timingCrewDatabaseHelper.getTimingCrewByPostChief(role, postChiefName);
        int crewID = timingCrew.getCrewId();
        Intent intent = new Intent(this, UpdateCrewActivity.class);
        intent.putExtra("ROLE", role);
        intent.putExtra("CREW_ID", crewID);
        startActivity(intent);
    }

    // Method for when a search is submitted. Each entry in the database is tested
    // to see if they contain the query and results are displayed
    @Override
    public boolean onQueryTextSubmit(String query) {
        String search = query.toLowerCase(Locale.ROOT);
        crewList = timingCrewDatabaseHelper.getTimingCrewsByPosition(role);
        List<String> results = new ArrayList<>();
        for (int i = 0; i < crewList.size(); i++) {
            String currPostChief = crewList.get(i).getPostChief();
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
    // to see if they contain the new query and the results are displayed
    @Override
    public boolean onQueryTextChange(String newText) {
        String search = newText.toLowerCase(Locale.ROOT);
        crewList = timingCrewDatabaseHelper.getTimingCrewsByPosition(role);
        List<String> results = new ArrayList<>();
        for (int i = 0; i < crewList.size(); i++) {
            String currPostChief = crewList.get(i).getPostChief();
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

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.AddCrewButton:
                // Changes to the add crew activity and passes the crew type with the intent
                intent = new Intent(this, AddCrewActivity.class);
                intent.putExtra("ROLE", role);
                startActivity(intent);
                break;
            case R.id.CrewListBackButton:
                // Returns to the admin options page
                intent = new Intent(this, AdminOptionsActivity.class);
                startActivity(intent);
                break;
        }
    }
}