/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service;

import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.LegalIssue;
import java.util.List;

/**
 * Interface Class for Legal Issue Service.
 *
 * @author HNDK
 */
public interface ILegalIssueService {

    /**
     * The purpose of this method is add new LegalIssue info.
     *
     * @param legalIssue
     * @throws CompetitorException
     */
    public void addLegalIssue(LegalIssue legalIssue) throws CompetitorException;

    /**
     * The purpose of this method is get LegalIssue info by companyId.
     *
     * @param companyId
     * @return
     * @throws CompetitorException
     */
    public List<LegalIssue> findLegalIssueListByCompanyId(int companyId) throws CompetitorException;
    
    public List<LegalIssue> findLegalIssueListByCompanyIdForPrint(int companyId) throws CompetitorException;

    /**
     * The purpose of this method is to remove LegalIssue info by legalissueId.
     *
     * @param legalIssueIdList
     * @throws CompetitorException
     */
    public void removeLegalIssueIdList(List<Integer> legalIssueIdList) throws CompetitorException;

    /**
     * The purpose of this method is to remove LegalIssue info by legalissueId.
     *
     * @param companyId
     * @throws CompetitorException
     */
    public void deleteLegalIssueByCompanyId(int companyId) throws CompetitorException;
}
