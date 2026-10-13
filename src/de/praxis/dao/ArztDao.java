package de.praxis.dao;

import de.praxis.model.Arzt;
import de.praxis.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArztDao {
    public void saveArzt(Arzt arzt) throws SQLException {
        String sql ="INSERT INTO arzt (name, Spezialisierung, telefon) VALUES(?,?,?)";

        Connection verbindung= DatenBankConnection.getConnention();
        PreparedStatement statement =verbindung.prepareStatement(sql);
//        statement.setInt(1,patient.getPatient_id());
        statement.setString(1,arzt.getName());
        statement.setString(2,arzt.getTelefon());
        statement.setString(3,arzt.getSpezialisierung());
        statement.executeUpdate();
        statement.close();
        verbindung.close();
    }
    public void updateArzt(Arzt arzt) throws SQLException {
        String sql = "UPDATE arzt SET name = ?, Spezialisierung = ?, telefon = ? WHERE arzt_id = ?";

        Connection verbindung= DatenBankConnection.getConnention();
        PreparedStatement statement =verbindung.prepareStatement(sql);
        statement.setString(1, arzt.getName());
        statement.setString(2,arzt.getSpezialisierung());
        statement.setString(3,arzt.getTelefon());
        statement.setInt(4,arzt.getArzt_id());
        statement.executeUpdate();
        statement.close();
        verbindung.close();
    }
    public void deleteArzt(int arzt_id) throws SQLException {
        String sql = "DELETE FROM arzt WHERE arzt_id = ?";

        Connection verbindung= DatenBankConnection.getConnention();
        PreparedStatement statement =verbindung.prepareStatement(sql);
        statement.setInt(1,arzt_id);
        statement.executeUpdate();

        statement.close();
        verbindung.close();

    }
    public int getAlleAerzteAnzahl() throws SQLException {
        String sql ="SELECT COUNT(*) FROM arzt";

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
    public List<Arzt> getAlleAerzte() throws SQLException{
        List<Arzt> arzts=new ArrayList<>();
        String sql ="SELECT * FROM arzt";

        Connection verbindung= DatenBankConnection.getConnention();
        PreparedStatement statement =verbindung.prepareStatement(sql);
        ResultSet resultSet= statement.executeQuery();
        while (resultSet.next()){
            Arzt arzt=new Arzt(
                    resultSet.getInt("arzt_id"),
                    resultSet.getString("name"),
                    resultSet.getString("Spezialisierung"),
                    resultSet.getString("telefon")

            );
            arzts.add(arzt);
        }
        resultSet.close();
        statement.close();
        verbindung.close();
        return arzts;

    }

    public List<String> getAllNamen() throws SQLException {
        List<String> namen=new ArrayList<>();
        String sql ="SELECT name FROM arzt";

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
