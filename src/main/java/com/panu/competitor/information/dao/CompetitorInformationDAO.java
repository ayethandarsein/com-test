/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dao;

import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.pojo.entity.PlantInformation;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Htet Nanda Kyaw
 */
@Named
public class CompetitorInformationDAO extends BaseDAO<PlantInformation> {

    @Autowired
    private SessionFactory sessionFactory;

    public CompetitorInformationDAO() {
        super(PlantInformation.class);
    }

    public List<PlantInformation> findCompetitorInfoIDByCompanyName(String companyname, List<Date> fromToAllPeriod) {
        Transaction transaction = null;
        List<PlantInformation> comInfo = new ArrayList<PlantInformation>();
        try {
            Session session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PlantInformation> queryQ = builder.createQuery(PlantInformation.class);
            Root<PlantInformation> root = queryQ.from(PlantInformation.class);
            List<Predicate> predicates = new ArrayList<Predicate>();

            if (!companyname.isEmpty()) {
                Predicate condition = builder.or(builder.equal(root.get("companyName"), companyname));
                predicates.add(condition);
            }

            if (!fromToAllPeriod.isEmpty()) {
                List<Predicate> predicatesOr = new ArrayList<Predicate>();
                for (Date FromToAllPeriod : fromToAllPeriod) {
                    Predicate condition = builder.or(builder.equal(root.get("period"), FromToAllPeriod));
                    predicatesOr.add(condition);
                }
                Predicate conditionFinal = builder.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                predicates.add(conditionFinal);
            }
            
            queryQ.select(root);
            queryQ.orderBy(builder.asc(root.get("period")));
            Query<PlantInformation> q = session.createQuery(queryQ.where(builder.and(predicates.toArray(new Predicate[predicates.size()]))));
            comInfo = q.getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return comInfo;
    }

    public List<PlantInformation> findCompetitorInfoIDByCompanyNameAndPeriod(List<String> companyname, Date period) {
        Transaction transaction = null;
        List<PlantInformation> comInfo = new ArrayList<PlantInformation>();
        try {
            Session session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PlantInformation> queryQ = builder.createQuery(PlantInformation.class);
            Root<PlantInformation> root = queryQ.from(PlantInformation.class);
            List<Predicate> predicates = new ArrayList<Predicate>();

            if (!companyname.isEmpty()) {
                List<Predicate> subPredicate = new ArrayList<Predicate>();
                for (String Companyname : companyname) {
                    Predicate condition = builder.and(builder.equal(root.get("companyName"), Companyname));
                    subPredicate.add(condition);
                }
                Predicate predicat = builder.or(subPredicate.toArray(new Predicate[subPredicate.size()]));
                predicates.add(predicat);
            }

            if (period != null) {
                Predicate condition = builder.and(builder.equal(root.<Date>get("period"), period));
                predicates.add(condition);
            }

            queryQ.select(root);
            Query<PlantInformation> q = session.createQuery(queryQ.where(builder.and(predicates.toArray(new Predicate[predicates.size()]))));
            comInfo = q.getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return comInfo;
    }

    public void updatePlantInfoCompanyName(String oldName, String newName) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        try {
            String query = "UPDATE PlantInformation p SET p.companyName ='" + newName + "' WHERE p.companyName='" + oldName + "' AND p.isDelete =" + ConstantCommon.STATUS_ISDELETE;
            session.createQuery(query).executeUpdate();
            trans.commit();

        } catch (Exception e) {
            trans.rollback();
        }
    }

    public PlantInformation getPlantInfoByCompanyNameAndPeriod(String companyName, String period) throws ParseException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Date date = new SimpleDateFormat("MMM-yyyy").parse(period);
        PlantInformation plantInformation = new PlantInformation();
        try {
            Query query = session.createQuery("SELECT c FROM PlantInformation c WHERE c.companyName =:companyName AND c.period =:period AND c.isDelete =:isDelete");
            query.setParameter("companyName", companyName);
            query.setParameter("period", date);
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            List<PlantInformation> list = query.list();
            trans.commit();
            if (list != null && list.size() > 0) {
                plantInformation = list.get(0);
            }

        } catch (Exception e) {
            trans.rollback();
        }
        return plantInformation;
    }

}
