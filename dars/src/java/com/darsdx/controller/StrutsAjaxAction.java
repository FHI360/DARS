/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.dao.DaoUtility;
import com.darsdx.util.AppUtility;
import java.io.PrintWriter;
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
public class StrutsAjaxAction extends org.apache.struts.action.Action {

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
        HttpSession session=request.getSession();
        String reqParam=request.getParameter("qparam");
        String id=request.getParameter("id");
        String returnValue=null;
        System.err.println("reqParam is "+reqParam);
        PrintWriter out=response.getWriter();
        AppUtility appUtil=new AppUtility();
        String userName=appUtil.getCurrentUser(session);
        String uniqueId=null;
        if(reqParam==null)
        {
            
        }
        else
        {
            DaoUtility util=new DaoUtility();
            String searchKeyWard="";
            if(id !=null)
            {
                if(id.equalsIgnoreCase("search"))
                {
                    String level2OuId="All";
                    String level3OuId="All";
                    String level4OuId="All";
                    
                    if(reqParam !=null && reqParam.trim().length()>0)
                    {
                        //level2OuId=reqParam;
                        if(reqParam.indexOf(":") !=-1)
                        {
                            String[] reqParamArray=reqParam.split(":");
                            if(reqParamArray.length>1)
                            {
                                searchKeyWard=reqParamArray[0];
                                level3OuId=reqParamArray[1];
                                level4OuId=reqParamArray[2];
                            }
                        }
                    }
                }
                else if(id.equalsIgnoreCase("uniqueId"))
                {
                   returnValue=null;
                }
        }    
        }
        out.print(returnValue);
        out.flush();
        return null;
    }
}
