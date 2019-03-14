/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dao;

import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.pojo.entity.Capabilities;
import com.panu.competitor.information.pojo.entity.Plant;
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
public class CapabilityDAO extends BaseDAO<Capabilities> {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public CapabilityDAO() {
        super(Capabilities.class);
    }
    
    public Capabilities getCapabilitesByCapabilyIds(String capabilityShortName) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Capabilities capabiliy = new Capabilities();
        try {
            Query query = session.createQuery("SELECT c FROM Capabilities c WHERE c.shortName =:capabilityShortName AND c.isDelete =:isDelete");
            query.setParameter("capabilityShortName", capabilityShortName);
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            List<Capabilities> list = query.list();
            if (list != null && list.size() > 0) {
                capabiliy = list.get(0);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        }
        return capabiliy;
    }
}
