/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.controller.bean;

import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.common.DashBoardMapDataList;
import com.panu.competitor.information.common.InformationMessages;
import com.panu.competitor.information.common.MessagesCommon;
import com.panu.competitor.information.dto.Record;
import com.panu.competitor.information.dto.RecordParent;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.CodeSetup;
import com.panu.competitor.information.pojo.entity.Company;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import com.panu.competitor.information.pojo.entity.Plant;
import com.panu.competitor.information.spring.service.ICodeSetupService;
import com.panu.competitor.information.spring.service.ICompanyService;
import com.panu.competitor.information.spring.service.ILocationService;
import com.panu.competitor.information.spring.service.IPlantInformationDetailService;
import com.panu.competitor.information.spring.service.IPlantService;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author HNDK
 */
@Named("northMapPageMB")
@ViewScoped
public class DetailMapPageMB implements Serializable {

    // <editor-fold desc="variables of the class">
    private static final long serialVersionUID = 1L;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private ICodeSetupService codeSetupService;
    @Autowired
    private IPlantInformationDetailService competitorInfoDetailService;
    @Autowired
    private IPlantService plantService;
    @Autowired
    private ILocationService locationService;
    private List<Company> companyList;
    private List<DashBoardMapDataList> competitorInfoDetailList = new ArrayList<DashBoardMapDataList>();
    private List<PlantInformationDetail> competitorInfoDetailSubList;
    private String codeSetupName;
    private String plantPeriod;
    private List<RecordParent> parents = new ArrayList<RecordParent>();
    private List<CodeSetup> codeSetupLocationList;
    private List<String> companyCodes;
    private String focusLatLong;
    private MapModel complexModel;
    private List<String> companyNameList;

    // </editor-fold>
    public void init() throws CompetitorException {
        try {
            reset();
            this.codeSetupLocationList = new ArrayList<CodeSetup>();
            codeSetupLocationList.addAll(codeSetupService.findAllCodeSetupByCodeSetupType(ConstantCommon.LOCATION_CODESETUP_TYPE));
            companyList = new ArrayList<Company>();
            companyList.addAll(companyService.getCompanyByPlantLocationId(codeSetupService.findCodeSetupByCodeSetupName(codeSetupName).getCodeSetupId()));
            companyList = companyService.removeCompanyDuplicate(companyList);

            plantPeriod = new SimpleDateFormat("MMM-yyyy").format(new Date());

            parents = new ArrayList<RecordParent>();
            for (Company company : companyList) {
                companyCodes.add(company.getCompanyCode());
                competitorInfoDetailSubList = new ArrayList<PlantInformationDetail>();
                competitorInfoDetailSubList.addAll(competitorInfoDetailService.getCompetiorInfoDetailByCompanyIdAndPeriodAndZone(company.getCompanyCode(), plantPeriod, codeSetupService.findCodeSetupByCodeSetupName(codeSetupName).getCodeSetupId()));
                RecordParent dashBoardMapDataList = new RecordParent(setDashBoardMapData(company, competitorInfoDetailSubList));
                parents.add(dashBoardMapDataList);
                companyNameList.add(company.getCompanyCode());
            }
        } catch (DataAccessException e) {

        }
    }

    public List<Record> setDashBoardMapData(Company company, List<PlantInformationDetail> competitorInformationDetail) {
        List<Record> dashboardMapList = new ArrayList<Record>();
        try {
            for (PlantInformationDetail competitorInfoDetial : competitorInformationDetail) {
                Record dashBoardMapDTO = new Record();
                dashBoardMapDTO.setCompanyId(company.getCompanyId());
                dashBoardMapDTO.setCompanyName(company.getCompanyCode());
                dashBoardMapDTO.setBatchingCapacity(competitorInfoDetial.getPlantCapacity());
                dashBoardMapDTO.setBatchingPlantLocation(competitorInfoDetial.getLocation());
                dashBoardMapDTO.setNoOfBatchingPlant(competitorInfoDetial.getNoOfPlant());
                dashBoardMapDTO.setNoOfTrack(competitorInfoDetial.getNoOfTruck());
                dashBoardMapDTO.setPlantId(String.valueOf(competitorInfoDetial.getPlantId()));
                dashboardMapList.add(dashBoardMapDTO);
            }
            dashboardMapList = competitorInfoDetailService.removeDuplicateRecord(dashboardMapList);
        } catch (DataAccessException e) {

        }
        return dashboardMapList;

    }

    public String getCompanyName(int index) {
        String name = "";
        try {
            if (companyNameList != null) {
                name = companyNameList.get(index);
            }
        } catch (DataAccessException e) {

        }
        return name;
    }

    public String getLocationNameById(int id) {
        String name = "-";
        try {
            name = locationService.findLocationByLocationId(id).getName();
        } catch (DataAccessException e) {

        }
        return name;
    }

