package fr.cours.dao;

import fr.cours.ressource.AllianceFrench;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AllianceFrenchDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    public AllianceFrench getAllianceFrenchByID(String id) {
        try {
            return entityManager.find(AllianceFrench.class, Long.parseLong(id));
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AllianceFrench getAllianceFrenchByColumn(String column, String value) {
        try {
            String nativeQuery = "SELECT a FROM AllianceFrench a WHERE a.%s = :aValue".formatted(column);
            return (AllianceFrench) entityManager.createQuery(nativeQuery).setParameter("aValue", value)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AllianceFrench getAllianceFrenchByNom(String value) {
        return getAllianceFrenchByColumn("nom", value);
    }

    public List getAllianceFrenchGroupByColumn(String column) {
        try {
            String nativeQuery = "SELECT a.%s, count(a) FROM AllianceFrench a group by a.%s".formatted(column, column);
            return entityManager.createQuery(nativeQuery).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List getAllianceFrenchGroupByZonegeo() {
        return this.getAllianceFrenchGroupByColumn("zonegeo");
    }

    public List getAllianceFrenchGroupByPays() {
        return this.getAllianceFrenchGroupByColumn("pays");
    }

    public boolean addAllianceFrench(AllianceFrench allianceFrench) {
        if (this.getAllianceFrenchByNom(allianceFrench.getNom()) != null) {
            return false;
        }
        try {
            userTransaction.begin();
            entityManager.persist(allianceFrench);
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }

    public boolean deleteAllianceFrench(String nom) {
        var allianceFrench = this.getAllianceFrenchByNom(nom);
        if (allianceFrench != null) {
            try {
                userTransaction.begin();
                entityManager.remove(entityManager.contains(allianceFrench) ? allianceFrench : entityManager.merge(allianceFrench));
                userTransaction.commit();
                return true;
            } catch (Exception e) {
                Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean updateAllianceFrench(AllianceFrench allianceFrench) {
        var checkAF = this.getAllianceFrenchByNom(allianceFrench.getNom());
        if (checkAF == null) {
            return false;
        }
        try {
            userTransaction.begin();
            for (Field field : AllianceFrench.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                var value = field.get(allianceFrench);
                if (value != null) {
                    field.set(checkAF, value);
                }
            }
            entityManager.merge(checkAF);
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }
}