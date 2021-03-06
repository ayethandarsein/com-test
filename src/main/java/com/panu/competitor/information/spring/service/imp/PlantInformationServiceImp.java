/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service.imp;

import com.panu.competitor.information.dao.CompetitorInformationDAO;
import com.panu.competitor.information.dto.ReportGraphDTO;
import com.panu.competitor.information.dto.SummaryReportDTO;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.PlantInformation;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import com.panu.competitor.information.spring.service.IPlantInformationService;
import com.panu.competitor.information.spring.service.IPlantInformationDetailService;
import com.panu.competitor.information.spring.service.constant.NamedQueryConstant;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
public class PlantInformationServiceImp implements IPlantInformationService {

    @Autowired
    private CompetitorInformationDAO competitorInfoDAO;

    @Autowired
    private IPlantInformationDetailService iplantinfomationdetailservice;

    @Override
    public List<PlantInformation> findAllCompetitorInfo() {
        return competitorInfoDAO.select(NamedQueryConstant.GET_COMPETITORINFOS);
    }

    @Override
    public void addCompetitorInfo(PlantInformation competitorInfo) throws CompetitorException {
        competitorInfoDAO.update(competitorInfo);
    }

    @Override
    public void updateCompetitorInfo(PlantInformation updateCompetitorInfo) throws CompetitorException {
        competitorInfoDAO.update(updateCompetitorInfo);
    }

    @Override
    public List<PlantInformation> findCompetitorInfoIDByCompanyName(List<String> companyname, Date period) throws CompetitorException {
//        List<String> paramColumns = new ArrayList<String>();
//        paramColumns.add(NamedQueryConstant.IS_COMPANY_NAME);
//         paramColumns.add(NamedQueryConstant.IS_PERIOD);
//        List<String> paramValues = new ArrayList<String>();
//        paramValues.add (companyname);
//        paramValues.add(period);
        List<PlantInformation> competitorInfoList = competitorInfoDAO.findCompetitorInfoIDByCompanyNameAndPeriod(companyname, period);
//        CompetitorInformation competitorInformation = new CompetitorInformation();
//        if (competitorInfoList != null && !competitorInfoList.isEmpty() && competitorInfoList.get(0) != null) {
//            competitorInformation = competitorInfoList.get(0);
//        }
        return competitorInfoList;
    }

    @Override
    public String findCompanyNameByCompetitorInfoID(int cominfoID) throws CompetitorException {
        List<String> paramColumns = new ArrayList<String>();
        paramColumns.add(NamedQueryConstant.IS_COM_INFO_ID);
        List<Integer> paramValues = new ArrayList<Integer>();
        paramValues.add(cominfoID);
        List<PlantInformation> competitorInfoList = competitorInfoDAO.selectByIntType(NamedQueryConstant.FIND_COMPANY_NAME_BY_COM_INFO_ID, paramColumns, paramValues);
        PlantInformation competitorInformation = new PlantInformation();
        if (competitorInfoList != null && !competitorInfoList.isEmpty() && competitorInfoList.get(0) != null) {
            competitorInformation = competitorInfoList.get(0);
        }
        return competitorInformation.getCompanyName();
    }

