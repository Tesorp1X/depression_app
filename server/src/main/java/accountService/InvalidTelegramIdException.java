package accountService;

/**
 * InvalidTelegramIdException being thrown,
 * when given telegram_id is invalid.
 *
 * @author Tesorp1X
 */
public class InvalidTelegramIdException extends UserInformationException {

    public InvalidTelegramIdException() {
        super();
    }

    /**
     * @param message - telegram_id that is not valid.
     */
    public InvalidTelegramIdException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return "The telegram_id: " + getMessage() + " is not valid.";
    }
}
