package fr.cours.dao;

import fr.cours.ressource.UserMe;
import fr.cours.utils.Helper;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.xml.registry.infomodel.User;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {
    private final static Logger logger = Logger.getLogger(UserDao.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    public UserMe getCurrentUserByID(String id) {
        try {
            return entityManager.find(UserMe.class, Long.parseLong(id));
        } catch (NoResultException nre) {
            return null;
        }
    }

    public UserMe getCurrentUserByEmail(String mail) {
        try {
            return (UserMe) entityManager.createQuery(
                            "SELECT u FROM UserMe u WHERE u.email = :cEmail")
                    .setParameter("cEmail", mail)
                    .getSingleResult();
        } catch (Exception e) {
            logger.info("email : " + e.getMessage());
            return null;
        }
    }

    public boolean addUser(UserMe userMe) {
        try {
            userTransaction.begin();
//            if (this.getCurrentUserByEmail(userMe.getEmail()) != null) {
//                return false;
//            }
            entityManager.persist(userMe);
            userTransaction.commit();
            logger.info("success");
            return true;
        } catch (Exception e) {
            logger.info("false :" + e.getMessage());
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String id) {
        var user = this.getCurrentUserByID(id);
        if (user != null) {
            try {
                userTransaction.begin();
                entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
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

    public boolean updatePasswordUser(UserMe userMe) {
        var checkUser = this.getCurrentUserByEmail(userMe.getEmail());
        if (checkUser == null) {
            return false;
        }
        try {
            userTransaction.begin();
            checkUser.setPassword(userMe.getPassword());
            entityManager.merge(checkUser);
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }

    public boolean checkPasswordUser(UserMe userMe) {
        var checkUser = this.getCurrentUserByEmail(userMe.getEmail());
        return checkUser != null &&
                checkUser.getPasswordHash().equals(Helper.hashUtil(userMe.getPassword()));
    }
}