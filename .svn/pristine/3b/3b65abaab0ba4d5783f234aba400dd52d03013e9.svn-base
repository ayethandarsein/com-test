/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.controller.bean;

import com.panu.competitor.information.common.AttachmentHandler;
import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.common.CurrentLoggedInUser;
import com.panu.competitor.information.common.InformationMessages;
import com.panu.competitor.information.common.MessagesCommon;
import com.panu.competitor.information.common.MessagesView;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Attachment;
import com.panu.competitor.information.pojo.entity.Capabilities;
import com.panu.competitor.information.pojo.entity.CodeSetup;
import com.panu.competitor.information.pojo.entity.Company;
import com.panu.competitor.information.pojo.entity.Location;
import com.panu.competitor.information.pojo.entity.PlantInformation;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import com.panu.competitor.information.pojo.entity.Plant;
import com.panu.competitor.information.spring.service.IAttachmentService;
import com.panu.competitor.information.spring.service.ICapabilityService;
import com.panu.competitor.information.spring.service.ICodeSetupService;
import com.panu.competitor.information.spring.service.ICompanyService;
import com.panu.competitor.information.spring.service.ILocationService;
import com.panu.competitor.information.spring.service.IPlantInformationDetailService;
import com.panu.competitor.information.spring.service.IPlantInformationService;
import com.panu.competitor.information.spring.service.IPlantService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Htet Nanda Kyaw
 */
@Named("competitorEntryFormMB")
@ViewScoped
public class PlantInformationEntryFormMB implements Serializable {

    // <editor-fold desc="variables of the class">
    private static final long serialVersionUID = 1L;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IPlantInformationService competitorInfoService;
    @Autowired
    private IPlantInformationDetailService competitorInfoDetailService;
    @Autowired
    private ICodeSetupService codeSetupService;
    @Autowired
    private ILocationService locationService;
    @Autowired
    private ICapabilityService capabilityService;
    @Autowired
    private IPlantService plantService;
    @Autowired
    private IAttachmentService attachmentService;
    private List<Company> companyList;
    private List<PlantInformationDetail> competitorInformationDetailList;
    private List<String> capabilityList;
    List<Location> codeSetupLocationList;
    List<Capabilities> capabilityIdList;
    private PlantInformation competitorInformation;
    private PlantInformation plantInfoUpdate;
    private Date date;
    private String copyPeriod;
    private String focusLatLong;
    private MapModel complexModel;
    private int competitorInfoDetailIndex;
    private int competitorInfoAttachmentIndex;
    private List<List<Attachment>> attachmentDetailList;
    private PlantInformationDetail tempCompetitorInfoDetail;
    private List<Attachment> attachmentFileList;
    private boolean iscopy;
    private StreamedContent file;
    private String period;
    private String infoMessage;
    private List<PlantInformationDetail> removedPlantInfoDetailList;

    // </editor-fold>
    /**
     * init
     *
     * The purpose of this method is to initialize method of the view
     *
     */
    public void init() {
        reset();
        this.capabilityList = new ArrayList<String>();
        this.capabilityIdList = new ArrayList<Capabilities>();
        this.companyList = new ArrayList<Company>();
        this.codeSetupLocationList = new ArrayList<Location>();
        competitorInformation.setCompanyName("");
        date = new Date();
        codeSetupLocationList.addAll(locationService.findAllLocation());
        companyList.addAll(companyService.findAllCompany());
        capabilityIdList = capabilityService.findAllCapabilities();
        infoMessage = "";
    }

