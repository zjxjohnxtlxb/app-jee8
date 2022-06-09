package fr.cours.dao;

import fr.cours.ressource.JWT;
import fr.cours.ressource.UserMe;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JWTDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    public JWT getCurrentUserToken(String id) {
        try {
            return entityManager.find(
                    JWT.class, Long.parseLong(id)
            );
        } catch (NoResultException nre) {
            return null;
        }
    }


    public boolean addToken(JWT jwt) {
        if (this.getCurrentUserToken(jwt.getUserMe().getId().toString()) != null) {
            return false;
        }
        try {
            userTransaction.begin();
            entityManager.persist(jwt);
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }

    public boolean deleteToken(UserMe userMe) {
        var jwt = this.getCurrentUserToken(userMe.getId().toString());
        if (jwt == null) {
            return false;
        }
        try {
            userTransaction.begin();
            entityManager.remove(entityManager.contains(jwt) ? jwt : entityManager.merge(jwt));
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }

    public boolean updateToken(JWT jwt) {
        var checkToken = this.getCurrentUserToken(jwt.getUserMe().getId().toString());
        if (checkToken == null) {
            return false;
        }
        try {
            userTransaction.begin();
            checkToken.setToken(jwt.getToken());
            entityManager.merge(checkToken);
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }
}