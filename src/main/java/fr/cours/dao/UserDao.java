package fr.cours.dao;

import fr.cours.ressource.User;
import fr.cours.utils.Helper;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {
    private final static Logger logger = Logger.getLogger(UserDao.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    public User getCurrentUserByID(String id) {
        try {
            return entityManager.find(User.class, Long.parseLong(id));
        } catch (NoResultException nre) {
            return null;
        }
    }

    public User getCurrentUserByEmail(String mail) {
        try {
            return (User) entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.email = :cEmail")
                    .setParameter("cEmail", mail)
                    .getSingleResult();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    public boolean addUser(User user) {
        if (this.getCurrentUserByEmail(user.getEmail()) != null) {
            return false;
        }
        try {
            userTransaction.begin();
            entityManager.persist(entityManager.contains(user) ? user : entityManager.merge(user));
            userTransaction.commit();
            return true;
        } catch (Exception e) {
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

    public boolean updatePasswordUser(User user) {
        var checkUser = this.getCurrentUserByEmail(user.getEmail());
        if (checkUser == null) {
            return false;
        }
        try {
            userTransaction.begin();
            checkUser.setPassword(user.getPassword());
            entityManager.merge(checkUser);
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "JPA error" + e.getMessage());
            return false;
        }
    }

    public boolean checkPasswordUser(User user) {
        var checkUser = this.getCurrentUserByEmail(user.getEmail());
        return checkUser != null &&
                checkUser.getPasswordHash().equals(Helper.hashUtil(user.getPassword()));
    }
}