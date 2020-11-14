package dbService.dao;

import dbService.dataSets.NoteDataSet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Tesorp1X
 */
public class NoteDAO {

    private final Session session;

    public NoteDAO(Session session) {
        this.session = session;
    }

    public NoteDataSet getById(long id) {
        return session.get(NoteDataSet.class, id);
    }

    public long addNote(String name, int value, String description, java.util.Date date, long user_id) {

        return (Long) session.save(new NoteDataSet(name, value, date, description, user_id));
    }

    public void updateById(long id, String name, int value, String description, java.util.Date date, long user_id) {

    }

    /*public void deleteNoteById(long id) {
    //TODO: Find a new way, not deprecated
        Query q = session.createQuery("delete FROM NoteDataSet where id = " + id);
        q.executeUpdate();

    }*/

    public boolean deleteById(long id) {

        NoteDataSet note = session.load(NoteDataSet.class, id);
        if (note != null) {
            session.delete(note);
            return true;
        }

        return false;
    }

    public List<NoteDataSet> getList(long user_id) {
        
    }

    public List<NoteDataSet> getList(long user_id, String name) {
        
    }

    public List<NoteDataSet> getList(long user_id, Date start_date, Date end_date) {
        
    }


}
