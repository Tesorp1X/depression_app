package accountService;

/**
 * InvalidUsernameException being thrown,
 * when given username is invalid.
 *
 * @author Tesorp1X
 */
public class InvalidUsernameException extends UserInformationException {

    public InvalidUsernameException() {

        super();
    }

    /**
     * @param message - username that is not valid.
     * */
    public InvalidUsernameException(String message) {

        super(message);
    }

    /**
     * @param message - username that is not valid.
     * */
    public InvalidUsernameException(String message, Throwable cause) {

        super(message, cause);
    }

    public InvalidUsernameException(Throwable cause) {

        super(cause);
    }

    /**
     * @param message - username that is not valid.
     * */
    protected InvalidUsernameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {

        return "The username: " + getMessage() + " is not valid.";
    }
}
