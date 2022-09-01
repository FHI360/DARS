/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DxOrganizationUnitMatch;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DxOrganizationUnitMatchDaoImpl implements DxOrganizationUnitMatchDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByProducerOuNamesAndInstances(String producerOrgunitName,String producerInstance,String consumerInstance) throws Exception
    {
        List list=null;
        DxOrganizationUnitMatch oum=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnitMatch oum where (oum.producerOrgUnitName=:poun and oum.producerInstanceId =:pinst and oum.consumerInstanceId =:cinst)").setString("poun", producerOrgunitName).setString("pinst", producerInstance).setString("cinst", consumerInstance).list();
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
           oum=(DxOrganizationUnitMatch)list.get(0);
       }
       return oum; 
    }
    public List getDxOrganizationUnitMatchByDhisInstances(String producerInstance,String consumerInstance) throws Exception
    {
        List list=new ArrayList();
       try
        {
            //System.err.println("instanceA is "+instanceA+"' and instanceB is '"+instanceB);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnitMatch oum where (oum.producerInstanceId=:pinst and oum.consumerInstanceId=:cinst)").setString("pinst", producerInstance).setString("cinst", consumerInstance).list();
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
    public List getDistinctProducerInstances() throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct oum.producerInstanceId from DxOrganizationUnitMatch oum").list();
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
    public List getDistinctConsumerInstances() throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct oum.consumerInstanceId from DxOrganizationUnitMatch oum").list();
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
    public List getDistinctConsumerInstances(String producerInstanceId) throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct oum.consumerInstanceId from DxOrganizationUnitMatch oum where oum.producerInstanceId=:pinst").setString("pinst", producerInstanceId).list();
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
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByIdAndMatchDescription(int recordId) throws Exception
    {
        
        DxOrganizationUnitMatch oum=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DxOrganizationUnitMatch oum where oum.recordId=:id").setInteger("id", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
               oum=(DxOrganizationUnitMatch)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       
       return oum; 
    }
    public void saveOrganizationUnit(DxOrganizationUnitMatch oum) throws Exception
    {
        if(oum !=null)
        {
            try
            { 
                oum=getPreparedOum(oum);
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(oum);
                tx.commit();
                closeSession(session);        
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void updateOrganizationUnit(DxOrganizationUnitMatch oum) throws Exception
    {
        if(oum !=null)
        {
            try
            { 
                oum=getPreparedOum(oum);
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(oum);
                tx.commit();
                closeSession(session);      
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void deleteOrganizationUnit(DxOrganizationUnitMatch oum) throws Exception
    {
        if(oum !=null)
        {
            try
            { 
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(oum);
                tx.commit();
                closeSession(session);  
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public DxOrganizationUnitMatch getPreparedOum(DxOrganizationUnitMatch oum)
    {
        if(oum !=null)
        {
            String matchDescriptor=getMatchDescription(oum.getProducerInstanceId(),oum.getConsumerInstanceId());
            String[] instanceArray=matchDescriptor.split("-");
            oum.setProducerInstanceId(instanceArray[0]);
            oum.setConsumerInstanceId(instanceArray[1]);
            oum.setMatchDescription(matchDescriptor);
        }
        return oum;
    }
    public List getDxOrganizationUnitMatchIdsByMatchDescription(String instanceA,String instanceB) throws Exception
    {
        List list=null;
        List idList=new ArrayList();
       try
        {
            //System.err.println("instanceA is "+instanceA+"' and instanceB is '"+instanceB);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnitMatch oum where (oum.matchDescription like '%"+instanceA+"%' and oum.matchDescription like '%"+instanceB+"%')").list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                //System.err.println("list size is "+list.size());
                for(Object obj:list)
                {
                    DxOrganizationUnitMatch oum=(DxOrganizationUnitMatch)obj;
                    if(oum !=null && oum.getConsumerOrgUnitId() !=null && oum.getProducerOrgUnitId() !=null)
                    {
                        idList.add(oum.getProducerOrgUnitId().trim());
                        idList.add(oum.getConsumerOrgUnitId().trim());
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
       return idList; 
    }
    public List getDxOrganizationUnitMatchByMatchDescription(String instanceA,String instanceB) throws Exception
    {
        List list=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnitMatch oum where (oum.matchDescription like '%"+instanceA+"%' and oum.matchDescription like '%"+instanceB+"%')").list();
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
    public List getAllDxOrganizationUnitMatch() throws Exception
    {
        List list=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnitMatch oum ").list();
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
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByOuNamesAndInstances(String consumerOrgunitName,String producerOrgunitName,String consumerInstance,String producerInstance) throws Exception
    {
        List list=null;
        DxOrganizationUnitMatch oum=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnitMatch oum where (oum.consumerOrgUnitName=:coun and oum.producerOrgUnitName=:poun and oum.consumerInstanceId =:cinst and oum.producerInstanceId =:pinst)").setString("coun", consumerOrgunitName).setString("poun", producerOrgunitName).setString("cinst", consumerInstance).setString("pinst", producerInstance).list();
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
           oum=(DxOrganizationUnitMatch)list.get(0);
       }
       return oum; 
    }
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByProducerOrgUnitIdAndProducerInstance(String orgUnitId,String producerInstanceId) throws Exception
    {
        //This is used for AFYA Nyota only
        List list=null;
        DxOrganizationUnitMatch oum=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnitMatch oum where oum.producerOrgUnitId=:pouid and oum.producerInstanceId=:pinst").setString("pouid", orgUnitId).setString("pinst", producerInstanceId).list();
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
           oum=(DxOrganizationUnitMatch)list.get(0);
       }
       return oum; 
    }
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByConsumerOrgUnitIdAndProducerInstance(String orgUnitId,String producerInstanceId) throws Exception
    {
        //This is used for AFYA Nyota only
        List list=null;
        DxOrganizationUnitMatch oum=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnitMatch oum where oum.consumerOrgUnitId=:couid and oum.producerInstanceId=:pinst").setString("couid", orgUnitId).setString("pinst", producerInstanceId).list();
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
           oum=(DxOrganizationUnitMatch)list.get(0);
       }
       return oum; 
    }
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByConsumerOrgUnitIdAndConsumerInstance(String orgUnitId,String consumerInstanceId) throws Exception
    {
        //This is used for AFYA Nyota only
        List list=null;
        DxOrganizationUnitMatch oum=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnitMatch oum where oum.consumerOrgUnitId=:couid and oum.consumerInstanceId=:cinst").setString("couid", orgUnitId).setString("cinst", consumerInstanceId).list();
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
           oum=(DxOrganizationUnitMatch)list.get(0);
       }
       return oum; 
    }
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByIdAndMatchDescription(String orgUnitId,String instanceA,String instanceB) throws Exception
    {
        List list=null;
        DxOrganizationUnitMatch oum=null;
       try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DxOrganizationUnitMatch oum where (oum.producerOrgUnitId=:pouid or oum.consumerOrgUnitId=:couid)  and (oum.matchDescription like '%"+instanceA+"%' and oum.matchDescription like '%"+instanceB+"%')").setString("pouid", orgUnitId).setString("couid", orgUnitId).list();
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
           oum=(DxOrganizationUnitMatch)list.get(0);
       }
       return oum; 
    }
    private String getMatchDescription(String producerInstance,String consumerInstance)
    {
        String matchDescriptor=producerInstance+"-"+consumerInstance;
        try
        {
            List list=getDxOrganizationUnitMatchByMatchDescription(producerInstance,consumerInstance);
            if(list !=null && !list.isEmpty())
            {
                DxOrganizationUnitMatch oum=(DxOrganizationUnitMatch)list.get(0);
                matchDescriptor=oum.getMatchDescription();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return matchDescriptor;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
