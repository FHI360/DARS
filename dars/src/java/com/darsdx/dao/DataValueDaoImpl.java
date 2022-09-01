/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DataElement;
import com.darsdx.business.DataValue;
import com.darsdx.business.OrganizationUnit;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DataValueDaoImpl implements DataValueDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public List getDataValuesByDhisId(String dhisId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataValue dv where dv.dhisId=:did and dv.uploadedToDhis2=0 order by dv.orgUnitId").setString("did", dhisId).list();
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
    public List getDistinctPeriodsFromDataValues() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dv.period from DataValue dv").list();
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
    public List getDistinctDataElementsByPeriodsFromDataValues(String period) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dv.dataElementId from DataValue dv where dv.period=:prd").setString("prd", period).list();
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
    public List getDistinctOrganizationUnitsByDataElementAndPeriodFromDataValues(String dataElementId,String period) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dv.orgUnitId from DataValue dv where dv.dataElementId=:de and dv.period=:prd").setString("de", dataElementId).setString("prd", period).list();
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
    public List getDatavaluesByOrganizationUnitsDataElementAndPeriod(String orgunitId,String dataElementId,String period) throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataValue dv where dv.orgUnitId=:ou and dv.dataElementId=:de and dv.period=:prd").setString("ou", orgunitId).setString("de", dataElementId).setString("prd", period).list();
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
    public List getDataValuesByDhisIdPeriodAndPrefix(String dhisId,String period,String prefix) throws Exception
    {
        List dvList=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataValue dv, OrganizationUnit ou where dv.orgUnitId=ou.orgunitId and ou.orgunitName like '"+prefix+" %' and dv.dhisId=:did and dv.period=:pe").setString("did", dhisId).setString("pe", period).list();
            tx.commit();
            closeSession(session);
            if(list !=null)
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    DataValue dv=(DataValue)objArray[0];
                    dvList.add(dv);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dvList;
    }
    public List getDataValuesByParentId(String parentId) throws Exception
    {
        List dvList=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataValue dv, OrganizationUnit ou where dv.orgUnitId=ou.orgunitId and ou.parentId=:pid").setString("pid", parentId).list();
            tx.commit();
            closeSession(session);
            if(list !=null)
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    DataValue dv=(DataValue)objArray[0];
                    OrganizationUnit ou=(OrganizationUnit)objArray[1];
                    //dv.setDataElementName(de.getDataElementName());
                    dv.setOrgUnitName(ou.getOrgunitName());
                    dvList.add(dv);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dvList;
    }
    public void saveDataValue(DataValue dv) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(dv);
            tx.commit();
            closeSession(session);  
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void saveOrUpdateDataValue(DataValue dv) throws Exception
    {
        try
        {//getDataValue(String datasetId,String dataElementId,String orgUnitId,String categoryId,String period) throws Exception
            if(dv !=null)
            {
                if(getDataValue(dv.getDatasetId(), dv.getDataElementId(), dv.getOrgUnitId(), dv.getCategoryOptionComboId(), dv.getPeriod()) ==null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(dv);
                    tx.commit();
                    closeSession(session); 
                }
                else
                {
                    updateDataValue(dv);
                }
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void updateDataValue(DataValue dv) throws Exception
    {
        try
        {
            if(dv !=null)
            {
                DataValue dv2=getDataValue(dv.getDatasetId(), dv.getDataElementId(), dv.getOrgUnitId(), dv.getCategoryOptionComboId(), dv.getPeriod());
                if(dv2 !=null)
                {
                    dv.setRecordId(dv2.getRecordId());
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(dv);
                    tx.commit();
                    closeSession(session);
                }
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void deleteDataValue(DataValue dv) throws Exception
    {
        try
        {
            if(dv !=null)
            {
                DataValue dv2=getDataValue(dv.getDatasetId(), dv.getDataElementId(), dv.getOrgUnitId(), dv.getCategoryOptionComboId(), dv.getPeriod());
                if(dv2 !=null)
                {
                    dv.setRecordId(dv2.getRecordId());
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.delete(dv);
                    tx.commit();
                    closeSession(session); 
                }
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public List getDataValuesForReport() throws Exception
    {
        List dvList=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataValue dv, DataElement de, OrganizationUnit ou where dv.dataElementId=de.dataElementId and dv.orgUnitId=ou.orgunitId ").list();
            tx.commit();
            closeSession(session);
            if(list !=null)
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    DataValue dv=(DataValue)objArray[0];
                    DataElement de=(DataElement)objArray[1];
                    OrganizationUnit ou=(OrganizationUnit)objArray[2];
                    dv.setDataElementName(de.getDataElementName());
                    dv.setOrgUnitName(ou.getOrgunitName());
                    dvList.add(dv);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dvList;
    }
    public List getAllDataValues() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataValue dv ").list();
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
    public DataValue getDataValue(int recordId) throws Exception
    {
        DataValue dvalue=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataValue dv where dv.recordId=:rid").setInteger("rid", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dvalue=(DataValue)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dvalue;
    }
    public DataValue getDataValue(String datasetId,String dataElementId,String orgUnitId,String categoryId,String period) throws Exception
    {
        DataValue dvalue=null;
        try
        {
            //Date date=new SimpleDateFormat("yyyy-MM-dd").parse((startDate));
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataValue dv where dv.datasetId=:dtid and dv.dataElementId=:deid and dv.orgUnitId=:ouid and dv.categoryOptionComboId=:cc and dv.period=:prd").setString("dtid", datasetId).setString("deid", dataElementId).setString("ouid", orgUnitId).setString("cc", categoryId).setString("prd", period).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dvalue=(DataValue)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dvalue;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
