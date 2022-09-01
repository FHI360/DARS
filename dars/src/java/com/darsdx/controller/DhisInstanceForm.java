/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.util.AppUtility;
import com.darsdx.util.InputValidator;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class DhisInstanceForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String dhisName;
    private String apiUrl;
    private String dhisHomeUrl;
    private String userName;
    private String password;
    private String hiddenPassword;
    private String dhisId;
    private String connectionType;
    
    public DhisInstanceForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getDhisHomeUrl() {
        return dhisHomeUrl;
    }

    public void setDhisHomeUrl(String dhisHomeUrl) {
        this.dhisHomeUrl = dhisHomeUrl;
    }

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public String getDhisName() {
        return dhisName;
    }

    public void setDhisName(String dhisName) {
        this.dhisName = dhisName;
    }

    public String getHiddenPassword() {
        return hiddenPassword;
    }

    public void setHiddenPassword(String hiddenPassword) {
        this.hiddenPassword = hiddenPassword;
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

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        apiUrl=null;
        dhisName=null;
        dhisHomeUrl=null;
        userName=null;
        password=null;
        hiddenPassword=null;
        dhisId=null;
        connectionType=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
                AppUtility appUtil=new AppUtility();
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("delete"))
        return errors;
        else
        {
            if(this.getDhisName()==null || !AppUtility.isString(getDhisName())|| getDhisName().length()<3)
            errors.add("dhisName", new ActionMessage("error.dhisName.required"));
            else if(this.getDhisHomeUrl()==null || this.getDhisHomeUrl().trim().length()<5)
            errors.add("dhisHomeUrl", new ActionMessage("error.dhisHomeUrl.required"));
            //!InputValidator.isValidUrl(getDhisHomeUrl())
            /*else if(getUserName()==null || getUserName().length()<3)
            errors.add("userName", new ActionMessage("error.userName.required"));
            else if(getActionName().equalsIgnoreCase("save") && (getPassword()==null || getPassword().length()<3))
            errors.add("password", new ActionMessage("error.password.required"));*/
        }
        return errors;
    }
}
