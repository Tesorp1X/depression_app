package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accountService.AccountService;
import accountService.InvalidUsernameException;
import dbService.NoSuchUserException;

/**
 * @author KyMaKa
 */
public class ServletDeleteUser extends HttpServlet {

	private static final long serialVersionUID = 1L;
    private AccountService accountService;

    public ServletDeleteUser(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String telegramId = request.getParameter("t_id");
        String username;

        if (telegramId == null) {
            username = request.getParameter("username");

            try {

                accountService.deleteUserByUsername(username);
            } catch (NoSuchUserException | InvalidUsernameException e) {

                if (e instanceof NoSuchUserException) {
                    response.getWriter().println("ERROR: can't get user! There is no such user");
                } else {
                    response.getWriter().println("ERROR: can't get user! Invalid username!");
                }
            }
            response.getWriter().println("User with username: " + username + " deleted");

        } else {
            try {

                accountService.deleteUserByTelegram(telegramId);
            } catch (NoSuchUserException | InvalidUsernameException e) {

                if (e instanceof NoSuchUserException) {
                    response.getWriter().println("ERROR: can't get user! There is no such user");
                } else {
                    response.getWriter().println("ERROR: can't get user! Invalid username!");
                }
            }
            response.getWriter().println("User with Telegram id: " + telegramId + " deleted"); 
        }

    }
    
}
