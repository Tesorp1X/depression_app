package servlets;

import java.io.IOException;
import java.util.Iterator;
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain;charset=utf-8");

        List<UserAccount> users = null;

        String start_point = request.getParameter("start_point");

        if(start_point == null) {
            users = accountService.getListOfUsers();
        } else {
            String max_result = request.getParameter("max_result");
            try {
                users = accountService.getListOfUsers(Integer.parseInt(start_point), Integer.parseInt(max_result));
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (IndexOutOfBoundsException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("ERROR: index out of bound.");
            } catch (NegativeArraySizeException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("ERROR: negative array size.");
            }
        }

        if (users != null) {
            Iterator<UserAccount> iterator = users.iterator();
            while(iterator.hasNext()) {
                response.getWriter().println(iterator.next().toString());
            }
        }
    }
}
