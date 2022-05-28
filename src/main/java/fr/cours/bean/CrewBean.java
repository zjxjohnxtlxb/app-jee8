/*
 * @Date: 2022-05-19 12:04:43
 * @LastEditors: Junxi ZHANG
 * @LastEditTime: 2022-05-19 20:57:04
 * @FilePath: /app-jee8/src/main/java/fr/cours/jee/CrewBean.java
 */
package fr.cours.bean;

import fr.cours.dao.CrewMemberDao;
import fr.cours.ressource.CrewMember;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CrewBean {

    @Inject
    private CrewMemberDao crewMemberDao;

    public List<CrewMember> getCrewMembers() {
        return crewMemberDao.getCrewMembers();
    }

    public CrewMember getCrewMemberByName(String name) {
        return crewMemberDao.getCrewMemberByName(name);
    }

    public boolean addMember(CrewMember crewMember) {
        return crewMemberDao.addCrewMember(crewMember);
    }

    public boolean deleteMember(String name) {
        return crewMemberDao.deleteMember(name);
    }

    public boolean updateMember(CrewMember crewMember) {
        return crewMemberDao.updateMember(crewMember.getName(), crewMember.getJob());
    }
}