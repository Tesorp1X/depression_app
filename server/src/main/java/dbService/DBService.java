package dbService;

import accountService.UserAccount;

import configurator.Configurator;
import configurator.ConfiguratorException;
import dbService.dataSets.*;

import noteService.Note;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 * Utility class to handle data base manipulations.
 * @author Tesorp1X
 * @author KyMaKa
 */
public class DBService {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "none";

    private final SessionFactory sessionFactory;

    public DBService() throws ConfiguratorException {

        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getMySqlConfiguration() throws ConfiguratorException {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(NoteDataSet.class);

        Configurator configurator = new Configurator("mysql.conf");

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", configurator.getUrl());
        configuration.setProperty("hibernate.connection.username", configurator.getUser());
        configuration.setProperty("hibernate.connection.password", configurator.getPassword());

        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);

        return configuration;
    }

    public void closeSessionFactory() {

        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        sessionFactory.close();
    }

    /*       User manipulation      */

    private UserDataSet getUserById(long id) throws NoSuchUserException {

        Session session = sessionFactory.openSession();
        UserDataSet dataset = session.get(UserDataSet.class, id);
        session.close();

        if (dataset == null) {
            throw new NoSuchUserException();
        }

        return dataset;
    }

    private boolean verifyUserId(long user_id) {

        try {
            UserDataSet user = getUserById(user_id);
        } catch (NoSuchUserException e) {
            return false;
        }

        return true;
    }

    private UserDataSet getUserEntity(String field_name, String field_value) {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);

        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        ParameterExpression<String> nameParam = builder.parameter(String.class);
        criteria.select(from).where(builder.equal(from.get(field_name), nameParam));

        TypedQuery<UserDataSet> typedQuery = session.createQuery(criteria);
        typedQuery.setParameter(nameParam, field_value);
        
        session.close();

