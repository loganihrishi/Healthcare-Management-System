//package ui;
//
//import model.Appointment;
//import model.AppointmentList;
//import model.Patient;
//import persistence.AppointmentFileHandler;
//import persistence.PatientFileHandler;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.List;
///**
// * This class represents the LoadData part of the GUI of this project.
// */
//
//public class LoadDataGUI extends JFrame {
//    protected final int dimX = 600;
//    protected final int dimY = 800;
//    private final String patientPath = "./data/patients.json";
//    private final String appointmentPath = "./data/appointments.json";
//    List<Patient> patients = new ArrayList<>(); // stores the patients made
//    AppointmentList appointments = new AppointmentList(); // stores all the appointments that have been scheduled so far
//    PatientFileHandler patientFile = new PatientFileHandler(patientPath);
//    AppointmentFileHandler appointmentFile = new AppointmentFileHandler(appointmentPath);
//    private JButton mainButton;
//
//    // EFFECTS: starts the another part of the main gui
//    public LoadDataGUI() {
//        initialize();
//    }
//
//    // EFFECTS: starts the loading gui
//    private void initialize() {
//        this.setTitle("Loading window");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setResizable(true);
//        this.getContentPane().setBackground(Color.WHITE);
//        this.setSize(new Dimension(dimX, dimY));
//        JPanel result = makePanel();
//        this.getContentPane().add(result);
//        this.setVisible(true);
//    }
//
//    // EFFECTS: makes the panel for the window
//    private JPanel makePanel() {
//        JPanel panel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.weighty = 1.0;
//        gbc.anchor = GridBagConstraints.CENTER;
//        panel.add(getExistingData(), gbc);
//        gbc.gridy++;
//        panel.add(getMain(), gbc);
//        return panel;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Loads the data stored in the files
//    private JLabel getExistingData() {
//        try {
//            List<Patient> result1 = patientFile.readPatientsFromFile();
//            List<Appointment> result2 = appointmentFile.readAppointmentsFromFile();
//            // adding all the patients to the currently stored patients
//            patients.addAll(result1);
//            // adding all the appointments to the currently stored appointments
//            appointments.addAll(result2);
//            JLabel returnLabel = new JLabel("Data has been loaded successfully");
//            returnLabel.setForeground(Color.BLACK);
//            returnLabel.setBounds(0, 50, 200, 50);
//            returnLabel.setFont(new Font("Arial", Font.BOLD, 28));
//            returnLabel.setVerticalAlignment(JLabel.TOP);
//            returnLabel.setHorizontalAlignment(JLabel.CENTER);
//            return returnLabel;
//        }
//        catch (IOException e){
//            JLabel returnLabel = new JLabel("Error reading from file");
//            returnLabel.setForeground(Color.BLACK);
//            returnLabel.setBounds(0, 50, 200, 50);
//            returnLabel.setFont(new Font("Arial", Font.BOLD, 28));
//            returnLabel.setVerticalAlignment(JLabel.TOP);
//            returnLabel.setHorizontalAlignment(JLabel.CENTER);
//            return returnLabel;
//        }
//    }
//
//    // EFFECTS: returns the button to get back to the main window
//    private JButton getMain() {
//        this.mainButton = new JButton("Click here to go back");
//        Font font = new Font("Arial", Font.PLAIN, 20);
//        mainButton.setFont(font);
//        mainButton.setPreferredSize(new Dimension(500, 100));
//        mainButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new StartApplicationGUI();
//            }
//        });
//        return mainButton;
//    }
//
//    public AppointmentList getAppointments() {
//        return appointments;
//    }
//
//    public List<Patient> getPatients() {
//        return patients;
//    }
//}
