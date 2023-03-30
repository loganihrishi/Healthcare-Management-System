package ui.gui;

import model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Represents the GUI class for looking for appointment details using patient's personal health number.
 */

public class FindUsingPhnGUI extends StartApplicationGUI {

    private JPanel mainPanel = new JPanel();

    // EFFECTS: displays the pop-up window showing the appropriate appointment message
    public FindUsingPhnGUI() throws IOException {
        JOptionPane.showOptionDialog(null, getMainPanel(),
                "Appointment Details", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                new ImageIcon(logo), new Object[]{"OK"},
                "OK");
    }

    // EFFECTS: returns the main panel
    private JPanel getMainPanel() {
        mainPanel.add(inputPHN());
        return mainPanel;
    }

    // EFFECTS: returns the appropriate JPanel as per the user's input
    private JPanel inputPHN() {
        JTextField phn = new JTextField();
        Object[] message = {"Enter Patient's Personal Health Number:", phn};
        int option = JOptionPane.showConfirmDialog(null, message,
                "Input Health Number", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                new ImageIcon(logo));
        if (option == JOptionPane.OK_OPTION) {
            String number = phn.getText();
            Appointment appointment = this.getAppointments().findAppointment(Integer.parseInt(number));
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


    // REQUIRES: appointment
    // EFFECTS: returns the appointment formatted in JText
    private JTextArea formatAppointment(Appointment appointment) {
        JTextArea resultTextArea = new JTextArea(10, 25);
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);
        String res = "Date: " + appointment.getDate().toString() + "\n"
                +
                "Time: " + appointment.getTime().toString() + "\n"
                +
                appointment.getPatient().toString();
        resultTextArea.setText(res);
        resultTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        resultTextArea.setBackground(Color.WHITE);
        return resultTextArea;
    }
}
