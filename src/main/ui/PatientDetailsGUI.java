package ui;

import model.Disease;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PatientDetailsGUI extends StartApplicationGUI {

    public PatientDetailsGUI() throws IOException {
        super();
        addPatient();
    }

    public void addPatient() {
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

            public void actionPerformed(ActionEvent e) {
                // Get the values from the text fields
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String sex = sexField.getText();
                String insurance = insuranceField.getText();
                int phn = Integer.parseInt(phnField.getText());
                String disName = diseaseNameField.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate diagDate = LocalDate.parse(diagnosisDateField.getText(), formatter);

                // Create a new patient object
                Patient patient = new Patient(name, age, sex, insurance, phn);
                patient.addDisease(disName, diagDate);

                // Add the patient to the list
                patients.add(patient);

                // Clear the text fields
                nameField.setText("");
                ageField.setText("");
                sexField.setText("");
                insuranceField.setText("");
                phnField.setText("");

                // Show a message to the user
                JOptionPane.showMessageDialog(frame, "Patient added successfully!");

                // Go back to the main menu
                try {
                    patientFile.writePatientsToFile(patients);
                    new StartApplicationGUI();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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

