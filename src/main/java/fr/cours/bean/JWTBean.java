package fr.cours.bean;

import fr.cours.dao.JWTDao;
import fr.cours.ressource.JWT;
import fr.cours.ressource.User;
import fr.cours.utils.Helper;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class JWTBean {

    @Inject
    private JWTDao jwtDao;

    public JWT getCurrentUserToken(String id) {
        return jwtDao.getCurrentUserToken(id);
    }

    public boolean addToken(JWT jwt) {
        return jwtDao.addToken(jwt);
    }

    public boolean deleteToken(User user) {
        return jwtDao.deleteToken(user);
    }

    public boolean updateToken(JWT jwt) {
        return jwtDao.updateToken(jwt);
    }

    public boolean checkToken(User user) {
        var checkToken = this.getCurrentUserToken(user.getId().toString());
        if (checkToken == null ||
                !checkToken.getToken().equals(Helper.hashUtil(user.getPassword()))
        ) {
            return false;
        } else {
            return true;
        }
    }
}