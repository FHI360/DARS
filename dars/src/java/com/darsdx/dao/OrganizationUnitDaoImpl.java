/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.OrganizationUnit;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitDaoImpl implements OrganizationUnitDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public List getOrganizationUnitAndParents(String dhisId,String orgunitId) throws Exception
    {
        List ouList=new ArrayList();
        OrganizationUnit ou=this.getOrganizationUnit(dhisId, orgunitId);
        if(ou !=null)
        {
            ouList.add(ou);
            List parentList=getParentOrganizationUnitsOnly(dhisId,orgunitId);
            if(parentList !=null)
            ouList.addAll(parentList);
        }
        return ouList;
    }
    public List getParentOrganizationUnitsOnly(String dhisId,String orgunitId) throws Exception
    {
        List parentOuList=new ArrayList();
        OrganizationUnit ou=this.getOrganizationUnit(dhisId, orgunitId);
        if(ou !=null)
        {
            int level=ou.getOulevel();
            if(level>1)
            {
                for(int i=level-1; i>0; i--)
                {
                    ou=this.getOrganizationUnit(dhisId,ou.getParentId());
                    parentOuList.add(ou);
                }
            }
        }
        return parentOuList;
    }
    public List getOrganizationUnitsByOuPath(String dhisId,String parentId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnit ou where ou.dhisId=:did and ou.path like '%"+parentId+"%'").setString("did", dhisId).list();
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
    public List getOrganizationUnitsByDhisId(String dhisId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnit ou where ou.dhisId=:did").setString("did", dhisId).list();
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
    public List getFacilityParentListByLevel2OuId(String level2OuId) throws Exception
    {
        List facilityParentList=new ArrayList();
        try
        {
            //get the list of all the LGAs in this state
            List level3OuIdList=getOrganizationUnitsByOuLevelAndParentId(level2OuId, 3);
            if(level3OuIdList !=null)
            {
                //get the list of wards per lga
                String level3OuId=null;
                for(int i=0; i<level3OuIdList.size(); i++)
                {
                    level3OuId=level3OuIdList.get(i).toString();
                    facilityParentList.addAll(getOrganizationUnitsByOuLevelAndParentId(level3OuId, 4));
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return facilityParentList;
    }
    public OrganizationUnit getOrganizationUnit(String orgunitId) throws Exception
    {
        OrganizationUnit ou=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from OrganizationUnit ou where ou.orgunitId=:ouid").setString("ouid", orgunitId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                ou=(OrganizationUnit)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return ou;
    }
    public OrganizationUnit getOrganizationUnit(String dhisId,String orgunitId) throws Exception
    {
        OrganizationUnit ou=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from OrganizationUnit ou where ou.orgunitId=:ouid and ou.dhisId=:did").setString("ouid", orgunitId).setString("did", dhisId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                ou=(OrganizationUnit)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        return ou;
    }
    public List getDistinctOrganizationUnitIdsByOuLevel(String dhisId,int oulevel) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ou.orgunitId from OrganizationUnit ou where ou.dhisId=:did and ou.oulevel=:lv").setString("did", dhisId).setInteger("lv", oulevel).list();
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
    public List getOrganizationUnitsByOuLevel(int oulevel) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnit ou where ou.oulevel=:lv").setInteger("lv", oulevel).list();
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
    public List getOrganizationUnitsByOuLevel(String dhisId,int oulevel) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnit ou where ou.dhisId=:did and ou.oulevel=:lv").setString("did", dhisId).setInteger("lv", oulevel).list();
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
    public List getOrganizationUnitsByOuLevelAndPrefix(String prefix,String dhisId,int oulevel) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnit ou where ou.dhisId=:did and ou.oulevel=:lv and ou.orgunitName like '"+prefix+" %'").setString("did", dhisId).setInteger("lv", oulevel).list();
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
    public List getOrganizationUnitsByParentId(String dhisId,String parentId) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnit ou where dhisId=:id and ou.parentId=:pi").setString("id", dhisId).setString("pi", parentId).list();
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
    public List getOrganizationUnitsByOuLevelAndParentId(String parentId,int oulevel) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnit ou where ou.parentId=:pi and ou.oulevel=:lv").setString("pi", parentId).setInteger("lv", oulevel).list();
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
    public List getAllOrganizationUnits() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OrganizationUnit ou").list();
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
    public void saveOrganizationUnit(OrganizationUnit ou) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(ou);
            tx.commit();
            closeSession(session);  
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void updateOrganizationUnit(OrganizationUnit ou) throws Exception
    {
        try
        {
            if(this.getOrganizationUnit(ou.getDhisId(), ou.getOrgunitId()) !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(ou);
                tx.commit();
                closeSession(session);  
            }
        }
        catch(Exception ex)
        {
            throw new Exception(ex);
        }
    }
    public void deleteOrganizationUnit(OrganizationUnit ou) throws Exception
    {
        try
        {
            if(this.getOrganizationUnit(ou.getDhisId(), ou.getOrgunitId()) !=null)
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
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
