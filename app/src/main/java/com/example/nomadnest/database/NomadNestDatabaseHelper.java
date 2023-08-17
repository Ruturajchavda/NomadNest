package com.example.nomadnest.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.nomadnest.model.Bookings;
import com.example.nomadnest.model.Places;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NomadNestDatabaseHelper extends SQLiteOpenHelper {

    // declare database name and version
    private static final String DATABASE_NAME = "NomadNestDB";
    private static final int DATABASE_VERSION = 1;

    //Create and Drop Places Table
    private static final String TABLE_PLACE = "tbl_places";
    private static final String COL_PLACE_ID = "placeId";
    private static final String COL_PLACE_NAME = "placeName";
    private static final String COL_PLACE_DESC = "placeDesc";
    private static final String COL_PLACE_LOCATION = "placeLocation";
    private static final String COL_RATINGS = "ratings";
    private static final String COL_PLACE_IMAGE = "placeImage";
    private static final String COL_ESTIMATED_PRICE = "estimatedPrice";
    private static final String COL_CATEGORY = "category";
    private static final String COL_CHECK_POPULAR = "isPopular";

    private static final String CREATE_TABLE_PLACE = "CREATE TABLE " + TABLE_PLACE + " (" +
            COL_PLACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_PLACE_NAME + " TEXT NOT NULL, " +
            COL_PLACE_DESC + " TEXT NOT NULL, " +
            COL_PLACE_LOCATION + " TEXT NOT NULL, " +
            COL_RATINGS + " TEXT NOT NULL, " +
            COL_PLACE_IMAGE + " INTEGER NOT NULL, " +
            COL_ESTIMATED_PRICE + " FLOAT NOT NULL, " +
            COL_CATEGORY + " TEXT NOT NULL, " +
            COL_CHECK_POPULAR + " BOOLEAN NOT NULL);";
    private static final String DROP_TABLE_PLACE = "DROP TABLE IF EXISTS " + TABLE_PLACE;

    //Create and Drop Bookings Table
    public static final String TABLE_BOOKINGS = "tbl_booking";
    public static final String COL_BOOKING_ID = "bookingId";
    public static final String COL_TOTAL_PEOPLE = "totalPeople";
    public static final String COL_TRAVELLING_DATE = "travellingDate";
    public static final String COL_TRIP_DURATION = "tripDuration";
    public static final String COL_TRIP_TYPE = "tripType";
    public static final String COL_TRIP_BUDGET= "tripBudget";

    private static final String CREATE_TABLE_BOOKINGS = "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                    COL_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_TOTAL_PEOPLE + " INTEGER NOT NULL, " +
                    COL_TRAVELLING_DATE + " DATE NOT NULL, " +
                    COL_TRIP_DURATION + " INTEGER NOT NULL, " +
                    COL_TRIP_TYPE + " TEXT NOT NULL, " +
                    COL_TRIP_BUDGET + " FLOAT NOT NULL);";
    private static final String DROP_TABLE_BOOKINGS = "DROP TABLE IF EXISTS " + TABLE_BOOKINGS;

    public NomadNestDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PLACE);
        db.execSQL(CREATE_TABLE_BOOKINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_PLACE);
        db.execSQL(DROP_TABLE_BOOKINGS);
        onCreate(db);
    }

    //Insert stock data in db
    public boolean addPlaceData(Places places) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PLACE_NAME, places.getPlaceName());
        contentValues.put(COL_PLACE_DESC, places.getPlaceDesc());
        contentValues.put(COL_PLACE_LOCATION, places.getPlaceLocation());
        contentValues.put(COL_RATINGS, places.getRatings());
        contentValues.put(COL_PLACE_IMAGE, places.getPlaceImage());
        contentValues.put(COL_ESTIMATED_PRICE, places.getEstimatedPrice());
        contentValues.put(COL_CATEGORY, places.getCategory());
        contentValues.put(COL_CHECK_POPULAR, places.isPopular() ? 1 : 0);
        long result = db.insert(TABLE_PLACE, null, contentValues);
        return (result != -1);
    }

    //Get all Stock items
    public ArrayList<Places> getAllPlaces() {
        ArrayList<Places> placesArrayList = new ArrayList<Places>();
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_PLACE, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int placeId = cursor.getInt(cursor.getColumnIndex(COL_PLACE_ID));
                @SuppressLint("Range") String placeName = cursor.getString(cursor.getColumnIndex(COL_PLACE_NAME));
                @SuppressLint("Range") String placeDesc = cursor.getString(cursor.getColumnIndex(COL_PLACE_DESC));
                @SuppressLint("Range") String placeLocation = cursor.getString(cursor.getColumnIndex(COL_PLACE_LOCATION));
                @SuppressLint("Range") String ratings = cursor.getString(cursor.getColumnIndex(COL_RATINGS));
                @SuppressLint("Range") int placeImage = cursor.getInt(cursor.getColumnIndex(COL_PLACE_IMAGE));
                @SuppressLint("Range") float estimatedPrice = cursor.getFloat(cursor.getColumnIndex(COL_ESTIMATED_PRICE));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
                @SuppressLint("Range") int isPopularInt = cursor.getInt(cursor.getColumnIndex(COL_CHECK_POPULAR));
                boolean isPopular = isPopularInt != 0;


                Places places = new Places(placeId, placeName, placeDesc, placeLocation, ratings, placeImage, estimatedPrice, category, isPopular);
                placesArrayList.add(places);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return placesArrayList;
    }

    //Get stock item by Place ID
    public Places getPlaceById(int placeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COL_PLACE_NAME, COL_PLACE_DESC, COL_PLACE_LOCATION,
                COL_RATINGS, COL_PLACE_IMAGE, COL_ESTIMATED_PRICE,
                COL_CATEGORY, COL_CHECK_POPULAR
        };
        String selection = COL_PLACE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(placeId)};
        Cursor cursor = db.query(TABLE_PLACE, columns, selection, selectionArgs, null, null, null);

        Places place = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String placeName = cursor.getString(cursor.getColumnIndex(COL_PLACE_NAME));
            @SuppressLint("Range") String placeDesc = cursor.getString(cursor.getColumnIndex(COL_PLACE_DESC));
            @SuppressLint("Range") String placeLocation = cursor.getString(cursor.getColumnIndex(COL_PLACE_LOCATION));
            @SuppressLint("Range") String ratings = cursor.getString(cursor.getColumnIndex(COL_RATINGS));
            @SuppressLint("Range") int placeImage = cursor.getInt(cursor.getColumnIndex(COL_PLACE_IMAGE));
            @SuppressLint("Range") float estimatedPrice = cursor.getFloat(cursor.getColumnIndex(COL_ESTIMATED_PRICE));
            @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
            @SuppressLint("Range") boolean isPopular = cursor.getInt(cursor.getColumnIndex(COL_CHECK_POPULAR)) == 1;

            place = new Places();
            place.setPlaceName(placeName);
            place.setPlaceDesc(placeDesc);
            place.setPlaceLocation(placeLocation);
            place.setRatings(ratings);
            place.setPlaceImage(placeImage);
            place.setEstimatedPrice(estimatedPrice);
            place.setCategory(category);
            place.setPopular(isPopular);
            cursor.close();
        }

        return place;
    }

    // Insert booking data into the db
    public boolean addBookingData(Bookings booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TOTAL_PEOPLE, booking.getTotalPeople());
        contentValues.put(COL_TRAVELLING_DATE, formatDateToString(booking.getTravellingDate()));
        contentValues.put(COL_TRIP_DURATION, booking.getTripDuration());
        contentValues.put(COL_TRIP_TYPE, booking.getTripType());
        contentValues.put(COL_TRIP_BUDGET, booking.getTripBudget());

        long result = db.insert(TABLE_BOOKINGS, null, contentValues);
        return (result != -1);
    }

    // Get all booking items
    public ArrayList<Bookings> getAllBookings() {
        ArrayList<Bookings> bookingsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKINGS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int bookingId = cursor.getInt(cursor.getColumnIndex(COL_BOOKING_ID));
                @SuppressLint("Range") int totalPeople = cursor.getInt(cursor.getColumnIndex(COL_TOTAL_PEOPLE));
                @SuppressLint("Range") String travellingDate = cursor.getString(cursor.getColumnIndex(COL_TRAVELLING_DATE));
                @SuppressLint("Range") int tripDuration = cursor.getInt(cursor.getColumnIndex(COL_TRIP_DURATION));
                @SuppressLint("Range") String tripType = cursor.getString(cursor.getColumnIndex(COL_TRIP_TYPE));
                @SuppressLint("Range") float tripBudget = cursor.getFloat(cursor.getColumnIndex(COL_TRIP_BUDGET));

                Bookings booking = new Bookings(bookingId, totalPeople, formatStringToDate(travellingDate), tripDuration, tripType,tripBudget);
                bookingsArrayList.add(booking);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return bookingsArrayList;
    }

    // Get booking item by Booking ID
    public Bookings getBookingById(int bookingId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COL_TOTAL_PEOPLE, COL_TRAVELLING_DATE,
                COL_TRIP_DURATION, COL_TRIP_TYPE,COL_TRIP_BUDGET
        };
        String selection = COL_BOOKING_ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookingId)};
        Cursor cursor = db.query(TABLE_BOOKINGS, columns, selection, selectionArgs, null, null, null);

        Bookings booking = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int totalPeople = cursor.getInt(cursor.getColumnIndex(COL_TOTAL_PEOPLE));
            @SuppressLint("Range") String travellingDate = cursor.getString(cursor.getColumnIndex(COL_TRAVELLING_DATE));
            @SuppressLint("Range") int tripDuration = cursor.getInt(cursor.getColumnIndex(COL_TRIP_DURATION));
            @SuppressLint("Range") String tripType = cursor.getString(cursor.getColumnIndex(COL_TRIP_TYPE));
            @SuppressLint("Range") float tripBudget = cursor.getFloat(cursor.getColumnIndex(COL_TRIP_BUDGET));


            booking = new Bookings();
            booking.setTotalPeople(totalPeople);
            booking.setTravellingDate(formatStringToDate(travellingDate));
            booking.setTripDuration(tripDuration);
            booking.setTripType(tripType);
            booking.setTripBudget(tripBudget);
            cursor.close();
        }

        return booking;
    }

    //Convert Date to String
    private String formatDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    //Convert String to Date
    private Date formatStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
