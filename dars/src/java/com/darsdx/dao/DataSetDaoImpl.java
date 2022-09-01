/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DataSet;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DataSetDaoImpl implements DataSetDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveDataSet(DataSet dst) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(dst);
            tx.commit();
            closeSession(session);  
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void updateDataSet(DataSet dst) throws Exception
    {
        try
        {
            DataSet dst2=getDataSet(dst.getDatasetId(),dst.getDhisId());
            if(dst2 !=null)
            {
                dst.setRecordId(dst2.getRecordId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(dst);
                tx.commit();
                closeSession(session);  
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void deleteDataSet(DataSet dst) throws Exception
    {
        try
        {
            DataSet dst2=getDataSet(dst.getDatasetId(),dst.getDhisId());
            if(dst2 !=null)
            {
                dst.setRecordId(dst2.getRecordId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(dst);
                tx.commit();
                closeSession(session); 
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public DataSet getDataSet(String dataSetId) throws Exception
    {
        DataSet dst=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataSet dst where dst.datasetId=:dstid").setString("dstid", dataSetId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dst=(DataSet)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dst;
    }
    public DataSet getDataSet(String dataSetId,String dhisId) throws Exception
    {
        DataSet dst=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataSet dst where dst.datasetId=:dstid and dst.dhisId=:did").setString("dstid", dataSetId).setString("did", dhisId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dst=(DataSet)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dst;
    }
    public List getAllDataSets() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataSet dst").list();
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
    public List getDataSetsByDhisId(String dhisId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataSet dst where dst.dhisId=:dhId").setString("dhId", dhisId).list();
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
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
