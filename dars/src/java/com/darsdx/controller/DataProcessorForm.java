/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.util.AppUtility;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class DataProcessorForm extends org.apache.struts.action.ActionForm {
    
    
    private String actionName;
    private String dhisInstance;
    private String businessRuleIndicator;
    private String uniqueRecordIndicator;
    private String dhis2ExportIndicator;
    private String dhisDataUpload;
    private String metadataIdAssignment;
    private String brdesc;
    private String deleteRecord;
    
    private List instanceList=new ArrayList();
    /**
     *
     */
    public DataProcessorForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getBusinessRuleIndicator() {
        return businessRuleIndicator;
    }

    public void setBusinessRuleIndicator(String businessRuleIndicator) {
        this.businessRuleIndicator = businessRuleIndicator;
    }

    public String getUniqueRecordIndicator() {
        return uniqueRecordIndicator;
    }

    public void setUniqueRecordIndicator(String uniqueRecordIndicator) {
        this.uniqueRecordIndicator = uniqueRecordIndicator;
    }

    public List getInstanceList() {
        return instanceList;
    }

    public void setInstanceList(List instanceList) {
        this.instanceList = instanceList;
    }

    public String getDhisInstance() {
        return dhisInstance;
    }

    public void setDhisInstance(String dhisInstance) {
        this.dhisInstance = dhisInstance;
    }

    public String getDhis2ExportIndicator() {
        return dhis2ExportIndicator;
    }

    public void setDhis2ExportIndicator(String dhis2ExportIndicator) {
        this.dhis2ExportIndicator = dhis2ExportIndicator;
    }

    public String getDhisDataUpload() {
        return dhisDataUpload;
    }

    public void setDhisDataUpload(String dhisDataUpload) {
        this.dhisDataUpload = dhisDataUpload;
    }

    public String getMetadataIdAssignment() {
        return metadataIdAssignment;
    }

    public void setMetadataIdAssignment(String metadataIdAssignment) {
        this.metadataIdAssignment = metadataIdAssignment;
    }

    public String getBrdesc() {
        return brdesc;
    }

    public void setBrdesc(String brdesc) {
        this.brdesc = brdesc;
    }

    public String getDeleteRecord() {
        return deleteRecord;
    }

    public void setDeleteRecord(String deleteRecord) {
        this.deleteRecord = deleteRecord;
    }
    
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        businessRuleIndicator=null;
        uniqueRecordIndicator=null;
        dhis2ExportIndicator=null;
        dhisDataUpload=null;
        metadataIdAssignment=null;
        brdesc=null;
        deleteRecord=null;
    }
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        AppUtility appUtil=new AppUtility();
        if(this.getActionName()==null)
        return errors;
        else
        if(this.getBrdesc()==null || !appUtil.isString(this.getBrdesc()))
        errors.add("brdesc", new ActionMessage("brdesc.required"));
        /*else if((getBusinessRuleIndicator()==null || !appUtil.isString(getBusinessRuleIndicator())) && (getUniqueRecordIndicator()==null || !appUtil.isString(getUniqueRecordIndicator())) && (getDhis2ExportIndicator()==null || !appUtil.isString(getDhis2ExportIndicator())) && (getDhisDataUpload()==null || !appUtil.isString(getDhisDataUpload())) && (getMetadataIdAssignment()==null || !appUtil.isString(getMetadataIdAssignment())))
        errors.add("taskIndicator", new ActionMessage("taskIndicator.required"));*/
        return errors;
    }
}
