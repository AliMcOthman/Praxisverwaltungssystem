package de.praxis.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Patient {
    private int patient_id;
//
    private String name;
    private int patient_alter;
    private String geschlecht;
    private String abteilung;
    private String telefonnummer;
    private String email;


    public Patient(int patient_id, String name, int patient_alter, String geschlecht, String abteilung, String telefonnummer, String email) {
        this.patient_id = patient_id;
        this.name = name;
        this.patient_alter = patient_alter;
        this.geschlecht = geschlecht;
        this.abteilung = abteilung;
        this.telefonnummer = telefonnummer;
        this.email = email;
    }

    public Patient(int patient_id, String name) {
        this.patient_id = patient_id;
        this.name = name;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public String getName() {
        return name;
    }

    public int getPatient_alter() {
        return patient_alter;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public String getEmail() {
        return email;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatient_alter(int patient_alter) {
        this.patient_alter = patient_alter;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patient_id == patient.patient_id && patient_alter == patient.patient_alter && Objects.equals(name, patient.name) && Objects.equals(geschlecht, patient.geschlecht) && Objects.equals(abteilung, patient.abteilung) && Objects.equals(telefonnummer, patient.telefonnummer) && Objects.equals(email, patient.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient_id, name, patient_alter, geschlecht, abteilung, telefonnummer, email);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patient_id=" + patient_id +
                ", name='" + name + '\'' +
                ", patient_alter=" + patient_alter +
                ", geschlecht='" + geschlecht + '\'' +
                ", abteilung='" + abteilung + '\'' +
                ", telefonnummer='" + telefonnummer + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
