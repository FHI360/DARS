/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.CategoryOptionCombo;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class CategoryOptionComboDaoImpl implements CategoryOptionComboDao 
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public List getCategoryOptionCombinationsByDhisId(String dhisId) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from CategoryOptionCombo cc where cc.dhisId=:did").setString("did", dhisId).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }

       return list;
    }
    public CategoryOptionCombo getCategoryOptionCombination(String categoryOptionComboId) throws Exception
    {
        CategoryOptionCombo cc=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from CategoryOptionCombo cc where cc.categoryOptionComboId=:ccid").setString("ccid", categoryOptionComboId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                cc=(CategoryOptionCombo)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }

        return cc;
    }
    public CategoryOptionCombo getCategoryOptionCombination(String dhisId,String categoryOptionComboId) throws Exception
    {
        CategoryOptionCombo cc=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from CategoryOptionCombo cc where cc.dhisId=:did and cc.categoryOptionComboId=:ccid").setString("did", dhisId).setString("ccid", categoryOptionComboId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                cc=(CategoryOptionCombo)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }

        return cc;
    }
    public CategoryOptionCombo getCategoryOptionComboByName(String categoryOptionComboName) throws Exception
    {
        CategoryOptionCombo cc=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from CategoryOptionCombo cc where cc.categoryOptionComboName=:ccn").setString("ccn", categoryOptionComboName).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                cc=(CategoryOptionCombo)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }

        return cc;
    }
    public List getAllCategoryOptionCombinations() throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from CategoryOptionCombo cc").list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }

       return list;
    }
    public void saveCategoryOptionCombination(CategoryOptionCombo cc) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(cc);
            tx.commit();
            closeSession(session);  
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void updateCategoryOptionCombination(CategoryOptionCombo cc) throws Exception
    {
        try
        {
            if(getCategoryOptionCombination(cc.getCategoryOptionComboId()) !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(cc);
                tx.commit();
                closeSession(session);  
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void deleteCategoryOptionCombination(CategoryOptionCombo cc) throws Exception
    {
        try
        {
            if(getCategoryOptionCombination(cc.getCategoryOptionComboId()) !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(cc);
                tx.commit();
                closeSession(session);  
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
