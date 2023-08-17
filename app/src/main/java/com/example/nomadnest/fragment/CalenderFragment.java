package com.example.nomadnest.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomadnest.R;
import com.example.nomadnest.adapter.CalendarEventAdapter;
import com.example.nomadnest.databinding.FragmentCalenderBinding;
import com.example.nomadnest.databinding.FragmentProfileBinding;
import com.example.nomadnest.model.Trip;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.time.DayOfWeek;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CalenderFragment extends Fragment {

    private FragmentCalenderBinding binding;
    private CalendarEventAdapter adapter;
    private List<Trip> trips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCalenderBinding.inflate(inflater, container, false);

        trips = new ArrayList<>();
        Trip trip1 = new Trip("Montreal","2 Days - 2 People",R.drawable.travel_1);
        Trip trip2 = new Trip("Toronto","4 Days - 6 People",R.drawable.travel_2);
        Trip trip3 = new Trip("Vancouver","6 Days - 3 People",R.drawable.travel_3);
        Trip trip4 = new Trip("Winnipeg","1 Day - 5 People",R.drawable.travel_4);
        Trip trip5 = new Trip("Calgary","8 Days - 7 People",R.drawable.travel_6);
        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);
        trips.add(trip4);
        trips.add(trip5);
        setTripToDate();
        binding.calendarView.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDayChanged() {

            }

            @Override
            public void onClickListener() {

            }

            @Override
            public void onDaySelect() {
                setTripToDate();
            }

            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {
            }

            @Override
            public void onMonthChange() {
            }

            @Override
            public void onWeekChange(int i) {

            }
        });
        return binding.getRoot();
    }

    private void setTripToDate(){
        Random random = new Random();
        Trip currentTrip = trips.get(random.nextInt(trips.size()));
        List<Trip> newTrips = new ArrayList<>();
        newTrips.add(currentTrip);
        binding.calendarRv.setAdapter(new CalendarEventAdapter(getContext(),newTrips));
    }

    private String addZeroInDate(int current) {
        if(String.valueOf(current).length()==1){
            return "0"+current;
        }else{
            return String.valueOf(current);
        }
    }


}