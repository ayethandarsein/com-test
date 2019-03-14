/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dao;

import com.panu.competitor.information.pojo.entity.Role;
import javax.inject.Named;

/**
 *
 * @author HNDK
 */
@Named
public class RoleDAO extends BaseDAO<Role> {

    public RoleDAO() {
        super(Role.class);
    }
}
