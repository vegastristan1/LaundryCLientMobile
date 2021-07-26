package com.example.laundryclientmobile.models;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class Controller extends Application {
    private  ArrayList<Service> myService = new ArrayList<Service>();

    private  Cart myCart = new Cart();

    public Service getService(int pPosition) {
        return myService.get(pPosition);
    }

    public void setService(Service service) {
        myService.add(service);
    }

    public Cart getCart() {
        return myCart;
    }

    public int getServiceArraylistSize() {
        return myService.size();
    }

}