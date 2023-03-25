package ui.gui;

import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents the GUI class for adding the patient details.
 */

public class PatientDetailsGUI extends StartApplicationGUI {

    // EFFECTS: starts the PatientDetailsGUI part of the main GUI
    public PatientDetailsGUI() throws IOException {
        super();
        addPatient();
    }

    // MODIFIES: this
    // EFFECTS: creates the GUI for Patient details
    @SuppressWarnings("methodlength")
    private void addPatient() {
        JFrame frame = new JFrame("Add Patient");
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel sexLabel = new JLabel("Sex (M/F):");
        JTextField sexField = new JTextField();
        JLabel insLbl = new JLabel("Insurance:");
        JTextField insField = new JTextField();
        JLabel phnLabel = new JLabel("Personal Health Number:");
        JTextField phnField = new JTextField();
        JLabel disNameLbl = new JLabel("Disease Name:");
        JTextField disNameFld = new JTextField();
        JLabel dateLbl = new JLabel("Diagnosis Date (yyyy-MM-dd):");
        JTextField dateFld = new JTextField();
        makeEditable(nameField, ageField, sexField, insField, phnField, disNameFld, dateFld);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handlePatientDetails(nameField, ageField, sexField, insField, phnField, disNameFld,
                        dateFld, frame);
            }
        });
        makePanel(nameLabel, nameField, ageLabel, ageField, sexLabel, sexField, insLbl, insField, phnLabel, phnField,
                disNameLbl, disNameFld, dateLbl, dateFld, submitButton,
                frame);
    }

    // REQUIRES: all the proper label fields and their text values
    // EFFECTS: generates the appropriate labels and buttons for PatientDetailsGUI
    private void makePanel(JLabel nameLabel, JTextField nameField, JLabel ageLabel, JTextField ageField,
                           JLabel sexLabel,
                           JTextField sexField,
                           JLabel insuranceLabel, JTextField insuranceField, JLabel phnLabel, JTextField phnField,
                           JLabel diseaseNameLabel, JTextField diseaseNameField, JLabel diagnosisDateLabel,
                           JTextField diagnosisDateField,
                           JButton submitButton, JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(sexLabel);
        panel.add(sexField);
        panel.add(insuranceLabel);
        panel.add(insuranceField);
        panel.add(phnLabel);
        panel.add(phnField);
        panel.add(diseaseNameLabel);
        panel.add(diseaseNameField);
        panel.add(diagnosisDateLabel);
        panel.add(diagnosisDateField);
        panel.add(submitButton);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    // REQUIRES: a frame
    // MODIFIES: this
    // EFFECTS: displays the appropriate message to the user after saving the data
    private void addPatientSuccessMessage(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Patient added successfully!", "Success",
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon(steveMeme));
        int option = JOptionPane.showConfirmDialog(frame, "Do you want to save the data?", "Save data",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Save the data to file
            try {
                patientFile.writePatientsToFile(patients);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(frame, "Data saved successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(steveMeme));
        }
    }

    // REQUIRES: all the JTextFields and JFrame
    // MODIFIES: this
    // EFFECTS: handles the patient's details as needed
    private void handlePatientDetails(JTextField nameField, JTextField ageField, JTextField sexField,
                                      JTextField insuranceField, JTextField phnField, JTextField diseaseNameField,
                                      JTextField diagnosisDateField, JFrame frame) {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String sex = sexField.getText();
        String insurance = insuranceField.getText();
        int phn = Integer.parseInt(phnField.getText());
        String disName = diseaseNameField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate diagnosisDate = LocalDate.parse(diagnosisDateField.getText(), formatter);
        // Create a new patient object
        Patient patient = new Patient(name, age, sex, insurance, phn);
        patient.addDisease(disName, diagnosisDate);
        // Add the patient to the list
        patients.add(patient);
        // Clear the text fields
        nameField.setText("");
        ageField.setText("");
        sexField.setText("");
        insuranceField.setText("");
        phnField.setText("");
        addPatientSuccessMessage(frame);
    }

    // REQUIRES: the JTextFields for different patient details
    // EFFECTS: makes those fields editable
    private void makeEditable(JTextField nameField, JTextField ageField, JTextField sexField,
                              JTextField insuranceField,
                              JTextField phnField, JTextField diseaseNameField, JTextField diagnosisDateField) {
        nameField.setEditable(true);
        ageField.setEditable(true);
        sexField.setEditable(true);
        insuranceField.setEditable(true);
        phnField.setEditable(true);
        diagnosisDateField.setEditable(true);
        diseaseNameField.setEditable(true);
    }
}

