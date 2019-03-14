/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service;

import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Capabilities;
import com.panu.competitor.information.pojo.entity.Location;
import java.util.List;
import java.util.Map;

/**
 *Interface Class for Location Service.
 * 
 * @author HNDK
 */
public interface ILocationService {
    /**
     * The purpose of this method is find all location info.
     *
     * @return companies as list
     */
    public List<Location> findAllLocation();

    /**
     * The purpose of this method is add new location info.
     *
     * @param location
     * @throws CompetitorException
     */
    public void addLocation(Location location) throws CompetitorException;
    /**
     * The purpose of this method is to delete location info.
     *
     * @param deleteLocation
     * @throws CompetitorException
     */
    public void deleteLocation(Location deleteLocation) throws CompetitorException;
    /**
     * The purpose of this method is add new location info.
     *
     * @param updateLocation
     * @throws CompetitorException
     */
    public void updateLocation(Location updateLocation) throws CompetitorException;

    /**
     * The purpose of this method is to validate new capability info.
     *
     * @param capability
     * @param capabilityList
     * @return boolean validated value
     */
    public Map<String, String> validateLocationInfo(Location location, List<Location> locationList);
     /**
     * The purpose of this method is to get location by id info.
     *
     * @param locationId
     * @return Location
     */
    public Location getLocationByLocationIds(String locationShortName);

    public Location findLocationByLocationId(int id);
}
