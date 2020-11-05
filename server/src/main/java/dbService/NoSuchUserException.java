package dbService;

import accountService.UserInformationException;

import java.sql.SQLException;

/**
 * NoSuchUserException being thrown,
 * when there is no such user with given username in DB.
 *
 * @author Tesorp1X
 * */

public class NoSuchUserException extends UserInformationException {

    public NoSuchUserException() {
        super();
    }

    /**
     * @param message - username that does not exist.
     * */
    public NoSuchUserException(String message) {
        super(message);
    }

    /**
     * @param message - username that does not exist.
     * */
    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchUserException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message - username that does not exist.
     * */
    protected NoSuchUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {
        return "No such user " + getMessage();
    }
}
