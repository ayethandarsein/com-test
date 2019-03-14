/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service;

import com.panu.competitor.information.dto.Record;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Htet Nanda Kyaw
 */
public interface IPlantInformationDetailService {

    /**
     * The purpose of this method is find all Competitor info.
     *
     * @return companies as list
     */
    public List<PlantInformationDetail> findAllCompetitorInfo();

    /**
     * The purpose of this method is add new Company info.
     *
     * @param competitorInfo
     * @throws CompetitorException
     */
    public void addCompetitorInfo(PlantInformationDetail competitorInfo) throws CompetitorException;

    /**
     * The purpose of this method is add new Company info.
     *
     * @param updateCompetitorInfoDetial
     * @throws CompetitorException
     */
    public void updateCompetitorInfoDetail(PlantInformationDetail updateCompetitorInfoDetial) throws CompetitorException;

    /**
     * The purpose of this method is get all previous competitor info detail by
     * companyId and period.
     *
     * @param companyName
     * @param period
     * @return competitor info detail list
     * @throws CompetitorException
     */
    public List<PlantInformationDetail> getPreviousCompetiorInfoDetailByCompanyIdAndPeriod(String companyName, String period) throws CompetitorException;

    /**
     * The purpose of this method is get all competitor info detail by companyId
     * and period.
     *
     * @param companyName
     * @param period
     * @return competitor info detail list
     * @throws CompetitorException
     */
    public List<PlantInformationDetail> getCompetiorInfoDetailByCompanyShortNameAndPeriod(String companyName, String period) throws CompetitorException;

    /**
     * The purpose of this method is get all competitor info detail from Plant
     *
     * @param companyName
     * @param period
     * @return competitor info detail list
     * @throws CompetitorException
     */
    public List<PlantInformationDetail> getAllCompetiorInfoDetailFromPlant(String companyName, String period) throws CompetitorException;
    
    public List<PlantInformationDetail> findAllCompetiorInfoDetailBySearchFields(List<String> companyname,Date period, List<Integer> location,List<Integer> zone,int nooftruck,char hdbapproved,List<String> capabilities) throws CompetitorException;
    
    public List<PlantInformationDetail> getCompetitorInfoDetialByCompanyShortName(String companyCode);

    public List<PlantInformationDetail> getNewCompetitorInfoDetailByCompanyNameAndPlantPeriod(String companyName, String period);
    
      public List<PlantInformationDetail> findAllCompetiorInfoDetailByComInfoId(int cominfoid) throws CompetitorException;

    public List<PlantInformationDetail> getCompetiorInfoDetailByCompanyIdAndPeriodAndZone(String companyCode, String plantPeriod, int codeSetupId);

    public List<Record> removeDuplicateRecord(List<Record> dashboardMapList);

    public List<Record> addAllplantInfoDetail(List<Record> dashboardMapList);

    public List<PlantInformationDetail> removeDuplicateInfoDetail(List<PlantInformationDetail> competitorInformationDetailList);

    public List<PlantInformationDetail> getCompetiorInfoDetailByCompanyIdAndPeriod(int companyId, String period);

    public List<PlantInformationDetail> RemoveDuplicateInfoDetailAndAddValues(List<PlantInformationDetail> plantInfoDetailList);

    public List<PlantInformationDetail> addValuesByLocation(List<PlantInformationDetail> plantInfoDetailList);

    public List<PlantInformationDetail> getCompetiorInfoDetailByLeasePeriodTo(int companyId, String periodto6months);

    public long getPlantInfoDetailCount(String companyShortName);
}
