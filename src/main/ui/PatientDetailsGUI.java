package ui;

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
        nameField.setEditable(true);
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        ageField.setEditable(true);
        JLabel sexLabel = new JLabel("Sex (M/F):");
        JTextField sexField = new JTextField();
        sexField.setEditable(true);
        JLabel insuranceLabel = new JLabel("Insurance:");
        JTextField insuranceField = new JTextField();
        insuranceField.setEditable(true);
        JLabel phnLabel = new JLabel("Personal Health Number:");
        JTextField phnField = new JTextField();
        phnField.setEditable(true);
        // Fields for adding diseases
        JLabel diseaseNameLabel = new JLabel("Disease Name:");
        JTextField diseaseNameField = new JTextField();
        diseaseNameField.setEditable(true);
        JLabel diagnosisDateLabel = new JLabel("Diagnosis Date (yyyy-MM-dd):");
        JTextField diagnosisDateField = new JTextField();
        diagnosisDateField.setEditable(true);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {

            @SuppressWarnings("methodlength")
            public void actionPerformed(ActionEvent e) {
                // Get the values from the text fields
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

                // Show a message to the user
                JOptionPane.showMessageDialog(frame, "Patient added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/gregor.jpeg"));

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
                            JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/gregor.jpeg"));
                }
            }
        });
        makePanel(nameLabel, nameField, ageLabel, ageField, sexLabel, sexField, insuranceLabel, insuranceField,
                phnLabel,
                phnField, diseaseNameLabel, diseaseNameField, diagnosisDateLabel, diagnosisDateField, submitButton,
                frame);
    }

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
}

