package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.nomadnest.R;
import com.example.nomadnest.constants.Extras;
import com.example.nomadnest.database.NomadNestDatabaseHelper;
import com.example.nomadnest.databinding.ActivityBookTripBinding;

import com.example.nomadnest.databinding.DialogBookingSuccessBinding;
import com.example.nomadnest.model.Bookings;
import com.example.nomadnest.model.Places;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookTripActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ActivityBookTripBinding binding;
    private int placeId;
    private NomadNestDatabaseHelper nomadNestDatabaseHelper;
    private DatePickerDialog datePickerDialog;
    private String durationDays;
    private Places places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // assign view to binding
        binding = ActivityBookTripBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        placeId = intent.getIntExtra(Extras.EXTRA_ATTACHMENT, 1);

        init();
    }

    private void init() {
        initializeListener();

        //Initialize Database
        nomadNestDatabaseHelper = new NomadNestDatabaseHelper(BookTripActivity.this);

        //Adapter for Design Type spinner
        ArrayAdapter<CharSequence> adapterTripDuration = ArrayAdapter.createFromResource(this, R.array.array_duration, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapterTripDuration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnDuration.setAdapter(adapterTripDuration);

        places = nomadNestDatabaseHelper.getPlaceById(placeId);
        binding.textPrice.setText(String.valueOf(places.getEstimatedPrice()));
    }

    private void initializeListener() {
        binding.btnBookNow.setOnClickListener(this);
        binding.edtTravelingDate.setOnClickListener(this);
        binding.spnDuration.setOnItemSelectedListener(this);


        //Check/Uncheck radio buttons as per need
        binding.rdFamilyTrip.setChecked(false);
        binding.rdFriendsTrip.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btnBookNow.getId()) {
            doBooking();
        } else if (v.getId() == binding.edtTravelingDate.getId()) {
            Calendar calendar = Calendar.getInstance();
            int dayOfSales = calendar.get(Calendar.DAY_OF_MONTH);
            int monthOfSales = calendar.get(Calendar.MONTH);
            int yearOfSales = calendar.get(Calendar.YEAR);
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            datePickerDialog = new DatePickerDialog(BookTripActivity.this, (view, year, month, dayOfMonth) -> binding.edtTravelingDate.setText(String.format("%d/%d/%d", dayOfMonth, month + 1, year)), yearOfSales, monthOfSales, dayOfSales);
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        durationDays = parent.getItemAtPosition(position).toString();

        if (position > 0) {

            String[] days = durationDays.split(" ");
            binding.textPrice.setText(String.valueOf(places.getEstimatedPrice() * (Integer.parseInt(days[0]) - 2)));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void doBooking() {
        if (isValidate()) {
        int totalPeople = Integer.parseInt(binding.edtTotalPeople.getText().toString().trim());
        String[] days = durationDays.split(" ");
        int tripDuration = Integer.parseInt(days[0]);
        String tripType = binding.rdFamilyTrip.isChecked() ? getResources().getString(R.string.hint_family_trip) : getResources().getString(R.string.hint_friend_trip);
        float tripBudget = Float.parseFloat(binding.textPrice.getText().toString().trim());


        // Get selected date from DatePicker
        int year = datePickerDialog.getDatePicker().getYear();
        int month = datePickerDialog.getDatePicker().getMonth();
        int day = datePickerDialog.getDatePicker().getDayOfMonth();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date travellingDate = calendar.getTime();

        Bookings bookings = new Bookings();
        bookings.setTotalPeople(totalPeople);
        bookings.setTravellingDate(travellingDate);
        bookings.setTripDuration(tripDuration);
        bookings.setTripType(tripType);
        bookings.setTripBudget(tripBudget);

        if(nomadNestDatabaseHelper.addBookingData(bookings)){
            showSuccessDialog();
        }else {
            Snackbar.make(binding.layoutMain, getResources().getString(R.string.err_select_date), Snackbar.LENGTH_SHORT).show();
        }
        }
    }

    // Validation for form fields
    private boolean isValidate() {
        if (binding.edtTotalPeople.getText().toString().trim().equals("")) {
            binding.edtTotalPeople.setError(getResources().getString(R.string.err_field_required));
            binding.edtTotalPeople.requestFocus();
            return false;
        }

        if (Integer.parseInt(binding.edtTotalPeople.getText().toString().trim()) <= 0) {
            binding.edtTotalPeople.setError(getResources().getString(R.string.err_value_zero));
            binding.edtTotalPeople.requestFocus();
            return false;
        }

        if (binding.edtTravelingDate.getText().toString().trim().equals("")) {
            Snackbar.make(binding.layoutMain, getResources().getString(R.string.err_select_date), Snackbar.LENGTH_SHORT).show();
            binding.edtTravelingDate.requestFocus();
            return false;
        }

        if (durationDays.equals("Please Select Trip Days")) {
            Snackbar.make(binding.layoutMain, getResources().getString(R.string.err_select_duration), Snackbar.LENGTH_SHORT).show();
            return false;
        }

        //Validate return trip type Selection
        if (binding.rgTripType.getCheckedRadioButtonId() == -1) {
            Snackbar.make(binding.layoutMain, getResources().getString(R.string.err_no_trip_type), Snackbar.LENGTH_SHORT).show();
            return false;
        }

        removeErrors();
        return true;
    }

    //Clearing all errors on successful validation.
    private void removeErrors() {
        binding.edtTotalPeople.setError(null);
    }

    private void showSuccessDialog() {

        DialogBookingSuccessBinding binding = DialogBookingSuccessBinding.inflate(LayoutInflater.from(this));
        Dialog dialog = new Dialog(this);
        dialog.setContentView(binding.getRoot());

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(BookTripActivity.this, HomeActivity.class));
                finish();
            }
        });

        dialog.show();

    }
}