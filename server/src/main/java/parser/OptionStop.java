package parser;

import org.eclipse.jetty.server.Server;

import dbService.DBService;

public class OptionStop {
    private String name;
    private String description;

    public void OptionStop() {
        this.name = "stop";
        this.description = "Closes latest session, commit latest transaction and stops server";
    }

    public void run(Server server, DBService dbService) {
        dbService.closeSessionFactory();
        try {
            server.stop();
            System.out.println("Server stopped");
		} catch (Exception e) {
			System.out.println("Can't stop server! " + e.getMessage());
		}
    }
}
