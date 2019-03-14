/*
 * LocationEntryFormMB
 */
package com.panu.competitor.information.controller.bean;

import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.common.CurrentLoggedInUser;
import com.panu.competitor.information.common.MessagesCommon;
import com.panu.competitor.information.common.MessagesView;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Location;
import com.panu.competitor.information.spring.service.ILocationService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Named("locationEntryFormMB")
@ViewScoped
public class LocationEntryFormMB implements Serializable {

    // <editor-fold desc="variables of the class">
    private static final long serialVersionUID = 1L;
    @Autowired
    private ILocationService locationService;
    private Location location = new Location();
    private Location updateLocation = new Location();
    private String locationNameRequired;
    private final String title_location_list_name = ConstantCommon.TITLE_CAPABILITY_LIST_NAME;
    private List<Location> locationList;
    private List<Location> filterlocation;
    private final Date date = new Date();
    private String tempname;
    private String duplicateLocationName;
    private String locationNameEmptyMessage;
    private String messagetext;
    private boolean isUpdateValidation;
    // </editor-fold>

    /**
     * init
     *
     * The purpose of this method is to initialize method of the view
     *
     */
    public void init() {
        this.location = new Location();
        this.updateLocation = new Location();
        this.locationList = new ArrayList<Location>();
        this.filterlocation = new ArrayList<Location>();
        this.locationNameRequired = MessagesCommon.M0051_LOCATION_NAME_REQUIRED;
        locationList.addAll(locationService.findAllLocation());
        filterlocation.addAll(locationList);
    }

    /**
     * saveCompany
     *
     * The purpose of this method is to save new company to the database
     *
     * @throws com.panu.competitor.information.exception.CompetitorException
     */
    public void saveLocation() throws CompetitorException {
        try {
            messagetext = "";
            location.setCreatedDate(date);
            location.setIsDelete(ConstantCommon.STATUS_ISDELETE);
            location.setCreatedUserId(new CurrentLoggedInUser().getLogInUserId());
            if (validateLocationInfo(location, locationList)) {
                locationService.addLocation(location);
                RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                MessagesView.info(MessagesCommon.M0053_LOCATION_SAVE_SUCCESSFULLY);
            } else {
                showWarningMessages();
            }
            init();
        } catch (CompetitorException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Message: "));
        }
    }

    /**
     * editCompany
     *
     * The purpose of this method is to copy row from data table and place it in
     *
     * @param location
     */
    public void editLocation(Location location) {
        this.updateLocation = location;
        tempname = location.getName();
    }

    /**
     * updateLocationInfo
     *
     * The purpose of this method is to update location info to the database
     */
    public void updateLocationInfo() {
        try {
            isUpdateValidation = true;
            messagetext = "";
            if (tempname.trim().equals(updateLocation.getName().trim())) {
                RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                MessagesView.info(MessagesCommon.M0053_LOCATION_EDITED_SUCCESSFULLY);
            } else if (validateLocationInfo(updateLocation, locationList)) {
                updateLocation.setUpdatedDate(date);
                updateLocation.setUpdatedUserId(new CurrentLoggedInUser().getLogInUserId());
                locationService.updateLocation(updateLocation);
                RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                MessagesView.info(MessagesCommon.M0053_LOCATION_EDITED_SUCCESSFULLY);
            } else {
                showWarningMessages();
            }
            init();
            isUpdateValidation = false;
        } catch (CompetitorException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Message: "));
        }
    }

    /**
     * deleteCompany
     *
     * The purpose of this method is to update delete flag to the database
     *
     * @param location
     */
    public void deleteLocation(Location location) {
        try {
            messagetext = "";
            location.setIsDelete(ConstantCommon.STATUS_DELETE);
            location.setUpdatedDate(date);
            location.setUpdatedUserId(new CurrentLoggedInUser().getLogInUserId());
            locationService.updateLocation(location);
            init();
            RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
            MessagesView.info(MessagesCommon.M0053_LOCATION_DELETE_SUCCESSFULLY);
        } catch (CompetitorException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Message: "));
        }
    }

    /**
     * validateCompanyInfo
     *
     * The purpose of this method is to validate location
     *
     * @param location
     * @param locationList
     * @return boolean returnValue
     */
    public boolean validateLocationInfo(Location location, List<Location> locationList) {
        duplicateLocationName = "";
        locationNameEmptyMessage = "";
        boolean validateValue = true;
        try {
            Map<String, String> validateIds = new HashMap<String, String>();
            if (!location.getName().trim().isEmpty()) {
                if (isUpdateValidation) {
                    locationList.remove(location);
                    validateIds = locationService.validateLocationInfo(location, locationList);
                } else {
                    validateIds = locationService.validateLocationInfo(location, locationList);
                }
                for (Map.Entry<String, String> entrySet : validateIds.entrySet()) {
                    if (entrySet.getKey().equals("Name") && entrySet.getValue().equals("true")) {
                        duplicateLocationName = MessagesCommon.M0053_LOCATION_DUPLICATE;
                        validateValue = false;
                    }

                }
            } else {
                if (location.getName().trim().isEmpty()) {
                    locationNameEmptyMessage = MessagesCommon.M0052_LOCATION_NAME_EMPTY;
                    validateValue = false;
                }
            }
        } catch (DataAccessException e) {

        }
        return validateValue;
    }

    /**
     * showWarningMessages
     *
     * The purpose of this method is to show warning message to user
     *
     */
    public void showWarningMessages() {
        messagetext = "";
        if (!duplicateLocationName.equals("")) {
            messagetext += duplicateLocationName;
        }

        if (!locationNameEmptyMessage.equals("")) {
            messagetext += locationNameEmptyMessage;
        }

        RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
        MessagesView.error(messagetext);
    }

    /**
     * reset
     *
     * The purpose of this method is to reset the bean variables
     *
     */
    public void reset() {
        this.location = new Location();
        this.updateLocation = new Location();
    }
    // <editor-fold desc="getter and setter of the class">

    public Location getUpdateLocation() {
        return updateLocation;
    }

    public void setUpdateLocation(Location updateLocation) {
        this.updateLocation = updateLocation;
    }

    public String getTitle_location_list_name() {
        return title_location_list_name;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Location> getFilterlocation() {
        return filterlocation;
    }

    public void setFilterlocation(List<Location> filterlocation) {
        this.filterlocation = filterlocation;
    }

    public String getLocationNameRequired() {
        return locationNameRequired;
    }

    public void setLocationNameRequired(String locationNameRequired) {
        this.locationNameRequired = locationNameRequired;
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

     // </editor-fold>
}
