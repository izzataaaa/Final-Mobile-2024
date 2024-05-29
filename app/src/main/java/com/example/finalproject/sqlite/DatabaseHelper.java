package com.example.finalproject.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finalproject.model.AirplaneModels;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "airplanes.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_AIRPLANES = "airplanes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AIRLINE = "airline";
    private static final String COLUMN_ARRIVAL = "arrival";
    private static final String COLUMN_DEPARTURE = "departure";
    private static final String COLUMN_FLIGHT = "flight";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_STATION = "station";
    public static final String TABLE_USER = "user";
    private static final String COLUMN_IDUSER = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IS_LOGGED_IN = "isLogged";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AIRPLANES_TABLE = "CREATE TABLE " + TABLE_AIRPLANES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AIRLINE + " TEXT,"
                + COLUMN_ARRIVAL + " TEXT,"
                + COLUMN_DEPARTURE + " TEXT,"
                + COLUMN_FLIGHT + " TEXT,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_STATION + " TEXT" + ")";
        db.execSQL(CREATE_AIRPLANES_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_IDUSER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_IS_LOGGED_IN + " INTEGER" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AIRPLANES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Airplane related methods
    public void addAirplane(AirplaneModels airplane) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AIRLINE, airplane.getAirline());
        values.put(COLUMN_ARRIVAL, airplane.getArrival());
        values.put(COLUMN_DEPARTURE, airplane.getDeparture());
        values.put(COLUMN_FLIGHT, airplane.getFlight());
        values.put(COLUMN_TYPE, airplane.getType());
        values.put(COLUMN_STATION, airplane.getStation());

        db.insert(TABLE_AIRPLANES, null, values);
        db.close();
    }

    public Cursor getAllAirplanes() {
        List<AirplaneModels> airplaneList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_AIRPLANES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AirplaneModels airplane = new AirplaneModels();
                airplane.setAirline(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AIRLINE)));
                airplane.setArrival(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL)));
                airplane.setDeparture(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE)));
                airplane.setFlight(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT)));
                airplane.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                airplane.setStation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATION)));

                airplaneList.add(airplane);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return (Cursor) airplaneList;
    }

    public void deleteAirplane(String flight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AIRPLANES, COLUMN_FLIGHT + " = ?", new String[]{flight});
        db.close();
    }

    // User related methods
    public void insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_IS_LOGGED_IN, 0);
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean isUserExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_IDUSER}, COLUMN_USERNAME + " = ?", new String[]{username}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    public void updateUser(int id, String username, String password, int isLoggedIn) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_IS_LOGGED_IN, isLoggedIn);
        db.update(TABLE_USER, values, COLUMN_IDUSER + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_IDUSER + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Public getters for private constants
    public String getTableUser() {
        return TABLE_USER;
    }

    public String getColumnIdUser() {
        return COLUMN_IDUSER;
    }

    public String getColumnUsername() {
        return COLUMN_USERNAME;
    }

    public String getColumnPassword() {
        return COLUMN_PASSWORD;
    }

    public String getColumnIsLoggedIn() {
        return COLUMN_IS_LOGGED_IN;
    }

    public String getColumnAirline() {return COLUMN_AIRLINE;}

    public String getColumnArrival() {return COLUMN_ARRIVAL;}

    public String getColumnDeparture() {return COLUMN_DEPARTURE;}

    public String getColumnFlight() {return COLUMN_FLIGHT;}

    public String getColumnType() {return COLUMN_TYPE;}

    public String getColumnStation() {return COLUMN_STATION;}
}
