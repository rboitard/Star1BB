package com.example.bah.horaires_de_bus.dataBase.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bah.horaires_de_bus.contrat.StarContract;
import com.example.bah.horaires_de_bus.dataBase.modelTables.BusRoute;
import com.example.bah.horaires_de_bus.dataBase.modelTables.Stop;
import com.example.bah.horaires_de_bus.dataBase.modelTables.StopeTimes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bah on 14/11/2017.
 */

public class DataBase  extends SQLiteOpenHelper implements StarContract {


    private SQLiteDatabase db;
    private static  String DATA_NAME ="bus_hours";
    private static final int DATA_BASE_VERSION = 1;

    public DataBase(Context context) {
        super(context, DATA_NAME, null, DATA_BASE_VERSION);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.DATABASE_CREATE_TABLE_BUS_ROUTE);
        db.execSQL(Constants.DATABASE_CREATE_TABLE_CALENDAR);
        db.execSQL(Constants.DATABASE_CREATE_TABLE_STOP_TIMES);
        db.execSQL(Constants.DATABASE_CREATE_TABLE_STOPS);
        db.execSQL(Constants.DATABASE_CREATE_TABLE_TIPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ BusRoutes.CONTENT_PATH);
        db.execSQL("DROP TABLE IF EXISTS "+ Trips.CONTENT_PATH);
        db.execSQL("DROP TABLE IF EXISTS "+ Stops.CONTENT_PATH);
        db.execSQL("DROP TABLE IF EXISTS "+ StopTimes.CONTENT_PATH);
        db.execSQL("DROP TABLE IF EXISTS "+ Calendar.CONTENT_PATH);
        onCreate(db);
    }

    public void insertBusRoutes( BusRoute busRoutes)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BusRoutes.BusRouteColumns.SHORT_NAME, busRoutes.getShortName());
        values.put(BusRoutes.BusRouteColumns.LONG_NAME, busRoutes.getLongName());
        values.put(BusRoutes.BusRouteColumns.DESCRIPTION, busRoutes.getDescription());
        values.put(BusRoutes.BusRouteColumns.TYPE, busRoutes.getType());
        values.put(BusRoutes.BusRouteColumns.COLOR, busRoutes.getColor());
        values.put(BusRoutes.BusRouteColumns.TEXT_COLOR, busRoutes.getTextColor());
        db.insert(BusRoutes.CONTENT_PATH,null, values);
    }

    public void insertTrips(com.example.bah.horaires_de_bus.dataBase.modelTables.Trips trips )
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Trips.TripColumns.BLOCK_ID, trips.getBlockId() );
        values.put(Trips.TripColumns.SERVICE_ID, trips.getServiceId() );
        values.put(Trips.TripColumns.HEADSIGN, trips.getHeadSign() );
        values.put(Trips.TripColumns.DIRECTION_ID, trips.getDirectionId() );
        values.put(Trips.TripColumns.BLOCK_ID, trips.getBlockId() );
        values.put(Trips.TripColumns.WHEELCHAIR_ACCESSIBLE, trips.getWheelchairAccessible() );
        db.insert(Trips.CONTENT_PATH,null, values);
    }

    public void insertStops(Stop stop)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Stops.StopColumns.NAME, stop.getName());
        values.put(Stops.StopColumns.DESCRIPTION, stop.getDescription());
        values.put(Stops.StopColumns.LATITUDE, stop.getLatitude());
        values.put(Stops.StopColumns.LONGITUDE, stop.getLongitude());
        values.put(Stops.StopColumns.WHEELCHAIR_BOARDING, stop.getWheelChairBoalding());
        db.insert(Stops.CONTENT_PATH,null, values);
    }

    public void insertStopTimes( StopeTimes stopTimes)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StopTimes.StopTimeColumns.TRIP_ID, stopTimes.getTripId());
        values.put(StopTimes.StopTimeColumns.ARRIVAL_TIME, stopTimes.getArrivalTime());
        values.put(StopTimes.StopTimeColumns.DEPARTURE_TIME, stopTimes.getDepartureTme());
        values.put(StopTimes.StopTimeColumns.STOP_ID, stopTimes.getStopId());
        values.put(StopTimes.StopTimeColumns.STOP_SEQUENCE, stopTimes.getStopSequence());
        db.insert(StopTimes.CONTENT_PATH,null,values);

    }

    public void insertCalendar( com.example.bah.horaires_de_bus.dataBase.modelTables.Calendar calendar)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Calendar.CalendarColumns.MONDAY, calendar.getMonday());
        values.put(Calendar.CalendarColumns.TUESDAY, calendar.getTuesday());
        values.put(Calendar.CalendarColumns.WEDNESDAY, calendar.getWednesday());
        values.put(Calendar.CalendarColumns.THURSDAY, calendar.getThursday());
        values.put(Calendar.CalendarColumns.FRIDAY, calendar.getFriday());
        values.put(Calendar.CalendarColumns.SATURDAY, calendar.getSaturday());
        values.put(Calendar.CalendarColumns.SUNDAY, calendar.getSunday());
        values.put(Calendar.CalendarColumns.START_DATE, calendar.getStartDate());
        values.put(Calendar.CalendarColumns.END_DATE, calendar.getEndDate());
        db.insert(Calendar.CONTENT_PATH,null,values);
    }

    public List<String> allTableNames()
    {
        List<String> result = new ArrayList<String>();
        String selectQuery = "select name from sqlite_master where type = 'table'";
        Cursor cursor = this.getReadableDatabase().rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                result.add(name);
            }while (cursor.moveToNext());
        }
        return result;
    }


}
