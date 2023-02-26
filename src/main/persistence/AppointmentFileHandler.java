package persistence;

import model.Appointment;
import model.Disease;
import model.Patient;
import org.json.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents the class to read and write List of appointments onto the appointment file. The class provides methods to
 * store the list of appointments in the file, read the list of appointments in the file and get the path of the file.
 */

public class AppointmentFileHandler {

    private String filePath;

    // EFFECTS: constructs an AppointmentFileHandler object with the given file path
    public AppointmentFileHandler(String filePath) {
        this.filePath = filePath;
    }

    // EFFECTS: reads the appointments from file and return them in the proper list format
    public List<Appointment> readAppointmentsFromFile() {
        List<Appointment> result = new ArrayList<>();
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(jsonString);

            for (Object obj : jsonArray) {
                JSONObject json = (JSONObject) obj;

                LocalDate date = LocalDate.parse(json.getString("Date"));
                LocalTime time = LocalTime.parse(json.getString("Time"));
                Patient patient = jsonToPatient(json.getJSONObject("Patient"));

                Appointment appointment = new Appointment(date, time, patient);
                result.add(appointment);
            }

        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return result;
    }

    // REQUIRES: a list of appointments to write onto the file
    // MODIFIES: this
    // EFFECTS:  writes the appointments to the file storing the data
    public void writeAppointmentsToFile(List<Appointment> appointments) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Appointment appointment : appointments) {
            JSONObject json = new JSONObject();
            json.put("Date", appointment.getDate().toString());
            json.put("Time", appointment.getTime().toString());
            json.put("Patient", appointment.getPatient().toJson());
            jsonArray.put(json);
        }
        Files.write(Paths.get(filePath), jsonArray.toString().getBytes());
    }

    // REQUIRES: a patient JSON Object
    // EFFECTS : returns the patient object from the json object
    private Patient jsonToPatient(JSONObject json) throws JSONException {
        String name = json.getString("Name: ");
        int age = json.getInt("Age");
        String sex = json.getString("Sex");
        String insurance = json.getString("Insurance");
        int phn = json.getInt("PHN");
        JSONArray diseasesJson = json.getJSONArray("Diseases");

        List<Disease> diseases = new ArrayList<>();
        for (Object obj : diseasesJson) {
            JSONObject diseaseJson = (JSONObject) obj;
            Disease disease = jsonToDisease(diseaseJson);
            diseases.add(disease);
        }

        Patient patient = new Patient(name, age, sex, insurance, phn);
        for (Disease disease : diseases) {
            patient.addDisease(disease.getName(), disease.getDiagnosedDate());
        }
        return patient;
    }

    // REQUIRES: a json object
    // EFFECTS : returns the disease object from the json object
    private static Disease jsonToDisease(JSONObject json) {
        String name = json.getString("Name");
        LocalDate diagnosedDate = LocalDate.parse(json.getString("Diagnosis Date"));
        return new Disease(name, diagnosedDate);
    }
}
