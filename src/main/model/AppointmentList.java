package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.WriteToFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of appointments that can be scheduled, rescheduled, and cancelled.
 * Appointments can be searched for and displayed by date or patient PHN.
 * It also has a toJson() method to convert the AppointmentList object to a JSON Object.
 * All the appointments have to be scheduled from 9:00 to 17:00 and each session is usually 15 minutes
 * So, at max we can only schedule 32 appointments for one day.
 */

public class AppointmentList implements WriteToFile {
    private ArrayList<Appointment> appointments;

    // EFFECTS: constructs a new object for appointments
    public AppointmentList() {
        this.appointments = new ArrayList<>();
    }

    // EFFECTS: returns the appointments that have been scheduled
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    // REQUIRES: a valid appointment
    // MODIFIES: this
    // EFFECTS: adds the appointment to the given list of appointments
    public void addAppointment(Appointment app) {
        this.appointments.add(app);
        EventLog.getInstance().logEvent(new Event("Appointment Added for PHN: "
                +
                app.getPatient().getPhn()));
    }

    // REQUIRES: an appointment which has already been scheduled
    // MODIFIES: this
    // EFFECTS: removes the appointment from the given list of appointments
    public void removeAppointment(Appointment app)  {
        this.appointments.remove(app);
        EventLog.getInstance().logEvent(new Event("Appointment Cancelled for PHN: "
                +
                app.getPatient().getPhn()));
    }

    // MODIFIES: this
    // EFFECTS: cancels all the currently scheduled appointment
    public void resetAppointment() {
        appointments.clear();
    }

    // REQUIRES: a valid appointment that has been scheduled already, a new date and time
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
                System.out.println("Appointment rescheduled successfully to " + date + " at " + time);
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

    public String displayGivenDate(LocalDate d) {
        String result = "";
        for (Appointment a : appointments) {
            if (a.getDate().equals(d)) {
                result += a.toString();
            }
        }
        if (result.equals("")) {
            return "No appointment found for the given date";
        } else {
            return result;
        }
    }

    // REQUIRES: a valid PHN
    // EFFECTS: returns the appointment details with the given PHN, if found
    //          otherwise returns "No appointment found with given PHN"
    public String findAppointmentToString(int phn) {
        if (findAppointment(phn) != null) {
            return findAppointment(phn).toString();
        }
        return "No appointment found with given PHN";
    }

    // REQUIRES: a valid PHN
    // EFFECTS: returns the appointment if found, null otherwise
    public Appointment findAppointment(int phn) {
        Appointment result = null;
        for (Appointment a: appointments) {
            if (a.getPatient().getPhn() == phn) {
                result = a;
                break;
            }
        }

        if (result != null) {
            EventLog.getInstance().logEvent(new Event("Retrieved Appointment Details For PHN: " + phn));
        } else {
            EventLog.getInstance().logEvent(new Event("Failed to retrieve appointment details for PHN: " + phn));
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS : adds all the given appointments to the current list of appointments if it does not have it already
    public void addAll(List<Appointment> appointments) {
        for (Appointment appointment:appointments) {
            boolean b = !(this.appointments.contains(appointment));
            if (b) {
                this.appointments.add(appointment);
            }
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Appointments", toJsonAppointment());
        return json;
    }

    // EFFECTS: returns the appointments as a JSONArray
    private JSONArray toJsonAppointment() {
        JSONArray jsonArray = new JSONArray();
        for (Appointment appointment: appointments)  {
            jsonArray.put(appointment.toJson());
        }
        return jsonArray;
    }
}
