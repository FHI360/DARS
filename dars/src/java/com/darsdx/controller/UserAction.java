/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.User;
import com.darsdx.dao.UserDao;
import com.darsdx.dao.UserDaoImpl;
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
public class UserAction extends org.apache.struts.action.Action {

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
        UserForm userForm=(UserForm)form;
        User user=new User();
        UserDao dao=new UserDaoImpl();
        HttpSession session=request.getSession();
        String userList=userForm.getUserList();
        String requiredAction=userForm.getActionName();
        
        if(requiredAction==null)
        {
            
           return mapping.findForward(SUCCESS); 
        }
        else if(requiredAction.equalsIgnoreCase("login"))
        {
            
           return mapping.findForward(SUCCESS); 
        }
        return mapping.findForward(SUCCESS);
    }
}
