package de.praxis.model;

import java.util.Objects;

public class Arzt {
    private int arzt_id;
    private String name;
    private String telefon;
    private String spezialisierung;

    public Arzt(int arzt_id, String name,String spezialisierung, String telefon) {
        this.arzt_id = arzt_id;
        this.name = name;
        this.telefon = telefon;
        this.spezialisierung = spezialisierung;
    }

    public int getArzt_id() {
        return arzt_id;
    }

    public String getName() {
        return name;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getSpezialisierung() {
        return spezialisierung;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setTelefonnummer(String telefonn) {
        this.telefon = telefonn;
    }

    public void setSpezialisierung(String spezialisierung) {
        this.spezialisierung = spezialisierung;
    }

    public Arzt(int arzt_id, String name) {
        this.arzt_id = arzt_id;
        this.name = name;
    }

    public Arzt() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Arzt arzt = (Arzt) o;
        return arzt_id == arzt.arzt_id && Objects.equals(name, arzt.name) && Objects.equals(telefon, arzt.telefon) && Objects.equals(spezialisierung, arzt.spezialisierung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arzt_id, name, telefon, spezialisierung);
    }

    @Override
    public String toString() {
        return "Arzt{" +
                "arzt_id=" + arzt_id +
                ", name='" + name + '\'' +
                ", telefon='" + telefon + '\'' +
                ", spezialisierung='" + spezialisierung + '\'' +
                '}';
    }
}
