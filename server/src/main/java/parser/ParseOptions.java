package parser;

import org.eclipse.jetty.server.Server;

import dbService.DBService;

public class ParseOptions {
    private Server server;
    private DBService dbService;
    private OptionStart start;
    private OptionStop stop;
    private OptionExit exit;

    public ParseOptions(Server server, DBService dbService) {
        this.server = server;
        this.dbService = dbService;
        this.exit = new OptionExit();
        this.stop = new OptionStop();
        this.start = new OptionStart();
    }

    public void parse(String name) {
        if (name.equals("start")) {
            start.run(server);
        }

        if (name.equals("stop")) {
            stop.run(server, dbService);
        }

        if (name.equals("exit")) {
            exit.run(server, dbService);
        }
    }
}
