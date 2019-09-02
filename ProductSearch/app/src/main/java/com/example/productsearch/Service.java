package com.example.productsearch;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.productsearch.Search.TAG;

public class Service {
//    String url;
    private JSONObject jsonObj;
    Context context;
    public JSONObject myobj;
    int flag = -1;
    private RequestQueue mQueue;

    public Service(Context context, String url) {
        this.context = context;
        this.mQueue = Volley.newRequestQueue(context);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener <JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        jsonObj = response;
                        Log.d(TAG, "in on response"+jsonObj.toString());
                        getData(jsonObj);
                    }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        this.mQueue.add(request);




    }

    void getData(JSONObject obj){
        Log.d(TAG, "in function"+obj.toString());
        this.myobj = obj;
    }

    void printJSON(){
        Log.d(TAG, "in print"+this.myobj.toString());
    }

//    JSONObject getJsonData(){
//        Log.d(TAG, "befotre returning json is");
//        Log.d(TAG, this.jsonObj.toString());
//        return this.jsonObj;
//    }
}
