/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.common;

import com.panu.competitor.information.pojo.entity.CodeSetup;
import com.panu.competitor.information.pojo.entity.Role;
import com.panu.competitor.information.spring.service.ICodeSetupService;
import com.panu.competitor.information.spring.service.IRoleService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author HNDK
 */
@Named("globalDropDownList")
@ApplicationScoped
public class GlobalDropDownList {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private ICodeSetupService codeSetupService;
    private List<Role> roleList;
    private List<String> monthAndYearList;
    SimpleDateFormat format = new SimpleDateFormat(ConstantCommon.FROMAT_DROPDOWN_DATE);
    private List<String> months;
    private List<String> years;
    private List<CodeSetup> codeSetupZoneList;
    private List<CodeSetup> codeSetupHDBApprovedList;
    private List<CodeSetup> codeSetupLocationList;
    private List<String> periodLastOneList;
    private List<String> periodCurrentAndLastFiveList;
    private List<String> yearOflodgeList;

    /**
     * initial function of the class
     *
     */
    @PostConstruct
    public void init() {
        try {
            roleList = new ArrayList<Role>();
            codeSetupZoneList = new ArrayList<CodeSetup>();
            codeSetupHDBApprovedList = new ArrayList<CodeSetup>();
            codeSetupLocationList = new ArrayList<CodeSetup>();
            periodCurrentAndLastFiveList = new ArrayList<String>();
            yearOflodgeList = new ArrayList<String>();
            getPreviousAndFutureTwoYears();
            roleList.addAll(roleService.findAllRoles());
            codeSetupZoneList.addAll(codeSetupService.findAllCodeSetupByCodeSetupType(ConstantCommon.ZONE_CODESETUP_TYPE));
            codeSetupHDBApprovedList.addAll(codeSetupService.findAllCodeSetupByCodeSetupType(ConstantCommon.HDB_CODESETUP_TYPE));
            codeSetupLocationList.addAll(codeSetupService.findAllCodeSetupByCodeSetupType(ConstantCommon.LOCATION_CODESETUP_TYPE));
            periodLastOneList = new ArrayList<String>();
            periodLastOneList.addAll(new CommonUtilsFunctions().getPreviousAndFutureOneYears());
            periodCurrentAndLastFiveList.addAll(new CommonUtilsFunctions().getCurrentAndPreviousFiveYears());
            yearOflodgeList.addAll(new CommonUtilsFunctions().getYearForYearofLodge());
        } catch (DataAccessException e) {
            //TODO
        }

    }

    /**
     * getPreviousAndFutureOneYears
     *
     * The purpose of this method is to get previous and future two years
     */
    public void getPreviousAndFutureTwoYears() {
        try {
            months = new ArrayList<String>();
            years = new ArrayList<String>();
            monthAndYearList = new ArrayList<String>();
            months.add("Jan");
            months.add("Feb");
            months.add("Mar");
            months.add("Apr");
            months.add("May");
            months.add("Jun");
            months.add("Jul");
            months.add("Aug");
            months.add("Sept");
            months.add("Oct");
            months.add("Nov");
            months.add("Dec");

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -2);
            years.add(format.format(cal.getTime()));

            cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            years.add(format.format(cal.getTime()));

            cal = Calendar.getInstance();
            years.add(format.format(cal.getTime()));

            cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 1);
            years.add(format.format(cal.getTime()));

            cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 2);
            years.add(format.format(cal.getTime()));

            for (String year : years) {
                for (String month : months) {
                    monthAndYearList.add(month + "-" + year);
                }
            }
        } catch (DataAccessException e) {
        }
    }

    /**
     * completePlantPeriod
     *
     * The purpose of this method is to auto complete month year dropdown
     *
     * @param query
     * @return filtered string list
     */
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

    public String changeDateFormat(Date date) {
        String formatdate = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            if (date != null) {
                formatdate = format.format(date);
            }
        } catch (DataAccessException e) {
        }
        return formatdate;

    }

    public String showYesOrNo(String mark) {
        String name = "";
        try {
            if (mark.equals("Y")) {
                name = ConstantCommon.STATUS_YES;
            } else if (mark.equals("N")) {
                name = ConstantCommon.STATUS_NO;
            }
        } catch (DataAccessException e) {
        }
        return name;
    }

    // <editor-fold desc="getter and setter of the class">
    public List<String> getMonthAndYearList() {
        getPreviousAndFutureTwoYears();
        return monthAndYearList;
    }

    public List<String> getPeriodLastOneList() {
        return periodLastOneList;
    }

    public void setPeriodLastOneList(List<String> periodList) {
        this.periodLastOneList = periodList;
    }

    public List<String> getPeriodCurrentAndLastFiveList() {
        return periodCurrentAndLastFiveList;
    }

    public void setPeriodCurrentAndLastFiveList(List<String> periodCurrentAndLastFiveList) {
        this.periodCurrentAndLastFiveList = periodCurrentAndLastFiveList;
    }

    public void setMonthAndYearList(List<String> monthAndYearList) {
        this.monthAndYearList = monthAndYearList;
    }

    public List<CodeSetup> getCodeSetupZoneList() {
        return codeSetupZoneList;
    }

    public void setCodeSetupZoneList(List<CodeSetup> codeSetupZoneList) {
        this.codeSetupZoneList = codeSetupZoneList;
    }

    public List<String> getYearOflodgeList() {
        return yearOflodgeList;
    }

    public void setYearOflodgeList(List<String> yearOflodgeList) {
        this.yearOflodgeList = yearOflodgeList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    // </editor-fold>
    public List<CodeSetup> getCodeSetupHDBApprovedList() {
        return codeSetupHDBApprovedList;
    }

    public void setCodeSetupHDBApprovedList(List<CodeSetup> codeSetupHDBApprovedList) {
        this.codeSetupHDBApprovedList = codeSetupHDBApprovedList;
    }

    public List<CodeSetup> getCodeSetupLocationList() {
        return codeSetupLocationList;
    }

    public void setCodeSetupLocationList(List<CodeSetup> codeSetupLocationList) {
        this.codeSetupLocationList = codeSetupLocationList;
    }

    /**
     * completeYearOfLodge
     *
     * The purpose of this method is to auto complete Year of Lodge for Legal
     * Issue dropdown
     *
     * @param query
     * @return filtered string list
     */
    public List<String> completeYearOfLodge(String query) {
        List<String> filteredPeriod = new ArrayList<String>();
        try {
            List<String> allPeriod = new CommonUtilsFunctions().getYearForYearofLodge();

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

}
