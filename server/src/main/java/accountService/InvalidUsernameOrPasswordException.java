package accountService;


/**
 * InvalidUsernameOrPasswordException being thrown,
 * when given username is invalid.
 *
 * @author Tesorp1X
*/
public class InvalidUsernameOrPasswordException extends UserInformationException {

    public InvalidUsernameOrPasswordException() {
        super();
    }

    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }

    public InvalidUsernameOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUsernameOrPasswordException(Throwable cause) {
        super(cause);
    }

    protected InvalidUsernameOrPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
