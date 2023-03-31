package ui.gui;

import java.io.IOException;
/**
 * This is the main class of the application. It is used to start the GUI of the application.
 */

public class Main {
    public static void main(String[] args) {
        try {
            new StartApplicationGUI();
        } catch (IOException e) {
            System.err.println("Error running the application");
        }
    }
}

