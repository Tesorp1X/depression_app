package dbService.dao;

import dbService.dataSets.NoteDataSet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Query;

import java.util.Map;

public class NoteDAO {

    private final Session session;

    public NoteDAO(Session session) {
        this.session = session;
    }

    public NoteDataSet getNoteById(long id) {
        return session.get(NoteDataSet.class, id);
    }

    public long addNewNote(String name, int value, String description, java.util.Date date, long user_id) {
        return (Long) session.save(new NoteDataSet(name, value, date, description, user_id));
    }

    public void changeNoteById(long id, String name, int value, String description, java.util.Date date, long user_id) {

    }

    public void deleteNoteById(long id) {
    /*TODO: Find a new way, not deprecated*/
        Query q = session.createQuery("delete FROM NoteDataSet where id = " + id);
        q.executeUpdate();
        Map<Integer, Integer>
    }
}
