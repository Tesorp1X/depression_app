package parser;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.server.Server;

import dbService.DBService;

public class ConsoleParser extends Thread {

    private String[] args; 
    private DBService dbService;
    private Server server;

    public ConsoleParser(String[] args, DBService dbService, Server server) {
        this.args = args;
        this.dbService = dbService;
        this.server = server;
    } 

    Options options;

    ParseOptions parseOptions = new ParseOptions();
    CommandLineParser parser = new  DefaultParser();
    CommandLine cmd;

    public void run() {
        options = parseOptions.createOptions();
        try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (cmd.hasOption("exit")) {
            dbService.closeSessionFactory();
            System.out.println("All sessions are closed");
            try {
                server.stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
            }
            System.exit(0);
        }
        if (cmd.hasOption("check")) {
            System.out.println("Checked");
            return;
        }
        if (cmd.hasOption("stop")) {
            dbService.closeSessionFactory();
            System.out.println("All sessions are closed");
            try {
				server.stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return;
        }
        if (cmd.hasOption("start")) {
            try {
				server.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }
    
}
