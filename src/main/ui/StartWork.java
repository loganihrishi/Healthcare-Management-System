package ui;
import model.Appointment;
import model.AppointmentList;
import model.Disease;
import model.Patient;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


public class StartWork {
    int option = 0;
    List<Patient> patients; // stores the patients made
    AppointmentList appointments; // stores all the appointments that have been scheduled so far

    public StartWork() throws ParseException {
        displayMenu();
        this.patients = new ArrayList<>();
        this.appointments = new AppointmentList();
    }

    Scanner sc = new Scanner(System.in);

    public void displayMenu() throws ParseException {
        System.out.println("1. Enter 1 to Add a patient.");
        System.out.println("2. Enter 2 to add an appointment");
        System.out.println("3. Enter 3 to cancel an appointment.");
        System.out.println("4. Enter 4 to reschedule an existing appointment.");
        System.out.println("5. Enter 5 to display all scheduled appointments for a particular date.");
        System.out.println("6. Enter 6 to Quit the application.");
        option = sc.nextInt();
        sc.nextLine();
        if (option == 1) {
            addPatient();
        } else if (option == 2) {
            addAppointment();
        } else if (option == 3)
            cancelAppointment();
        else if (option == 4)
            rescheduleAppointment();
        else if (option == 5) {
            displayAll();
        }
        else {
            System.out.println("Exiting the application...");
            System.out.println("Exited");
        }
    }

    public void addPatient() throws ParseException {
        System.out.print("Enter Patient Name: ");
        String name = sc.next();
        System.out.print("Enter Patient's Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Patient's Sex (M/F): ");
        char sex = sc.next().charAt(0);
        System.out.print("Enter Patient's Insurance Details: ");
        String insurance = sc.next();
        System.out.print("Enter Patient's Personal Health Number: ");
        int PHN = sc.nextInt();
        Patient patient = new Patient(name, age, sex, insurance, PHN);
        patients.add(patient);
        List<Disease> diseases = addDisease();
        for (Disease d : diseases) {
            patient.addDisease(d.getName(), d.getDiagnosedDate());
        }
        displayMenu();
    }

    public List<Disease> addDisease() throws ParseException {
        List<Disease> diseases = new ArrayList<>();
        System.out.print("Enter the number of diseases: ");
        int num = sc.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.print("Enter the name of disease no. " + (i + 1) + " :");
            String name = sc.next();
            System.out.print("Enter the date of the date (YYYY/MM/DD) when the disease was diagnosed: ");
            String date = sc.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate d = LocalDate.parse(date, formatter);
            diseases.add(new Disease(name, d));
            System.out.println("Disease successfully added!");
        }
        System.out.println("All the diseases have been stored");
        return diseases;
    }

    public void addAppointment() throws ParseException {
        System.out.print("Enter the date of the appointment (YYYY/MM/DD): ");
        String date = sc.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate d = LocalDate.parse(date, formatter);
        System.out.print("Enter the time of the appointment (hh:mm): ");
        String time = sc.next();
        LocalTime t = LocalTime.parse(time + ":00");

        System.out.println("Enter 1 for to schedule an appointment with an existing patient and 2 " +
                "to add a new patient.");
        int choice = sc.nextInt();
        if (choice == 1) {
            System.out.print("Enter the Personal Health Number of the patient: ");
            int PHN = sc.nextInt();
            String result = appointments.findAppointmentWithPHN(PHN);
            if (result.equals("No appointment found with given PHN") &&
                    !overlaps(d, t) &&
                    (getPatient(PHN) != null)) {
                appointments.addAppointment(new Appointment(d, t, getPatient(PHN)));
                System.out.println("Appointment successfully scheduled");
            }
            else {
                System.out.println("Could not schedule the appointment either the slot is taken, " +
                        "invalid PHN or patient already has an appointment");
            }
            displayMenu();
        }

        else if (choice == 2) {
            addPatient();
        }
        else
            displayMenu();
    }


    public void cancelAppointment() throws ParseException {
        System.out.print("Enter PHN of the Patient: ");
        int PHN = sc.nextInt();
        for (Appointment appointment: appointments.getAppointments()) {
            if (appointment.getPatient().getPHN() == PHN) {
                appointments.removeAppointment(appointment);
                System.out.println("Cancelled the Appointment");
            }
        }
        System.out.println("Could not find any appointment associated with it");
        displayMenu();
    }


    public void rescheduleAppointment() throws ParseException {
        System.out.print("Enter the date of the current appointment (YYYY/MM/DD): ");
        String date = sc.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate d = LocalDate.parse(date, formatter);
        System.out.print("Enter the time of the current appointment (hh:mm): ");
        String time = sc.next();
        LocalTime t = LocalTime.parse(time + ":00");
        System.out.print("Enter the PHN of the patient: ");
        int PHN = sc.nextInt();
        if (getPatient(PHN) == null) {
            System.out.println("Invalid PHN!");
            displayMenu();
        }
        else {
            System.out.print("Enter the date of the NEW appointment (YYYY/MM/DD): ");
            String date1 = sc.next();
            LocalDate d1 = LocalDate.parse(date, formatter);
            System.out.print("Enter the time of the NEW appointment (hh:mm): ");
            String time1 = sc.next();
            LocalTime t1 = LocalTime.parse(time + ":00");
            Appointment app = new Appointment(d, t, getPatient(PHN));
            appointments.rescheduleAppointment(app, d1, t1);
            displayMenu();
        }
    }

    public void displayAll() throws ParseException {
        System.out.print("Enter the date of the current appointment (YYYY/MM/DD): ");
        String date = sc.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate d = LocalDate.parse(date, formatter);
        appointments.DisplayGivenDate(d);
        displayMenu();
    }


    // this is a helper
    // returns true if any appointment overlaps with the appointment in already scheduled appointments
    public boolean overlaps(LocalDate date, LocalTime time) {
        for (Appointment a: appointments.getAppointments()) {
            if (a.getDate().equals(date) && a.getTime().equals(time))
                return true;
        }
        return false;
    }

    // this is a helper
    // returns patient if found, null otherwise
    public Patient getPatient(int PHN) {
        for (Patient p: patients) {
            if (p.getPHN() == PHN)
                return p;
        }
        return null;
    }

}


/*
* int currPHN = sc.nextInt();
            // checks if the patient exists in the list of patients
            for (Patient p: patients) {
                if (p.getPHN() == currPHN) {
                    name = p.getName();
                    age = p.getAge();
                    sex = p.getSex();
                    diseases = p.getDiseases();
                    insurance = p.getInsurance();
                    PHN = p.getPHN();
                    break;
                }
                System.out.println("Invalid Personal Health Number!");
            }
            // check if the current appointment overlaps any existing appointments
            boolean b =false;
            for (Appointment appointment: appointments.getAppointments()) {
                if (appointment.overlap(appointment)) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                appointments.addAppointment(new Appointment(d ,t, new Patient(name, age, sex, insurance, PHN)));
            }
        }

*
*
*
*
* */