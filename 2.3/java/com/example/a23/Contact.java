package com.example.a23;

/**
 * This class contains all the data our contact should have.
 * We also use getters and setters to access this data.
 * @author Viggo Lagerstedt Ekholm
 */
public class Contact {
    private String name;
    private final String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
