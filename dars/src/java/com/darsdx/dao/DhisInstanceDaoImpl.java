/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.util.AppUtility;
import com.darsdx.business.DhisInstance;
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
public class DhisInstanceDaoImpl implements DhisInstanceDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public List getInstance(List instanceCodes) throws Exception
    {
        List instanceList=new ArrayList();
         if(instanceCodes !=null)
         {
             for(int i=0; i<instanceCodes.size(); i++)
             {
                 if(getDhisInstanceById(instanceCodes.get(i).toString()) !=null)
                 {
                     instanceList.add(getDhisInstanceById(instanceCodes.get(i).toString()));
                 }
             }
         }
        return instanceList;
    }
    public DhisInstance getDhisInstanceByInstanceName(String instanceName) throws Exception
    {
        DhisInstance dhisInstance=null;
        if(instanceName !=null)
        instanceName=instanceName.trim();
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisInstance dhisInstance where TRIM(dhisInstance.dhisName)=:name ").setString("name", instanceName).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dhisInstance=(DhisInstance)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        
       return dhisInstance;
    }
    public List getDhisInstanceByConnectionType(String connectionType) throws Exception
    {
        if(connectionType !=null)
        connectionType=connectionType.trim();
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisInstance dhisInstance where TRIM(dhisInstance.connectionType)=:name ").setString("name", connectionType).list();
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
    public List getAllDhisInstances() throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisInstance dhisInstance order by dhisInstance.dhisName ").list();
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
    public DhisInstance getDhisInstanceById(String id) throws Exception
    {
        List list = new ArrayList();
        DhisInstance dhisInstance=null;
            try
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                list = session.createQuery("from DhisInstance dhisInstance where dhisInstance.dhisId =:did").setString("did", id).list();
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
              dhisInstance=(DhisInstance)list.get(0);
           }
           return dhisInstance;
    }
    public void saveDhisInstance(DhisInstance dhisInstance) throws Exception
    {
        if(dhisInstance !=null)
        {
            try
            {
                if(dhisInstance.getDhisId()==null)
                dhisInstance.setDhisId(generateId());
                System.out.println("dhisInstance id is "+dhisInstance.getDhisId()+" and name is "+dhisInstance.getDhisName());
                if(getDhisInstanceByInstanceName(dhisInstance.getDhisName()) ==null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(dhisInstance);
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
    public void updateDhisInstance(DhisInstance dhisInstance) throws Exception
    {
        if(dhisInstance !=null && dhisInstance.getDhisId() !=null)
        {
             if(this.getDhisInstanceById(dhisInstance.getDhisId()) !=null)
             {
                 DhisInstance existingInstance=getDhisInstanceByInstanceName(dhisInstance.getDhisName());
                if(existingInstance ==null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(dhisInstance);
                    tx.commit();
                    closeSession(session);
                }
                else if(existingInstance.getDhisId().equals(dhisInstance.getDhisId()))
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(dhisInstance);
                    tx.commit();
                    closeSession(session);
                }
             }
            
        }
    }
    public void deleteDhisInstance(DhisInstance dhisInstance) throws Exception
    {
        if(dhisInstance !=null && dhisInstance.getDhisId() !=null)
        {
             if(this.getDhisInstanceById(dhisInstance.getDhisId()) !=null)
             {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(dhisInstance);
                tx.commit();
                closeSession(session);
             }
            
        }
    }
    public DhisInstance[] getDhisInstanceComboItems()
    {
        List list=null;
        try
        {
            list=getAllDhisInstances();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(list ==null)
        list=new ArrayList();
        DhisInstance[] items=new DhisInstance[list.size()];
        for(int i=0; i<list.size(); i++)
        {
            DhisInstance instance=(DhisInstance)list.get(i);
            items[i]=instance;
        }
        return items;
    }
    public List filterInstance(List instanceList,String dhisInstanceToExclude) throws Exception
    {
        List filteredList=new ArrayList();
        try
        {
            if(instanceList !=null && dhisInstanceToExclude !=null)
            {
                for(Object obj:instanceList)
                {
                    DhisInstance dhisInstance=(DhisInstance)obj;
                    if(dhisInstance.getDhisId().equalsIgnoreCase(dhisInstanceToExclude))
                    continue;
                    filteredList.add(dhisInstance);
                }
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return filteredList;
    }
    public String generateId() throws Exception
    {
        AppUtility appUtil=new AppUtility();
        String id=appUtil.generateUniqueId(8)+"api";
        DhisInstance dhisInstance=getDhisInstanceById(id);
        if(dhisInstance!=null)
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
