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
import java.util.ArrayList;
import java.util.List;

public class RecordParent {

	private List<Record> children = new ArrayList<Record>();
	private List<Record> childrenSelected;
	
	public RecordParent(List<Record> children) {
		this.children = children;
	}

	public List<Record> getChildren() {
		return children;
	}

	public void setChildren(List<Record> children) {
		this.children = children;
	}

	public List<Record> getChildrenSelected() {
		return childrenSelected;
	}

	public void setChildrenSelected(List<Record> childrenSelected) {
		this.childrenSelected = childrenSelected;
	}
}
