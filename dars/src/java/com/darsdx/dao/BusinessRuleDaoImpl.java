/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.BusinessRule;
import com.darsdx.business.DhisInstance;
import com.darsdx.business.DxCategoryCombination;
import com.darsdx.business.DxDataElement;
import com.darsdx.util.AppUtility;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class BusinessRuleDaoImpl implements BusinessRuleDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    public BusinessRule getBusinessRuleByConsumerDataElementIdAndConsumerCatComboIdAndConsumerInstanceId(String consumerDataElementId,String consumerCatComboId,String consumerInstanceId) throws Exception
    {
        BusinessRule br=null;
        if(consumerDataElementId !=null)
        consumerDataElementId=consumerDataElementId.trim();
        if(consumerCatComboId !=null)
        consumerCatComboId=consumerCatComboId.trim();
        if(consumerInstanceId !=null)
        consumerInstanceId=consumerInstanceId.trim();
        
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from BusinessRule br where br.consumerDeId=:cdeId and br.consumerCatComboId=:ccId and br.consumerInstanceId=:cinst").setString("cdeId", consumerDataElementId).setString("ccId", consumerCatComboId).setString("cinst", consumerInstanceId).list();
            
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                br=(BusinessRule)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        
       return br;
    }
    public BusinessRule getBusinessRuleByProducerCatComboAndProducerInstance(String producerDataElementId,String producerCatComboName,String dhisInstance) throws Exception
    {
        BusinessRule br=null;
        if(producerCatComboName !=null)
        producerCatComboName=producerCatComboName.trim();
        if(dhisInstance !=null)
        dhisInstance=dhisInstance.trim();
        
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from BusinessRule br where br.producerDeId=:pdeId and br.producerCatComboName=:pcc and br.producerInstanceId=:pinst").setString("pdeId", producerDataElementId).setString("pcc", producerCatComboName).setString("pinst", dhisInstance).list();
            //List list = session.createQuery("from BusinessRule br where TRIM(br.producerCatComboName)=:pcc and TRIM(br.producerInstanceId)=:pinst").setString("pcc", producerCatComboName).setString("pinst", dhisInstance).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                br=(BusinessRule)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        
       return br;
    }
    public BusinessRule getBusinessRuleByConsumerAndProducerParameters(String producerDe,String producerCatCombo,String consumerDe,String consumerCatCombo) throws Exception
    {
        BusinessRule br=null;
        if(producerDe !=null)
        producerDe=producerDe.trim();
        if(producerCatCombo !=null)
        producerCatCombo=producerCatCombo.trim();
        if(consumerDe !=null)
        consumerDe=consumerDe.trim();
        if(consumerCatCombo !=null)
        consumerCatCombo=consumerCatCombo.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from BusinessRule br where TRIM(br.producerDeName)=:pde and TRIM(br.producerCatComboName)=:pcc and TRIM(br.consumerDeName)=:cde and TRIM(br.consumerCatComboName)=:ccc").setString("pde", producerDe).setString("pcc", producerCatCombo).setString("cde", consumerDe).setString("ccc", consumerCatCombo).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                br=(BusinessRule)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        
       return br;
    }
    public BusinessRule getBusinessRule(int recordId) throws Exception
    {
        BusinessRule br=null;
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BusinessRule br where br.recordId=:id").setInteger("id", recordId).list();
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
            br=(BusinessRule)list.get(0);
        }
       return br;
    }
    public List getBusinessRuleByConsumerParameters(String consumerInstance,String consumerDe,String consumerCatCombo) throws Exception
    {
        List mainList=new ArrayList();
        List list = new ArrayList();
        if(consumerInstance !=null)
        consumerInstance=consumerInstance.trim();
        if(consumerDe !=null)
        consumerDe=consumerDe.trim();
        if(consumerCatCombo !=null)
        consumerCatCombo=consumerCatCombo.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BusinessRule br where TRIM(br.consumerInstanceId)=:cinst and TRIM(br.consumerDeId)=:cde and TRIM(br.consumerCatComboId)=:ccc").setString("cinst", consumerInstance).setString("cde", consumerDe).setString("ccc", consumerCatCombo).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        if(list !=null)
        {
            BusinessRule br=null;
            for(Object obj: list)
            {
                br=(BusinessRule)obj;
                mainList.add(getBusinessRule(br));
            }
        }
       return mainList;
    }
    public BusinessRule getBusinessRuleByProducerParameters(String producerInstance,String producerDe,String producerCatCombo) throws Exception
    {
        BusinessRule br=null;
        List list = new ArrayList();
        if(producerInstance !=null)
        producerInstance=producerInstance.trim();
        if(producerDe !=null)
        producerDe=producerDe.trim();
        if(producerCatCombo !=null)
        producerCatCombo=producerCatCombo.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BusinessRule br where TRIM(br.producerInstanceId)=:pinst and TRIM(br.producerDeId)=:pde and TRIM(br.producerCatComboId)=:pcc").setString("pinst", producerInstance).setString("pde", producerDe).setString("pcc", producerCatCombo).list();
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
            br=(BusinessRule)list.get(0);
        }
       return br;
    }
    public List getBusinessRules(String producerInstance,String consumerInstance) throws Exception
    {
        List mainList=new ArrayList();
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BusinessRule br where br.producerInstanceId=:pinst and br.consumerInstanceId=:cinst").setString("pinst", producerInstance).setString("cinst", consumerInstance).list();
            tx.commit();
            closeSession(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
        if(list !=null)
        {
            BusinessRule br=null;
            for(Object obj: list)
            {
                br=(BusinessRule)obj;
                mainList.add(getBusinessRule(br));
            }
        }
       return mainList;
    }
    public List getAllBusinessRules() throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BusinessRule br").list();
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
    public List getDistinctBusinessRuleInstances() throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct br.producerInstanceId, br.consumerInstanceId from BusinessRule br").list();
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
            list = session.createQuery("select distinct br.producerInstanceId from BusinessRule br").list();
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
            list = session.createQuery("select distinct br.consumerInstanceId from BusinessRule br").list();
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
            list = session.createQuery("select distinct br.consumerInstanceId from BusinessRule br where br.producerInstanceId=:pinst").setString("pinst", producerInstanceId).list();
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
    public void saveBusinessRule(BusinessRule bizrule) throws Exception
    {
        if(bizrule !=null)
        {
            try
            {
                //System.out.println("bizrule id is "+bizrule.getConsumerDeId()+" and name is "+bizrule.getProducerDeId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(bizrule);
                tx.commit();
                closeSession(session);  
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void updateBusinessRule(BusinessRule bizrule) throws Exception
    {
        if(bizrule !=null)
        {
            try
            {
                //System.out.println("bizrule id is "+bizrule.getConsumerDeId()+" and name is "+bizrule.getProducerDeId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(bizrule);
                tx.commit();
                closeSession(session);  
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public void deleteBusinessRule(BusinessRule bizrule) throws Exception
    {
        if(bizrule !=null)
        {
            try
            {
                //System.out.println("bizrule id is "+bizrule.getConsumerDeId()+" and name is "+bizrule.getProducerDeId());
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(bizrule);
                tx.commit();
                closeSession(session);  
            }
            catch(Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
    public String getUniqueId() throws Exception
    {
        AppUtility appUtil=new AppUtility();
        return appUtil.generateUniqueId(11);
    }
    
    public BusinessRule getBusinessRule(BusinessRule br) throws Exception
    {
        if(br !=null)
        {
           DaoUtil util=new DaoUtil();
           
           DxCategoryCombination consumerCatCombo=util.getDxCategoryCombinationDaoInstance().getCategoryCombination(br.getConsumerCatComboId());
           DxCategoryCombination producerCatCombo=util.getDxCategoryCombinationDaoInstance().getCategoryCombination(br.getProducerCatComboId());
           DxDataElement producerDe=util.getDxDataElementDaoInstance().getDataElement(br.getProducerDeId());
           DxDataElement consumerDe=util.getDxDataElementDaoInstance().getDataElement(br.getConsumerDeId());
           DhisInstance consumerInst=util.getDhisInstanceDaoInstance().getDhisInstanceById(br.getConsumerInstanceId());
           DhisInstance producerInst=util.getDhisInstanceDaoInstance().getDhisInstanceById(br.getProducerInstanceId());
          
           if(consumerCatCombo !=null)
           br.setConsumerCatComboName(consumerCatCombo.getCatComboName());
           if(producerCatCombo !=null)
           br.setProducerCatComboName(producerCatCombo.getCatComboName());
           if(producerDe !=null)
           br.setProducerDeName(producerDe.getDataElementName());
           if(consumerDe !=null)
           br.setConsumerDeName(consumerDe.getDataElementName());
            if(consumerInst !=null)
           br.setConsumerInstanceName(consumerInst.getDhisName());
           if(producerInst !=null)
           br.setProducerInstanceName(producerInst.getDhisName());
        }
        return br;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
