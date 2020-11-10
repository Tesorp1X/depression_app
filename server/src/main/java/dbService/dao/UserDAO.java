package dbService.dao;

import dbService.NoSuchUserException;
import dbService.dataSets.UserDataSet;
import org.hibernate.Session;


import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;


/**
 * @author Tesorp1X
 */
public class UserDAO {

    private final Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public UserDataSet getUserById(long id) {

        return session.get(UserDataSet.class, id);
    }

    public long addNewUser(String username, String password) {

        return (Long) session.save(new UserDataSet(username, password));
    }

    public long addNewUser(String username, String password, String telegram) {

        return (Long) session.save(new UserDataSet(username, password, telegram));
    }


    public void updateUser(UserDataSet user_to_update) {

        session.update(user_to_update);
    }



    public boolean deleteUserById(long id) {

        UserDataSet user = getUserById(id);

        if (user != null) {
            session.delete(user);
            return true;
        }

        return false;
    }

    private UserDataSet getEntity(String field_name, String field_value) {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);

        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        ParameterExpression<String> nameParam = builder.parameter(String.class);
        criteria.select(from).where(builder.equal(from.get(field_name), nameParam));

        TypedQuery<UserDataSet> typedQuery = session.createQuery(criteria);
        typedQuery.setParameter(nameParam, field_value);

        return typedQuery.getSingleResult();
    }

    public UserDataSet getUserByUsername(String username) throws NoSuchUserException {

        try {

            return getEntity("username", username);

        } catch (NoResultException e) {

            throw new NoSuchUserException(username);
        }

    }

    public UserDataSet getUserByTelegram(String telegram) throws NoSuchUserException {

        try {

            return getEntity("telegram", telegram);

        } catch (NoResultException e) {

            throw new NoSuchUserException("telegramId_" + telegram);
        }

    }

    private List<UserDataSet> getListOfUsers_(int start_point, int max_result) {

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> cq = cb.createQuery(UserDataSet.class);
        Root<UserDataSet> rootEntry = cq.from(UserDataSet.class);
        CriteriaQuery<UserDataSet> all = cq.select(rootEntry);

        TypedQuery<UserDataSet> allQuery = session.createQuery(all);
        if (max_result != -1) {
            allQuery.setFirstResult(start_point).setMaxResults(max_result);
        }

        return allQuery.getResultList();
    }

    public List<UserDataSet> getListOfUsers() {

        return getListOfUsers_(-1, -1);
    }

    public List<UserDataSet> getListOfUsers(int start_point, int max_result) {

        return getListOfUsers_(start_point, max_result);
    }

}
