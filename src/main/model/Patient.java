package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**The Patient class represents a patient with basic personal and medical information.
 * It has fields for name, age, sex, insurance details, personal health number (PHN), and a list of diseases.
 * It provides methods to get and set these fields and add diseases to the list.
 * The toString() method returns the patient's details in a formatted string, and the displayDiseases() method returns
 * a formatted string of all the diseases in the patient's list.
 */
public class Patient implements Writable {
    private String name;
    private int age;
    private List<Disease> diseases;
    private char sex;
    private String insurance;
    private int phn;

    // REQUIRES: a valid name, age, sex, insurance and PHN
    // EFFECTS: constructs the Patient object with the given details
    public Patient(String name, int age, char sex, String insurance, int phn) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.insurance = insurance;
        this.phn = phn;
        this.diseases = new ArrayList<>();
    }

    // EFFECTS: returns the name of the patient
    public String getName() {
        return name;
    }

    // EFFECTS: returns the age of the patient
    public int getAge() {
        return age;
    }

    // EFFECTS: returns the insurance details of the patient
    public String getInsurance() {
        return insurance;
    }

    // EFFECTS: returns the sex of the patient
    public char getSex() {
        return sex;
    }

    // EFFECTS: returns the PHN of the patient
    public int getPhn() {
        return phn;
    }

    // REQUIRES: a valid insurance
    // MODIFIES: this
    // EFFECTS:  sets the given insurance to the insurance of the patient
    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    // REQUIRES: a valid disease name and a valid date at which the disease was diagnosed
    // MODIFIES: this
    // EFFECTS:  adds the given disease to the list of diseases
    public void addDisease(String diseaseName, LocalDate diagnosisDate) {
        diseases.add(new Disease(diseaseName, diagnosisDate));
    }

    // REQUIRES: a valid list of diseases
    // MODIFIES: this
    // EFFECTS: sets the given disease to the diseases of the patient
    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    // EFFECTS: returns the diseases of the patient
    public List<Disease> getDiseases() {
        return diseases;
    }

    // EFFECTS: returns all the diseases in the proper string format
    public String displayDiseases() {
        String result = "";
        for (int i = 0; i < diseases.size(); i++) {
            result += (i + 1) + ". " + diseases.get(i).getName() + ", " + diseases.get(i).getDiagnosedDate() + "\n";
        }
        return result;
    }

    // EFFECTS: displays the details of the patient in the proper string format
    public String toString() {
        return "Patient: " + "\n"
                +
                "Name: " + name + "\n"
                +
                "Age: " + age + "\n"
                +
                "Sex: " + sex + "\n"
                +
                "Insurance: " + insurance + "\n"
                +
                "PHN: " + phn + "\n"
                +
                "Diseases: " + "\n"
                +
                displayDiseases();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name: ", this.name);
        json.put("Age", this.age);
        json.put("Sex", this.sex);
        json.put("Insurance", this.insurance);
        json.put("PHN", this.phn);
        json.put("Diseases", diseasesToJson());
        return json;
    }

    // EFFECTS: returns the diseases as a json array
    private JSONArray diseasesToJson() {
        JSONArray result = new JSONArray();
        for (Disease d: diseases) {
            result.put(d.toJson());
        }
        return result;
    }
}
