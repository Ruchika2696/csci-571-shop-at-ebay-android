package com.example.productsearch;

public class Model {

    private String title ;
    private String zip;
    private String shipping ;
    private String price;


    public Model() {
    }

    public Model(String title, String zip, String shipping, String price) {
        this.title = title;
        this.zip = zip;
        this.shipping = shipping;
        this.price = price;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
