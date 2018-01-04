package fr.istic.database.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fr.istic.contrat.StarContract;
import fr.istic.database.modelTables.BusRoute;
import fr.istic.database.modelTables.Calendar;
import fr.istic.database.modelTables.StopTimes;
import fr.istic.database.modelTables.Stops;
import fr.istic.database.modelTables.Trips;

import static fr.istic.database.db.Constants.DATABASE_NAME;

/**
 * Created by Bah on 14/11/2017.
 */

public class Database extends SQLiteOpenHelper  {


    private SQLiteDatabase db;
    private static final int DATA_BASE_VERSION = 6;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.DATABASE_CREATE_TABLE_BUS_ROUTE);
        db.execSQL(Constants.DATABASE_CREATE_TABLE_CALENDAR);
        db.execSQL(Constants.DATABASE_CREATE_TABLE_STOP_TIMES);
        db.execSQL(Constants.DATABASE_CREATE_TABLE_STOPS);
        db.execSQL(Constants.DATABASE_CREATE_TABLE_TRIPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ StarContract.BusRoutes.CONTENT_PATH);
        db.execSQL("DROP TABLE IF EXISTS "+ StarContract.Trips.CONTENT_PATH);
        db.execSQL("DROP TABLE IF EXISTS "+ StarContract.Stops.CONTENT_PATH);
        db.execSQL("DROP TABLE IF EXISTS "+ StarContract.StopTimes.CONTENT_PATH);
        db.execSQL("DROP TABLE IF EXISTS "+ StarContract.Calendar.CONTENT_PATH);
        onCreate(db);
    }

    public void insertBusRoutes( BusRoute busRoutes)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME, busRoutes.getShortName());
        values.put(StarContract.BusRoutes.BusRouteColumns.LONG_NAME, busRoutes.getLongName());
        values.put(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION, busRoutes.getDescription());
        values.put(StarContract.BusRoutes.BusRouteColumns.TYPE, busRoutes.getType());
        values.put(StarContract.BusRoutes.BusRouteColumns.COLOR, busRoutes.getColor());
        values.put(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR, busRoutes.getTextColor());
        db.insert(StarContract.BusRoutes.CONTENT_PATH,null, values);
    }

    public void insertTrips(fr.istic.database.modelTables.Trips trips)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StarContract.Trips.TripColumns.BLOCK_ID, trips.getBlockId() );
        values.put(StarContract.Trips.TripColumns.SERVICE_ID, trips.getServiceId() );
        values.put(StarContract.Trips.TripColumns.HEADSIGN, trips.getHeadSign() );
        values.put(StarContract.Trips.TripColumns.DIRECTION_ID, trips.getDirectionId() );
        values.put(StarContract.Trips.TripColumns.BLOCK_ID, trips.getBlockId() );
        values.put(StarContract.Trips.TripColumns.WHEELCHAIR_ACCESSIBLE, trips.getWheelchairAccessible() );
        db.insert(StarContract.Trips.CONTENT_PATH,null, values);
    }

    public void insertStops(fr.istic.database.modelTables.Stops stop)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StarContract.Stops.StopColumns.NAME, stop.getName());
        values.put(StarContract.Stops.StopColumns.DESCRIPTION, stop.getDescription());
        values.put(StarContract.Stops.StopColumns.LATITUDE, stop.getLatitude());
        values.put(StarContract.Stops.StopColumns.LONGITUDE, stop.getLongitude());
        values.put(StarContract.Stops.StopColumns.WHEELCHAIR_BOARDING, stop.getWheelChairBoalding());
        db.insert(StarContract.Stops.CONTENT_PATH,null, values);
    }

    public void insertStopTimes( fr.istic.database.modelTables.StopTimes stopTimes)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StarContract.StopTimes.StopTimeColumns.TRIP_ID, stopTimes.getTripId());
        values.put(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME, stopTimes.getArrivalTime());
        values.put(StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME, stopTimes.getDepartureTime());
        values.put(StarContract.StopTimes.StopTimeColumns.STOP_ID, stopTimes.getStopId());
        values.put(StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE, stopTimes.getStopSequence());
        db.insert(StarContract.StopTimes.CONTENT_PATH,null,values);

    }

    public void insertCalendar(fr.istic.database.modelTables.Calendar calendar)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StarContract.Calendar.CalendarColumns.MONDAY, calendar.getMonday());
        values.put(StarContract.Calendar.CalendarColumns.TUESDAY, calendar.getTuesday());
        values.put(StarContract.Calendar.CalendarColumns.WEDNESDAY, calendar.getWednesday());
        values.put(StarContract.Calendar.CalendarColumns.THURSDAY, calendar.getThursday());
        values.put(StarContract.Calendar.CalendarColumns.FRIDAY, calendar.getFriday());
        values.put(StarContract.Calendar.CalendarColumns.SATURDAY, calendar.getSaturday());
        values.put(StarContract.Calendar.CalendarColumns.SUNDAY, calendar.getSunday());
        values.put(StarContract.Calendar.CalendarColumns.START_DATE, calendar.getStartDate());
        values.put(StarContract.Calendar.CalendarColumns.END_DATE, calendar.getEndDate());
        db.insert(StarContract.Calendar.CONTENT_PATH,null,values);
    }

    public  List<BusRoute> getContentsBusRouteTable(String request )
    {
        List<BusRoute> list = new ArrayList<>();
        Cursor  cursor = db.rawQuery(request,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                BusRoute busRoute = new BusRoute(Integer.valueOf(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
                list.add(busRoute);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public  List<Calendar> getContentsCalendarTable(String request )
    {
        List<Calendar> list = new ArrayList<>();
        Cursor  cursor = db.rawQuery(request,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Calendar calendar = new Calendar(Integer.valueOf(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(7),
                        cursor.getString(9));
                list.add(calendar);
                cursor.moveToNext();
            }
        }
        return list;
    }


    public  List<Stops> getContentsStopsTable(String request )
    {
        List<Stops> list = new ArrayList<>();
        Cursor  cursor = db.rawQuery(request,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Stops stops = new Stops(Integer.valueOf(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Float.valueOf(cursor.getString(3)),
                        Float.valueOf(cursor.getString(4)),
                        cursor.getString(5));
                list.add(stops);
                cursor.moveToNext();
            }
        }
        return list;
    }


    public  List<StopTimes> getContentsStopTimesTable(String request)
    {
        List<StopTimes> list = new ArrayList<>();
        Cursor  cursor = db.rawQuery(request,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                StopTimes stopTimes = new StopTimes(Integer.valueOf(cursor.getString(0)),
                        Integer.valueOf(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.valueOf(cursor.getString(4)),
                        cursor.getString(5));
                list.add(stopTimes);
                cursor.moveToNext();
            }
        }
        return list;
    }


    public  List<Trips> getContentsTripsTable(String request)
    {
        List<Trips> list = new ArrayList<>();
        Cursor  cursor = db.rawQuery(request,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Trips trips = new Trips(Integer.valueOf(cursor.getString(0)),
                        getInteger(cursor.getString(1)),
                        getInteger(cursor.getString(2)),
                        cursor.getString(3),
                        getInteger(cursor.getString(4)),
                        cursor.getString(5),
                        cursor.getString(6));

                list.add(trips);
                cursor.moveToNext();
            }
        }
        return list;
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

    public void deleteAllContents()
    {
        db.delete(Constants.BusRoutes.CONTENT_PATH, null, null);
        db.delete(Constants.Calendar.CONTENT_PATH, null, null);
        db.delete(Constants.Stops.CONTENT_PATH, null, null);
        db.delete(Constants.StopTimes.CONTENT_PATH, null, null);
        db.delete(Constants.Trips.CONTENT_PATH, null, null);

    }

    private static Integer getInteger(String str) {
        if (str == null) {
            return new Integer(0);
        } else {

            return Integer.parseInt(str);
        }
    }


}
