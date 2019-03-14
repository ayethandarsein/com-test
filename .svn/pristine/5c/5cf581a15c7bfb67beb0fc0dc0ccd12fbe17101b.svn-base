/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dao;

import com.panu.competitor.information.common.ConstantCommon;
import com.panu.competitor.information.pojo.entity.LegalIssue;
import java.util.List;
import javax.inject.Named;
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
public class LegalIssueDAO extends BaseDAO<LegalIssue> {

    @Autowired
    private SessionFactory sessionFactory;

    public LegalIssueDAO() {
        super(LegalIssue.class);
    }

    public void removeLegalIssueIdList(List<Integer> legalIssueIdList) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        try {
            legalIssueIdList.stream().map((legalissueIds) -> "UPDATE LegalIssue SET isDelete=" + ConstantCommon.STATUS_DELETE
                    + " WHERE legalIssueId=" + legalissueIds).forEach((query) -> {
                        session.createQuery(query).executeUpdate();
                    });
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        }
    }

    public void deleteLegalIssueByCompanyId(int companyId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        try {
            String query = "update LegalIssue i set i.isDelete =" + ConstantCommon.STATUS_DELETE + " where i.companyId =" + companyId;
            session.createQuery(query).executeUpdate();
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        }
    }
}
