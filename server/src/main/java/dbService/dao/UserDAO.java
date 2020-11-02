package dbService.dao;

import dbService.dataSets.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class UserDAO {

    private final Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet getUserById(long id) {
        return session.get(UsersDataSet.class, id);
    }

    public long addNewUser(String username, String password) {

        return (Long) session.save(new UsersDataSet(username, password));
    }

    public long addNewUser(String username, String password, String telegram) {

        return (Long) session.save(new UsersDataSet(username, password, telegram));
    }

    public void updateUserById(long id, String username, String password, String telegram) {
        //UsersDataSet user_to_update = session.load()
        UsersDataSet user_to_update = this.getUserById(id);
        user_to_update.setUsername(username);
        user_to_update.setPassword(password);
        user_to_update.setTelegram_key(telegram);

        //TODO: warp into try/catch
        session.update(user_to_update);
    }

    public boolean deleteUserById(long id) {

        //UsersDataSet user = session.get(UsersDataSet.class, id);
        UsersDataSet user = this.getUserById(id);
        if (user != null) {
            session.delete(user);
            return true;
        }

        return false;
    }

}
