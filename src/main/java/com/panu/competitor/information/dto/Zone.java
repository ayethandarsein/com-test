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
public class Zone {
    private int north;
    private int east;
    private int west;
    private int central_south;
    private int north_east;
    private int total;

    public Zone() {
    }

    public Zone(int north, int east, int west, int central_south, int north_east,int total) {
        this.north = north;
        this.east = east;
        this.west = west;
        this.central_south = central_south;
        this.north_east = north_east;
        this.total = total;
    }

    public int getNorth() {
        return north;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public int getEast() {
        return east;
    }

    public void setEast(int east) {
        this.east = east;
    }

    public int getWest() {
        return west;
    }

    public void setWest(int west) {
        this.west = west;
    }

    public int getCentral_south() {
        return central_south;
    }

    public void setCentral_south(int central_south) {
        this.central_south = central_south;
    }

    public int getNorth_east() {
        return north_east;
    }

    public void setNorth_east(int north_east) {
        this.north_east = north_east;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
