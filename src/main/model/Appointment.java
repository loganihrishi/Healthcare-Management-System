package model;

import org.json.*;
import persistence.WriteToFile;

import java.time.*;

/**
 * Represents an appointment that has a Date, Time and Patient.
 * It contains methods to retrieve and modify date and time and patient information for an appointment .
 * The class also provides a method to determine if an appointment overlaps with another appointment.
 * The class implements the Writable interface and provides a toJson() method to convert the appointment
 * to a JSON object.
 */

public class Appointment implements WriteToFile {
    private LocalTime time;
    private LocalDate date;
    private Patient patient;

    // REQUIRES: valid date and time (between 9:00:00 and 17:00:00)
    // EFFECTS: construct appointment object using the given details
    public Appointment(LocalDate date, LocalTime time, Patient patient) {
        this.date = date;
        this.time = time;
        this.patient = patient;
    }


    // EFFECTS: return the date of the appointment
    public LocalDate getDate() {
        return date;
    }

    // EFFECTS: returns the time of the given appointment
    public LocalTime getTime() {
        return time;
    }

    // EFFECTS: returns tha patient object of the given appointment
    public Patient getPatient() {
        return patient;
    }

    // REQUIRES: a valid date
    // MODIFIES: this
    // EFFECTS: sets the date of the appointment
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // REQUIRES: a valid time
    // MODIFIES: this
    // EFFECTS: sets the time of the appointment
    public void setTime(LocalTime time) {
        this.time = time;
    }

    // EFFECTS: displays the appointment in a proper format
    public String toString() {
        return patient.toString() + " " + " Date:" + date + " Time:" + time;
    }

    // REQUIRES: a valid appointment
    // EFFECTS: returns true if the given appointment overlaps another appointment
    public boolean overlap(Appointment appointment) {
        return (date.equals(appointment.getDate()) && time.equals(appointment.getTime()));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Time", this.time);
        json.put("Date", this.date);
        json.put("Patient", this.patient.toJson());
        return json;
    }
}
