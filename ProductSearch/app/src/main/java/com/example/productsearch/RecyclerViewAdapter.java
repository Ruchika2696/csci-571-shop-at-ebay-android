package com.example.productsearch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import static com.example.productsearch.Search.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    String iD = "";
    String itemId = "";
    String productTitle = "";
    String shippingInfo = "";
    String itemUrl="";
    String shippingOption = "";
    String sellerInfo = "";
    String storeInfo="";
    String wishCount = "";
    String wishTot="";
    String globalShip="";
    String conditionShip="";
    String policy="", within="", refund="", shippedBy="";
    JSONObject productDetail;
    GetDataAdapter wishadapter;

    private RequestQueue mQueue, photoQueue, similarQueue;
    List<GetDataAdapter> getDataAdapter;

    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;

//        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);
//        this.itemId = getDataAdapter1.getTitle();
//        binding.executePendingBindings();
        String title = getDataAdapter1.getTitle();
        if(title.length()>46){
            holder.TitleTextView.setText(title.substring(0,46)+"...");
        }
        else {
            holder.TitleTextView.setText(getDataAdapter1.getTitle());
        }
        this.productTitle = getDataAdapter1.getTitle();
        holder.PriceTextView.setText("$"+getDataAdapter1.getPrice());
        holder.ZipTextView.setText("Zip: " +getDataAdapter1.getZip());
        holder.ConditionTextView.setText(getDataAdapter1.getCondition());
        holder.ShippingTextView.setText(getDataAdapter1.getShipping());

        if(GlobalWishItem.check(getDataAdapter1.getId())){
            holder.wishBtn.setVisibility(View.GONE);
            holder.wishRemoveBtn.setVisibility(View.VISIBLE);
        }
        holder.wishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataAdapter getDataAdapter = new GetDataAdapter();
                getDataAdapter.setTitle(getDataAdapter1.getTitle());
                getDataAdapter.setPrice(getDataAdapter1.getPrice());
                getDataAdapter.setZip(getDataAdapter1.getZip());
                getDataAdapter.setCondition(getDataAdapter1.getCondition());
                getDataAdapter.setShipping(getDataAdapter1.getShipping());
                getDataAdapter.setAndroid_image_url(getDataAdapter1.getAndroid_image_url());
                getDataAdapter.setId(getDataAdapter1.getId());
                getDataAdapter.setShippingInfo(getDataAdapter1.getShippingInfo());
                getDataAdapter.setSellerInfo(getDataAdapter1.getSellerInfo());
                wishlist.addWish(getDataAdapter);
                Toast.makeText(context, getDataAdapter1.getTitle()+"added", Toast.LENGTH_SHORT).show();
                holder.wishBtn.setVisibility(View.GONE);
                holder.wishRemoveBtn.setVisibility(View.VISIBLE);
                Log.d(TAG, GlobalWishItem.wishes.toString());
            }
        });

        holder.wishRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataAdapter getDataAdapter = new GetDataAdapter();
                getDataAdapter.setTitle(getDataAdapter1.getTitle());
                getDataAdapter.setPrice(getDataAdapter1.getPrice());
                getDataAdapter.setZip(getDataAdapter1.getZip());
                getDataAdapter.setCondition(getDataAdapter1.getCondition());
                getDataAdapter.setShipping(getDataAdapter1.getShipping());
                getDataAdapter.setAndroid_image_url(getDataAdapter1.getAndroid_image_url());
                getDataAdapter.setId(getDataAdapter1.getId());
                getDataAdapter.setSellerInfo(getDataAdapter1.getSellerInfo());
                getDataAdapter.setShippingInfo(getDataAdapter.getShippingInfo());
                wishlist.removeWish(getDataAdapter);
                Toast.makeText(context, getDataAdapter1.getTitle()+"removed", Toast.LENGTH_SHORT).show();
                holder.wishRemoveBtn.setVisibility(View.GONE);
                holder.wishBtn.setVisibility(View.VISIBLE);



            }
        });
        this.shippingOption = getDataAdapter1.getShipping();
        this.iD = getDataAdapter1.getId();
        this.shippingInfo = getDataAdapter1.getShippingInfo();
        this.globalShip =getDataAdapter1.getGlobalShip();
        this.conditionShip = getDataAdapter1.getConditionShip();
        this.policy = getDataAdapter1.getPolicy();
        this.within = getDataAdapter1.getWithin();
        this.refund = getDataAdapter1.getRefund();
        this.shippedBy=getDataAdapter1.getShippedBy();

        this.sellerInfo = getDataAdapter1.getSellerInfo();
        Picasso.with(context).load((getDataAdapter1.getAndroid_image_url())).placeholder(R.drawable.ic_launcher_foreground).fit().into(holder.img_android);
    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

