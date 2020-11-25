package noteService;

public class NoSuchNoteException extends NoteInformationException {

    /**
     * @param message please follow this format "id + note_id that cased exception" or
     *                           "name + note_name that cased exception".
     */
    public NoSuchNoteException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return "No such expense note with " + getMessage();
    }
}
