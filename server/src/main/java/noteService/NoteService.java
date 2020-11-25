package noteService;


import com.sun.istack.NotNull;
import dbService.DBService;
import dbService.NoSuchNoteException;
import dbService.NoSuchUserException;
import dbService.dataSets.NoteDataSet;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;


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

    /**
     * Use to add new note for user with user_id.
     * @param name note name.
     * @param description Long text-description. May be empty as well.
     * @return id of added note or -1 if note is not inserted.
     */
    public long addNote(@NotNull String name, String description, @NotNull int value, @NotNull long user_id) {

        return dbService.addNote(name, description, value, user_id);
    }

    /**
     * Use to get note by its id.
     * @param note_id id in DB.
     * @throws NoSuchNoteException if note with given id doesn't exist.
     * @see Note .
     */
    public Note getNoteById(long note_id) throws NoSuchNoteException {

        NoteDataSet dataSet = dbService.getNoteById(note_id);

        return new Note(dataSet.getValue(), dataSet.getName(),
                dataSet.getDescription(), dataSet.getUser_id(),
                dataSet.getId(), dataSet.getDate());
    }

    /**
     * Use to change note by its id.
     * @param note_id note id in DB.
     * @param new_name new note_name parameter.
     * @param new_description new note_description parameter.
     * @param new_value new note_value parameter.
     * @throws NoSuchNoteException if note with given id doesn't exist.
     */
    public void changeNote(long note_id, String new_name, String new_description, int new_value) throws NoSuchNoteException {

        dbService.changeNote(note_id, new_name, new_description, new_value);
    }

    /**
     * Use to delete note by its id.
     * @return true if note successfully removed from db. False is being return
     * if note doesn't given id doesn't exist or something went wrong.
     */
    public boolean deleteNote(long note_id) {

        return dbService.deleteNoteById(note_id);
    }

    /**
     * Use to get list of all notes, that user had ever committed into DB with a given name.
     * @param user_id user ID in DB. If there is no such user, exception is being thrown then.
     * @param note_name name parameter of the note.
     * @return List of Notes. It may empty. Probably should throw an exception.
     * @see Note .
     * @author Tesorp1X
     */
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

    /**
     * Use to convert pair of start_date and end_date strings into Pair of Date and Date format.
     * @return Pair of Dates object.
     * @see Pair .
     * @author Tesorp1X
     */

    private Pair<Date, Date> convertStringToDate(String start_date, String end_date) {

        /*  Changing format from dd-MM-yyyy to yyyy-MM-dd.

            String startDateString = "08-12-2017";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println(LocalDate.parse(startDateString, formatter).format(formatter2));
        * */
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

    /**
     * Use to get list of notes in given time period.
     * @param user_id user ID in DB. If there is no such user, exception is being thrown then.
     * @param start_date date-format is YYYY-MM-DD.
     * @param end_date date-format is YYYY-MM-DD.
     * @return a lists of Note class.
     * @see Note .
     * @author Tesorp1X
     */
    public List<Note> getAllUserNotesInTimePeriod(long user_id, String start_date, String end_date) throws NoSuchUserException {

        Pair<Date, Date> pairOfDates = convertStringToDate(start_date, end_date);

        return getAllUserNotesInTimePeriod_util(user_id, pairOfDates.first, pairOfDates.second);
    }

    /**
     * Use to get spending report in given time period.
     * @param user_id user ID in DB. If there is no such user, exception is being thrown then.
     * @param start_date date-format is YYYY-MM-DD.
     * @param end_date date-format is YYYY-MM-DD.
     * @return ReportClass object.
     * @see ReportClass .
     * @author Tesorp1X
     */
    public ReportClass getReport(long user_id, String start_date, String end_date) throws NoSuchUserException {

        Pair<Date, Date> pairOfDates = convertStringToDate(start_date, end_date);
        List<Note> listOfNotes = getAllUserNotesInTimePeriod_util(user_id, pairOfDates.first, pairOfDates.second);

        return new ReportClass(pairOfDates.first, pairOfDates.second, listOfNotes);
    }
}
