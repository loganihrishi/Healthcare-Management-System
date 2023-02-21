package persistence;

import model.Appointment;
import model.AppointmentList;
import model.Patient;
import org.json.JSONObject;

import java.applet.AppletStub;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes the list of patients to the json file
    public void writePatients(List<Patient> patients) {
        for (Patient p: patients) {
            writePatient(p);
        }
    }

    // REQUIRES: a non-null patient
    // MODIFIES: this
    // EFFECTS: writes the patient to the file
    public void writePatient(Patient patient) {
        JSONObject json = patient.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes the list of appointments to the given file
    public void writeAppointmentList(AppointmentList appointmentList) {
        for (Appointment appointment: appointmentList.getAppointments()) {
            writeAppointment(appointment);
        }
    }

    // MODIFIES: this
    // EFFECTS: writes the appointment to file
    public void writeAppointment(Appointment appointment) {
        JSONObject json = appointment.toJson();
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
