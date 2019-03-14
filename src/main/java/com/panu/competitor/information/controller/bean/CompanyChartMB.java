/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.controller.bean;

import com.google.gson.Gson;
import com.panu.competitor.information.common.CommonUtilsFunctions;
import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.dto.DataLabels;
import com.panu.competitor.information.dto.PlantAndCapacityPieData;
import com.panu.competitor.information.dto.Record;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Company;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import com.panu.competitor.information.spring.service.ICompanyService;
import com.panu.competitor.information.spring.service.IPlantInformationDetailService;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Htet Nanda Kyaw
 */
@Named("companyChartMB")
@ViewScoped
public class CompanyChartMB implements Serializable {

    // <editor-fold desc="variables of the class">
    private static final long serialVersionUID = 1L;
    @Autowired
    private IPlantInformationDetailService plantInfoDetailService;
    @Autowired
    private ICompanyService companyService;
    List<String> filterName = new ArrayList<String>();
    private String charttitle;
    List<PlantInformationDetail> plantInfoDetailList;
    private List<Company> companyList;
    private List<Record> parents = new ArrayList<Record>();
    private List<PlantInformationDetail> competitorInfoDetailSubList;
    private List<PlantAndCapacityPieData> dataList;
    private String data;
    private List<String> colorList;
    private String color;
    private String period;
    private int hiddenPeriod;
    private int hiddenCompanyId;
    private Double piePlantCount;
    private Double pieTruckCount;
    private String seriesName;
    private String pieName;
    private final String returnUrl = "/view/marketing/chart/CompanyDetailChart.xhtml?faces-redirect=true&infoDetailId=";
    private List<String> periodList;
// </editor-fold>

    public void onfirstchartChange() throws IOException, CompetitorException {
        initBatchingAndCapacityPie();
    }

    @PostConstruct
    public void initial() {
        try {
            this.filterName = new ArrayList<String>();
            this.filterName.add(ConstantCommon.TOTAL_BATCHING_PLANT_CAPACITY);
            this.filterName.add(ConstantCommon.TOTAL_NUMBER_OF_TRUCKS);
            charttitle = filterName.get(0);
            companyList = new ArrayList<Company>();
            period = new SimpleDateFormat("MMM-yyyy").format(new Date());
        } catch (DataAccessException e) {

        }
    }

    public void onPeriodSelect() throws CompetitorException {
        try {
            setBatchingAndCapacityPieData(companyList, period);
        } catch (DataAccessException e) {
        }
    }

    public void initBatchingAndCapacityPie() throws CompetitorException {
        try {
            companyList = new ArrayList<Company>();
            companyList.addAll(companyService.findAllCompany());
            companyList = companyService.removeCompanyDuplicate(companyList);
            periodList = new ArrayList<String>();
            periodList.addAll(new CommonUtilsFunctions().getPreviousAndFutureOneYears());
            setBatchingAndCapacityPieData(companyList, period);
        } catch (DataAccessException e) {
        }
    }

    public void initTrucksPie() {
        try {
            period = new SimpleDateFormat("MMM-yyyy").format(new Date());
        } catch (DataAccessException e) {

        }
    }

    public void setBatchingAndCapacityPieData(List<Company> companyList, String period) throws CompetitorException {
        try {
            dataList = new ArrayList<PlantAndCapacityPieData>();
            colorList = new ArrayList<String>();
            parents = new ArrayList<Record>();
            piePlantCount = 0.0;
            pieTruckCount = 0.0;
            for (Company company : companyList) {
                competitorInfoDetailSubList = new ArrayList<PlantInformationDetail>();
                competitorInfoDetailSubList.addAll(plantInfoDetailService.getCompetiorInfoDetailByCompanyShortNameAndPeriod(company.getCompanyCode(), period));
                if (!competitorInfoDetailSubList.isEmpty()) {
                    parents.add(setDashBoardMapData(company, competitorInfoDetailSubList).get(0));
                }
            }
            for (int i = 0; i < parents.size(); i++) {
                piePlantCount += parents.get(i).getNoOfBatchingPlant();
                pieTruckCount += parents.get(i).getNoOfTrack();
            }
            dataList = setDataListForPie(parents);
            data = new Gson().toJson(dataList);
            colorList = setPieChartColors(parents);
            color = new Gson().toJson(colorList);
            seriesName = new Gson().toJson(seriesName);
        } catch (DataAccessException e) {
        }
    }

