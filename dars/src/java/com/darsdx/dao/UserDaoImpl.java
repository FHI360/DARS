/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class UserDaoImpl implements UserDao
{
   Session session;
    Transaction tx;
    SessionFactory sessions; 
    
    public User getUser(String username,String password) throws Exception 
    {
        User user =null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list=session.createQuery("from User user where user.username=:usn and user.password=:pwd").setString("usn",username).setString("pwd", password).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                user=(User)list.get(0);
            }
        } 
        catch(Exception ex)
        {
            session.close();
        }
        return user;
    }
    public User getUser(String username) throws Exception 
    {
        User user =null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            user = (User) session.load(User.class, username, LockMode.UPGRADE);
            tx.commit();
            session.close();
        } 
        catch(Exception ex)
        {
            session.close();
        }
        return user;
    }

    
    public List getUserByUserRole(String userRole) throws Exception {
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from User user where user.userroleId=:user_role").setString("user_role",userRole).list();
           tx.commit();
        session.close();
        } catch (HibernateException he) 
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        return list;
    }
public List getUserInfo(String username) throws Exception {
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from User user where user.username=:user_name").setString("user_name",username).list();
           tx.commit();
            session.close();
        } catch (HibernateException he) 
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        return list;
    }
public List getAllUsers() throws Exception {
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from User user").list();
           tx.commit();
        session.close();
        } catch (HibernateException he) 
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        return list;
    }
public void saveUser(User user) throws Exception 
{
   try 
   {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
        session.close();
    }
}
   public void updateUser(User user) throws Exception {
   try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        session.update(user);
        tx.commit();
        session.close();
        } 
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
}
public void deleteUser(User user) throws Exception {
   try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        session.delete(user);
        tx.commit();
        session.close();
        } 
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
}
}
