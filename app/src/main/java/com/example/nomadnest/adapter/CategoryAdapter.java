package com.example.nomadnest.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomadnest.databinding.ItemCategoryBinding;
import com.example.nomadnest.databinding.ItemSearchListBinding;
import com.example.nomadnest.interfaces.RecyclerViewItemInterface;
import com.example.nomadnest.model.Category;


import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Category> categoryArrayList;
    private ItemCategoryBinding binding;
    public Activity activity;
    private RecyclerViewItemInterface viewItemInterface;

    public void setItemClickListener(RecyclerViewItemInterface viewItemInterface) {
        this.viewItemInterface = viewItemInterface;
    }

    public CategoryAdapter(ArrayList<Category> categoryArrayList, Activity activity) {
        this.categoryArrayList = categoryArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = ItemCategoryBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(categoryArrayList.get(position));
        Category category = categoryArrayList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewItemInterface != null) {
                    viewItemInterface.OnItemClick(holder.getAdapterPosition(), category);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    //ViewHolder class to populate data in recyclerView
    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding binding;

        public ViewHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables"})
        public void bindView(Category category) {
            binding.imageCategory.setImageDrawable(activity.getResources().getDrawable(category.getCategoryImage()));
            binding.textCategoryTitle.setText(category.getCategoryName());

        }
    }
}
