/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.ImisDatavalue;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class ImisDatavalueDaoImpl implements ImisDatavalueDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveImisDatavalue(ImisDatavalue idv) throws Exception
    {
        if(idv !=null && getImisDatavalue(idv.getRecordId())==null)
        {
            try
            { 
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(idv);
                tx.commit();
                closeSession(session);        
            }
            catch(Exception ex)
            {
                closeSession(session);
                throw new Exception(ex);
            }
        }
    }
    public void updateImisDatavalue(ImisDatavalue idv) throws Exception
    {
       if(idv !=null && getImisDatavalue(idv.getRecordId())!=null)
        {
            try
            { 
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(idv);
                tx.commit();
                closeSession(session);        
            }
            catch(Exception ex)
            {
                closeSession(session);
                throw new Exception(ex);
            }
        } 
    }
    public void deleteImisDatavalue(ImisDatavalue idv) throws Exception
    {
        if(idv !=null && getImisDatavalue(idv.getRecordId())!=null)
        {
            try
            { 
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(idv);
                tx.commit();
                closeSession(session);        
            }
            catch(Exception ex)
            {
                closeSession(session);
                throw new Exception(ex);
            }
        } 
    }
    public void saveOrUpdateImisDatavalue(ImisDatavalue idv) throws Exception
    {
        if(idv !=null)
        {
            try
            { 
                if(getImisDatavalue(idv.getRecordId())==null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(idv);
                    tx.commit();
                    closeSession(session); 
                }
                else
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(idv);
                    tx.commit();
                    closeSession(session);
                }
            }
            catch(Exception ex)
            {
                closeSession(session);
                throw new Exception(ex);
            }
        } 
    }
    public List getAllImisDatavalues() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ImisDatavalue idv order by idv.yearMonth").list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        return list;
    }
    public ImisDatavalue getImisDatavalue(String recordId) throws Exception
    {
        ImisDatavalue idv=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from ImisDatavalue idv where idv.recordId=:id").setString("id", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                idv=(ImisDatavalue)list.get(0);
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        return idv;
    }
    public List getDistinctPeriodsFromImisDatavalues() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct idv.yearMonth from ImisDatavalue idv").list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
        return list;
    }
    public List getDistinctDataElementsFromImisDatavalues() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct idv.imisDataElementId from ImisDatavalue idv").list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
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
