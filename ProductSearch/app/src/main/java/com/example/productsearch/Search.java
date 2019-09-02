package com.example.productsearch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CircularProgressDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.net.URL;


public class Search extends Fragment {
    String[] users = {"All", "Art", "Baby", "Books", "Clothing, Shoes & Accessories", "Computers, Tablets & Networking", "Health & Beauty", "Music", "Video Games & Console"};
    private static final String Result = "result";
//    private CircularProgressDrawable
    private LinearLayout progress, myForm;
    private Button searchBtn, clearBtn;
    private EditText keywordET, milesET, zipcodeET;
    private CheckBox newItem, used, unspecified, local, free,enableNearby;
    private TextView keywordError, zipError, tvFrom, progressText;
    private RadioGroup locationRadio;
    private RadioButton current, zipBtn;
    public static final String TAG = "MyTAG";
    String productSearchURL = "";
    private RequestQueue mQueue, ipQueue;
    String storeInfo = "";
    String zipVal = "";
    private Spinner category;
    private ProgressBar progressBar;
    String search_url = "";

    // Defining the Volley request queue that handles the URL request concurrently
//    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, container, false);
//        progress = (LinearLayout) view.findViewById(R.id.progress);
//        myForm = (LinearLayout) view.findViewById(R.id.myform);
        Button search = (Button) view.findViewById(R.id.search);
        keywordError = (TextView) view.findViewById(R.id.tvErrorKeyword);
        zipError = (TextView) view.findViewById(R.id.tvErrorZip);
        keywordET = (EditText) view.findViewById(R.id.etKeyword);
        milesET = (EditText) view.findViewById(R.id.etMiles);
        zipcodeET = (EditText) view.findViewById(R.id.etzip);
        newItem = (CheckBox) view.findViewById(R.id.newItem);
        used = (CheckBox) view.findViewById(R.id.used);
        unspecified = (CheckBox) view.findViewById(R.id.unspecified);
        local = (CheckBox) view.findViewById(R.id.local);
        free = (CheckBox) view.findViewById(R.id.free);
        enableNearby = (CheckBox) view.findViewById(R.id.enableNearby);
        locationRadio = (RadioGroup) view.findViewById(R.id.radiogp);
        current = (RadioButton) view.findViewById(R.id.currentButton);
        zipBtn = (RadioButton) view.findViewById(R.id.zipButton);
        category = (Spinner) view.findViewById(R.id.spinner1);
        clearBtn = (Button) view.findViewById(R.id.clear);
//        progressBar= (ProgressBar) view.findViewById(R.id.progressSpinner);
//        progressText = (TextView) view.findViewById(R.id.progressText);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordET.setText("");
                newItem.setChecked(false);
                used.setChecked(false);
                unspecified.setChecked(false);
                local.setChecked(false);
                free.setChecked(false);
                enableNearby.setChecked(false);
                category.setSelected(false);
                milesET.setText("");
                zipcodeET.setText("");
                current.setChecked(true);
                zipBtn.setChecked(false);
                keywordError.setVisibility(View.GONE);
                zipError.setVisibility(View.GONE);
            }
        });
        enableNearby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(enableNearby.isChecked()) {
                    milesET.setVisibility(View.VISIBLE);
                    locationRadio.setVisibility(View.VISIBLE);
                    zipcodeET.setVisibility(View.VISIBLE);

                }
                else{
                    milesET.setVisibility(View.GONE);
                    locationRadio.setVisibility(View.GONE);
                    zipcodeET.setVisibility(View.GONE);
                    zipError.setVisibility(View.GONE);

                }
            }
        });



        Spinner spin = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter <String> adapter = new ArrayAdapter <String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, users);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String keyword = keywordET.getText().toString();
//                final String keyword = "iphone";
                final boolean isNew = newItem.isChecked();
                final boolean isUsed = used.isChecked();
                final boolean isUnspecified = unspecified.isChecked();
                final boolean isLocal = local.isChecked();
                final boolean isFree = free.isChecked();
                String distance = milesET.getText().toString();
