package parser;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.eclipse.jetty.server.Server;

import dbService.DBService;

public class ConsoleParser {

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

    public void parseCMD() throws Exception {
        options = parseOptions.createOptions();
        cmd = parser.parse(options, args);

        if (cmd.hasOption("exit")) {
            dbService.closeSessionFactory();
            System.out.println("All sessions are closed");
            server.stop();
            System.exit(0);
        }
        if (cmd.hasOption("check")) {
            System.out.println("Checked");
            return;
        }
        if (cmd.hasOption("stop")) {
            dbService.closeSessionFactory();
            System.out.println("All sessions are closed");
            server.stop();
            return;
        }

    }
    
}
