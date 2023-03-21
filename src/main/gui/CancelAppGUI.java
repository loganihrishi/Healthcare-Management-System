package gui;

import model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
/**
 * This class represents the GUI to cancel the appointment.
 */

public class CancelAppGUI extends StartApplicationGUI {

    // EFFECTS: creates a CancelAppGUI and calls a method to cancel the appointment
    public CancelAppGUI() throws IOException {
        super();
        cancelAppointment();
    }

    // MODIFIES: this
    // EFFECTS: cancels the appointment, if possible, displays the appropriate error message otherwise
    private void cancelAppointment() {
        JFrame frame = new JFrame("Cancel an appointment");
        JLabel phnLabel = new JLabel("Personal Health Number:");
        JTextField phnTextField = new JTextField();
        phnTextField.setEditable(true);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int phn = Integer.parseInt(phnTextField.getText());
                Appointment toBeCancelled = appointments.findAppointment(phn);
                if (toBeCancelled == null) {
                    JOptionPane.showMessageDialog(frame, "Appointment does not exist", "Failure",
                            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(gregorMeme));
                } else {
                    cancelAppMessage(toBeCancelled, frame, phnTextField);
                }
            }
        });
        makePanel(phnLabel, phnTextField, submitButton, frame);
    }

    // REQUIRES: an appointment, a JFrame and a phnTextField
    // MODIFIES: this
    // EFFECTS; displays the appropriate message based on user's inputs
    private void cancelAppMessage(Appointment toBeCancelled, JFrame frame, JTextField phnTextField) {
        appointments.removeAppointment(toBeCancelled);
        JOptionPane.showMessageDialog(frame, "Appointment Cancelled", "Success",
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon(gregorMeme));

        int option = JOptionPane.showConfirmDialog(frame, "Do you want to save the data?",
                "Save data",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                appointmentFile.writeAppointmentsToFile(appointments.getAppointments());
            } catch (IOException ex) {
                System.err.println("Error saving the data to the file: " + ex.getMessage());
            }
            JOptionPane.showMessageDialog(frame, "Data saved successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(gregorMeme));
        }
        phnTextField.setText("");
    }

    // EFFECTS: creates the appropriate panel
    private void makePanel(JLabel phnLabel, JTextField phnField, JButton submitButton, JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(2,2));
        panel.add(phnLabel);
        panel.add(phnField);
        panel.add(submitButton);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
