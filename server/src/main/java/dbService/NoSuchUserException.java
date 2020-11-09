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


    @Override
    public String toString() {
        return "No such user " + getMessage();
    }
}
