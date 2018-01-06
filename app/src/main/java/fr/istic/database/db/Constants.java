package fr.istic.database.db;


import fr.istic.contrat.StarContract;

/**
 * Created by Bah on 18/11/2017.
 */

public class Constants implements StarContract {

    public static final String DATABASE_NAME ="bus_hours";
    public static final String DATABASE_CREATE_TABLE_BUS_ROUTE = "CREATE TABLE IF NOT EXISTS "+ StarContract.BusRoutes.CONTENT_PATH +
            "( _ID INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT , "+
            BusRoutes.BusRouteColumns.ROUTE_ID + " INTEGER , "+
            BusRoutes.BusRouteColumns.SHORT_NAME + " TEXT , "+
            BusRoutes.BusRouteColumns.LONG_NAME+" TEXT, "+
            BusRoutes.BusRouteColumns.DESCRIPTION+" TEXT, "+
            BusRoutes.BusRouteColumns.TYPE+" TEXT,"+
            BusRoutes.BusRouteColumns.COLOR+" TEXT, "+
            BusRoutes.BusRouteColumns.TEXT_COLOR+" TEXT );";

    public static final String DATABASE_CREATE_TABLE_TRIPS = "CREATE TABLE IF NOT EXISTS "+ Trips.CONTENT_PATH +
            "( _ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , "+
            Trips.TripColumns.ROUTE_ID+" INTEGER ,"+
            Trips.TripColumns.SERVICE_ID+" INTEGER,"+
            Trips.TripColumns.HEADSIGN+" TEXT,"+
            Trips.TripColumns.DIRECTION_ID+" INTEGER,"+
            Trips.TripColumns.BLOCK_ID+" TEXT,"+
            Trips.TripColumns.WHEELCHAIR_ACCESSIBLE+" TEXT );";


    public static final String DATABASE_CREATE_TABLE_STOPS = "CREATE TABLE IF NOT EXISTS "+ Stops.CONTENT_PATH +
            "( _ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
            Stops.StopColumns.NAME + " TEXT , "+
            Stops.StopColumns.DESCRIPTION+" TEXT,"+
            Stops.StopColumns.LATITUDE+" INTEGER, "+
            Stops.StopColumns.LONGITUDE+" INTEGER,"+
            Stops.StopColumns.WHEELCHAIR_BOARDING+" TEXT );";


    public static final String DATABASE_CREATE_TABLE_STOP_TIMES = "CREATE TABLE IF NOT EXISTS "+ StarContract.StopTimes.CONTENT_PATH +
            "( _ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
            StarContract.StopTimes.StopTimeColumns.TRIP_ID + " INTEGER ,"+
            StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME+" TEXT,"+
            StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME+" TEXT,"+
            StarContract.StopTimes.StopTimeColumns.STOP_ID+" INTEGER,"+
            StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE+" TEXT );";

    public static final String DATABASE_CREATE_TABLE_CALENDAR = "CREATE TABLE IF NOT EXISTS "+ Calendar.CONTENT_PATH +
            "( _ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
            Calendar.CalendarColumns.MONDAY + " TEXT , "+
            Calendar.CalendarColumns.TUESDAY+" TEXT,"+
            Calendar.CalendarColumns.WEDNESDAY+" TEXT,"+
            Calendar.CalendarColumns.THURSDAY+" TEXT,"+
            Calendar.CalendarColumns.FRIDAY+" TEXT," +
            Calendar.CalendarColumns.SATURDAY+" TEXT,"+
            Calendar.CalendarColumns.SUNDAY+" TEXT,"+
            Calendar.CalendarColumns.START_DATE+" TEXT,"+
            Calendar.CalendarColumns.END_DATE+" TEXT"+
            " );";



}
