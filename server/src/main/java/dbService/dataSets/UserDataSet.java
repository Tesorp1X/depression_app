package dbService.dataSets;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "users") //table name where data of this entity will be stored.
public class UserDataSet implements Serializable {

    private static final long serialVersionUID = -7729539235975598423L;



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telegram", unique = true)
    private String telegram;

    protected UserDataSet() { }

    public UserDataSet(String username, String password) {

        this.username = username;

        this.password = password;

        this.telegram = null;

    }

    public UserDataSet(String username, String password, String telegram) {

        this.username = username;

        this.password = password;

        this.telegram = telegram;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public void setTelegram(String telegram_key) {

        this.telegram = telegram_key;
    }

    public void setPassword(String password) {

        this.password = password;
    }


    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTelegram() {
        return telegram;
    }



    @Override
    public String toString() {
        return "User [id = " + id
                + "username = " + username
                + "telegram = " + telegram + "]";
    }
}
