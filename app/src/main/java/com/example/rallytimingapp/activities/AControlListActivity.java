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
    private List<TimingCrew> aControlList = new ArrayList<TimingCrew>();
    private List<String> postChiefs = new ArrayList<>();
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

        // Setup Adapter
        aControlListView = (ListView) findViewById(R.id.AControlListView);
        crewDatabaseHelper = new TimingCrewDatabaseHelper(this);
        if (aControlListView != null) {
            postChiefs = getPostChiefs();
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postChiefs);
            aControlListView.setAdapter(adapter);
            aControlListView.setOnItemClickListener(this);

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

    public List<String> getPostChiefs() {
        List<String> currPostChiefs = new ArrayList<>();
        aControlList = crewDatabaseHelper.getTimingCrewsByPosition(role);
        for (int i = 0; i < aControlList.size(); i++) {
            currPostChiefs.add(aControlList.get(i).getPostChief());
        }
        return currPostChiefs;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String postChiefName = (String) adapterView.getAdapter().getItem(position);
        aControlCrew = crewDatabaseHelper.getTimingCrewByPostChief(role, postChiefName);
        int crewID = aControlCrew.getCrewId();
        Intent intent = new Intent(this, UpdateAControlActivity.class);
        intent.putExtra("CREW_ID", crewID);
        startActivity(intent);
    }

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

    public void back(View view) {
        Intent intent = new Intent(this, AdminOptionsActivity.class);
        startActivity(intent);
    }

    public void addNew(View view) {
        Intent intent = new Intent(this, AddAControlActivity.class);
        startActivity(intent);
    }
}