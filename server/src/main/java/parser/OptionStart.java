package parser;

import org.eclipse.jetty.server.Server;

public class OptionStart {
    private String name;
    private String description;

    public OptionStart() {
        this.name = "start";
        this.description = "Starts server";
    }

    public void run(Server server) {
        try {
            server.start();
            System.out.println("||| Server started |||");
		} catch (Exception e) {
			System.out.println("Can't start server! " + e.getMessage());
		}
        
    }
    
    
}
