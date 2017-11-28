package com.example.bah.horaires_de_bus.dataBase;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by romain on 28/11/2017.
 */

public class AsyncFileDownloader extends AsyncTask<String, Void, String> {
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

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer.toByteArray());
            fos.close();




        } catch (IOException e) {
            e.printStackTrace();


        }
        return null;
    }

    @Override
    protected void onPostExecute(String message) {
        System.out.println("done");
    }
}
