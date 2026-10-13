package de.praxis.service;

import de.praxis.dao.PatientDao;
import de.praxis.model.Patient;

import java.sql.SQLException;
import java.util.List;

public class PatientService {
    private PatientDao patientDao=new PatientDao();
    public void patientHinzufuegen(Patient patient) throws SQLException {
        if(patient.getName()== null || patient.getName().isEmpty()){
            throw new IllegalArgumentException("Name fehlt");
        }
        patientDao.savePatient(patient);

    }

    public void updatePatient(Patient patient) throws SQLException {
        patientDao.updatePatient(patient);
    }

    public void deletePatient(int patient_id) throws SQLException {
        patientDao.deletePatient(patient_id);
    }
//
//    public String suchenPatient(String name) {
//        return name;
//    }
    public List<Patient> getPatienten() throws SQLException {
        return patientDao.getAllePatienten();
    }
    public List<String> getPatientenNamen() throws SQLException {
        return patientDao.getAllNamen();
    }
    public int getAllPateintenAnzahl() throws SQLException {
        return patientDao.getPatientenAnzahl();
    }
}
