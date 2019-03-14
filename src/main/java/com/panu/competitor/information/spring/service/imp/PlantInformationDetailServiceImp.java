/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service.imp;

import com.panu.competitor.information.dao.CompetitorInformationDAO;
import com.panu.competitor.information.dao.CompetitorInformationDetailDAO;
import com.panu.competitor.information.dto.Record;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Company;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import com.panu.competitor.information.spring.service.IPlantInformationDetailService;
import com.panu.competitor.information.spring.service.IPlantInformationService;
import com.panu.competitor.information.spring.service.constant.NamedQueryConstant;
import com.panu.competitor.information.pojo.entity.PlantInformation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Htet Nanda Kyaw
 */
@Named
public class PlantInformationDetailServiceImp implements IPlantInformationDetailService {

    @Autowired
    private PlantInformationServiceImp competitorinfomationserviceimp;

    @Autowired
    private CompetitorInformationDetailDAO competitorInfoDetailDAO;

    @Override
    public List<PlantInformationDetail> findAllCompetitorInfo() {
        return competitorInfoDetailDAO.select(NamedQueryConstant.GET_COMPETITORINFODETAIL);
    }

    @Override
    public void addCompetitorInfo(PlantInformationDetail competitorInfoDetial) throws CompetitorException {
        competitorInfoDetailDAO.add(competitorInfoDetial);
    }

    @Override
    public void updateCompetitorInfoDetail(PlantInformationDetail updateCompetitorInfoDetial) throws CompetitorException {
        competitorInfoDetailDAO.update(updateCompetitorInfoDetial);
    }

