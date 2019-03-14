/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service.imp;

import com.panu.competitor.information.dao.LegalIssueDAO;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.LegalIssue;
import com.panu.competitor.information.spring.service.ILegalIssueService;
import com.panu.competitor.information.spring.service.constant.NamedQueryConstant;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Htet Nanda Kyaw
 */
@Named
public class LegalIssueServiceImp implements ILegalIssueService {

    @Autowired
    private LegalIssueDAO legalIssueDAO;

    @Override
    public void addLegalIssue(LegalIssue legalIssue) throws CompetitorException {
        legalIssueDAO.update(legalIssue);
    }

    @Override
    public List<LegalIssue> findLegalIssueListByCompanyId(int companyId) throws CompetitorException {
        String constantQuery = NamedQueryConstant.FIND_LEGALISSUE_BYCOMPANY_ID;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.COMPANYID);
        List<Integer> paramValueList = new ArrayList<Integer>();
        paramValueList.add(companyId);
        List<LegalIssue> legalIssueList = legalIssueDAO.selectByIntType(constantQuery, paramColumnList,
                paramValueList);
        List<LegalIssue> legalIssues = new ArrayList<LegalIssue>();
        if (legalIssueList != null && !legalIssueList.isEmpty() && legalIssueList.get(0) != null) {
            legalIssues = legalIssueList;
        }
        return legalIssues;
    }
    
     @Override
    public List<LegalIssue> findLegalIssueListByCompanyIdForPrint(int companyId) throws CompetitorException {
        String constantQuery = NamedQueryConstant.FIND_LEGALISSUE_BYCOMPANY_ID;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.COMPANYID);
        List<Integer> paramValueList = new ArrayList<Integer>();
        paramValueList.add(companyId);
        List<LegalIssue> legalIssueList = legalIssueDAO.selectByIntType(constantQuery, paramColumnList,
                paramValueList);
        List<LegalIssue> legalIssues = new ArrayList<LegalIssue>();
        LegalIssue legalissue=new LegalIssue();
        legalissue.setCompany("Company:");
        legalissue.setYearOfLodge(001);
        legalissue.setCourtType("Supreme Court/ Subordinate Court:");
        legalIssues.add(legalissue);
        if (legalIssueList != null && !legalIssueList.isEmpty() && legalIssueList.get(0) != null) {
            legalIssues.addAll(legalIssueList);
        }
        return legalIssues;
    }

    @Override
    public void removeLegalIssueIdList(List<Integer> legalIssueIdList) throws CompetitorException {
        legalIssueDAO.removeLegalIssueIdList(legalIssueIdList);
    }

    @Override
    public void deleteLegalIssueByCompanyId(int companyId) throws CompetitorException {
        legalIssueDAO.deleteLegalIssueByCompanyId(companyId);
    }

}
