package ui.gui;

import model.Event;
import model.EventLog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
/**
 * This is the main class of the application. It is used to start the GUI of the application.
 */

public class Main {
    public static void main(String[] args) {
        try {
            StartApplicationGUI gui = new StartApplicationGUI();
            System.out.println(EventLog.getInstance().toString());
            gui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    for (Event el : EventLog.getInstance()) {
                        System.out.println(el.toString());
                    }

                    //THEN you can exit the program
                    System.exit(0);
                    EventLog.getInstance().clear();
                }
            });

        } catch (IOException e) {
            System.err.println("Error running the application");
        }
    }
}