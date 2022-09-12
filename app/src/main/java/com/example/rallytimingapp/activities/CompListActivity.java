package com.example.rallytimingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.rallytimingapp.R;
import com.example.rallytimingapp.model.Competitor;
import com.example.rallytimingapp.sql.CompDatabaseHelper;
import com.example.rallytimingapp.sql.UserDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CompListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private List<Competitor> competitorList = new ArrayList<Competitor>();
    private List<String> drivers = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView compUserListView;
    private SearchView searchBar;
    private CompDatabaseHelper compDatabaseHelper;
    private Competitor competitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_list);

        // Setup Adapter
        compUserListView = (ListView) findViewById(R.id.CompListView);
        compDatabaseHelper = new CompDatabaseHelper(this);
        if (compUserListView != null) {
            competitorList = compDatabaseHelper.getAllCompetitors();
            for (int i = 0; i < competitorList.size(); i++) {
                drivers.add(competitorList.get(i).getDriver());
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drivers);
            compUserListView.setAdapter(adapter);
            compUserListView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String compName = (String) adapterView.getAdapter().getItem(position);
        Toast.makeText(adapterView.getContext(), "Clicked " + compName, Toast.LENGTH_SHORT).show();
        competitor = compDatabaseHelper.getCompetitorByDriver(compName);
        int compID = competitor.getCompId();
        Intent intent = new Intent(this, AdminCompActivity.class);
        intent.putExtra("MESSAGE", "Update Competitor Details");
        intent.putExtra("COMP_ID", compID);
        startActivity(intent);
    }
}