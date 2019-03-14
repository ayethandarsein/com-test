//## BaseDAO 1.0
//## Copyright 2018-2018 by PanUnited, Ltd. All right reserved.
//## 2018-05-03 01-00 Create
package com.panu.competitor.information.dao;

import com.panu.competitor.information.exception.CompetitorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author PS
 * @param <T>
 */
@Named
public abstract class BaseDAO<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private final Class<T> _entityClass;

    @Autowired
    public BaseDAO(Class<T> pEntityClass) {
        _entityClass = pEntityClass;
    }

    /**
     * The purpose of this method is to select all information.
     *
     * @param queryConstant
     * @return
     */
    public List<T> select(String queryConstant) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        List<T> tList = new ArrayList<T>();
        try {
            Query query = session.createNamedQuery(queryConstant);
            tList = query.list();
            trans.commit();
        } finally {
            trans.rollback();
            session.close();
        }
        return tList;
    }

    /**
     * The purpose of this method is to select information with condition.
     *
     * @param queryConstant
     * @param paramColumnList
     * @param paramValueList  
     * @return
     */
    public List<T> selectByStringType(String queryConstant, List<String> paramColumnList, List<String> paramValueList) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        List<T> tList = new ArrayList<T>();
        try {
            Query query = session.createNamedQuery(queryConstant);
            List<String> paramColums = paramColumnList;
            List<String> paramValues = paramValueList;
            for (int i = 0; i < paramColums.size(); i++) {
                query.setParameter(paramColums.get(i), paramValues.get(i));
            }
            tList = query.list();
            trans.commit();
        } finally {
            trans.rollback();
            session.close();
        }
        return tList;
    }

     /**
     * The purpose of this method is to select information with condition.
     *
     * @param queryConstant
     * @param paramColumnList
     * @param paramValueList
     * @return
     */
    public List<T> selectByIntType(String queryConstant, List<String> paramColumnList, List<Integer> paramValueList) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        List<T> tList = new ArrayList<T>();
        try {
            Query query = session.createNamedQuery(queryConstant);
            List<String> paramColums = paramColumnList;
            List<Integer> paramValues = paramValueList;
            for (int i = 0; i < paramColums.size(); i++) {
                query.setParameter(paramColums.get(i), paramValues.get(i));
            }
            tList = query.list();
            trans.commit();
        } finally {
           trans.rollback();
            session.close();
        }
        return tList;
    }
    
    /**
     * The purpose of this method is to select information by id.
     * @param t
     * @param id
     * @return
     * @throws CompetitorException 
     */
    public T select(Class t, Serializable id) throws CompetitorException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        try {
            T persistentInstance = (T) session.get(t, id);
            return persistentInstance;
        } catch (ObjectNotFoundException e) {
            throw new CompetitorException();
        } finally {
            trans.commit();
            session.close();
        }
    }

    /**
     * The purpose of this method is to insert information.
     *
     * @param t
     * @throws CompetitorException
     */
    public void insert(T t) throws CompetitorException {
        
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        try {
            session.save(t);
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
                throw new CompetitorException();
            }
        } finally {
            session.close();
        }
    }
    /**
     * The purpose of this method is to delete information.
     * @param t
     * @throws CompetitorException 
     */
     public void delete(T t) throws CompetitorException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        try {
            session.delete(t);
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
                throw new CompetitorException();
            }
        } finally {
            session.close();
        }
    }
    
    /**
     * The purpose of this method is to insert information.
     *
     * @param t
     * @throws CompetitorException
     */
    public void add(T t) throws CompetitorException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        try {            
            session.persist(t);
            trans.commit();
        } catch (Exception e) { 
            if (trans != null) {
                trans.rollback();
                throw new CompetitorException();
            }
        } finally {
            session.close();
        }
    }

    /**
     * The purpose of this method is to update information.
     *
     * @param t
     * @throws CompetitorException
     */
    public void update(T t) throws CompetitorException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        try {
            session.saveOrUpdate(t);
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
                throw new CompetitorException();
            }
        } finally {
            session.close();
        }
    }
    
    /**
     * Getter for SessionFactory.
     * @return 
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Setter for sessionFactory.
     * @param sessionFactory 
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
