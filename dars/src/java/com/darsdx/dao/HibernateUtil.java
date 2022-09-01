/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.darsdx.dao;

import com.darsdx.util.AppUtility;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author HP USER
 */
public class HibernateUtil
{

private static SessionFactory sessionFactory;

static {
try
{
    AppUtility appUtil=new AppUtility();
    
    Configuration cfg = new Configuration();
    cfg.configure();
    String dbURL=appUtil.getDatabaseURL();
    //cfg.setProperty("hibernate.connection.url", dbURL);
    sessionFactory = cfg.buildSessionFactory();
    //appUtil.setDbRootDirectory(appUtil.getUserHomeDirectory());
    
    //"jdbc:derby:"+AppUtility.getDatabaseDirectory()+"\\nomisdb";//dbPath+"\\Nomis\\dbs\\nomisdb";
    //System.err.println("dbPath is "+dbURL);
}
//catch (Throwable ex)
catch (Exception ex)
{
    ex.getMessage();
    //ex.printStackTrace(System.out);
    //throw new ExceptionInInitializerError(ex);
}

}
public static Session getSession() throws HibernateException
{
    return sessionFactory.openSession();
}
}