package com.example.nomadnest.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomadnest.databinding.SearchViewItemsBinding;
import com.example.nomadnest.interfaces.RecyclerViewItemInterface;
import com.example.nomadnest.model.Places;

import java.util.ArrayList;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.ViewHolder> {

    private SearchViewItemsBinding binding;
    private ArrayList<Places> originalList;
    private ArrayList<Places> filteredList;

    private Filter filter = new ItemFilter();

    private RecyclerViewItemInterface viewItemInterface;

    // Constructor
    public SearchViewAdapter(ArrayList<Places> originalList) {
        this.originalList = originalList;
        this.filteredList = new ArrayList<>(originalList);
    }

    public void setItemClickListener(RecyclerViewItemInterface viewItemInterface) {
        this.viewItemInterface = viewItemInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = SearchViewItemsBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ((ViewHolder) holder).bindView(filteredList.get(position));

        Places stringSelectedPlace = filteredList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewItemInterface != null) {
                    viewItemInterface.OnItemClick(holder.getAdapterPosition(), stringSelectedPlace);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    //ViewHolder class to populate data in recyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SearchViewItemsBinding binding;

        public ViewHolder(@NonNull SearchViewItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("DefaultLocale")
        public void bindView(Places places) {
            binding.textSearch.setText(places.getPlaceName());
        }
    }


    public class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<Places> list = originalList;

            int count = list.size();
            final ArrayList<Places> filteredList = new ArrayList<>(count);

            Places filterableString;
            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getPlaceName().toLowerCase().contains(filterString)) {
                    filteredList.add(filterableString);
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Places>) results.values;
            notifyDataSetChanged();
        }
    }

    public Filter getFilter() {
        return filter;
    }

}