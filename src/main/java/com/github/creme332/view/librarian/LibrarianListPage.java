package com.github.creme332.view.librarian;

public class LibrarianListPage extends ListPage {
    @Override
    protected String getUserType() {
        return "Librarian";
    }

    @Override
    public void notifyDeleteUser(int row) {
        Integer userId = (Integer) getTableModel().getValueAt(row, 0);
        firePropertyChange("deleteUser", null, userId);
    }
}
