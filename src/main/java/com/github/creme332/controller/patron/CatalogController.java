package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.Material;
import com.github.creme332.model.Video;
import com.github.creme332.view.patron.Catalog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogController {

    public CatalogController(AppState app, Catalog catalog) {

        Thread th = new Thread() {
            @Override
            public void run() {
                List<Material> allMaterials = fetchAllMaterials();
                catalog.displayMaterials(allMaterials);
            }
        };
        th.start();
    }

    /**
     * Fetches all materials from database
     */
    private List<Material> fetchAllMaterials() {
        List<Material> allMaterials = new ArrayList<>();
        try {
            // Load books from the database
            List<Book> books = Book.findAll();
            for (Book book : books) {
                allMaterials.add(book);
            }

            // Load videos from the database
            List<Video> videos = Video.findAll();
            for (Video video : videos) {
                allMaterials.add(video);
            }

            // Load journals from the database
            List<Journal> journals = Journal.findAll();
            for (Journal journal : journals) {
                allMaterials.add(journal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return allMaterials;
    }
}
