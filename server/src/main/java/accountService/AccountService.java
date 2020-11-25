package accountService;


import dbService.DBService;
import dbService.NoSuchUserException;
import dbService.dataSets.UserDataSet;

import java.util.LinkedList;
import java.util.List;

/**
 * Utility class over dbService to handle user accounts manipulations.
 * @author Tesorp1X
 */
public class AccountService {

    private final DBService dbService;


    public AccountService(DBService dbService) {
        this.dbService = dbService;
    }

    private String conventTelegramIdIntoUsername(String telegram) {

        return "telegramUser_" + telegram;

    }


    /**
     * Verifies if given username and password are valid.
     *
     * @param username Valid username should consist of 0-9, a-z and A-Z.
     * @param password password should consist of 0-9, a-z and A-Z.
     */
    private boolean verifyUsernamePassword(String username, String password) {

        return username != null
                && password != null
                && username.matches("[0-9a-zA-Z]+")
                && password.matches("[0-9a-zA-Z]+");
    }

    /**
     * Verifies if given username are valid.
     *
     * @param username Valid username should consist of 0-9, a-z and A-Z.
     *
     */
    private boolean verifyUsername(String username) {

        return username != null && username.matches("[0-9a-zA-Z]+");
    }

    /**
     * Verifies if given telegram id is valid.
     */
    private boolean verifyTelegramId(String telegram) {

        return telegram != null && telegram.matches("\\d+");
    }

    /**
     * Use to login user via telegram.
     * @return user_id with given username and password or "-1" if there is no such user or wrong password.
     */
    public long loginViaUsername(String username, String password) {

        if (!verifyUsername(username)) {
            return -1;
        }

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
    public long registerNewUser(String username, String password) throws InvalidUsernameOrPasswordException {

        if (!verifyUsernamePassword(username, password)) {

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
    public long registerNewUser(String telegram) throws InvalidUsernameException {

        String username = conventTelegramIdIntoUsername(telegram);
        if (!verifyTelegramId(telegram)) {

            throw new InvalidUsernameException(username);
        }

        String password = "";

        return dbService.addUser(username, password, telegram);
    }

    /**
     * Use to get accountService.UserAccount Object by given username.
     * @param username username should be unique and should contain only digits and chars.
     * @throws NoSuchUserException being thrown when there is no such no user in DB with given username.
     * @throws InvalidUsernameException being thrown when username is invalid.
     * @return accountService.UserAccount object.
     */
    public UserAccount getUserByUsername(String username) throws NoSuchUserException, InvalidUsernameException {

        if (!verifyUsername(username)) {

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
    public UserAccount getUserByTelegram(String telegram) throws NoSuchUserException, InvalidUsernameException {

        String username = conventTelegramIdIntoUsername(telegram);
        if (!verifyTelegramId(telegram)) {

            throw new InvalidUsernameException(username);
        }

        return dbService.getUserAccountByTelegram(telegram);
    }

    /**
     * Use it to get a list of all users.
     * @return a list of UserAccount objects with toString method implemented.
     */
    public List<UserAccount> getListOfUsers() {

        List<UserDataSet> dataSetList = dbService.getListOfUsers();
        List<UserAccount> accountList = new LinkedList<>();

        for (var o : dataSetList) {
            accountList.add(new UserAccount(o.getId(), o.getUsername(), o.getPassword(), o.getTelegram()));
        }

        return accountList;
    }

    /**
     * Use it to get a limited list of users.
     * @param start_point int value (user id to start list assembly until length
     *                                      of max_result or end of results in table is reached.
     * @param max_result max amount of objects in list.
     * @throws NegativeArraySizeException if max_result is negative.
     * @throws IndexOutOfBoundsException if start_point is negative.
     * @return a list of UserAccount objects with toString method implemented.
     */
    public List<UserAccount> getListOfUsers(int start_point, int max_result) {

        if (max_result < 0) {
            throw new NegativeArraySizeException(String.valueOf(max_result));
        }
        if (start_point < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(start_point));
        }

        List<UserDataSet> dataSetList = dbService.getListOfUsers(start_point, max_result);
        List<UserAccount> accountList = new LinkedList<>();

        for (var o : dataSetList) {
            accountList.add(new UserAccount(o.getId(), o.getUsername(), o.getPassword(), o.getTelegram()));
        }

        return accountList;
    }

    /**
     * Use to delete user from DB via username.
     * @param username username should be valid and point to some user, otherwise exception being thrown.
     * @throws NoSuchUserException being thrown when there is no such no user in DB with given username.
     * @throws InvalidUsernameException being thrown when username is invalid.
     */
    public void deleteUserByUsername(String username) throws NoSuchUserException, InvalidUsernameException {


        if (!verifyUsername(username)) {

            throw new InvalidUsernameException(username);
        }

        dbService.deleteUserByUsername(username);
    }

    /**
     * Use to delete user from DB via telegram_id.
     * @param telegram telegram_id should be valid and point to some user, otherwise exception being thrown.
     * @throws NoSuchUserException being thrown when there is no such no user in DB with given username.
     * @throws InvalidTelegramIdException if telegram_id is empty or consists not only from 0-9.
     */
    public void deleteUserByTelegram(String telegram) throws NoSuchUserException, InvalidTelegramIdException {

        if (!verifyTelegramId(telegram)) {

            throw new InvalidTelegramIdException(telegram);
        }

        dbService.deleteUserByTelegram(telegram);
    }

    /**
     * Use to update user's password and telegram_id.
     * @param username should point to some user, which is being updated.
     * @param new_password new password should be valid.
     * @param new_telegram should be valid telegram_id. If no need to update it,
     *                                                  use version without telegram parameter then.
     * @throws NoSuchUserException being thrown when there is no such no user in DB with given username.
     * @throws InvalidUsernameOrPasswordException if username or password parameter is empty or contains invalid characters.
     * @throws InvalidTelegramIdException if telegram_id is empty or consists not only from 0-9.
     */
    public void updateUser(String username, String new_password, String new_telegram)
            throws NoSuchUserException, InvalidUsernameOrPasswordException, InvalidTelegramIdException {

        if (!verifyUsernamePassword(username, new_password)) {

            throw new InvalidUsernameOrPasswordException();

        } else if (!verifyTelegramId(new_telegram)) {

            throw new InvalidTelegramIdException(new_telegram);
        }

        dbService.updateUser(username, new_password, new_telegram);

    }

}