    @Override
    public List<SummaryReportDTO> findCompetitorInfoIDByCompanyNameANDPeriod(String companyname, List<Date> fromToAllPeriod) throws CompetitorException {
        List<PlantInformationDetail> competitorInfoDetail = new ArrayList<PlantInformationDetail>();
        List<SummaryReportDTO> summaryReportDto = new ArrayList<SummaryReportDTO>();
        List<PlantInformationDetail> templist = new ArrayList<PlantInformationDetail>();
        List<SummaryReportDTO> templistForReport = new ArrayList<SummaryReportDTO>();
        List<PlantInformation> competitorInfoList = competitorInfoDAO.findCompetitorInfoIDByCompanyName(companyname, fromToAllPeriod);
        if (competitorInfoList.size() > 0 || competitorInfoList.isEmpty()) {
            for (int i = 0; competitorInfoList.size() > i; i++) {
                SummaryReportDTO tempDto = new SummaryReportDTO();
                tempDto.setCompetitorInfoId(competitorInfoList.get(i).getCompetitorInfoId());
                tempDto.setCompanyName(competitorInfoList.get(i).getCompanyName());
                tempDto.setPeriod(competitorInfoList.get(i).getPeriod());
                summaryReportDto.add(tempDto);
                competitorInfoDetail.addAll(iplantinfomationdetailservice.findAllCompetiorInfoDetailByComInfoId(competitorInfoList.get(i).getCompetitorInfoId()));
            }
        }

        if (competitorInfoDetail.size() > 0 || competitorInfoDetail.isEmpty()) {
            for (int i = 0; i < competitorInfoDetail.size(); i++) {
                for (int j = i + 1; j < competitorInfoDetail.size(); j++) {
                    if (competitorInfoDetail.get(i).getCompetitorInfoId() == competitorInfoDetail.get(j).getCompetitorInfoId()) {
                        competitorInfoDetail.get(i).setNoOfPlant(competitorInfoDetail.get(i).getNoOfPlant() + competitorInfoDetail.get(j).getNoOfPlant());
                        competitorInfoDetail.get(i).setNoOfTruck(competitorInfoDetail.get(i).getNoOfTruck() + competitorInfoDetail.get(j).getNoOfTruck());
                        competitorInfoDetail.get(i).setPlantCapacity(competitorInfoDetail.get(i).getPlantCapacity() + competitorInfoDetail.get(j).getPlantCapacity());
                        competitorInfoDetail.get(i).setVolumePerMonth(competitorInfoDetail.get(i).getVolumePerMonth() + competitorInfoDetail.get(j).getVolumePerMonth());
                        templist.add(competitorInfoDetail.get(j));
                    }
                }
            }
        }

        if (templist.size() > 0 || templist.isEmpty()) {
            for (int i = 0; i < templist.size(); i++) {
                competitorInfoDetail.remove(templist.get(i));
            }
        }

        if (competitorInfoDetail.size() > 0 || competitorInfoDetail.isEmpty()) {
            for (int i = 0; i < competitorInfoDetail.size(); i++) {
                for (int j = 0; j < summaryReportDto.size(); j++) {
                    if (competitorInfoDetail.get(i).getCompetitorInfoId() == summaryReportDto.get(j).getCompetitorInfoId()) {
                        summaryReportDto.get(j).setNoOfPlant(competitorInfoDetail.get(i).getNoOfPlant());
                        summaryReportDto.get(j).setNoOfTruck(competitorInfoDetail.get(i).getNoOfTruck());
                        summaryReportDto.get(j).setPlantCapacity(competitorInfoDetail.get(i).getPlantCapacity());
                        summaryReportDto.get(j).setVolumePerMonth(competitorInfoDetail.get(i).getVolumePerMonth());
                    }
                }
            }
        }

        if (summaryReportDto.size() > 0 || summaryReportDto.isEmpty()) {
            for (int i = 0; i < summaryReportDto.size(); i++) {
                for (int j = i + 1; j < summaryReportDto.size(); j++) {
                    if ((summaryReportDto.get(i).getCompanyName().equals(summaryReportDto.get(j).getCompanyName())) && (summaryReportDto.get(j).getPeriod().equals(summaryReportDto.get(i).getPeriod()))) {
                        summaryReportDto.get(i).setNoOfPlant(summaryReportDto.get(i).getNoOfPlant() + summaryReportDto.get(j).getNoOfPlant());
                        summaryReportDto.get(i).setNoOfTruck(summaryReportDto.get(i).getNoOfTruck() + summaryReportDto.get(j).getNoOfTruck());
                        summaryReportDto.get(i).setPlantCapacity(summaryReportDto.get(i).getPlantCapacity() + summaryReportDto.get(j).getPlantCapacity());
                        summaryReportDto.get(i).setVolumePerMonth(summaryReportDto.get(i).getVolumePerMonth() + summaryReportDto.get(j).getVolumePerMonth());
                        templistForReport.add(summaryReportDto.get(j));
                    }
                }
            }
        }

        if (templistForReport.size() > 0 || templistForReport.isEmpty()) {
            for (int i = 0; i < templistForReport.size(); i++) {
                summaryReportDto.remove(templistForReport.get(i));
            }
        }

        return summaryReportDto;
    }

