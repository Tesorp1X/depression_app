package parser;

import java.io.Console;
import java.util.List;

import org.eclipse.jetty.server.Server;

import dbService.DBService;

public class ConsoleParser extends Thread {

    private String[] args; 
    private DBService dbService;
    private Server server;
    private Console cmd;
    private ParseOptions pOptions;

    public ConsoleParser(String[] args, DBService dbService, Server server) {
        this.args = args;
        this.dbService = dbService;
        this.server = server;
        this.pOptions = new ParseOptions(server, dbService);
    } 

    public void listenCMD() {
        cmd = System.console();
        if (cmd == null) {
            System.out.println("Can't get console");
        }

        String command;

        while (true) {
            command = cmd.readLine();
            pOptions.parse(command);
        }
    }
    
}
