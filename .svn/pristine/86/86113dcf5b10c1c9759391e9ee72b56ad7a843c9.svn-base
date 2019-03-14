/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service.imp;

import com.panu.competitor.information.dao.CompetitorUserDAO;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.CompetitorUser;
import com.panu.competitor.information.spring.service.ICompetitorUserService;
import com.panu.competitor.information.spring.service.constant.NamedQueryConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author HNDK
 */
@Named
public class CompetitorUserServiceImp implements ICompetitorUserService {

    @Autowired
    private CompetitorUserDAO userDAO;

    @Override
    public List<CompetitorUser> findAllUser() {
        return userDAO.select(NamedQueryConstant.GET_USERS);
    }

    @Override
    public void addUser(CompetitorUser user) throws CompetitorException {
        userDAO.insert(user);
    }

    @Override
    public void updateUser(CompetitorUser updateUser) throws CompetitorException {
        userDAO.update(updateUser);
    }

    @Override
    public boolean validateUserInfo(CompetitorUser user, List<CompetitorUser> userList) {
        boolean returnValue = false;
        for (Iterator<CompetitorUser> it = userList.iterator(); it.hasNext();) {
            CompetitorUser userList1 = it.next();
            if (userList1.getUserName().trim().equalsIgnoreCase(user.getUserName().trim())) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    @Override
    public CompetitorUser findUserByUserId(int userId) {
        String constantQuery = NamedQueryConstant.FIND_BY_USERID;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.USERID);
        List<Integer> paramValueList = new ArrayList<Integer>();
        paramValueList.add(userId);
        List<CompetitorUser> userList = userDAO.selectByIntType(constantQuery, paramColumnList,
                paramValueList);
        CompetitorUser user = new CompetitorUser();
        if (userList != null && !userList.isEmpty() && userList.get(0) != null) {
            user = userList.get(0);
        }
        return user;
    }

    @Override
    public CompetitorUser findUserByUserRole(int role) {
        String constantQuery = NamedQueryConstant.FIND_BY_USERROLE;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.USERROLE);
        List<Integer> paramValueList = new ArrayList<Integer>();
        paramValueList.add(role);
        List<CompetitorUser> userList = userDAO.selectByIntType(constantQuery, paramColumnList,
                paramValueList);
        CompetitorUser user = new CompetitorUser();
        if (userList != null && !userList.isEmpty() && userList.get(0) != null) {
            user = userList.get(0);
        }
        return user;
    }

}
