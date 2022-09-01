/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.FinalFlatTable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class FinalFlatTableDaoImpl implements FinalFlatTableDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public List getAllRecords() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from FinalFlatTable fft order by fft.consumerOrganizationUnitName")
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
    public List getNonDuplicateRecords(String orgUnitId) throws Exception
    {
        List list=null;
        try
        {
            //DhisDataExport dde=new DhisDataExport();dde.gets;
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct fft.consumerOrganizationUnitId, fft.consumerDataElementId,fft.consumerCategoryId,fft.startDate, sum(fft.value),fft.attributeOptionCombo from FinalFlatTable fft where fft.consumerDataElementId is not null and fft.consumerCategoryId is not null and fft.consumerOrganizationUnitId=:ouId group by fft.consumerOrganizationUnitId, fft.consumerDataElementId,fft.consumerCategoryId,fft.startDate,fft.attributeOptionCombo")
            .setString("ouId", orgUnitId).list();
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
    public List getFinalFlatTableByDataElementNameAndDhisInstance(String deName,String instance) throws Exception
    {
        List list=null;
        try
        {
            //DhisDataExport dde=new DhisDataExport();dde.gets;
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from FinalFlatTable fft where fft.consumerDataElementName =:dename and  fft.consumerInstance=:inst ")
            .setString("dename", deName).setString("inst", instance).list();
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
    public List getFinalFlatTableByOrgUnitNameAndDhisInstance(String orgUnitName,String instance) throws Exception
    {
        List list=null;
        try
        {
            //DhisDataExport dde=new DhisDataExport();dde.gets;
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from FinalFlatTable fft where fft.consumerOrganizationUnitName =:ouname and  fft.consumerInstance=:inst ")
            .setString("ouname", orgUnitName).setString("inst", instance).list();
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
    public List getFinalFlatTableByCatComboNameAndDhisInstance(String ccName,String instance) throws Exception
    {
        List list=null;
        try
        {
            //DhisDataExport dde=new DhisDataExport();dde.gets;
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from FinalFlatTable fft where fft.consumerCategoryName =:ccname and  fft.consumerInstance=:inst ")
            .setString("ccname", ccName).setString("inst", instance).list();
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
    public List getNonDuplicateRecords(String orgUnitId,String instance) throws Exception
    {
        List list=null;
        try
        {
            //DhisDataExport dde=new DhisDataExport();dde.gets;
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct fft.consumerOrganizationUnitId, fft.consumerDataElementId,fft.consumerCategoryId,fft.startDate, sum(fft.value) from FinalFlatTable fft where fft.consumerDataElementId is not null and fft.consumerCategoryId is not null and fft.consumerOrganizationUnitId=:ouId and  fft.consumerInstance=:inst group by fft.consumerOrganizationUnitId, fft.consumerDataElementId,fft.consumerCategoryId,fft.startDate")
            .setString("ouId", orgUnitId).setString("inst", instance).list();
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
    public List getDistinctOrgUnitNamesFromFinalFlatTable(String consumerInstance,boolean nullIdsOnly) throws Exception
    {
        List list=null;
        try
        {
            String query="select distinct fft.consumerOrganizationUnitName from FinalFlatTable fft where fft.consumerOrganizationUnitName is not null and fft.consumerInstance=:cinst";
            if(nullIdsOnly)
            query=query+" and fft.consumerOrganizationUnitId is null";
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query+" order by fft.consumerOrganizationUnitName").setString("cinst", consumerInstance)
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
    public List getDistinctDataElementNamesFromFinalFlatTable(boolean nullIdsOnly) throws Exception
    {
        List list=null;
        try
        {
            String query="select distinct fft.consumerDataElementName from FinalFlatTable fft where fft.consumerDataElementName is not null ";
            if(nullIdsOnly)
            query=query+" and fft.consumerDataElementId is null";
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query+" order by fft.consumerDataElementName")
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
    public List getDistinctCatComboNamesFromFinalFlatTable(boolean nullIdsOnly) throws Exception
    {
        List list=null;
        try
        {
            String query="select distinct fft.consumerCategoryName from FinalFlatTable fft where fft.consumerCategoryName is not null ";
            if(nullIdsOnly)
            query=query+" and fft.consumerCategoryId is null";
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query+" order by fft.consumerCategoryName")
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
    public List getDistinctOrgUnitIdsFromFinalFlatTable() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct fft.consumerOrganizationUnitId from FinalFlatTable fft where fft.consumerOrganizationUnitId is not null and fft.consumerDataElementId is not null and fft.consumerCategoryId is not null order by fft.consumerOrganizationUnitId asc")
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
    public FinalFlatTable getFinalFlatTableByNames(String consumerOrganizationUnitName,String consumerDataElementName,String consumerCategoryName,String startDate,String consumerInstanceId) throws Exception
    {
        FinalFlatTable fft=null;
        if(consumerOrganizationUnitName !=null)
        consumerOrganizationUnitName=consumerOrganizationUnitName.trim();
        else if(consumerDataElementName !=null)
        consumerDataElementName=consumerDataElementName.trim();
        else if(consumerCategoryName !=null)
        consumerCategoryName=consumerCategoryName.trim();
        else if(startDate !=null)
        startDate=startDate.trim();
        
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //List list = session.createQuery("from FinalFlatTable fft where TRIM(fft.consumerOrganizationUnitName)=:cou and TRIM(fft.consumerDataElementName)=:cde and TRIM(fft.consumerCategoryName)=:cc and TRIM(fft.startDate)=:std and fft.consumerInstance=:cinst")
                    List list = session.createQuery("from FinalFlatTable fft where fft.consumerOrganizationUnitName=:cou and fft.consumerDataElementName=:cde and fft.consumerCategoryName=:cc and fft.startDate=:std and fft.consumerInstance=:cinst")
                    .setString("cou", consumerOrganizationUnitName).setString("cde", consumerDataElementName).setString("cc", consumerCategoryName).setString("std", startDate).setString("cinst", consumerInstanceId).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                fft=(FinalFlatTable)list.get(0);
            }
        }
        catch (Exception ex)
        {
            session.close();
            ex.printStackTrace();
            //throw new Exception(ex);
        }
        return fft;
    }
    public FinalFlatTable getFinalFlatTable(String consumerOrganizationUnitId,String consumerDataElementId,String consumerCategoryId,String attributeOptionCombo,String startDate,String consumerInstanceId) throws Exception
    {
        FinalFlatTable fft=null;
        if(consumerOrganizationUnitId !=null)
        consumerOrganizationUnitId=consumerOrganizationUnitId.trim();
        else if(consumerDataElementId !=null)
        consumerDataElementId=consumerDataElementId.trim();
        else if(consumerCategoryId !=null)
        consumerCategoryId=consumerCategoryId.trim();
        else if(startDate !=null)
        startDate=startDate.trim();
        
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //List list = session.createQuery("from FinalFlatTable fft where TRIM(fft.consumerOrganizationUnitId)=:couId and TRIM(fft.consumerDataElementId)=:deId and TRIM(fft.consumerCategoryId)=:ccId and TRIM(fft.startDate)=:std and fft.attributeOptionCombo=:attr and fft.consumerInstance=:cinst").setString("couId", consumerOrganizationUnitId).setString("attr", attributeOptionCombo).setString("cinst", consumerInstanceId)
                    List list = session.createQuery("from FinalFlatTable fft where fft.consumerOrganizationUnitId=:couId and fft.consumerDataElementId=:deId and fft.consumerCategoryId=:ccId and fft.startDate=:std and fft.attributeOptionCombo=:attr and fft.consumerInstance=:cinst").setString("couId", consumerOrganizationUnitId).setString("attr", attributeOptionCombo).setString("cinst", consumerInstanceId)
                    .setString("deId", consumerDataElementId).setString("ccId", consumerCategoryId).setString("std", startDate).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                fft=(FinalFlatTable)list.get(0);
            }
        }
        catch (Exception ex)
        {
            session.close();
            throw new Exception(ex);
        }
        return fft;
    }
    private FinalFlatTable getTrimmedFinalFlatTable(FinalFlatTable fft)
    {
        if(fft.getConsumerCategoryName() !=null)
        fft.setConsumerCategoryName(fft.getConsumerCategoryName().trim());
        if(fft.getConsumerDataElementName() !=null)
        fft.setConsumerDataElementName(fft.getConsumerDataElementName().trim());
        if(fft.getConsumerOrganizationUnitName() !=null)
        fft.setConsumerOrganizationUnitName(fft.getConsumerOrganizationUnitName().trim());
        if(fft.getProducerCategoryName() !=null)
        fft.setProducerCategoryName(fft.getProducerCategoryName().trim());
        if(fft.getProducerDataElementName() !=null)
        fft.setProducerDataElementName(fft.getProducerDataElementName().trim());
        if(fft.getProducerOrganizationUnitName() !=null)
        fft.setProducerOrganizationUnitName(fft.getProducerOrganizationUnitName().trim());
        return fft;
    }
    public void saveFinalFlatTable(FinalFlatTable fft) throws Exception
    {
        if(fft !=null)
        {
            try
            { 
                fft=getTrimmedFinalFlatTable(fft);
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(fft);
                tx.commit();
                closeSession(session);        
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void updateFinalFlatTable(FinalFlatTable fft) throws Exception
    {
        if(fft !=null)
        {
            try
            { 
                fft=getTrimmedFinalFlatTable(fft);
                FinalFlatTable fft2=getFinalFlatTable(fft.getConsumerOrganizationUnitId(), fft.getConsumerDataElementId(), fft.getConsumerCategoryId(),fft.getAttributeOptionCombo(), fft.getStartDate(), fft.getConsumerInstance());
                if(fft2 !=null)
                {
                    fft.setRecordId(fft2.getRecordId());
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(fft);
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
    public void deleteFinalFlatTable(FinalFlatTable fft) throws Exception
    {
        if(fft !=null)
        {
            try
            { 
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(fft);
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
