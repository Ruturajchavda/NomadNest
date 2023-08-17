package com.example.nomadnest.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.nomadnest.R;
import com.example.nomadnest.activity.PlaceDetailsActivity;
import com.example.nomadnest.activity.SearchListActivity;
import com.example.nomadnest.adapter.CategoryAdapter;
import com.example.nomadnest.adapter.PlacesAdapter;
import com.example.nomadnest.adapter.SearchViewAdapter;
import com.example.nomadnest.constants.Extras;
import com.example.nomadnest.database.NomadNestDatabaseHelper;
import com.example.nomadnest.databinding.FragmentHomeBinding;
import com.example.nomadnest.databinding.LayoutSearchPopupBinding;
import com.example.nomadnest.interfaces.HostInterface;
import com.example.nomadnest.interfaces.RecyclerViewItemInterface;
import com.example.nomadnest.model.Category;
import com.example.nomadnest.model.Places;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, RecyclerViewItemInterface, View.OnClickListener {


    private FragmentHomeBinding binding;
    HostInterface hostInterface;

    private ArrayList<Places> placesArrayList = new ArrayList<>();
    private ArrayList<Category> categoryArrayList = new ArrayList<>();

    private PlacesAdapter placesAdapter;
    private CategoryAdapter categoryAdapter;

    private NomadNestDatabaseHelper nomadNestDatabaseHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hostInterface = (HostInterface) context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        //Initialize Database
        nomadNestDatabaseHelper = new NomadNestDatabaseHelper(getActivity());

        // set click listener to all view
        initializeListener();

        setModeSelection(true);

        //Populate Places Data
        populatePlaceData(true);

        //Populate Category Data
        populateCategoryData();

    }

    private void initializeListener() {
        binding.searchPlace.setOnQueryTextListener(this);
        binding.textPopular.setOnClickListener(this);
        binding.textRecommended.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.textPopular.getId()) {
            setModeSelection(true);
        } else if (v.getId() == binding.textRecommended.getId()) {
            setModeSelection(false);
        }
    }

    private void setModeSelection(boolean isPopular) {
        // Apply effects to the clicked TextView
        if (isPopular) {
            changeSelectionUI(binding.textPopular, binding.textRecommended, binding.dotViewPopular, binding.dotViewRecommended);
            populatePlaceData(true);
        } else {
            changeSelectionUI(binding.textRecommended, binding.textPopular, binding.dotViewRecommended, binding.dotViewPopular);
            populatePlaceData(false);
        }
    }


    // Event Listener for SearchView
    @Override
    public boolean onQueryTextSubmit(String query) {
        if(!query.isEmpty()){
            Intent intent = new Intent(getActivity(), SearchListActivity.class);
            intent.putExtra(Extras.EXTRA_IS_SEARCH,true);
            getActivity().startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    //Populate Data for Place List
    private void populatePlaceData(boolean isPopular) {
        placesArrayList.clear();
        if (nomadNestDatabaseHelper.getAllPlaces() != null && nomadNestDatabaseHelper.getAllPlaces().size() > 0) {
            for (int i = 0; i < nomadNestDatabaseHelper.getAllPlaces().size(); i++) {
                if (isPopular) {
                    if (nomadNestDatabaseHelper.getAllPlaces().get(i).isPopular()) {
                        placesArrayList.add(nomadNestDatabaseHelper.getAllPlaces().get(i));
                    }
                } else {
                    if (!nomadNestDatabaseHelper.getAllPlaces().get(i).isPopular()) {
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
        placesAdapter = new PlacesAdapter(placesArrayList, getActivity());
        binding.rvPlaces.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        binding.rvPlaces.setAdapter(placesAdapter);
        placesAdapter.notifyDataSetChanged();
        placesAdapter.setItemClickListener(this);
    }

    //Populate Data for Place List
    private void populateCategoryData() {
        categoryArrayList.clear();

        Category category1 = new Category(1,getResources().getString(R.string.category_nature),R.drawable.category_nature);
        categoryArrayList.add(category1);

        Category category2 = new Category(2,getResources().getString(R.string.category_falls),R.drawable.category_falls);
        categoryArrayList.add(category2);

        Category category3 = new Category(3,getResources().getString(R.string.category_countryside),R.drawable.category_countr_side);
        categoryArrayList.add(category3);

        bindCategoryAdapter();
    }

    // Initialize and set up RecyclerView and Adapter
    @SuppressLint("NotifyDataSetChanged")
    private void bindCategoryAdapter() {
        categoryAdapter = new CategoryAdapter(categoryArrayList, getActivity());
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        binding.rvCategory.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        categoryAdapter.setItemClickListener(this);
    }


    @Override
    public void OnItemClick(int position) {

    }

    @Override
    public void OnItemClick(int position, Object o) {
        if (o != null && o instanceof Category) {
            Category category = (Category) o;
            Intent intent = new Intent(getActivity(), SearchListActivity.class);
            intent.putExtra(Extras.EXTRA_IS_SEARCH,false);
            intent.putExtra(Extras.EXTRA_ATTACHMENT, category.getCategoryName());
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void OnItemMoved(int position, Object o) {

    }

    @Override
    public void OnItemShare(int position, Object o) {
        if (o != null && o instanceof Places) {
            Places places = (Places) o;

            Intent intent = new Intent(getActivity(), PlaceDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Extras.EXTRA_ATTACHMENT, places);
            intent.putExtra(Extras.EXTRA_FRAGMENT_BUNDLE, bundle);
            getActivity().startActivity(intent);
        }

    }


    // Change UI for Selected Text
    private void changeSelectionUI(TextView textView1, TextView textView2, View view1, View view2) {
        textView1.setSelected(true);
        view1.setVisibility(View.VISIBLE);
        textView2.setSelected(false);
        view2.setVisibility(View.INVISIBLE);

        textView1.setTextColor(getActivity().getResources().getColor(R.color.color_primary));
        textView1.setScaleX(1.2f);
        textView1.setScaleY(1.2f);
        textView1.setTypeface(null, Typeface.BOLD); // Set to bold font style

        textView2.setTextColor(getActivity().getResources().getColor(R.color.gray));
        textView2.setScaleX(1f);
        textView2.setScaleY(1f);
        textView2.setTypeface(null, Typeface.NORMAL); // Set to bold font style
    }


}