package com.github.creme332.model;

public enum LoanStatus {
    RETURNED,
    OVERDUE,
    BORROWED,
    DAMAGED;

    @Override
    public String toString() {
        // Get the name of the constant
        String name = name();

        // Capitalize the first letter and make the rest lowercase
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
}
