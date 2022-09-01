/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DatavalueResource;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DatavalueResourceDaoImpl implements DatavalueResourceDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public DatavalueResource getDatavalueResource(String dhisId) throws Exception
    {
        DatavalueResource dv=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DatavalueResource dv where dv.dhisId=:id").setString("id", dhisId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dv=(DatavalueResource)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dv;
    }
    public List getAllDatavalueResources() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatavalueResource dv").list();
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
    public void saveOrUpdateDatavalueResource(DatavalueResource dvr) throws Exception
    {
        DatavalueResource dvr2=this.getDatavalueResource(dvr.getDhisId());
        if(dvr2==null)
        saveDatavalueResource(dvr);
        else
        updateDatavalueResource(dvr);
    }
    public void saveDatavalueResource(DatavalueResource dvr) throws Exception
    {
        try
        {
            if(dvr !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(dvr);
                tx.commit();
                closeSession(session); 
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void updateDatavalueResource(DatavalueResource dvr) throws Exception
    {
        try
        {
            if(dvr !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(dvr);
                tx.commit();
                closeSession(session); 
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void deleteDatavalueResource(DatavalueResource dvr) throws Exception
    {
        try
        {
            if(dvr !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(dvr);
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
