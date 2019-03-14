/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Htet Nanda Kyaw
 */
public class CommonUtilsFunctions {

    private List<String> monthAndYearList;
    private List<String> yearList;
    SimpleDateFormat format = new SimpleDateFormat(ConstantCommon.FROMAT_DROPDOWN_DATE);
    private List<String> months;
    private List<String> years;
    private List<String> mapIcons;

    public CommonUtilsFunctions() {
    }

    public List<String> getGoogleMapIcons() {
        mapIcons = new ArrayList<String>();
        mapIcons.add(ConstantCommon.gmap_icon_red);
        mapIcons.add(ConstantCommon.gmap_icon_yellow);
        mapIcons.add(ConstantCommon.gmap_icon_blue);
        mapIcons.add(ConstantCommon.gmap_icon_purple);
        mapIcons.add(ConstantCommon.gmap_icon_green);
        mapIcons.add(ConstantCommon.gmap_icon_orange);
        mapIcons.add(ConstantCommon.gmap_icon_gray);
        mapIcons.add(ConstantCommon.gmap_icon_brown);
        mapIcons.add(ConstantCommon.gmap_icon_white);
        mapIcons.add(ConstantCommon.gmap_icon_black);
        return mapIcons;
    }

    /**
     * getPreviousAndFutureOneYears
     *
     * The purpose of this method is to get previous and future two years
     *
     * @return
     */
    public List<String> getPreviousAndFutureOneYears() {
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
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");

        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.YEAR, -2);
//        years.add(format.format(cal.getTime()));
//
//        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        years.add(format.format(cal.getTime()));

        cal = Calendar.getInstance();
        years.add(format.format(cal.getTime()));

        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        years.add(format.format(cal.getTime()));

//        cal = Calendar.getInstance();
//        cal.add(Calendar.YEAR, 2);
//        years.add(format.format(cal.getTime()));
        for (String year : years) {
            for (String month : months) {
                monthAndYearList.add(month + "-" + year);
            }
        }
        return monthAndYearList;
    }
    
    
    public List<String> getCurrentAndPreviousFiveYears() {
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
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -5);
        years.add(format.format(cal.getTime()));

        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -4);
        years.add(format.format(cal.getTime()));

        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -3);
        years.add(format.format(cal.getTime()));

        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -2);
        years.add(format.format(cal.getTime()));

        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        years.add(format.format(cal.getTime()));

        cal = Calendar.getInstance();
        years.add(format.format(cal.getTime()));

        for (String year : years) {
            for (String month : months) {
                monthAndYearList.add(month + "-" + year);
            }
        }
        return monthAndYearList;
    }
    
    /**
     * getPreviousAndFutureOneYears
     *
     * The purpose of this method is to get previous and future two years
     *
     * @return
     */
    public List<String> getYearForYearofLodge() {
        yearList = new ArrayList<String>();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -5);
        yearList.add(format.format(cal.getTime()));
        
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -4);
        yearList.add(format.format(cal.getTime()));

        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -3);
        yearList.add(format.format(cal.getTime()));
        
        cal.add(Calendar.YEAR, -2);
        yearList.add(format.format(cal.getTime()));

        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        yearList.add(format.format(cal.getTime()));

        cal = Calendar.getInstance();
        yearList.add(format.format(cal.getTime()));

        return yearList;
    }
}
