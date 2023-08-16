package com.example.nomadnest.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.nomadnest.adapter.SearchViewAdapter;
import com.example.nomadnest.databinding.FragmentHomeBinding;
import com.example.nomadnest.databinding.LayoutSearchPopupBinding;
import com.example.nomadnest.interfaces.HostInterface;
import com.example.nomadnest.interfaces.RecyclerViewItemInterface;
import com.example.nomadnest.model.Places;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, RecyclerViewItemInterface, View.OnClickListener {


    private FragmentHomeBinding binding;
    HostInterface hostInterface;

    private SearchViewAdapter searchViewAdapter;
    private ArrayList<Places> searchItemList = new ArrayList<>();
    private PopupWindow popupWindow;

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
        // Add example data to itemList
        Places places = new Places(1, "Apple");
        searchItemList.add(places);
        Places places1 = new Places(2, "Banana");
        searchItemList.add(places1);
        Places places2 = new Places(3, "Cherry");
        searchItemList.add(places2);
        Places places3 = new Places(3, "Cheese");
        searchItemList.add(places3);
        Places places4 = new Places(3, "Astro");
        searchItemList.add(places4);

        // set click listener to all view
        initializeListener();

        //Set Popup SearchView
        popupView();

        setModeSelection(true);
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
        } else {
            changeSelectionUI(binding.textRecommended, binding.textPopular, binding.dotViewRecommended, binding.dotViewPopular);
        }
    }



    // Event Listener for SearchView
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()) {
            searchViewAdapter.getFilter().filter(newText);
            popupWindow.showAsDropDown(binding.searchPlace);
        } else {
            popupWindow.dismiss();
        }
        return false;
    }

    //Setup Popup RecyclerView
    private void popupView() {
        // Initialize PopupWindow
        LayoutSearchPopupBinding popupBinding = LayoutSearchPopupBinding.inflate(getLayoutInflater());
        View popupView = popupBinding.getRoot();
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(false);

        bindAdapter(popupBinding.rvSearch);
    }

    // Initialize and set up RecyclerView and Adapter
    @SuppressLint("NotifyDataSetChanged")
    private void bindAdapter(RecyclerView recyclerView) {
        searchViewAdapter = new SearchViewAdapter(searchItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(searchViewAdapter);
        searchViewAdapter.notifyDataSetChanged();
        searchViewAdapter.setItemClickListener(this);
    }

    @Override
    public void OnItemClick(int position) {

    }

    @Override
    public void OnItemClick(int position, Object o) {
        if (o != null && o instanceof Places) {
            Places places = (Places) o;
            binding.searchPlace.setQuery(places.getPlaceName(), false);
            popupWindow.dismiss();
        }
    }

    @Override
    public void OnItemMoved(int position, Object o) {

    }

    @Override
    public void OnItemShare(int position, Object o) {

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