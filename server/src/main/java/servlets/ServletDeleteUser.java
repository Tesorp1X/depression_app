package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accountService.AccountService;
import accountService.InvalidTelegramIdException;
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

        response.setContentType("text/plain;charset=utf-8");;

        String telegramId = request.getParameter("t_id");
        String username;

        if (telegramId == null) {
            username = request.getParameter("username");

            try {
                accountService.deleteUserByUsername(username);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (NoSuchUserException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("ERROR: can't get user! " + e.toString());
            } catch (InvalidUsernameException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("ERROR: can't get user! " + e.toString());
            }

            if (response.getStatus() == HttpServletResponse.SC_OK) {
                response.getWriter().println("User with username: " + username + " deleted");
            }

        } else {

            try {
                accountService.deleteUserByTelegram(telegramId);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (NoSuchUserException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("ERROR: can't get user! " + e.toString());
            } catch (InvalidTelegramIdException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("ERROR: can't get user! " + e.toString());
            }

            if (response.getStatus() == HttpServletResponse.SC_OK) {
                response.getWriter().println("User with Telegram id: " + telegramId + " deleted."); 
            }
        }

    }
    
}
