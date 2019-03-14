/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dao;

import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.pojo.entity.Company;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author HNDK
 */
@Named
public class CompanyDAO extends BaseDAO<Company> {

    @Autowired
    private SessionFactory sessionFactory;

    public CompanyDAO() {
        super(Company.class);
    }

    public List<Company> getCompanyByPlantLocationId(int zoneId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        List<Company> companyList = new ArrayList<Company>();
        try {
            Query query = session.createQuery("SELECT c FROM Company c,Plant p WHERE c.companyId = p.companyId AND p.zone =:zoneId AND p.isDelete =:isDelete AND c.isDelete =:isDelete");
            query.setParameter("zoneId", zoneId);
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            List<Company> list = query.list();
            if (list != null && list.size() > 0) {
                companyList.addAll(list);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        }
        return companyList;
    }

}
