package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accountService.AccountService;
import accountService.UserAccount;


/**
 * @author KyMaKa
 */
public class ServletGetListOfUsers extends HttpServlet {

	private static final long serialVersionUID = 1L;
    AccountService accountService;
    
    public ServletGetListOfUsers(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        List<UserAccount> users = null;

        String start_point = request.getParameter("start_point");

        if(start_point == null) {
            users = accountService.getListOfUsers();
        } else {
            String max_result = request.getParameter("max_result");
            users = accountService.getListOfUsers(Integer.parseInt(start_point), Integer.parseInt(max_result));
        }

        if (users != null) {
            users.forEach(user ->
            {
			    try {
				    response.getWriter().println(user.toString());
			    } catch (IOException e) {
				    e.getMessage();
			    }
            });
        }
    }
}
