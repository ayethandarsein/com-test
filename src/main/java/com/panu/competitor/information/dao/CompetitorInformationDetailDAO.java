/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dao;

import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import com.panu.competitor.information.pojo.entity.Plant;
import java.text.ParseException;
import java.util.List;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
public class CompetitorInformationDetailDAO extends BaseDAO<PlantInformationDetail> {

    @Autowired
    private SessionFactory sessionFactory;

    public CompetitorInformationDetailDAO() {
        super(PlantInformationDetail.class);
    }

    public List<PlantInformationDetail> getPreviousCompetiorInfoDetailByCompanyIdAndPeriod(String companyName, String period) throws ParseException {
        Date date = new SimpleDateFormat("MMM-yyyy").parse(period);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        String previousPeriod = new SimpleDateFormat("MMM-yyyy").format(calendar.getTime());
        Date periodDate = new SimpleDateFormat("MMM-yyyy").parse(previousPeriod);
        PlantInformationDetail competitorInfoDetail;
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        List<PlantInformationDetail> competiorInfoDetailList = new ArrayList<PlantInformationDetail>();
        try {
            Query query = session.createQuery("SELECT i from PlantInformationDetail i, PlantInformation c, Plant p WHERE i.isDelete =:isDelete AND c.isDelete =:isDelete AND i.competitorInfoId = c.competitorInfoId AND c.companyName =:companyName AND c.period =:period AND p.plantId = i.plantId AND p.activeStatus =:activeStatus");
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            query.setParameter("companyName", companyName);
            query.setParameter("activeStatus", ConstantCommon.STATUS_ACTIVE);
            query.setParameter("period", periodDate);
            List<PlantInformationDetail> list = query.list();
            trans.commit();
            if (list != null && list.size() > 0) {
                competiorInfoDetailList = list;
            }
            for (int i = 0; i < competiorInfoDetailList.size(); i++) {
                competitorInfoDetail = new PlantInformationDetail();
                competitorInfoDetail = competiorInfoDetailList.get(i);
                competiorInfoDetailList.set(i, competitorInfoDetail);
            }
        } catch (Exception e) {
            trans.rollback();
        }
        return competiorInfoDetailList;
    }

