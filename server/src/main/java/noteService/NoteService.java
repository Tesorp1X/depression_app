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
    public NoteDataSet getNoteById(long note_id) throws NoSuchNoteException {
        return dbService.getNoteById(note_id);
    }

    
}
