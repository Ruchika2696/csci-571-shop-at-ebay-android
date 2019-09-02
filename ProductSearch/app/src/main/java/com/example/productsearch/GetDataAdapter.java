package com.example.productsearch;

import java.util.ArrayList;

public class GetDataAdapter {

//    int Id;
    String title;
    String itemUrl;
    String zip;
    String price;
    String shipping;
    String condition;
    String id;
    String android_image_url;
    String shippingInfo;
    String sellerInfo;
    String storeInfo;
    String globalShip;
    String conditionShip;
    String policy, within, refund, shippedBy;

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getWithin() {
        return within;
    }

    public void setWithin(String within) {
        this.within = within;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getShippedBy() {
        return shippedBy;
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
    }

    public String getGlobalShip() {
        return globalShip;
    }

    public void setGlobalShip(String globalShip) {
        this.globalShip = globalShip;
    }

    public String getConditionShip() {
        return conditionShip;
    }

    public void setConditionShip(String conditionShip) {
        this.conditionShip = conditionShip;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getCondition() {

        return condition;
    }

    public void setCondition(String condition1) {

        this.condition = condition1;
    }

    public String getShipping() {

        return shipping;
    }

    public void setShipping(String shipping1) {

        this.shipping = shipping1;
    }

    public String getId() {

        return id;
    }

    public void setId(String Id1) {

        this.id = Id1;
    }


    public String getZip() {

        return zip;
    }

    public void setZip(String zip1) {

        this.zip = zip1;
    }

    public String getPrice() {

        return price;
    }

    public void setPrice(String price1) {

        this.price = price1;
    }

    public String getShippingInfo() {

        return shippingInfo;
    }

    public void setShippingInfo(String shippingInfo) {

        this.shippingInfo = shippingInfo;
    }

    public String getSellerInfo() {

        return sellerInfo;
    }

    public void setSellerInfo(String sellerInfo) {

        this.sellerInfo = sellerInfo;
    }

    public String getAndroid_image_url() {
        return android_image_url;
    }

    public void setAndroid_image_url(String android_image_url) {
        this.android_image_url = android_image_url;
    }

    public void setItemUrl(String url){
        this.itemUrl = url;
    }
    public String getItemUrl(){
        return itemUrl;
    }

    public void setStoreInfo(String storeInfo){
        this.storeInfo=storeInfo;
    }
    public String getStoreInfo(){
        return storeInfo;
    }
}