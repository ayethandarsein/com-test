/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.controller.bean;

import com.google.gson.Gson;
import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.dto.CapabilitiesNamesDTO;
import com.panu.competitor.information.dto.DataLabels;
import com.panu.competitor.information.dto.PlantAndCapacityPieData;
import com.panu.competitor.information.dto.Zone;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.CodeSetup;
import com.panu.competitor.information.pojo.entity.Company;
import com.panu.competitor.information.pojo.entity.LegalIssue;
import com.panu.competitor.information.pojo.entity.Plant;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import com.panu.competitor.information.pojo.entity.PlantInformationTable;
import com.panu.competitor.information.spring.service.ICodeSetupService;
import com.panu.competitor.information.spring.service.ICompanyService;
import com.panu.competitor.information.spring.service.ILegalIssueService;
import com.panu.competitor.information.spring.service.ILocationService;
import com.panu.competitor.information.spring.service.IPlantInformationDetailService;
import com.panu.competitor.information.spring.service.IPlantService;
import java.awt.Color;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Htet Nanda Kyaw
 */
@Named("companyDetailChartMB")
@ViewScoped
public class CompanyDetailChartMB implements Serializable {

    // <editor-fold desc="variables of the class">
    @Autowired
    private IPlantInformationDetailService plantInfoDetailService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private ICodeSetupService codeSetupService;
    @Autowired
    private ILocationService locationService;
    @Autowired
    private ILegalIssueService legalIssueService;
    @Autowired
    private IPlantService plantService;
    private int valueDiv;
    private String infoDetailId;
    private int hiddenSliceId;
    private List<PlantInformationDetail> plantInfoDetailList;
    private List<PlantInformationDetail> plantInfoDetailPieList;
    private List<PlantInformationDetail> plantInfoTempList;
    private PlantInformationDetail plantInformationDetail;
    private List<PlantAndCapacityPieData> dataList;
    private String data;
    private List<String> companyIDandPeriodList;
    private int companyId;
    private String period;
    private String chartName;
    private String pieType;
    private boolean noOfPlantRendered;
    private boolean truckRendered;
    private boolean tolRendered;
    private boolean capabilityRendered;
    private boolean volumeRendered;
    private boolean truckPieRendered;
    private boolean capacityPieRendered;
    private List<Zone> zonePlantList;
    private List<Zone> zoneTruckList;
    private List<Zone> zoneVolumeList;
    private List<Zone> zoneCapacityList;
    private List<String> capabilityList;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
    private List<CapabilitiesNamesDTO> capabilityNameList;
    private List<LegalIssue> legalIssueList;
    private List<LegalIssue> legalIssueListForPrint;
    private String focusLatLong;
    private MapModel complexModel;
    private String color;
    private Company company;
    private List<PlantInformationDetail> filteredplantInfoDetailList;
    private List<Color> colorList;
    private List<PlantInformationDetail> plantInfoDetailTOLList;
    private List<Zone> tolZonePlantList;
    private List<String> zoneList;
    private List<CodeSetup> codeSetupZoneList;
    private int plantId;
    private int location;
    private char hdbapprove;
    private String capabilities;
    private String hdp = "";
    List<PlantInformationTable> lstPitTable = new ArrayList<PlantInformationTable>();
    List<PlantInformationTable> listforPlant = new ArrayList<PlantInformationTable>();
    List<PlantInformationTable> listforVolume = new ArrayList<PlantInformationTable>();
    List<PlantInformationTable> listforTotal = new ArrayList<PlantInformationTable>();
    List<PlantInformationTable> listforTruckAndCapacity = new ArrayList<PlantInformationTable>();
    List<PlantInformationTable> listforCapabiliteAndOthers = new ArrayList<PlantInformationTable>();
    List<PlantInformationTable> filteredLstPitTable = new ArrayList<PlantInformationTable>();
    // </editor-fold>

    PlantInformationTable plantInfoTable;

