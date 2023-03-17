package ui;

import model.Disease;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

// TODO: COMPLETE THIS SHIT
public class PatientDetailsGUI extends StartApplicationGUI {

    private JPanel mainPanel = new JPanel();
    private JButton mainMenu = new JButton("Click here to go back");

    public PatientDetailsGUI() {
        initialize();
    }

    private void initialize() {
        this.setTitle("Healthcare Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.WHITE);
        this.setSize(new Dimension(dimX, dimY));
       // this.add(getMainPanel());
        this.setVisible(true);
    }

//    private JPanel getMainPanel() {
//
//    }

    public void addPatient() {
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField sexField = new JTextField();
        JTextField insuranceField = new JTextField();
        JTextField phnField = new JTextField();
        Object[] fields = {
                "Enter Patient Name:", nameField,
                "Enter Patient's Age:", ageField,
                "Enter Patient's Sex (M/F):", sexField,
                "Enter Patient's Insurance Details:", insuranceField,
                "Enter Patient's Personal Health Number:", phnField
        };
        int option = JOptionPane.showConfirmDialog(null, fields, "Add Patient",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String sex = sexField.getText();
            String insurance = insuranceField.getText();
            int phn = Integer.parseInt(phnField.getText());
            Patient patient = new Patient(name, age, sex, insurance, phn);
            List<Disease> diseases = addDisease();
            patient.setDiseases(diseases);
            patients.add(patient);
            JOptionPane.showMessageDialog(null, "Patient " + name + " has been added");
        }
    }

    private List<Disease> addDisease() {
        List <Disease> diseases = new ArrayList<>();
        JTextField numField = new JTextField();
        Object[] fields = {
                "Enter the number of diseases:", numField
        };
        int option = JOptionPane.showConfirmDialog(null, fields, "Add Diseases",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            int num = Integer.parseInt(numField.getText());
            for (int i = 0; i < num; i++) {
                JTextField nameField = new JTextField();
                JTextField dateField = new JTextField();
                Object[] fields2 = {
                        "Enter the name of disease no. " + (i + 1) + ":", nameField,
                        "Enter the date when the disease was diagnosed (YYYY/MM/DD):", dateField
                };
                int option2 = JOptionPane.showConfirmDialog(null, fields2,
                        "Add Disease " + (i + 1), JOptionPane.OK_CANCEL_OPTION);
                if (option2 == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    String dateString = dateField.getText();
                    LocalDate date;
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        date = LocalDate.parse(dateString, formatter);
                    } catch (DateTimeParseException e) {
                        JOptionPane.showMessageDialog(null,
                                "Invalid date format. Please enter date in YYYY/MM/DD format.");
                        i--;
                        continue;
                    }
                    diseases.add((new Disease(name, date)));
                } else {
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "All the diseases have been stored");
        }

        return diseases;
    }


    // EFFECTS: returns the JButton for the main menu
    private JButton getMainMenu() {
        mainMenu.setFont(new Font("Arial", Font.PLAIN, 20));
        this.mainMenu.setPreferredSize(new Dimension(500, 100));
        this.mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartApplicationGUI();
            }
        });
        return mainMenu;
    }

}
