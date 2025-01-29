package com.account;

import java.util.List;

public class Holder {
    private String name;
    private String surname;
    private String address;
    private List<Account> accounts;

    /**
     * Constructor for Human with the name, surname and address
     *
     * @param name    : name of the human (String)
     * @param surname : surname of the human (string)
     * @param address : address of the human (String)
     */
    public Holder(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    /**
     * Method to get the identification of the human
     * @return name + surname + address of the human (String)
     */
    public String toString() {
        return "Name : " + this.name + ", Surname : " + this.surname + ", Address : " + this.address;
    }
}
