/*
 * CapabilityEntryFormMB
 */
package com.panu.competitor.information.controller.bean;

import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.common.CurrentLoggedInUser;
import com.panu.competitor.information.common.InformationMessages;
import com.panu.competitor.information.common.MessagesCommon;
import com.panu.competitor.information.common.MessagesView;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Capabilities;
import com.panu.competitor.information.spring.service.ICapabilityService;
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
@Named("capabilityEntryFormMB")
@ViewScoped
public class CapabilityEntryFormMB implements Serializable {

    // <editor-fold desc="variables of the class">
    private static final long serialVersionUID = 1L;
    @Autowired
    private ICapabilityService capabilityService;
    private Capabilities capability = new Capabilities();
    private Capabilities updateCapability = new Capabilities();
    private String capabilityNameRequired;
    private String capabilityShortNameRequired;
    private final String title_capability_list_name = ConstantCommon.TITLE_CAPABILITY_LIST_NAME;
    private List<Capabilities> capabilityList;
    private List<Capabilities> filtercapability;
    private final Date date = new Date();
    private String tempname;
    private String tempshortname;
    private String duplicateCapabilityName;
    private String duplicateCapabilityShortName;
    private String capabilityNameEmptyMessage;
    private String capabilityShortNameEmptyMessage;
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
        try {
            this.capability = new Capabilities();
            this.updateCapability = new Capabilities();
            this.capabilityList = new ArrayList<Capabilities>();
            this.filtercapability = new ArrayList<Capabilities>();
            this.capabilityNameRequired = MessagesCommon.M0014_CAPABILITY_NAME_REQUIRED;
            this.capabilityShortNameRequired = MessagesCommon.M0015_CAPABILITY_SHORT_NAME_REQUIRED;
            capabilityList.addAll(capabilityService.findAllCapabilities());
            filtercapability.addAll(capabilityList);
        } catch (DataAccessException e) {

        }
    }

    /**
     * saveCompany
     *
     * The purpose of this method is to save new company to the database
     *
     * @throws com.panu.competitor.information.exception.CompetitorException
     */
    public void saveCapability() throws CompetitorException {
        try {
            messagetext = "";
            capability.setCreatedDate(date);
            capability.setIsDelete(ConstantCommon.STATUS_ISDELETE);
            capability.setCreatedUserId(new CurrentLoggedInUser().getLogInUserId());
            if (validateCapabilityInfo(capability, capabilityList)) {
                capabilityService.addCapability(capability);
                RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                MessagesView.info(MessagesCommon.M0017_CAPABILITY_SAVE_SUCCESSFULLY);
//                new InformationMessages().showMessageDialog(FacesMessage.SEVERITY_INFO, MessagesCommon.M0016_CAPABILITY_INFORMATION, MessagesCommon.M0017_CAPABILITY_SAVE_SUCCESSFULLY);
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
     * @param capability
     */
    public void editCapability(Capabilities capability) {
        this.updateCapability = capability;
        tempname = capability.getName();
        tempshortname = capability.getShortName();
    }

    /**
     * updateCapabilityInfo
     *
     * The purpose of this method is to update capability info to the database
     */
    public void updateCapabilityInfo() {
        try {
            isUpdateValidation = true;
            messagetext = "";
            if (tempname.trim().equals(updateCapability.getName().trim())
                    && tempshortname.trim().equals(updateCapability.getShortName().trim())) {
                RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                MessagesView.info(MessagesCommon.M0023_CAPABILITY_EDITED_SUCCESSFULLY);
//                new InformationMessages().showMessageDialog(FacesMessage.SEVERITY_INFO, MessagesCommon.M0016_CAPABILITY_INFORMATION, MessagesCommon.M0023_CAPABILITY_EDITED_SUCCESSFULLY);
            } else if (validateCapabilityInfo(updateCapability, capabilityList)) {
                updateCapability.setUpdatedDate(date);
                updateCapability.setUpdatedUserId(new CurrentLoggedInUser().getLogInUserId());
                capabilityService.updateCapability(updateCapability);
                RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                MessagesView.info(MessagesCommon.M0023_CAPABILITY_EDITED_SUCCESSFULLY);
//                new InformationMessages().showMessageDialog(FacesMessage.SEVERITY_INFO, MessagesCommon.M0016_CAPABILITY_INFORMATION, MessagesCommon.M0023_CAPABILITY_EDITED_SUCCESSFULLY);
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
     * @param capability
     */
    public void deleteCapability(Capabilities capability) {
        try {
            messagetext = "";
            capability.setIsDelete(ConstantCommon.STATUS_DELETE);
            capability.setUpdatedDate(date);
            capability.setUpdatedUserId(new CurrentLoggedInUser().getLogInUserId());
            capabilityService.updateCapability(capability);
            init();
            RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
            MessagesView.info(MessagesCommon.M0018_CAPABILITY_DELETED_SUCCESSFULLY);
//            new InformationMessages().showMessageDialog(FacesMessage.SEVERITY_INFO, MessagesCommon.M0016_CAPABILITY_INFORMATION, MessagesCommon.M0018_CAPABILITY_DELETED_SUCCESSFULLY);
        } catch (CompetitorException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Message: "));
        }
    }

    /**
     * validateCompanyInfo
     *
     * The purpose of this method is to validate capability
     *
     * @param capability
     * @param capabilityList
     * @return boolean returnValue
     */
    public boolean validateCapabilityInfo(Capabilities capability, List<Capabilities> capabilityList) {
        duplicateCapabilityName = "";
        duplicateCapabilityShortName = "";
        capabilityNameEmptyMessage = "";
        capabilityShortNameEmptyMessage = "";
        boolean validateValue = true;
        try {
            Map<String, String> validateIds = new HashMap<String, String>();
            if (!capability.getName().trim().isEmpty() && !capability.getShortName().trim().isEmpty()) {
                if (isUpdateValidation) {
                    capabilityList.remove(capability);
                    validateIds = capabilityService.validateCapabilityInfo(capability, capabilityList);
                } else {
                    validateIds = capabilityService.validateCapabilityInfo(capability, capabilityList);
                }
                for (Map.Entry<String, String> entrySet : validateIds.entrySet()) {
                    if (entrySet.getKey().equals("Name") && entrySet.getValue().equals("true")) {
                        duplicateCapabilityName = MessagesCommon.M0019_CAPABILITY_NAME_DUPLICATE;
                        validateValue = false;
                    }
                    if (entrySet.getKey().equals("ShortName") && entrySet.getValue().equals("true")) {
                        duplicateCapabilityShortName = MessagesCommon.M0020_CAPABILITY_SHORT_NAME_DUPLICATE;
                        validateValue = false;
                    }
                }
            } else {
                if (capability.getName().trim().isEmpty()) {
                    capabilityNameEmptyMessage = MessagesCommon.M0021_CAPABILITY_NAME_EMPTY;
                    validateValue = false;
                }
                if (capability.getShortName().trim().isEmpty()) {
                    capabilityShortNameEmptyMessage = MessagesCommon.M0022_CAPABILITY_SHORT_NAME_EMPTY;
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
        try {
            messagetext = "";
            if (!duplicateCapabilityName.equals("")) {
                messagetext += duplicateCapabilityName;
            }
            if (!duplicateCapabilityShortName.equals("")) {
                messagetext += duplicateCapabilityShortName;
            }

            if (!capabilityNameEmptyMessage.equals("")) {
                messagetext += capabilityNameEmptyMessage;
            }
            if (!capabilityShortNameEmptyMessage.equals("")) {
                messagetext += capabilityShortNameEmptyMessage;
            }
            RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
            MessagesView.error(messagetext);
        } catch (DataAccessException e) {

        }
    }

    /**
     * reset
     *
     * The purpose of this method is to reset the bean variables
     *
     */
    public void reset() {
        this.capability = new Capabilities();
        this.updateCapability = new Capabilities();
    }
    // <editor-fold desc="getter and setter of the class">

    public Capabilities getUpdateCapability() {
        return updateCapability;
    }

    public void setUpdateCapability(Capabilities updateCapability) {
        this.updateCapability = updateCapability;
    }

    public String getTitle_capability_list_name() {
        return title_capability_list_name;
    }

    public List<Capabilities> getCapabilityList() {
        return capabilityList;
    }

    public void setCapabilityList(List<Capabilities> capabilityList) {
        this.capabilityList = capabilityList;
    }

    public List<Capabilities> getFiltercapability() {
        return filtercapability;
    }

    public void setFiltercapability(List<Capabilities> filtercapability) {
        this.filtercapability = filtercapability;
    }

    public String getCapabilityNameRequired() {
        return capabilityNameRequired;
    }

    public void setCapabilityNameRequired(String capabilityNameRequired) {
        this.capabilityNameRequired = capabilityNameRequired;
    }

    public String getCapabilityShortNameRequired() {
        return capabilityShortNameRequired;
    }

    public void setCapabilityShortNameRequired(String capabilityShortNameRequired) {
        this.capabilityShortNameRequired = capabilityShortNameRequired;
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public Capabilities getCapability() {
        return capability;
    }

    public void setCapability(Capabilities capability) {
        this.capability = capability;
    }

     // </editor-fold>
}
