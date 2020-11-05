package dbService;

import accountService.UserAccount;
import configurator.Configurator;

import dbService.dataSets.*;
import dbService.dao.*;


import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class DBService {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "none";

    private final SessionFactory sessionFactory;

    public DBService() {

        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getMySqlConfiguration() {

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


    //User manipulation
    public long addUser(String username, String password) {

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserDAO userDAO = new UserDAO(session);
            long id = userDAO.addNewUser(username, password);
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
            UserDAO userDAO = new UserDAO(session);
            long id = userDAO.addNewUser(username, password, telegram);
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
        UserDAO userDAO = new UserDAO(session);
        UserDataSet userToDelete = userDAO.getUserByName(username);
        if (!userDAO.deleteUserById(userToDelete.getId())) {

            throw new NoSuchUserException(username);
        }
        //TODO: LOG INFO ABOUT DELETION.
        transaction.commit();
        session.close();
    }


    public void deleteUserByTelegram(String telegram) throws NoSuchUserException {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        UserDAO userDAO = new UserDAO(session);
        UserDataSet user_to_delete = userDAO.getUserByTelegram(telegram);

        if (!userDAO.deleteUserById(user_to_delete.getId())) {

            throw new NoSuchUserException(telegram);
        }
        //TODO: LOG INFO ABOUT DELETION.
        transaction.commit();
        session.close();
    }


    public void updateUser(String username, String new_password, String new_telegram) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        UserDAO userDAO = new UserDAO(session);
        userDAO.updateUserById(
                userDAO.getUserByName(username).getId(),
                username,
                new_password,
                new_telegram);
        //TODO: LOG INFO ABOUT UPDATE.
        //TODO: HANDLE EXCEPTIONS.
        transaction.commit();
        session.close();

    }

    public List<UserDataSet> getAllUsers() {

        Session session = sessionFactory.openSession();

        UserDAO userDAO = new UserDAO(session);
        List<UserDataSet> result_list = userDAO.getListOfUsers();

        session.close();

        return  result_list;
    }


    public UserDataSet findUserByUsername(String username) throws NoSuchUserException {
        Session session = sessionFactory.openSession();
        UserDAO userDAO = new UserDAO(session);

        UserDataSet dataSet = userDAO.getUserByName(username);
        if (dataSet == null) {
            dataSet = userDAO.getUserByTelegram(username);
        }

        session.close();

        if (dataSet == null) {
            throw new NoSuchUserException(username);
        }
        return dataSet;
    }


    public UserAccount getUserAccountByUsername(String username) throws NoSuchUserException {

        UserDataSet dataSet = findUserByUsername(username);

        return new UserAccount(dataSet.getId() ,dataSet.getUsername(), dataSet.getPassword(), dataSet.getTelegram_key());
    }
    //Note manipulation




}
