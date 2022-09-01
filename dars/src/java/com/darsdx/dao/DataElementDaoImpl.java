/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DataElement;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DataElementDaoImpl implements DataElementDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    
    public DataElement getDataElement(String dataElementId) throws Exception
    {
        DataElement de=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataElement de where de.dataElementId=:deid").setString("deid", dataElementId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                de=(DataElement)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return de;
    }
    public DataElement getDataElement(String dhisId,String dataElementId) throws Exception
    {
        DataElement de=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataElement de where de.dhisId=:did and de.dataElementId=:deid").setString("did", dhisId).setString("deid", dataElementId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                de=(DataElement)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return de;
    }
    public List getAllDataElements() throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataElement de").list();
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
    public List getDataElementsByDhisId(String dhisId) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataElement de where de.dhisId=:did").setString("did", dhisId).list();
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
    public void saveDataElement(DataElement de) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(de);
            tx.commit();
            closeSession(session);  
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void updateDataElement(DataElement de) throws Exception
    {
        try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(de);
                tx.commit();
                closeSession(session);  
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
    }
    public void deleteDataElement(DataElement de) throws Exception
    {
        try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(de);
                tx.commit();
                closeSession(session);  
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
