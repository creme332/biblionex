package com.github.creme332.view.patron;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import com.github.creme332.model.Book;

public class Dashboard extends JPanel {
    JLabel pendingFinesLabel;
    JLabel totalFinesPaidLabel;
    JLabel activeLoansLabel;
    JTable bookRecommendationsTable;

    public Dashboard() {
        setLayout(new BorderLayout());

        // Create the panel with labels and add a border
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Patron Dashboard", TitledBorder.CENTER, TitledBorder.TOP));

        pendingFinesLabel = new JLabel("Pending fines: Rs 1000");
        totalFinesPaidLabel = new JLabel("Total fines paid: Rs 453");
        activeLoansLabel = new JLabel("Active loans: 4");

        pendingFinesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalFinesPaidLabel.setHorizontalAlignment(SwingConstants.CENTER);
        activeLoansLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(pendingFinesLabel);
        topPanel.add(totalFinesPaidLabel);
        topPanel.add(activeLoansLabel);

        add(topPanel, BorderLayout.NORTH);

        // Create the table and add it to the center
        bookRecommendationsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookRecommendationsTable);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JLabel("Book recommendations"), BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void setPendingFines(String fines) {
        pendingFinesLabel.setText("Pending fines: " + fines);
    }

    public void setTotalFinesPaid(String fines) {
        totalFinesPaidLabel.setText("Total fines paid: " + fines);
    }

    public void setActiveLoans(String loans) {
        activeLoansLabel.setText("Active loans: " + loans);
    }

    public void setBookRecommendations(List<Book> books) {
        String[] columnNames = {"Title", "Author", "Action"};
        Object[][] data = new Object[books.size()][3];

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            data[i][0] = book.getTitle();
            // data[i][1] = book.getAuthor();
            data[i][2] = "View";
        }

        bookRecommendationsTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;  // Only the "Action" column is editable
            }
        });

        bookRecommendationsTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        bookRecommendationsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
    }
}

// ButtonRenderer class to render buttons in the table
class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

// ButtonEditor class to handle button actions in the table
class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
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

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
