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

public class FinishListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
    private List<TimingCrew> finishList = new ArrayList<TimingCrew>();
    private List<String> postChiefs = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView finishUserListView;
    private SearchView searchBar;
    private TimingCrewDatabaseHelper timingCrewDatabaseHelper;
    private TimingCrew finishCrew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_list);

        // Setup Adapter
        finishUserListView = (ListView) findViewById(R.id.FinishListView);
        timingCrewDatabaseHelper = new TimingCrewDatabaseHelper(this);
        if (finishUserListView != null) {
            postChiefs = getPostChiefs();
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postChiefs);
            finishUserListView.setAdapter(adapter);
            finishUserListView.setOnItemClickListener(this);

            searchBar = (SearchView) findViewById(R.id.FinishSearchBar);
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
        finishList = timingCrewDatabaseHelper.getTimingCrewsByPosition("Finish");
        for (int i = 0; i < finishList.size(); i++) {
            currPostChiefs.add(finishList.get(i).getPostChief());
        }
        return currPostChiefs;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String postChiefName = (String) adapterView.getAdapter().getItem(position);
        finishCrew = timingCrewDatabaseHelper.getTimingCrewByPostChief(postChiefName);
        int crewID = finishCrew.getCrewId();
        Intent intent = new Intent(this, UpdateFinishActivity.class);
        intent.putExtra("CREW_ID", crewID);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String search = query.toLowerCase(Locale.ROOT);
        finishList = timingCrewDatabaseHelper.getTimingCrewsByPosition("Finish");
        List<String> results = new ArrayList<>();
        for (int i = 0; i < finishList.size(); i++) {
            String currPostChief = finishList.get(i).getPostChief();
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
        finishList = timingCrewDatabaseHelper.getTimingCrewsByPosition("Finish");
        List<String> results = new ArrayList<>();
        for (int i = 0; i < finishList.size(); i++) {
            String currPostChief = finishList.get(i).getPostChief();
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
        Intent intent = new Intent(this, AddFinishActivity.class);
        startActivity(intent);
    }
}