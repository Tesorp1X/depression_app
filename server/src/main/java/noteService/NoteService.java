package noteService;

import dbService.DBService;
import dbService.NoSuchNoteException;
import dbService.dataSets.NoteDataSet;

public class NoteService {
    
    private final DBService dbService;

    public NoteService(DBService dbService) {
        this.dbService = dbService;
    }

    public long addNote(String name, String description, int value, long user_id) {
        return dbService.addNote(name, description, value, user_id);
    }

    //TODO: Must return Note!
    //Or mustn't?
    public NoteDataSet getNoteById(long note_id) throws NoSuchNoteException {
        return dbService.getNoteById(note_id);
    }

    public void changeNote(long note_id, String new_name, String new_description, int new_value) throws NoSuchNoteException {
        dbService.changeNote(note_id, new_name, new_description, new_value);
    }

    public boolean deleteNote(long note_id) {
        return dbService.deleteNoteById(note_id);
    }
    
}