    public void onCompanySelect() throws CompetitorException {
        try {
            parents = new ArrayList<RecordParent>();
            companyNameList = new ArrayList<String>();
            if (companyCodes != null && !companyCodes.isEmpty() && companyCodes.get(0) != null) {
                List<Company> companys = new ArrayList<Company>();
                for (String companyCode : companyCodes) {
                    companys.add(companyService.findCompanyByCompanyCode(companyCode));
                }
                for (Company company : companys) {
                    competitorInfoDetailSubList = new ArrayList<PlantInformationDetail>();
                    competitorInfoDetailSubList.addAll(competitorInfoDetailService.getCompetiorInfoDetailByCompanyIdAndPeriodAndZone(company.getCompanyCode(), plantPeriod, codeSetupService.findCodeSetupByCodeSetupName(codeSetupName).getCodeSetupId()));
                    RecordParent dashBoardMapDataList = new RecordParent(setDashBoardMapData(company, competitorInfoDetailSubList));
                    parents.add(dashBoardMapDataList);
                    companyNameList.add(company.getCompanyCode());
                }
            }
        } catch (DataAccessException e) {

        }
    }

    public void onPeriodSelect(SelectEvent event) throws CompetitorException {
        try {
            parents = new ArrayList<RecordParent>();
            if (companyCodes != null && !companyCodes.isEmpty() && companyCodes.get(0) != null) {
                List<Company> companys = new ArrayList<Company>();
                for (String companyCode : companyCodes) {
                    companys.add(companyService.findCompanyByCompanyId(Integer.parseInt(companyCode)));
                }
                for (Company company : companys) {
                    competitorInfoDetailSubList = new ArrayList<PlantInformationDetail>();
                    competitorInfoDetailSubList.addAll(competitorInfoDetailService.getCompetiorInfoDetailByCompanyShortNameAndPeriod(company.getCompanyCode(), plantPeriod));
                    RecordParent dashBoardMapDataList = new RecordParent(setDashBoardMapData(company, competitorInfoDetailSubList));
                    parents.add(dashBoardMapDataList);
                }
            } else {
                new InformationMessages().showMessageDialog(FacesMessage.SEVERITY_INFO, MessagesCommon.M0047_COMPETITOR_INFORMATION, MessagesCommon.M0008_COMPANY_NAME_REQUIRED);
            }
        } catch (DataAccessException e) {

        }
    }

    public String getDataTableHeaderName(List<Record> records) {
        String name = "";
        try {
            for (Record record : records) {
                name = record.getCompanyName();
            }
        } catch (DataAccessException e) {

        }
        return name;
    }

    public void bindMapData(Record record) {
        try {
            Plant plant;
            complexModel = new DefaultMapModel();
            List<String> plantIdList = Arrays.asList(record.getPlantId().split(","));
            if (plantIdList.size() > 0) {
                for (String plantId : plantIdList) {
                    plant = plantService.getPlantByPlantId(Integer.parseInt(plantId));
                    focusLatLong = plant.getLatitude() + "," + plant.getLongitude();
                    LatLng coord = new LatLng(plant.getLatitude().doubleValue(), plant.getLongitude().doubleValue());
                    Marker marker = new Marker(coord, plant.getPlantName() + " (" + plant.getLatitude().doubleValue() + "," + plant.getLongitude().doubleValue() + ")");
                    complexModel.addOverlay(marker);
                }
            }
        } catch (DataAccessException e) {

        }
    }

    public void reset() {
        parents = new ArrayList<RecordParent>();
        competitorInfoDetailList = new ArrayList<DashBoardMapDataList>();
        competitorInfoDetailSubList = new ArrayList<PlantInformationDetail>();
        companyCodes = new ArrayList<String>();
        companyNameList = new ArrayList<String>();
    }

    // <editor-fold desc="getter and setter of the class">
    public String getCodeSetupName() {
        return codeSetupName;
    }

    public void setCodeSetupName(String codeSetupName) {
        this.codeSetupName = codeSetupName;
    }

    public List<String> getCompanyCodes() {
        return companyCodes;
    }

    public void setCompanyCodes(List<String> companyCode) {
        this.companyCodes = companyCode;
    }

    public String getPlantPeriod() {
        return plantPeriod;
    }

    public void setPlantPeriod(String plantPeriod) {
        this.plantPeriod = plantPeriod;
    }

    public List<DashBoardMapDataList> getCompetitorInfoDetailList() {
        return competitorInfoDetailList;
    }

    public void setCompetitorInfoDetail(List<DashBoardMapDataList> competitorInfoDetailList) {
        this.competitorInfoDetailList = competitorInfoDetailList;
    }

    public List<RecordParent> getParents() {
        return parents;
    }

    public void setParents(List<RecordParent> parents) {
        this.parents = parents;
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

    public List<String> getCompanyNameList() {
        return companyNameList;
    }

    public void setCompanyNameList(List<String> companyNameList) {
        this.companyNameList = companyNameList;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }
    // </editor-fold>

}
