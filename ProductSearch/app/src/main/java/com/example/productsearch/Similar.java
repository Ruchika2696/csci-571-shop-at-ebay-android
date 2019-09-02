package com.example.productsearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.productsearch.Search.TAG;


public class Similar extends Fragment {
    RecyclerView recyclerView, recyclerView2;
    RecyclerView.LayoutManager recyclerViewlayoutManager,recyclerViewlayoutManager2;
    SimilarRecyclerViewAdapter recyclerViewadapter, recyclerViewadapter2;
    List<GetSimilarAdapter> GetSimilarAdapter1, GetSimilarAdapter2;
    String[] sortBy = {"Default", "Name", "Price", "Days"};
    String[] sortOrder = {"Ascending", "Descending"};
    private Spinner sortBySpinner, sortOrderSpinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container2, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_similar, container2, false);
        String similarObj = getArguments().getString("similarObj");
        Log.d(TAG, "similar in similar is "+similarObj);
        GetSimilarAdapter1 = new ArrayList<>();
        GetSimilarAdapter2 = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView2 = view.findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);


        recyclerViewlayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewlayoutManager2 = new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerView2.setLayoutManager(recyclerViewlayoutManager2);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        sortBySpinner = view.findViewById(R.id.sortBy);
//        sortBySpinner.setSelected();
        sortOrderSpinner = view.findViewById(R.id.sortOrder);
        sortOrderSpinner.setEnabled(false);
        sortOrderSpinner.setClickable(false);


        receiveData(similarObj);


        ArrayAdapter<String> adapter = new ArrayAdapter <String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, sortBy);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(adapter);
        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter <String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, sortOrder);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortOrderSpinner.setAdapter(adapter1);
        sortOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });

        if(sortBySpinner.getSelectedItem().toString().equals("Default")){
            sortOrderSpinner.setEnabled(false);
            sortOrderSpinner.setClickable(false);
            recyclerView.setVisibility(View.GONE);
        }

        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                if(sortBySpinner.getSelectedItem().equals("Default")){
                    Log.d(TAG,"Default selected");
                    sortOrderSpinner.setEnabled(false);
                    sortOrderSpinner.setClickable(false);
                    recyclerView2.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
                else{
                    sortOrderSpinner.setEnabled(true);
                    sortOrderSpinner.setClickable(true);
                    recyclerView.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    if(sortBySpinner.getSelectedItem().equals("Name")){
                        Log.d(TAG, "Name selected");
                        if(sortOrderSpinner.getSelectedItem().equals("Ascending")) {
                            recyclerViewadapter2.sortByName();
                        }
                        else{
                            recyclerViewadapter2.reversesortByName();
                        }
                    }
                    if(sortBySpinner.getSelectedItem().equals("Price")){
                        if(sortOrderSpinner.getSelectedItem().equals("Ascending")) {
                            recyclerViewadapter2.sortByPrice();
                        }
                        else{
                            recyclerViewadapter2.reversesortByPrice();
                        }
                    }
                    if(sortBySpinner.getSelectedItem().equals("Days")){
                        if(sortOrderSpinner.getSelectedItem().equals("Ascending")) {

                            recyclerViewadapter2.sortByDays();
                        }
                        else{
                            recyclerViewadapter2.reversesortByDays();
                        }
                    }

                }

                sortOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                        if(sortOrderSpinner.getSelectedItem().equals("Ascending")){
                           if(sortBySpinner.getSelectedItem().equals("Name")){
                               recyclerViewadapter2.sortByName();
                           }
                            if(sortBySpinner.getSelectedItem().equals("Price")){
                                recyclerViewadapter2.sortByPrice();
                            }
                            if(sortBySpinner.getSelectedItem().equals("Days")){
                                recyclerViewadapter2.sortByDays();
                            }
                        }
                        else{

                            if(sortBySpinner.getSelectedItem().equals("Name")){
                                recyclerViewadapter2.reversesortByName();
                            }
                            if(sortBySpinner.getSelectedItem().equals("Price")){
                                recyclerViewadapter2.reversesortByPrice();
                            }
                            if(sortBySpinner.getSelectedItem().equals("Days")){
                                recyclerViewadapter2.reversesortByDays();
                            }

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView <?> parent) {

                    }
                });
            }



            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });


        return view;
    }

    private void receiveData(String similarObj) {
        //RECEIVE DATA VIA INTENT

      try {
          JSONObject itemObj = new JSONObject(similarObj);
          JSONArray items = itemObj.getJSONObject("getSimilarItemsResponse").getJSONObject("itemRecommendations").getJSONArray("item");
          JSONArray itemsCopy = itemObj.getJSONObject("getSimilarItemsResponse").getJSONObject("itemRecommendations").getJSONArray("item");
          Log.d(TAG, "Similar item array is "+items);

          for(int i=0; i<items.length(); i++){
              GetSimilarAdapter similarAdapter = new GetSimilarAdapter();
              JSONObject json = null;
              try{
                  json = items.getJSONObject(i);

                  similarAdapter.setTitle(json.getString("title"));
                  similarAdapter.setPrice(json.getJSONObject("buyItNowPrice").getString("__value__"));
                  similarAdapter.setImage_url(json.getString("imageURL"));
                  similarAdapter.setDays(json.getString("timeLeft").substring((json.getString("timeLeft").indexOf('P'))+1, (json.getString("timeLeft").indexOf('D')))+" Days Left");

                  if(json.getJSONObject("shippingCost").getString("__value__").equals("0.00")){
                      similarAdapter.setShipping("Free Shipping");
                  }
                  else{
                      similarAdapter.setShipping(json.getJSONObject("shippingCost").getString("__value__"));
                  }


              }
              catch(Exception e){

                  e.printStackTrace();

              }
              GetSimilarAdapter1.add(similarAdapter);

          }
          recyclerViewadapter = new SimilarRecyclerViewAdapter(GetSimilarAdapter1, getActivity());
          recyclerView.setAdapter(recyclerViewadapter);
          recyclerViewadapter2 = new SimilarRecyclerViewAdapter(GetSimilarAdapter2, getActivity());
          recyclerView2.setAdapter(recyclerViewadapter2);



          for(int i=0; i<itemsCopy.length(); i++){
              GetSimilarAdapter similarAdapter = new GetSimilarAdapter();
              JSONObject json = null;
              try{
                  json = items.getJSONObject(i);

                  similarAdapter.setTitle(json.getString("title"));
                  similarAdapter.setPrice(json.getJSONObject("buyItNowPrice").getString("__value__"));
                  similarAdapter.setImage_url(json.getString("imageURL"));
                  similarAdapter.setDays(json.getString("timeLeft").substring((json.getString("timeLeft").indexOf('P')), (json.getString("timeLeft").indexOf('D'))));

                  if(json.getJSONObject("shippingCost").getString("__value__").equals("0.00")){
                      similarAdapter.setShipping("Free Shipping");
                  }
                  else{
                      similarAdapter.setShipping(json.getJSONObject("shippingCost").getString("__value__"));
                  }


              }
              catch(Exception e){
                  e.printStackTrace();
              }
              GetSimilarAdapter2.add(similarAdapter);

          }
          recyclerViewadapter2 = new SimilarRecyclerViewAdapter(GetSimilarAdapter2, getActivity());
          recyclerView2.setAdapter(recyclerViewadapter2);

//          Log.d(TAG, array.toString());

      }
      catch(Exception e){
          e.printStackTrace();
      }
    }
}
