package com.github.creme332.model;

public enum UserType {
    PATRON("PATRON"),
    LIBRARIAN("LIBRARIAN");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}