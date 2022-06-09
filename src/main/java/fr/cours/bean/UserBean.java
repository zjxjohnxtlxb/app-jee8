package fr.cours.bean;

import fr.cours.dao.UserDao;
import fr.cours.ressource.UserMe;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserBean {

    @Inject
    private UserDao userDao;

    public UserMe getCurrentUserByID(String id) {
        return userDao.getCurrentUserByID(id);
    }

    public UserMe getCurrentUserByEmail(String mail) {
        return userDao.getCurrentUserByEmail(mail);
    }

    public boolean addUser(UserMe userMe) {
        return userDao.addUser(userMe);
    }

    public boolean deleteUser(String id) {
        return userDao.deleteUser(id);
    }

    public boolean updatePasswordUser(UserMe userMe) {
        return userDao.updatePasswordUser(userMe);
    }

    public boolean checkPasswordUser(UserMe userMe) {
        return userDao.checkPasswordUser(userMe);
    }
}