    public List<PlantAndCapacityPieData> setDataListForPie(List<Record> competitorInformationDetail) {
        List<PlantAndCapacityPieData> pieData = new ArrayList<PlantAndCapacityPieData>();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        try {
            for (Record plantinfoDetail : competitorInformationDetail) {
                if (charttitle.equalsIgnoreCase(ConstantCommon.TOTAL_BATCHING_PLANT_CAPACITY)) {
                    pieData.add(new PlantAndCapacityPieData(plantinfoDetail.getCompanyId(), "<b>" + plantinfoDetail.getCompanyName() + "</b>" + " (Batching Plants-" + plantinfoDetail.getNoOfBatchingPlant() + ", Capacity-" + plantinfoDetail.getBatchingCapacity() + ")", plantinfoDetail.getNoOfBatchingPlant(),
                            new DataLabels(-150, "<div style=\"text-align:center;font-size:13px; font-weight: 900;\"><font style=\"font-size:3em;margin-bottom:-5px\">"
                                    + numberFormat.format(plantinfoDetail.getNoOfBatchingPlant())
                                    + "</font><br><font style=\"letter-spacing :5px;\">BATCHING<br>PLANTS</font><br><div style=\"font-size:14px\"><button style=\"background-color:#3B3838;width: 130px;height: 30px;margin: 0 auto; padding: 0;display: inline-block;line-height: 30px;text-align: center;border-width:0;letter-spacing :2px;\" disabled>"
                                    + plantinfoDetail.getCompanyName()
                                    + "</button></div><font style=\"font-size:3em;margin-bottom:-5px\">"
                                    + numberFormat.format(plantinfoDetail.getBatchingCapacity())
                                    + "</font><font style=\"letter-spacing :5px;\"><br>CAPACITY</font></div>", enablePlantDataLables(plantinfoDetail.getNoOfBatchingPlant()))));
                    seriesName = ConstantCommon.TOTAL_BATCHING_PLANT;
                    pieName = ConstantCommon.PLANT_PIE;
                } else {
                    pieData.add(new PlantAndCapacityPieData(plantinfoDetail.getCompanyId(), plantinfoDetail.getCompanyName() + " (Trucks-" + plantinfoDetail.getNoOfTrack() + ")", plantinfoDetail.getNoOfTrack(),
                            new DataLabels(-140, "<div style=\"text-align:center;font-size:13px; font-weight: 900;\"><font style=\"font-size:3em;margin-bottom:-5px\">"
                                    + numberFormat.format(plantinfoDetail.getNoOfTrack())
                                    + "</font><font style=\"letter-spacing :5px;\"><br>TRUCKS<br></font><div style=\"font-size:14px\"><button style=\"background-color:#3B3838;width: 130px;height: 30px;margin: 0 auto; padding: 0;display: inline-block;line-height: 30px;text-align: center;border-width:0;letter-spacing :2px;\" disabled>"
                                    + plantinfoDetail.getCompanyName(), enableTruckDataLables(plantinfoDetail.getNoOfTrack()))));
                    seriesName = ConstantCommon.TOTAL_NUMBER_OF_TRUCKS;
                    pieName = ConstantCommon.TRUCK_PIE;
                }
            }
        } catch (Exception e) {

        }
        return pieData;
    }

    public boolean enablePlantDataLables(int noOfBatching) {
        boolean enable = true;
        try {
            double piePercentage = Integer.valueOf(noOfBatching).doubleValue() / piePlantCount * 100.00;
            if (piePercentage < 12) {
                enable = false;
            }
        } catch (DataAccessException e) {

        }
        return enable;
    }

    public boolean enableTruckDataLables(int getNoOfTrack) {
        boolean enable = true;
        try {
            double piePercentage = Integer.valueOf(getNoOfTrack).doubleValue() / pieTruckCount * 100.00;
            if (piePercentage <= 9) {
                enable = false;
            }
        } catch (DataAccessException e) {
        }
        return enable;
    }

    public List<String> setPieChartColors(List<Record> competitorInformationDetail) {
        List<String> colors = new ArrayList<String>();
        for (Record plantinfoDetail : competitorInformationDetail) {
            colors.add(plantinfoDetail.getColor());
        }
        return colors;
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
                dashBoardMapDTO.setColor(company.getColor());
                dashBoardMapDTO.setCompetitorInfoDetialId(competitorInfoDetial.getCompetitorDetailId());
                dashboardMapList.add(dashBoardMapDTO);
            }
            dashboardMapList = plantInfoDetailService.removeDuplicateRecord(dashboardMapList);
            dashboardMapList = plantInfoDetailService.addAllplantInfoDetail(dashboardMapList);
        } catch (DataAccessException e) {

        }
        return dashboardMapList;
    }

    public String redirectToDetailChart() {
        return returnUrl + hiddenCompanyId + "_" + period + "_" + pieName;
    }

    // <editor-fold desc="getter and setter of the class">
    public List<PlantInformationDetail> getPlantInfoDetailList() {
        return plantInfoDetailList;
    }

    public void setPlantInfoDetailList(List<PlantInformationDetail> plantInfoDetailList) {
        this.plantInfoDetailList = plantInfoDetailList;
    }

    public int getHiddenCompanyId() {
        return hiddenCompanyId;
    }

    public void setHiddenCompanyId(int hiddenCompanyId) {
        this.hiddenCompanyId = hiddenCompanyId;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public List<Record> getParents() {
        return parents;
    }

    public void setParents(List<Record> parents) {
        this.parents = parents;
    }

    public List<PlantInformationDetail> getCompetitorInfoDetailSubList() {
        return competitorInfoDetailSubList;
    }

    public void setCompetitorInfoDetailSubList(List<PlantInformationDetail> competitorInfoDetailSubList) {
        this.competitorInfoDetailSubList = competitorInfoDetailSubList;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getFilterName() {
        return filterName;
    }

    public void setFilterName(List<String> filterName) {
        this.filterName = filterName;
    }

    public int getHiddenPeriod() {
        return hiddenPeriod;
    }

    public void setHiddenPeriod(int hiddenPeriod) {
        this.hiddenPeriod = hiddenPeriod;
    }

    public String getCharttitle() {
        return charttitle;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public List<String> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<String> periodList) {
        this.periodList = periodList;
    }

    public void setCharttitle(String charttitle) {
        this.charttitle = charttitle;
    }

    // </editor-fold>
}
