package accountService;


public class UserAccount {

    private final long user_id;

    private final String username;

    private String password;

    private String telegram;




    public UserAccount(long user_id, String username, String password, String telegram) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.telegram = telegram;
    }

    public long getUser_id() { return user_id; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
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
}