    public void init() throws CompetitorException {
        try {
            hdp = "";
            valueDiv = 12;
            noOfPlantRendered = false;
            truckRendered = false;
            volumeRendered = false;
            tolRendered = false;
            capabilityRendered = false;
            zoneTruckList = new ArrayList<Zone>();
            zoneVolumeList = new ArrayList<Zone>();
            zonePlantList = new ArrayList<Zone>();
            zoneCapacityList = new ArrayList<Zone>();
            plantInfoDetailList = new ArrayList<PlantInformationDetail>();
            capabilityNameList = new ArrayList<CapabilitiesNamesDTO>();
            plantInfoDetailPieList = new ArrayList<PlantInformationDetail>();
            plantInfoTempList = new ArrayList<PlantInformationDetail>();
            capabilityList = new ArrayList<String>();
            legalIssueList = new ArrayList<LegalIssue>();
            legalIssueListForPrint = new ArrayList<LegalIssue>();
            dataList = new ArrayList<PlantAndCapacityPieData>();
            companyIDandPeriodList = new ArrayList<String>();
            colorList = new ArrayList<Color>();
            company = new Company();
            companyIDandPeriodList = Arrays.asList(infoDetailId.split("_"));
            companyId = Integer.parseInt(companyIDandPeriodList.get(0));
            company = companyService.findCompanyByCompanyId(companyId);
            legalIssueList = legalIssueService.findLegalIssueListByCompanyId(companyId);
            legalIssueListForPrint = legalIssueService.findLegalIssueListByCompanyIdForPrint(companyId);
            period = companyIDandPeriodList.get(1);
            pieType = companyIDandPeriodList.get(2);
            chartName = company.getCompanyCode();
            plantInfoTempList.addAll(plantInfoDetailService.getCompetiorInfoDetailByCompanyIdAndPeriod(companyId, period));
            plantInfoDetailList.addAll(plantInfoDetailService.getCompetiorInfoDetailByCompanyIdAndPeriod(companyId, period));
            plantInfoDetailPieList.addAll(plantInfoDetailService.RemoveDuplicateInfoDetailAndAddValues(plantInfoTempList));
            dataList = setCompanyDetailPieData(plantInfoDetailPieList.get(0));
            capabilityList = Arrays.asList(plantInfoDetailPieList.get(0).getCapabilities().split(","));
            capabilityList = removeDuplicateCapability(capabilityList);
            capabilityNameList = divideArrayIntoParts(ConstantCommon.DIVIDED_SIZE, capabilityList);
            color = "#" + company.getColor();
            data = new Gson().toJson(dataList);
            chartName = new Gson().toJson(chartName);
            colorList = getColorBands(hex2Rgb(color), 5);
            color = new Gson().toJson(rgb2Hex(colorList));
            zoneList = new ArrayList<String>();
            codeSetupZoneList = new ArrayList<CodeSetup>();
            codeSetupZoneList.addAll(codeSetupService.findAllCodeSetupByCodeSetupType(ConstantCommon.ZONE_CODESETUP_TYPE));
            for (CodeSetup cs : codeSetupZoneList) {
                zoneList.add(cs.getCodeSetupName());
            }
        } catch (DataAccessException e) {
        }
    }

    public List<CapabilitiesNamesDTO> divideArrayIntoParts(int size, List<String> capabilityNameList) {
        List<CapabilitiesNamesDTO> dividedList = new ArrayList<CapabilitiesNamesDTO>();
        try {
            CapabilitiesNamesDTO capabilityDTO;
            for (int start = 0; start < capabilityNameList.size(); start += size) {
                int end = Math.min(start + size, capabilityNameList.size());
                List<String> sublist = capabilityNameList.subList(start, end);
                capabilityDTO = new CapabilitiesNamesDTO();
                for (int i = 0; i < sublist.size(); i++) {
                    if (sublist.indexOf(sublist.get(i)) == 0) {
                        capabilityDTO.setColumn1(sublist.get(i));
                    } else if (sublist.indexOf(sublist.get(i)) == 1) {
                        capabilityDTO.setColumn2(sublist.get(i));
                    } else if (sublist.indexOf(sublist.get(i)) == 2) {
                        capabilityDTO.setColumn3(sublist.get(i));
                    }
                }
                dividedList.add(capabilityDTO);

            }
        } catch (DataAccessException e) {

        }
        return dividedList;
    }

    public List<String> removeDuplicateCapability(List<String> capabilityList) {
        List<String> newList = new ArrayList<String>();
        try {
            for (String element : capabilityList) {
                // If this element is not present in newList 
                // then add it 
                if (!newList.contains(element)) {
                    newList.add(element);
                }
            }
        } catch (DataAccessException e) {
        }
        return newList;
    }

    public boolean mark(char mark) {
        if (mark == 'Y') {
            return true;
        }
        return false;
    }

    public boolean header(char mark) {
        if (mark == 'P') {
            hdp = "HDB Approved?";
            return true;
        }
        hdp = "";
        return false;
    }

    public String checkHeader(int no) {
        String value = "";
        if (no == 001) {
            value = "Year of Lodge:";
        } else {
            value = String.valueOf(no);
        }
        return value;
    }

    public String checkHeaderForTruck(int no) {
        String value = "";
        if (no == 001) {
            value = "No of Trucks";
        } else {
            value = String.valueOf(no);
        }
        return value;
    }

    public String checkHeaderForCapacity(int no) {
        String value = "";
        if (no == 021) {
            value = "Capacity";
        } else {
            value = String.valueOf(no);
        }
        return value;
    }

