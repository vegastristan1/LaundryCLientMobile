package com.example.laundryclientmobile.models;

import java.util.ArrayList;

public class Cart {
    private  ArrayList<Service> cartService = new ArrayList<Service>();

    public Service getService(int pPosition) {
        return cartService.get(pPosition);
    }

    public void setService(Service service) {
        cartService.add(service);
    }

    public int getCartSize() {
        return cartService.size();
    }

    public boolean checkServiceInCart(Service aService) {
        return cartService.contains(aService);
    }

    public void removeService(int pPosition){
        cartService.remove(pPosition);
    }
}
