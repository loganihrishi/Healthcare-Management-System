package gui;

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
    private JButton cancelAppButton;
    private JButton loadDataButton;
    private JButton findUsingPhnButton;
    protected final int dimX = 600; // represents the width of the JFrame
    protected final int dimY = 800; // represents the height of the JFrame

    private final String patientPath = "./data/patients.json";
    private final String appointmentPath = "./data/appointments.json";
    protected List<Patient> patients = new ArrayList<>(); // stores the patients made
    protected AppointmentList appointments = new AppointmentList(); // stores all the appointments that have been
                                                                   // scheduled so far
    protected PatientFileHandler patientFile = new PatientFileHandler(patientPath);
    protected AppointmentFileHandler appointmentFile = new AppointmentFileHandler(appointmentPath);

    protected final String steveMeme = "data/steve_meme.jpg";

    // EFFECTS: starts the GUI of the application
    public StartApplicationGUI() throws IOException {
        initializeFrame();
    }

    // EFFECTS: initializes the frame
    public void initializeFrame() throws IOException {
        getExistingData();
        this.setTitle("Healthcare Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.WHITE);
        JPanel panel = addPanel();
        this.add(panel);
        this.setVisible(true);
        this.setSize(dimX, dimY);
        try {
            patients.addAll(patientFile.readPatientsFromFile());
            appointments.addAll(appointmentFile.readAppointmentsFromFile());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
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

    // MODIFIES: this
    // EFFECTS: adds the panel with appropriate dimensions
    private JPanel addPanel() throws IOException {
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
        res.add(getExistingData(), gbc);
        gbc.gridy++;
        res.add(addAppButton(), gbc);
        gbc.gridy++;
        res.add(cancelAppButton(), gbc);
        gbc.gridy++;
        res.add(phnButton(), gbc);
        return res;
    }

    // MODIFIES: this
    // EFFECTS: adds the patient button
    private JButton addPatientButton() throws IOException {
        this.addPatientButton = new JButton("Add a patient");
        Font font = new Font("Arial", Font.PLAIN, 22);
        addPatientButton.setFont(font);
        addPatientButton.setPreferredSize(new Dimension(500, 100));
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new PatientDetailsGUI();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return addPatientButton;
    }

    // MODIFIES: this
    // EFFECTS: returns the button to cancel the appointment
    private JButton cancelAppButton() {
        this.cancelAppButton = new JButton("Cancel an appointment");
        cancelAppButton.setFont(new Font("Arial", Font.PLAIN, 22));
        cancelAppButton.setPreferredSize(new Dimension(500, 100));
        cancelAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CancelAppGUI();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return cancelAppButton;
    }

    // MODIFIES: this
    // EFFECTS: adds the add appointment button
    private JButton addAppButton() {
        this.addAppButton = new JButton("Add an appointment");
        Font font = new Font("Arial", Font.PLAIN, 22);
        addAppButton.setFont(font);
        addAppButton.setPreferredSize(new Dimension(500, 100));
        addAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AppDetailsGUI();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return addAppButton;
    }

    // MODIFIES: this
    // EFFECTS: adds the find appointments using PHN button
    private JButton phnButton() {
        this.findUsingPhnButton = new JButton("Find Appointment Details Using PHN");
        Font font = new Font("Arial", Font.PLAIN, 22);
        findUsingPhnButton.setFont(font);
        findUsingPhnButton.setPreferredSize(new Dimension(500, 100));
        findUsingPhnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new FindUsingPhnGUI();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return findUsingPhnButton;
    }

    // MODIFIES: this
    // EFFECTS: loads the data from the JSON files
    private JButton getExistingData() {
        this.loadDataButton = new JButton("Load the data");
        Font font = new Font("Arial", Font.PLAIN, 22);
        loadDataButton.setFont(font);
        loadDataButton.setPreferredSize(new Dimension(500, 100));
        JFrame frame = new JFrame("Loading Data ...");
        loadDataButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Data Loaded successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE, new ImageIcon(steveMeme));
            }
        });
        return loadDataButton;
    }

    // EFFECTS: returns the appointments
    public AppointmentList getAppointments() {
        return appointments;
    }
}
