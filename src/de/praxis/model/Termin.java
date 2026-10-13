package de.praxis.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Termin {
    private int termin_id;
    private String patientName;
    private  String arztName;
    private LocalDate datum;
    private LocalTime uhrzeit;

    public Termin(int termin_id, String patientName, String arztName, LocalDate datum, LocalTime uhrzeit) {
        this.termin_id = termin_id;
        this.patientName = patientName;
        this.arztName = arztName;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
    }

    public Termin(int termin_id, LocalDate datum, LocalTime uhrzeit) {
        this.termin_id = termin_id;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
    }

    public int getTermin_id() {
        return termin_id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getArztName() {
        return arztName;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public LocalTime getUhrzeit() {
        return uhrzeit;
    }

    public void setTermin_id(int termin_id) {
        this.termin_id = termin_id;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setArztName(String arztName) {
        this.arztName = arztName;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public void setUhrzeit(LocalTime uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Termin termin = (Termin) o;
        return termin_id == termin.termin_id && Objects.equals(patientName, termin.patientName) && Objects.equals(arztName, termin.arztName) && Objects.equals(datum, termin.datum) && Objects.equals(uhrzeit, termin.uhrzeit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(termin_id, patientName, arztName, datum, uhrzeit);
    }

    @Override
    public String toString() {
        return "Termin{" +
                "termin_id=" + termin_id +
                ", patientName='" + patientName + '\'' +
                ", arztName='" + arztName + '\'' +
                ", datum=" + datum +
                ", uhrzeit=" + uhrzeit +
                '}';
    }
}
