/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service;

import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.CompetitorUser;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HNDK
 */
public interface ICompetitorUserService {

    /**
     * The purpose of this method is find all CompetitorUser info.
     *
     * @return companies as list
     */
    public List<CompetitorUser> findAllUser();

    /**
     * The purpose of this method is add new CompetitorUser info.
     *
     * @param user
     * @throws CompetitorException
     */
    public void addUser(CompetitorUser user) throws CompetitorException;

    /**
     * The purpose of this method is add new CompetitorUser info.
     *
     * @param updateUser
     * @throws CompetitorException
     */
    public void updateUser(CompetitorUser updateUser) throws CompetitorException;

    /**
     * The purpose of this method is to validate new CompetitorUser info.
     *
     * @param user
     * @param userList
     * @return boolean validated value
     */
    public boolean validateUserInfo(CompetitorUser user, List<CompetitorUser> userList);

    /**
     * The purpose of this method is find CompetitorUser By CompetitorUser id.
     *
     * @param userId
     * @return CompetitorUser
     */
    public CompetitorUser findUserByUserId(int userId);

    /**
     * The purpose of this method is find CompetitorUser By CompetitorUser id.
     *
     * @param role
     * @return CompetitorUser
     */
    public CompetitorUser findUserByUserRole(int role);
}
