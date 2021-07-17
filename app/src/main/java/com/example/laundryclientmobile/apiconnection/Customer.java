package com.example.laundryclientmobile.apiconnection;

public class Customer {
    private int id;
    private String customer_name, customer_phone_number, customer_email, customer_address, customer_username, customer_password;

    public Customer(int id, String customer_name, String customer_phone_number, String customer_email, String customer_address, String customer_username, String customer_password){
        this.id = id;
        this.customer_name = customer_name;
        this.customer_phone_number = customer_phone_number;
        this.customer_email = customer_email;
        this.customer_address = customer_address;
        this.customer_username = customer_username;
        this.customer_password = customer_password;
    }

    public int getId() {
        return id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_phone_number() {
        return customer_phone_number;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public String getCustomer_username() {
        return customer_username;
    }

    public String getCustomer_password() {
        return customer_password;
    }
}
