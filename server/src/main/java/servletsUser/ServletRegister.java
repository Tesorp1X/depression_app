package servletsUser;

import accountService.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        String telegramId = request.getParameter("id");
        try {
            accountService.registerNewUser(telegramId);
        } catch (InvalidUsernameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        response.getWriter().println(telegramId);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
