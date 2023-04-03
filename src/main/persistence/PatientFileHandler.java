package persistence;

import model.Disease;
import model.Event;
import model.EventLog;
import model.Patient;
import org.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the class to read and write patients onto patient file. The class provides methods for getting the file
 * path, writing the list of patients on to file and reading the list of patients on to file.
 */

public class PatientFileHandler {
    private String filePath;

    // EFFECTS: creates a PatientFileHandler object with the given file path
    public PatientFileHandler(String filePath) {
        this.filePath = filePath;
    }

    // EFFECTS: returns the path of the file as a string
    public String getFilePath() {
        return filePath;
    }

    // EFFECTS: reads the patients from file and return them in the proper list format
    public List<Patient> readPatientsFromFile() throws IOException {
        List<Patient> result = new ArrayList<>();
        String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONArray jsonArray = new JSONArray(jsonString);
        for (Object obj : jsonArray) {
            JSONObject json = (JSONObject) obj;
            String name = json.getString("Name: ");
            int age = json.getInt("Age");
            String sex = json.getString("Sex");
            String insurance = json.getString("Insurance");
            int phn = json.getInt("PHN");
            JSONArray jsonDiseases = json.getJSONArray("Diseases");
            List<Disease> diseases = toJsonDiseases(jsonDiseases);
            Patient patient = new Patient(name, age, sex, insurance, phn);
            patient.setDiseases(diseases);
            result.add(patient);
        }
        return result;
    }

    // REQUIRES: a non-null list of patients
    // MODIFIES: this
    // EFFECTS: EFFECTS:  writes the list of patients to the file storing the data
    public void writePatientsToFile(List<Patient> patients) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Patient patient : patients) {
            jsonArray.put(patient.toJson());
        }
        Files.write(Paths.get(filePath), jsonArray.toString().getBytes());
    }

    // REQUIRES: a JSONArray of diseases
    // EFFECTS: converts the JSONArray to List<Disease> and returns it
    private List<Disease> toJsonDiseases(JSONArray jsonDiseases) {
        List<Disease> result = new ArrayList<>();
        for (Object dis : jsonDiseases) {
            JSONObject jsonDis = (JSONObject) dis;
            String diseaseName = jsonDis.getString("Name");
            LocalDate diagnosedDate = LocalDate.parse(jsonDis.getString("Diagnosis Date"));
            Disease disease = new Disease(diseaseName, diagnosedDate);
            result.add(disease);
        }
        return result;
    }
}
