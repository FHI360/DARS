/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.dao.HibernateUtil;
import com.darsdx.business.DxCategoryCombination;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DxCategoryCombinationDaoImpl implements DxCategoryCombinationDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveOrUpdateCategoryCombination(DxCategoryCombination cc) throws Exception
    {
        if(cc !=null)
        {
            if(cc.getCatComboId() !=null && cc.getCatComboName() !=null)
            {
                DxCategoryCombination ccById=getCategoryCombinationByIdAndDhisInstance(cc.getCatComboId(),cc.getDhisInstance());
                //DxCategoryCombination ccByName=getCategoryCombinationByNameAndDhisInstance(cc.getCatComboId(),cc.getDhisInstance());
                if(ccById ==null)
                {
                   saveCategoryCombination(cc); 
                }
                else
                {
                    cc.setRecordId(ccById.getRecordId());
                    updateCategoryCombination(cc); 
                }
            }
        }
    }
    private void saveCategoryCombination(DxCategoryCombination cc) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(cc);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void updateCategoryCombination(DxCategoryCombination cc) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(cc);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteCategoryCombination(DxCategoryCombination cc) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(cc);
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public DxCategoryCombination getCategoryCombination(int recordId) throws Exception
    {
        DxCategoryCombination cc=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DxCategoryCombination cc where cc.recordId=:id ").setInteger("id", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
               cc=(DxCategoryCombination)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       
       return cc;
    }
    public DxCategoryCombination getCategoryCombination(String CategoryCombinationId) throws Exception
    {
        DxCategoryCombination cc=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxCategoryCombination cc where cc.catComboId=:ccid ").setString("ccid", CategoryCombinationId).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       if(list !=null && !list.isEmpty())
       {
           cc=(DxCategoryCombination)list.get(0);
       }
       return cc;
    }
    public DxCategoryCombination getCategoryCombinationByName(String CategoryCombinationName) throws Exception
    {
        DxCategoryCombination cc=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxCategoryCombination cc where cc.catComboName=:name ").setString("name", CategoryCombinationName).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       if(list !=null && !list.isEmpty())
       {
           cc=(DxCategoryCombination)list.get(0);
       }
       return cc;
    }
    public DxCategoryCombination getCategoryCombinationByIdAndDhisInstance(String categoryCombinationId, String dhisInstance) throws Exception
    {
        DxCategoryCombination cc=null;
        if(categoryCombinationId !=null)
        categoryCombinationId=categoryCombinationId.trim();
        if(dhisInstance !=null)
        dhisInstance=dhisInstance.trim();
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxCategoryCombination cc where TRIM(cc.catComboId)=:ccid and TRIM(cc.dhisInstance)=:inst").setString("ccid", categoryCombinationId).setString("inst", dhisInstance).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
           {
               cc=(DxCategoryCombination)list.get(0);
           }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       
       return cc;
    }
    public DxCategoryCombination getCategoryCombinationByNameAndDhisInstance(String CategoryCombinationName, String dhisInstance) throws Exception
    {
        DxCategoryCombination cc=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxCategoryCombination cc where cc.catComboName=:name and cc.dhisInstance=:inst").setString("name", CategoryCombinationName).setString("inst", dhisInstance).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       if(list !=null && !list.isEmpty())
       {
           cc=(DxCategoryCombination)list.get(0);
       }
       return cc;
    }
    public List getAllCategoryCombinations() throws Exception
    {
        List list=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxCategoryCombination cc order by cc.catComboName").list();
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
    public List getCategoryCombinationByDhisInstance(String dhisInstance) throws Exception
    {
        List list=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxCategoryCombination cc where cc.dhisInstance=:inst ").setString("inst", dhisInstance).list();
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
