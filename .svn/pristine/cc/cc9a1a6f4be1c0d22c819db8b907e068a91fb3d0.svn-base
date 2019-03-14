/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.controller.bean;

import com.panu.competitor.information.common.ConstantCommon;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Htet Nanda Kyaw
 */
@Named("dashBoardMapMB")
@ViewScoped
public class DashBoardMapMB implements Serializable {

    // <editor-fold desc="variables of the class">

    private static final long serialVersionUID = 1L;
    private String northCodeSetup;
    private String eastCodeSetup;
    private String southCodeSetup;
    private String westCodeSetup;
    private String northEastCodeSetup;

    // </editor-fold>\

    @PostConstruct
    public void init() {
        northCodeSetup = ConstantCommon.NORTH_CODESETUP;
        eastCodeSetup = ConstantCommon.EAST_CODESETUP;
        southCodeSetup = ConstantCommon.CENTRAL_SOUTH;
        westCodeSetup = ConstantCommon.WEST_CODESETUP;
        northEastCodeSetup = ConstantCommon.NORTH_EAST;
    }

    public String northMapClick() {
        return "/view/marketing/map/MapDetailPage.xhtml?faces-redirect=true CodeSetupName=" + northCodeSetup;
    }

    public String eastMapClick() {
        return "/view/marketing/map/MapDetailPage.xhtml?faces-redirect=true CodeSetupName=" + eastCodeSetup;
    }

    public String northEastMapClick() {
        return "/view/marketing/map/MapDetailPage.xhtml?faces-redirect=true CodeSetupName=" + northEastCodeSetup;
    }

    public String southMapClick() {
        return "/view/marketing/map/MapDetailPage.xhtml?faces-redirect=true CodeSetupName=" + southCodeSetup;
    }

    public String westMapClick() {
        return "/view/marketing/map/MapDetailPage.xhtml?faces-redirect=true CodeSetupName=" + westCodeSetup;
    }

    public String getNorthCodeSetup() {
        return northCodeSetup;
    }

    public void setNorthCodeSetup(String northCodeSetup) {
        this.northCodeSetup = northCodeSetup;
    }

}
