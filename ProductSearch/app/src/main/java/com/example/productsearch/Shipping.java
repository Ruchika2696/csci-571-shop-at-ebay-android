package com.example.productsearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wssholmes.stark.circular_score.CircularScoreView;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.productsearch.SearchResults.TAG;


public class Shipping extends Fragment {

    TextView store_name, feedbackStar, popularity, feedbackScore, shippingCost, globalShipping, handlingTime, condition, policy, returnWithin, refundMode, shippedBy;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container2, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipping, container2, false);

        Log.d(TAG, "IN SHIPPINGGGG");
        String shippingInfo = getArguments().getString("shippingInfo");
        String sellerInfo = getArguments().getString("sellerInfo");
        String storeInfo = getArguments().getString("storeInfo");
        Log.d(TAG, "shipping info in shippig is "+shippingInfo);
        Log.d(TAG, "seller info in shippig is "+sellerInfo);
        try {
            JSONObject ship = new JSONObject(shippingInfo);
            JSONArray seller = new JSONArray(sellerInfo);
            final JSONArray storeInfoJson = new JSONArray(storeInfo);
            store_name = (TextView) view.findViewById(R.id.store_name_value);
            feedbackScore = (TextView) view.findViewById(R.id.feedback_score_value);
//            popularity = (TextView) view.findViewById(R.id.popularity_value);
            feedbackStar = (TextView) view.findViewById(R.id.feedback_star_value);
            shippingCost = (TextView) view.findViewById(R.id.shipping_cost_value);
            globalShipping = (TextView) view.findViewById(R.id.global_shipping_value);
            handlingTime = (TextView) view.findViewById(R.id.handling_time_value);
            condition = (TextView) view.findViewById(R.id.ship_condition_value);
            policy = (TextView) view.findViewById(R.id.policy_value);
            returnWithin = (TextView) view.findViewById(R.id.returns_within_value);
            refundMode = (TextView) view.findViewById(R.id.refund_mode_value);
            shippedBy = (TextView) view.findViewById(R.id.shipped_by_value);
            CircularScoreView circularScoreView = (CircularScoreView) view.findViewById(R.id.two);


//            store_name.setText(ship.getJSONArray());
//            feedbackScore.setText(seller.getJSONObject(0).getJSONArray("feedbackScore").getString(0));
            circularScoreView.setScore(Integer.parseInt(seller.getJSONObject(0).getJSONArray("feedbackScore").getString(0)));
            circularScoreView.setTextColor(1);


            popularity.setText(seller.getJSONObject(0).getJSONArray("positiveFeedbackPercent").getString(0));
            feedbackStar.setText(seller.getJSONObject(0).getJSONArray("feedbackRatingStar").getString(0));
            if(ship.getJSONArray("shippingServiceCost").getJSONObject(0).getString("__value__").equals("0.0")){
                shippingCost.setText("Free Shipping");
            }
            else {
                shippingCost.setText(ship.getJSONArray("shippingServiceCost").getJSONObject(0).getString("__value__"));
            }
            if(ship.getJSONArray("shipToLocations").getString(0).equals("Worldwide")){
                globalShipping.setText("Yes");
            }
            else{
                globalShipping.setText("No");
            }

            if (ship.getJSONArray("handlingTime").getString(0).equals("0") || ship.getJSONArray("handlingTime").getString(0).equals("1")) {
                handlingTime.setText(ship.getJSONArray("handlingTime").getString(0) + " day");

            }
            else {
                handlingTime.setText(ship.getJSONArray("handlingTime").getString(0)+" days");
            }
            store_name.setText(storeInfoJson.getJSONObject(0).getJSONArray("storeName").getString(0));
            store_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    try {

                        intent.setData(Uri.parse(storeInfoJson.getJSONObject(0).getJSONArray("storeURL").getString(0)));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }




        return view;
    }
}