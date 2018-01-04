package fr.istic.starproviderBB;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.istic.contrat.StarContract;
import fr.istic.database.db.Database;


/**
 * Created by Bah on 30/12/2017.
 */

public class StarProvider extends ContentProvider {


    private static  final  int QUERY_ALL_DATA_BUS_ROUTE_TABLE = 1;
    private static final int QUERY_BY_DATA_ID_BUS_ROUTE_TABLE  = 2;

    private static  final  int QUERY_ALL_DATA_CALENDAR_TABLE = 10;
    private static final int QUERY_BY_DATA_ID_CALENDAR_TABLE  = 20;

    private static  final  int QUERY_ALL_DATA_STOPS_TABLE = 30;
    private static final int QUERY_BY_DATA_ID_STOPS_TABLE  = 40;

    private static  final  int QUERY_ALL_DATA_STOP_TIME_TABLE = 50;
    private static final int QUERY_BY_DATA_ID_STOP_TIME_TABLE  = 60;

    private static  final  int QUERY_ALL_DATA_TRIPS_TABLE = 70;
    private static final int QUERY_BY_DATA_ID_TRIPS_TABLE  = 80;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    Database database ;

    static {

         /************* Bus route **************************/

        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.BusRoutes.CONTENT_PATH,QUERY_ALL_DATA_BUS_ROUTE_TABLE);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.BusRoutes.CONTENT_PATH +"/#",QUERY_BY_DATA_ID_BUS_ROUTE_TABLE);

        /*************** Calendar *****************************/

        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Calendar.CONTENT_PATH,QUERY_ALL_DATA_CALENDAR_TABLE);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Calendar.CONTENT_PATH +"/#",QUERY_BY_DATA_ID_CALENDAR_TABLE);

        /*** ********** Stop ************************************/

        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Stops.CONTENT_PATH,QUERY_ALL_DATA_STOPS_TABLE);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Stops.CONTENT_PATH +"/#",QUERY_BY_DATA_ID_STOPS_TABLE);

        /************* Stop Time *********************************/

        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.StopTimes.CONTENT_PATH,QUERY_ALL_DATA_STOP_TIME_TABLE);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.StopTimes.CONTENT_PATH +"/#",QUERY_BY_DATA_ID_STOP_TIME_TABLE);

        /*** ***********Trips**********************************/

        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Trips.CONTENT_PATH,QUERY_ALL_DATA_TRIPS_TABLE);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Trips.CONTENT_PATH +"/#",QUERY_BY_DATA_ID_TRIPS_TABLE);
    }

    @Override
    public boolean onCreate() {
        database = new Database(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        database = new Database(getContext());
        SQLiteDatabase db = database.getReadableDatabase();
        switch (URI_MATCHER.match(uri))
        {
            case QUERY_ALL_DATA_BUS_ROUTE_TABLE :
                builder.setTables(StarContract.BusRoutes.CONTENT_PATH);
                break;
            case QUERY_BY_DATA_ID_BUS_ROUTE_TABLE:
                builder.setTables(StarContract.BusRoutes.CONTENT_PATH);
                break;
            case QUERY_ALL_DATA_CALENDAR_TABLE :
                builder.setTables(StarContract.Calendar.CONTENT_PATH);
                break;
            case QUERY_BY_DATA_ID_CALENDAR_TABLE:
                builder.setTables(StarContract.Calendar.CONTENT_PATH);
                break;
            case QUERY_ALL_DATA_STOPS_TABLE :
                builder.setTables(StarContract.Stops.CONTENT_PATH);
                break;
            case QUERY_BY_DATA_ID_STOPS_TABLE:
                builder.setTables(StarContract.Stops.CONTENT_PATH);
                break;
            case QUERY_ALL_DATA_STOP_TIME_TABLE :
                builder.setTables(StarContract.StopTimes.CONTENT_PATH);
                break;
            case QUERY_BY_DATA_ID_STOP_TIME_TABLE:
                builder.setTables(StarContract.StopTimes.CONTENT_PATH);
                break;
            case QUERY_ALL_DATA_TRIPS_TABLE :
                builder.setTables(StarContract.Trips.CONTENT_PATH);
                break;
            case QUERY_BY_DATA_ID_TRIPS_TABLE:
                builder.setTables(StarContract.Trips.CONTENT_PATH);
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported URI: " + uri);

        }

        Cursor cursor = builder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (URI_MATCHER.match(uri))
        {
            case QUERY_ALL_DATA_BUS_ROUTE_TABLE :
                return StarContract.BusRoutes.CONTENT_TYPE;
            case QUERY_BY_DATA_ID_BUS_ROUTE_TABLE:
               return StarContract.BusRoutes.CONTENT_ITEM_TYPE;

            case QUERY_ALL_DATA_CALENDAR_TABLE :
                return StarContract.Calendar.CONTENT_TYPE;
            case QUERY_BY_DATA_ID_CALENDAR_TABLE:
                return StarContract.Calendar.CONTENT_ITEM_TYPE;

            case QUERY_ALL_DATA_STOPS_TABLE :
                return StarContract.Stops.CONTENT_TYPE;
            case QUERY_BY_DATA_ID_STOPS_TABLE:
                return StarContract.Stops.CONTENT_ITEM_TYPE;

            case QUERY_ALL_DATA_STOP_TIME_TABLE :
                return StarContract.StopTimes.CONTENT_TYPE;
            case QUERY_BY_DATA_ID_STOP_TIME_TABLE:
                return StarContract.StopTimes.CONTENT_ITEM_TYPE;

            case QUERY_ALL_DATA_TRIPS_TABLE :
                return StarContract.Trips.CONTENT_TYPE;
            case QUERY_BY_DATA_ID_TRIPS_TABLE:
                return StarContract.Trips.CONTENT_ITEM_TYPE;

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
