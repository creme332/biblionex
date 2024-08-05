package com.github.creme332.controller.librarian;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentEvent;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.User;
import com.github.creme332.model.UserType;
import com.github.creme332.model.Video;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.model.Librarian;
import com.github.creme332.model.Material;
import com.github.creme332.model.MaterialType;
import com.github.creme332.view.librarian.MaterialList;
import com.github.creme332.view.patron.Catalog;

public class MaterialListController implements PropertyChangeListener {
    private MaterialList view;
    private Librarian librarian;
    private List<Material> allMaterials;

    public MaterialListController(AppState app, MaterialList view) {
        this.view = view;
        app.addPropertyChangeListener(this);

        view.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));
        view.getNewMaterialButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_CATALOGING_SCREEN));

        Thread th = new Thread() {
            @Override
            public void run() {
                fetchMaterials();
                displayMaterials(allMaterials);
                addTableButtonListeners();
            }
        };
        th.start();
    }

    /**
     * Fetch all materials from database and saves it to allMaterials.
     */
    private void fetchMaterials() {
        try {
            allMaterials = Material.findAllMaterials();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while fetching materials.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Material findMaterialById(int id) {
        for (Material material : allMaterials) {
            if (material.getMaterialId() == id) {
                return material;
            }
        }
        return null; // Return null if no material with the given ID is found
    }

    private void addTableButtonListeners() {
        JTable table = view.getTable();
        MaterialList.ActionCellRenderer rendererEditor = view.getActionCellRenderer();

        view.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterMaterials();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterMaterials();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterMaterials();
            }
        });

        rendererEditor.setViewButtonActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int materialId = (int) table.getValueAt(selectedRow, 0);
                Catalog.showMaterialDialog(findMaterialById(materialId), table);
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

    private void displayMaterials(List<Material> materialsToDisplay) {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);

        for (Material material : materialsToDisplay) {
            Object[] rowData = {
                    material.getMaterialId(),
                    material.getTitle(),
                    material.getPublisherId(),
                    material.getDescription(),
                    material.getAgeRestriction(),
                    material.getType()
            };
            tableModel.addRow(rowData);
        }
    }

    private void handleDeleteMaterial(int materialId) {
        int confirmation = JOptionPane.showConfirmDialog(view,
                String.format("Are you sure you want to delete material with ID %d?", materialId),
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirmation != JOptionPane.YES_OPTION)
            return;

        Material material = findMaterialById(materialId);

        if (material == null) {
            JOptionPane.showMessageDialog(view, String.format("Material with ID %d not found.", materialId), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (material.getType() == MaterialType.BOOK) {
                Book.delete((Book) material);
            } else if (material.getType() == MaterialType.JOURNAL) {
                Journal.delete((Journal) material);
            }
            if (material.getType() == MaterialType.VIDEO) {
                Video.delete((Video) material);
            }

            JOptionPane.showMessageDialog(view, "Material deleted successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            displayMaterials(allMaterials);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while deleting the material.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    private void filterMaterials() {
        String searchText = view.getSearchField().getText().trim();

        if (searchText.isEmpty()) {
            displayMaterials(allMaterials); // Display all materials if the search field is empty
            return;
        }

        List<Material> filteredMaterials = new ArrayList<>();

        for (Material material : allMaterials) {
            if (StringUtil.isSimilar(material.getTitle(), searchText)) {
                filteredMaterials.add(material);
            }
        }

        displayMaterials(filteredMaterials);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        if (propertyName.equals("currentScreen")
                && (Screen) evt.getNewValue() == Screen.LIBRARIAN_MATERIAL_LIST_SCREEN) {
            fetchMaterials();
            displayMaterials(allMaterials);
        }

        if (propertyName.equals("loggedInUser")) {
            User newUser = (User) evt.getNewValue();
            if (newUser == null || newUser.getUserType() != UserType.LIBRARIAN)
                return;

            librarian = (Librarian) newUser;
        }
    }
}
