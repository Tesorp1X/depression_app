package noteService;


import dbService.DBService;
import dbService.NoSuchNoteException;
import dbService.NoSuchUserException;
import dbService.dataSets.NoteDataSet;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


//TODO: JavaDoc
public class NoteService {

    private static class Pair<K, V> {
        public final K first;
        public final V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    }
    
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

    public void changeNote(long note_id, String new_name, String new_description, int new_value) throws NoSuchNoteException {
        dbService.changeNote(note_id, new_name, new_description, new_value);
    }

    public boolean deleteNote(long note_id) {
        return dbService.deleteNoteById(note_id);
    }


    public List<Note> getAllUserNotes(long user_id, String note_name) throws NoSuchUserException {

        List<NoteDataSet> dataSetList = dbService.getListOfNotes(user_id, note_name);
        List<Note> noteList = new ArrayList<>();

        for (var o : dataSetList) {
            noteList.add(new Note(o.getValue(),
                    o.getName(), o.getDescription(),
                    o.getUser_id(), o.getId(), o.getDate()));
        }

        return noteList;
    }

    private Pair<Date, Date> convertStringToDate(String start_date, String end_date) {


        try {
            Date db_date_start = new Date((new SimpleDateFormat("yyyy-MM-dd").parse(start_date)).getTime());
            Date db_date_end = new Date((new SimpleDateFormat("yyyy-MM-dd").parse(end_date)).getTime());

            return new Pair<>(db_date_start, db_date_end);

        } catch (ParseException e) {

            throw new IllegalArgumentException(e);
        }
    }

    private List<Note> getAllUserNotesInTimePeriod_util(long user_id, Date start_date, Date end_date) throws NoSuchUserException {

        List<NoteDataSet> dataSetList = dbService.getListOfNotes(user_id, start_date, end_date);
        List<Note> noteList = new ArrayList<>();

        for (var o : dataSetList) {
            noteList.add(new Note(o.getValue(),
                    o.getName(), o.getDescription(),
                    o.getUser_id(), o.getId(), o.getDate()));
        }

        return noteList;
    }

    public List<Note> getAllUserNotesInTimePeriod(long user_id, String start_date, String end_date) throws NoSuchUserException {

        Pair<Date, Date> pairOfDates = convertStringToDate(start_date, end_date);

        return getAllUserNotesInTimePeriod_util(user_id, pairOfDates.first, pairOfDates.second);
    }

    public ReportClass getReport(long user_id, String start_date, String end_date) throws NoSuchUserException {

        Pair<Date, Date> pairOfDates = convertStringToDate(start_date, end_date);
        List<Note> listOfNotes = getAllUserNotesInTimePeriod_util(user_id, pairOfDates.first, pairOfDates.second);

        return new ReportClass(pairOfDates.first, pairOfDates.second, listOfNotes);
    }
}
