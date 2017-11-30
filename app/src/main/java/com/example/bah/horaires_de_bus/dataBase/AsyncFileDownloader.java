package com.example.bah.horaires_de_bus.dataBase;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

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
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by romain on 28/11/2017.
 */

public class AsyncFileDownloader extends AsyncTask<String, Void, String> {

    private File myZip;

    private String calendar;
    private String routes;
    private String stops;
    private String stopeTimes;
    private String trips;

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
        System.out.println("done");
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

    private String fileToString(String path){
        File file = new File(path);
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                //traitement de la ligne
            }
            System.out.println("routes : " + text.toString());
            br.close();
        }
        catch (IOException e) {
        }
        return text.toString();
    }

}
