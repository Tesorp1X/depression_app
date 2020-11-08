package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accountService.AccountService;

public class ServletUpdateUser extends HttpServlet {

	private static final long serialVersionUID = 1L;
    private AccountService accountService;
    
    public ServletUpdateUser(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
    }
    
}
