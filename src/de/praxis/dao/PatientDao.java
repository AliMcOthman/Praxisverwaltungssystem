package de.praxis.dao;

import de.praxis.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import static jdk.internal.net.http.common.Utils.close;

public class PatientDao {

    public void savePatient(Patient patient) throws SQLException {
        String sql ="INSERT INTO patienten (name, patient_alter, geschlecht, abteilung, telefonnummer, email) VALUES(?,?,?,?,?,?)";

        Connection verbindung= DatenBankConnection.getConnention();
        PreparedStatement statement =verbindung.prepareStatement(sql);
//        statement.setInt(1,patient.getPatient_id());
        statement.setString(1,patient.getName());
        statement.setInt(2,patient.getPatient_alter());
        statement.setString(3,patient.getGeschlecht());
        statement.setString(4,patient.getAbteilung());
        statement.setString(5,patient.getTelefonnummer());
        statement.setString(6,patient.getEmail());
        statement.executeUpdate();

        statement.close();
        verbindung.close();

    }
    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patienten SET name = ?, patient_alter = ?, geschlecht = ?, abteilung = ?, telefonnummer = ? ,email = ? WHERE patient_id = ?";

        Connection verbindung= DatenBankConnection.getConnention();
        PreparedStatement statement =verbindung.prepareStatement(sql);
        statement.setString(1,patient.getName());
        statement.setInt(2,patient.getPatient_alter());
        statement.setString(3,patient.getGeschlecht());
        statement.setString(4,patient.getAbteilung());
        statement.setString(5,patient.getTelefonnummer());
        statement.setString(6,patient.getEmail());
        statement.setInt(7,patient.getPatient_id());
        statement.executeUpdate();

        statement.close();
        verbindung.close();

    }
    public void deletePatient(int patient_id) throws SQLException{
        String sql = "DELETE FROM patienten WHERE patient_id = ?";

        Connection verbindung= DatenBankConnection.getConnention();
        PreparedStatement statement =verbindung.prepareStatement(sql);
        statement.setInt(1,patient_id);
        statement.executeUpdate();

        statement.close();
        verbindung.close();


    }
    public List<Patient> getAllePatienten() throws SQLException{
        List<Patient> patients=new ArrayList<>();
        String sql ="SELECT * FROM patienten";

        Connection verbindung= DatenBankConnection.getConnention();
        PreparedStatement statement =verbindung.prepareStatement(sql);
        ResultSet resultSet= statement.executeQuery();
        while (resultSet.next()){
            Patient patient=new Patient(
                    resultSet.getInt("patient_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("patient_alter"),
                    resultSet.getString("geschlecht"),
                    resultSet.getString("abteilung"),
                    resultSet.getString("telefonnummer"),
                    resultSet.getString("email")
            );
            patients.add(patient);
        }
        resultSet.close();
        statement.close();
        verbindung.close();
        return patients;

    }
    public int getPatientenAnzahl() throws SQLException{
        String sql ="SELECT COUNT(*) FROM patienten";

        Connection verbindung= DatenBankConnection.getConnention();
        PreparedStatement statement =verbindung.prepareStatement(sql);
        ResultSet resultSet= statement.executeQuery();
        int anzahl=0;
        if(resultSet.next()){
            anzahl=resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        verbindung.close();
        return  anzahl;
    }


    public List<String> getAllNamen() throws SQLException {
        List<String> namen=new ArrayList<>();
        String sql ="SELECT name FROM patienten";

        Connection verbindung= DatenBankConnection.getConnention();
        PreparedStatement statement =verbindung.prepareStatement(sql);
        ResultSet resultSet= statement.executeQuery();
        while (resultSet.next()) {
            namen.add(resultSet.getString("name"));
        }

        resultSet.close();
        statement.close();
        verbindung.close();
        return namen;
    }
}
