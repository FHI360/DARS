/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

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
public class HomeAction extends org.apache.struts.action.Action {

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
            throws Exception {
        HomeForm hf=(HomeForm)form;
        String q=request.getParameter("q");
        HttpSession session=request.getSession();
        String requiredAction=hf.getActionName();
        if(q !=null)
        requiredAction=q; 
        
        if(requiredAction==null || requiredAction.equalsIgnoreCase("login"))
        {
            return mapping.findForward("homePage");
        }
        else if(requiredAction.equalsIgnoreCase("mdmgt"))
        {
            hf.reset(mapping, request);
            return mapping.findForward("metadataMgtPage");
        }//
        else if(requiredAction.equalsIgnoreCase("loaddata"))
        {
            hf.reset(mapping, request);
            return mapping.findForward("eiddatauploadPage");
        }
        
        hf.reset(mapping, request);
        return mapping.findForward("homePage");
    }
}
