/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DhisInstance;
import com.darsdx.dao.DaoUtil;
import com.darsdx.operationsmanagement.DatavalueManager;
import com.darsdx.util.ExcelReader;
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
public class BusinessRuleUploadAction extends org.apache.struts.action.Action {

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
        BusinessRuleUploadForm bruf=(BusinessRuleUploadForm)form;
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        String requiredAction=bruf.getActionName();
        String producerInstance=bruf.getProducerInstance();
        String consumerInstance=bruf.getConsumerInstance();
        FormFile uploadedFile=bruf.getUploadedFile();
        
        List instanceList=util.getDhisInstanceDaoInstance().getAllDhisInstances();
        List filteredList=new ArrayList();
        
        
        if(producerInstance ==null || producerInstance ==null)
        {
            if(instanceList !=null && !instanceList.isEmpty())
            {
                DhisInstance dhisInstance=(DhisInstance)instanceList.get(0);
                filteredList=util.getDhisInstanceDaoInstance().filterInstance(instanceList, dhisInstance.getDhisId());
            }
        }
        DhisInstance pdhisInstance=(DhisInstance)util.getDhisInstanceDaoInstance().getDhisInstanceById(producerInstance);
        DhisInstance cdhisInstance=(DhisInstance)util.getDhisInstanceDaoInstance().getDhisInstanceById(consumerInstance);
        session.setAttribute("busrpinstance",pdhisInstance);
        session.setAttribute("busrcinstance",cdhisInstance);
        if(requiredAction==null)
        {
            session.setAttribute("busrproducerInstanceList",instanceList);
            session.setAttribute("busrconsumerInstanceList",filteredList);
            bruf.reset(mapping, request);
            return mapping.findForward("uploadpage");
        }
        else if(requiredAction.equalsIgnoreCase("producerChanged"))
        {
            filteredList=util.getDhisInstanceDaoInstance().filterInstance(instanceList, producerInstance);
            session.setAttribute("busrproducerInstanceList",instanceList);
            session.setAttribute("busrconsumerInstanceList",filteredList);
            return mapping.findForward("uploadpage");
        }
        else if(requiredAction.equalsIgnoreCase("consumerChanged"))
        {
            filteredList=util.getDhisInstanceDaoInstance().filterInstance(instanceList, consumerInstance);
            session.setAttribute("busrproducerInstanceList",filteredList);
            session.setAttribute("busrconsumerInstanceList",instanceList);
            return mapping.findForward("uploadpage");
        }
        else if(requiredAction.equalsIgnoreCase("brExcelUpload"))
        {
            String msg="No record saved";
            ExcelReader er=new ExcelReader();
            if(uploadedFile !=null)
            {
                InputStream is=uploadedFile.getInputStream();
                if(is !=null)
                {
                    int numberSaved=er.readBusinessRuleFromExcelTable(is, producerInstance, consumerInstance);
                    if(numberSaved>0)
                    msg=numberSaved+" records save";
                }
            }
            request.setAttribute("bruplmsg", msg);
        }
        return mapping.findForward("uploadpage");
    }
}
