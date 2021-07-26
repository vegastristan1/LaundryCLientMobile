package com.example.laundryclientmobile.models;

public class Service {
    private int id;
    private String services_name;
    private String services_price;
    private String services_desc;
    private String item_name;
    private String category_name;
    private String store_name;
    private String item_qty = "1";
    private String stringTotalPrice = "0";


    public Service(int id, String services_name, String services_price, String services_desc, String item_name, String category_name, String store_name) {
        this.id = id;
        this.services_name = services_name;
        this.services_price = services_price;
        this.services_desc = services_desc;
        this.item_name = item_name;
        this.category_name = category_name;
        this.store_name = store_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServices_name() {
        return services_name;
    }

    public void setServices_name(String services_name) {
        this.services_name = services_name;
    }

    public String getServices_price() {
        return services_price;
    }

    public void setServices_price(String services_price) {
        this.services_price = services_price;
    }

    public String getServices_desc() {
        return services_desc;
    }

    public void setServices_desc(String services_desc) {
        this.services_desc = services_desc;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", services_name='" + services_name + '\'' +
                ", services_price='" + services_price + '\'' +
                ", services_desc='" + services_desc + '\'' +
                ", item_name='" + item_name + '\'' +
                ", category_name='" + category_name + '\'' +
                ", store_name='" + store_name + '\'' +
                '}';
    }

    public String getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(String itemQty) {
        this.item_qty = itemQty;
    }

    public String getItem_totalPrice() {
        return stringTotalPrice;
    }

    public void setItem_totalPrice(String totalPrice) {
        this.stringTotalPrice = totalPrice;
    }
}
