package org.team25.Utils;

/**
 * This class handles user-defined exceptions that are raised during the normal execution
 * @author Bharti Chhabra
 * @version 1.0.0
 */
public class ValidationException extends Exception {
    /**
     * initialising a string to hold message
     */
    String d_ErrorMessage = "Invalid input.Try again!";

    /**
     * Validation Exception Constructor
     */
    public ValidationException() {
        super();
    }

    /**
     * Exception message
     * @param message exception message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Get the message and check for exception
     * @return the message
     */
    @Override
    public String getMessage() {
        if(super.getMessage() != null) {
            return super.getMessage();
        }
        return d_ErrorMessage;
    }
}
