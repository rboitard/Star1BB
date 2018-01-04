package fr.istic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fr.istic.Tools.AsyncFileDownloader;
import fr.istic.Tools.MySingleton;
import fr.istic.contrat.StarContract;
import fr.istic.database.db.Database;
import fr.istic.database.modelTables.BusRoute;
import fr.istic.database.modelTables.Calendar;
import fr.istic.database.modelTables.StopTimes;
import fr.istic.database.modelTables.Stops;
import fr.istic.database.modelTables.Trips;

public class MainActivity extends AppCompatActivity {

    private String Tag = this.getClass().getName();
    private Context context = this;
    private Database dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = new Database(context);
       /* if(isNetworkAValable())
        {
            dataBase.deleteAllContents();
        }*/

        Log.i(Tag,dataBase.allTableNames().toString());
        String url = "https://data.explore.star.fr/explore/dataset/tco-busmetro-horaires-gtfs-versions-td/download/?format=json&timezone=Europe/Berlin";

       // makeJsonArraygRequest(url);
        TestBDD();
    }


    private void makeJsonArraygRequest(String url) {

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        downloadURl(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(Tag, "Error: " + error.getMessage());

            }
        });

        // Adding request to request queue
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(req);
    }

    public void downloadURl(JSONArray jsonArray)
    {
        JSONObject  jsonObject= new JSONObject();
        String URL ="";
        try {

            jsonObject = jsonArray.getJSONObject(0);
            jsonObject = (JSONObject)jsonObject.get("fields");
            URL = (String) jsonObject.get("url");

        } catch (JSONException e) {
            e.printStackTrace();

        }
        AsyncFileDownloader downloader = new AsyncFileDownloader(context);
        downloader.execute(URL);
    }

    /**
     * pemet de vérifier s'il y a une connexion internet
     * @return un boolean
     */
    public boolean isNetworkAValable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;
        }
        return isAvailable;
    }

    public void TestBDD()
    {

        List<BusRoute> list1 = dataBase.getContentsBusRouteTable(" SELECT * FROM "+ StarContract.BusRoutes.CONTENT_PATH);
        List<Calendar> list2 = dataBase.getContentsCalendarTable(" SELECT * FROM "+ StarContract.Calendar.CONTENT_PATH);
        List<Stops> list3 = dataBase.getContentsStopsTable(" SELECT * FROM "+ StarContract.Stops.CONTENT_PATH);
        List<StopTimes> list4 = dataBase.getContentsStopTimesTable(" SELECT * FROM "+ StarContract.StopTimes.CONTENT_PATH);
        List<Trips> list5 = dataBase.getContentsTripsTable(" SELECT * FROM "+ StarContract.Trips.CONTENT_PATH);
        Log.i(Tag,"size table Bus Route :  "+list1.size()+"\n");
        Log.i(Tag,"size table Calendar :  "+list2.size()+"\n");
        Log.i(Tag,"size table Stops :  "+list3.size()+"\n");
        Log.i(Tag,"size table Stop times : "+list4.size()+"\n");
        Log.i(Tag,"size Trips : "+list5.size()+"\n");

    }




}