    public String getLocationNameById(int id) {
        String name = "";
        try {
            name = locationService.findLocationByLocationId(id).getName();
        } catch (DataAccessException e) {

        }
        return name;
    }

    public String zonetoName(int id) {
        String zoneName = "";
        try {
            zoneName = codeSetupService.findCodeSetupByCodeSetupId(id).getCodeSetupName();
        } catch (DataAccessException e) {

        }
        return zoneName;
    }

    public List<PlantAndCapacityPieData> setCompanyDetailPieData(PlantInformationDetail plantInformationDetail) {
        List<PlantAndCapacityPieData> pieData = new ArrayList<PlantAndCapacityPieData>();
        try {
            String formattedVolume = numberFormat.format(plantInformationDetail.getVolumePerMonth());
            String formattedPlantNo = numberFormat.format(plantInformationDetail.getNoOfPlant());

            pieData.add(new PlantAndCapacityPieData(0, "<i class=\"fa fa-map-marker fa-3x\" style=\"margin-top: -0.3em;\"></i>TOL DETAILS", 100,
                    new DataLabels(-90, "<div style=\"float:right\"><i class=\"fa fa-map-marker fa-3x\" ></i></div><br><br><div style=\"text-align:center;letter-spacing :3px;font-size:13px; font-weight: 900;margin-top: 1.8em;\">TOL DETAILS</div>", true)));

            pieData.add(new PlantAndCapacityPieData(1, "<i class=\"fa fa-bar-chart fa-2x\"></i>VOLUME m³ PERMONTH" + plantInformationDetail.getVolumePerMonth(), 100,
                    new DataLabels(-70, "<br><br><div style=\"text-align:center;font-size:13px; font-weight: 900;\"><font style=\"letter-spacing :3px;\">VOLUME m³<br><span style=\"white-space: pre\">PER MONTH</span></font></div><br><span style=\"font-size:1.9em; font-weight:bold;white-space: pre\">" + formattedVolume + " </span><i class=\"fa fa-bar-chart fa-2x\" style=\"float:right;margin-right: -1.2em;\"></i>", true)));

            pieData.add(new PlantAndCapacityPieData(2, "<i class=\"fa fa-industry fa-2x\" style=\"margin-left: 0.4em;\"></i>NO OF PLANTS " + plantInformationDetail.getNoOfPlant(), 100,
                    new DataLabels(-75, "<div style=\"text-align:center;letter-spacing :3px;font-size:13px; font-weight: 900;\"><font style=\"letter-spacing :3px;\"><br><br>NO OF<br>PLANTS<br></font></div><br><span style=\"font-size:2em; font-weight:bold;horizontal-align:center;white-space: pre;margin-left: 0.4em;\">" + formattedPlantNo + "</span><br><i class=\"fa fa-industry fa-2x\" style=\"margin-left: 0.4em;margin-top: 0.5em;\"></i><br>", true)));

            pieData.add(new PlantAndCapacityPieData(3, "<i class=\"fa fa-truck fa-3x\"></i>TRUCKING CAPACITY " + determineCapacityOrTruck(plantInformationDetail), 100,
                    new DataLabels(-115, "<br><br><div class=\"ui-g\"><div class=\"ui-g-6\" style=\"margin-top: 1em;margin-left: -1em;\"><i class=\"fa fa-truck fa-3x\"></i></div><div class=\"ui-g-6\" style=\"margin-top: -0.8em;margin-right: -1.5em;margin-left: 0.8em;\"><div style=\"text-align:center;font-size:13px;\"><span style=\"margin: auto 0;text-align:center;\"><font style=\"letter-spacing :3px;\">TRUCKING<br>CAPACITY</font></span></div><br><span style=\"font-size:2em; font-weight:bold;margin-left: 0.2em;margin-bottom: -1.3em;\">" + determineCapacityOrTruck(plantInformationDetail) + "</span><div></div>", true)));

            pieData.add(new PlantAndCapacityPieData(4, "<i class=\"fa fa-gears fa-3x\" style=\"margin-left: 0.01em;margin-top: -0.3em;\"></i>CAPABILITIES & OTHERS ", 100,
                    new DataLabels(-70, "<i class=\"fa fa-gears fa-3x\" style=\"margin-left: 0.01em;margin-top: -0.3em;\"></i><div style=\"margin: 6px 0px 0px 20px;letter-spacing :3px;font-size:13px; font-weight: 900;margin-top: 1.3em;margin-left: 2em;\">CAPABILITIES<br>& OTHERS</div>", true)));
        } catch (DataAccessException e) {

        }
        return pieData;
    }

    public String determineCapacityOrTruck(PlantInformationDetail plantInformationDetail) {
        if (pieType.equalsIgnoreCase(ConstantCommon.PLANT_PIE)) {
            return numberFormat.format(plantInformationDetail.getPlantCapacity());
        } else {
            return numberFormat.format(plantInformationDetail.getNoOfTruck());
        }
    }

