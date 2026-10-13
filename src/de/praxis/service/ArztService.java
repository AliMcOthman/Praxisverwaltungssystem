package de.praxis.service;

import de.praxis.dao.ArztDao;
import de.praxis.model.Arzt;
import de.praxis.model.Patient;

import java.sql.SQLException;
import java.util.List;

public class ArztService {
    ArztDao arztDao=new ArztDao();
    public void arztHinzufuegen(Arzt arzt) throws SQLException {
        arztDao.saveArzt(arzt);
    }

    public void updatearzt(Arzt arzt) throws SQLException {
        arztDao.updateArzt(arzt);
    }

    public void deleteArzt(int arzt_id) throws SQLException {
        arztDao.deleteArzt(arzt_id);

    }
    public List<Arzt> getListAerzte() throws SQLException {
        return arztDao.getAlleAerzte();
    }

    public int getAerzte() throws SQLException {
        return  arztDao.getAlleAerzteAnzahl();

    }

    public List<String> getAerzteNamen() throws SQLException {
        return arztDao.getAllNamen();
    }
}
