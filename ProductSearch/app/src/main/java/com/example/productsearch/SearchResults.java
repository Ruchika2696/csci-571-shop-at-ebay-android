package com.example.productsearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class SearchResults extends AppCompatActivity {
    public static final String TAG = "MyTAG";
    List<GetDataAdapter> GetDataAdapter1;
    LinearLayout firstLine;
    ImageButton goBackToResults;
    TextView count, orangeKeyword, errorMsg;
    String id = "";
    String itemUrl="";
    String shippingInfo = "";
    String globalShip="";
    String conditionShip="";
    String policy="", within="", refund="", shippedBy="";
    String sellerInfo = "", storeInfo="";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
//    ProgressBar progressBar;
    LinearLayout progress;
    RequestQueue mQueue;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SearchResults.this;
        setContentView(R.layout.activity_search_results);
        GetDataAdapter1 = new ArrayList<>();
        goBackToResults = findViewById(R.id.goBackToResults);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

//        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        recyclerView.setHasFixedSize(true);


        recyclerViewlayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);
//        firstLine = findViewById(R.id.firstLine);
        progress = findViewById(R.id.progress);

        progress.setVisibility(View.VISIBLE);
        errorMsg = findViewById(R.id.errormsg);
        goBackToResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "back clicked");
                finish();
            }
        });
//        count = findViewById(R.id.count);
//        orangeKeyword = findViewById(R.id.orangeKeyword);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        progressBar = findViewById(R.id.)
        setSupportActionBar(toolbar);

//        progressBar.setVisibility(View.VISIBLE);
        receiveData();
//        progressBar.setVisibility(View.GONE);


    }

    private void receiveData() {
        //RECEIVE DATA VIA INTENT
        Intent i = getIntent();
        final String name = i.getStringExtra("keyword");
        String dataObj = i.getStringExtra("jsonObj");
        String searchUrl = i.getStringExtra("SearchURL");
        Log.d(TAG, "url in search results"+searchUrl);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, searchUrl, null,
                new Response.Listener <JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("findItemsAdvancedResponse");
                            if(jsonArray.getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getString("@count").equals("0")){
                                errorMsg.setVisibility(View.VISIBLE);
                                progress.setVisibility(View.GONE);
                            }
                            else {
                                Log.d(TAG, jsonArray.toString());


//                            sendData(keyword, jsonArray.toString());


                                JSONObject itemObj = null;
                                try {
//                                JSONArray array = new JSONArray(response);

                                    JSONArray searchResult = jsonArray.getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item");
                                    Log.d(TAG, searchResult.toString());
                                    progress.setVisibility(View.GONE);
//                                count.setText(searchResult.length());
//                                orangeKeyword.setText(name);
//                                firstLine.setVisibility(View.VISIBLE);

                                    for (int j = 0; j < searchResult.length(); j++) {

                                        GetDataAdapter GetDataAdapter2 = new GetDataAdapter();
                                        JSONObject json = null;
                                        try {
                                            json = searchResult.getJSONObject(j);
                                            GetDataAdapter2.setTitle(json.getJSONArray("title").getString(0));
                                            GetDataAdapter2.setZip(json.getJSONArray("postalCode").getString(0));
                                            Log.d(TAG, "shipping is" + (json.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingType").getString(0)));

                                            shippingInfo = (json.getJSONArray("shippingInfo").getJSONObject(0)).toString();
                                            GetDataAdapter2.setShippingInfo(shippingInfo);
                                            if ((json.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingType").getString(0)).equals("Calculated")) {
                                                GetDataAdapter2.setShipping(json.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingServiceCost").getJSONObject(0).getString("__value__"));
                                            } else {
                                                GetDataAdapter2.setShipping((json.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingType").getString(0)) + " Shipping");
                                            }
                                            GetDataAdapter2.setPrice(json.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice").getJSONObject(0).getString("__value__"));
//                    GetDataAdapter2.setShipping(json.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingServiceCost").getJSONObject(0).getString("__value__"));

                                            if ((json.getJSONArray("condition").getJSONObject(0).getJSONArray("conditionDisplayName").getString(0)).equals("Seller refurbished") || (json.getJSONArray("condition").getJSONObject(0).getJSONArray("conditionDisplayName").getString(0)).equals("Manufacturer refurbished")) {
                                                GetDataAdapter2.setCondition("Refurbished");
                                            } else {
                                                GetDataAdapter2.setCondition(json.getJSONArray("condition").getJSONObject(0).getJSONArray("conditionDisplayName").getString(0));
                                            }

                                            GetDataAdapter2.setAndroid_image_url(json.getJSONArray("galleryURL").getString(0));
//                    GetDataAdapter2.setSubject(json.getJSONArray("galleryURL").getString(0));
                                            GetDataAdapter2.setId(json.getJSONArray("itemId").getString(0));
                                            id = json.getJSONArray("itemId").getString(0);
                                            GetDataAdapter2.setItemUrl(json.getString("viewItemURL"));
                                            Log.d(TAG, "item url is" + json.getString("viewItemURL"));
                                            sellerInfo = json.getJSONArray("sellerInfo").toString();
                                            GetDataAdapter2.setSellerInfo(sellerInfo);
//                                            policy=json.getJSONObject("ReturnPolicy").getString("ReturnsAccepted");
//                                            within=json.getJSONObject("ReturnPolicy").getString("ReturnsWithin");
//                                            refund =json.getJSONObject("ReturnPolicy").getString("Refund");
//                                            shippedBy = json.getJSONObject("ReturnPolicy").getString("ShippingCostPaidBy");
                                            storeInfo = json.getJSONArray("storeInfo").toString();
                                            Log.d(TAG,"storeInfo is"+storeInfo.toString());
//                                            globalShip = json.getString("GlobalShipping");
                                            String shipCondition = json.getString("ConditionDescription");
                                            GetDataAdapter2.setPolicy("");
                                            GetDataAdapter2.setRefund("");
                                            GetDataAdapter2.setWithin("");
                                            GetDataAdapter2.setShippedBy("");
                                            GetDataAdapter2.setStoreInfo(storeInfo);
                                            GetDataAdapter2.setGlobalShip("");
                                            GetDataAdapter2.setConditionShip("");
                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }
                                        GetDataAdapter1.add(GetDataAdapter2);
                                    }

                                    recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, context);
                                    recyclerView.setAdapter(recyclerViewadapter);

                                    Log.d(TAG, jsonArray.toString());

                                } catch (Exception e) {
                                    Log.d(TAG, "Error ocuured");

                                }
                            }
//                            Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();








                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);












    }




}



