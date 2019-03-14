/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service.imp;

import com.panu.competitor.information.dao.PlantDAO;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Plant;
import com.panu.competitor.information.spring.service.IPlantService;
import com.panu.competitor.information.spring.service.constant.NamedQueryConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author HNDK
 */
@Named
public class PlantServiceImp implements IPlantService {

    @Autowired
    private PlantDAO plantDAO;

    @Override
    public List<Plant> findAllPlants() {
        return plantDAO.select(NamedQueryConstant.GET_PLANTS);
    }

    @Override
    public void addPlant(Plant plant) throws CompetitorException {
        plantDAO.insert(plant);
    }

    @Override
    public void updatePlant(Plant updatePlant) throws CompetitorException {
        plantDAO.update(updatePlant);
    }

    @Override
    public boolean validateCompanyInfo(Plant plant, List<Plant> plantList) {
        boolean returnValue = true;
        for (Plant pl : plantList) {
            if (pl.getPlantName().equalsIgnoreCase(plant.getPlantName()) || pl.getLatitude() == plant.getLatitude() || pl.getLongitude() == plant.getLongitude()) {
                returnValue = false;
            }
        }
        return returnValue;
    }

    @Override
    public List<Plant> getPlantsByCompanyIdandLocationId(int companyId, int location) {
        return plantDAO.getPlantsByCompanyIdandLocationId(companyId,location);
    }

    @Override
    public Plant getPlantByPlantId(int id) {
        String constantQuery = NamedQueryConstant.GET_PLANT_BY_PLANT_ID;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.PLANTID);
        List<Integer> paramValueList = new ArrayList<Integer>();
        paramValueList.add(id);
        List<Plant> plantList = plantDAO.selectByIntType(constantQuery, paramColumnList,
                paramValueList);
        Plant plant = new Plant();
        if (plantList != null && !plantList.isEmpty() && plantList.get(0) != null) {
            plant = plantList.get(0);
        }
        return plant;
    }

    @Override
    public List<Plant> getPlantsByCompanyId(int companyId) {
        String constantQuery = NamedQueryConstant.GET_PLANT_BY_COMPANY_ID;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.COMPANYID);
        List<Integer> paramValueList = new ArrayList<Integer>();
        paramValueList.add(companyId);
        List<Plant> plantList = plantDAO.selectByIntType(constantQuery, paramColumnList,
                paramValueList);
        List<Plant> plant = new ArrayList<Plant>();
        if (plantList != null && !plantList.isEmpty() && plantList.get(0) != null) {
            plant = plantList;
        }
        return plant;
    }

}
