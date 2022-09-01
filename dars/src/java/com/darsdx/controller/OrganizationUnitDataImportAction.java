/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.dao.DaoUtil;
import com.darsdx.util.ExcelReader;
import java.io.InputStream;
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
public class OrganizationUnitDataImportAction extends org.apache.struts.action.Action {

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
        OrganizationUnitDataImportForm oudiform=(OrganizationUnitDataImportForm)form;
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        
        String requiredAction=oudiform.getActionName();
        List instanceList=util.getDhisInstanceDaoInstance().getAllDhisInstances();
        session.setAttribute("ouimportinstanceList",instanceList);
        if(requiredAction==null)
        {
            oudiform.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("import"))
        {
            FormFile uploadedFile=oudiform.getUploadedFile();
            String dhisInstance=oudiform.getDhisInstance();
            InputStream is=uploadedFile.getInputStream();
            if(is !=null)
            {
                ExcelReader exr=new ExcelReader();
                List list=exr.readOrganizationUnitFromExcel(is, dhisInstance);
            }
        }
        return mapping.findForward(SUCCESS);
    }
}
