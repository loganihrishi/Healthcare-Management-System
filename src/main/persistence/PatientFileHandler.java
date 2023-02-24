package persistence;

import model.Disease;
import model.Patient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents the class to read and write patients onto patient file. The class provides methods for getting the file
 * path, writing the list of patients on to file and reading the list of patients on to file.
 */

public class PatientFileHandler {
    private String filePath;

    public PatientFileHandler(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

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
            List<Disease> diseases = new ArrayList<>();
            for (Object dis : jsonDiseases) {
                JSONObject jsonDis = (JSONObject) dis;
                String diseaseName = jsonDis.getString("Name");
                LocalDate diagnosedDate = LocalDate.parse(jsonDis.getString("Diagnosis Date"));
                Disease disease = new Disease(diseaseName, diagnosedDate);
                diseases.add(disease);
            }
            Patient patient = new Patient(name, age, sex, insurance, phn);
            patient.setDiseases(diseases);
            result.add(patient);
        }
        return result;
    }

    public void writePatientsToFile(List<Patient> patients) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Patient patient : patients) {
            jsonArray.put(patient.toJson());
        }
        Files.write(Paths.get(filePath), jsonArray.toString().getBytes());
    }
}
