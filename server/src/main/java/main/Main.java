package main;

import accountService.AccountService;
import dbService.DBService;
import noteService.NoteService;
import parser.ConsoleParser;
import servlets.*;

import org.apache.commons.cli.CommandLine;
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
        DBService dbService = new DBService();
        //AccountService accountService = new AccountService(new DBService());
        AccountService accountService = new AccountService(dbService);
        //NoteService noteService = new NoteService(new DBService());
        NoteService noteService = new NoteService(dbService);

        //Servlets
        /* !!! Put accountService into users servlets. !!!  */

        //User servlets
        ServletRegister sRegister = new ServletRegister(accountService);
        ServletGetUser sGetUser = new ServletGetUser(accountService);
        ServletDeleteUser sDeleteUser = new ServletDeleteUser(accountService);
        ServletUpdateUser sUpdateUser = new ServletUpdateUser(accountService);
        ServletGetListOfUsers sGetListOfUsers = new ServletGetListOfUsers(accountService);
        ServletLogin sLogin = new ServletLogin(accountService);

        //Note Servlets
        /* !!! Put NoteService into notes servlets. !!!  */
        ServletGetListOfNotes sGetListOfNotes = new ServletGetListOfNotes(noteService);
        ServletAddNote sAddNote = new ServletAddNote(noteService);
        ServletChangeNote sChangeNote = new ServletChangeNote(noteService);
        ServletDeleteNote sDeleteNote = new ServletDeleteNote(noteService);
        ServletGetNote sGetNote = new ServletGetNote(noteService);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(sRegister), "/AddNewUser");
        context.addServlet(new ServletHolder(sGetUser), "/GetUser");
        context.addServlet(new ServletHolder(sDeleteUser), "/DeleteUser");
        context.addServlet(new ServletHolder(sUpdateUser), "/UpdateUser");
        context.addServlet(new ServletHolder(sGetListOfUsers), "/GetListOfUsers");
        context.addServlet(new ServletHolder(sLogin), "/Login");

        context.addServlet(new ServletHolder(sGetListOfNotes), "/GetListOfNotes");
        context.addServlet(new ServletHolder(sAddNote), "/AddNote");
        context.addServlet(new ServletHolder(sChangeNote), "/ChangeNote");
        context.addServlet(new ServletHolder(sDeleteNote), "/DeleteNote");
        context.addServlet(new ServletHolder(sGetNote), "/GetNote");


        //Server itself
        Server server = new Server(8080);
        server.setHandler(context);
        ConsoleParser parser = new ConsoleParser(args, dbService, server);

        //If everything is alright then we can start server from console too.
        //TODO: test it!
        parser.parseCMD();

        //TODO: Handle exceptions.
        //server.start();
        //server.join();


    }
}
