/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.common;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Htet Nanda Kyaw
 */
public class DashBoardMapDataList {

    private List<DashBoardMapData> children = new ArrayList<DashBoardMapData>();

    public DashBoardMapDataList() {
    }

    public DashBoardMapDataList(List<DashBoardMapData> dashBoardMapData) {
        this.children = dashBoardMapData;
    }

    public List<DashBoardMapData> getChildren() {
        return children;
    }

    public void setChildren(List<DashBoardMapData> dashBoardMapData) {
        this.children = dashBoardMapData;
    }

}