    public void onCompanySelect() throws CompetitorException, ParseException {
        competitorInformationDetailList = new ArrayList<PlantInformationDetail>();
        plantInfoUpdate = new PlantInformation();
        infoMessage = "";
        copyPeriod = "";
        if (!period.isEmpty()) {
            plantInfoUpdate = competitorInfoService.getPlantInfoByCompanyNameAndPeriod(competitorInformation.getCompanyName(), period);
            iscopy = false;
            long rowCount = competitorInfoDetailService.getPlantInfoDetailCount(competitorInformation.getCompanyName());
            if (rowCount == 0) {
                competitorInformationDetailList.addAll(competitorInfoDetailService.getAllCompetiorInfoDetailFromPlant(competitorInformation.getCompanyName(), period));
                infoMessage = "Showing all plant information";
            } else {
                competitorInformationDetailList.addAll(competitorInfoDetailService.getCompetiorInfoDetailByCompanyShortNameAndPeriod(competitorInformation.getCompanyName(), period));
                if (competitorInformationDetailList.isEmpty()) {
                    competitorInformationDetailList.addAll(competitorInfoDetailService.getPreviousCompetiorInfoDetailByCompanyIdAndPeriod(competitorInformation.getCompanyName(), period));
                    iscopy = true;
                    Date date = new SimpleDateFormat("MMM-yyyy").parse(period);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.MONTH, -1);
                    String previousPeriod = new SimpleDateFormat("MMM-yyyy").format(calendar.getTime());
                    infoMessage = "Copy from " + previousPeriod + " plant information";
                } else {
                    infoMessage = "Showing " + period + " plant information";
                }
            }
            competitorInformationDetailList.addAll(competitorInfoDetailService.getNewCompetitorInfoDetailByCompanyNameAndPlantPeriod(competitorInformation.getCompanyName(), period));
            competitorInformationDetailList = competitorInfoDetailService.removeDuplicateInfoDetail(competitorInformationDetailList);
            attachmentDetailList = new ArrayList<List<Attachment>>();
            for (int i = 0; i < competitorInformationDetailList.size(); i++) {
                List<Attachment> attachment = new ArrayList<Attachment>();
                attachmentDetailList.add(attachment);
            }
        }
    }

    public void onPeriodSelect() throws CompetitorException, ParseException {
        competitorInformationDetailList = new ArrayList<PlantInformationDetail>();
        plantInfoUpdate = new PlantInformation();
        infoMessage = "";
        if (!competitorInformation.getCompanyName().isEmpty()) {
            iscopy = false;
            plantInfoUpdate = competitorInfoService.getPlantInfoByCompanyNameAndPeriod(competitorInformation.getCompanyName(), period);
            long rowCount = competitorInfoDetailService.getPlantInfoDetailCount(competitorInformation.getCompanyName());
            if (rowCount == 0) {
                competitorInformationDetailList.addAll(competitorInfoDetailService.getAllCompetiorInfoDetailFromPlant(competitorInformation.getCompanyName(), period));
                infoMessage = "Showing all plant information";
            } else {
                competitorInformationDetailList.addAll(competitorInfoDetailService.getCompetiorInfoDetailByCompanyShortNameAndPeriod(competitorInformation.getCompanyName(), period));
                if (competitorInformationDetailList.isEmpty()) {
                    competitorInformationDetailList.addAll(competitorInfoDetailService.getPreviousCompetiorInfoDetailByCompanyIdAndPeriod(competitorInformation.getCompanyName(), period));
                    iscopy = true;
                    Date date = new SimpleDateFormat("MMM-yyyy").parse(period);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.MONTH, -1);
                    String previousPeriod = new SimpleDateFormat("MMM-yyyy").format(calendar.getTime());
                    infoMessage = "Copy from " + previousPeriod + " plant information";
                } else {
                    infoMessage = "Showing " + period + " plant information";
                }
            }
            competitorInformationDetailList.addAll(competitorInfoDetailService.getNewCompetitorInfoDetailByCompanyNameAndPlantPeriod(competitorInformation.getCompanyName(), period));
            competitorInformationDetailList = competitorInfoDetailService.removeDuplicateInfoDetail(competitorInformationDetailList);
            attachmentDetailList = new ArrayList<List<Attachment>>();
            for (int i = 0; i < competitorInformationDetailList.size(); i++) {
                List<Attachment> attachment = new ArrayList<Attachment>();
                attachmentDetailList.add(attachment);
            }
        }

    }

    public void copyPeriodInfoDetail() throws CompetitorException {
        iscopy = true;
        infoMessage = "";
        if (period != null && !period.isEmpty()) {
            competitorInformationDetailList = new ArrayList<PlantInformationDetail>();
            competitorInformationDetailList.addAll(competitorInfoDetailService.getCompetiorInfoDetailByCompanyShortNameAndPeriod(competitorInformation.getCompanyName(), copyPeriod));
            competitorInformationDetailList.addAll(competitorInfoDetailService.getNewCompetitorInfoDetailByCompanyNameAndPlantPeriod(competitorInformation.getCompanyName(), period));
            competitorInformationDetailList = competitorInfoDetailService.removeDuplicateInfoDetail(competitorInformationDetailList);
            infoMessage = "Copy from " + copyPeriod + " plant information";
            attachmentDetailList = new ArrayList<List<Attachment>>();
            for (int i = 0; i < competitorInformationDetailList.size(); i++) {
                List<Attachment> attachment = new ArrayList<Attachment>();
                attachmentDetailList.add(attachment);
            }
            if (competitorInformationDetailList.isEmpty()) {
                MessagesView.error(MessagesCommon.M0049_NO_PLANT_TO_COPY);
            }
        } else {
            MessagesView.error(MessagesCommon.M0032_PERIOD_NAMED_REQUIRED);
        }

    }

    public String getLocationNameById(int id) {
        String name = "-";
        for (Location codeSetup : codeSetupLocationList) {
            if (codeSetup.getLocationId() == id) {
                name = codeSetup.getName();
            }
        }
        return name;
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (attachmentFileList.size() < 5) {
            Attachment attachment = new Attachment();
            attachment = new AttachmentHandler().setAttachmentData(event.getFile(), ConstantCommon.COMPETITOR_INFO_DETAIL_ATTACHEMNT);
            attachmentFileList.add(attachment);
        } else {
            MessagesView.error("Exceed file limit of 5");
        }
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            String column_name = event.getColumn().getHeaderText();
            if (column_name.equalsIgnoreCase("No. of Trucks")) {
                int newValue = (Integer) event.getNewValue();
                int index = event.getRowIndex();
                int capacity = newValue * 9;
                competitorInformationDetailList.get(index).setPlantCapacity(capacity);
            }
        } catch (Exception e) {

        }
    }

    public void removeAttachment(Attachment attachment) {
        attachmentFileList.remove(attachment);
    }

    /**
     * downloadAttachment download attachments
     *
     * @param attachment
     */
    public void downloadAttachment(Attachment attachment) {
        if (attachment != null) {
            InputStream stream = new ByteArrayInputStream(attachment.getUploadedFile());
            String extension = attachment.getAttachedFileName().substring(attachment.getAttachedFileName().lastIndexOf("."));
            file = new DefaultStreamedContent(stream, extension, attachment.getAttachedFileName());
        } else {
            MessagesView.fatal(MessagesCommon.M0016_FILEDOWNLOAD_FILEDOESNOTEXIST);
        }
    }

    public void saveAttachment() {
        attachmentDetailList.set(competitorInfoAttachmentIndex, attachmentFileList);
    }

    public String zonetoName(int id) {
        String zoneName = "";
        if (id != 0) {
            CodeSetup codeSetup = new CodeSetup();
            codeSetup = codeSetupService.findCodeSetupByCodeSetupId(id);
            zoneName = codeSetup.getCodeSetupName();
        }
        return zoneName;
    }

    public String integertoString(int value) {
        String stringValue = "";
        if (value != 0) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            stringValue = numberFormat.format(value);
        }
        return stringValue;
    }

    public void bindMapData(int id) {
        Plant plant = new Plant();
        complexModel = new DefaultMapModel();
        plant = plantService.getPlantByPlantId(id);
        focusLatLong = plant.getLatitude() + "," + plant.getLongitude();

        LatLng coord = new LatLng(plant.getLatitude().doubleValue(), plant.getLongitude().doubleValue());
        Marker marker = new Marker(coord, plant.getPlantName() + " (" + plant.getLatitude().doubleValue() + "," + plant.getLongitude().doubleValue() + ")");
        complexModel.addOverlay(marker);

    }

    public void bindAttachments(int index, PlantInformationDetail competitorInfoDetail) {
        attachmentFileList = new ArrayList<Attachment>();
        competitorInfoAttachmentIndex = index;
        attachmentFileList.addAll(attachmentService.getAttachmentByCompetitorInfoDetialID(competitorInfoDetail.getCompetitorDetailId()));
        for (int i = 0; i < attachmentDetailList.size(); i++) {
            if (i == competitorInfoAttachmentIndex) {
                attachmentFileList.addAll(attachmentDetailList.get(i));
            }
        }

    }

    public void capabilitySelection(int index, PlantInformationDetail competitorInformationDetail) {
        List<String> capabilityIds = new ArrayList<String>();
        competitorInfoDetailIndex = index;
        tempCompetitorInfoDetail = new PlantInformationDetail();
        tempCompetitorInfoDetail = competitorInformationDetail;
        capabilityList = new ArrayList<String>();
        if (competitorInformationDetail.getCapabilities() != null && !competitorInformationDetail.getCapabilities().isEmpty()) {
            capabilityIds = Arrays.asList(competitorInformationDetail.getCapabilities().split(","));
            for (int i = 0; i < capabilityIds.size(); i++) {
                if (!"".equals((capabilityIds.get(i).trim()))) {
                    capabilityList.add(i, capabilityService.getCapabilitesByCapabilyIds(capabilityIds.get(i).trim()).getShortName());
                }
            }
        }
    }

    public void selectCapabilities() {
        String capability = "";
        for (String capabilities : capabilityList) {
            capability += capabilities + ",";
        }
        if (capability.length() > 0) {
            capability = capability.substring(0, capability.length() - 1);
        }
        tempCompetitorInfoDetail.setCapabilities(capability);
        competitorInformationDetailList.set(competitorInfoDetailIndex, tempCompetitorInfoDetail);
    }

    public void removeCompetitorInfoDetail(PlantInformationDetail competitorInformationDetail) {
        if (competitorInformationDetailList.indexOf(competitorInformationDetail) != -1) {
            attachmentDetailList.remove(competitorInformationDetailList.indexOf(competitorInformationDetail));
        }
        removedPlantInfoDetailList.add(competitorInformationDetail);
        competitorInformationDetailList.remove(competitorInformationDetail);
    }

    /**
     * saveCompetitorInfo
     *
     * The purpose of this method is to save competitor information
     *
     * @throws com.panu.competitor.information.exception.CompetitorException
     * @throws java.text.ParseException
     */
    public void saveCompetitorInfo() throws CompetitorException, ParseException {
        try {
            if (competitorInformationDetailList.size() > 0) {
                if (plantInfoUpdate.getCompanyName() != null && !plantInfoUpdate.getCompanyName().equals("")) {
                    competitorInformation = new PlantInformation();
                    competitorInformation = plantInfoUpdate;
                    for (PlantInformationDetail plantInfoDetail : removedPlantInfoDetailList) {
                        plantInfoDetail.setIsDelete(ConstantCommon.STATUS_DELETE);
                        competitorInfoDetailService.updateCompetitorInfoDetail(plantInfoDetail);
                    }
                }
                competitorInformation.setCreatedUserId(new CurrentLoggedInUser().getLogInUserId());
                competitorInformation.setPeriod(new SimpleDateFormat("MMM-yyyy").parse(period));
                competitorInformation.setCreatedDate(date);
                competitorInformation.setIsDelete(ConstantCommon.STATUS_ISDELETE);
                competitorInfoService.addCompetitorInfo(competitorInformation);
                for (int i = 0; i < competitorInformationDetailList.size(); i++) {
                    if (iscopy) {
                        competitorInformationDetailList.get(i).setCompetitorDetailId(0);
                    }
                    competitorInformationDetailList.get(i).setCompetitorInfoId(competitorInformation.getCompetitorInfoId());
                    competitorInformationDetailList.get(i).setIsDelete(ConstantCommon.STATUS_ISDELETE);
                    competitorInformationDetailList.get(i).setCreatedUserId(new CurrentLoggedInUser().getLogInUserId());
                    competitorInformationDetailList.get(i).setCreatedDate(date);
                    competitorInfoDetailService.updateCompetitorInfoDetail(competitorInformationDetailList.get(i));
                    for (int j = 0; j < attachmentDetailList.get(i).size(); j++) {
                        attachmentDetailList.get(i).get(j).setCompetitorInfoDetailId(competitorInformationDetailList.get(i).getCompetitorDetailId());
                        if (iscopy) {
                            attachmentDetailList.get(i).get(j).setAttachmentId(0);
                        }
                        attachmentService.updateAttachment(attachmentDetailList.get(i).get(j));
                    }
                }
                reset();
                RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
                MessagesView.info(MessagesCommon.M0047_COMPETITOR_INFORMATION_SAVE_SUCCESSFULLY);
            } else {
                MessagesView.error(MessagesCommon.M0047_COMPETITOR_INFORMATION_NO_DATA);
            }
        } catch (Exception e) {

        }
    }

    public String getPlantNameById(int id) {
        String name = "";
        name = plantService.getPlantByPlantId(id).getPlantName();
        return name;
    }

    /**
     * reset
     *
     * The purpose of this method is to reset the bean variables
     *
     */
    public void reset() {
        this.competitorInformationDetailList = new ArrayList<PlantInformationDetail>();
        this.competitorInformation = new PlantInformation();
        this.attachmentFileList = new ArrayList<Attachment>();
        this.period = new SimpleDateFormat("MMM-yyyy").format(new Date());
        this.attachmentDetailList = new ArrayList<List<Attachment>>();
        this.plantInfoUpdate = new PlantInformation();
        this.copyPeriod = "";
        this.removedPlantInfoDetailList = new ArrayList<PlantInformationDetail>();
        infoMessage = "";
    }

    // <editor-fold desc="getter and setter of the class">
    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public List<PlantInformationDetail> getCompetitorInformationDetailList() {
        return competitorInformationDetailList;
    }

    public void setCompetitorInformationDetailList(List<PlantInformationDetail> competitorInformationDetailList) {
        this.competitorInformationDetailList = competitorInformationDetailList;
    }

    public List<String> getCapabilityList() {
        return capabilityList;
    }

    public void setCapabilityList(List<String> capabilityList) {
        this.capabilityList = capabilityList;
    }

    public String getCopyPeriod() {
        return copyPeriod;
    }

    public void setCopyPeriod(String copyPeriod) {
        this.copyPeriod = copyPeriod;
    }

    public List<Capabilities> getCapabilityIdList() {
        return capabilityIdList;
    }

    public void setCapabilityIdList(List<Capabilities> capabilities) {
        this.capabilityIdList = capabilities;
    }

    public List<Attachment> getAttachmentFileList() {
        return attachmentFileList;
    }

    public void setAttachmentFileList(List<Attachment> attachmentFileList) {
        this.attachmentFileList = attachmentFileList;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public PlantInformation getCompetitorInformation() {
        return competitorInformation;
    }

    public void setCompetitorInformation(PlantInformation competitorInformation) {
        this.competitorInformation = competitorInformation;
    }

    // </editor-fold>
    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }
}
