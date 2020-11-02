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

    


}
