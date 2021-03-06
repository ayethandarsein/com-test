/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.controller.bean;

import com.panu.competitor.information.common.CommonUtilsFunctions;
import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.common.CurrentLoggedInUser;
import com.panu.competitor.information.common.MessagesCommon;
import com.panu.competitor.information.common.MessagesView;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.CodeSetup;
import com.panu.competitor.information.pojo.entity.Company;
import com.panu.competitor.information.pojo.entity.Location;
import com.panu.competitor.information.pojo.entity.Plant;
import com.panu.competitor.information.spring.service.ICodeSetupService;
import com.panu.competitor.information.spring.service.ICompanyService;
import com.panu.competitor.information.spring.service.ILocationService;
import com.panu.competitor.information.spring.service.IPlantService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author HNDK
 */
@Named("plantEntryFormMB")
@ViewScoped
public class PlantEntryFormMB implements Serializable {

    // <editor-fold desc="variables of the class">
    private static final long serialVersionUID = 1L;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private ICodeSetupService codeSetupService;
    @Autowired
    private IPlantService plantService;
    @Autowired
    private ILocationService locationService;
    private List<Location> locationList;
    private List<Company> companyList;
    private List<CodeSetup> codeSetupLocationList;
    private List<CodeSetup> codeSetupActiveStatusList;
    private List<Plant> plantList;
    private List<Plant> filterPlantList;
    private Plant plant;
    private Plant updatePlant;
    private Plant tempPlant;
    private String activeStatus;
    private String updateActiveStatus;
    private boolean isUpdateValidation;
    private String title_plant_list_name = ConstantCommon.TITLE_PLANT_LIST_NAME;
    private final Date date = new Date();
    private String plantNameRequiredMessage = MessagesCommon.M0028_PLANT_NAMED_REQUIRED;
    private String companyNameRequiredMessage = MessagesCommon.M0008_COMPANY_NAME_REQUIRED;
    private String locationRequiredMessage = MessagesCommon.M0029_LOCATION_NAMED_REQUIRED;
    private String statusRequiredMessage = MessagesCommon.M0030_STATUS_NAMED_REQUIRED;
    private String longitudeRequiredMessage = MessagesCommon.M0031_LONGITUDE_NAMED_REQUIRED;
    private String latitudeRequiredMessage = MessagesCommon.M0032_LATITUDE_NAMED_REQUIRED;
    private String periodRequiredMessage = MessagesCommon.M0032_PERIOD_NAMED_REQUIRED;
    private String zoneRequiredMessage = MessagesCommon.M0050_ZONE_NAMED_REQUIRED;
    private String messagetext;
    private String messagelbl;
    private String period;
    private String updatePeriod;

    // </editor-fold>
    /**
     * init
     *
     * The purpose of this method is to initialize method of the view
     *
     */
    public void init() {
        try {
            reset();
            locationList = new ArrayList<Location>();
            locationList.addAll(locationService.findAllLocation());
            codeSetupLocationList.addAll(codeSetupService.findAllCodeSetupByCodeSetupType(ConstantCommon.LOCATION_CODESETUP_TYPE));
            codeSetupActiveStatusList.addAll(codeSetupService.findAllCodeSetupByCodeSetupType(ConstantCommon.ACTIVE_STATUS_CODESETUP_TYPE));
            plantList.addAll(plantService.findAllPlants());
            filterPlantList.addAll(plantList);
            companyList.addAll(companyService.findAllCompany());
        } catch (DataAccessException e) {

        }

    }

    /**
     * savePlant
     *
     * The purpose of this method is to save new plant to the database
     *
     * @throws com.panu.competitor.information.exception.CompetitorException
     * @throws java.text.ParseException
     */
    public void savePlant() throws CompetitorException, ParseException {
        try {
            messagetext = "";
            plant.setActiveStatus(activeStatus.charAt(0));
            plant.setIsDelete(ConstantCommon.STATUS_ISDELETE);
            plant.setCreatedUserId(new CurrentLoggedInUser().getLogInUserId());
            plant.setCreatedDate(date);
            if (validatePlantInfo(plant, plantList)) {
                plant.setPlantPeriod(new SimpleDateFormat("MMM-yyyy").parse(period));
                plantService.addPlant(plant);
                messagetext = MessagesCommon.M0025_PLANT_SAVE_SUCCESSFULLY;
                RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                MessagesView.info(messagetext);
            } else {
                if (messagelbl.trim().isEmpty()) {
                    messagetext = MessagesCommon.M0026_PLANT_INFO_DUPLICATE;
                    RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                    MessagesView.info(messagetext);
                } else {
                    RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                    MessagesView.info(messagelbl);
                }
            }
            init();
        } catch (CompetitorException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Message: "));
        }
    }

    /**
     * editPlant
     *
     * The purpose of this method is to copy row from data table and place it in
     * dialog
     *
     * @param plant
     */
    public void editPlant(Plant plant) {
        try {
            updatePlant = plant;
            updatePeriod = new SimpleDateFormat("MMM-yyyy").format(plant.getPlantPeriod());
            updateActiveStatus = String.valueOf(updatePlant.getActiveStatus());
            tempPlant = updatePlant;
        } catch (DataAccessException e) {

        }
    }

