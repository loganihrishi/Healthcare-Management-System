package ui;

import java.io.IOException;
import java.text.ParseException;
/**
 * This is the main class of the project. It is used to run the application and start its user interface.
 */

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        StartApplication st = new StartApplication();
        st.displayMenu();
    }
}
