/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service.imp;

import com.panu.competitor.information.dao.CompanyDAO;
import com.panu.competitor.information.dto.GoogleMapLegendsDTO;
import com.panu.competitor.information.dto.Record;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Company;
import com.panu.competitor.information.spring.service.ICompanyService;
import com.panu.competitor.information.spring.service.constant.NamedQueryConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author HNDK
 */
@Named
public class CompanyServiceImp implements ICompanyService {

    @Autowired
    private CompanyDAO companyDAO;

    @Override
    public List<Company> findAllCompany() {
        return companyDAO.select(NamedQueryConstant.GET_COMPANIES);
    }

    @Override
    public void addCompany(Company company) throws CompetitorException {
        companyDAO.insert(company);
    }

    @Override
    public void updateCompany(Company updateCompany) throws CompetitorException {
        companyDAO.update(updateCompany);
    }

    @Override
    public Map<String, String> validateCompanyInfo(Company new_company, List<Company> companyList) {
        Map<String, String> validateIds = new HashMap<String,String>();
        companyList.stream().map((company) -> {
            if (company.getName().trim().equalsIgnoreCase(new_company.getName().trim())) {
                validateIds.put("Name", "true");
            }
            return company;
        }).map((company) -> {
            if (company.getColor().trim().equals(new_company.getColor().trim())) {
                validateIds.put("Color", "true");
            }
            return company;
        }).filter((company) -> (company.getCompanyCode().trim().equalsIgnoreCase(new_company.getCompanyCode().trim()))).forEach((_item) -> {
            validateIds.put("CompanyCode", "true");
        });
        return validateIds;
    }

    @Override
    public Company findCompanyByCompanyId(int companyId) {
        String constantQuery = NamedQueryConstant.FIND_COMPANY_BYCOMPANY_ID;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.COMPANYID);
        List<Integer> paramValueList = new ArrayList<Integer>();
        paramValueList.add(companyId);
        List<Company> companyList = companyDAO.selectByIntType(constantQuery, paramColumnList,
                paramValueList);
        Company company = new Company();
        if (companyList != null && !companyList.isEmpty() && companyList.get(0) != null) {
            company = companyList.get(0);
        }
        return company;
    }

    @Override
    public List<Company> getCompanyByPlantLocationId(int locationId) {
        return companyDAO.getCompanyByPlantLocationId(locationId);
    }

    @Override
    public Company findCompanyByCompanyCode(String companyCode) {
        String constantQuery = NamedQueryConstant.FIND_COMPANY_BYCOMPANY_CODE;
        List<String> paramColumnList = new ArrayList<String>();
        paramColumnList.add(NamedQueryConstant.COMPANY_CODE);
        List<String> paramValueList = new ArrayList<String>();
        paramValueList.add(companyCode);
        List<Company> companyList = companyDAO.selectByStringType(constantQuery, paramColumnList, paramValueList);
        Company company = new Company();
        if (companyList != null && !companyList.isEmpty() && companyList.get(0) != null) {
            company = companyList.get(0);
        }
        return company;
    }

    @Override
    public List<Company> removeCompanyDuplicate(List<Company> companyList) {
        List<Company> newList = new ArrayList<Company>();
        // Traverse through the first list 
        for (Company element : companyList) {
            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        // return the new list 
        return newList;
    }

    @Override
    public List<GoogleMapLegendsDTO> removeMapIconsDuplicate(List<GoogleMapLegendsDTO> googleMapIcons) {
        List<GoogleMapLegendsDTO> newList = new ArrayList<GoogleMapLegendsDTO>();
        for (int j = 0; j < googleMapIcons.size(); j++) {
            for (int i = j + 1; i < googleMapIcons.size(); i++) {
                if (googleMapIcons.get(j).getName().equalsIgnoreCase(googleMapIcons.get(i).getName())) {
                    newList.add(googleMapIcons.get(i));
                }
            }
        }
        for (int i = 0; i < newList.size(); i++) {
            googleMapIcons.remove(newList.get(i));
        }
        return googleMapIcons;
    }

}
