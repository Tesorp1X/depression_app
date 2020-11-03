package dbService.dao;

import dbService.dataSets.UserDataSet;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

    public void updateUserById(long id, String username, String password, String telegram) {

        UserDataSet user_to_update = this.getUserById(id);
        user_to_update.setPassword(password);
        user_to_update.setTelegram_key(telegram);

        //TODO: warp into try/catch
        session.update(user_to_update);
    }



    public boolean deleteUserById(long id) {

        //UserDataSet user = session.get(UserDataSet.class, id);
        UserDataSet user = this.getUserById(id);
        if (user != null) {
            session.delete(user);
            return true;
        }

        return false;
    }

    public UserDataSet getUserByName(String username) {

        //TODO: refactor using CriteriaBuilder
        //CriteriaBuilder cb = session.getCriteriaBuilder();
        return ((UserDataSet) session.createCriteria(UserDataSet.class)
                .add(Restrictions
                        .eq("username", username)));
    }

    public UserDataSet getUserByTelegram(String telegram) {

        //TODO: refactor using CriteriaBuilder
        //CriteriaBuilder cb = session.getCriteriaBuilder();

        return ((UserDataSet) session.createCriteria(UserDataSet.class)
                .add(Restrictions
                        .eq("telegram", telegram)));
    }

    public List<UserDataSet> getListOfUsers() {
        /**/

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> cq = cb.createQuery(UserDataSet.class);
        Root<UserDataSet> rootEntry = cq.from(UserDataSet.class);
        CriteriaQuery<UserDataSet> all = cq.select(rootEntry);

        TypedQuery<UserDataSet> allQuery = session.createQuery(all);

        return allQuery.getResultList();
    }

}
