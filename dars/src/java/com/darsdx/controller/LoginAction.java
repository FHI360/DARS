/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.User;
import com.darsdx.operationsmanagement.UserManager;
import com.darsdx.util.AppUtility;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class LoginAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String LOGINPAGE = "loginPage";

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
        AppUtility appUtil=new AppUtility();
        LoginForm lform=(LoginForm)form;
        String username = lform.getUsername();
        String password = lform.getPassword();
        String requiredAction=lform.getActionName();
        if(requiredAction==null)
        {
            return mapping.findForward(LOGINPAGE);
        }
        else
        {
            if(username !=null)
            username=username.trim();
            if(password !=null)
            password=password.trim();

            String dbsource=getServlet().getServletContext().getRealPath("/Resources/dars2");
            String destination="C:\\Dars\\dars2";
            String dbpath="C:\\Dars\\dars2\\darsdb";
            File file=new File(dbpath);
            if(!file.exists())
            {
                appUtil.createDatabase(dbsource, destination);
            }
            User user=UserManager.getUser(username, password);
            if(user ==null)
            {
                String msg="Error: Wrong user name or password";
                if(!file.exists())
                {
                    msg="Error: Unable to connect to database";
                }
                //user does not exist, back to login page
                System.err.println("User with username:"+password+" Password:"+password+" does not exist");
                request.setAttribute("loginError", msg);
                return mapping.findForward(LOGINPAGE);
            }
        }
        System.err.println("User with username:"+password+" Password:"+password+" exist");
        //Open home page
        return mapping.findForward(SUCCESS);
    }
}
