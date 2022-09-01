/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.report.controller;

import com.darsdx.business.DhisInstance;
import com.darsdx.dao.DaoUtil;
import com.darsdx.util.ExcelWriter;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.write.WritableWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitMatchReportAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE="paramPage";
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
        OrganizationUnitMatchReportForm reportform=(OrganizationUnitMatchReportForm)form;
        DaoUtil util=new DaoUtil();
        
        String requiredAction=reportform.getActionName();
        String producerInstance=reportform.getProducerInstance();
        String consumerInstance=reportform.getConsumerInstance();
        
        List producerInstanceList=new ArrayList();
        List consumerInstanceList=new ArrayList();
        
        List producerInstanceCodeList=util.getDxOrganizationUnitMatchDaoInstance().getDistinctProducerInstances();
        List consumerInstanceCodeList=util.getDxOrganizationUnitMatchDaoInstance().getDistinctConsumerInstances(producerInstance);
        
        if(producerInstanceCodeList !=null)
        producerInstanceList.addAll(util.getDhisInstanceDaoInstance().getInstance(producerInstanceCodeList));
        if(consumerInstanceCodeList !=null)
        consumerInstanceList.addAll(util.getDhisInstanceDaoInstance().getInstance(consumerInstanceCodeList));
        
        reportform.setProducerInstanceList(producerInstanceList);
        reportform.setConsumerInstanceList(consumerInstanceList);
        
        if(requiredAction ==null)
        {
            reportform.reset(mapping, request);
            if(producerInstanceCodeList !=null && !producerInstanceCodeList.isEmpty())
            producerInstance=producerInstanceCodeList.get(0).toString();
            consumerInstanceCodeList=util.getDxOrganizationUnitMatchDaoInstance().getDistinctConsumerInstances(producerInstance);
            consumerInstanceList.addAll(util.getDhisInstanceDaoInstance().getInstance(consumerInstanceCodeList));
            reportform.setConsumerInstanceList(consumerInstanceList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("fetchconsumerList"))
        {
            consumerInstanceCodeList=util.getDxOrganizationUnitMatchDaoInstance().getDistinctConsumerInstances(producerInstance);
            reportform.setConsumerInstanceList(consumerInstanceList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("downloadInExcel"))
        {
            //DaoUtil util=new DaoUtil();
            String consumerInstanceName=" ";
            String producerInstanceName=" ";
            DhisInstance consumerInst=util.getDhisInstanceDaoInstance().getDhisInstanceById(consumerInstance);
            DhisInstance producerInst=util.getDhisInstanceDaoInstance().getDhisInstanceById(producerInstance);
            if(consumerInst !=null)
            consumerInstanceName=consumerInst.getDhisName();
            if(producerInst !=null)
            producerInstanceName=producerInst.getDhisName();
            if(consumerInstanceName !=null)
            consumerInstanceName=consumerInstanceName.replaceAll(" ", "_");
            if(producerInstanceName !=null)
            producerInstanceName=producerInstanceName.replaceAll(" ", "_");
            String fileName=producerInstanceName+"-"+consumerInstanceName+"_OrgUnit_mapping";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
            OutputStream os=response.getOutputStream();
            ExcelWriter ew=new ExcelWriter();
            List list=util.getDxOrganizationUnitMatchDaoInstance().getDxOrganizationUnitMatchByDhisInstances(producerInstance, consumerInstance);
            System.err.println("oum list size is "+list.size());
            
            WritableWorkbook wworkbook=ew.writeOrgUnitMatchToExcel(os, list,producerInstanceName, consumerInstanceName);
            if(wworkbook !=null)
            {
                wworkbook.write();
                wworkbook.close();
            }

            os.close();
            return null;
        }
        return mapping.findForward(SUCCESS);
    }
}
