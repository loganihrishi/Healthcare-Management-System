package ui.tui;

import javax.imageio.IIOException;
import java.io.IOException;
import java.text.ParseException;

/**
 * This is the main class of the project. It is used to run the application and start its user interface.
 */

public class Main {
    public static void main(String[] args) {
        try {
            new StartApplication();
        } catch (Exception e) {
            System.err.println("Error running the application");
        }
    }
}
