/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service.imp;

import com.panu.competitor.information.dao.CompetitorUserDAO;
import com.panu.competitor.information.dao.RoleDAO;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.CompetitorUser;
import com.panu.competitor.information.pojo.entity.LogInUser;
import com.panu.competitor.information.pojo.entity.Role;
import com.panu.competitor.information.spring.service.constant.NamedQueryConstant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.BadCredentialsException;

/**
 *
 * @author HNDK
 */
@Named(value = "logInUserService")
@Transactional(readOnly = true)
public class LogInUserServiceImp implements UserDetailsService {

    @Autowired
    CompetitorUserDAO competitorUserDAO;
    @Autowired
    RoleDAO roleDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add("userName");
        List<String> paramValueList = new ArrayList<String>();
        paramValueList.add(userName);

        List<CompetitorUser> eUser = competitorUserDAO.selectByStringType(NamedQueryConstant.FIND_BY_USERNAME, paramColumnList, paramValueList);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (eUser != null && eUser.size() > 0) {
            try {
                authorities = buildUserAuthority(userName);
            } catch (CompetitorException ex) {
                Logger.getLogger(LogInUserServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new BadCredentialsException("User Name or Password is incorrect!");
        }
        return buildUserForAuthentication(eUser.get(0), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(String userName) throws CompetitorException {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add("userName");
        List<String> paramValueList = new ArrayList<String>();
        paramValueList.add(userName);
        List<CompetitorUser> userList = competitorUserDAO.selectByStringType(NamedQueryConstant.FIND_BY_USERNAME, paramColumnList, paramValueList);

        // Build user's authorities
        for (CompetitorUser user : userList) {
            findRoleNameByRoleId(user.getRoleId());
            setAuths.add(new SimpleGrantedAuthority(findRoleNameByRoleId(user.getRoleId())));
        }
        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
        return Result;
    }

    private LogInUser buildUserForAuthentication(CompetitorUser eUser, List<GrantedAuthority> authorities) {
        return new LogInUser(eUser.getUserName(), eUser.getPassword(), authorities, eUser);
    }

    private String findRoleNameByRoleId(int roleId) throws CompetitorException {
        String roleName = "";
        Role roleBean = roleDao.select(Role.class, roleId);
        roleName = roleBean.getRoleName();
        return roleName;
    }
}
