package com.github.creme332.model;

import java.util.Date;

public class Patron extends User {
    private Date registrationDate;
    private String creditCardNo;
    private Date birthDate;

    public Patron(String email, String password, int userId, String address, String firstName, String lastName,
            String phoneNo) {
        super(email, password, userId, address, firstName, lastName, phoneNo);
        // TODO Auto-generated constructor stub
    }

}
