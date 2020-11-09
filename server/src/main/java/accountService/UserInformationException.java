package accountService;

import java.sql.SQLException;

public abstract class UserInformationException extends Exception {

    public UserInformationException() {
        super();
    }

    public UserInformationException(String message) {
        super(message);
    }

    public UserInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInformationException(Throwable cause) {
        super(cause);
    }

    protected UserInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
