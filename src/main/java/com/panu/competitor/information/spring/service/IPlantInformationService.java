/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service;

import com.panu.competitor.information.dto.SummaryReportDTO;
import com.panu.competitor.information.dto.ReportGraphDTO;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.PlantInformation;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Htet Nanda Kyaw
 */
public interface IPlantInformationService {
    /**
     * The purpose of this method is find all Competitor info.
     *
     * @return companies as list
     */
    public List<PlantInformation> findAllCompetitorInfo();

    /**
     * The purpose of this method is add new Company info.
     *
     * @param competitorInfo
     * @throws CompetitorException
     */
    public void addCompetitorInfo(PlantInformation competitorInfo) throws CompetitorException;

    /**
     * The purpose of this method is add new Company info.
     *
     * @param updateCompetitorInfo
     * @throws CompetitorException
     */
    public void updateCompetitorInfo(PlantInformation updateCompetitorInfo) throws CompetitorException;
    
    public List<PlantInformation> findCompetitorInfoIDByCompanyName(List<String> companyname,Date period) throws CompetitorException;
    
    public String findCompanyNameByCompetitorInfoID(int cominfoID) throws CompetitorException;
    
    public List<SummaryReportDTO> findCompetitorInfoIDByCompanyNameANDPeriod(String companyname,List<Date>fromToAllPeriod) throws CompetitorException;
    
    public List<ReportGraphDTO> findCompetitorInfoIDByCompanyNameANDPeriodForReportGraph(String companyname,List<Date>fromToAllPeriod) throws CompetitorException;

    public void updatePlantInfoCompanyName(String oldName, String newName);

    public PlantInformation getPlantInfoByCompanyNameAndPeriod(String companyName, String period);
    
}
