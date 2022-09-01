/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DhisInstance;
import com.darsdx.business.DxOrganizationUnitMatch;
import com.darsdx.dao.DaoUtil;
import com.darsdx.dao.DhisInstanceDao;
import com.darsdx.dao.DhisInstanceDaoImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class OrgUnitMatchEditAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

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
        OrgUnitMatchEditForm oumform=(OrgUnitMatchEditForm)form;
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        DhisInstanceDao dinstdao=new DhisInstanceDaoImpl();
        
        String requiredAction=oumform.getActionName();
        String producerInstance=oumform.getProducerInstance();
        String consumerInstance=oumform.getConsumerInstance();
        String reportId=request.getParameter("id");
        if(reportId !=null)
        requiredAction=reportId;
        List instanceList=dinstdao.getAllDhisInstances();
        List filteredList=new ArrayList();
        if(producerInstance ==null || consumerInstance ==null)
        {
            if(instanceList !=null && !instanceList.isEmpty())
            {
                DhisInstance dhisInstance=(DhisInstance)instanceList.get(0);
                filteredList=dinstdao.filterInstance(instanceList, dhisInstance.getDhisId()); 
            }
        }
        
        
        if(requiredAction ==null)
        {
            oumform.reset(mapping, request);
            oumform.setProducerInstanceList(instanceList);
            oumform.setConsumerInstanceList(filteredList);
            
        }
        else if(requiredAction.equalsIgnoreCase("fetchconsumerList"))
        {
            oumform.setProducerInstanceList(instanceList);
            filteredList=dinstdao.filterInstance(instanceList, producerInstance); 
            oumform.setConsumerInstanceList(filteredList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("loadrecord"))
        {
            List oumList=util.getDxOrganizationUnitMatchDaoInstance().getDxOrganizationUnitMatchByMatchDescription(producerInstance, consumerInstance);
            request.setAttribute("oumList", oumList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("deleteOrdisable"))
        {
            String[] selectedForDelete=oumform.getDelete();
            String[] selectedForDisable=oumform.getDisable();
            DxOrganizationUnitMatch oum=null;
            if(selectedForDelete !=null)
            {
                for(int i=0; i<selectedForDelete.length; i++)
                {
                    oum=util.getDxOrganizationUnitMatchDaoInstance().getDxOrganizationUnitMatchByIdAndMatchDescription(Integer.parseInt(selectedForDelete[i]));
                    if(oum !=null)
                    util.getDxOrganizationUnitMatchDaoInstance().deleteOrganizationUnit(oum);
                }
            }
            if(selectedForDisable !=null)
            {
                for(int i=0; i<selectedForDisable.length; i++)
                {
                    
                }
            }
            List oumList=util.getDxOrganizationUnitMatchDaoInstance().getDxOrganizationUnitMatchByMatchDescription(producerInstance, consumerInstance);
            request.setAttribute("oumList", oumList);
            return mapping.findForward(SUCCESS);
        }
        //oumform.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
}
