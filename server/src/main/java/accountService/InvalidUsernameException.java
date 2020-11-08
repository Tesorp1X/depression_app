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
     */
    public InvalidUsernameException(String message) {

        super(message);
    }


    @Override
    public String toString() {

        return "The username: " + getMessage() + " is not valid.";
    }
}
