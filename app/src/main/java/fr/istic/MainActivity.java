package fr.istic;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.istic.Tools.AsyncFileDownloader;
import fr.istic.Tools.MySingleton;
import fr.istic.database.db.Database;
import fr.istic.service.MyService;

public class MainActivity extends AppCompatActivity {

    private String Tag = this.getClass().getName();
    private Context context = this;
    private Database dataBase;
    private ProgressBar progressBar;
    private TextView textView;
    private boolean downloadingComplete ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = new Database(context);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        textView = (TextView) findViewById(R.id.text_view_progress_bar);
        downloadingComplete = false;
        progressBar.setIndeterminate(true);
       initialize();
       CreatService();
    }




    public void CreatService()
    {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }
    public void initialize()
    {
        if(isNetworkAValable())
        {
            dataBase.deleteAllContents();
            textView.setText(" Downloading.... ");
            Log.i(Tag,dataBase.allTableNames().toString());
            String url = "https://data.explore.star.fr/explore/dataset/tco-busmetro-horaires-gtfs-versions-td/download/?format=json&timezone=Europe/Berlin";
            makeJsonArraygRequest(url);
        }
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

        AsyncFileDownloader downloader = new AsyncFileDownloader(context, progressBar, textView, downloadingComplete);
        downloader.execute(URL);
    }

   
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

}
