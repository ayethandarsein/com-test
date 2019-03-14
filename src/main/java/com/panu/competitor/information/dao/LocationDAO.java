/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dao;

import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.pojo.entity.Location;
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
public class LocationDAO extends BaseDAO<Location> {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public LocationDAO() {
        super(Location.class);
    }
    
    public Location geLocationByLocationId(String name) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Location capabiliy = new Location();
        try {
            Query query = session.createQuery("SELECT c FROM Location c WHERE c.name =:name AND c.isDelete =:isDelete");
            query.setParameter("name", name);
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            List<Location> list = query.list();
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
