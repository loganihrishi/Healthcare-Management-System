package model;

import java.time.*;
import java.util.ArrayList;
// All the appointments have to be scheduled from 9:00 to 17:00 and
// each session is usually 15 minutes
// So, at max we can only schedule 32 appointments for one day


public class AppointmentList {
    private ArrayList<Appointment> appointments;
    // EFFECTS: constructs a new object for appointments
    public AppointmentList() {
        this.appointments = new ArrayList<>();
    }

    // EFFECTS: returns the appointments
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    // REQUIRES: a valid appointment
    // MODIFIES: this
    // EFFECTS: adds the appointment to the given list of appointments
    public void addAppointment(Appointment app) {
        this.appointments.add(app);
    }

    // REQUIRES: an appointment which has already been scheduled
    // MODIFIES: this
    // EFFECTS: removes the appointment from the given list of appointments
    public void removeAppointment(Appointment app)  {
        this.appointments.remove(app);
    }

    // MODIFIES: this
    // EFFECTS: cancels all the currently scheduled appointment
    public void resetAppointment() {
        appointments.clear();
    }

    // REQUIRES: a valid appointment that has been scheduled already
    // MODIFIES: this
    // EFFECTS: reschedules the given appointment to the given date, if possible
    //          otherwise do nothing

    public boolean rescheduleAppointment(Appointment a, LocalDate date, LocalTime time) {
        if (time.isAfter(LocalTime.of(9, 0)) && time.isBefore(LocalTime.of(17, 0))) {
            boolean dateTimeAvailable = true;
            for (Appointment app : appointments) {
                if (app.getDate().equals(date) && app.getTime().equals(time)) {
                    dateTimeAvailable = false;
                    break;
                }
            }
            if (dateTimeAvailable) {
                Appointment newAppointment = new Appointment(date, time, a.getPatient());
                removeAppointment(a);
                addAppointment(newAppointment);
                System.out.println("Appointment rescheduled successfully to "+ date + " at " + time);
                return true;
            } else {
                System.out.println("The selected date and time are not available, please choose a different one.");
                return false;
            }
        } else {
            System.out.println("Invalid time. Appointment can only be scheduled between 9:00 and 17:00.");
            return false;
        }
    }


    // REQUIRES: a valid date
    // EFFECTS: displays all the appointments scheduled on the give

    public void DisplayGivenDate(LocalDate d) {
        for (Appointment a : appointments) {
            if (a.getDate().equals(d)) {
                System.out.println(a.toString());
            }
        }
    }

    // REQUIRES: a valid PHN
    // EFFECTS: returns the appointment details with the given PHN, if found
    //          otherwise returns "No appointment found with given PHN"

    public String findAppointmentToString(int PHN) {
        if (findAppointment(PHN) != null) {
            return findAppointment(PHN).toString();
        }
        return "No appointment found with given PHN";
    }

    // REQUIRES: a valid PHN
    // EFFECTS: returns the appointment if found, null otherwise

    public Appointment findAppointment(int PHN) {
        for (Appointment a: appointments) {
            if (a.getPatient().getPHN() == PHN) {
                return a;
            }
        }
        return null;
    }
}
