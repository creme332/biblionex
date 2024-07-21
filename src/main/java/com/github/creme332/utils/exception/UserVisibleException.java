package com.github.creme332.utils.exception;

/**
 * An exception with a user-friendly message that can be displayed on UI.
 */
public class UserVisibleException extends Exception {
    public UserVisibleException(String message) {
        super(message);
    }
}