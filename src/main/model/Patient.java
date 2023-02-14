package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient {
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
}
