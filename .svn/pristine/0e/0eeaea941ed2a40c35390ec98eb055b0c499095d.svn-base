/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.controller.bean;

import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.common.CurrentLoggedInUser;
import com.panu.competitor.information.pojo.entity.CompetitorUser;
import com.panu.competitor.information.pojo.entity.Role;
import com.panu.competitor.information.spring.service.IRoleService;
import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author HNDK
 */
@Named("menuMB")
@ViewScoped
public class MenuMB implements Serializable {

    // <editor-fold desc="variables of the class">
    @Autowired
    private IRoleService roleService;
    private boolean mk_render;
    private boolean admin_render;
    private boolean sales_render;
    private String username;
    private String fullname;
    private String rolename;
    private CompetitorUser user;
    private Role role;

    // </editor-fold>
    /**
     * initialization
     *
     * The purpose of this method is to initialize method of the view
     *
     */
    public void init() {
        try {
            admin_render = false;
            mk_render = false;
            sales_render = false;
            user = new CompetitorUser();
            role = new Role();
            user = new CurrentLoggedInUser().getCurrentUser();
            username = user.getUserName();
            fullname = user.getFullName();
            role = roleService.findRoleByRoleId(user.getRoleId());
            rolename = role.getRoleName();
            if (rolename.trim().equalsIgnoreCase(ConstantCommon.ROLE_ADMIN)) {
                admin_render = true;
            } else if (rolename.trim().equalsIgnoreCase(ConstantCommon.ROLE_MARKETING)) {
                mk_render = true;
            } else if (rolename.trim().equalsIgnoreCase(ConstantCommon.ROLE_SALES)) {
                sales_render = true;
            }
        } catch (DataAccessException ex) {

        }
    }

    // <editor-fold desc="getter and setter of the class">
    public boolean isAdmin_render() {
        return admin_render;
    }

    public void setAdmin_render(boolean admin_render) {
        this.admin_render = admin_render;
    }

    public boolean isSales_render() {
        return sales_render;
    }

    public void setSales_render(boolean sales_render) {
        this.sales_render = sales_render;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return rolename;
    }

    public void setRole(String role) {
        this.rolename = role;
    }

    public boolean isMk_render() {
        return mk_render;
    }

    public void setMk_render(boolean mk_render) {
        this.mk_render = mk_render;
    }

    // </editor-fold>
}
