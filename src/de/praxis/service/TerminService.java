package de.praxis.service;

import de.praxis.dao.TerminDao;
import de.praxis.model.Termin;

import java.sql.SQLException;
import java.util.List;

public class TerminService {
    TerminDao terminDao=new TerminDao();
    public void terminErstellen(Termin termin){}
    public void updateTermin(){}
    public  void deleteTermin(){}
    public Termin suchenTermin(Termin termin){return termin; }
    public int getAlleTermine() throws SQLException{
        return terminDao.getAlleTermineAnzahl();
    }
    public List<Termin> getListTermine() throws SQLException {
        return terminDao.getListTermine();
    }

}
