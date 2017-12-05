package com.example.bah.horaires_de_bus;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.bah.horaires_de_bus.contrat.StarContract;

/**
 * Created by romain on 05/12/2017.
 */

public class StarBBProvider extends ContentProvider {

    private static final int QUERY_ALL_BUSS = 0;
    private static final int QUERY_BY_BUSS_ID = 1;
    private static final int QUERY_ALL_TRIP = 2;
    private static final int QUERY_BY_TRIP_ID = 3;
    private static final int QUERY_ALL_STOP = 4;
    private static final int QUERY_BY_STOP_ID = 5;
    private static final int QUERY_ALL_STOPTIME = 6;
    private static final int QUERY_BY_STOPTIME_ID = 7;
    private static final int QUERY_ALL_CALENDAR = 8;
    private static final int QUERY_BY_CALENDAR_ID = 9;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.BusRoutes.CONTENT_PATH, QUERY_ALL_BUSS);
        URI_MATCHER.addURI(StarContract.AUTHORITY,StarContract.BusRoutes.CONTENT_PATH + "/#", QUERY_BY_BUSS_ID);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Trips.CONTENT_PATH, QUERY_ALL_TRIP);
        URI_MATCHER.addURI(StarContract.AUTHORITY,StarContract.Trips.CONTENT_PATH + "/#", QUERY_BY_TRIP_ID);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Stops.CONTENT_PATH, QUERY_ALL_STOP);
        URI_MATCHER.addURI(StarContract.AUTHORITY,StarContract.Stops.CONTENT_PATH + "/#", QUERY_BY_STOP_ID);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.StopTimes.CONTENT_PATH, QUERY_ALL_STOPTIME);
        URI_MATCHER.addURI(StarContract.AUTHORITY,StarContract.StopTimes.CONTENT_PATH + "/#", QUERY_BY_STOPTIME_ID);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Calendar.CONTENT_PATH, QUERY_ALL_CALENDAR);
        URI_MATCHER.addURI(StarContract.AUTHORITY,StarContract.Calendar.CONTENT_PATH + "/#", QUERY_BY_CALENDAR_ID);

    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case QUERY_ALL_BUSS: return StarContract.BusRoutes.CONTENT_TYPE;
            case QUERY_BY_BUSS_ID: return StarContract.BusRoutes.CONTENT_ITEM_TYPE;
            case QUERY_ALL_TRIP: return StarContract.Trips.CONTENT_TYPE;
            case QUERY_BY_TRIP_ID: return StarContract.Trips.CONTENT_ITEM_TYPE;
            case QUERY_ALL_STOP: return StarContract.Stops.CONTENT_TYPE;
            case QUERY_BY_STOP_ID: return StarContract.Stops.CONTENT_ITEM_TYPE;
            case QUERY_ALL_STOPTIME: return StarContract.StopTimes.CONTENT_TYPE;
            case QUERY_BY_STOPTIME_ID: return StarContract.StopTimes.CONTENT_ITEM_TYPE;
            case QUERY_ALL_CALENDAR: return StarContract.Calendar.CONTENT_TYPE;
            case QUERY_BY_CALENDAR_ID: return StarContract.Calendar.CONTENT_ITEM_TYPE;
            default: return null;
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
