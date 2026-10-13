package de.praxis.dao;

import de.praxis.model.Behandlung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BehandlungDao {
    public void saveBehandlung(Behandlung behandlung){

    }
    public void update(Behandlung behandlung){

    }
    public void deleteBehandlung(int behandlung_id){

    }
    public int getAlleBehandlungen() throws SQLException{

        String sql ="SELECT COUNT(*) FROM bahandlung";

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
}
