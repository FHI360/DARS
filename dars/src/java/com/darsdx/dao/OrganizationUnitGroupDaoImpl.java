/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.OrganisationUnitGroup;
import com.darsdx.util.AppUtility;
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
public class OrganizationUnitGroupDaoImpl implements OrganizationUnitGroupDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveOrganizationUnitGroup(OrganisationUnitGroup ou) throws Exception
    {
        if(ou !=null)
        {
            try
            {//&& getOrganizationUnitGroupByIdAndInstance(ou.getOrgunitGroupId(),ou.getDhisId()) !=null
                System.out.println("ou is "+ou.getOrgunitGroupId()+": "+ou.getOrgunitGroupName()+": "+ou.getDhisId());
                if(ou.getOrgunitGroupId() !=null && getOrganizationUnitGroup(ou.getOrgunitGroupId()) !=null)
                updateOrganizationUnitGroup(ou);
                else
                {
                    OrganisationUnitGroup ou2=getOrganizationUnitGroupByNameAndInstance(ou.getOrgunitGroupName(),ou.getDhisId());
                    if(ou2==null)
                    {
                        if(ou.getOrgunitGroupId() !=null && ou.getOrgunitGroupName() !=null && ou.getDhisId() !=null && ou.getLastModifiedDate() !=null)
                        {
                            if(ou.getOrgunitGroupId()==null)
                            ou.setOrgunitGroupId(generateId());
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
    public void updateOrganizationUnitGroup(OrganisationUnitGroup ou) throws Exception
    {
        if(ou !=null)
        {
            try
            {
                if(ou.getOrgunitGroupId() !=null)
                {//getOrganizationUnitByIdAndInstance(String orgunitId,String instance)
                    OrganisationUnitGroup ou2=getOrganizationUnitGroupByIdAndInstance(ou.getOrgunitGroupId(),ou.getDhisId());
                    if(ou2 !=null)
                    {
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
    public void deleteOrganizationUnitGroup(OrganisationUnitGroup ou) throws Exception
    {
        if(ou !=null)
        {
            try
            {
                if(ou.getOrgunitGroupId() !=null)
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
    public OrganisationUnitGroup getOrganizationUnitGroup(String orgunitGroupId) throws Exception
    {
        OrganisationUnitGroup ou=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from OrganisationUnitGroup ou where ou.orgunitGroupId=:ouid ").setString("ouid", orgunitGroupId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                ou=(OrganisationUnitGroup)list.get(0);
            }
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
    public OrganisationUnitGroup getOrganizationUnitGroupByIdAndInstance(String orgunitGroupId,String instance) throws Exception
    {
        if(orgunitGroupId !=null)
        orgunitGroupId=orgunitGroupId.trim();
        if(instance !=null)
        instance=instance.trim();
        OrganisationUnitGroup ou=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganisationUnitGroup ou where TRIM(ou.orgunitGroupId)=:ouid and TRIM(ou.dhisId)=:inst").setString("ouid", orgunitGroupId).setString("inst", instance).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                ou=(OrganisationUnitGroup)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return ou;
    }
    public OrganisationUnitGroup getOrganizationUnitGroupByName(String orgunitGroupName) throws Exception
    {
        OrganisationUnitGroup ou=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganisationUnitGroup ou where ou.orgunitGroupName=:ouname ").setString("ouname", orgunitGroupName).list();
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
            ou=(OrganisationUnitGroup)list.get(0);
        }
        return ou;
    }
    public OrganisationUnitGroup getOrganizationUnitGroupByNameAndInstance(String orgunitGroupName,String instance) throws Exception
    {
        OrganisationUnitGroup ou=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganisationUnitGroup ou where ou.orgunitGroupName=:ouname and ou.dhisId=:inst").setString("ouname", orgunitGroupName).setString("inst", instance).list();
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
            ou=(OrganisationUnitGroup)list.get(0);
        }
        return ou;
    }
    public List getOrganizationUnitGroups(String dhisInstance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganisationUnitGroup ou where ou.dhisId=:dinst ").setString("dinst", dhisInstance).list();
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
        OrganisationUnitGroup ou=getOrganizationUnitGroup(id);
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
