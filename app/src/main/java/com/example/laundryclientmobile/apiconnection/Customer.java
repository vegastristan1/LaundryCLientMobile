package com.example.laundryclientmobile.apiconnection;

public class Customer {
    private int id;
    private String name, phone, email, address, username, password;

    public Customer(int id, String name, String phone, String email, String address, String username, String password){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
