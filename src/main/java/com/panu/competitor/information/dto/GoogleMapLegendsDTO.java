/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dto;

/**
 *
 * @author OhMyGhoul
 */
public class GoogleMapLegendsDTO {
    private String name;
    private String icon;

    public GoogleMapLegendsDTO() {
    }

    public GoogleMapLegendsDTO(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    
}
