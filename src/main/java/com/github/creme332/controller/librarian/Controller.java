package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.view.Frame;
import com.github.creme332.view.librarian.AuthorForm;
import com.github.creme332.view.librarian.CheckInPage;
import com.github.creme332.view.librarian.CheckOutPage;
import com.github.creme332.view.librarian.Dashboard;
import com.github.creme332.view.librarian.MaterialForm;
import com.github.creme332.view.librarian.PublisherForm;
import com.github.creme332.view.librarian.RegistrationForm;
import com.github.creme332.view.librarian.UserListPage;
import com.github.creme332.view.librarian.VendorForm;
import com.github.creme332.view.librarian.MaterialList;

/**
 * Instantiates all controllers for pages accessible by librarian only after log
 * in.
 */
public class Controller {
    public Controller(AppState app, Frame frame) {

        // controller for librarian registration form
        new RegistrationController(app, (RegistrationForm) frame.getPage(Screen.LIBRARIAN_REGISTRATION_SCREEN));

        // controller for page listing patron details
        new PatronListPageController(app, (UserListPage) frame.getPage(Screen.LIBRARIAN_PATRON_LIST_SCREEN));

        // controller for listing librarian details
        new LibrarianListPageController(app, (UserListPage) frame.getPage(Screen.LIBRARIAN_LIBRARIAN_LIST_SCREEN));

        // controller for Check In and Renew
        new CheckInController(app, (CheckInPage) frame.getPage(Screen.LIBRARIAN_CHECKIN_SCREEN));

        // controller for Check Out
        new CheckOutController(app, (CheckOutPage) frame.getPage(Screen.LIBRARIAN_CHECKOUT_SCREEN));

        // controller for librarian dashboard
        new DashboardController(app, (Dashboard) frame.getPage(Screen.LIBRARIAN_DASHBOARD_SCREEN));

        // controller for vendor creation form
        new VendorController(app, (VendorForm) frame.getPage(Screen.LIBRARIAN_VENDOR_SCREEN));

        // controller for author creation form
        new AuthorController(app, (AuthorForm) frame.getPage(Screen.LIBRARIAN_AUTHOR_SCREEN));

        // controller for publisher creation form
        new PublisherController(app, (PublisherForm) frame.getPage(Screen.LIBRARIAN_PUBLISHER_SCREEN));

        // controller for material form
        new MaterialFormController(app, (MaterialForm) frame.getPage(Screen.LIBRARIAN_CATALOGING_SCREEN));

        // controller for material list
        new MaterialListController(app, (MaterialList) frame.getPage(Screen.LIBRARIAN_MATERIAL_LIST_SCREEN));
    }
}