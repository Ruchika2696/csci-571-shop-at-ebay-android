package com.example.productsearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.productsearch.Search.TAG;


public class Photos extends Fragment {
    ArrayList<String> Img_url= new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager Manager;
    Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container2, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container2, false);
        String photosObj = getArguments().getString("photoJson");
        Log.d(TAG,"Photos in Photo fragment are"+photosObj);
        try {
            JSONObject images = new JSONObject(photosObj);

            for (int i = 0; i <images.getJSONArray("items").length(); i++) {
                Img_url.add(images.getJSONArray("items").getJSONObject(i).getString("link"));

            }


            this.recyclerView = view.findViewById(R.id.recyclerView);
            Manager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(Manager);
            adapter = new Adapter(Img_url, getActivity().getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return view;
    }




}