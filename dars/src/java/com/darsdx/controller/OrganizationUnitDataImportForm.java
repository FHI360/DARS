/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitDataImportForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String dhisInstance;
    private FormFile uploadedFile;

    
    public OrganizationUnitDataImportForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDhisInstance() {
        return dhisInstance;
    }

    public void setDhisInstance(String dhisInstance) {
        this.dhisInstance = dhisInstance;
    }

    public FormFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(FormFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    uploadedFile=null;
}
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null)
        return errors;
        else if(getUploadedFile()==null)
        errors.add("uploadedFile", new ActionMessage("error.fileUpload.required"));
        return errors;
    }
}