    /**
     * deletePlant
     *
     * The purpose of this method is to update delete flag to the database true
     *
     * @param plant
     * @throws com.panu.competitor.information.exception.CompetitorException
     */
    public void deletePlant(Plant plant) throws CompetitorException {
        try {
            plant.setIsDelete(ConstantCommon.STATUS_DELETE);
            plant.setUpdatedDate(date);
            plant.setUpdatedUserId(new CurrentLoggedInUser().getLogInUserId());
            plantService.updatePlant(plant);
            init();
            RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
            MessagesView.info(MessagesCommon.M0027_PLANT_DELETE_SUCCESSFULLY);
//            new InformationMessages().showMessageDialog(FacesMessage.SEVERITY_INFO, MessagesCommon.M0024_PLANT_INFORMATION, MessagesCommon.M0027_PLANT_DELETE_SUCCESSFULLY);
        } catch (CompetitorException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Message: "));
        }
    }

    /**
     * updatePlantInfo
     *
     * The purpose of this method is to update plant info to the database
     *
     * @throws com.panu.competitor.information.exception.CompetitorException
     * @throws java.text.ParseException
     */
    public void updatePlantInfo() throws CompetitorException, ParseException {
        try {
            isUpdateValidation = true;
            updatePlant.setUpdatedDate(date);
            updatePlant.setActiveStatus(updateActiveStatus.charAt(0));
            updatePlant.setUpdatedUserId(new CurrentLoggedInUser().getLogInUserId());
            if (validatePlantInfo(updatePlant, plantList)) {
                updatePlant.setPlantPeriod(new SimpleDateFormat("MMM-yyyy").parse(updatePeriod));
                plantService.updatePlant(updatePlant);
                RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                MessagesView.info(MessagesCommon.M0033_PLANT_UPDATED_SUCCESSFULLY);
//            new InformationMessages().showMessageDialog(FacesMessage.SEVERITY_INFO, MessagesCommon.M0024_PLANT_INFORMATION, MessagesCommon.M0033_PLANT_UPDATED_SUCCESSFULLY);
            } else {
                RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                MessagesView.error(MessagesCommon.M0026_PLANT_INFO_DUPLICATE);
//            new InformationMessages().showMessageDialog(FacesMessage.SEVERITY_INFO, MessagesCommon.M0024_PLANT_INFORMATION, MessagesCommon.M0026_PLANT_INFO_DUPLICATE);
            }
            init();
            isUpdateValidation = false;
        } catch (DataAccessException e) {

        }
    }

    /**
     * validatePlantInfo
     *
     * The purpose of this method is to validate plant
     *
     * @param plant
     * @param plantList
     * @return boolean returnValue
     */
    public boolean validatePlantInfo(Plant plant, List<Plant> plantList) {
        messagelbl = "";
        boolean returnValue = false;
        try {
            if (!plant.getPlantName().trim().isEmpty()
                    && plant.getLatitude().compareTo(BigDecimal.ZERO) != 0
                    && plant.getLongitude().compareTo(BigDecimal.ZERO) != 0) {
                if (isUpdateValidation) {
                    plantList.remove(plant);
                }

                returnValue = plantService.validateCompanyInfo(plant, plantList);
            } else {
                if (plant.getPlantName().trim().isEmpty()) {
                    messagelbl += MessagesCommon.M0047_PLANT_NAME_EMPTY;
                    returnValue = false;
                }
                if (plant.getLatitude().compareTo(BigDecimal.ZERO) == 0) {
                    messagelbl += MessagesCommon.M0048_LATITUDE_EMPTY;
                    returnValue = false;
                }
                if (plant.getLongitude().compareTo(BigDecimal.ZERO) == 0) {
                    messagelbl += MessagesCommon.M0049_LONGITUDE_EMPTY;
                    returnValue = false;
                }
            }
        } catch (DataAccessException e) {

        }
        return returnValue;
    }

    public String bigDecimalNumberFormat(BigDecimal bdValue) {
        String bdStringValue = bdValue.toString();
        DecimalFormat decimalFormat = new DecimalFormat("0.######");
        String result = decimalFormat.format(Double.valueOf(bdStringValue));
        return result;
    }

    /**
     * reset
     *
     * The purpose of this method is to reset the bean variables
     *
     */
    public void reset() {
        companyList = new ArrayList<Company>();
        codeSetupLocationList = new ArrayList<CodeSetup>();
        codeSetupActiveStatusList = new ArrayList<CodeSetup>();
        plantList = new ArrayList<Plant>();
        filterPlantList = new ArrayList<Plant>();
        plant = new Plant();
        updatePlant = new Plant();
        tempPlant = new Plant();
        messagetext = "";
        period = new SimpleDateFormat("MMM-yyyy").format(new Date());
    }

