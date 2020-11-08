package main;

import accountService.AccountService;
import dbService.DBService;
import servlets.ServletDeleteUser;
import servlets.ServletGetListOfUsers;
import servlets.ServletGetUser;
import servlets.ServletRegister;
import servlets.ServletUpdateUser;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author Tesorp1X
 * @author KyMaKa
 */
public class Main {
    public static void main(String[] args) throws Exception {

        //Services
        AccountService accountService = new AccountService(new DBService());
        //AccountService accountService = new AccountService(dbService);

        //Servlets
        /* !!! Put accountService into users servlets. !!!  */

        //User servlets
        ServletRegister sRegister = new ServletRegister(accountService);
        ServletGetUser sGetUser = new ServletGetUser(accountService);
        ServletDeleteUser sDeleteUser = new ServletDeleteUser(accountService);
        ServletUpdateUser sUpdateUser = new ServletUpdateUser(accountService);
        ServletGetListOfUsers sGetListOfUsers = new ServletGetListOfUsers(accountService);



        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(sRegister), "/AddNewUser");
        context.addServlet(new ServletHolder(sGetUser), "/GetUser");
        context.addServlet(new ServletHolder(sDeleteUser), "/DeleteUser");
        context.addServlet(new ServletHolder(sUpdateUser), "/UpdateUser");
        context.addServlet(new ServletHolder(sGetListOfUsers), "/GetListOfUsers");


        //Server itself
        Server server = new Server(8080);
        server.setHandler(context);


        //TODO: Handle exceptions.
        server.start();
        server.join();

    }
}
