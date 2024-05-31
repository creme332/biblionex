package com.github.creme332.model;

import java.util.Arrays;

public abstract class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
        email = "";
        password = "";
    }

    public abstract String getUserType();

    public abstract boolean save();

    public static boolean authenticate(String email, char[] input) {
        boolean isCorrect = true;

        // TODO: Fetch password from database
        char[] correctPassword = { 'b', 'u', 'g', 'a', 'b', 'o', 'o' };

        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals(input, correctPassword);
        }

        // Zero out the password for security purposes.
        Arrays.fill(correctPassword, '0');

        return isCorrect;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