//                final String distance = "10";
                if(distance.equals("")){
                    distance = "10";
                }
                final String distanceVal = distance;
                final String zipcode = zipcodeET.getText().toString();
                final String categoryVal = category.getSelectedItem().toString();
                String categoryId="";

                switch (categoryVal){
                    case "All":
                        categoryId = "-1";
                        break;
                    case "Art":
                        categoryId = "550";
                        break;
                    case "Baby":
                        categoryId = "2984";
                        break;
                    case "Books":
                        categoryId = "267";
                        break;
                    case "Clothing, Shoes & Accessories":
                        categoryId = "11450";
                        break;
                    case "Computers, Tablets & Networking":
                        categoryId = "58058";
                        break;
                    case "Health & Beauty":
                        categoryId = "26395";
                        break;
                    case "Music":
                        categoryId = "11233";
                        break;
                    case "Video Games & Console":
                        categoryId = "1249";
                        break;
                }
                final String categoryIdVal = categoryId;
//                final String zipcode = "90007";
//                Toast.makeText(getActivity().getApplicationContext(), "keyword is:" + keyword + "isNew:" + isNew + "isUsed:+" + isUsed + "isUnspecified:" + isUnspecified + "islocal:" + isLocal + "isfree:" + isFree + "distance is:" + distance + "zipcode is:" + zipcode, Toast.LENGTH_SHORT).show();
                zipError.setVisibility(View.GONE);
                keywordError.setVisibility(View.GONE);
                Log.d(TAG, "category selected is:"+category.getSelectedItem().toString());

                if (enableNearby.isChecked() && current.isChecked()) {
                    Log.d(TAG, "current Checked");
                    ipQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    JsonObjectRequest iprequest = new JsonObjectRequest(Request.Method.GET, "http://ip-api.com/json", null, new Response.Listener <JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                zipVal = response.getString("zip");
                                Log.d(TAG, "Zipval is" + zipVal);
                                if (!keyword.equals("")) {
//

                                    final String searchURL = "https://cscinodebackend.appspot.com/getResultTable?keyword=" + keyword + "&distance=" + distanceVal + "&newone=" + isNew + "&used=" + isUsed + "&unspecified=" + isUnspecified + "&buyerPostalCode=" + zipVal + "&free=" + isFree + "&local=" + isLocal + "&category="+categoryIdVal;
                                    search_url = searchURL;
                                    sendData(keyword,search_url);
                                            Log.d(TAG, searchURL);
//


                                } else {


                                    keywordError.setVisibility(View.VISIBLE);
                                    Toast.makeText(getActivity().getApplicationContext(), "Please fix all fields with error", Toast.LENGTH_SHORT).show();


                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    ipQueue.add(iprequest);
//                    progress.setVisibility(View.INVISIBLE);

                } else if (enableNearby.isChecked() && zipBtn.isChecked()) {


                    if (isZipValid(zipcode) && !keyword.equals("")) {

//
                        final String searchURL = "https://cscinodebackend.appspot.com/getResultTable?keyword=" + keyword + "&distance=" + distanceVal + "&newone=" + isNew + "&used=" + isUsed + "&unspecified=" + isUnspecified + "&buyerPostalCode=" + zipcode + "&free=" + isFree + "&local=" + isLocal + "&category="+categoryIdVal;
                        search_url = searchURL;
                        sendData(keyword,search_url);
                        Log.d(TAG, searchURL);
//


                    } else {
                        if (!isZipValid(zipcode)) {
                            zipError.setVisibility(View.VISIBLE);
                        }

                        if (keyword.equals("")) {
                            keywordError.setVisibility(View.VISIBLE);

                        }
                        Toast.makeText(getActivity().getApplicationContext(), "Please fix all fields with error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!keyword.equals("")) {
//
                        final String searchURL = "https://cscinodebackend.appspot.com/getResultTable?keyword=" + keyword + "&newone=" + isNew + "&used=" + isUsed + "&unspecified=" + isUnspecified + "&free=" + isFree + "&local=" + isLocal + "&category="+categoryIdVal;
                        search_url = searchURL;
                        sendData(keyword,search_url);
                        Log.d(TAG, searchURL);
//


                    } else {
                        keywordError.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity().getApplicationContext(), "Please fix all fields with error", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });


                return view;
            }

            private void sendData(String keyword, String search_url) {
                //INTENT OBJ
                Intent i = new Intent(getActivity().getBaseContext(),
                        SearchResults.class);

                //PACK DATA
                i.putExtra("keyword", keyword);
//                i.putExtra("jsonObj", jsonObj);
                i.putExtra("SearchURL",search_url );



                //START ACTIVITY
                getActivity().startActivity(i);
            }

    public boolean isZipValid(String zip){

        return zip.matches("^\\d{5}(-\\d{4})?$");
    }


        }
