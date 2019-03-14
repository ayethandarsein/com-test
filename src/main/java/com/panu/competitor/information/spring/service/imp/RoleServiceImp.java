/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service.imp;

import com.panu.competitor.information.dao.RoleDAO;
import com.panu.competitor.information.pojo.entity.Role;
import com.panu.competitor.information.spring.service.IRoleService;
import com.panu.competitor.information.spring.service.constant.NamedQueryConstant;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author HNDK
 */
@Named
public class RoleServiceImp implements IRoleService{
    @Autowired
    private RoleDAO roleDAO;
    @Override
    public Role findRoleByRoleId(int roleId) {
        String constantQuery = NamedQueryConstant.FIND_ROLE_BYROLE_ID;
		List<String> paramColumnList = new ArrayList<String>();
		paramColumnList.add(NamedQueryConstant.ROLEID);
		List<Integer> paramValueList = new ArrayList<Integer>();
		paramValueList.add(roleId);
		List<Role> codeSetUpList = roleDAO.selectByIntType(constantQuery, paramColumnList,
				paramValueList);
		Role role = new Role();
		if (codeSetUpList != null && !codeSetUpList.isEmpty() && codeSetUpList.get(0) != null) {
			role = codeSetUpList.get(0);
		}
		return role;
    }

    @Override
    public List<Role> findAllRoles() {
       return roleDAO.select(NamedQueryConstant.GET_ROLES);
    }
    
}
