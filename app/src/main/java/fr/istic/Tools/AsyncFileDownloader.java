package fr.istic.Tools;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import fr.istic.database.db.Database;
import fr.istic.database.modelTables.BusRoute;
import fr.istic.database.modelTables.Calendar;
import fr.istic.database.modelTables.StopTimes;
import fr.istic.database.modelTables.Stops;
import fr.istic.database.modelTables.Trips;
import static android.content.ContentValues.TAG;

/**
 * Created by romain on 28/11/2017.
 */

public class AsyncFileDownloader extends AsyncTask<String, Context, String> {

    private File myZip;
    private String calendar;
    private String routes;
    private String stops;
    private String stopeTimes;
    private String trips;
    private Context context;
    private Database dataBase;
    private String Tag = "AsyncFileDownloader";


    public AsyncFileDownloader(Context context)
    {
        this.context = context;
        this.dataBase = new Database(context);
    }
    @Override
    protected String doInBackground(String... params) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/");
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        try {
            URL downloadUrl = new URL(params[0]); // you can write any link here

            File file = new File(dir, "horraire.zip");
        /* Open a connection to that URL. */
            URLConnection ucon = null;
            try {
                ucon = downloadUrl.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

        /*
         * Define InputStreams to read from the URLConnection.
         */
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

        /*
         * Read bytes to the Buffer until there is nothing more to read(-1).
         */
            ByteArrayOutputStream buffer  = new ByteArrayOutputStream (5000);
            byte[] data = new byte[50];
            int current = 0;

            while((current = bis.read(data,0,data.length)) != -1){
                buffer.write(data,0,current);
            }

            this.myZip = file;

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();


        }
        try {
            unzip("horraire.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String message) {
        Log.i(Tag,"start of loading data");
        insertionTableBusRoute(routes);
        insertionTableCalendar(calendar);
        insertionStop(stops);
        insertionStopTimes(stopeTimes);
        insertionTips(trips);
        Log.i(Tag,"end of loading data");

    }

    public void unzip(String zipFile) throws IOException {
        String path = "";
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/");
            if (dir.exists() == false) {
                dir.mkdirs();
            }
            ZipInputStream zin = new ZipInputStream(new FileInputStream(this.myZip));
            try {

                ZipEntry ze = zin.getNextEntry();
                while(ze != null) {

                    path =dir.getPath() + "/" + ze.getName();
                    FileOutputStream out = new FileOutputStream(path, false);
                    System.out.println(path);
                    copyStream(zin, out, ze);

                    ze = zin.getNextEntry();
                }
            }
            finally {
                zin.close();
                this.routes = dir.getPath() + "/routes.txt";
                this.stopeTimes = dir.getPath() + "/stop_times.txt";
                this.stops = dir.getPath() + "/stops.txt";
                this.calendar = dir.getPath() + "/calendar.txt";
                this.trips = dir.getPath() + "/trips.txt";
                System.out.println("done");
            }

        }
        catch (Exception e) {
            Log.e(TAG, "Unzip exception", e);
        }

    }

    private static void copyStream(InputStream in, OutputStream out, ZipEntry entry) throws IOException {
        byte[] buffer = new byte[1024 * 4];
        long count = 0;
        int n = 0;
        long size = entry.getSize();
        for (n = in.read(buffer); n != -1; n = in.read(buffer)) {
            out.write(buffer, 0, n);
            count += n;
        }
    }

    private String insertionTableBusRoute(String path){
        File file = new File(path);
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
             Boolean start = false;
             int routeId;
             String shortName;
             String longName;
             String description;
             String type;
             String color;
             String textColor;

            while ((line = br.readLine()) != null) {
                if(start)
                {
                    String result[] = line.split(",");
                    routeId = Integer.valueOf(result[0].substring(1,result[0].length()-1));
                    shortName = result[2].substring(1,result[2].length()-1);
                    longName = result[3].substring(1,result[3].length()-1);
                    description = result[4].substring(1,result[4].length()-1);
                    type = result[5].substring(1,result[5].length()-1);
                    color = result[7].substring(1,result[7].length()-1);
                    textColor = result[8].substring(1,result[8].length()-1);
                    BusRoute busRoute = new BusRoute(routeId,shortName,longName,description,type,color,textColor);
                    this.dataBase.insertBusRoutes(busRoute);

                }
                start = true;

            }

            br.close();
        }
        catch (IOException e) {
        }
        return text.toString();
    }

