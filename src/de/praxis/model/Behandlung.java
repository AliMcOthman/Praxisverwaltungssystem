package de.praxis.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Behandlung {
    private int behandlung_id;
    private LocalDate datum;
    private String patient_name;
    private String arzt_name;
    private String beschreibung;


    public Behandlung(int behandlung_id, String beschreibung, LocalDate datum, String name, String arzt_name) {
        this.behandlung_id = behandlung_id;
        this.beschreibung = beschreibung;
        this.datum = datum;
        this.patient_name = patient_name;
        this.arzt_name = arzt_name;
    }

    public Behandlung(int behandlung_id, String beschreibung, Patient patient) {
        this.behandlung_id = behandlung_id;
        this.beschreibung = beschreibung;
        this.patient_name = patient_name;
    }

    public int getBehandlung_id() {
        return behandlung_id;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getPatient() {
        return patient_name;
    }

    public String getArzt() {
        return arzt_name;
    }

    public void setBehandlung_id(int behandlung_id) {
        this.behandlung_id = behandlung_id;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public void setPatient(String patien_name) {
        this.patient_name = patient_name;
    }

    public void setArzt(String arzt_name) {
        this.arzt_name = arzt_name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Behandlung that = (Behandlung) o;
        return behandlung_id == that.behandlung_id && Objects.equals(beschreibung, that.beschreibung) && Objects.equals(datum, that.datum) && Objects.equals(patient_name, that.patient_name) && Objects.equals(arzt_name, that.arzt_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(behandlung_id, beschreibung, datum, patient_name, arzt_name);
    }

    @Override
    public String toString() {
        return "Behandlung{" +
                "behandlung_id=" + behandlung_id +
                ", beschreibung='" + beschreibung + '\'' +
                ", datum=" + datum +
                ", patient=" + patient_name +
                ", arzt=" + arzt_name +
                '}';
    }
}

