package servlets;

import accountService.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author KyMaKa
 */
public class ServletRegister extends HttpServlet {

    AccountService accountService;

    public ServletRegister(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * DON'T KNOW WHAT IT IS BUT YEAH, ENJOY.
     */
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String telegramId = request.getParameter("t_id");
        String username;
        String pass;
        long returnedValue = -1; //By default contains -1 as there is already user with such name.
        if (telegramId == null) { //If we register new user from site by comb of username and password.
            username = request.getParameter("username");
            pass = request.getParameter("password");

            try {
                returnedValue = accountService.registerNewUser(username, pass);
            } catch (InvalidUsernameOrPasswordException e) {
                response.getWriter().println("ERROR: can't register new user! Invalid username.");
            }
            
        } else {

            try {
                returnedValue = accountService.registerNewUser(telegramId); //If we added new user to db, then value changed to if of user.
            } catch (InvalidUsernameException e) {
                response.getWriter().println("ERROR: can't register new user! Invalid username.");
            }
        }

        if (returnedValue == -1) {
            response.getWriter().println("ERROR: can't register new user! User with such username is already exist.");
        } else {
            response.getWriter().print("UUID: ");
            response.getWriter().println(telegramId);
            response.getWriter().print("User id in db: ");
            response.getWriter().println(returnedValue);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
