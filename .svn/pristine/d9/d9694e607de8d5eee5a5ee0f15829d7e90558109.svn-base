/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service;

import com.panu.competitor.information.dto.GoogleMapLegendsDTO;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Company;
import java.util.List;
import java.util.Map;

/**
 * Interface Class for Company Service.
 *
 * @author HNDK
 */
public interface ICompanyService {

    /**
     * The purpose of this method is find all Company info.
     *
     * @return companies as list
     */
    public List<Company> findAllCompany();

    /**
     * The purpose of this method is add new Company info.
     *
     * @param company
     * @throws CompetitorException
     */
    public void addCompany(Company company) throws CompetitorException;

    /**
     * The purpose of this method is add new Company info.
     *
     * @param updateCompany
     * @throws CompetitorException
     */
    public void updateCompany(Company updateCompany) throws CompetitorException;

    /**
     * The purpose of this method is to validate new Company info.
     *
     * @param company
     * @param companyList
     * @return boolean validated value
     */
    public Map<String, String> validateCompanyInfo(Company company, List<Company> companyList);
    /**
     * The purpose of this method is find Company By Company id.
     *
     * @param companyId
     * @return Company
     */
    public Company findCompanyByCompanyId(int companyId);
    /**
     * The purpose of this method is find Company List By Plant Location id.
     *
     * @param locationId
     * @return Company List
     */
    public List<Company> getCompanyByPlantLocationId(int locationId);
    /**
     * The purpose of this method is find Company By Company Code.
     *
     * @param companyCode
     * @return Company
     */
    public Company findCompanyByCompanyCode(String companyCode);

    public List<Company> removeCompanyDuplicate(List<Company> companyList);

    public List<GoogleMapLegendsDTO> removeMapIconsDuplicate(List<GoogleMapLegendsDTO> googleMapIcons);
}
