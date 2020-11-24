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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain;charset=utf-8");


        boolean is_bot = Boolean.parseBoolean(request.getParameter("is_bot"));
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
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("ERROR: can't register new user! " + e.toString());
            }
            
        } else {

            try {
                returnedValue = accountService.registerNewUser(telegramId); //If we added new user to db, then value changed to if of user.
            } catch (InvalidUsernameException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("ERROR: can't register new user! " + e.toString());
            }
        }

        if (returnedValue == -1) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().println("ERROR: can't register new user! User with such username is already exist.");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            if (!is_bot) {
                response.getWriter().print("UUID: ");
                response.getWriter().println(telegramId);
                response.getWriter().print("User id in db: ");
            }
            response.getWriter().print(returnedValue);
        }
    }
}
