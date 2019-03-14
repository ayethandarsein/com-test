/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service;

import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Plant;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HNDK
 */
public interface IPlantService {
    /**
     * The purpose of this method is find all Company info.
     *
     * @return companies as list
     */
    public List<Plant> findAllPlants();

    /**
     * The purpose of this method is add new Plant info.
     *
     * @param plant
     * @throws CompetitorException
     */
    public void addPlant(Plant plant) throws CompetitorException;

    /**
     * The purpose of this method is add new Plant info.
     *
     * @param updatePlant
     * @throws CompetitorException
     */
    public void updatePlant(Plant updatePlant) throws CompetitorException;

    /**
     * The purpose of this method is to validate new Plant info.
     *
     * @param plant
     * @param plantList
     * @return boolean validated value
     */
    public boolean validateCompanyInfo(Plant plant, List<Plant> plantList);
    /**
     * The purpose of this method is to validate new Plant info.
     * 
     * @param companyId
     * @param location
     * @return boolean validated value
     */
    public List<Plant> getPlantsByCompanyIdandLocationId(int companyId, int location);

    public Plant getPlantByPlantId(int id);

    public List<Plant> getPlantsByCompanyId(int companyId);
}
