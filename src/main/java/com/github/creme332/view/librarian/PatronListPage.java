package com.github.creme332.view.librarian;

import com.github.creme332.model.Patron;
import java.sql.SQLException;

public class PatronListPage extends ListPage {
    @Override
    protected String getUserType() {
        return "Patron";
    }

    @Override
    public void notifyDeleteUser(int row) {
        Integer userId = (Integer) getTableModel().getValueAt(row, 0);
        try {
            Patron patron = Patron.findById(userId); 
            if (patron != null) {
                Patron.delete(patron.getUserId());
                getTableModel().removeRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
