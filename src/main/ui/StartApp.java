package ui;

import model.Appointment;
import model.AppointmentList;
import model.Disease;
import model.Patient;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


public class StartApp {
    int option;
    List<Patient> patients; // stores the patients made
    AppointmentList appointments; // stores all the appointments that have been scheduled so far

    public StartApp() throws ParseException {
        this.patients = new ArrayList<>();
        this.appointments = new AppointmentList();
        displayMenu();
    }

    // creating a scanner object
    Scanner input = new Scanner(System.in);

    // the function to start the UI of the application
    public void displayMenu() throws ParseException {
        System.out.println("1. Enter 1 to Add a patient.");
        System.out.println("2. Enter 2 to add an appointment.");
        System.out.println("3. Enter 3 to cancel an appointment.");
        System.out.println("4. Enter 4 to reschedule an existing appointment.");
        System.out.println("5. Enter 5 to display all scheduled appointments for a particular date.");
        System.out.println("6. Enter 6 to Quit the application.");
        option = input.nextInt();
        while (true) {
            if (option == 1) {
                addPatient();
            } else if (option == 2) {
                addAppointment();
            } else if (option == 3) {
                cancelAppointment();
            } else if (option == 4) {
                rescheduleAppointment();
            } else if (option == 5) {
                displayAll();
            } else {
                System.out.println("Exiting the application...");
                System.exit(69);
            }
        }
    }

    public void addPatient() throws ParseException {
        System.out.print("Enter Patient Name: ");
        String name = input.next();
        System.out.print("Enter Patient's Age: ");
        int age = input.nextInt();
        System.out.print("Enter Patient's Sex (M/F): ");
        // converting the string to char
        char sex = input.next().charAt(0);
        System.out.print("Enter Patient's Insurance Details: ");
        String insurance = input.next();
        System.out.print("Enter Patient's Personal Health Number: ");
        int phn = input.nextInt();
        Patient patient = new Patient(name, age, sex, insurance, phn);
        patients.add(patient);
        List<Disease> diseases = addDisease();
        patient.setDiseases(diseases);
        System.out.println();
        displayMenu();
    }

    public List<Disease> addDisease() throws ParseException {
        List<Disease> diseases = new ArrayList<>();
        System.out.print("Enter the number of diseases: ");
        int num = input.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.print("Enter the name of disease no. " + (i + 1) + " :");
            String name = input.next();
            System.out.print("Enter the date (YYYY/MM/DD) when the disease was diagnosed: ");
            String date = input.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate d = LocalDate.parse(date, formatter);
            diseases.add(new Disease(name, d));
        }
        System.out.println("All the diseases have been stored");
        return diseases;
    }

    public void addAppointment() throws ParseException {
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
            displayMenu();
        } else if (choice == 2) {
            addPatient();
        } else {
            System.out.println();
            displayMenu();
        }
    }

    public void cancelAppointment() throws ParseException {
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

    public void rescheduleAppointment() throws ParseException {
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

    // EFFECTS: displays all the appointments scheduled at a particular date
    public void displayAll() throws ParseException {
        System.out.print("Enter the date of the appointment (YYYY/MM/DD): ");
        String date = input.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate d = LocalDate.parse(date, formatter);
        appointments.displayGivenDate(d);
        System.out.println();
        displayMenu();
    }

    // this is a helper
    // returns true if any appointment overlaps with the appointment in already scheduled appointments
    public boolean overlaps(LocalDate date, LocalTime time) {
        for (Appointment a: appointments.getAppointments()) {
            if (a.getDate().equals(date) && a.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    // this is a helper
    // returns patient if found, null otherwise
    public Patient getPatient(int phn) {
        for (Patient p: patients) {
            if (p.getPhn() == phn) {
                return p;
            }
        }
        return null;
    }
}