//        public TextView IdTextView;
        public TextView TitleTextView;
        public TextView ZipTextView;
        public TextView PriceTextView;
        public  TextView ShippingTextView;
        public  TextView ConditionTextView, wishCount, wishTot;
        public ImageView img_android;
        public ImageButton wishBtn, wishRemoveBtn;



        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
            TitleTextView = (TextView) itemView.findViewById(R.id.titleTv1) ;
            PriceTextView = (TextView) itemView.findViewById(R.id.priceTv) ;
            ZipTextView = (TextView) itemView.findViewById(R.id.zipTv) ;
            ConditionTextView = (TextView) itemView.findViewById(R.id.conditionTv);
            ShippingTextView = (TextView) itemView.findViewById(R.id.shippingTv);
            img_android = (ImageView)itemView.findViewById(R.id.img_android);
            wishBtn = (ImageButton) itemView.findViewById(R.id.wishbtn);
            wishRemoveBtn = (ImageButton) itemView.findViewById(R.id.removeWishbtn);
            wishCount = (TextView) itemView.findViewById(R.id.wishCount);
            wishTot = (TextView) itemView.findViewById(R.id.wishTot);




        }

        void showDetail(View v, final String item_id, final String title, final String itemUrl){
            Log.d(TAG, "id is"+item_id);
            String url = "https://cscinodebackend.appspot.com/getProductDetail?id="+item_id;
            mQueue = Volley.newRequestQueue(context);
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener <JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d(TAG,"details of product are"+response.toString());
                            Log.d(TAG, "title is"+title);
                            productDetail = response;


                            String photourl = "https://cscinodebackend.appspot.com/getProductPhotos?title="+title;
                            photoQueue = Volley.newRequestQueue(context);
                            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, photourl, null,
                                    new Response.Listener <JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d(TAG, "photos json is:" + response.toString());
                                            Log.d(TAG, "again product detail is"+productDetail.toString());
                                            final String photoResponse = response.toString();


                                            String similarUrl = "https://cscinodebackend.appspot.com/getSimilarItems?id="+item_id;
                                            Log.d(TAG, "similar url is"+ similarUrl);
                                            similarQueue = Volley.newRequestQueue(context);
                                            final JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, similarUrl, null,
                                                    new Response.Listener <JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            Log.d(TAG, "similar response is" + response.toString());

                                                            sendData(productDetail.toString(),photoResponse, response.toString(), itemUrl);
                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    error.printStackTrace();
                                                }
                                            });


                                                similarQueue.add(request2);








                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                }
                            });
                            photoQueue.add(request);


                        }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(request);



        }


        @Override
        public void onClick(View v) {

//            Log.d(TAG, "position is"+getAdapterPosition());
            productTitle = getDataAdapter.get(getAdapterPosition()).getTitle();
            shippingOption = getDataAdapter.get(getAdapterPosition()).getShipping();
            shippingInfo = getDataAdapter.get(getAdapterPosition()).getShippingInfo();
            globalShip = getDataAdapter.get(getAdapterPosition()).getGlobalShip();
            policy=getDataAdapter.get(getAdapterPosition()).getPolicy();
            conditionShip = getDataAdapter.get(getAdapterPosition()).getConditionShip();
            refund =getDataAdapter.get(getAdapterPosition()).getRefund();
            within = getDataAdapter.get(getAdapterPosition()).getWithin();
            shippedBy = getDataAdapter.get(getAdapterPosition()).getShippedBy();
            sellerInfo = getDataAdapter.get(getAdapterPosition()).getSellerInfo();
            storeInfo = getDataAdapter.get(getAdapterPosition()).getStoreInfo();
            Log.d(TAG, "id is"+getDataAdapter.get(getAdapterPosition()).getId());
            itemUrl = getDataAdapter.get(getAdapterPosition()).getItemUrl();
            showDetail(v, getDataAdapter.get(getAdapterPosition()).getId(), getDataAdapter.get(getAdapterPosition()).getTitle(), getDataAdapter.get(getAdapterPosition()).getItemUrl());
//            showPhoto(v, productTitle);


        }
    }

    private void sendData(String jsonObj, String photoObj, String similarResponse, String itemUrl) {
        //INTENT OBJ
        Intent intent = new Intent (context, ProductDetail.class);
        Log.d(TAG,"in send data" + productTitle);
        Log.d(TAG,"in send data" + productTitle);

        //PACK DATA


        intent.putExtra("productDetailObj", jsonObj);
        intent.putExtra("productTitle", productTitle );
        intent.putExtra("photoJson", photoObj);
        intent.putExtra("shippingOption", shippingOption);
        intent.putExtra("globalShip", globalShip);
        intent.putExtra("policy", policy);
        intent.putExtra("conditionShip", conditionShip);
        intent.putExtra("refund", refund);
        intent.putExtra("within", within);
        intent.putExtra("shippedBy", shippedBy);




        intent.putExtra("shippingInfo", shippingInfo);
        intent.putExtra("sellerInfo", sellerInfo);
        intent.putExtra("storeInfo", storeInfo);
        intent.putExtra("similarObj", similarResponse);
        intent.putExtra("itemUrl", itemUrl);




        //START ACTIVITY
        context.startActivity(intent);

    }


}
