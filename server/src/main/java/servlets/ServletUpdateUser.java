package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accountService.AccountService;
import accountService.InvalidTelegramIdException;
import accountService.InvalidUsernameOrPasswordException;
import dbService.NoSuchUserException;

public class ServletUpdateUser extends HttpServlet {

	private static final long serialVersionUID = 1L;
    private AccountService accountService;
    
    public ServletUpdateUser(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String new_pass = request.getParameter("new_password");
        String new_telegramId = request.getParameter("new_telegramId");

        try {
			accountService.updateUser(username, new_pass, new_telegramId);
		} catch (NoSuchUserException e) {
			response.getWriter().println("ERROR: can't update user. There is no such user!");
		} catch (InvalidUsernameOrPasswordException e) {
			response.getWriter().println("ERROR: can't update user. Invalid username or new password");
		} catch (InvalidTelegramIdException e) {
			response.getWriter().println("ERROR: can't update user. Invalid new Telegram id");
		}
        
        response.getWriter().println("User: " + username + " updated.");
    }
    
}
