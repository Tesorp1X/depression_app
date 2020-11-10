package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accountService.AccountService;
import accountService.InvalidUsernameException;
import accountService.UserAccount;
import dbService.NoSuchUserException;

/**
 * @author KyMaKa
 */
public class ServletGetUser extends HttpServlet {

    private static final long serialVersionUID = 5673755050970387664L;

    private AccountService accountService;

    public ServletGetUser(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain;charset=utf-8");

        String telegramId = request.getParameter("t_id");
        String username;
        UserAccount account = null;

        
        if (telegramId == null) { //If user registered from site. 
            
            username = request.getParameter("username");

            try {
                account = accountService.getUserByUsername(username);
                response.setStatus(HttpServletResponse.SC_OK);
              
            } catch (NoSuchUserException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("ERROR: can't get user! " + e.toString());
            } catch (InvalidUsernameException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("ERROR: can't get user! " + e.toString());
            }

        } else {

            try {
                account = accountService.getUserByTelegram(telegramId);
                response.setStatus(HttpServletResponse.SC_OK);

            } catch (NoSuchUserException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("ERROR: can't get user! " + e.toString());
            } catch (InvalidUsernameException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("ERROR: can't get user! " + e.toString());

            }
        }

        if (response.getStatus() == HttpServletResponse.SC_OK) {
            response.getWriter().print(account.toString());
        }
    }

}