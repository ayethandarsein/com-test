/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BI3
 */
public class ReportGraphDTOParent {
    
    private List<ReportGraphDTO> children = new ArrayList<ReportGraphDTO>();
    
    public ReportGraphDTOParent(List<ReportGraphDTO> children) {
        this.children = children;
    }

    public List<ReportGraphDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ReportGraphDTO> children) {
        this.children = children;
    }
    
    
    
}
