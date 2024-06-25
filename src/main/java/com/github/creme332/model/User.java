package com.github.creme332.model;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;

import com.github.creme332.utils.DatabaseConnection;

import io.github.cdimascio.dotenv.Dotenv;

import com.github.creme332.model.User;

public abstract class User {
    protected String email;
    protected String password;
    protected int userId;
    protected String address;
    protected String firstName;
    protected String lastName;
    protected String phoneNo;
    protected UserType userType;

    protected User(String email, String password, int userId, String address, String firstName, String lastName,
            String phoneNo) {
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
    }

    protected User(String email, String password, String address, String firstName, String lastName,
            String phoneNo) {
        this.email = email;
        this.password = password;
        this.userId = -1;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
    }

    public boolean authenticate(String email, char[] enteredPassword) {
        boolean isCorrect = true;

        // check if emails do not match
        if (!email.equals(this.email)) {
            Arrays.fill(enteredPassword, '0');
            return false;
        }

        com.github.creme332.utils.PasswordAuthentication passwordAuthentication = new com.github.creme332.utils.PasswordAuthentication();
        isCorrect = passwordAuthentication.authenticate(enteredPassword, this.password);

        // Zero out the password for security purposes.
        Arrays.fill(enteredPassword, '0');

        return isCorrect;
    }

    /**
     * Changes password of a user in database
     * 
     * @param user        User who wants to change password
     * @param newPassword New password in plain text (original version)
     * @throws SQLException
     */
    public static void changePassword(User user, char[] newPassword) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query;

        com.github.creme332.utils.PasswordAuthentication passwordAuthentication = new com.github.creme332.utils.PasswordAuthentication();
        String hashedPassword = passwordAuthentication.hash(newPassword);

        if (user.getUserType() == UserType.PATRON) {
            query = "UPDATE patron SET password = ? WHERE patron_id = ?";
        } else {
            query = "UPDATE librarian SET password = ? WHERE librarian_id = ?";
        }

        try (PreparedStatement updatePassword = conn.prepareStatement(query)) {
            updatePassword.setString(1, hashedPassword);
            updatePassword.setInt(2, user.getUserId());
            int rowsAffected = updatePassword.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException(String.format("User record with ID %d could not be found.", user.getUserId()));
            }
        }

        // Zero out the password for security purposes.
        Arrays.fill(newPassword, '0');
    }

    public static boolean validateEmail(String email) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT COUNT(*) FROM patron WHERE email = ? UNION SELECT COUNT(*) FROM librarian WHERE email = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    return false; // Email already exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // Email is unique
    }

    /**
     * Sends an email with a password reset token to user.
     * 
     * @return Password reset token found in email
     * @throws MessagingException
     */
    public String sendPasswordResetEmail()
            throws MessagingException {
        Dotenv dotenv = Dotenv.load();
        final String SENDER_EMAIL = dotenv.get("EMAIL_USERNAME");
        final String SENDER_PASSWORD = dotenv.get("EMAIL_PASSWORD");

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        SecureRandom random = new SecureRandom();
        String passwordResetCode = String.valueOf(100000 + random.nextInt(900000));

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL, "Biblionex"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Biblionex: Password Reset");

            final String emailTemplate = """
                    <html>
                        <body>
                            <h1>Password Reset Verification</h1>
                            <p>Dear %s,</p>
                            <p>Your password reset code is: <strong>%s</strong></p>.
                            <p>Please enter this code in the application to reset your password.</p>
                            <p>Best regards,</p>
                            <p>Biblionex Team</p>
                            <hr>
                            <p style='font-size:0.8em;'>&copy; 2024 Biblionex. All rights reserved.</p>
                        </body>
                    </html>
                    """;
            String forrmattedEmail = String.format(emailTemplate, firstName, passwordResetCode);

            message.setContent(forrmattedEmail, "text/html");

            Transport.send(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return passwordResetCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
