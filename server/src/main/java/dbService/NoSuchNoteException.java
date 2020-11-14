package dbService;

import noteService.NoteInformationException;

public class NoSuchNoteException extends NoteInformationException {

    public NoSuchNoteException() {
        super();
    }

    public NoSuchNoteException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "No such note: " + getMessage();
    }
    
}
