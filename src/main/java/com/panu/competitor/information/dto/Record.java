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
public class Record {

    private String companyName;
    private int batchingPlantLocation;
    private int noOfBatchingPlant;
    private int noOfTrack;
    private int batchingCapacity;
    private int companyId;
    private String color;
    private int competitorInfoDetialId;
    private String plantId;

    public Record() {
    }

    public Record(int competitorInfoDetialId,String companyName, int batchingPlantLocation, int noOfBatchingPlant, int noOfTrack, int batchingCapacity, int companyId,String color,String plantId) {
        super();
        this.competitorInfoDetialId = competitorInfoDetialId;
        this.companyName = companyName;
        this.batchingPlantLocation = batchingPlantLocation;
        this.noOfBatchingPlant = noOfBatchingPlant;
        this.noOfTrack = noOfTrack;
        this.batchingCapacity = batchingCapacity;
        this.companyId = companyId;
        this.color = color;
        this.plantId = plantId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getBatchingPlantLocation() {
        return batchingPlantLocation;
    }

    public void setBatchingPlantLocation(int batchingPlantLocation) {
        this.batchingPlantLocation = batchingPlantLocation;
    }

    public int getNoOfBatchingPlant() {
        return noOfBatchingPlant;
    }

    public void setNoOfBatchingPlant(int noOfBatchingPlant) {
        this.noOfBatchingPlant = noOfBatchingPlant;
    }

    public int getNoOfTrack() {
        return noOfTrack;
    }

    public void setNoOfTrack(int noOfTrack) {
        this.noOfTrack = noOfTrack;
    }

    public int getBatchingCapacity() {
        return batchingCapacity;
    }

    public void setBatchingCapacity(int batchingCapacity) {
        this.batchingCapacity = batchingCapacity;
    }

    public String getColor() {
        return "#"+color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCompetitorInfoDetialId() {
        return competitorInfoDetialId;
    }

    public void setCompetitorInfoDetialId(int competitorInfoDetialId) {
        this.competitorInfoDetialId = competitorInfoDetialId;
    }
    
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }
    
}