    private String insertionTableCalendar(String path){
        File file = new File(path);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
             String monday;
             String tuesday;
             String wednesday;
             String thursday;
             String friday;
             String saturday;
             String sunday;
             String startDate;
             String endDate;
             boolean start = false;

            while ((line = br.readLine()) != null) {
                if(start)
                {
                    String result[] = line.split(",");
                    monday = result[1].substring(1,result[1].length()-1);
                    tuesday = result[2].substring(1,result[2].length()-1);
                    wednesday = result[3].substring(1,result[3].length()-1);
                    thursday = result[4].substring(1,result[4].length()-1);
                    friday = result[5].substring(1,result[5].length()-1);
                    saturday = result[6].substring(1,result[6].length()-1);
                    sunday = result[7].substring(1,result[7].length()-1);
                    startDate = result[8].substring(1,result[8].length()-1);
                    endDate = result[9].substring(1,result[9].length()-1);
                    Calendar calendar = new Calendar(monday,tuesday,wednesday,thursday,friday,saturday,sunday,startDate,endDate);
                    dataBase.insertCalendar(calendar);
                }
                start = true;

            }

            br.close();
        }
        catch (IOException e) {
        }
        return text.toString();
    }

    private String insertionStop(String path){
        File file = new File(path);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
             String name;
             String description;
             float latitude;
             float longitude;
             String wheelChairBoalding;
             Boolean start = false;

            while ((line = br.readLine()) != null) {
                if(start)
                {
                    String result[] = line.split(",");
                    name = result[2].substring(1,result[2].length()-1);
                    description = result[3].substring(1,result[3].length()-1);
                    latitude = Float.valueOf(result[4].substring(1,result[4].length()-1)) ;
                    longitude = Float.valueOf(result[5].substring(1,result[5].length()-1));
                    wheelChairBoalding = result[11].substring(1,result[11].length()-1);
                    Stops stop = new Stops(name, description, latitude,longitude, wheelChairBoalding);
                    dataBase.insertStops(stop);
                }
                start = true;
            }
            br.close();
        }
        catch (IOException e) {
        }
        return text.toString();
    }

    private String insertionStopTimes(String path){
        File file = new File(path);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            Boolean start = false;
             int tripId;
             String arrivalTime;
             String departureTme;
             int stopId;
             String stopSequence;

            while ((line = br.readLine()) != null) {
                if(start)
                {
                    String result[] = line.split(",");
                    tripId = Integer.valueOf(result[0].substring(1,result[0].length()-1));
                    arrivalTime = result[1].substring(1,result[1].length()-1);
                    departureTme = result[2].substring(1,result[2].length()-1);
                    stopId = Integer.valueOf(result[3].substring(1,result[3].length()-1));
                    stopSequence = result[4].substring(1,result[4].length()-1);
                    StopTimes stopeTimes = new StopTimes(tripId, arrivalTime, departureTme, stopId,stopSequence );
                    dataBase.insertStopTimes(stopeTimes);
                }
                start = true;
            }
            br.close();
        }
        catch (IOException e) {
        }
        return text.toString();
    }

    private String insertionTips(String path){
        File file = new File(path);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            Boolean start = false;
             int RouteId;
             int serviceId;
             String headSign;
             int directionId;
             String blockId;
             String wheelchairAccessible;

            while ((line = br.readLine()) != null) {
                if(start)
                {
                    String result[] = line.split(",");
                    RouteId = Integer.valueOf(result[0].substring(1,result[0].length()-1));
                    serviceId = Integer.valueOf(result[1].substring(1,result[1].length()-1));
                    headSign = result[3].substring(1,result[3].length()-1);
                    directionId = Integer.valueOf(result[5].substring(1,result[5].length()-1));
                    blockId = result[6].substring(1,result[6].length()-1);
                    wheelchairAccessible = result[8].substring(1,result[8].length()-1);
                    Trips trips = new Trips(RouteId, serviceId, headSign, directionId,blockId,wheelchairAccessible );
                    dataBase.insertTrips(trips);
                }
                start = true;
            }
            br.close();
        }
        catch (IOException e) {
        }
        return text.toString();
    }



}