    public void bindMapData(int id) {
        try {
            Plant plant = new Plant();
            complexModel = new DefaultMapModel();
            plant = plantService.getPlantByPlantId(id);
            focusLatLong = plant.getLatitude() + "," + plant.getLongitude();
            LatLng coord = new LatLng(plant.getLatitude().doubleValue(), plant.getLongitude().doubleValue());
            Marker marker = new Marker(coord, plant.getPlantName() + " (" + plant.getLatitude().doubleValue() + "," + plant.getLongitude().doubleValue() + ")");
            complexModel.addOverlay(marker);
        } catch (DataAccessException e) {

        }
    }

    public void showTable() throws ParseException {
        try {
            tolRendered = false;
            noOfPlantRendered = false;
            truckRendered = false;
            volumeRendered = false;
            capabilityRendered = false;
            truckPieRendered = false;
            capacityPieRendered = false;
            this.valueDiv = 6;
            if (hiddenSliceId == 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, 6);
                String periodto6months = new SimpleDateFormat("MMM-yyyy").format(calendar.getTime());
                tolRendered = true;
                plantInfoDetailTOLList = plantInfoDetailService.getCompetiorInfoDetailByLeasePeriodTo(companyId, periodto6months);
                setTolZoneData(plantInfoDetailTOLList);
                PlantInformationTable volumeTable;
                listforTotal = new ArrayList<PlantInformationTable>();
                lstPitTable = new ArrayList<PlantInformationTable>();
                volumeTable = new PlantInformationTable(011, "Location", "Zone", 'P', "Lease Period", "Supported by");
                listforTotal.add(volumeTable);
                for (PlantInformationDetail pid : plantInfoDetailTOLList) {
                    String location = getLocationNameById(pid.getLocation());
                    String zone = zonetoName(pid.getZone());
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM YYYY");

                    String leasePeriod = "";
                    if (pid.getLeasePeriodFromDate() != null && pid.getLeasePeriodToDate() != null) {
                        leasePeriod = sdf.format(pid.getLeasePeriodFromDate()) + "-" + sdf.format(pid.getLeasePeriodToDate());
                    }

                    PlantInformationTable plantInfoTable = new PlantInformationTable(pid.getPlantId(), location, zone, pid.getHdbapprove(), leasePeriod, pid.getSupportedBy());
                    lstPitTable.add(plantInfoTable);
                    volumeTable = new PlantInformationTable(pid.getPlantId(), location, zone, pid.getHdbapprove(), leasePeriod, pid.getSupportedBy());
                    listforTotal.add(volumeTable);
                }
                this.dataList = recreatePieColor(dataList, hiddenSliceId);
            } else if (hiddenSliceId == 1) {
                PlantInformationTable volumetable;
                volumeRendered = true;
                setZoneData(plantInfoDetailList);
                listforVolume = new ArrayList<PlantInformationTable>();
                lstPitTable = new ArrayList<PlantInformationTable>();
                volumetable = new PlantInformationTable(011, "Location", "Zone", 007, 0);
                listforVolume.add(volumetable);
                for (PlantInformationDetail pid : plantInfoDetailList) {
                    String location = getLocationNameById(pid.getLocation());
                    String zone = zonetoName(pid.getZone());
                    PlantInformationTable plantInfoTable = new PlantInformationTable(pid.getPlantId(), location, zone, pid.getVolumePerMonth(), pid.getPlantCapacity());
                    lstPitTable.add(plantInfoTable);
                    volumetable = new PlantInformationTable(pid.getPlantId(), location, zone, pid.getVolumePerMonth(), pid.getPlantCapacity());
                    listforVolume.add(volumetable);
                }
                this.dataList = recreatePieColor(dataList, hiddenSliceId);
            } else if (hiddenSliceId == 2) {
                noOfPlantRendered = true;
                PlantInformationTable planttable;
                setZoneData(plantInfoDetailList);
                lstPitTable = new ArrayList<PlantInformationTable>();
                listforPlant = new ArrayList<PlantInformationTable>();
                planttable = new PlantInformationTable(011, "Location", "Zone", "Batching Capabilities", 'P');
                listforPlant.add(planttable);
                for (PlantInformationDetail pid : plantInfoDetailList) {
                    String location = getLocationNameById(pid.getLocation());
                    String zone = zonetoName(pid.getZone());
                    plantInfoTable = new PlantInformationTable(pid.getPlantId(), location, zone, pid.getCapabilities(), pid.getHdbapprove());
                    planttable = new PlantInformationTable(pid.getPlantId(), location, zone, pid.getCapabilities(), pid.getHdbapprove());
                    lstPitTable.add(plantInfoTable);
                    listforPlant.add(planttable);
                }
                this.dataList = recreatePieColor(dataList, hiddenSliceId);

            } else if (hiddenSliceId == 3) {
                setZoneData(plantInfoDetailList);
                PlantInformationTable plantInfoForTruckAndCapacity;
                listforTruckAndCapacity = new ArrayList<PlantInformationTable>();
                lstPitTable = new ArrayList<PlantInformationTable>();
                plantInfoForTruckAndCapacity = new PlantInformationTable(011, "Location", "Zone", 001, 021);
                listforTruckAndCapacity.add(plantInfoForTruckAndCapacity);
                for (PlantInformationDetail pid : plantInfoDetailList) {
                    String location = getLocationNameById(pid.getLocation());
                    String zone = zonetoName(pid.getZone());
                    PlantInformationTable plantInfoTable = new PlantInformationTable(pid.getPlantId(), location, zone, pid.getNoOfTruck(), pid.getPlantCapacity());
                    lstPitTable.add(plantInfoTable);
                    plantInfoForTruckAndCapacity = new PlantInformationTable(pid.getPlantId(), location, zone, pid.getNoOfTruck(), pid.getPlantCapacity());
                    listforTruckAndCapacity.add(plantInfoForTruckAndCapacity);
                }
                truckRendered = true;
                if (pieType.equalsIgnoreCase(ConstantCommon.PLANT_PIE)) {
                    capacityPieRendered = true;
                } else {
                    truckPieRendered = true;
                }
                this.dataList = recreatePieColor(dataList, hiddenSliceId);
            } else if (hiddenSliceId == 4) {
                capabilityRendered = true;
                this.dataList = recreatePieColor(dataList, hiddenSliceId);
            }
            data = new Gson().toJson(dataList);
        } catch (DataAccessException e) {

        }
    }

    public List<PlantAndCapacityPieData> recreatePieColor(List<PlantAndCapacityPieData> dataList, int index) {
        try {
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setColor("");
                if (dataList.indexOf(dataList.get(i)) != index) {
                    dataList.get(i).setColor("rgba(" + colorList.get(i).getRed() + "," + colorList.get(i).getGreen() + "," + colorList.get(i).getBlue() + ", 0.2)");
                }
            }
        } catch (DataAccessException e) {

        }
        return dataList;
    }

    public void setTolZoneData(List<PlantInformationDetail> plantInfoDetailList) {
        try {
            tolZonePlantList = new ArrayList<Zone>();
            Zone tolPlantZone = new Zone();
            for (int i = 0; i < plantInfoDetailList.size(); i++) {
                if (ConstantCommon.NORTH.equalsIgnoreCase(codeSetupService.findCodeSetupByCodeSetupId(plantInfoDetailList.get(i).getZone()).getCodeSetupName())) {
                    tolPlantZone.setNorth(tolPlantZone.getNorth() + 1);
                } else if (ConstantCommon.EAST.equalsIgnoreCase(codeSetupService.findCodeSetupByCodeSetupId(plantInfoDetailList.get(i).getZone()).getCodeSetupName())) {
                    tolPlantZone.setEast(tolPlantZone.getEast() + 1);
                } else if (ConstantCommon.NORTH_EAST.equalsIgnoreCase(codeSetupService.findCodeSetupByCodeSetupId(plantInfoDetailList.get(i).getZone()).getCodeSetupName())) {
                    tolPlantZone.setNorth_east(tolPlantZone.getNorth_east() + 1);
                } else if (ConstantCommon.WEST.equalsIgnoreCase(codeSetupService.findCodeSetupByCodeSetupId(plantInfoDetailList.get(i).getZone()).getCodeSetupName())) {
                    tolPlantZone.setWest(tolPlantZone.getWest() + 1);
                } else if (ConstantCommon.CENTRAL_SOUTH.equalsIgnoreCase(codeSetupService.findCodeSetupByCodeSetupId(plantInfoDetailList.get(i).getZone()).getCodeSetupName())) {
                    tolPlantZone.setCentral_south(tolPlantZone.getCentral_south() + 1);
                }
            }
            tolPlantZone.setTotal(tolPlantZone.getCentral_south() + tolPlantZone.getEast() + tolPlantZone.getNorth() + tolPlantZone.getWest() + tolPlantZone.getNorth_east());
            tolZonePlantList.add(tolPlantZone);
        } catch (DataAccessException e) {

        }
    }

    public void setZoneData(List<PlantInformationDetail> plantInfoDetailList) {
        try {
            zoneTruckList = new ArrayList<Zone>();
            zonePlantList = new ArrayList<Zone>();
            zoneVolumeList = new ArrayList<Zone>();
            zoneCapacityList = new ArrayList<Zone>();
            Zone plantZone = new Zone();
            Zone truckZone = new Zone();
            Zone volumeZone = new Zone();
            Zone capacityZone = new Zone();
            for (int i = 0; i < plantInfoDetailList.size(); i++) {
                if (ConstantCommon.NORTH.equalsIgnoreCase(codeSetupService.findCodeSetupByCodeSetupId(plantInfoDetailList.get(i).getZone()).getCodeSetupName())) {
                    plantZone.setNorth(plantZone.getNorth() + 1);
                    truckZone.setNorth(truckZone.getNorth() + plantInfoDetailList.get(i).getNoOfTruck());
                    volumeZone.setNorth(volumeZone.getNorth() + plantInfoDetailList.get(i).getVolumePerMonth());
                    capacityZone.setNorth(capacityZone.getNorth() + plantInfoDetailList.get(i).getPlantCapacity());
                } else if (ConstantCommon.EAST.equalsIgnoreCase(codeSetupService.findCodeSetupByCodeSetupId(plantInfoDetailList.get(i).getZone()).getCodeSetupName())) {
                    plantZone.setEast(plantZone.getEast() + 1);
                    truckZone.setEast(truckZone.getEast() + plantInfoDetailList.get(i).getNoOfTruck());
                    volumeZone.setEast(volumeZone.getEast() + plantInfoDetailList.get(i).getVolumePerMonth());
                    capacityZone.setEast(capacityZone.getEast() + plantInfoDetailList.get(i).getPlantCapacity());
                } else if (ConstantCommon.NORTH_EAST.equalsIgnoreCase(codeSetupService.findCodeSetupByCodeSetupId(plantInfoDetailList.get(i).getZone()).getCodeSetupName())) {
                    plantZone.setNorth_east(plantZone.getNorth_east() + 1);
                    truckZone.setNorth_east(truckZone.getNorth_east() + plantInfoDetailList.get(i).getNoOfTruck());
                    volumeZone.setNorth_east(volumeZone.getNorth_east() + plantInfoDetailList.get(i).getVolumePerMonth());
                    capacityZone.setNorth_east(capacityZone.getNorth_east() + plantInfoDetailList.get(i).getPlantCapacity());
                } else if (ConstantCommon.WEST.equalsIgnoreCase(codeSetupService.findCodeSetupByCodeSetupId(plantInfoDetailList.get(i).getZone()).getCodeSetupName())) {
                    plantZone.setWest(plantZone.getWest() + 1);
                    truckZone.setWest(truckZone.getWest() + plantInfoDetailList.get(i).getNoOfTruck());
                    volumeZone.setWest(volumeZone.getWest() + plantInfoDetailList.get(i).getVolumePerMonth());
                    capacityZone.setWest(capacityZone.getWest() + plantInfoDetailList.get(i).getPlantCapacity());
                } else if (ConstantCommon.CENTRAL_SOUTH.equalsIgnoreCase(codeSetupService.findCodeSetupByCodeSetupId(plantInfoDetailList.get(i).getZone()).getCodeSetupName())) {
                    plantZone.setCentral_south(plantZone.getCentral_south() + 1);
                    truckZone.setCentral_south(truckZone.getCentral_south() + plantInfoDetailList.get(i).getNoOfTruck());
                    volumeZone.setCentral_south(volumeZone.getCentral_south() + plantInfoDetailList.get(i).getVolumePerMonth());
                    capacityZone.setCentral_south(capacityZone.getCentral_south() + plantInfoDetailList.get(i).getPlantCapacity());
                }
            }
            truckZone.setTotal(truckZone.getCentral_south() + truckZone.getEast() + truckZone.getNorth() + truckZone.getWest() + truckZone.getNorth_east());
            plantZone.setTotal(plantZone.getCentral_south() + plantZone.getEast() + plantZone.getNorth() + plantZone.getWest() + plantZone.getNorth_east());
            volumeZone.setTotal(volumeZone.getCentral_south() + volumeZone.getEast() + volumeZone.getNorth() + volumeZone.getWest() + volumeZone.getNorth_east());
            capacityZone.setTotal(capacityZone.getCentral_south() + capacityZone.getEast() + capacityZone.getNorth() + capacityZone.getWest() + capacityZone.getNorth_east());
            zoneTruckList.add(truckZone);
            zonePlantList.add(plantZone);
            zoneVolumeList.add(volumeZone);
            zoneCapacityList.add(capacityZone);
        } catch (DataAccessException e) {

        }
    }

    public List<PlantInformationDetail> setPlantInformationDetailList(List<PlantInformationDetail> plantInfoDetailList) {
        try {
            plantInfoDetailList = plantInfoDetailService.addValuesByLocation(plantInfoDetailList);
        } catch (DataAccessException e) {

        }
        return plantInfoDetailList;
    }

    public List<Color> getColorBands(Color color, int bands) {
        List<Color> colorBands = new ArrayList<Color>(bands);
        try {
            for (int index = 0; index < bands; index++) {
                if (getForeGroundColorBasedOnBGBrightness(color)) {
                    if (index == bands - 3) {
                        colorBands.add(brighten(color, (double) 0 / (double) bands));
                    }
                    colorBands.add(brighten(color, (double) index / (double) bands));
                } else {
                    if (index == bands - 1) {
                        colorBands.add(darken(color, (double) 1 / (double) bands));
                    }
                    colorBands.add(darken(color, (double) index / (double) bands));
                }
            }
        } catch (DataAccessException e) {

        }
        return colorBands;

    }

    private int getBrightness(Color c) {
        return (int) Math.sqrt(
                c.getRed() * c.getRed() * .241
                + c.getGreen() * c.getGreen() * .691
                + c.getBlue() * c.getBlue() * .068);
    }

    public boolean getForeGroundColorBasedOnBGBrightness(Color color) {
        if (getBrightness(color) < 130) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Make a color brighten.
     *
     * @param color Color to make brighten.
     * @param fraction Darkness fraction.
     * @return Lighter color.
     */
    public static Color brighten(Color color, double fraction) {

        int red = (int) Math.round(Math.min(255, color.getRed() + 255 * fraction));
        int green = (int) Math.round(Math.min(255, color.getGreen() + 255 * fraction));
        int blue = (int) Math.round(Math.min(255, color.getBlue() + 255 * fraction));

        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);

    }

    public static Color darken(Color color, double fraction) {

        int red = (int) Math.round(Math.max(0, color.getRed() - 255 * fraction));
        int green = (int) Math.round(Math.max(0, color.getGreen() - 255 * fraction));
        int blue = (int) Math.round(Math.max(0, color.getBlue() - 255 * fraction));

        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);

    }

    /**
     *
     * @param colorStr e.g. "#FFFFFF"
     * @return
     */
    public static Color hex2Rgb(String colorStr) {
        return Color.decode(colorStr);
    }

    public static List<String> rgb2Hex(List<Color> colorList) {
        List<String> colors = new ArrayList<String>();
        try {
            String hex;
            for (Color color : colorList) {
                hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
                colors.add(hex);
            }
        } catch (DataAccessException e) {

        }
        return colors;
    }

    public String getPlantByPlantId(int id) {
        String plantName = "";
        try {
            if (id == 011) {
                plantName = "Plant Name";
            } else {
                plantName = plantService.getPlantByPlantId(id).getPlantName();
            }
        } catch (DataAccessException e) {

        }
        return plantName;
    }

    public String checkforVolume(int n) {
        String value = "";
        try {
            if (n == 007) {
                value = "Volume m3 per Month";
            } else {
                value = String.valueOf(n);
            }
        } catch (DataAccessException e) {

        }
        return value;
    }

    // <editor-fold desc="getter and setter of the class">
    public String getHdp() {
        return hdp;
    }

    public void setHdp(String hdp) {
        this.hdp = hdp;
    }

    public boolean isTruckRendered() {
        return truckRendered;
    }

    public void setTruckRendered(boolean truckRendered) {
        this.truckRendered = truckRendered;
    }

    public List<PlantInformationDetail> getFilteredplantInfoDetailList() {
        return filteredplantInfoDetailList;
    }

    public void setFilteredplantInfoDetailList(List<PlantInformationDetail> filteredplantInfoDetailList) {
        this.filteredplantInfoDetailList = filteredplantInfoDetailList;
    }

    public boolean isTolRendered() {
        return tolRendered;
    }

    public void setTolRendered(boolean tolRendered) {
        this.tolRendered = tolRendered;
    }

    public int getValueDiv() {
        return valueDiv;
    }

    public void setValueDiv(int valueDiv) {
        this.valueDiv = valueDiv;
    }

    public String getInfoDetailId() {
        return infoDetailId;
    }

    public void setInfoDetailId(String infoDetailId) {
        this.infoDetailId = infoDetailId;
    }

    public int getHiddenSliceId() {
        return hiddenSliceId;
    }

    public void setHiddenSliceId(int hiddenSliceId) {
        this.hiddenSliceId = hiddenSliceId;
    }

    public List<String> getCapabilityList() {
        return capabilityList;
    }

    public void setCapabilityList(List<String> capabilityList) {
        this.capabilityList = capabilityList;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Zone> getZonePlantList() {
        return zonePlantList;
    }

    public void setZonePlantList(List<Zone> zonePlantList) {
        this.zonePlantList = zonePlantList;
    }

    public boolean isNoOfPlantRendered() {
        return noOfPlantRendered;
    }

    public void setNoOfPlantRendered(boolean noOfPlantRendered) {
        this.noOfPlantRendered = noOfPlantRendered;
    }

    public List<Zone> getZoneTruckList() {
        return zoneTruckList;
    }

    public void setZoneTruckList(List<Zone> zoneTruckList) {
        this.zoneTruckList = zoneTruckList;
    }

    public boolean isCapabilityRendered() {
        return capabilityRendered;
    }

    public void setCapabilityRendered(boolean capabilityRendered) {
        this.capabilityRendered = capabilityRendered;
    }

    public List<CapabilitiesNamesDTO> getCapabilityNameList() {
        return capabilityNameList;
    }

    public void setCapabilityNameList(List<CapabilitiesNamesDTO> capabilityNameList) {
        this.capabilityNameList = capabilityNameList;
    }

    public List<LegalIssue> getLegalIssueList() {
        return legalIssueList;
    }

    public void setLegalIssueList(List<LegalIssue> legalIssueList) {
        this.legalIssueList = legalIssueList;
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

    public List<PlantInformationDetail> getPlantInfoDetailList() {
        return plantInfoDetailList;
    }

    public void setPlantInfoDetailList(List<PlantInformationDetail> plantInfoDetailList) {
        this.plantInfoDetailList = plantInfoDetailList;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<PlantInformationDetail> getPlantInfoDetailTOLList() {
        return plantInfoDetailTOLList;
    }

    public void setPlantInfoDetailTOLList(List<PlantInformationDetail> plantInfoDetailTOLList) {
        this.plantInfoDetailTOLList = plantInfoDetailTOLList;
    }

    public List<Zone> getTolZonePlantList() {
        return tolZonePlantList;
    }

    public void setTolZonePlantList(List<Zone> tolZonePlantList) {
        this.tolZonePlantList = tolZonePlantList;
    }

    public List<String> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<String> zoneList) {
        this.zoneList = zoneList;
    }

    public boolean isVolumeRendered() {
        return volumeRendered;
    }

    public void setVolumeRendered(boolean volumeRendered) {
        this.volumeRendered = volumeRendered;
    }

    public List<Zone> getZoneVolumeList() {
        return zoneVolumeList;
    }

    public void setZoneVolumeList(List<Zone> zoneVolumeList) {
        this.zoneVolumeList = zoneVolumeList;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public char getHdbapprove() {
        return hdbapprove;
    }

    public void setHdbapprove(char hdbapprove) {
        this.hdbapprove = hdbapprove;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public List<PlantInformationTable> getFilteredLstPitTable() {
        return filteredLstPitTable;
    }

    public void setFilteredLstPitTable(List<PlantInformationTable> filteredLstPitTable) {
        this.filteredLstPitTable = filteredLstPitTable;
    }

    public List<PlantInformationTable> getLstPitTable() {
        return lstPitTable;
    }

    public void setLstPitTable(List<PlantInformationTable> lstPitTable) {
        this.lstPitTable = lstPitTable;
    }

    public List<Zone> getZoneCapacityList() {
        return zoneCapacityList;
    }

    public void setZoneCapacityList(List<Zone> zoneCapacityList) {
        this.zoneCapacityList = zoneCapacityList;
    }

    public boolean isTruckPieRendered() {
        return truckPieRendered;
    }

    public void setTruckPieRendered(boolean truckPieRendered) {
        this.truckPieRendered = truckPieRendered;
    }

    public boolean isCapacityPieRendered() {
        return capacityPieRendered;
    }

    public void setCapacityPieRendered(boolean capacityPieRendered) {
        this.capacityPieRendered = capacityPieRendered;
    }

    public List<PlantInformationTable> getListforPlant() {
        return listforPlant;
    }

    public void setListforPlant(List<PlantInformationTable> listforPlant) {
        this.listforPlant = listforPlant;
    }

    public List<PlantInformationTable> getListforVolume() {
        return listforVolume;
    }

    public void setListforVolume(List<PlantInformationTable> listforVolume) {
        this.listforVolume = listforVolume;
    }

    public List<PlantInformationTable> getListforTotal() {
        return listforTotal;
    }

    public void setListforTotal(List<PlantInformationTable> listforTotal) {
        this.listforTotal = listforTotal;
    }

    public List<LegalIssue> getLegalIssueListForPrint() {
        return legalIssueListForPrint;
    }

    public void setLegalIssueListForPrint(List<LegalIssue> legalIssueListForPrint) {
        this.legalIssueListForPrint = legalIssueListForPrint;
    }

    public List<PlantInformationTable> getListforTruckAndCapacity() {
        return listforTruckAndCapacity;
    }

    public void setListforTruckAndCapacity(List<PlantInformationTable> listforTruckAndCapacity) {
        this.listforTruckAndCapacity = listforTruckAndCapacity;
    }
    // </editor-fold>
}
