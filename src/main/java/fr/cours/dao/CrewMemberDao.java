package fr.cours.dao;

import fr.cours.ressource.CrewMember;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrewMemberDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    public List<CrewMember> getCrewMembers() {
        return entityManager.createQuery("select cm from CrewMember cm", CrewMember.class).getResultList();
    }

    public CrewMember getCrewMemberByName(String name) {
        try {
            return (CrewMember) entityManager.createQuery(
                            "SELECT c FROM CrewMember c WHERE c.name = :cName")
                    .setParameter("cName", name)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public boolean addCrewMember(CrewMember crewMember) {
        if (getCrewMemberByName(crewMember.getName()) != null) {
            return false;
        }
        try {
            userTransaction.begin();
            entityManager.persist(crewMember);
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }

    public boolean deleteMember(String name) {
        var cm = getCrewMemberByName(name);
        if (cm == null) {
            return false;
        }
        try {
            userTransaction.begin();
            entityManager.remove(entityManager.contains(cm) ? cm : entityManager.merge(cm));
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }

    public boolean updateMember(String name, String job) {
        var cm = getCrewMemberByName(name);
        if (cm == null) {
            return false;
        }
        try {
            userTransaction.begin();
            cm.setJob(job);
            entityManager.merge(cm);
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }

}