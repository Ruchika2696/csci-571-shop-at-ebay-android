package com.example.productsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.example.productsearch.Search.TAG;


public class Product extends Fragment {
    JSONObject productDetailObj = null;
    private LinearLayout mGallery;
    private ListView lv;
    TextView title, price, subtitle, priceVal, brand, shipping;
    String imageUrls;
    ArrayList<String> myImageList = new ArrayList<String>();
    List<String> mySpec = new ArrayList<String>();
    private LayoutInflater mInflater;
    private HorizontalScrollView horizontalScrollView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container2, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_product, container2, false);
        title = (TextView) view.findViewById(R.id.productTitle);
        price = (TextView) view.findViewById(R.id.productPrice);
        shipping = (TextView) view.findViewById(R.id.productShipping);
        subtitle = (TextView) view.findViewById(R.id.subTitle);
        priceVal = (TextView) view.findViewById(R.id.priceValue);
        brand = (TextView) view.findViewById(R.id.brandValue);
        lv = (ListView) view.findViewById(R.id.specList);

        String productData = getArguments().getString("productDetailJsonString");
        String productTitle = getArguments().getString("productTitle");
        String shippingOption = getArguments().getString("shippingOption");

        Log.d(TAG, "in Product"+productData);
//        String getArgument = getArguments().getString("data");
        try {
            this.productDetailObj = new JSONObject(productData);
            Log.d(TAG,"Successful"+productDetailObj.toString());

            title.setText(productTitle);
            price.setText(productDetailObj.getJSONObject("Item").getJSONObject("CurrentPrice").getString("Value"));
            shipping.setText("With "+shippingOption);
//            subtitle.setText(productDetailObj.getJSONObject("Item").getJSONObject("Subtitle"));
            priceVal.setText(productDetailObj.getJSONObject("Item").getJSONObject("CurrentPrice").getString("Value"));
            for(int i=0; i<productDetailObj.getJSONObject("Item").getJSONObject("ItemSpecifics").getJSONArray("NameValueList").length(); i++){
                if(productDetailObj.getJSONObject("Item").getJSONObject("ItemSpecifics").getJSONArray("NameValueList").getJSONObject(i).getString("Name").equals("Brand")){
                    brand.setText(productDetailObj.getJSONObject("Item").getJSONObject("ItemSpecifics").getJSONArray("NameValueList").getJSONObject(i).getJSONArray("Value").getString(0));

                }
                else{
                    mySpec.add(productDetailObj.getJSONObject("Item").getJSONObject("ItemSpecifics").getJSONArray("NameValueList").getJSONObject(i).getJSONArray("Value").getString(0));
                }
            }

            for(int i=0; i< productDetailObj.getJSONObject("Item").getJSONArray("PictureURL").length(); i++){
                myImageList.add(productDetailObj.getJSONObject("Item").getJSONArray("PictureURL").getString(i));

            }
            ViewPager viewPager = view.findViewById(R.id.view_pager);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), myImageList);
            viewPager.setAdapter(viewPagerAdapter);
            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.bullet,

                    mySpec );

            lv.setAdapter(arrayAdapter);



        }
        catch(Exception e){
            Log.d(TAG, "error occured");
        }


        return view;
    }
}
