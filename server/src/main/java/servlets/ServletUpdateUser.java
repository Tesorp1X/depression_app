package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accountService.AccountService;
import accountService.InvalidTelegramIdException;
import accountService.InvalidUsernameOrPasswordException;
import dbService.NoSuchUserException;

/**
 * @author KyMaKa
 */
public class ServletUpdateUser extends HttpServlet {

	private static final long serialVersionUID = 1L;
    private AccountService accountService;
    
    public ServletUpdateUser(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain;charset=utf-8");

        String username = request.getParameter("username");
        String new_pass = request.getParameter("new_password");
        String new_telegramId = request.getParameter("new_telegramId");

        try {
            accountService.updateUser(username, new_pass, new_telegramId);
            response.setStatus(HttpServletResponse.SC_OK);
		} catch (NoSuchUserException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().println("ERROR: can't update user. " + e.toString());
		} catch (InvalidUsernameOrPasswordException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().println("ERROR: can't update user. " + e.toString());
		} catch (InvalidTelegramIdException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().println("ERROR: can't update user. " + e.toString());
		}
        
        if (response.getStatus() == HttpServletResponse.SC_OK) {
            response.getWriter().println("User: " + username + " updated.");
        }
    }
    
}
