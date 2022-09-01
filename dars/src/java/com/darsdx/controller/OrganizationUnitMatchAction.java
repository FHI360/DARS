/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.util.AppUtility;
import com.darsdx.business.DhisInstance;
import com.darsdx.business.DxOrganizationUnitMatch;
import com.darsdx.dao.DaoUtil;
import com.darsdx.dao.DhisInstanceDao;
import com.darsdx.dao.DhisInstanceDaoImpl;
import com.darsdx.dao.DxOrganizationUnitDao;
import com.darsdx.dao.DxOrganizationUnitDaoImpl;
import com.darsdx.dao.DxOrganizationUnitMatchDao;
import com.darsdx.dao.DxOrganizationUnitMatchDaoImpl;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitMatchAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE = "oumparampage";
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
        OrganizationUnitMatchForm oumform=(OrganizationUnitMatchForm)form;
        DhisInstanceDao dinstdao=new DhisInstanceDaoImpl();
        DxOrganizationUnitDao dxorgdao=new DxOrganizationUnitDaoImpl();
        //FormFile uploadedFile=oumform.getUploadedFile();
        //InputStream inputStream=null;
        String requiredAction=oumform.getActionName();
        String producerInstance=oumform.getProducerInstance();
        String consumerInstance=oumform.getConsumerInstance();
        int producerOrgUnitLevel=oumform.getProducerOrgUnitLevel();
        int consumerOrgUnitLevel=oumform.getConsumerOrgUnitLevel();
        String producerOrgUnitId=oumform.getProducerOrgUnit();
        String consumerOrgUnitId=oumform.getConsumerOrgUnit();
        HttpSession session=request.getSession();
        List instanceList=dinstdao.getAllDhisInstances();
        List filteredList=new ArrayList();
        //System.err.println("producerInstance is "+producerInstance+" and consumerInstance is "+consumerInstance);
        
        List producerOulevelList=dxorgdao.getOrganizationUnitLevels(producerInstance);
        List consumerOulevelList=dxorgdao.getOrganizationUnitLevels(consumerInstance);
        //System.err.println("producerOulevelList size is "+producerOulevelList.size()+" and consumerOulevelList size is "+consumerOulevelList.size());
        if(producerInstance ==null || consumerInstance ==null)
        {
            if(instanceList !=null && !instanceList.isEmpty())
            {
                DhisInstance dhisInstance=(DhisInstance)instanceList.get(0);
                filteredList=dinstdao.filterInstance(instanceList, dhisInstance.getDhisId());
                producerOulevelList=dxorgdao.getOrganizationUnitLevels(dhisInstance.getDhisId());
                if(filteredList !=null && !filteredList.isEmpty())
                {
                    DhisInstance dhisInstance2=(DhisInstance)filteredList.get(0);
                    consumerOulevelList=dxorgdao.getOrganizationUnitLevels(dhisInstance2.getDhisId());
                }
                session.setAttribute("producerOrgUnitLevelList",producerOulevelList);
                session.setAttribute("consumerOrgUnitLevelList",consumerOulevelList);
            }
        }
        else
        {
            session.setAttribute("producerOrgUnitLevelList",producerOulevelList);
            session.setAttribute("consumerOrgUnitLevelList",consumerOulevelList);
        }
        //System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
            session.setAttribute("producerInstanceList",instanceList);
            session.setAttribute("consumerInstanceList",filteredList);
            session.setAttribute("producerOrgUnitLevelList",producerOulevelList);
            session.setAttribute("consumerOrgUnitLevelList",consumerOulevelList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("producerChanged"))
        {
            filteredList=dinstdao.filterInstance(instanceList, producerInstance);
            setOrgunitLevelList(session,filteredList,producerInstance,consumerInstance);
            session.setAttribute("producerInstanceList",instanceList);
            session.setAttribute("consumerInstanceList",filteredList);
            
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("consumerChanged"))
        {
            filteredList=dinstdao.filterInstance(instanceList, consumerInstance);
            setOrgunitLevelList(session,filteredList,producerInstance,consumerInstance);
            session.setAttribute("producerInstanceList",filteredList);
            session.setAttribute("consumerInstanceList",instanceList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("next"))
        {
            setOrgUnitList(session, producerInstance, consumerInstance, producerOrgUnitLevel,consumerOrgUnitLevel);
            DhisInstance pdhisInstance=(DhisInstance)dinstdao.getDhisInstanceById(producerInstance);
            DhisInstance cdhisInstance=(DhisInstance)dinstdao.getDhisInstanceById(consumerInstance);
            session.setAttribute("pinstance",pdhisInstance);
            session.setAttribute("cinstance",cdhisInstance);
            
            return mapping.findForward("oumatchpage");
        }
        else if(requiredAction.equalsIgnoreCase("back"))
        {
            setOrgUnitList(session, producerInstance, consumerInstance, producerOrgUnitLevel,consumerOrgUnitLevel);
            
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            AppUtility appUtil=new AppUtility();
            DhisInstance pdhisInstance=(DhisInstance)session.getAttribute("pinstance");
            DhisInstance cdhisInstance=(DhisInstance)session.getAttribute("cinstance");
            //String matchDescription=null;
            if(pdhisInstance !=null && cdhisInstance !=null)
            {
                if(producerInstance==null)
                producerInstance=pdhisInstance.getDhisId();
                if(consumerInstance==null)
                consumerInstance=cdhisInstance.getDhisId();
                String matchDescription=producerInstance+"-"+consumerInstance;
                DxOrganizationUnitMatch oum=new DxOrganizationUnitMatch();
                oum.setConsumerInstanceId(consumerInstance);
                oum.setProducerInstanceId(producerInstance);
                oum.setMatchDescription(matchDescription);
                oum.setProducerOrgUnitId(producerOrgUnitId);
                oum.setConsumerOrgUnitId(consumerOrgUnitId);
                oum.setLastModifiedDate(appUtil.getCurrentDate());
                DxOrganizationUnitMatchDao oumdao=new DxOrganizationUnitMatchDaoImpl();
                oumdao.saveOrganizationUnit(oum);
                
            }
            setOrgUnitList(session, producerInstance, consumerInstance, producerOrgUnitLevel,consumerOrgUnitLevel);
            return mapping.findForward("oumatchpage");
        }
        return mapping.findForward(PARAMPAGE);
    }
    private void setOrgUnitList(HttpSession session, String producerInstance, String consumerInstance, int producerOrgUnitLevel,int consumerOrgUnitLevel)
    {
        DaoUtil util=new DaoUtil();
        try
        {
            //System.err.println(producerInstance+" "+consumerInstance+" "+producerOrgUnitLevel+" "+consumerOrgUnitLevel);
            List producerOuList=util.getDxOrganizationUnitDaoInstance().getOrganizationUnitsNotMatched(producerInstance, consumerInstance,producerOrgUnitLevel,producerInstance);
            List consumerOuList=util.getDxOrganizationUnitDaoInstance().getOrganizationUnitsNotMatched(producerInstance,consumerInstance, consumerOrgUnitLevel,consumerInstance);
            session.setAttribute("producerOrgUnitList",producerOuList);
            session.setAttribute("consumerOrgUnitList",consumerOuList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void setOrgunitLevelList(HttpSession session,List filteredList,String producerInstance,String consumerInstance)
    {
        try
        {
            DxOrganizationUnitDao dxorgdao=new DxOrganizationUnitDaoImpl();
            if(filteredList !=null && !filteredList.isEmpty())
            consumerInstance=((DhisInstance)filteredList.get(0)).getDhisId();
            List producerOulevelList=dxorgdao.getOrganizationUnitLevels(producerInstance);
            List consumerOulevelList=dxorgdao.getOrganizationUnitLevels(consumerInstance);
            session.setAttribute("producerOrgUnitLevelList",producerOulevelList);
            session.setAttribute("consumerOrgUnitLevelList",consumerOulevelList);
            //System.err.println("producerOulevelList 2 size is "+producerOulevelList.size()+" and consumerOulevelList 2 size is "+consumerOulevelList.size());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}