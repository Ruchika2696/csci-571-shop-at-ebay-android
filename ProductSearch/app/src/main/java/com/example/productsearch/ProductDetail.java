package com.example.productsearch;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.productsearch.Search.TAG;

public class ProductDetail extends AppCompatActivity {

    private static final String TAG = "ProductDetail Activity";
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    FloatingActionButton addWish, removeWish;
    TextView toolbarTitle;
    ImageView fb;
    String productTitle = "", itemUrl="";
    String shippingOption = "";
    String sellerInfo = "";
    String storeInfo = "";
    String productDetailData = "";
    String shippingInfo = "";
    String similarObj = "";
    String[] imageUrls;
    String productPhotoJson = "";
    ImageButton goBack;
    private RequestQueue mQueue, photoQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Log.d(TAG, "onCreate: Starting");
        fb = findViewById(R.id.shareOnFb);
        goBack = findViewById(R.id.goBackToItems);
        addWish = findViewById(R.id.fab);
        removeWish = findViewById(R.id.fab1);
        toolbarTitle = findViewById(R.id.productDetailtoolbar_title);
//        Picasso.with(getApplicationContext()).load("http://csci571.com/hw/hw8/Images/facebook.png").into();
        receiveData();
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager =  (ViewPager) findViewById(R.id.container2);
        setupViewPager(viewPager);
        addWish.show();
        removeWish.hide();
        addWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addWish.hide();
                removeWish.show();
//                wishlist.addWish();

            }
        });
        removeWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeWish.hide();
                addWish.show();
//                wishlist.removeWish();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                Log.d(TAG, "facebook url is"+itemUrl);
                try {
                    JSONArray a = new JSONArray(itemUrl);
                    itemUrl = a.getString(0);
                }catch(Exception e){
                    e.printStackTrace();
                }

                intent.setData(Uri.parse("https://www.facebook.com/dialog/share?app_id=368802450377566&href="+Uri.parse(itemUrl)+"&quote=this%20is%20the%20"+productTitle+"&hashtag=%23CSCI571Spring2019Ebay"));
                startActivity(intent);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(viewPager);


            }

    private void setupViewPager(ViewPager viewPager){

        Product product = new Product();
        Photos photo = new Photos();
        Shipping ship = new Shipping();
        Similar similar = new Similar();
        Bundle productBundle = new Bundle();
        Bundle photoBundle = new Bundle();
        Bundle shippingBundle = new Bundle();
        Bundle similarBundle = new Bundle();
        productBundle.putString("productDetailJsonString", productDetailData);
        productBundle.putSerializable("productTitle", productTitle);
        Log.d(TAG, "in view pager title is"+productTitle);
        productBundle.putSerializable("shippingOption", shippingOption);

        photoBundle.putSerializable("photoJson", productPhotoJson);

        shippingBundle.putSerializable("shippingInfo", shippingInfo);
        shippingBundle.putSerializable("sellerInfo", sellerInfo);
        shippingBundle.putSerializable("storeInfo", storeInfo);
        similarBundle.putSerializable("similarObj", similarObj);

        product.setArguments(productBundle);
        photo.setArguments(photoBundle);
        ship.setArguments(shippingBundle);
        similar.setArguments(similarBundle);

        SectionsPageAdapter adapter =  new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(product, "Product");
        adapter.addFragment(ship,"Shipping");
        adapter.addFragment(photo,"Photos");
        adapter.addFragment(similar,"Similar");
        viewPager.setAdapter(adapter);
    }

    void receiveData(){

        Intent i = getIntent();
        String productDetailObj = i.getStringExtra("productDetailObj");
        Log.d(TAG, "in product detail"+productDetailObj);
        this.productDetailData = productDetailObj;


        this.productTitle = i.getStringExtra("productTitle");
        if(productTitle.length()>25){
            String tempTitle = productTitle.substring(0,25);
            toolbarTitle.setText(tempTitle+"...");

        }
        else{
            toolbarTitle.setText(productTitle);
        }
        this.shippingOption = i.getStringExtra("shippingOption");
        this.productPhotoJson = i.getStringExtra("photoJson");
        this.shippingInfo = i.getStringExtra("shippingInfo");
        
        this.sellerInfo = i.getStringExtra("sellerInfo");
        this.similarObj = i.getStringExtra("similarObj");
        this.itemUrl = i.getStringExtra("itemUrl");
        this.storeInfo = i.getStringExtra("storeInfo");
        Log.d("photoJSON is", productPhotoJson);
        Log.d(TAG, "similar in product detail");
        Log.d(TAG, "similar in productDetail is "+ similarObj);

//        getPhotoJson(productTitle);

    }


    String getProductData(){
        Log.d(TAG, "in get methos of productDetail"+productDetailData);
        return productDetailData;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}







