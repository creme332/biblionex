package com.github.creme332.model;

public enum LoanStatus {
    COMPLETE,
    OVERDUE,
    PENDING;

    @Override
    public String toString() {
        // Get the name of the constant
        String name = name();
        
        // Capitalize the first letter and make the rest lowercase
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
}
