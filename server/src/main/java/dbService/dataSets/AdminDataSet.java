package dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "admins")
public class AdminDataSet implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "telegram_id", nullable = false, unique = true)
    private String telegram_id;

    @Column(name = "user_id", nullable = false, unique = true)
    private long user_id;


}
