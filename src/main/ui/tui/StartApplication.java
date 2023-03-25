package ui.tui;

import model.*;
import persistence.AppointmentFileHandler;
import persistence.PatientFileHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class represent the console based UI of the project. It has all the methods to interact with the user and
 * implements the USER stories in the README file.
 */


public class StartApplication {
    int option;
    List<Patient> patients; // stores the patients made
    AppointmentList appointments; // stores all the appointments that have been scheduled so far
    private final String patientPath = "./data/patients.json";
    private final String appointmentPath = "./data/appointments.json";

    PatientFileHandler patientFile = new PatientFileHandler(patientPath);
    AppointmentFileHandler appointmentFile = new AppointmentFileHandler(appointmentPath);


    // EFFECTS: creates an empty list of patients and appointments and starts the displayMenu()
    public StartApplication() throws ParseException, IOException {
        this.patients = new ArrayList<>();
        this.appointments = new AppointmentList();
        displayMenu();
    }

    // creating a scanner object
    Scanner input = new Scanner(System.in);

    // creating a BufferReader object
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // EFFECTS: starts the UI of the application
    public void displayMenu() throws ParseException, IOException {
        System.out.println("1. Enter 1 to Add a patient.");
        System.out.println("2. Enter 2 to add an appointment.");
        System.out.println("3. Enter 3 to cancel an appointment.");
        System.out.println("4. Enter 4 to reschedule an existing appointment.");
        System.out.println("5. Enter 5 to display all scheduled appointments for a particular date.");
        System.out.println("6. Enter 6 to find the appointment details using PHN. ");
        System.out.println("7. Enter 7 to save the data. ");
        System.out.println("8. Enter 8 to Load the data. ");
        System.out.println("9. Enter 9 to Quit the application.");
        System.out.println("Make sure that you load the data before running the application.");
        System.out.println("Otherwise you may lose all of your data.");
        option = input.nextInt();
        processCommand(option);
    }

