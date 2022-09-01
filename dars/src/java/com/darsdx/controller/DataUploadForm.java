/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author smomoh
 */
public class DataUploadForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String dhisInstance;
    private String typeOfDataForUpload;
    private FormFile uploadedFile;
    private List instanceList=new ArrayList();

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

    public List getInstanceList() {
        return instanceList;
    }

    public void setInstanceList(List instanceList) {
        this.instanceList = instanceList;
    }

    public String getTypeOfDataForUpload() {
        return typeOfDataForUpload;
    }

    public void setTypeOfDataForUpload(String typeOfDataForUpload) {
        this.typeOfDataForUpload = typeOfDataForUpload;
    }
    
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    uploadedFile=null;
}
    
    public DataUploadForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        return errors;
    }
}
