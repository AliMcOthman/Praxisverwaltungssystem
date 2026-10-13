package de.praxis.service;

import de.praxis.dao.BehandlungDao;
import de.praxis.model.Behandlung;

import java.sql.SQLException;

public class BehandlungService {
    BehandlungDao behandlungDao=new BehandlungDao();
    public void behandlungHinzufuegin(){}
    public void updateBehandlung(){}
    public Behandlung zeigenBehandlung(Behandlung behandlung){
        return behandlung;
    }
    public int getBehandlungen() throws SQLException{
        return behandlungDao.getAlleBehandlungen();
    }
}
