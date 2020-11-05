package main;

import accountService.AccountService;
import dbService.DBService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


public class Main {
    public static void main(String[] args) throws Exception {

        //Services
        DBService dbService = new DBService();
        AccountService accountService = new AccountService(new DBService());
        //AccountService accountService = new AccountService(dbService);

        //Servlets
        /* !!! Put accountService into users servlets. !!!  */

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        //Server itself
        Server server = new Server(8080);
        server.setHandler(context);


        //TODO: Handle exceptions.
        server.start();
        server.join();

    }
}
