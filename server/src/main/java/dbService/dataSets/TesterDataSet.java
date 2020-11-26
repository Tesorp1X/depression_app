package dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "testers")
public class TesterDataSet implements Serializable {

    private static final long serialVersionUID = 4650428521085064357L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "telegram_id", nullable = false, unique = true)
    private String telegram_id;

    @Column(name = "user_id", nullable = false, unique = true)
    private long user_id;

    public TesterDataSet() {}

    public TesterDataSet(long id, String telegram_id, long user_id) {
        this.id = id;
        this.telegram_id = telegram_id;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTelegram_id() {
        return telegram_id;
    }

    public void setTelegram_id(String telegram_id) {
        this.telegram_id = telegram_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
