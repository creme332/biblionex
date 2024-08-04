package com.github.creme332.controller.librarian;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.event.DocumentEvent;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.Material;
import com.github.creme332.model.User;
import com.github.creme332.model.UserType;
import com.github.creme332.model.Video;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.model.Librarian;
import com.github.creme332.view.librarian.MaterialList;

public class MaterialListController implements PropertyChangeListener {
    private MaterialList view;
    private Librarian librarian;

    public MaterialListController(AppState app, MaterialList view) {
        this.view = view;
        app.addPropertyChangeListener(this);

        view.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));
        view.getSearchButton().addActionListener(e -> searchMaterials());
        view.getNewMaterialButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_CATALOGING_SCREEN));

        Thread th = new Thread() {
            @Override
            public void run() {
                refreshTable();
                addTableButtonListeners();
            }
        };
        th.start();
    }

    private void addTableButtonListeners() {
        JTable table = view.getTable();
        MaterialList.ActionCellRenderer rendererEditor = view.getActionCellRenderer();

        view.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchMaterials();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchMaterials();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchMaterials();
            }
        });

        rendererEditor.setViewButtonActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int materialId = (int) table.getValueAt(selectedRow, 0);
                handleViewMaterial(materialId);
            }
        });

        rendererEditor.setDeleteButtonActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int materialId = (int) table.getValueAt(selectedRow, 0);
                handleDeleteMaterial(materialId);
            }
        });
    }

    private void refreshTable() {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);

        try {
            List<Book> books = Book.findAll();
            List<Journal> journals = Journal.findAll();
            List<Video> videos = Video.findAll();

            for (Book book : books) {
                Object[] rowData = {
                        book.getMaterialId(),
                        book.getTitle(),
                        book.getPublisherId(),
                        book.getDescription(),
                        book.getAgeRestriction(),
                        "Book"
                };
                tableModel.addRow(rowData);
            }

            for (Journal journal : journals) {
                Object[] rowData = {
                        journal.getMaterialId(),
                        journal.getTitle(),
                        journal.getPublisherId(),
                        journal.getDescription(),
                        journal.getAgeRestriction(),
                        "Journal"
                };
                tableModel.addRow(rowData);
            }

            for (Video video : videos) {
                Object[] rowData = {
                        video.getMaterialId(),
                        video.getTitle(),
                        video.getPublisherId(),
                        video.getDescription(),
                        video.getAgeRestriction(),
                        "Video"
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while fetching materials.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleViewMaterial(int materialId) {
        try {
            Material material = null;

            // Try to find the material by its ID in the respective lists
            for (Book book : Book.findAll()) {
                if (book.getMaterialId() == materialId) {
                    material = book;
                    break;
                }
            }

            if (material == null) {
                for (Journal journal : Journal.findAll()) {
                    if (journal.getMaterialId() == materialId) {
                        material = journal;
                        break;
                    }
                }
            }

            if (material == null) {
                for (Video video : Video.findAll()) {
                    if (video.getMaterialId() == materialId) {
                        material = video;
                        break;
                    }
                }
            }

            if (material == null) {
                JOptionPane.showMessageDialog(view, "Material not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String title = "";
            String details = "";

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

            JOptionPane.showMessageDialog(view, "<html><h2>" + title + "</h2><p>" + details + "</p></html>",
                    "Material Details", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while retrieving material details.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteMaterial(int materialId) {
        int confirmation = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this material?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                // Attempt to delete the material as a Book
                Book book = Book.findById(materialId);
                if (book != null) {
                    Book.delete(book);
                } else {
                    // Attempt to delete the material as a Journal
                    Journal journal = Journal.findById(materialId);
                    if (journal != null) {
                        Journal.delete(journal);
                    } else {
                        // Attempt to delete the material as a Video
                        Video video = Video.findByID(materialId);
                        if (video != null) {
                            Video.delete(video);
                        } else {
                            JOptionPane.showMessageDialog(view, "Material not found.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                JOptionPane.showMessageDialog(view, "Material deleted successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view, "Error occurred while deleting the material.", "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchMaterials() {
        String searchText = view.getSearchField().getText().trim();
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);

        if (searchText.isEmpty()) {
            refreshTable(); // Reload all materials if the search field is empty
            return;
        }

        try {
            List<Book> books = Book.findAll();
            List<Journal> journals = Journal.findAll();
            List<Video> videos = Video.findAll();

            for (Book book : books) {
                if (StringUtil.isSimilar(String.valueOf(book.getMaterialId()), searchText) ||
                        StringUtil.isSimilar(book.getTitle(), searchText)) {
                    Object[] rowData = {
                            book.getMaterialId(),
                            book.getTitle(),
                            book.getPublisherId(),
                            book.getDescription(),
                            book.getAgeRestriction(),
                            "Book"
                    };
                    tableModel.addRow(rowData);
                }
            }

            for (Journal journal : journals) {
                if (StringUtil.isSimilar(String.valueOf(journal.getMaterialId()), searchText) ||
                        StringUtil.isSimilar(journal.getTitle(), searchText)) {
                    Object[] rowData = {
                            journal.getMaterialId(),
                            journal.getTitle(),
                            journal.getPublisherId(),
                            journal.getDescription(),
                            journal.getAgeRestriction(),
                            "Journal"
                    };
                    tableModel.addRow(rowData);
                }
            }

            for (Video video : videos) {
                if (StringUtil.isSimilar(String.valueOf(video.getMaterialId()), searchText) ||
                        StringUtil.isSimilar(video.getTitle(), searchText)) {
                    Object[] rowData = {
                            video.getMaterialId(),
                            video.getTitle(),
                            video.getPublisherId(),
                            video.getDescription(),
                            video.getAgeRestriction(),
                            "Video"
                    };
                    tableModel.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while searching materials.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        if (propertyName.equals("currentScreen")
                && (Screen) evt.getNewValue() == Screen.LIBRARIAN_MATERIAL_LIST_SCREEN) {
            refreshTable();
        }

        if (propertyName.equals("loggedInUser")) {
            User newUser = (User) evt.getNewValue();
            if (newUser == null || newUser.getUserType() != UserType.LIBRARIAN)
                return;

            librarian = (Librarian) newUser;
        }
    }
}
