package com.example.productsearch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.productsearch.Search.TAG;


public class wishlist extends Fragment {
    private static final String wishlist = "wishlist";
    private Button wish;
    TextView nowishes, wishCount, wishTot;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    LinearLayout lastrow;
    static RecyclerView.Adapter recyclerViewadapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
//        context= wishlist.this;
        Log.d(TAG, GlobalWishItem.wishes.toString());
        this.recyclerView =  view.findViewById(R.id.recyclerViewWish);
        GlobalWishItem item = new GlobalWishItem();
        nowishes = view.findViewById(R.id.no_wishes);
        wishCount = view.findViewById(R.id.wishCount);
        wishTot = view.findViewById(R.id.wishTot);
        lastrow = view.findViewById(R.id.lastBar);
        nowishes.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new GridLayoutManager(getActivity()
                , 2);
        lastrow.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapter = new RecyclerViewAdapter(GlobalWishItem.wishes, getActivity());
        recyclerView.setAdapter(recyclerViewadapter);
        if((GlobalWishItem.wishes).size()==0){
            nowishes.setVisibility(View.VISIBLE);
            lastrow.setVisibility(View.GONE);

        }
        else{
            nowishes.setVisibility(View.GONE);
        }




        return view;
    }

    public void changeBar(){
        float price=0;
        int count=0;
        for (GetDataAdapter d: GlobalWishItem.wishes){

            price = price + Float.parseFloat(d.getPrice());
            count++;

        }
        String priceAnd = Float.toString(price);
        wishTot.setText(priceAnd);
        wishCount.setText(count);

    }

    public static void addWish(GetDataAdapter d){
        GlobalWishItem.wishes.add(d);

        recyclerViewadapter.notifyDataSetChanged();


    }

    public static void removeWish(GetDataAdapter d){
        GlobalWishItem.removeFromWishList(d);
        recyclerViewadapter.notifyDataSetChanged();


    }


}
