package com.example.nomadnest.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomadnest.databinding.ItemSearchListBinding;
import com.example.nomadnest.interfaces.RecyclerViewItemInterface;
import com.example.nomadnest.model.Places;

import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Places> placesArrayList;
    private ItemSearchListBinding binding;
    public Activity activity;
    private RecyclerViewItemInterface viewItemInterface;

    public void setItemClickListener(RecyclerViewItemInterface viewItemInterface) {
        this.viewItemInterface = viewItemInterface;
    }

    public SearchListAdapter(ArrayList<Places> placesArrayList, Activity activity) {
        this.placesArrayList = placesArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = ItemSearchListBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(placesArrayList.get(position));
        Places places= placesArrayList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewItemInterface != null) {
                    viewItemInterface.OnItemClick(holder.getAdapterPosition(), places);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return placesArrayList.size();
    }

    //ViewHolder class to populate data in recyclerView
    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchListBinding binding;

        public ViewHolder(@NonNull ItemSearchListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables"})
        public void bindView(Places places) {
            binding.imagePlace.setImageDrawable(activity.getResources().getDrawable(places.getPlaceImage()));
            binding.textPlaceTitle.setText(places.getPlaceName());
            binding.textPlaceLoction.setText(places.getPlaceLocation());
            binding.textPlaceRating.setText(places.getRatings());

        }
    }
}
