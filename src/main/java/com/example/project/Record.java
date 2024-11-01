package com.example.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Record {
    private final IntegerProperty recordId;
    private final StringProperty diagnosis;
    private final StringProperty treatment;
    private final StringProperty dosage;
    private final StringProperty vaccinationStatus;
    private final IntegerProperty petId;
    private final IntegerProperty veterinarianId;

    public Record(int recordId, String diagnosis, String treatment, String dosage, String vaccinationStatus, int petId, int veterinarianId) {
        this.recordId = new SimpleIntegerProperty(recordId);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.treatment = new SimpleStringProperty(treatment);
        this.dosage = new SimpleStringProperty(dosage);
        this.vaccinationStatus = new SimpleStringProperty(vaccinationStatus);
        this.petId = new SimpleIntegerProperty(petId);
        this.veterinarianId = new SimpleIntegerProperty(veterinarianId);
    }

    public int getRecordId() {
        return recordId.get();
    }

    public String getDiagnosis() {
        return diagnosis.get();
    }

    public String getTreatment() {
        return treatment.get();
    }

    public String getDosage() {
        return dosage.get();
    }

    public String getVaccinationStatus() {
        return vaccinationStatus.get();
    }

    public int getPetId() {
        return petId.get();
    }

    public int getVeterinarianId() {
        return veterinarianId.get();
    }
}