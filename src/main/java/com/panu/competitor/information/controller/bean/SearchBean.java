/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.controller.bean;

import com.panu.competitor.information.common.CommonUtilsFunctions;
import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Capabilities;
import com.panu.competitor.information.pojo.entity.CodeSetup;
import com.panu.competitor.information.pojo.entity.Company;
import com.panu.competitor.information.pojo.entity.Plant;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import com.panu.competitor.information.spring.service.IPlantInformationDetailService;
import com.panu.competitor.information.spring.service.ICapabilityService;
import com.panu.competitor.information.spring.service.ICodeSetupService;
import com.panu.competitor.information.spring.service.ICompanyService;
import com.panu.competitor.information.spring.service.IPlantService;
import com.panu.competitor.information.spring.service.imp.PlantInformationServiceImp;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * @author Htet Nanda Kyaw
 */
@Named("searchBean")
@ViewScoped
public class SearchBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private PlantInformationServiceImp competitorinfomationserviceimp;

    @Autowired
    private IPlantInformationDetailService icompanyservice;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IPlantService plantService;

    @Autowired
    private ICapabilityService capabilityService;

    @Autowired
    private ICodeSetupService codeSetupService;

    private List<Company> companyList;

    private List<Plant> plantList;

    private List<Capabilities> capabilityList;

    private List<String> companyshortname;

    private List<Integer> location;

    private List<PlantInformationDetail> comInfoDetailList = new ArrayList<PlantInformationDetail>();

    private List<Integer> zone;

    private Integer nooftruck;

    private List<String> capabilities;

    private char hdbapproved;

    private String focusLatLong;

    private MapModel complexModel;

    private String period;

    private Date periodDate;

    private boolean dataTableRendered;

    public void init() {
        zone=new ArrayList<>();
        dataTableRendered = false;
        comInfoDetailList = new ArrayList<PlantInformationDetail>();
        companyList = new ArrayList<Company>();
        companyList.addAll(companyService.findAllCompany());
        plantList = new ArrayList<Plant>();
        plantList.addAll(plantService.findAllPlants());
        capabilityList = new ArrayList<Capabilities>();
        capabilityList.addAll(capabilityService.findAllCapabilities());
        dataclear();
    }

    public void dataclear() {
        zone=new ArrayList<>();
        period = "";
        comInfoDetailList = new ArrayList<>();
        companyshortname = new ArrayList<>();
        plantList = new ArrayList<>();
        location = new ArrayList<>();
        capabilities = new ArrayList<>();
        nooftruck = null;
        hdbapproved = '\u0000';
        dataTableRendered = false;
    }

    public void bindMapData(PlantInformationDetail competitorInfoDetail) throws CompetitorException {
        Plant plant = new Plant();
        complexModel = new DefaultMapModel();
        plant = plantService.getPlantByPlantId(competitorInfoDetail.getPlantId());
        focusLatLong = plant.getLatitude() + "," + plant.getLongitude();

        LatLng coord = new LatLng(plant.getLatitude().doubleValue(), plant.getLongitude().doubleValue());
        Marker marker = new Marker(coord, plant.getPlantName() + " (" + plant.getLatitude().doubleValue() + "," + plant.getLongitude().doubleValue() + ")");
        complexModel.addOverlay(marker);

    }

    public String getPlantNameById(int id) {
        String name = "";
        name = plantService.getPlantByPlantId(id).getPlantName();
        return name;
    }

    public String bindCoordinate(PlantInformationDetail competitorInfoDetail) {
        String xyCoordinate = "";
        Plant plant = new Plant();
        plant = plantService.getPlantByPlantId(competitorInfoDetail.getPlantId());
        xyCoordinate = plant.getLatitude().doubleValue() + ", " + plant.getLongitude().doubleValue();
        return xyCoordinate;
    }

    public List<String> completePlantPeriod(String query) {
        List<String> allPeriod = new CommonUtilsFunctions().getPreviousAndFutureOneYears();
        List<String> filteredPeriod = new ArrayList<String>();

        for (int i = 0; i < allPeriod.size(); i++) {
            String period = allPeriod.get(i);
            if (period.toLowerCase().contains(query)) {
                filteredPeriod.add(period);
            }
        }

        return filteredPeriod;
    }

    public void searchData() throws CompetitorException {
        try {
            dataTableRendered = true;
            if (!companyshortname.isEmpty()) {
                if (nooftruck == null) {
                    nooftruck = 0;
                }
                if (period.equals("") || period == null) {
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");
                    String strDate = dateFormat.format(date);
                    periodDate = new SimpleDateFormat("MMM-yyyy").parse(strDate);
                } else {
                    periodDate = new SimpleDateFormat("MMM-yyyy").parse(period);
                }
                comInfoDetailList = icompanyservice.findAllCompetiorInfoDetailBySearchFields(companyshortname, periodDate, location, zone, nooftruck, hdbapproved, capabilities);
            } else {
                List<String> companyshortname = new ArrayList();
                for (int i = 0; i < companyList.size(); i++) {
                    companyshortname.add(companyList.get(i).getCompanyCode());
                }
                if (nooftruck == null) {
                    nooftruck = 0;
                }
                if (period.equals("") || period == null) {
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");
                    String strDate = dateFormat.format(date);
                    periodDate = new SimpleDateFormat("MMM-yyyy").parse(strDate);
                } else {
                    periodDate = new SimpleDateFormat("MMM-yyyy").parse(period);
                }
                comInfoDetailList = icompanyservice.findAllCompetiorInfoDetailBySearchFields(companyshortname, periodDate, location, zone, nooftruck, hdbapproved, capabilities);
//                MessagesView.error(MessagesCommon.M0048_NEED_TO_SELECT_COMPANY_NAME);
            }
        } catch (Exception e) {
        }

    }

    public String showTolDetail(PlantInformationDetail competitorInfoDetail) {
        String tolDetail = "";
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        if (competitorInfoDetail.getLeasePeriodFromDate() != null && competitorInfoDetail.getLeasePeriodToDate() != null) {
            tolDetail = format.format(competitorInfoDetail.getLeasePeriodFromDate()) + " to " + format.format(competitorInfoDetail.getLeasePeriodToDate());
        }
        return tolDetail;
    }

    public String showHDBApprove(char hdbApproved) {
        String hdbApproveOrNot = ConstantCommon.STATUS_NO;
        if (hdbApproved == 'Y') {
            hdbApproveOrNot = ConstantCommon.STATUS_YES;
        }
        return hdbApproveOrNot;
    }

    public String getCompanyName(int cominfoID) throws CompetitorException {
        return competitorinfomationserviceimp.findCompanyNameByCompetitorInfoID(cominfoID);
    }

    public String getLocation(int locationid) {
        CodeSetup cs = new CodeSetup();
        cs = codeSetupService.findCodeSetupByCodeSetupId(locationid);
        return cs.getCodeSetupName();
    }

    public String getZone(int zone) {
        CodeSetup cs = new CodeSetup();
        cs = codeSetupService.findCodeSetupByCodeSetupId(zone);
        return cs.getCodeSetupName();
    }

    public ICompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(ICompanyService companyService) {
        this.companyService = companyService;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public String getFocusLatLong() {
        return focusLatLong;
    }

    public void setFocusLatLong(String focusLatLong) {
        this.focusLatLong = focusLatLong;
    }

    public MapModel getComplexModel() {
        return complexModel;
    }

    public void setComplexModel(MapModel complexModel) {
        this.complexModel = complexModel;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public IPlantService getPlantService() {
        return plantService;
    }

    public void setPlantService(IPlantService plantService) {
        this.plantService = plantService;
    }

    public List<Plant> getPlantList() {
        return plantList;
    }

    public void setPlantList(List<Plant> plantList) {
        this.plantList = plantList;
    }

    public ICapabilityService getCapabilityService() {
        return capabilityService;
    }

    public void setCapabilityService(ICapabilityService capabilityService) {
        this.capabilityService = capabilityService;
    }

    public List<Capabilities> getCapabilityList() {
        return capabilityList;
    }

    public void setCapabilityList(List<Capabilities> capabilityList) {
        this.capabilityList = capabilityList;
    }

    public List<String> getCompanyshortname() {
        return companyshortname;
    }

    public void setCompanyshortname(List<String> companyshortname) {
        this.companyshortname = companyshortname;
    }

    public IPlantInformationDetailService getIcompanyservice() {
        return icompanyservice;
    }

    public void setIcompanyservice(IPlantInformationDetailService icompanyservice) {
        this.icompanyservice = icompanyservice;
    }

    public List<Integer> getLocation() {
        return location;
    }

    public void setLocation(List<Integer> location) {
        this.location = location;
    }

    public List<Integer> getZone() {
        return zone;
    }

    public void setZone(List<Integer> zone) {
        this.zone = zone;
    }

    

    public Integer getNooftruck() {
        return nooftruck;
    }

    public void setNooftruck(Integer nooftruck) {
        this.nooftruck = nooftruck;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

    public char getHdbapproved() {
        return hdbapproved;
    }

    public void setHdbapproved(char hdbapproved) {
        this.hdbapproved = hdbapproved;
    }

    public List<PlantInformationDetail> getComInfoDetailList() {
        return comInfoDetailList;
    }

    public void setComInfoDetailList(List<PlantInformationDetail> comInfoDetailList) {
        this.comInfoDetailList = comInfoDetailList;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getPeriodDate() {
        return periodDate;
    }

    public boolean isDataTableRendered() {
        return dataTableRendered;
    }

    public void setDataTableRendered(boolean dataTableRendered) {
        this.dataTableRendered = dataTableRendered;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
    }

}
