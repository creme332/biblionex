package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.view.Frame;
import com.github.creme332.view.librarian.CheckInRenew;
import com.github.creme332.view.librarian.Dashboard;
import com.github.creme332.view.librarian.LibrarianListPage;
import com.github.creme332.view.librarian.PatronListPage;
import com.github.creme332.view.librarian.RegistrationForm;
import com.github.creme332.view.librarian.VendorForm;

/**
 * Instantiates all controllers for pages accessible by librarian only after log
 * in.
 */
public class Controller {
    public Controller(AppState app, Frame frame) {

        // controller for librarian registration form
        new RegistrationController(app, (RegistrationForm) frame.getPage(Screen.LIBRARIAN_REGISTRATION_SCREEN));

        // controller for page listing patron details
        new PatronListPageController(app, (PatronListPage) frame.getPage(Screen.LIBRARIAN_PATRON_LIST_SCREEN));

        // controller for listing librarian details
        new LibrarianListPageController(app, (LibrarianListPage) frame.getPage(Screen.LIBRARIAN_LIBRARIAN_LIST_SCREEN));

        // controller for Check In and Renew
        new CheckInRenewController(app, (CheckInRenew) frame.getPage(Screen.LIBRARIAN_CHECKIN_SCREEN));

        // controller for librarian dashboard
        new DashboardController(app, (Dashboard) frame.getPage(Screen.LIBRARIAN_DASHBOARD_SCREEN));

        // controller for vendor creation form
        new VendorController(app, (VendorForm) frame.getPage(Screen.LIBRARIAN_VENDOR_SCREEN));
    }
}
