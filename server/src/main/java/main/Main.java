package main;

import accountService.AccountService;
import dbService.DBService;
import servletsUser.ServletRegister;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servletsUser.ServletTest;

/**
 * @author Tesorp1X
 * @author KyMaKa
 */
public class Main {
    public static void main(String[] args) throws Exception {

        //Services
        DBService dbService = new DBService();
        AccountService accountService = new AccountService(new DBService());
        //AccountService accountService = new AccountService(dbService);

        //Servlets
        /* !!! Put accountService into users servlets. !!!  */


        //User servlets
        ServletRegister sRegister = new ServletRegister(accountService);
        ServletTest sTest = new ServletTest(accountService);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(sRegister), "/AddNewUser");
        context.addServlet(new ServletHolder(sTest), "/test");


        //Server itself
        Server server = new Server(8080);
        server.setHandler(context);


        //TODO: Handle exceptions.
        server.start();
        server.join();

    }
}
