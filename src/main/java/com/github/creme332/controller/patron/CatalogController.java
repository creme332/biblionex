package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.Video;
import com.github.creme332.view.patron.Catalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class CatalogController {
    private AppState app;
    private Catalog catalog;

    public CatalogController(AppState app, Catalog catalog) {
        this.app = app;
        this.catalog = catalog;

        Thread th = new Thread() {
            @Override
            public void run() {
                initCatalogItems();
            }
        };
        th.start();
    }

    private void initCatalogItems() {
        try {
            // Load books from the database
            List<Book> books = Book.findAll();
            for (Book book : books) {
                addItemToCatalog(book);
            }

            // Load videos from the database
            List<Video> videos = Video.findAll();
            for (Video video : videos) {
                addItemToCatalog(video);
            }

            // Load journals from the database
            List<Journal> journals = Journal.findAll();
            for (Journal journal : journals) {
                addItemToCatalog(journal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, possibly by showing an error message to the user
        }
    }

    private void addItemToCatalog(Object material) {
        String title = "";
        String iconPath = "";
        if (material instanceof Book) {
            Book book = (Book) material;
            title = book.getTitle();
            iconPath = "/catalog/book.png";
        } else if (material instanceof Video) {
            Video video = (Video) material;
            title = video.getTitle();
            iconPath = "/catalog/video.png";
        } else if (material instanceof Journal) {
            Journal journal = (Journal) material;
            title = journal.getTitle();
            iconPath = "/catalog/journal.png";
        }

        JPanel itemPanel = new JPanel();
        catalog.addCatalogItem(title, iconPath, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleItemClick(material);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                itemPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true)); // Large white border
            }

            @Override
            public void mouseExited(MouseEvent e) {
                itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true)); // Revert to default border
            }
        });
    }

    private void handleItemClick(Object material) {
        String details = "";
        String title = "";

        if (material instanceof Book) {
            Book book = (Book) material;
            title = "Book: " + book.getTitle();
            details = "<b style='color:blue;'>Title:</b> " + book.getTitle() + "<br>"
                    + "<b style='color:blue;'>ISBN:</b> " + book.getIsbn() + "<br>"
                    + "<b style='color:blue;'>Page Count:</b> " + book.getPageCount() + "<br>"
                    + "<b style='color:blue;'>Description:</b> " + book.getDescription() + "<br>"
                    + "<b style='color:blue;'>Publisher ID:</b> " + book.getPublisherId() + "<br>"
                    + "<b style='color:blue;'>Age Restriction:</b> " + book.getAgeRestriction();
        } else if (material instanceof Video) {
            Video video = (Video) material;
            title = "Video: " + video.getTitle();
            details = "<b style='color:blue;'>Title:</b> " + video.getTitle() + "<br>"
                    + "<b style='color:blue;'>Language:</b> " + video.getLanguage() + "<br>"
                    + "<b style='color:blue;'>Duration:</b> " + video.getDuration() + " minutes<br>"
                    + "<b style='color:blue;'>Rating:</b> " + video.getRating() + "<br>"
                    + "<b style='color:blue;'>Format:</b> " + video.getFormat() + "<br>"
                    + "<b style='color:blue;'>Description:</b> " + video.getDescription() + "<br>"
                    + "<b style='color:blue;'>Publisher ID:</b> " + video.getPublisherId() + "<br>"
                    + "<b style='color:blue;'>Age Restriction:</b> " + video.getAgeRestriction();
        } else if (material instanceof Journal) {
            Journal journal = (Journal) material;
            title = "Journal: " + journal.getTitle();
            details = "<b style='color:blue;'>Title:</b> " + journal.getTitle() + "<br>"
                    + "<b style='color:blue;'>ISSN:</b> " + journal.getIssn() + "<br>"
                    + "<b style='color:blue;'>Website:</b> " + journal.getWebsite() + "<br>"
                    + "<b style='color:blue;'>Frequency:</b> " + journal.getFrequency() + "<br>"
                    + "<b style='color:blue;'>Start Date:</b> " + journal.getStartDate() + "<br>"
                    + "<b style='color:blue;'>Description:</b> " + journal.getDescription() + "<br>"
                    + "<b style='color:blue;'>Publisher ID:</b> " + journal.getPublisherId() + "<br>"
                    + "<b style='color:blue;'>Age Restriction:</b> " + journal.getAgeRestriction();
        }

        catalog.showMaterialDialog(title, details);
    }
}
