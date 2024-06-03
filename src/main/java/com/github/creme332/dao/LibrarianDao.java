package com.github.creme332.dao;

import java.util.ArrayList;
import com.github.creme332.model.Librarian;

public interface LibrarianDao {
    void save(Librarian librarian);

    void update(Librarian librarian);

    void delete(int id);

    Librarian findById(int id);

    Librarian findByEmail(String email);

    ArrayList<Librarian> findAll();
}