    @Override
    public List<PlantInformationDetail> getPreviousCompetiorInfoDetailByCompanyIdAndPeriod(String companyName, String period) throws CompetitorException {
        try {
            return competitorInfoDetailDAO.getPreviousCompetiorInfoDetailByCompanyIdAndPeriod(companyName, period);
        } catch (ParseException ex) {
            Logger.getLogger(PlantInformationDetailServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<PlantInformationDetail> getCompetiorInfoDetailByCompanyShortNameAndPeriod(String companyName, String period) throws CompetitorException {
        List<PlantInformationDetail> plantInformationDetail = new ArrayList<PlantInformationDetail>();
        try {
            plantInformationDetail = competitorInfoDetailDAO.getCompetiorInfoDetailByCompanyShortNameAndPeriod(companyName, period);
        } catch (ParseException ex) {
            Logger.getLogger(PlantInformationDetailServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plantInformationDetail;
    }

    @Override
    public List<PlantInformationDetail> getAllCompetiorInfoDetailFromPlant(String companyName, String period) throws CompetitorException {
        List<PlantInformationDetail> plantInformationDetail = new ArrayList<PlantInformationDetail>();
        try {
            plantInformationDetail = competitorInfoDetailDAO.getAllCompetiorInfoDetailFromPlant(companyName, period);
        } catch (ParseException ex) {
            Logger.getLogger(PlantInformationDetailServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plantInformationDetail;
    }

    @Override
    public List<PlantInformationDetail> findAllCompetiorInfoDetailBySearchFields(List<String> companyname, Date period, List<Integer> location, List<Integer> zone, int nooftruck, char hdbapproved, List<String> capabilities) throws CompetitorException {
        List<PlantInformation> comInfo = new ArrayList<PlantInformation>();
        List<PlantInformationDetail> comInfoDetailList = new ArrayList<PlantInformationDetail>();
        List<Integer> cominfodetailid = new ArrayList<Integer>();
        List<Integer> locationToInt = new ArrayList<Integer>();
        List<Integer> zoneToInt = new ArrayList<Integer>();
        comInfo = competitorinfomationserviceimp.findCompetitorInfoIDByCompanyName(companyname, period);
        if (!comInfo.isEmpty()) {
            for (int i = 0; i < comInfo.size(); i++) {
                cominfodetailid.add(comInfo.get(i).getCompetitorInfoId());
            }
            if (location.size() > 0 || !location.isEmpty()) {
                for (int i = 0; i < location.size(); i++) {
                    String tempValue = String.valueOf(location.get(i));
                    int tempIntValue = Integer.parseInt(tempValue);
                    locationToInt.add(tempIntValue);
                }
            }
             if (zone.size() > 0 || !zone.isEmpty()) {
                for (int i = 0; i < zone.size(); i++) {
                    String tempValue = String.valueOf(zone.get(i));
                    int tempIntValue = Integer.parseInt(tempValue);
                    zoneToInt.add(tempIntValue);
                }
            }
            comInfoDetailList = competitorInfoDetailDAO.findSearchValueList(cominfodetailid, locationToInt, zoneToInt, nooftruck, hdbapproved, capabilities);
        }

        return comInfoDetailList;
    }

    @Override
    public List<PlantInformationDetail> getCompetitorInfoDetialByCompanyShortName(String companyCode) {
        return competitorInfoDetailDAO.getCompetitorInfoDetialByCompanyShortName(companyCode);
    }

    @Override
    public List<PlantInformationDetail> getNewCompetitorInfoDetailByCompanyNameAndPlantPeriod(String companyName, String period) {
        List<PlantInformationDetail> competitorInfoDetail = new ArrayList<PlantInformationDetail>();
        try {
            competitorInfoDetail = competitorInfoDetailDAO.getNewCompetitorInfoDetailByCompanyNameAndPlantPeriod(companyName, period);
        } catch (ParseException ex) {
            Logger.getLogger(PlantInformationDetailServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return competitorInfoDetail;
    }

    @Override
    public List<PlantInformationDetail> findAllCompetiorInfoDetailByComInfoId(int cominfoid) throws CompetitorException {
        List<String> paramColumns = new ArrayList<String>();
        paramColumns.add(NamedQueryConstant.IS_COM_INFO_ID);
        List<Integer> paramValues = new ArrayList<Integer>();
        paramValues.add(cominfoid);
        List<PlantInformationDetail> plantInfoList = competitorInfoDetailDAO.selectByIntType(NamedQueryConstant.FIND_COMPETITORINFODETAIL_INFO_BY_COM_INFO_ID, paramColumns, paramValues);
        return plantInfoList;
    }

    @Override
    public List<PlantInformationDetail> getCompetiorInfoDetailByCompanyIdAndPeriodAndZone(String companyCode, String plantPeriod, int codeSetupId) {
        List<PlantInformationDetail> plantInformationDetail = new ArrayList<PlantInformationDetail>();
        try {
            plantInformationDetail = competitorInfoDetailDAO.getCompetiorInfoDetailByCompanyIdAndPeriodAndZone(companyCode, plantPeriod, codeSetupId);
        } catch (ParseException ex) {
            Logger.getLogger(PlantInformationDetailServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plantInformationDetail;
    }

    @Override
    public List<Record> removeDuplicateRecord(List<Record> dashboardMapList) {
        List<Record> newList = new ArrayList<Record>();
        for (int j = 0; j < dashboardMapList.size(); j++) {
            for (int i = j + 1; i < dashboardMapList.size(); i++) {
                if (dashboardMapList.get(j).getBatchingPlantLocation() == dashboardMapList.get(i).getBatchingPlantLocation()) {
                    dashboardMapList.get(j).setNoOfBatchingPlant(dashboardMapList.get(i).getNoOfBatchingPlant() + dashboardMapList.get(j).getNoOfBatchingPlant());
                    dashboardMapList.get(j).setNoOfTrack(dashboardMapList.get(i).getNoOfTrack() + dashboardMapList.get(j).getNoOfTrack());
                    dashboardMapList.get(j).setBatchingCapacity(dashboardMapList.get(i).getBatchingCapacity() + dashboardMapList.get(j).getBatchingCapacity());
                    dashboardMapList.get(j).setPlantId(dashboardMapList.get(i).getPlantId() + "," + dashboardMapList.get(j).getPlantId());
                    newList.add(dashboardMapList.get(i));
                }
            }
        }
        for (int i = 0; i < newList.size(); i++) {
            dashboardMapList.remove(newList.get(i));
        }
        return dashboardMapList;
    }

    @Override
    public List<Record> addAllplantInfoDetail(List<Record> dashboardMapList) {
        if (dashboardMapList.size() > 1) {
            for (int i = 1; i < dashboardMapList.size(); i++) {
                dashboardMapList.get(0).setNoOfBatchingPlant(dashboardMapList.get(0).getNoOfBatchingPlant() + dashboardMapList.get(i).getNoOfBatchingPlant());
                dashboardMapList.get(0).setNoOfTrack(dashboardMapList.get(0).getNoOfTrack() + dashboardMapList.get(i).getNoOfTrack());
                dashboardMapList.get(0).setBatchingCapacity(dashboardMapList.get(0).getBatchingCapacity() + dashboardMapList.get(i).getBatchingCapacity());
            }
        }
        return dashboardMapList;
    }

    @Override
    public List<PlantInformationDetail> removeDuplicateInfoDetail(List<PlantInformationDetail> competitorInformationDetailList) {
        List<PlantInformationDetail> newList = new ArrayList<PlantInformationDetail>();
        for (int j = 0; j < competitorInformationDetailList.size(); j++) {
            for (int i = j + 1; i < competitorInformationDetailList.size(); i++) {
                if (competitorInformationDetailList.get(j).getPlantId() == competitorInformationDetailList.get(i).getPlantId()) {
                    competitorInformationDetailList.get(j).setPlantId(competitorInformationDetailList.get(i).getPlantId());
                    competitorInformationDetailList.get(j).setLocation(competitorInformationDetailList.get(i).getLocation());
                    competitorInformationDetailList.get(j).setLatitude(competitorInformationDetailList.get(i).getLatitude());
                    competitorInformationDetailList.get(j).setLongitude(competitorInformationDetailList.get(i).getLongitude());
                    competitorInformationDetailList.get(j).setZone(competitorInformationDetailList.get(i).getZone());
                    competitorInformationDetailList.get(j).setNoOfPlant(competitorInformationDetailList.get(i).getNoOfPlant());
                    newList.add(competitorInformationDetailList.get(i));
                }
            }
        }
        for (int i = 0; i < newList.size(); i++) {
            competitorInformationDetailList.remove(newList.get(i));
        }
        return competitorInformationDetailList;
    }

    @Override
    public List<PlantInformationDetail> getCompetiorInfoDetailByCompanyIdAndPeriod(int companyId, String period) {
        List<PlantInformationDetail> plantInformationDetail = new ArrayList<PlantInformationDetail>();
        try {
            plantInformationDetail = competitorInfoDetailDAO.getCompetiorInfoDetailByCompanyIdAndPeriod(companyId, period);
        } catch (ParseException ex) {
            Logger.getLogger(PlantInformationDetailServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plantInformationDetail;
    }

    @Override
    public List<PlantInformationDetail> RemoveDuplicateInfoDetailAndAddValues(List<PlantInformationDetail> plantInfoDetailList) {
        List<PlantInformationDetail> returnList = new ArrayList<PlantInformationDetail>();
        for (int i = 1; i < plantInfoDetailList.size(); i++) {
            plantInfoDetailList.get(0).setNoOfPlant(plantInfoDetailList.get(0).getNoOfPlant() + plantInfoDetailList.get(i).getNoOfPlant());
            plantInfoDetailList.get(0).setNoOfTruck(plantInfoDetailList.get(0).getNoOfTruck() + plantInfoDetailList.get(i).getNoOfTruck());
            plantInfoDetailList.get(0).setPlantCapacity(plantInfoDetailList.get(0).getPlantCapacity() + plantInfoDetailList.get(i).getPlantCapacity());
            plantInfoDetailList.get(0).setVolumePerMonth(plantInfoDetailList.get(0).getVolumePerMonth() + plantInfoDetailList.get(i).getVolumePerMonth());
            plantInfoDetailList.get(0).setCapabilities(plantInfoDetailList.get(0).getCapabilities() + "," + plantInfoDetailList.get(i).getCapabilities());
        }
        returnList.addAll(plantInfoDetailList);
        return returnList;
    }

    @Override
    public List<PlantInformationDetail> addValuesByLocation(List<PlantInformationDetail> plantInfoDetailList) {
        List<PlantInformationDetail> tempList = new ArrayList<PlantInformationDetail>();
        for (int i = 0; i < plantInfoDetailList.size(); i++) {
            for (int j = i + 1; j < plantInfoDetailList.size(); j++) {
                if (plantInfoDetailList.get(i).getLocation() == plantInfoDetailList.get(j).getLocation()) {
                    plantInfoDetailList.get(i).setNoOfPlant(plantInfoDetailList.get(i).getNoOfPlant() + plantInfoDetailList.get(j).getNoOfPlant());
                    plantInfoDetailList.get(i).setCapabilities(plantInfoDetailList.get(i).getCapabilities() + "," + plantInfoDetailList.get(j).getCapabilities());
                    plantInfoDetailList.get(i).setSupportedBy(plantInfoDetailList.get(i).getSupportedBy() + "," + plantInfoDetailList.get(j).getSupportedBy());
                    plantInfoDetailList.get(i).setNoOfTruck(plantInfoDetailList.get(i).getNoOfTruck() + plantInfoDetailList.get(j).getNoOfTruck());
                    tempList.add(plantInfoDetailList.get(j));
                }
            }
        }
        for (int i = 0; i < tempList.size(); i++) {
            plantInfoDetailList.remove(tempList.get(i));
        }
        return plantInfoDetailList;
    }

    @Override
    public List<PlantInformationDetail> getCompetiorInfoDetailByLeasePeriodTo(int companyId, String periodto6months) {
        List<PlantInformationDetail> plantInformationDetail = new ArrayList<PlantInformationDetail>();
        try {
            plantInformationDetail = competitorInfoDetailDAO.getCompetiorInfoDetailByLeasePeriodTo(companyId, periodto6months);
        } catch (ParseException ex) {
            Logger.getLogger(PlantInformationDetailServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plantInformationDetail;
    }

    @Override
    public long getPlantInfoDetailCount(String companyShortName) {
        return competitorInfoDetailDAO.getPlantInfoDetailCount(companyShortName);
    }
}
