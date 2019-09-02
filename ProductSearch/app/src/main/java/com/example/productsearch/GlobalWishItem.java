package com.example.productsearch;

import java.util.ArrayList;
import java.util.List;

public class GlobalWishItem {
    GetDataAdapter a = new GetDataAdapter();
//    GlobalWishItem(){
//        a.setTitle("Hello");
//        a.setShipping("Free");
//        a.setPrice("100");
//        wishes.add(a);
//    }
    public static List<GetDataAdapter> wishes = new ArrayList<GetDataAdapter>();

    public static void addItemToWishList(GetDataAdapter getDataAdapter,RecyclerViewAdapter e){
        wishes.add( getDataAdapter );
        e.notifyDataSetChanged();

    }

    public static void removeFromWishList(GetDataAdapter getDataAdapter){
        for (GetDataAdapter d: wishes){
            String id1 = "";
            id1 = d.getId();
            String id2 = getDataAdapter.getId();
            if(id1.equals(id2)){
                wishes.remove( d );
                break;
            }
        }



    }

    public static boolean check(String id){
        for (GetDataAdapter d: wishes){
            String id1 = "";
            id1 = d.getId();

            if(id1.equals(id)){

                return true;
            }
        }
        return false;
    }
//    public void addToCart(GetDataAdapter a){
//        this.wishes.add(a);
//
//
//    }

}
