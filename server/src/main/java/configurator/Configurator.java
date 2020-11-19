package configurator;

import java.util.Properties;

/**
 * Use to parse <SQL name>.conf file.
 * File must contain those fields: CONNECT_URL, CONNECT_USER, CONNECT_PASSWORD.
 * @author Tesorp1X
 */
public class Configurator {

    private final String CONNECT_URL;

    private final String CONNECT_USER;

    private final String CONNECT_PASSWORD;


    /** //TODO: JavaDoc.
     * @param pathname string which is path to that file.
     */
    public Configurator(String pathname) throws ConfiguratorException {

        Properties props = new Properties();

        try {

            props.load(new FileInputStream(new File(pathname)));

            CONNECT_URL = props.getProperty("CONNECT_URL");

            CONNECT_USER = props.getProperty("CONNECT_USER");

            CONNECT_PASSWORD = props.getProperty("CONNECT_PASSWORD");


        } catch (IOException | IllegalArgumentException | NullPointerException e) {

            System.err.println("ERROR OCCURRED : " + e.getMessage());
            e.printStackTrace();
            throw new ConfiguratorException(e);
        }


    }

    //Getters

    public String getUrl() {

        return CONNECT_URL;
    }

    public String getUser() {

        return CONNECT_USER;
    }

    public String getPassword() {

        return CONNECT_PASSWORD;
    }

}