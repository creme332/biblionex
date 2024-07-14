package com.github.creme332.view.patron;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import com.github.creme332.model.Author;
import com.github.creme332.model.Book;

public class Dashboard extends JPanel {
    DashboardCard pendingFinesCard;
    DashboardCard totalFinesPaidCard;
    DashboardCard activeLoansCard;

    JTable bookRecommendationsTable;

    public Dashboard() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Create the panel with labels and add a border
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.gridy = 0;
        gbc.gridx = 0;

        pendingFinesCard = new DashboardCard("Pending fines", "Rs 0");
        topPanel.add(pendingFinesCard, gbc);
        gbc.gridx++;

        totalFinesPaidCard = new DashboardCard("Total fines paid", "Rs 0");
        topPanel.add(totalFinesPaidCard, gbc);
        gbc.gridx++;

        activeLoansCard = new DashboardCard("Active loans", "Rs 0");
        topPanel.add(activeLoansCard, gbc);
        gbc.gridx++;

        add(topPanel, BorderLayout.NORTH);

        // Create the table and add it to the center
        bookRecommendationsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookRecommendationsTable);

        JPanel centerPanel = new JPanel(new BorderLayout());

        JLabel bookRecommendationsLabel = new JLabel("Book recommendations");
        bookRecommendationsLabel.putClientProperty("FlatLaf.style", "font: $h2.font");

        centerPanel.add(bookRecommendationsLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void setPendingFines(int amount) {
        pendingFinesCard.setDescription("Rs " + amount);
    }

    public void setTotalFinesPaid(int amount) {
        pendingFinesCard.setDescription("Rs " + amount);

    }

    public void setActiveLoans(int amount) {
        activeLoansCard.setDescription(amount + "");
    }

    public void setBookRecommendations(List<Book> books) {
        String[] columnNames = { "Title", "Author", "Description", "Age Restriction", "Action" };
        Object[][] data = new Object[books.size()][7];

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            data[i][0] = book.getTitle();
            data[i][1] = book.getAuthors().stream()
                    .map(Author::getLastName)
                    .collect(Collectors.joining(", "));
            data[i][2] = book.getDescription();
            data[i][3] = book.getAgeRestriction();
            data[i][4] = "View";
        }

        bookRecommendationsTable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == columnNames.length - 1; // Only the "Action" column is editable
            }
        });

        bookRecommendationsTable.getColumn("Action").setCellRenderer(new ActionButtonRenderer());
        bookRecommendationsTable.getColumn("Action").setCellEditor(new ActionButtonEditor(new JCheckBox()));
    }

    // ButtonRenderer class to render buttons in the table
    class ActionButtonRenderer extends JButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // ButtonEditor class to handle button actions in the table
    class ActionButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ActionButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Handle button action here
                System.out.println("Button clicked");
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
