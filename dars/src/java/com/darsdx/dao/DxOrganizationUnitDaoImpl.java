/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.dao.HibernateUtil;
import com.darsdx.util.AppUtility;
import com.darsdx.business.DxOrganizationUnit;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DxOrganizationUnitDaoImpl implements DxOrganizationUnitDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveOrganizationUnit(DxOrganizationUnit ou) throws Exception
    {
        if(ou !=null)
        {
            try
            {
                System.out.println("ou is "+ou.getOrgunitId()+": "+ou.getOrgunitName()+": "+ou.getInstanceName()+": "+ou.getParentId()+": "+ou.getLastModifiedDate()+": "+ou.getLevel());
                if(ou.getOrgunitId() !=null && getOrganizationUnitByIdAndInstance(ou.getOrgunitId(),ou.getInstanceName()) !=null)
                updateOrganizationUnit(ou);
                else
                {
                    DxOrganizationUnit ou2=getOrganizationUnitByNameAndInstance(ou.getOrgunitName(),ou.getInstanceName());
                    if(ou2==null)
                    {
                        if(ou.getOrgunitId() !=null && ou.getOrgunitName() !=null && ou.getInstanceName() !=null && ou.getParentId() !=null && ou.getLastModifiedDate() !=null)
                        {
                            if(ou.getOrgunitId()==null)
                            ou.setOrgunitId(generateId());
                            session = HibernateUtil.getSession();
                            tx = session.beginTransaction();
                            session.save(ou);
                            tx.commit();
                            closeSession(session); 
                        }
                    }
                }
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void updateOrganizationUnit(DxOrganizationUnit ou) throws Exception
    {
        if(ou !=null)
        {
            try
            {
                if(ou.getOrgunitId() !=null)
                {//getOrganizationUnitByIdAndInstance(String orgunitId,String instance)
                    DxOrganizationUnit ou2=getOrganizationUnitByIdAndInstance(ou.getOrgunitId(),ou.getInstanceName());
                    if(ou2 !=null)
                    {
                        ou.setRecordId(ou2.getRecordId());
                        session = HibernateUtil.getSession();
                        tx = session.beginTransaction();
                        session.update(ou);
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
    }
    public void deleteOrganizationUnit(DxOrganizationUnit ou) throws Exception
    {
        if(ou !=null)
        {
            try
            {
                if(ou.getOrgunitId() !=null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.delete(ou);
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
    public List getOrganizationUnitLevels(String dhisInstance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ou.level from DxOrganizationUnit ou where ou.instanceName=:dinst").setString("dinst", dhisInstance).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return list;
    }
    public String[] getOrganizationUnitLevelsAsArray(String dhisInstance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ou.level from DxOrganizationUnit ou where ou.instanceName=:dinst").setString("dinst", dhisInstance).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        if(list ==null)
        list=new ArrayList();
        String[] items=new String[list.size()];
        for(int i=0; i<list.size(); i++)
        {
            items[i]=list.get(i).toString();
        }
        return items;
    }
    public DxOrganizationUnit getOrganizationUnit(String orgunitId) throws Exception
    {
        DxOrganizationUnit ou=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnit ou where ou.orgunitId=:ouid ").setString("ouid", orgunitId).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return ou;
    }
    public DxOrganizationUnit getOrganizationUnitByIdAndInstance(String orgunitId,String instance) throws Exception
    {
        if(orgunitId !=null)
        orgunitId=orgunitId.trim();
        if(instance !=null)
        instance=instance.trim();
        DxOrganizationUnit ou=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnit ou where TRIM(ou.orgunitId)=:ouid and TRIM(ou.instanceName)=:inst").setString("ouid", orgunitId).setString("inst", instance).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                ou=(DxOrganizationUnit)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return ou;
    }
    public DxOrganizationUnit getOrganizationUnitByName(String orgunitName) throws Exception
    {
        DxOrganizationUnit ou=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnit ou where ou.orgunitName=:ouname ").setString("ouname", orgunitName).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            ou=(DxOrganizationUnit)list.get(0);
        }
        return ou;
    }
    public DxOrganizationUnit getOrganizationUnitByNameAndInstance(String orgunitName,String instance) throws Exception
    {
        DxOrganizationUnit ou=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnit ou where ou.orgunitName=:ouname and ou.instanceName=:inst").setString("ouname", orgunitName).setString("inst", instance).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        if(list !=null && !list.isEmpty())
        {
            ou=(DxOrganizationUnit)list.get(0);
        }
        return ou;
    }
    public List getOrganizationUnits(String dhisInstance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnit ou where ou.instanceName=:dinst ").setString("dinst", dhisInstance).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return list;
    }
    public List getOrganizationUnits(String dhisInstance, int level) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnit ou where ou.instanceName=:dinst and ou.level=:lv order by ou.orgunitName").setString("dinst", dhisInstance).setInteger("lv", level).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return list;
    }
    public List getOrganizationUnitsNotMatched(String producerInstance,String consumerInstance, int level, String activeInstance) throws Exception
    {
        List list=null;
        List availableOuList=new ArrayList();
        try
        {
            List ouList=getOrganizationUnits(activeInstance, level);
            if(ouList !=null && !ouList.isEmpty())
            {
                DaoUtil util=new DaoUtil();
                List matchedList=util.getDxOrganizationUnitMatchDaoInstance().getDxOrganizationUnitMatchIdsByMatchDescription(producerInstance, consumerInstance);
                if(matchedList !=null)
                {
                    System.err.println("matchedList size is "+matchedList.size()+" and ouList size is "+ouList.size());
                    for(Object obj:ouList)
                    {
                        DxOrganizationUnit ou=(DxOrganizationUnit)obj;
                        if(ou !=null && ou.getOrgunitId() !=null)
                        {
                            //System.err.println("ou.getOrgunitId() is "+ou.getOrgunitId());
                            if(!matchedList.contains(ou.getOrgunitId().trim()))
                            {
                                //System.err.println("ou.getOrgunitName() is "+ou.getOrgunitName());
                                availableOuList.add(ou);
                            }
                        }
                    }
                }
            }
            /*String query="from DxOrganizationUnit ou where ou.instanceName=:dinst and ou.level=:lv and (ou.orgunitId not in (select distinct oum.producerOrgUnitId from  DxOrganizationUnitMatch oum where (oum.matchDescription like '"+instanceA+"' and oum.matchDescription like '"+instanceB+"') ) and ou.orgunitId not in (select distinct oum.consumerOrgUnitId from  DxOrganizationUnitMatch oum where (oum.matchDescription like '"+instanceA+"' and oum.matchDescription like '"+instanceB+"')) )order by ou.orgunitName";
            //System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).setString("dinst", instanceA).setInteger("lv", instALevel).list();
            tx.commit();
            closeSession(session);*/
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return availableOuList;
    }
    public DxOrganizationUnit[] getOrganizationUnitsNotMatched(String dhisInstance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnit ou where ou.instanceName=:dinst").setString("dinst", dhisInstance).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        if(list ==null)
        list=new ArrayList();
        DxOrganizationUnit[] items=new DxOrganizationUnit[list.size()];
        for(int i=0; i<list.size(); i++)
        {
            DxOrganizationUnit instance=(DxOrganizationUnit)list.get(i);
            items[i]=instance;
        }
        return items;
    }
    public List getOrganizationUnitsByParentId(String parentId) throws Exception
    {
       List list=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnit ou where ou.parentId=:pid ").setString("pid", parentId).list();
            tx.commit();
            closeSession(session);
        }
        catch (HibernateException he)
        {
            closeSession(session);
            he.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       return list; 
    }
    public String generateId() throws Exception
    {
        AppUtility appUtil=new AppUtility();
        String id=appUtil.generateUniqueId(11);
        DxOrganizationUnit ou=getOrganizationUnit(id);
        if(ou!=null)
        generateId();
        return id;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
