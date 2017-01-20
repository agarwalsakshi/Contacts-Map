package com.develop.android.contacts_map.model;

/**
 * Created by sakshiagarwal on 18/01/17.
 */

public class Contacts {
    private String name, email, phone, officePhone, address;

    public Contacts(String name, String email, String phone, String officePhone, String address)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.officePhone = officePhone;
        this.address = address;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getOfficePhone()
    {
        return officePhone;
    }

    public String getAddress() {
        return address;
    }
}
