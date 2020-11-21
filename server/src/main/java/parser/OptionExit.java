package parser;

import org.eclipse.jetty.server.Server;

import dbService.DBService;

public class OptionExit {
    private String name;
    private String description;

    public OptionExit() {
        this.name = "exit";
        this.description = "Stops server and interrupts program execution";
    }

    public void run(Server server, DBService dbService) {
        dbService.closeSessionFactory();
        try {
            server.stop();
        } catch (Exception e) {
            System.out.println("Can't stop server! " + e.getMessage());
        } finally {
            System.exit(0);
        }
    }
}