    public List<PlantInformationDetail> getCompetiorInfoDetailByCompanyShortNameAndPeriod(String companyName, String period) throws ParseException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Date date = new SimpleDateFormat("MMM-yyyy").parse(period);
        List<PlantInformationDetail> competiorInfoDetailList = new ArrayList<PlantInformationDetail>();
        PlantInformationDetail competitorInfoDetail;
        try {
            Query query = session.createQuery("SELECT i from PlantInformationDetail i, PlantInformation c,Plant p WHERE i.isDelete =:isDelete AND c.isDelete =:isDelete AND i.competitorInfoId = c.competitorInfoId AND c.companyName =:companyName AND c.period =:period AND p.plantId = i.plantId AND p.activeStatus =:activeStatus");
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            query.setParameter("companyName", companyName);
            query.setParameter("activeStatus", ConstantCommon.STATUS_ACTIVE);
            query.setParameter("period", date);
            List<PlantInformationDetail> list = query.list();
            trans.commit();
            if (list != null && list.size() > 0) {
                competiorInfoDetailList = list;
            }
            for (int i = 0; i < competiorInfoDetailList.size(); i++) {
                competitorInfoDetail = new PlantInformationDetail();
                competitorInfoDetail = competiorInfoDetailList.get(i);
                competiorInfoDetailList.set(i, competitorInfoDetail);
            }
        } catch (Exception e) {
            trans.rollback();
        }
        return competiorInfoDetailList;
    }

    public List<PlantInformationDetail> getAllCompetiorInfoDetailFromPlant(String companyName, String period) throws ParseException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Date date = new SimpleDateFormat("MMM-yyyy").parse(period);
        List<PlantInformationDetail> competiorInfoDetailList = new ArrayList<PlantInformationDetail>();
        try {
            Query query = session.createQuery("SELECT p FROM Plant p,Company c WHERE p.companyId = c.companyId and c.isDelete=:isDelete and c.companyCode =:companyName and p.isDelete =:isDelete and p.activeStatus=:activeStatus and p.plantPeriod<=:period");
            query.setParameter("companyName", companyName);
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            query.setParameter("activeStatus", ConstantCommon.STATUS_ACTIVE);
            query.setParameter("period", date);
            List<Plant> list = query.list();
            trans.commit();
            if (list != null && list.size() > 0) {
                for (Plant plant : list) {
                    PlantInformationDetail competitorInfoDetail = new PlantInformationDetail();
                    competitorInfoDetail.setPlantId(plant.getPlantId());
                    competitorInfoDetail.setLocation(plant.getLocation());
                    competitorInfoDetail.setZone(plant.getZone());
                    competitorInfoDetail.setLatitude(plant.getLatitude().toString());
                    competitorInfoDetail.setLongitude(plant.getLongitude().toString());
                    competitorInfoDetail.setNoOfPlant(1);
                    competitorInfoDetail.setVolumePerMonth(1200);
                    competitorInfoDetail.setCapabilities("");
                    competiorInfoDetailList.add(competitorInfoDetail);
                }
            }

//            competiorInfoDetailList = remvoeDuplicateListAndCalculateCount(competiorInfoDetailList);
        } catch (Exception e) {
            trans.rollback();
        }
        return competiorInfoDetailList;
    }

    public List<PlantInformationDetail> remvoeDuplicateListAndCalculateCount(List<PlantInformationDetail> competiorInfoDetailList) {
        List<PlantInformationDetail> newList = new ArrayList<PlantInformationDetail>();
        Map<Integer, Integer> counts = new HashMap<Integer,Integer>();
        for (PlantInformationDetail competiorInfoDetail : competiorInfoDetailList) {
            if (counts.containsKey(competiorInfoDetail.getLocation())) {
                counts.put(competiorInfoDetail.getLocation(), counts.get(competiorInfoDetail.getLocation()) + 1);
            } else {
                counts.put(competiorInfoDetail.getLocation(), 1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            PlantInformationDetail competitorInfoDetail = new PlantInformationDetail();
            competitorInfoDetail.setLocation(entry.getKey());
            competitorInfoDetail.setNoOfPlant(entry.getValue());
            newList.add(competitorInfoDetail);
        }
        return newList;
    }

    public List<PlantInformationDetail> findSearchValueList(List<Integer> cominfodetailid, List<Integer> location, List<Integer> zone, int nooftruck, char hdbapproved, List<String> capabilities) {
        Transaction transaction = null;
        List<PlantInformationDetail> comInfoDetail = new ArrayList<PlantInformationDetail>();
        try {
            Session session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PlantInformationDetail> queryQ = builder.createQuery(PlantInformationDetail.class);
            Root<PlantInformationDetail> root = queryQ.from(PlantInformationDetail.class);
            List<Predicate> predicates = new ArrayList<Predicate>();

            if (!cominfodetailid.isEmpty()) {
                List<Predicate> subPredicate = new ArrayList<Predicate>();
                for (Integer Cominfodetailid : cominfodetailid) {
                    Predicate condition = builder.or(builder.equal(root.get("competitorInfoId"), Cominfodetailid));
                    subPredicate.add(condition);
                }
                Predicate predicat = builder.or(subPredicate.toArray(new Predicate[subPredicate.size()]));
                predicates.add(predicat);
            }
            if (!location.isEmpty()) {
                List<Predicate> subPredicate = new ArrayList<Predicate>();
                for (Integer Location : location) {
                    Predicate condition = builder.and(builder.equal(root.get("location"), Location));
                    subPredicate.add(condition);
                }
                Predicate predicat = builder.or(subPredicate.toArray(new Predicate[subPredicate.size()]));
                predicates.add(predicat);
            }
//            if (zone != 0) {
//                Predicate condition = builder.and(builder.equal(root.get("zone"), zone));
//                predicates.add(condition);
//            }
            if (!zone.isEmpty()) {
                List<Predicate> subPredicate = new ArrayList<Predicate>();
                for (Integer Zone : zone) {
                    Predicate condition = builder.and(builder.equal(root.get("zone"), Zone));
                    subPredicate.add(condition);
                }
                Predicate predicat = builder.or(subPredicate.toArray(new Predicate[subPredicate.size()]));
                predicates.add(predicat);
            }
            if (nooftruck != 0) {
                Predicate condition = builder.and(builder.equal(root.get("noOfTruck"), nooftruck));
                predicates.add(condition);
            }
            if (hdbapproved != 0) {
                Predicate condition = builder.and(builder.equal(root.get("hdbapprove"), hdbapproved));
                predicates.add(condition);
            }
            if (!capabilities.isEmpty()) {
                String listString = "";
                for (String s : capabilities) {
                    listString += s + ",";
                }
                listString = listString.substring(0, listString.lastIndexOf(","));
                Predicate condition = builder.and(builder.like(root.<String>get("capabilities"), "%" + listString + "%"));
                predicates.add(condition);
            }

            queryQ.select(root);
            Query<PlantInformationDetail> q = session.createQuery(queryQ.where(builder.and(predicates.toArray(new Predicate[predicates.size()]))));
            comInfoDetail = q.getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return comInfoDetail;
    }

    public List<PlantInformationDetail> getCompetitorInfoDetialByCompanyShortName(String companyCode) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        List<PlantInformationDetail> competiorInfoDetailList = new ArrayList<PlantInformationDetail>();
        try {
            Query query = session.createQuery("SELECT d FROM PlantInformation c,PlantInformationDetail d,Plant p WHERE c.competitorInfoId=d.competitorInfoId AND c.companyName =:companyCode AND c.isDelete =:isDelete AND d.isDelete =:isDelete AND p.plantId = d.plantId AND p.activeStatus =:activeStatus");
            query.setParameter("companyCode", companyCode);
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            query.setParameter("activeStatus", ConstantCommon.STATUS_ACTIVE);
            List<PlantInformationDetail> list = query.list();
            trans.commit();
            if (list != null && list.size() > 0) {
                competiorInfoDetailList = list;
            }

        } catch (Exception e) {
            trans.rollback();
        }
        return competiorInfoDetailList;
    }

    public List<PlantInformationDetail> getNewCompetitorInfoDetailByCompanyNameAndPlantPeriod(String companyName, String period) throws ParseException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Date date = new SimpleDateFormat("MMM-yyyy").parse(period);
        List<PlantInformationDetail> competiorInfoDetailList = new ArrayList<PlantInformationDetail>();
        try {
            Query query = session.createQuery("SELECT p FROM Plant p,Company c WHERE p.companyId = c.companyId and c.isDelete=:isDelete and c.companyCode =:companyName and p.plantPeriod =:period and p.isDelete =:isDelete and p.activeStatus =:activeStatus");
            query.setParameter("companyName", companyName);
            query.setParameter("period", date);
            query.setParameter("activeStatus", ConstantCommon.STATUS_ACTIVE);
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            List<Plant> list = query.list();
            trans.commit();
            if (list != null && list.size() > 0) {
                for (Plant plant : list) {
                    PlantInformationDetail competitorInfoDetail = new PlantInformationDetail();
                    competitorInfoDetail.setPlantId(plant.getPlantId());
                    competitorInfoDetail.setLocation(plant.getLocation());
                    competitorInfoDetail.setLatitude(plant.getLatitude().toString());
                    competitorInfoDetail.setLongitude(plant.getLongitude().toString());
                    competitorInfoDetail.setZone(plant.getZone());
                    competitorInfoDetail.setNoOfPlant(1);
                    competitorInfoDetail.setVolumePerMonth(1200);
                    competitorInfoDetail.setCapabilities("");
                    competiorInfoDetailList.add(competitorInfoDetail);
                }
            }

//            competiorInfoDetailList = remvoeDuplicateListAndCalculateCount(competiorInfoDetailList);
        } catch (Exception e) {
            trans.rollback();
        }
        return competiorInfoDetailList;
    }

    public List<PlantInformationDetail> getCompetiorInfoDetailByCompanyIdAndPeriodAndZone(String companyCode, String plantPeriod, int codeSetupId) throws ParseException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Date date = new SimpleDateFormat("MMM-yyyy").parse(plantPeriod);
        List<PlantInformationDetail> competiorInfoDetailList = new ArrayList<PlantInformationDetail>();
        PlantInformationDetail competitorInfoDetail;
        try {
            Query query = session.createQuery("SELECT i from PlantInformationDetail i, PlantInformation c,Plant p WHERE i.isDelete =:isDelete AND c.isDelete =:isDelete AND i.competitorInfoId = c.competitorInfoId AND c.companyName =:companyName AND c.period =:period AND i.zone =:zone AND p.plantId = i.plantId AND p.activeStatus =:activeStatus");
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            query.setParameter("companyName", companyCode);
            query.setParameter("activeStatus", ConstantCommon.STATUS_ACTIVE);
            query.setParameter("period", date);
            query.setParameter("zone", codeSetupId);
            List<PlantInformationDetail> list = query.list();
            trans.commit();
            if (list != null && list.size() > 0) {
                competiorInfoDetailList = list;
            }
            for (int i = 0; i < competiorInfoDetailList.size(); i++) {
                competitorInfoDetail = new PlantInformationDetail();
                competitorInfoDetail = competiorInfoDetailList.get(i);
                competiorInfoDetailList.set(i, competitorInfoDetail);
            }
        } catch (Exception e) {
            trans.rollback();
        }
        return competiorInfoDetailList;
    }

    public List<PlantInformationDetail> getCompetiorInfoDetailByCompanyIdAndPeriod(int companyId, String period) throws ParseException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Date date = new SimpleDateFormat("MMM-yyyy").parse(period);
        List<PlantInformationDetail> competiorInfoDetailList = new ArrayList<PlantInformationDetail>();
        PlantInformationDetail competitorInfoDetail;
        try {
            Query query = session.createQuery("SELECT i from PlantInformationDetail i, PlantInformation c,Plant p, Company co WHERE i.isDelete =:isDelete AND c.isDelete =:isDelete AND i.competitorInfoId = c.competitorInfoId AND c.companyName = co.companyCode AND c.period =:period AND p.plantId = i.plantId AND p.activeStatus =:activeStatus AND co.companyId =:companyId");
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            query.setParameter("companyId", companyId);
            query.setParameter("activeStatus", ConstantCommon.STATUS_ACTIVE);
            query.setParameter("period", date);
            List<PlantInformationDetail> list = query.list();
            trans.commit();
            if (list != null && list.size() > 0) {
                competiorInfoDetailList = list;
            }
            for (int i = 0; i < competiorInfoDetailList.size(); i++) {
                competitorInfoDetail = new PlantInformationDetail();
                competitorInfoDetail = competiorInfoDetailList.get(i);
                competiorInfoDetailList.set(i, competitorInfoDetail);
            }
        } catch (Exception e) {
            trans.rollback();
        }
        return competiorInfoDetailList;
    }

    public List<PlantInformationDetail> getCompetiorInfoDetailByLeasePeriodTo(int companyId, String periodto6months) throws ParseException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Date previous6date = new SimpleDateFormat("MMM-yyyy").parse(periodto6months);
        Date currentDate = new Date();
        List<PlantInformationDetail> competiorInfoDetailList = new ArrayList<PlantInformationDetail>();
        PlantInformationDetail competitorInfoDetail;
        try {
            Query query = session.createQuery("SELECT i from PlantInformationDetail i, PlantInformation c,Plant p, Company co WHERE i.isDelete =:isDelete AND c.isDelete =:isDelete AND i.competitorInfoId = c.competitorInfoId AND c.companyName = co.companyCode AND i.leasePeriodToDate >= :currentDate AND i.leasePeriodToDate <=:previous6date AND p.plantId = i.plantId AND p.activeStatus =:activeStatus AND co.companyId =:companyId");
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            query.setParameter("companyId", companyId);
            query.setParameter("activeStatus", ConstantCommon.STATUS_ACTIVE);
            query.setParameter("currentDate", currentDate);
            query.setParameter("previous6date", previous6date);
            List<PlantInformationDetail> list = query.list();
            trans.commit();
            if (list != null && list.size() > 0) {
                competiorInfoDetailList = list;
            }
            for (int i = 0; i < competiorInfoDetailList.size(); i++) {
                competitorInfoDetail = new PlantInformationDetail();
                competitorInfoDetail = competiorInfoDetailList.get(i);
                competiorInfoDetailList.set(i, competitorInfoDetail);
            }
        } catch (Exception e) {
            trans.rollback();
        }
        return competiorInfoDetailList;
    }

    public long getPlantInfoDetailCount(String companyShortName) {
        long count = 0;
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        try {
            Query query = session.createQuery("SELECT COUNT(*) FROM PlantInformation c,PlantInformationDetail d,Plant p WHERE c.competitorInfoId=d.competitorInfoId AND c.companyName =:companyCode AND c.isDelete =:isDelete AND d.isDelete =:isDelete AND p.plantId = d.plantId AND p.activeStatus =:activeStatus");
            query.setParameter("companyCode", companyShortName);
            query.setParameter("isDelete", ConstantCommon.STATUS_ISDELETE);
            query.setParameter("activeStatus", ConstantCommon.STATUS_ACTIVE);
            List list = query.list();
            trans.commit();
            if (list != null && list.size() > 0) {
                count = (long) list.get(0);
            }

        } catch (Exception e) {
            trans.rollback();
        }
        return count;
    }
}
