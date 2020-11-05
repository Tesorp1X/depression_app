package dbService.dataSets;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "users")
public class UserDataSet implements Serializable {

    private static final long serialVersionUID = -7729539235975598423L;



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "username", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;



    @Column(name = "telegram")
    private String telegram_key;

    protected UserDataSet() { }

    public UserDataSet(String username, String password) {

        this.username = username;

        this.password = password;

        this.telegram_key = null;

    }

    public UserDataSet(String username, String password, String telegram_key) {

        this.username = username;

        this.password = password;

        this.telegram_key = telegram_key;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public void setTelegram_key(String telegram_key) {

        this.telegram_key = telegram_key;
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

    public String getTelegram_key() {
        return telegram_key;
    }



    @Override
    public String toString() {
        return "User [id = " + id
                + "username = " + username
                + "telegram = " + telegram_key + "]";
    }
}
