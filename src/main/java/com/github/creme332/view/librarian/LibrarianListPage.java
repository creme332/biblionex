package com.github.creme332.view.librarian;

import com.github.creme332.model.Librarian;
import java.sql.SQLException;

public class LibrarianListPage extends ListPage {
    @Override
    protected String getUserType() {
        return "Librarian";
    }

    @Override
    public void notifyDeleteUser(int row) {
        Integer userId = (Integer) getTableModel().getValueAt(row, 0);
        try {
            Librarian librarian = Librarian.findById(userId);
            if (librarian != null) {
                Librarian.delete(librarian.getUserId());
                getTableModel().removeRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
