/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.CleanedDatavalue;
import com.darsdx.business.DataElement;
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
public class CleanedDatavalueDaoImpl implements CleanedDatavalueDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public List getCleanedDatavaluesByDhisId(String dhisId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from CleanedDatavalue cdv where cdv.dhisId=:did").setString("did", dhisId).list();
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
    public List getDataFromDatavalues(String orgunitPath,String dataElementId,String periodQuery) throws Exception
    {
        List list=new ArrayList();
        try
        {
            String query="select cdv.dataElementName,sum(cdv.dvalue) from OrganizationUnit ou, CleanedDatavalue cdv where ou.orgunitId=cdv.orgUnitId and ou.path like '%"+orgunitPath+"%' and cdv.dataElementId=:de"+periodQuery+" group by cdv.dataElementName";
            //System.err.println("query is "+query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).setString("de", dataElementId).list();
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
    public List getDistinctPeriodsFromDatavalues() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distict cdv.period from CleanedDatavalue cdv").list();
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
    public List getCleanedDatavaluesForReport() throws Exception
    {
        List dvList=new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from CleanedDatavalue cdv, DataElement de, OrganizationUnit ou where cdv.dataElementId=de.dataElementId and cdv.orgUnitId=ou.orgunitId ").list();
            tx.commit();
            closeSession(session);
            if(list !=null)
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    CleanedDatavalue dv=(CleanedDatavalue)objArray[0];
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
    public List getAllCleanedDatavalues() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from CleanedDatavalue cdv ").list();
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
    public CleanedDatavalue getCleanedDatavalue(int recordId) throws Exception
    {
        CleanedDatavalue dvalue=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from CleanedDatavalue cdv where cdv.recordId=:rid").setInteger("rid", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dvalue=(CleanedDatavalue)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dvalue;
    }
    public CleanedDatavalue getCleanedDatavalue(String datasetId,String dataElementId,String orgUnitId,String categoryId,String period) throws Exception
    {
        CleanedDatavalue dvalue=null;
        try
        {
            //Date date=new SimpleDateFormat("yyyy-MM-dd").parse((startDate));
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from CleanedDatavalue cdv where cdv.datasetId=:dtid and cdv.dataElementId=:deid and cdv.orgUnitId=:ouid and cdv.categoryOptionComboId=:cc and cdv.period=:prd").setString("dtid", datasetId).setString("deid", dataElementId).setString("ouid", orgUnitId).setString("cc", categoryId).setString("prd", period).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dvalue=(CleanedDatavalue)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dvalue;
    }
    public void saveCleanedDatavalue(CleanedDatavalue cdv) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(cdv);
            tx.commit();
            closeSession(session);  
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void updateCleanedDatavalue(CleanedDatavalue cdv) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(cdv);
            tx.commit();
            closeSession(session);  
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void deleteCleanedDatavalue(CleanedDatavalue cdv) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(cdv);
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
