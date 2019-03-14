/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service;

import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Capabilities;
import java.util.List;
import java.util.Map;

/**
 *Interface Class for Capability Service.
 * 
 * @author HNDK
 */
public interface ICapabilityService {
    /**
     * The purpose of this method is find all capability info.
     *
     * @return companies as list
     */
    public List<Capabilities> findAllCapabilities();

    /**
     * The purpose of this method is add new capability info.
     *
     * @param capability
     * @throws CompetitorException
     */
    public void addCapability(Capabilities capability) throws CompetitorException;
    /**
     * The purpose of this method is to delete capability info.
     *
     * @param deleteCapability
     * @throws CompetitorException
     */
    public void deleteCapability(Capabilities deleteCapability) throws CompetitorException;
    /**
     * The purpose of this method is add new capability info.
     *
     * @param updateCapability
     * @throws CompetitorException
     */
    public void updateCapability(Capabilities updateCapability) throws CompetitorException;

    /**
     * The purpose of this method is to validate new capability info.
     *
     * @param capability
     * @param capabilityList
     * @return boolean validated value
     */
    public Map<String, String> validateCapabilityInfo(Capabilities capability, List<Capabilities> capabilityList);
     /**
     * The purpose of this method is to get capability by id info.
     *
     * @param capabilityId
     * @return Capabilities
     */
    public Capabilities getCapabilitesByCapabilyIds(String capabilityShortName);
}
