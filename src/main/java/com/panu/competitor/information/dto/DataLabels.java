/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dto;

/**
 *
 * @author Htet Nanda Kyaw
 */
public class DataLabels {

    private String format;
    private int distance;
    private boolean enabled;

    public DataLabels() {
    }

    public DataLabels(String format) {
        this.format = format;
    }

    public DataLabels(int distance, String format) {
        this.format = format;
        this.distance = distance;
    }

    public DataLabels(int distance, String format, boolean enabled) {
        this.format = format;
        this.distance = distance;
        this.enabled = enabled;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
