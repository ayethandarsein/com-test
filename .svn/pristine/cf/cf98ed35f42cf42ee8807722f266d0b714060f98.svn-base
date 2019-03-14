/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.pojo.entity;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author HNDK
 */
public class LogInUser extends User {

    private int userId;
    private CompetitorUser user;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CompetitorUser getUser() {
        return user;
    }

    public void setUser(CompetitorUser eUser) {
        this.user = eUser;
    }

    public LogInUser(String username, String password, Collection<? extends GrantedAuthority> authorities, CompetitorUser user) {
        super(username, password, authorities);
        this.user = user;
    }

}
