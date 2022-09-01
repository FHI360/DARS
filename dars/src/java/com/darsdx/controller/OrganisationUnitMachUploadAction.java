/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DhisInstance;
import com.darsdx.dao.DaoUtil;
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
public class OrganisationUnitMachUploadAction extends org.apache.struts.action.Action {

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
        OrganisationUnitMachUploadForm oumf=(OrganisationUnitMachUploadForm)form;
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        String requiredAction=oumf.getActionName();
        String producerInstance=oumf.getProducerInstance();
        String consumerInstance=oumf.getConsumerInstance();
        FormFile uploadedFile=oumf.getUploadedFile();
        
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
        session.setAttribute("oumpinstance",pdhisInstance);
        session.setAttribute("oumcinstance",cdhisInstance);
        if(requiredAction==null)
        {
            session.setAttribute("oumproducerInstanceList",instanceList);
            session.setAttribute("oumconsumerInstanceList",filteredList);
            oumf.reset(mapping, request);
            return mapping.findForward("uploadpage");
        }
        else if(requiredAction.equalsIgnoreCase("producerChanged"))
        {
            filteredList=util.getDhisInstanceDaoInstance().filterInstance(instanceList, producerInstance);
            session.setAttribute("oumproducerInstanceList",instanceList);
            session.setAttribute("oumconsumerInstanceList",filteredList);
            return mapping.findForward("uploadpage");
        }
        else if(requiredAction.equalsIgnoreCase("consumerChanged"))
        {
            filteredList=util.getDhisInstanceDaoInstance().filterInstance(instanceList, consumerInstance);
            session.setAttribute("oumproducerInstanceList",filteredList);
            session.setAttribute("oumconsumerInstanceList",instanceList);
            return mapping.findForward("uploadpage");
        }
        else if(requiredAction.equalsIgnoreCase("oumExcelUpload"))
        {
            String msg="No record saved";
            ExcelReader er=new ExcelReader();
            if(uploadedFile !=null)
            {
                InputStream is=uploadedFile.getInputStream();
                if(is !=null)
                {
                    int numberSaved=er.readOrgunitMatchFromExcelTable(is, producerInstance, consumerInstance);
                    if(numberSaved>0)
                    msg=numberSaved+" records save";
                }
            }
            request.setAttribute("oumuplmsg", msg);
        }
        return mapping.findForward("uploadpage");
    }
}