    /**
     * getCodeSetupNameById
     *
     * The purpose of this method is to get CodeSetup name by id
     *
     * @param codeSetupId
     * @return code setup name as string
     */
    public String getCodeSetupNameById(int codeSetupId) {
        String name = "-";
        try {
            for (Location codeSetup : locationList) {
                if (codeSetup.getLocationId() == codeSetupId) {
                    name = codeSetup.getName();
                }
            }
        } catch (DataAccessException e) {

        }
        return name;
    }

    /**
     * getCompanyNameById
     *
     * The purpose of this method is to get Company name by id
     *
     * @param companyId
     * @return code setup name as string
     */
    public String getCompanyNameById(int companyId) {
        String companyName = "";
        try {
            Company company = companyService.findCompanyByCompanyId(companyId);
            companyName = company.getName();
        } catch (DataAccessException e) {

        }
        return companyName;
    }

    /**
     * getCompanyNameById
     *
     * The purpose of this method is to get status by character
     *
     * @param c
     * @return status as string
     */
    public String getActiveStatus(char c) {
        if (c == 'Y') {
            return ConstantCommon.ACTIVE_STATUS;
        } else {
            return ConstantCommon.INACTIVE_STATUS;
        }
    }

    public List<String> completePlantPeriod(String query) {
        List<String> filteredPeriod = new ArrayList<String>();
        try {
            List<String> allPeriod = new CommonUtilsFunctions().getPreviousAndFutureOneYears();

            for (int i = 0; i < allPeriod.size(); i++) {
                String period = allPeriod.get(i);
                if (period.toLowerCase().contains(query)) {
                    filteredPeriod.add(period);
                }
            }
        } catch (DataAccessException e) {

        }
        return filteredPeriod;
    }
    // <editor-fold desc="getter and setter of the class">

    public String getPlantNameRequiredMessage() {
        return plantNameRequiredMessage;
    }

    public void setPlantNameRequiredMessage(String plantNameRequiredMessage) {
        this.plantNameRequiredMessage = plantNameRequiredMessage;
    }

    public String getCompanyNameRequiredMessage() {
        return companyNameRequiredMessage;
    }

    public void setCompanyNameRequiredMessage(String companyNameRequiredMessage) {
        this.companyNameRequiredMessage = companyNameRequiredMessage;
    }

    public String getLocationRequiredMessage() {
        return locationRequiredMessage;
    }

    public void setLocationRequiredMessage(String locationRequiredMessage) {
        this.locationRequiredMessage = locationRequiredMessage;
    }

    public String getStatusRequiredMessage() {
        return statusRequiredMessage;
    }

    public void setStatusRequiredMessage(String statusRequiredMessage) {
        this.statusRequiredMessage = statusRequiredMessage;
    }

    public String getLongitudeRequiredMessage() {
        return longitudeRequiredMessage;
    }

    public void setLongitudeRequiredMessage(String longitudeRequiredMessage) {
        this.longitudeRequiredMessage = longitudeRequiredMessage;
    }

    public String getLatitudeRequiredMessage() {
        return latitudeRequiredMessage;
    }

    public void setLatitudeRequiredMessage(String latitudeRequiredMessage) {
        this.latitudeRequiredMessage = latitudeRequiredMessage;
    }

    public Plant getUpdatePlant() {
        return updatePlant;
    }

    public void setUpdatePlant(Plant updatePlant) {
        this.updatePlant = updatePlant;
    }

    public String getUpdateActiveStatus() {
        return updateActiveStatus;
    }

    public void setUpdateActiveStatus(String updateActiveStatus) {
        this.updateActiveStatus = updateActiveStatus;
    }

    public String getTitle_plant_list_name() {
        return title_plant_list_name;
    }

    public void setTitle_plant_list_name(String title_plant_list_name) {
        this.title_plant_list_name = title_plant_list_name;
    }

    public List<Plant> getPlantList() {
        return plantList;
    }

    public void setPlantList(List<Plant> plantList) {
        this.plantList = plantList;
    }

    public List<Plant> getFilterPlantList() {
        return filterPlantList;
    }

    public void setFilterPlantList(List<Plant> filterPlantList) {
        this.filterPlantList = filterPlantList;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public List<CodeSetup> getCodeSetupActiveStatusList() {
        return codeSetupActiveStatusList;
    }

    public void setCodeSetupActiveStatusList(List<CodeSetup> codeSetupActiveStatusList) {
        this.codeSetupActiveStatusList = codeSetupActiveStatusList;
    }

    public List<CodeSetup> getCodeSetupLocationList() {
        return codeSetupLocationList;
    }

    public void setCodeSetupLocationList(List<CodeSetup> codeSetupList) {
        this.codeSetupLocationList = codeSetupList;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public String getPeriodRequiredMessage() {
        return periodRequiredMessage;
    }

    public void setPeriodRequiredMessage(String periodRequiredMessage) {
        this.periodRequiredMessage = periodRequiredMessage;
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public String getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(String updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Plant getPlant() {
        return plant;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public String getZoneRequiredMessage() {
        return zoneRequiredMessage;
    }

    public void setZoneRequiredMessage(String zoneRequiredMessage) {
        this.zoneRequiredMessage = zoneRequiredMessage;
    }

    // </editor-fold>
}
