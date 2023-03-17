package ui;//package ui;

import model.Appointment;
import model.AppointmentList;
import model.Patient;
import persistence.AppointmentFileHandler;
import persistence.PatientFileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** This class is used to start the GRAPHICAL user Interface of the project.
 * It implements four user stories, add patient, add appointment, save data and load the data.
 */

public class StartApplicationGUI extends JFrame {
    private JButton addAppButton;
    private JButton addPatientButton;
    private JButton saveDataButton;
    private JButton loadDataButton;
    private JButton findUsingPhnButton;
    protected final int dimX = 600; // represents the width of the JFrame
    protected final int dimY = 800; // represents the height of the JFrame

    private final String patientPath = "./data/patients.json";
    private final String appointmentPath = "./data/appointments.json";
    protected List<Patient> patients = new ArrayList<>(); // stores the patients made
    protected AppointmentList appointments = new AppointmentList(); // stores all the appointments that have been
                                                                   // scheduled so far
    PatientFileHandler patientFile = new PatientFileHandler(patientPath);
    AppointmentFileHandler appointmentFile = new AppointmentFileHandler(appointmentPath);

    // EFFECTS: starts the GUI of the application
    public StartApplicationGUI() {
        initializeFrame();
    }

    // EFFECTS: initializes the frame
    public void initializeFrame() {
        getExistingData();
        this.setTitle("Healthcare Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.WHITE);
        JPanel panel = addPanel();
        this.add(panel);
        this.setVisible(true);
        this.setSize(dimX, dimY);
    }

    // EFFECTS: adds the welcome label
    private JLabel addWelcomeLabel() {
        JLabel label = new JLabel("Welcome Back!");
        label.setForeground(Color.BLACK);
        label.setBounds(0, 50, 200, 50);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    // EFFECTS: adds the panel with appropriate dimensions
    private JPanel addPanel() {
        JPanel res = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        res.add(addWelcomeLabel(), gbc);
        gbc.gridy++;
        res.add(addPatientButton(), gbc);
        gbc.gridy++;
        res.add(addSaveData(), gbc);
        gbc.gridy++;
        res.add(addAppButton(), gbc);
        gbc.gridy++;
        res.add(phnButton(), gbc);
        return res;
    }

    // EFFECTS: adds the patient button
    private JButton addPatientButton() {
        this.addPatientButton = new JButton("Add a patient");
        Font font = new Font("Arial", Font.PLAIN, 22);
        addPatientButton.setFont(font);
        addPatientButton.setPreferredSize(new Dimension(500, 100));
        return addPatientButton;
    }

    // EFFECTS: adds the save data button
    private JButton addSaveData() {
        this.saveDataButton = new JButton("Save the data");
        Font font = new Font("Arial", Font.PLAIN, 22);
        saveDataButton.setFont(font);
        saveDataButton.setPreferredSize(new Dimension(500, 100));
        return saveDataButton;
    }

    // EFFECTS: adds the add appointment button
    private JButton addAppButton() {
        this.addAppButton = new JButton("Add an appointment");
        Font font = new Font("Arial", Font.PLAIN, 22);
        addAppButton.setFont(font);
        addAppButton.setPreferredSize(new Dimension(500, 100));
        return addAppButton;
    }

    // EFFECTS: adds the find appointments using PHN button
    private JButton phnButton() {
        this.findUsingPhnButton = new JButton("Find Appointment Details Using PHN");
        Font font = new Font("Arial", Font.PLAIN, 22);
        findUsingPhnButton.setFont(font);
        findUsingPhnButton.setPreferredSize(new Dimension(500, 100));
        findUsingPhnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FindUsingPhnGUI();
            }
        });
        return findUsingPhnButton;
    }

    // EFFECTS: loads the data from the existing JSON files
    private void getExistingData() {
        try {
            List<Patient> result1 = patientFile.readPatientsFromFile();
            List<Appointment> result2 = appointmentFile.readAppointmentsFromFile();

           // adding all the patients to the currently stored patients
            patients.addAll(result1);
           // adding all the appointments to the currently stored appointments
            appointments.addAll(result2);
        } catch (IOException e) {
            System.out.println("Error reading from the file: " + e.getMessage());
        }
    }

    public AppointmentList getAppointments() {
        return appointments;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public StartApplicationGUI getStartApplicationGUI() {
        return this;
    }

    public static void main(String[] args) {
        new StartApplicationGUI();
    }
}
