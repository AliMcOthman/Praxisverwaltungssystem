package de.praxis.dao;
import java.sql.Timestamp;
import de.praxis.model.Arzt;
import de.praxis.model.Termin;
import de.praxis.service.TerminService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TerminDao {
//    public void saveTermin(Termin termin){
//
//    }
//    public void updateTermin(Termin termin){
//
//    }
//    public void deleteTermin(int termin_id){
//
//    }
    public int getAlleTermineAnzahl() throws SQLException{
        String sql ="SELECT COUNT(*) FROM termine";

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
    public List<Termin> getListTermine() throws SQLException {

        List<Termin> termins = new ArrayList<>();

        String sql = "SELECT * FROM termine";

        Connection verbindung = DatenBankConnection.getConnention();
        PreparedStatement statement = verbindung.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            Timestamp timestamp =
                    resultSet.getTimestamp("termin_datetime");

            Termin termin = new Termin(
                    resultSet.getInt("termin_id"),
                    resultSet.getString("patient_name"),
                    resultSet.getString("arzt_name"),
                    timestamp.toLocalDateTime().toLocalDate(),
                    timestamp.toLocalDateTime().toLocalTime()
            );

            termins.add(termin);
        }

        resultSet.close();
        statement.close();
        verbindung.close();

        return termins;
    }

}
