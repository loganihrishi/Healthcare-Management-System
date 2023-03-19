package gui;

import model.Appointment;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/**
 * This class represents the GUI of the add appointment feature of the main GUI.
 */

public class AppDetailsGUI extends StartApplicationGUI {

    // EFFECTS: starts the add appointment GUI
    public AppDetailsGUI() throws IOException {
        super();
        addAppointment();
    }

    // MODIFIES: this
    // EFFECTS: adds the appointment if possible, displays appropriate error message otherwise
    @SuppressWarnings("methodlength")
    private void addAppointment() {
        JFrame frame = new JFrame("Add an Appointment");
        JLabel dateLabel = new JLabel("Date (yyyy/mm/dd):");
        JTextField dateField = new JTextField();
        dateField.setEditable(true);
        JLabel timeLabel = new JLabel("Time (hh:mm):");
        JTextField timeField = new JTextField();
        timeField.setEditable(true);
        JLabel phnLabel = new JLabel("Personal Health Number:");
        JTextField phnField = new JTextField();
        phnField.setEditable(true);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate date = LocalDate.parse(dateField.getText(), formatter);
                LocalTime time = LocalTime.parse(timeField.getText() + ":00");
                Patient patient = getPatientFromPHN(Integer.parseInt(phnField.getText()));

                if (patient == null) {
                    JOptionPane.showMessageDialog(frame, "Patient Not Found!", "Failure",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("data/gregor.jpeg"));
                } else {

                    Appointment newAppointment = new Appointment(date, time, patient);
                    if (appointments.findAppointment(Integer.parseInt(phnField.getText())) != null) {
                        JOptionPane.showMessageDialog(frame, "Patient Already has an appointment",
                                "Failure",
                                JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon("data/gregor.jpeg"));
                    }

                    if (overlaps(date, time)) {
                        JOptionPane.showMessageDialog(frame, "Appointment clashes with another appointment",
                                "Failure",
                                JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon("data/gregor.jpeg"));
                    } else {
                        appointments.addAppointment(newAppointment);
                        JOptionPane.showMessageDialog(frame, "Appointment Scheduled successfully", "Success",
                                JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/gregor.jpeg"));

                        int option = JOptionPane.showConfirmDialog(frame, "Do you want to save the data?", "Save data",
                                JOptionPane.YES_NO_OPTION);

                        if (option == JOptionPane.YES_OPTION) {
                            try {
                                appointmentFile.writeAppointmentsToFile(appointments.getAppointments());
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            JOptionPane.showMessageDialog(frame, "Data saved successfully!", "Success",
                                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/gregor.jpeg"));
                        }
                    }
                }
                dateField.setText("");
                timeField.setText("");
                phnField.setText("");
            }
        });
        makePanel(dateLabel, dateField, timeLabel, timeField, phnLabel, phnField, frame, submitButton);
    }

    // REQUIRES: all the valid fields required to make the panel
    // EFFECTS: sets the specifications of the panel
    private void makePanel(JLabel dateLabel, JTextField dateField, JLabel timeLabel, JTextField timeField,
                           JLabel phnLabel,
                           JTextField phnField, JFrame frame, JButton submitButton) {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(timeLabel);
        panel.add(timeField);
        panel.add(phnLabel);
        panel.add(phnField);
        panel.add(submitButton);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    // REQUIRES: a valid PHN
    // EFFECTS: returns Patient object if it exists, null otherwise
    private Patient getPatientFromPHN(int phn) {
        for (Patient p : patients) {
            if (p.getPhn() == phn) {
                return p;
            }
        }
        return null;
    }

    // REQUIRES: a valid date and time
    // EFFECTS: returns true if the given date and time overlap with another appointment
    private boolean overlaps(LocalDate date, LocalTime time) {
        for (Appointment a: appointments.getAppointments()) {
            if (a.getDate().equals(date) && a.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }
}
