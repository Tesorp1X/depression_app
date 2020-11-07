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


    @Override
    public String toString() {
        return super.toString();
    }
}
