/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DhisOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class DhisDataUploadAction extends org.apache.struts.action.Action {

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
        DhisDataUploadForm dduform=(DhisDataUploadForm)form;
        String requiredAction=dduform.getActionName();
        String dhisUrl=dduform.getDhisUrl();
        String userName=dduform.getUserName();
        String password=dduform.getPassword();
        
        if(requiredAction==null)
        {
            dduform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("upload"))
        {
            DhisOperation dop=new DhisOperation();
            String msg=dop.uploadDataToDhis2(dhisUrl, userName, password);
            request.setAttribute("ddumsg", msg);
        }
        return mapping.findForward(PARAMPAGE);
    }
}