    @Override
    public List<ReportGraphDTO> findCompetitorInfoIDByCompanyNameANDPeriodForReportGraph(String companyname, List<Date> fromToAllPeriod) throws CompetitorException {
        List<PlantInformationDetail> competitorInfoDetail = new ArrayList<PlantInformationDetail>();
        List<ReportGraphDTO> reportGraphDto = new ArrayList<ReportGraphDTO>();
        List<PlantInformationDetail> templist = new ArrayList<PlantInformationDetail>();
        List<ReportGraphDTO> templistForReport = new ArrayList<ReportGraphDTO>();
        List<PlantInformation> competitorInfoList = competitorInfoDAO.findCompetitorInfoIDByCompanyName(companyname, fromToAllPeriod);
        if (competitorInfoList.size() > 0 || competitorInfoList.isEmpty()) {
            for (int i = 0; competitorInfoList.size() > i; i++) {
                ReportGraphDTO tempDto = new ReportGraphDTO();
                tempDto.setCompetitorInfoId(competitorInfoList.get(i).getCompetitorInfoId());
                tempDto.setCompanyName(competitorInfoList.get(i).getCompanyName());
                tempDto.setPeriod(competitorInfoList.get(i).getPeriod());
                reportGraphDto.add(tempDto);
                competitorInfoDetail.addAll(iplantinfomationdetailservice.findAllCompetiorInfoDetailByComInfoId(competitorInfoList.get(i).getCompetitorInfoId()));
            }
        }

        if (competitorInfoDetail.size() > 0 || competitorInfoDetail.isEmpty()) {
            for (int i = 0; i < competitorInfoDetail.size(); i++) {
                for (int j = i + 1; j < competitorInfoDetail.size(); j++) {
                    if (competitorInfoDetail.get(i).getCompetitorInfoId() == competitorInfoDetail.get(j).getCompetitorInfoId()) {
                        competitorInfoDetail.get(i).setNoOfPlant(competitorInfoDetail.get(i).getNoOfPlant() + competitorInfoDetail.get(j).getNoOfPlant());
                        competitorInfoDetail.get(i).setNoOfTruck(competitorInfoDetail.get(i).getNoOfTruck() + competitorInfoDetail.get(j).getNoOfTruck());
                        competitorInfoDetail.get(i).setPlantCapacity(competitorInfoDetail.get(i).getPlantCapacity() + competitorInfoDetail.get(j).getPlantCapacity());
                        competitorInfoDetail.get(i).setVolumePerMonth(competitorInfoDetail.get(i).getVolumePerMonth() + competitorInfoDetail.get(j).getVolumePerMonth());
                        templist.add(competitorInfoDetail.get(j));
                    }
                }
            }
        }

        if (templist.size() > 0 || templist.isEmpty()) {
            for (int i = 0; i < templist.size(); i++) {
                competitorInfoDetail.remove(templist.get(i));
            }
        }

        if (competitorInfoDetail.size() > 0 || competitorInfoDetail.isEmpty()) {
            for (int i = 0; i < competitorInfoDetail.size(); i++) {
                for (int j = 0; j < reportGraphDto.size(); j++) {
                    if (competitorInfoDetail.get(i).getCompetitorInfoId() == reportGraphDto.get(j).getCompetitorInfoId()) {
                        reportGraphDto.get(j).setNoOfPlant(competitorInfoDetail.get(i).getNoOfPlant());
                        reportGraphDto.get(j).setNoOfTruck(competitorInfoDetail.get(i).getNoOfTruck());
                        reportGraphDto.get(j).setPlantCapacity(competitorInfoDetail.get(i).getPlantCapacity());
                        reportGraphDto.get(j).setVolumePerMonth(competitorInfoDetail.get(i).getVolumePerMonth());
                    }
                }
            }
        }

        if (reportGraphDto.size() > 0 || reportGraphDto.isEmpty()) {
            for (int i = 0; i < reportGraphDto.size(); i++) {
                for (int j = i + 1; j < reportGraphDto.size(); j++) {
                    if ((reportGraphDto.get(i).getCompanyName().equals(reportGraphDto.get(j).getCompanyName())) && (reportGraphDto.get(j).getPeriod().equals(reportGraphDto.get(i).getPeriod()))) {
                        reportGraphDto.get(i).setNoOfPlant(reportGraphDto.get(i).getNoOfPlant() + reportGraphDto.get(j).getNoOfPlant());
                        reportGraphDto.get(i).setNoOfTruck(reportGraphDto.get(i).getNoOfTruck() + reportGraphDto.get(j).getNoOfTruck());
                        reportGraphDto.get(i).setPlantCapacity(reportGraphDto.get(i).getPlantCapacity() + reportGraphDto.get(j).getPlantCapacity());
                        reportGraphDto.get(i).setVolumePerMonth(reportGraphDto.get(i).getVolumePerMonth() + reportGraphDto.get(j).getVolumePerMonth());
                        templistForReport.add(reportGraphDto.get(j));
                    }
                }
            }
        }

        if (templistForReport.size() > 0 || templistForReport.isEmpty()) {
            for (int i = 0; i < templistForReport.size(); i++) {
                reportGraphDto.remove(templistForReport.get(i));
            }
        }
        return reportGraphDto;
    }

    @Override
    public void updatePlantInfoCompanyName(String oldName, String newName) {
        competitorInfoDAO.updatePlantInfoCompanyName(oldName, newName);
    }

    @Override
    public PlantInformation getPlantInfoByCompanyNameAndPeriod(String companyName, String period) {
        PlantInformation plantInformation = new PlantInformation();
        try {
            plantInformation = competitorInfoDAO.getPlantInfoByCompanyNameAndPeriod(companyName, period);
        } catch (ParseException ex) {
            Logger.getLogger(PlantInformationServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plantInformation;
    }

}
