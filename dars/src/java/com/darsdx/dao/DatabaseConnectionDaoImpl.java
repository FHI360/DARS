/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DatabaseConnection;
import com.darsdx.util.AppUtility;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DatabaseConnectionDaoImpl implements DatabaseConnectionDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveDatabaseConnection(DatabaseConnection dbconn) throws Exception
    {
        try
        {
            if(this.getDatabaseConnectionByName(dbconn.getConnectionName())==null)
            {
                if(dbconn.getId()==null)
                dbconn.setId(generateId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(dbconn);
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
    public void updateDatabaseConnection(DatabaseConnection dbconn) throws Exception
    {
        try
        {
            if(getDatabaseConnection(dbconn.getId()) !=null)
            {
                DatabaseConnection dbconn2=this.getDatabaseConnectionByName(dbconn.getConnectionName());
                if(dbconn2 !=null)
                {
                    if(dbconn2.getId().equalsIgnoreCase(dbconn.getId()))
                    {
                        session = HibernateUtil.getSession();
                        tx = session.beginTransaction();
                        session.update(dbconn);
                        tx.commit();
                        closeSession(session);
                    }
                }
                else
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(dbconn);
                    tx.commit();
                    closeSession(session); 
                }
            }
        }
        catch(Exception ex)
        {
            closeSession(session);
            throw new Exception(ex);
        }
    }
    public void deleteDatabaseConnection(DatabaseConnection dbconn) throws Exception
    {
       try
        {
            if(getDatabaseConnection(dbconn.getId()) !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(dbconn);
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
    public List getAllDatabaseConnections() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatabaseConnection dbconn").list();
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
    public DatabaseConnection getDatabaseConnection(String id) throws Exception
    {
       DatabaseConnection dbconn=null;
       List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatabaseConnection dbconn where dbconn.id=:uid").setString("uid", id).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dbconn=(DatabaseConnection)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       return dbconn;
    }
    public DatabaseConnection getDatabaseConnectionByName(String connectionName) throws Exception
    {
       DatabaseConnection dbconn=null;
       List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatabaseConnection dbconn where dbconn.connectionName=:name").setString("name", connectionName).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                dbconn=(DatabaseConnection)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       return dbconn;
    }
    public String generateId() throws Exception
    {
        AppUtility appUtil=new AppUtility();
        String id=appUtil.generateUniqueId(8)+"dbc";
        DatabaseConnection dbconn=this.getDatabaseConnection(id);
        if(dbconn!=null)
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
