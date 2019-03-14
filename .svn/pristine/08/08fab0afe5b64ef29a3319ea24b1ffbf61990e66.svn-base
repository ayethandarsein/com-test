/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.controller.bean;

import com.panu.competitor.information.common.CommonUtilsFunctions;
import com.panu.competitor.information.dto.GoogleMapLegendsDTO;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Company;
import com.panu.competitor.information.pojo.entity.Plant;
import com.panu.competitor.information.spring.service.ICompanyService;
import com.panu.competitor.information.spring.service.IPlantService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author HNDK
 */
@Named("singaporeMapMB")
@ViewScoped
public class SingaporeMapMB implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IPlantService plantService;
    private List<Company> companyList;
    private MapModel simpleModel;
    private List<String> companyCodes;
    private List<GoogleMapLegendsDTO> googleMapLegends;

    public void init() {
        companyList = new ArrayList<Company>();
        companyCodes = new ArrayList<String>();
        companyList.addAll(companyService.findAllCompany());
        companyList = companyService.removeCompanyDuplicate(companyList);
        for (Company company : companyList) {
            companyCodes.add(company.getCompanyCode());
        }
        simpleModel = new DefaultMapModel();
        bindMapMarker(companyList);
    }

    public void reset() {

    }

    public void onCompanySelect() throws CompetitorException {
        googleMapLegends = new ArrayList<GoogleMapLegendsDTO>();
        simpleModel = new DefaultMapModel();
        List<Company> companys = new ArrayList<Company>();
        if (companyCodes != null && !companyCodes.isEmpty() && companyCodes.get(0) != null) {
            for (String companyCode : companyCodes) {
                companys.add(companyService.findCompanyByCompanyCode(companyCode));
            }
            bindMapMarker(companys);
        }
    }

    public void bindMapMarker(List<Company> companyList) {
        List<Plant> plantList;
        googleMapLegends = new ArrayList<GoogleMapLegendsDTO>();
        int index = 0;
        List<String> googleMapIcons = new ArrayList<String>();
        googleMapIcons = new CommonUtilsFunctions().getGoogleMapIcons();
        for (int i = 0; i < companyList.size(); i++) {
            if (index == 10) {
                index = 0;
            }
            plantList = new ArrayList<Plant>();
            plantList = plantService.getPlantsByCompanyId(companyList.get(i).getCompanyId());
            for (int j = 0; j < plantList.size(); j++) {
                LatLng coord = new LatLng(plantList.get(j).getLatitude().doubleValue(), plantList.get(j).getLongitude().doubleValue());
                Marker marker = new Marker(coord, companyList.get(i).getCompanyCode() + " (" + plantList.get(j).getLatitude().doubleValue() + "," + plantList.get(j).getLongitude().doubleValue() + ")");
                marker.setIcon(googleMapIcons.get(index));
                googleMapLegends.add(new GoogleMapLegendsDTO(companyList.get(i).getCompanyCode(), googleMapIcons.get(index)));
                simpleModel.addOverlay(marker);
            }
            index += 1;
        }
        googleMapLegends = companyService.removeMapIconsDuplicate(googleMapLegends);
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

    public List<String> getCompanyCodes() {
        return companyCodes;
    }

    public void setCompanyCodes(List<String> companyCodes) {
        this.companyCodes = companyCodes;
    }

    public List<GoogleMapLegendsDTO> getGoogleMapLegends() {
        return googleMapLegends;
    }

    public void setGoogleMapLegends(List<GoogleMapLegendsDTO> googleMapLegends) {
        this.googleMapLegends = googleMapLegends;
    }

}
