package com.example.nomadnest.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomadnest.R;
import com.example.nomadnest.model.Trip;

import java.util.List;

public class CalendarEventAdapter extends RecyclerView.Adapter<CalendarEventAdapter.ViewHolder> {

    private Context context;
    private List<Trip> trips;

    public CalendarEventAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.trips = trips;
    }


    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @NonNull
    @Override
    public CalendarEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_calendar_event_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tripDestination.setText("Trip To "+trips.get(position).getDestination());
        holder.tripDescription.setText(trips.get(position).getDescription());
        holder.tripImage.setImageResource(trips.get(position).getDestinationImage());
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tripDestination, tripDescription;
        ImageView tripImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tripDestination = itemView.findViewById(R.id.tvTripDestination);
            tripDescription = itemView.findViewById(R.id.tvTripDescription);
            tripImage = itemView.findViewById(R.id.ivTripImage);
        }
    }

}

