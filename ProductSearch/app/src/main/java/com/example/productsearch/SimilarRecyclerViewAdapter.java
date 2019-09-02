package com.example.productsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SimilarRecyclerViewAdapter extends RecyclerView.Adapter<SimilarRecyclerViewAdapter.ViewHolder> {

    Context context;
    String title;
    String price;
    String shipping;
    String days;
    String image_url;
    List <GetSimilarAdapter> getDataAdapter;
    public void sortByName(){
        Collections.sort(getDataAdapter, new Comparator <GetSimilarAdapter>() {
            @Override
            public int compare(GetSimilarAdapter o1, GetSimilarAdapter o2) {
                return o1.title.compareTo(o2.title);
            }
        });
        notifyDataSetChanged();

    }

    public void sortByPrice(){
        Collections.sort(getDataAdapter, new Comparator <GetSimilarAdapter>() {
            @Override
            public int compare(GetSimilarAdapter o1, GetSimilarAdapter o2) {
                return o1.price.compareTo(o2.price);
            }
        });
        notifyDataSetChanged();

    }

    public void sortByDays(){
        Collections.sort(getDataAdapter, new Comparator <GetSimilarAdapter>() {
            @Override
            public int compare(GetSimilarAdapter o1, GetSimilarAdapter o2) {
                return o1.days.compareTo(o2.days);
            }
        });
        notifyDataSetChanged();

    }

    public void reversesortByName(){
        Collections.sort(getDataAdapter, new Comparator <GetSimilarAdapter>() {
            @Override
            public int compare(GetSimilarAdapter o1, GetSimilarAdapter o2) {
                return o1.title.compareTo(o2.title);
            }
        });
        Collections.reverse(getDataAdapter);
        notifyDataSetChanged();

    }

    public void reversesortByPrice(){
        Collections.sort(getDataAdapter, new Comparator <GetSimilarAdapter>() {
            @Override
            public int compare(GetSimilarAdapter o1, GetSimilarAdapter o2) {
                return o1.price.compareTo(o2.price);
            }
        });
        Collections.reverse(getDataAdapter);
        notifyDataSetChanged();

    }

    public void reversesortByDays(){
        Collections.sort(getDataAdapter, new Comparator <GetSimilarAdapter>() {
            @Override
            public int compare(GetSimilarAdapter o1, GetSimilarAdapter o2) {
                return o1.days.compareTo(o2.days);
            }
        });
        Collections.reverse(getDataAdapter);
        notifyDataSetChanged();

    }

    public SimilarRecyclerViewAdapter(List <GetSimilarAdapter> getDataAdapter, Context context) {

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }



    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview2_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GetSimilarAdapter getDataAdapter1 = getDataAdapter.get(position);
        holder.title.setText(getDataAdapter1.getTitle());
        holder.price.setText("$"+getDataAdapter1.getPrice());
        holder.days.setText(getDataAdapter1.getDays());
        holder.shipping.setText(getDataAdapter1.getShipping());
        Picasso.with(context).load((getDataAdapter1.getImage_url())).placeholder(R.drawable.ic_launcher_foreground).fit().into(holder.similarImg);
    }

    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }

    public void printHi()
    {

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, price, days, shipping;
        public ImageView similarImg;

        public ViewHolder(View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.similarTitle);
            price = (TextView) itemView.findViewById(R.id.similarPrice);
            days = (TextView) itemView.findViewById(R.id.similarDays);
            shipping = (TextView) itemView.findViewById(R.id.similarShipping);
            similarImg = (ImageView) itemView.findViewById(R.id.similarImg);


        }

        @Override
        public void onClick(View v) {
        }


    }

}
