/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.SourceData;
import com.darsdx.util.AppUtility;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class SourceDataDaoImpl implements SourceDataDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    public List getDistinctDhisInstances() throws Exception
    {
        List list=null;
         try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct sd.dhisInstance from SourceData sd").list();
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
    public List getSourceDataByDataElementCategoryAndInstance(String dataElement,String categoryCombo,String instance) throws Exception
    {
        List list=null;
         try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from SourceData sd where sd.dataElementName=:de and sd.categoryOptionComboName=:cc and sd.dhisInstance=:inst").setString("de", dataElement).setString("cc", categoryCombo).setString("inst", instance).list();
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
    public List getSourceDataByDataElementAndInstance(String dataElement,String instance) throws Exception
    {
        List list=null;
         try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from SourceData sd where sd.dataElementName=:de and sd.dhisInstance=:inst").setString("de", dataElement).setString("inst", instance).list();
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
    public List getDistinctDataElements(String instance) throws Exception
    {
        List list=null;
         try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct sd.dataElementName from SourceData sd where sd.dhisInstance=:inst").setString("inst", instance).list();
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
    public int getNumberOfSourceDataSaved() throws Exception
    {
        List list=null;
        int count=0;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count (sd.recordId) from SourceData sd").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
        return count;
    }
    
    public List getSourceData(String instance) throws Exception
    {
        List list=null;
         try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from SourceData sd where sd.dhisInstance=:inst").setString("inst", instance).list();
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
    
    public List getAllSourceData() throws Exception
    {
        List list=null;
         try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from SourceData sd").list();
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
    public SourceData getSourceDataById(int id) throws Exception
    {
        SourceData sd=null;
        
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from SourceData sd where sd.recordId=:uid").setInteger("uid", id).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            { 
               sd=(SourceData)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       return sd;
    }
    public SourceData getSourceData(String organizationUnit,String dataElement,String category,String startDate) throws Exception
    {
        SourceData sd=null;
        
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from SourceData sd where sd.organizationUnit=:ou and sd.dataElementName=:de and sd.categoryOptionComboName=:cc and sd.startDate=:std").setString("ou", organizationUnit).setString("de", dataElement).setString("cc", category).setString("std", startDate).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            { 
               sd=(SourceData)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       return sd;
    }
    public void saveSourceData(SourceData sd) throws Exception
    {
        if(sd !=null)
        {
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(sd);
                tx.commit();
                closeSession(session);  
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void updateSourceData(SourceData sd) throws Exception
    {
        if(sd !=null)
        {
            try
            {
                SourceData sd2=getSourceData(sd.getOrganizationUnit(),sd.getDataElementName(),sd.getCategoryOptionComboName(),sd.getStartDate());
                if(sd2 !=null)
                {
                    sd.setRecordId(sd2.getRecordId());
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.delete(sd);
                    tx.commit();
                    closeSession(session);
                }
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void deleteSourceData(SourceData sd) throws Exception
    {
        if(sd !=null)
        {
            try
            {
                if(getSourceDataById(sd.getRecordId()) !=null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.delete(sd);
                    tx.commit();
                    closeSession(session);
                }
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
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
