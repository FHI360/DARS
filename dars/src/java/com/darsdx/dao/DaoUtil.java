/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DhisInstance;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DaoUtil implements Serializable
{
    Session session;
    Transaction tx;
    public static DhisInstance getDhisInstance(String instanceCode)
    {
        DhisInstance dinst=new DhisInstance();
        return dinst;
    }
    public static DhisInstanceDao getDhisInstanceDaoInstance()
    {
        DhisInstanceDao dinstdao=new DhisInstanceDaoImpl();
        return dinstdao;
    }
    public static DxCategoryCombinationDao getDxCategoryCombinationDaoInstance()
    {
        DxCategoryCombinationDao ccdao=new DxCategoryCombinationDaoImpl();
        return ccdao;
    }
    public static DxDataElementDao getDxDataElementDaoInstance()
    {
        DxDataElementDao dedao=new DxDataElementDaoImpl();
        return dedao;
    }
    public static DxOrganizationUnitDao getDxOrganizationUnitDaoInstance()
    {
        DxOrganizationUnitDao oudao=new DxOrganizationUnitDaoImpl();
        return oudao;
    }
    public static BusinessRuleDao getBusinessRuleDaoInstance()
    {
        BusinessRuleDao brdao=new BusinessRuleDaoImpl();
        return brdao;
    }
    public static DxOrganizationUnitMatchDao getDxOrganizationUnitMatchDaoInstance()
    {
        DxOrganizationUnitMatchDao oumdao=new DxOrganizationUnitMatchDaoImpl();
        return oumdao;
    }
    public static SourceDataDao getSourceDataDaoInstance()
    {
        SourceDataDao sddao=new SourceDataDaoImpl();
        return sddao;
    }
    public static IntermediateFlatTableDao getIntermediateFlatTableDaoInstance()
    {
        IntermediateFlatTableDao iftdao=new IntermediateFlatTableDaoImpl();
        return iftdao;
    }
    public static FinalFlatTableDao getFinalFlatTableDaoInstance()
    {
        FinalFlatTableDao fftdao=new FinalFlatTableDaoImpl();
        return fftdao;
    }//
    public static UserDao getUserDaoInstance()
    {
       return new UserDaoImpl();
    }
    public static OrganizationUnitGroupDao getOrganizationUnitGroupDaoInstance()
    {
       return new OrganizationUnitGroupDaoImpl();
    }
    public static DatabaseConnectionDao getDatabaseConnectionDaoInstance()
    {
       return new DatabaseConnectionDaoImpl();
    }
    public static DataTransferManagerDao getDataTransferManagerDaoInstance()
    {
       return new DataTransferManagerDaoImpl();
    }
    public void updateDatabase(String query)
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
            //throw new Exception();
        }
    }
    public List createSqlQuery(String query)
    {
        List list=null;
        try
        {
            //Desktop.getDesktop().browse(new URI("hhh"));
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createSQLQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
            //throw new Exception();
        }
        return list;
    }
    public List execSqlQuery(String sql)
    {
        List list=new ArrayList();
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        list=session.createSQLQuery(sql).list();
        tx.commit();
        session.close();
        return list;
    }
}
