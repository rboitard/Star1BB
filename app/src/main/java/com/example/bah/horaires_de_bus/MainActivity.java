package com.example.bah.horaires_de_bus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bah.horaires_de_bus.dataBase.db.DataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String Tag = this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBase dataBase = new DataBase(this);
        Log.i(Tag,dataBase.allTableNames().toString());
        String url = "https://data.explore.star.fr/explore/dataset/tco-busmetro-horaires-gtfs-versions-td/download/?format=json&timezone=Europe/Berlin";
        //makeJsonObjReq( url);
        makeJsonArraygRequest(url);
    }


    private void makeJsonObjReq( String URl) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(Tag,"response :"+response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(Tag, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjReq);
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

        Log.i(Tag,"response :"+URL);
    }
}
