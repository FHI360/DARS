/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.util.AppUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class DhisDataUploadForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String dhisUrl;
    private String userName;
    private String password;

    
    public DhisDataUploadForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDhisUrl() {
        return dhisUrl;
    }

    public void setDhisUrl(String dhisUrl) {
        this.dhisUrl = dhisUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        dhisUrl=null;
        userName=null;
        password=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = new ActionErrors();
        AppUtility appUtil=new AppUtility();
        if(this.getActionName()==null)
        return errors;
        else if(this.getDhisUrl()==null || !appUtil.isString(this.getDhisUrl()))
        errors.add("dhisUrl", new ActionMessage("error.invalid.url"));
        return errors;
    }
}
