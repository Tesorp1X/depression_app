package noteService;

public class NoteInformationException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoteInformationException() {
        super();
    }

    public NoteInformationException(String message) {
        super(message);
    }

    public NoteInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteInformationException(Throwable cause) {
        super(cause);
    }

    protected NoteInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
