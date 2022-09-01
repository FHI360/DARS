/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.report.controller;

import com.darsdx.business.BusinessRule;
import com.darsdx.business.DhisInstance;
import com.darsdx.dao.BusinessRuleDao;
import com.darsdx.dao.BusinessRuleDaoImpl;
import com.darsdx.dao.DaoUtil;
import com.darsdx.dao.DhisInstanceDao;
import com.darsdx.dao.DhisInstanceDaoImpl;
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
public class BusinessRuleReportAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE = "paramPage";

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
        BusinessRuleReportForm reportform=(BusinessRuleReportForm)form;
        BusinessRuleDao brdao=new BusinessRuleDaoImpl();
        DhisInstanceDao instancedao=new DhisInstanceDaoImpl();
        String requiredAction=reportform.getActionName();
        String producerInstance=reportform.getProducerInstance();
        String consumerInstance=reportform.getConsumerInstance();
        String reportId=request.getParameter("id");
        if(reportId !=null)
        requiredAction=reportId;
        List producerInstanceList=new ArrayList();
        List consumerInstanceList=new ArrayList();
        
        List producerInstanceCodeList=brdao.getDistinctProducerInstances();
        List consumerInstanceCodeList=brdao.getDistinctConsumerInstances(producerInstance);
        
        if(producerInstanceCodeList !=null)
        producerInstanceList.addAll(instancedao.getInstance(producerInstanceCodeList));
        if(consumerInstanceCodeList !=null)
        consumerInstanceList.addAll(instancedao.getInstance(consumerInstanceCodeList));
        
        reportform.setProducerInstanceList(producerInstanceList);
        reportform.setConsumerInstanceList(consumerInstanceList);
        
        if(requiredAction ==null)
        {
            reportform.reset(mapping, request);
            if(producerInstanceCodeList !=null && !producerInstanceCodeList.isEmpty())
            producerInstance=producerInstanceCodeList.get(0).toString();
            consumerInstanceCodeList=brdao.getDistinctConsumerInstances(producerInstance);
            consumerInstanceList.addAll(instancedao.getInstance(consumerInstanceCodeList));
            reportform.setConsumerInstanceList(consumerInstanceList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("fetchconsumerList"))
        {
            consumerInstanceCodeList=brdao.getDistinctConsumerInstances(producerInstance);
            reportform.setConsumerInstanceList(consumerInstanceList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("brmgt"))
        {
            producerInstanceCodeList=brdao.getDistinctProducerInstances();
            consumerInstanceCodeList=brdao.getDistinctConsumerInstances();
            if(consumerInstanceCodeList !=null)
            {
                consumerInstanceList.addAll(instancedao.getInstance(consumerInstanceCodeList));
                reportform.setConsumerInstanceList(consumerInstanceList);
            }
            return mapping.findForward("bizrulemgt");
        }
        else if(requiredAction.equalsIgnoreCase("loadbizrule"))
        {
            producerInstanceCodeList=brdao.getDistinctProducerInstances();
            consumerInstanceCodeList=brdao.getDistinctConsumerInstances();
            if(consumerInstanceCodeList !=null)
            {
                consumerInstanceList.addAll(instancedao.getInstance(consumerInstanceCodeList));
                reportform.setConsumerInstanceList(consumerInstanceList);
            }
            List bizruleList=brdao.getBusinessRules(producerInstance, consumerInstance);
            request.setAttribute("bizruleList", bizruleList);
            return mapping.findForward("bizrulemgt");
        }
        else if(requiredAction.equalsIgnoreCase("deleteOrdisable"))
        {
            String[] selectedForDelete=reportform.getDelete();
            String[] selectedForDisable=reportform.getDisable();
            BusinessRule br=null;
            if(selectedForDelete !=null)
            {
                for(int i=0; i<selectedForDelete.length; i++)
                {
                    br=brdao.getBusinessRule(Integer.parseInt(selectedForDelete[i]));
                    if(br !=null)
                    brdao.deleteBusinessRule(br);
                }
            }
            if(selectedForDisable !=null)
            {
                for(int i=0; i<selectedForDisable.length; i++)
                {
                    br=brdao.getBusinessRule(Integer.parseInt(selectedForDisable[i]));
                    if(br !=null)
                    {
                        br.setDisable("yes");
                        brdao.updateBusinessRule(br);
                    }
                }
            }
            List bizruleList=brdao.getBusinessRules(producerInstance, consumerInstance);
            request.setAttribute("bizruleList", bizruleList);
            return mapping.findForward("bizrulemgt");
        }
        else if(requiredAction.equalsIgnoreCase("downloadInExcel"))
        {
            DaoUtil util=new DaoUtil();
            String consumerInstanceName=" ";
            String producerInstanceName=" ";
            DhisInstance consumerInst=util.getDhisInstanceDaoInstance().getDhisInstanceById(consumerInstance);
            DhisInstance producerInst=util.getDhisInstanceDaoInstance().getDhisInstanceById(producerInstance);
            if(consumerInst !=null)
            consumerInstanceName=consumerInst.getDhisName();
            if(producerInst !=null)
            producerInstanceName=producerInst.getDhisName();
            String fileName="Business_rule_"+consumerInstanceName+"-"+producerInstanceName;
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
            OutputStream os=response.getOutputStream();
            ExcelWriter ew=new ExcelWriter();
            List list=brdao.getBusinessRules(producerInstance, consumerInstance);
            System.err.println("br list size is "+list.size());
            
            WritableWorkbook wworkbook=ew.writeBusinessRulesToExcel(os, list,producerInstanceName, consumerInstanceName);
            if(wworkbook !=null)
            {
                wworkbook.write();
                wworkbook.close();
            }

            os.close();
            return null;
        }
        return mapping.findForward(PARAMPAGE);
    }
}
