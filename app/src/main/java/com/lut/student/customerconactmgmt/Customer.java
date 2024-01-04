package com.lut.student.customerconactmgmt;

public class Customer {
    private String name;
    private String email;
    private String address;
    private String phone;
    private String country;

    public Customer(String name, String email, String address, String phone, String country) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.country = country;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public String getCountry() {
        return country;
    }
    // Getters for each field
}
