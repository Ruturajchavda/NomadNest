package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.example.nomadnest.adapter.PlacesAdapter;
import com.example.nomadnest.adapter.SearchListAdapter;
import com.example.nomadnest.adapter.SearchViewAdapter;
import com.example.nomadnest.constants.Extras;
import com.example.nomadnest.database.NomadNestDatabaseHelper;
import com.example.nomadnest.databinding.ActivityMainBinding;
import com.example.nomadnest.databinding.ActivitySearchListBinding;
import com.example.nomadnest.interfaces.RecyclerViewItemInterface;
import com.example.nomadnest.model.Places;

import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity implements RecyclerViewItemInterface {

    private ActivitySearchListBinding binding;

    private SearchListAdapter searchListAdapter;
    private ArrayList<Places> placesArrayList = new ArrayList<>();

    private NomadNestDatabaseHelper nomadNestDatabaseHelper;

    private boolean isSearch = true;
    private String category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // assign view to binding
        binding = ActivitySearchListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        isSearch = intent.getBooleanExtra(Extras.EXTRA_IS_SEARCH, true);
        if (!isSearch) {
            category = intent.getStringExtra(Extras.EXTRA_ATTACHMENT);
        }

        init();
    }

    private void init() {
        //Initialize Database
        nomadNestDatabaseHelper = new NomadNestDatabaseHelper(SearchListActivity.this);

        //Populate Places Data
        populateData();

    }


    //Populate Data
    private void populateData() {
        placesArrayList.clear();
        if (nomadNestDatabaseHelper.getAllPlaces() != null && nomadNestDatabaseHelper.getAllPlaces().size() > 0) {
            for (int i = 0; i < nomadNestDatabaseHelper.getAllPlaces().size(); i++) {
                if (isSearch) {
                    placesArrayList.add(nomadNestDatabaseHelper.getAllPlaces().get(i));
                } else {
                    if (nomadNestDatabaseHelper.getAllPlaces().get(i).getCategory().equals(category)) {
                        placesArrayList.add(nomadNestDatabaseHelper.getAllPlaces().get(i));
                    }
                }
            }
        }

        bindPlacesAdapter();
    }

    // Initialize and set up RecyclerView and Adapter
    @SuppressLint("NotifyDataSetChanged")
    private void bindPlacesAdapter() {
        searchListAdapter = new SearchListAdapter(placesArrayList, SearchListActivity.this);
        binding.rvPlaces.setLayoutManager(new LinearLayoutManager(SearchListActivity.this, RecyclerView.VERTICAL, false));
        binding.rvPlaces.setAdapter(searchListAdapter);
        searchListAdapter.notifyDataSetChanged();
        searchListAdapter.setItemClickListener(this);
    }

    @Override
    public void OnItemClick(int position) {

    }

    @Override
    public void OnItemClick(int position, Object o) {
        if (o != null && o instanceof Places) {
            Places places = (Places) o;

            Intent intent = new Intent(SearchListActivity.this, PlaceDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Extras.EXTRA_ATTACHMENT, places);
            intent.putExtra(Extras.EXTRA_FRAGMENT_BUNDLE, bundle);
            startActivity(intent);
        }
    }

    @Override
    public void OnItemMoved(int position, Object o) {

    }

    @Override
    public void OnItemShare(int position, Object o) {

    }
}