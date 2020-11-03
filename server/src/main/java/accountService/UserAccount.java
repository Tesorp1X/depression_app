package accountService;


public class UserAccount {

    private final String username;

    private String password;

    private String telegram;


    public UserAccount(String username, String password, String telegram) {
        this.username = username;
        this.password = password;
        this.telegram = telegram;
    }

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
