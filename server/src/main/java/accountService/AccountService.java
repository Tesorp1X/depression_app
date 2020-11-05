package accountService;

import dbService.DBService;
import dbService.NoSuchUserException;

/**
 * Utility class over dbService to handle user accounts manipulations.
 * @author Tesorp1X
 */
public class AccountService {

    private final DBService dbService;

    public AccountService() {
        dbService = new DBService();
    }

    public AccountService(DBService dbService) {
        this.dbService = dbService;
    }

    private String conventTelegramIdIntoUsername(String telegram) {

        return "telegramUser_" + telegram;

    }

    @SuppressWarnings("Do we need it?")
    private boolean isUserExist(String username) {

        if (username == null) return false;

        try {

            UserAccount user = dbService.getUserAccountByUsername(username);
            return true;

        } catch (NoSuchUserException e) {


            return false;
        }

    }

    /**
     * Verifies if given username and password are valid.
     *
     * Valid username should consist of ...
     * Valid password should be ...
     */
    private boolean verifyUsernamePassword (String username, String password) {

        //TODO: Username and password verificator.

        return false;
    }

    /**
     * Verifies if given telegram id is valid.
     */
    private boolean verifyTelegramId (String telegram) {

        return telegram.matches("\\d+");
    }

    /**
     * Use to login user via telegram.
     * @return user_id with given username and password or "-1" if there is no such user or wrong password.
     */
    public long loginViaUsername (String username, String password) {

        try {
            UserAccount userAccount = dbService.getUserAccountByUsername(username);
            if (userAccount.getPassword().equals(password)) {

                return userAccount.getUser_id();
            }

            return -1;

        } catch (NoSuchUserException e) {

            return -1;
        }

    }

    /**
     * Use to register new users via username and password.
     * @param username username should be unique and should contain only digits and chars.
     * @param password password should be not null. //TODO: Security politics.
     * @throws InvalidUsernameOrPasswordException if username or password parameter is empty or contains invalid characters.
     * @return id of new user in DB or "-1" if username isn't unique.
     */
    public long registerNewUser (String username, String password) throws InvalidUsernameOrPasswordException {

        if (username == null || password == null || !username.matches("[0-9a-zA-Z]+")) {

            throw new InvalidUsernameOrPasswordException(username + " " + password);
        }

        return dbService.addUser(username, password);
    }

    /**
     * Use to register new users via telegram_id.
     * @param telegram Telegram_ID should be unique and should contain only digits.
     * @throws InvalidUsernameException if telegram parameter is empty or contains not only digits from 0 to 9.
     * @return id of new user in DB or "-1" if username isn't unique.
     */
    public long registerNewUser (String telegram) throws InvalidUsernameException {

        String username = conventTelegramIdIntoUsername(telegram);
        if (telegram == null || !telegram.matches("\\d+")) {

            throw new InvalidUsernameException(username);
        }

        String password = "";

        return dbService.addUser(username, password);
    }

    /**
     * Use to get accountService.UserAccount Object by given username.
     * @param username username should be unique and should contain only digits and chars.
     * @throws NoSuchUserException being thrown when there is no such no user in DB with given username.
     * @throws InvalidUsernameException being thrown when username is invalid.
     * @return accountService.UserAccount object.
     */
    public UserAccount getUserByUsername (String username) throws NoSuchUserException, InvalidUsernameException {

        if (username == null || !username.matches("[0-9a-zA-Z]+")) {

            throw new InvalidUsernameException(username);
        }

        return dbService.getUserAccountByUsername(username);
    }

    /**
     * Use to get accountService.UserAccount Object by given username.
     * @param telegram Telegram_ID should be unique and should contain only digits.
     * @throws NoSuchUserException being thrown when there is no such no user in DB with given username.
     * @throws InvalidUsernameException being thrown when username is invalid.
     * @return accountService.UserAccount object.
     */
    public UserAccount getUserByTelegram (String telegram) throws NoSuchUserException, InvalidUsernameException {

        String username = conventTelegramIdIntoUsername(telegram);

        if (telegram == null || !telegram.matches("\\d+")) {

            throw new InvalidUsernameException(username);
        }

        return dbService.getUserAccountByUsername(username);
    }

    /**
     * Use to delete user from DB via username.
     * @param username username should be valid and point to some user, otherwise exception being thrown.
     * @throws NoSuchUserException being thrown when there is no such no user in DB with given username.
     * @throws InvalidUsernameException being thrown when username is invalid.
     */
    public void deleteUserByUsername (String username) throws NoSuchUserException, InvalidUsernameException {

        if (username == null || !username.matches("[0-9a-zA-Z]+")) {

            throw new InvalidUsernameException(username);
        }

        dbService.deleteUserByUsername(username);
    }

    /**
     * Use to delete user from DB via telegram_id.
     * @param telegram telegram_id should be valid and point to some user, otherwise exception being thrown.
     * @throws NoSuchUserException being thrown when there is no such no user in DB with given username.
     * @throws InvalidUsernameException being thrown when username is invalid.
     */
    public void deleteUserByTelegram (String telegram) throws InvalidUsernameException, NoSuchUserException {

        String username = conventTelegramIdIntoUsername(telegram);

        if (telegram == null || !telegram.matches("\\d+")) {

            throw new InvalidUsernameException(username);
        }

        dbService.deleteUserByUsername(username);
    }

    /**
     * Use to update user's password and telegram_id.
     * @param username should point to some user, which is being updated.
     * @param new_password new password should be valid.
     * @param new_telegram should be valid telegram_id. If no need to update it,
     *                                                  use version without telegram parameter then.
     * @throws NoSuchUserException being thrown when there is no such no user in DB with given username.
     * @throws InvalidUsernameOrPasswordException if username or password parameter is empty or contains invalid characters.
     */
    public void updateUser(String username, String new_password, String new_telegram)
                                                throws NoSuchUserException, InvalidUsernameOrPasswordException {

        if (new_password == null) {
            throw new InvalidUsernameOrPasswordException("Empty password.");
        }

        dbService.updateUser(username, new_password, new_telegram);

    }

}