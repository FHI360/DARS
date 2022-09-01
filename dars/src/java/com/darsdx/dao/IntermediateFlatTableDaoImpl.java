/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.IntermediateFlatTable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class IntermediateFlatTableDaoImpl implements IntermediateFlatTableDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public List getIntermediateFlatTableByOrgUnitNameAndInstance(String consumerOrganizationUnitName,String producerInstanceId,String consumerInstanceId) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from IntermediateFlatTable ift where ift.consumerOrganizationUnitName=:ouname and ift.producerInstance=:pinst and ift.consumerInstance=:cinst").setString("ouname", consumerOrganizationUnitName).setString("pinst", producerInstanceId).setString("cinst", consumerInstanceId).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            session.close();
            throw new Exception(ex);
        }
        return list;
    }
    public List aggregateRecordsWithSameOrgUnitDataElementAndCategory(String consumerOrganizationUnitName,String producerInstanceId,String consumerInstanceId) throws Exception
    {
        List list=null;
        try
        { 
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ift.consumerOrganizationUnitName, ift.consumerDataElementName, ift.consumerCategoryName, ift.startDate,sum(ift.value) from IntermediateFlatTable ift where ift.consumerOrganizationUnitName=:cou and ift.producerInstance=:pinst and ift.consumerInstance=:cinst group by ift.consumerOrganizationUnitName, ift.consumerDataElementName, ift.consumerCategoryName,ift.startDate")
            .setString("cou", consumerOrganizationUnitName).setString("pinst", producerInstanceId).setString("cinst", consumerInstanceId).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDistinctConsumerOrgUnitNamesFromIntermediateFlatTable(String producerInstanceId,String consumerInstanceId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ift.consumerOrganizationUnitName from IntermediateFlatTable ift where ift.producerInstance=:pinst and ift.consumerInstance=:cinst ").setString("pinst", producerInstanceId).setString("cinst", consumerInstanceId).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getAllRecords() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from IntermediateFlatTable ift order by ift.consumerOrganizationUnitName")
            .list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public IntermediateFlatTable getIntermediateFlatTable(IntermediateFlatTable ift) throws Exception
    {
        IntermediateFlatTable ift2=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list= session.createQuery("from IntermediateFlatTable ift where ift.producerOrganizationUnit=:pou and ift.producerDataElement=:pde and ift.producerCategory=:pcc and ift.consumerOrganizationUnitName=:cou and ift.consumerDataElementName=:cde and ift.consumerCategoryName =:ccc and ift.startDate=:std")
                    .setString("pou", ift.getProducerOrganizationUnit()).setString("pde", ift.getProducerDataElement()).setString("pcc", ift.getProducerCategory()).setString("cou", ift.getConsumerOrganizationUnitName()).setString("cde", ift.getConsumerDataElementName()).setString("ccc", ift.getConsumerCategoryName()).setString("std", ift.getStartDate())
            .list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                ift2=(IntermediateFlatTable)list.get(0);
            }
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return ift2;
    }
    public List getDistinctConsumerOrgUnitIdsFromIntermediateFlatTable() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ift.consumerOrganizationUnitId from IntermediateFlatTable ift ")
            .list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getDistinctConsumerOrgUnitIdsFromIntermediateFlatTable(String instance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ift.consumerOrganizationUnitId from IntermediateFlatTable ift where ift.consumerInstance=:inst")
            .setString("inst", instance).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List aggregateRecordsWithSameOrgUnitDataElementAndCategory(String consumerOrganizationUnitId) throws Exception
    {
        List list=null;
        try
        { 
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select ift.consumerOrganizationUnitId, ift.consumerDataElementId, ift.consumerCategoryId, ift.startDate,sum(ift.value) from IntermediateFlatTable ift where ift.consumerOrganizationUnitId=:couId group by ift.consumerOrganizationUnitId, ift.consumerDataElementId, ift.consumerCategoryId,ift.startDate")
            .setString("couId", consumerOrganizationUnitId).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }//
    public List getIntermediateDhisFlatTableByConsumerOrganizationUnitId(String consumerOrganizationUnitId) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from IntermediateFlatTable ift where ift.consumerOrganizationUnitId=:couId").setString("couId", consumerOrganizationUnitId).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            session.close();
            throw new Exception(ex);
        }
        return list;
    }
    public List getIntermediateDhisFlatTableByOrgUnitName(String consumerOrganizationUnitName) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from IntermediateFlatTable ift where ift.consumerOrganizationUnitName=:ouname").setString("ouname", consumerOrganizationUnitName).list();
            tx.commit();
            session.close();
        }
        catch (Exception ex)
        {
            session.close();
            throw new Exception(ex);
        }
        return list;
    }
    public void saveIntermediateFlatTable(IntermediateFlatTable ift) throws Exception
    {
        if(ift !=null)
        {
            try
            { 
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(ift);
                tx.commit();
                closeSession(session);        
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void updateIntermediateFlatTable(IntermediateFlatTable ift) throws Exception
    {
        if(ift !=null)
        {
            try
            { 
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(ift);
                tx.commit();
                closeSession(session);        
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void deleteIntermediateFlatTable(IntermediateFlatTable ift) throws Exception
    {
        if(ift !=null)
        {
            try
            { 
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(ift);
                tx.commit();
                closeSession(session);        
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
