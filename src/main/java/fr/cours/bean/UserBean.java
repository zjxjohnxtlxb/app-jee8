package fr.cours.bean;

import fr.cours.dao.UserDao;
import fr.cours.ressource.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserBean {

    @Inject
    private UserDao userDao;

    public User getCurrentUserByID(String id) {
        return userDao.getCurrentUserByID(id);
    }

    public User getCurrentUserByEmail(String mail) {
        return userDao.getCurrentUserByEmail(mail);
    }

    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    public boolean deleteUser(String id) {
        return userDao.deleteUser(id);
    }

    public boolean updatePasswordUser(User user) {
        return userDao.updatePasswordUser(user);
    }

    public boolean checkPasswordUser(User user) {
        return userDao.checkPasswordUser(user);
    }
}