package fr.cours.bean;

import fr.cours.dao.AllianceFrenchDao;
import fr.cours.ressource.AllianceFrench;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AllianceFrenchBean {

    @Inject
    private AllianceFrenchDao allianceFrenchDao;

    public AllianceFrench getAllianceFrenchByID(String id) {
        return allianceFrenchDao.getAllianceFrenchByID(id);
    }

    public AllianceFrench getAllianceFrenchByNom(String nom) {
        return allianceFrenchDao.getAllianceFrenchByNom(nom);
    }

    public boolean addAllianceFrench(AllianceFrench allianceFrench) {
        return allianceFrenchDao.addAllianceFrench(allianceFrench);
    }

    public boolean deleteAllianceFrench(String nom) {
        return allianceFrenchDao.deleteAllianceFrench(nom);
    }

    public boolean updateAllianceFrench(AllianceFrench allianceFrench) {
        return allianceFrenchDao.updateAllianceFrench(allianceFrench);
    }

    public List getAllianceFrenchGroupByZonegeo() {
        return allianceFrenchDao.getAllianceFrenchGroupByZonegeo();
    }

    public List getAllianceFrenchGroupByPays() {
        return allianceFrenchDao.getAllianceFrenchGroupByPays();
    }
}