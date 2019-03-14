/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service.imp;

import com.panu.competitor.information.dao.CodeSetupDAO;
import com.panu.competitor.information.pojo.entity.CodeSetup;
import com.panu.competitor.information.spring.service.ICodeSetupService;
import com.panu.competitor.information.spring.service.constant.NamedQueryConstant;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author HNDK
 */
@Named
public class CodeSetupServiceImp implements ICodeSetupService {
    
    @Autowired
    private CodeSetupDAO codeSetupDAO;
    
    @Override
    public List<CodeSetup> findAllCodeSetupByCodeSetupType(String codeSetupType) {
        String constantQuery = NamedQueryConstant.FIND_CODESETUP_BYCODESETUP_TYPE;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.CODESETUPTYPE);
        List<String> paramValueList = new ArrayList<String>();
        paramValueList.add(codeSetupType);
        List<CodeSetup> codeSetUpList = codeSetupDAO.selectByStringType(constantQuery, paramColumnList,
                paramValueList);
        List<CodeSetup> codeSetup = new ArrayList<CodeSetup>();
        if (codeSetUpList != null && !codeSetUpList.isEmpty() && codeSetUpList.get(0) != null) {
            codeSetup = codeSetUpList;
        }
        return codeSetup;
    }
    
    @Override
    public CodeSetup findCodeSetupByCodeSetupId(int codeSetupId) {
        String constantQuery = NamedQueryConstant.FIND_CODESETUP_BYCODESETUP_ID;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.CODESETUPID);
        List<Integer> paramValueList = new ArrayList<Integer>();
        paramValueList.add(codeSetupId);
        List<CodeSetup> codeSetUpList = codeSetupDAO.selectByIntType(constantQuery, paramColumnList,
                paramValueList);
        CodeSetup codeSetup = new CodeSetup();
        if (codeSetUpList != null && !codeSetUpList.isEmpty() && codeSetUpList.get(0) != null) {
            codeSetup = codeSetUpList.get(0);
        }
        return codeSetup;
    }
    
    @Override
    public CodeSetup findCodeSetupByCodeSetupName(String codeSetupName) {
        String constantQuery = NamedQueryConstant.FIND_CODESETUP_BYCODESETUP_NAME;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.CODESETUPNAME);
        List<String> paramValueList = new ArrayList<String>();
        paramValueList.add(codeSetupName);
        List<CodeSetup> codeSetUpList = codeSetupDAO.selectByStringType(constantQuery, paramColumnList,
                paramValueList);
        CodeSetup codeSetup = new CodeSetup();
        if (codeSetUpList != null && !codeSetUpList.isEmpty() && codeSetUpList.get(0) != null) {
            codeSetup = codeSetUpList.get(0);
        }
        return codeSetup;
    }

    @Override
    public CodeSetup findCodeSetupByCodeSetupType(String codeSetupType) {
        String constantQuery = NamedQueryConstant.FIND_CODESETUP_BYCODESETUP_TYPE;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.CODESETUPTYPE);
        List<String> paramValueList = new ArrayList<String>();
        paramValueList.add(codeSetupType);
        List<CodeSetup> codeSetUpList = codeSetupDAO.selectByStringType(constantQuery, paramColumnList,
                paramValueList);
        CodeSetup codeSetup = new CodeSetup();
        if (codeSetUpList != null && !codeSetUpList.isEmpty() && codeSetUpList.get(0) != null) {
            codeSetup = codeSetUpList.get(0);
        }
        return codeSetup;
    }
    
}
