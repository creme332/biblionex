package com.github.creme332.model;

public class Librarian extends User {
    private String role;

    public Librarian(String email, String password, int userId, String address, String firstName, String lastName,
            String phoneNo, String role) {
        super(email, password, userId, address, firstName, lastName, phoneNo);
        this.role = role;
    }

    public Librarian(String email, String password, String address, String firstName, String lastName,
            String phoneNo, String role) {
        super(email, password, address, firstName, lastName, phoneNo);
        this.role = role;
    }

    public Librarian() {
        // this constructor is for testing only
        super("admin@admin.com", "abcd", "street", "john", "peter", "432423423");
        this.role = "Chief Officer";
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
