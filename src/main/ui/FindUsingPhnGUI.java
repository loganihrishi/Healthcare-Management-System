package ui;

import model.Appointment;
import ui.StartApplicationGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents the GUI class for looking for appointment details using patient's personal health number.
 */

public class FindUsingPhnGUI extends StartApplicationGUI {

    private JButton mainMenu = new JButton("Click Here to go back");
    private JPanel mainPanel = new JPanel();

    // EFFECTS: starts the find patient gui
    public FindUsingPhnGUI() throws IOException {
        initialize();
    }

    // EFFECTS: initializes the GUI of the healthcare management system
    private void initialize() {
        this.setTitle("Healthcare Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.WHITE);
        this.setSize(new Dimension(dimX, dimY));
        this.add(getMainPanel());
        this.setVisible(true);
    }

    // EFFECTS: returns the main panel
    private JPanel getMainPanel() {
        mainPanel.add(inputPHN());
        mainPanel.add(getMainMenu());
        return mainPanel;
    }

    // EFFECTS: returns the appropriate JPanel as per the user's input
    private JPanel inputPHN() {
        JTextField phn = new JTextField();
        Object[] message = {"Enter Patient's Personal Health Number:", phn};
        int option = JOptionPane.showConfirmDialog(null, message,
                "Input Health Number", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String number = phn.getText();
            Appointment appointment = super.getAppointments().findAppointment(Integer.parseInt(number));
            JPanel resultPanel = new JPanel();
            if (appointment == null) {
                JLabel curr = new JLabel("Appointment Not found");
                curr.setFont(new Font("Arial", Font.BOLD, 24));
                resultPanel.add(curr);
            } else {
                resultPanel.add(formatAppointment(appointment));
            }
            return resultPanel;
        }
        return null;
    }

    // EFFECTS: returns the JButton for the main menu
    private JButton getMainMenu() {
        mainMenu.setFont(new Font("Arial", Font.PLAIN, 20));
        this.mainMenu.setPreferredSize(new Dimension(500, 100));
        this.mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new StartApplicationGUI();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return mainMenu;
    }

    // REQUIRES: appointment
    // EFFECTS: returns the appointment formatted in JText
    private JTextArea formatAppointment(Appointment appointment) {
        JTextArea resultTextArea = new JTextArea();
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);
        String res = "Date: " + appointment.getDate().toString() + "\n"
                +
                "Time: " + appointment.getTime().toString() + "\n"
                +
                appointment.getPatient().toString();
        resultTextArea.setText(res);
        resultTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        resultTextArea.setBackground(Color.WHITE);
        return resultTextArea;
    }
}
