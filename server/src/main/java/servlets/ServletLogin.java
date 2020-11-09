package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accountService.AccountService;

/**
 * @author KyMaKa
 */
public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private AccountService accountService;
    
    public ServletLogin(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long returnedValue = -1;
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        returnedValue = accountService.loginViaUsername(username, password);

        if (returnedValue == -1) {
            response.getWriter().println("ERROR: can't login. Incorrect username or password.");
        } else {
            response.getWriter().print("User id in db: ");
            response.getWriter().println(returnedValue);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
    
}
