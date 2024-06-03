package com.github.creme332.dao;

import java.util.ArrayList;

import com.github.creme332.model.Patron;

public interface PatronDao {
    void save(Patron librarian);

    void update(Patron librarian);

    void delete(int id);

    Patron findById(int id);

    Patron findByEmail(String email);

    ArrayList<Patron> findAll();
}