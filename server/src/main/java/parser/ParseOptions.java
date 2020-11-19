package parser;

import org.apache.commons.cli.Options;

public class ParseOptions {

    Options options = new Options();

    public Options createOptions() {
        options.addOption("exit", false, "Close connection with server and stops it + closes cmd");
        options.addOption("check", false, "Writes checked");
        options.addOption("stop", false, "Close connection with server and stops it");
        return options;
    }

    //TODO: add new option.
    //Possibe one more class to represent option.
    public void addOption() {

    }
}