    // REQUIRES: an option inputted by the user
    // EFFECTS: processes the command entered from the user
    @SuppressWarnings("methodlength")
    private void processCommand(int option) throws ParseException, IOException {
        while (true) {
            switch (option) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    addAppointment();
                    break;
                case 3:
                    cancelAppointment();
                    break;
                case 4:
                    rescheduleAppointment();
                    break;
                case 5:
                    displayAll();
                    break;
                case 6:
                    findWithPHN();
                    break;
                case 7:
                    saveData();
                    break;
                case 8:
                    getExistingData();
                    break;
                case 9:
                    exitApplication();
                default:
                    System.out.println("Invalid choice entered. \n");
                    displayMenu();
                    break;
            }
        }
    }

    // EFFECTS: exits the application
    private void exitApplication() {
        System.exit(69);
    }
    // REQUIRES: a valid Personal Health Number and other basic details
    // MODIFIES: this
    // EFFECTS: adds the patient to the given list of patients

    private void addPatient() throws ParseException, IOException {
        System.out.print("Enter Patient Name: ");
        String name = reader.readLine();
        System.out.print("Enter Patient's Age: ");
        int age = input.nextInt();
        System.out.print("Enter Patient's Sex (M/F): ");
        // converting the string to char
        String sex = input.next();
        System.out.print("Enter Patient's Insurance Details: ");
        String insurance = reader.readLine();
        System.out.print("Enter Patient's Personal Health Number: ");
        int phn = input.nextInt();
        Patient patient = new Patient(name, age, sex, insurance, phn);
        patients.add(patient);
        List<Disease> diseases = addDisease();
        patient.setDiseases(diseases);
        System.out.println();
        displayMenu();
    }
    // THIS IS CALLED AS A HELPER, CANNOT BE CALLED DIRECTLY BY THE USER
    // REQUIRES: a valid disease name and a valid date
    // MODIFIES: this
    // EFFECTS: sets the disease to the patient

    private List<Disease> addDisease() throws IOException {
        List<Disease> diseases = new ArrayList<>();
        System.out.print("Enter the number of diseases: ");
        int num = input.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.print("Enter the name of disease no. " + (i + 1) + " :");
            String name = reader.readLine();
            System.out.print("Enter the date (YYYY/MM/DD) when the disease was diagnosed: ");
            String date = input.next();
            input.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate d = LocalDate.parse(date, formatter);
            diseases.add(new Disease(name, d));
        }
        System.out.println("All the diseases have been stored");
        return diseases;
    }

    // MODIFIES: this
    // EFFECTS: adds the appointment to the given list of appointments, if possible
    //          otherwise displays an appropriate error message
    private void addAppointment() throws ParseException, IOException {
        System.out.print("Enter the date of the appointment (YYYY/MM/DD): ");
        String date = input.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate d = LocalDate.parse(date, formatter);
        System.out.print("Enter the time of the appointment (hh:mm): ");
        String time = input.next();
        LocalTime t = LocalTime.parse(time + ":00");

        System.out.println("Enter 1 for to schedule an appointment with an existing patient and 2 "
                +
                "to add a new patient.");
        int choice = input.nextInt();
        processCommandForAppointment(d, t, choice);
    }

    // REQUIRES: a valid LocalDate, LocalTime
    // MODIFIES: this
    // EFFECTS: adds the appointment if possible, otherwise generates appropriate error message
    private void processCommandForAppointment(LocalDate d, LocalTime t, int choice) throws ParseException, IOException {
        if (choice == 1) {
            System.out.print("Enter the Personal Health Number of the patient: ");
            int phn = input.nextInt();
            String result = appointments.findAppointmentToString(phn);
            if (result.equals("No appointment found with given PHN")
                    &&
                    !overlaps(d, t)
                    &&
                    (getPatient(phn) != null)) {
                Appointment a1 = new Appointment(d, t, getPatient(phn));
                appointments.addAppointment(a1);
                System.out.println("Appointment successfully scheduled");
            } else {
                System.out.println("Could not schedule the appointment either the slot is taken, "
                        +
                        "invalid PHN or patient already has an appointment");
            }
        } else if (choice == 2) {
            addPatient();
        }
        System.out.println();
        displayMenu();
    }

    // REQUIRES: a valid PHN
    // MODIFIES: this
    // EFFECTS: cancels the appointment if found, displays the appropriate message otherwise
    private void cancelAppointment() throws ParseException, IOException {
        System.out.print("Enter PHN of the Patient: ");
        // assuming that the appointment has not been cancelled
        boolean isCancelled = false;
        int phn = input.nextInt();
        for (Appointment appointment: appointments.getAppointments()) {
            if (appointment.getPatient().getPhn() == phn) {
                appointments.removeAppointment(appointment);
                isCancelled = true;
                break;
            }
        }
        if (isCancelled) {
            System.out.println("Cancelled the Appointment!");
        } else {
            System.out.println("Could not find the appointment!");
        }
        System.out.println();
        displayMenu();
    }

    // EFFECTS: saves the data if possible, displays the appropriate error message otherwise
    private void saveData() throws ParseException, IOException {
        System.out.println("Enter 1 to save Patients and 2 to Save appointments!");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                patientFile.writePatientsToFile(patients);
                System.out.println("Patients Successfully Saved");
                break;
            case 2:
                appointmentFile.writeAppointmentsToFile(appointments.getAppointments());
                System.out.println("Appointments stored successfully!");
                break;
            default:
                System.out.println("Invalid Choice");
                break;
        }
        displayMenu();
    }

    // MODIFIES: this
    // EFFECTS: Loads the data stored in the files
    private void getExistingData() throws ParseException, IOException {
        List<Patient> result1 = patientFile.readPatientsFromFile();
        List<Appointment> result2 = appointmentFile.readAppointmentsFromFile();

        // adding all the patients to the currently stored patients
        patients.addAll(result1);
        // adding all the appointments to the currently stored appointments
        appointments.addAll(result2);

        System.out.println("The data has been loaded successfully!" + "\n");
        displayMenu();
    }

    // EFFECTS: displays the patients
    private void displayPatients(List<Patient> patients) {
        for (Patient p: patients) {
            System.out.println(p.toString());
        }
    }

    // EFFECTS: displays the appointments in the given list
    private void displayAppointments(List<Appointment> appointments) {
        for (Appointment appointment:appointments) {
            System.out.println(appointment.toString());
        }
    }
    // REQUIRES: a valid PHN
    // MODIFIES: this
    // EFFECTS: reschedules the appointment to the date and time provided by the user if possible,
    //          displays the appropriate message otherwise

    private void rescheduleAppointment() throws ParseException, IOException {
        System.out.print("Enter the PHN of the patient whose appointment needs to be rescheduled: ");
        int phn = input.nextInt();
        Patient patient = getPatient(phn);
        // if the patient is null
        if (patient == null) {
            System.out.println("Patient does not exist! ");
            System.out.println();
            displayMenu();
        }

        // if the patient is not null, we find its appointment
        Appointment currentAppointment = appointments.findAppointment(phn);

        // if the appointment is null
        if (currentAppointment == null) {
            System.out.println("Appointment Not Found!");
        } else {
            System.out.print("Enter the date for the new appointment (YYYY/MM/DD): ");
            String newDate = input.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate d = LocalDate.parse(newDate, formatter);
            System.out.print("Enter the time of the new appointment (hh:mm): ");
            String time = input.next();
            LocalTime t = LocalTime.parse(time + ":00");
            appointments.rescheduleAppointment(currentAppointment,d,t);
        }
        System.out.println();
        displayMenu();
    }
    // REQUIRES: a valid date
    // EFFECTS: displays all the appointments scheduled at a particular date

    private void displayAll() throws ParseException, IOException {
        System.out.print("Enter the date of the appointment (YYYY/MM/DD): ");
        String date = input.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate d = LocalDate.parse(date, formatter);
        System.out.println(appointments.displayGivenDate(d));
        System.out.println();
        displayMenu();
    }

    // REQUIRES: a valid PHN
    // EFFECTS: prints the appointment details associated with a particular PHN
    private void findWithPHN() throws ParseException, IOException {
        System.out.print("Enter the PHN of the patient whose appointment details are needed: ");
        int phn = input.nextInt();
        Appointment appointment = appointments.findAppointment(phn);
        if (appointment == null) {
            System.out.println("No Appointment found");
        } else {
            System.out.println(appointment.toString());
        }
        displayMenu();
    }

    // REQUIRES: a valid date and time
    // EFFECTS: returns true if any appointment overlaps with the appointment in already scheduled appointments
    private boolean overlaps(LocalDate date, LocalTime time) {
        for (Appointment a: appointments.getAppointments()) {
            if (a.getDate().equals(date) && a.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: a valid PHN
    // EFFECTS: returns the patient object if found, null otherwise
    private Patient getPatient(int phn) {
        for (Patient p: patients) {
            if (p.getPhn() == phn) {
                return p;
            }
        }
        return null;
    }
}