        return typedQuery.getSingleResult();
    }


    private List<UserDataSet> getListOfUsers_(int start_point, int max_result) {

        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> cq = cb.createQuery(UserDataSet.class);
        Root<UserDataSet> rootEntry = cq.from(UserDataSet.class);
        CriteriaQuery<UserDataSet> all = cq.select(rootEntry);

        TypedQuery<UserDataSet> allQuery = session.createQuery(all);
        if (max_result != -1) {
            allQuery.setFirstResult(start_point).setMaxResults(max_result);
        }

        List<UserDataSet> resultList = allQuery.getResultList();
        session.close();

        return resultList;
    }


    public long addUser(String username, String password) {

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            long id = (Long) session.save(new UserDataSet(username, password));
            transaction.commit();
            session.close();

            return id;

        } catch (HibernateException e) {

            return -1;
        }

    }

    public long addUser(String username, String password, String telegram) {

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            long id = (Long) session.save(new UserDataSet(username, password, telegram));
            transaction.commit();
            session.close();

            return id;

        } catch (HibernateException e) {

            return -1;
        }

    }

    public void deleteUserByUsername(String username) throws NoSuchUserException {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserDataSet userToDelete = getUserEntity("username", username);
        if (userToDelete == null) {

            throw new NoSuchUserException(username);
        }

        session.delete(userToDelete);
        //TODO: LOG INFO ABOUT DELETION.
        transaction.commit();
        session.close();
    }

    public void deleteUserByTelegram(String telegram) throws NoSuchUserException {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserDataSet userToDelete = getUserEntity("telegram", telegram);
        if (userToDelete == null) {

            throw new NoSuchUserException(telegram);
        }

        session.delete(userToDelete);
        //TODO: LOG INFO ABOUT DELETION.
        transaction.commit();
        session.close();
    }

    public void updateUser(String username, String new_password, String new_telegram) throws NoSuchUserException {

        UserDataSet user_to_update = findUserByUsername(username);
        user_to_update.setPassword(new_password);
        user_to_update.setTelegram(new_telegram);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(user_to_update);

        //TODO: LOG INFO ABOUT UPDATE.
        transaction.commit();
        session.close();

    }

    public List<UserDataSet> getListOfUsers() {


        return getListOfUsers_(-1, -1);
    }

    public List<UserDataSet> getListOfUsers(int start_point, int max_result) {


        return getListOfUsers_(start_point, max_result);
    }

    public UserDataSet findUserByUsername(String username) throws NoSuchUserException {

        Session session = sessionFactory.openSession();

        UserDataSet dataSet = getUserEntity("username", username);

        session.close();

        if (dataSet == null) {
            throw new NoSuchUserException(username);
        }

        return dataSet;
    }

    public UserDataSet findUserByTelegram(String telegram) throws NoSuchUserException {

        Session session = sessionFactory.openSession();

        UserDataSet dataSet = getUserEntity("telegram", telegram);

        session.close();

        if (dataSet == null) {
            throw new NoSuchUserException(telegram);
        }

        return dataSet;
    }


    public UserAccount getUserAccountByUsername(String username) throws NoSuchUserException {

        UserDataSet dataSet = findUserByUsername(username);

        return new UserAccount(dataSet.getId() ,dataSet.getUsername(), dataSet.getPassword(), dataSet.getTelegram());
    }

    public UserAccount getUserAccountByTelegram(String telegram) throws NoSuchUserException {

        UserDataSet dataSet = findUserByTelegram(telegram);

        return new UserAccount(dataSet.getId() ,dataSet.getUsername(), dataSet.getPassword(), dataSet.getTelegram());
    }

    //Note manipulation

    private NoteDataSet getNoteEntity(String field_name, String field_value) {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<NoteDataSet> criteria = builder.createQuery(NoteDataSet.class);

        Root<NoteDataSet> from = criteria.from(NoteDataSet.class);
        ParameterExpression<String> nameParam = builder.parameter(String.class);
        criteria.select(from).where(builder.equal(from.get(field_name), nameParam));

        TypedQuery<NoteDataSet> typedQuery = session.createQuery(criteria);
        typedQuery.setParameter(nameParam, field_value);

        session.close();

        return typedQuery.getSingleResult();
    }

    //TODO: JavaDoc and comments!

    public long addNote(String name, String description, int value, long user_id) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            long id = (Long) session.save(new NoteDataSet(name, value, new Date(), description, user_id));
            transaction.commit();
            session.close();

            return id;

        } catch (HibernateException e) {

            return -1;
        }
    }


    public NoteDataSet getNoteById(long note_id) throws NoSuchNoteException {
        Session session = sessionFactory.openSession();
        NoteDataSet noteDS = session.get(NoteDataSet.class, note_id);
        
        session.close();

        if (noteDS == null) {
            throw new NoSuchNoteException(note_id + "");
        }

        return noteDS;

    }

    //TODO: think about calling getNoteById method.
    public void changeNote(long note_id, String new_name, String new_description, int new_value) throws NoSuchNoteException {
        NoteDataSet note_to_update = getNoteById(note_id); //Is this good implementation?
        note_to_update.setName(new_name);
        note_to_update.setDescription(new_description);
        note_to_update.setValue(new_value);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(note_to_update);

        transaction.commit();
        session.close();
    }

    public boolean deleteNoteById(long note_id) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        NoteDataSet note = session.load(NoteDataSet.class, note_id);

        if (note != null) {
            session.delete(note);
            transaction.commit();
            session.close();
            return true;
        }

        transaction.commit();
        session.close();
        return false;
    }

    /**
     * @param user_id user id in DB. If there is no such user the exception is being thrown.
     * @param note_name if it is not empty, then list will consists of all notes with that name.
     * @return  a list of NoteDataSet. If note_name is empty, then list will consists of all notes for given user.
     * @throws NoSuchUserException if given user id doesn't point to any user row in DB.
     * @author Tesorp1X
     * */
    public List<NoteDataSet> getListOfNotes(long user_id, String note_name) throws NoSuchUserException {

        if (!verifyUserId(user_id)) {
            throw new NoSuchUserException();
        }

        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<NoteDataSet> cq = cb.createQuery(NoteDataSet.class);
        Root<NoteDataSet> rootEntry = cq.from(NoteDataSet.class);
        CriteriaQuery<NoteDataSet> userNotesCriteria;

        if (note_name == null) {
            userNotesCriteria = cq.where(cb.equal(rootEntry.get("user_id"), user_id));
        } else {
            userNotesCriteria = cq.where(cb.equal(rootEntry.get("user_id"), user_id),
                                         cb.equal(rootEntry.get("name"), note_name));
        }

        List<NoteDataSet> resultList = session.createQuery(userNotesCriteria).getResultList();

        session.close();

        return resultList;

        
    }



}
