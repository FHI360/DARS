/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DataTransferManager;
import com.darsdx.util.AppUtility;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DataTransferManagerDaoImpl implements DataTransferManagerDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveDataTransferManager(DataTransferManager dtm) throws Exception
    {
        try
        {
            if(dtm !=null && getDataTransferManagerByName(dtm.getName())==null)
            {
                if(dtm.getUid()==null || dtm.getUid().trim().length() !=11)
                dtm.setUid(generateId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(dtm);
                tx.commit();
                closeSession(session);  
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void updateDataTransferManager(DataTransferManager dtm) throws Exception
    {
        try
        {
            if(dtm !=null)
            {
                DataTransferManager dtm2=this.getDataTransferManager(dtm.getUid());
                if(dtm2 !=null)
                {
                    DataTransferManager dtm3=getDataTransferManagerByName(dtm.getName());
                    if(dtm3 !=null)
                    {
                        if(!dtm2.getUid().equals(dtm3.getUid()))
                        return;
                    }
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(dtm);
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
    public void deleteDataTransferManager(DataTransferManager dtm) throws Exception
    {
        try
        {
            if(dtm !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(dtm);
                tx.commit();
                closeSession(session);  
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public DataTransferManager getDataTransferManagerByName(String name) throws Exception
    {
        DataTransferManager dtm=null;
        if(name !=null)
        name=name.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataTransferManager dtm where TRIM(dtm.name)=:nm").setString("nm", name).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dtm=(DataTransferManager)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dtm;
    }
    public DataTransferManager getDataTransferManager(String id) throws Exception
    {
        DataTransferManager dtm=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DataTransferManager dtm where dtm.uid=:id").setString("id", id).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dtm=(DataTransferManager)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return dtm;
    }
    public List getAllDataTransferManagers() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DataTransferManager dtm").list();
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
    public String generateId() throws Exception
    {
        AppUtility appUtil=new AppUtility();
        String id=appUtil.generateUniqueId(11);
        if(this.getDataTransferManager(id) !=null)
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
