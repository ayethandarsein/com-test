/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service;

import com.panu.competitor.information.pojo.entity.CodeSetup;
import java.util.List;

/**
 *
 * @author HNDK
 */
public interface ICodeSetupService {
    /**
     * The purpose of this method is find CodeSetup By CodeSetup type.
     *
     * @param codeSetupType
     * @return CodeSetup as list
     */
    public List<CodeSetup> findAllCodeSetupByCodeSetupType(String codeSetupType);
    /**
     * The purpose of this method is find CodeSetup By CodeSetup id.
     *
     * @param codeSetupId
     * @return CodeSetup
     */
    public CodeSetup findCodeSetupByCodeSetupId(int codeSetupId);
    /**
     * The purpose of this method is find CodeSetup By CodeSetup type.
     *
     * @param codeSetupName
     * @return CodeSetup
     */
    public CodeSetup findCodeSetupByCodeSetupName(String codeSetupName);
     /**
     * The purpose of this method is find CodeSetup By CodeSetup Name.
     *
     * @param codeSetupType
     * @return CodeSetup
     */
    public CodeSetup findCodeSetupByCodeSetupType(String codeSetupType);
}
