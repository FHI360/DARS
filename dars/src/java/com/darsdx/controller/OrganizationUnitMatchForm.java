/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitMatchForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String producerInstance;
    private String consumerInstance;
    private String producerOrgUnit;
    private String consumerOrgUnit;
    private int producerOrgUnitLevel;
    private int consumerOrgUnitLevel;
    FormFile uploadedFile;

    
    public OrganizationUnitMatchForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getConsumerInstance() {
        return consumerInstance;
    }

    public void setConsumerInstance(String consumerInstance) {
        this.consumerInstance = consumerInstance;
    }

    public String getConsumerOrgUnit() {
        return consumerOrgUnit;
    }

    public void setConsumerOrgUnit(String consumerOrgUnit) {
        this.consumerOrgUnit = consumerOrgUnit;
    }

    public String getProducerOrgUnit() {
        return producerOrgUnit;
    }

    public void setProducerOrgUnit(String producerOrgUnit) {
        this.producerOrgUnit = producerOrgUnit;
    }

    public int getConsumerOrgUnitLevel() {
        return consumerOrgUnitLevel;
    }

    public void setConsumerOrgUnitLevel(int consumerOrgUnitLevel) {
        this.consumerOrgUnitLevel = consumerOrgUnitLevel;
    }

    public int getProducerOrgUnitLevel() {
        return producerOrgUnitLevel;
    }

    public void setProducerOrgUnitLevel(int producerOrgUnitLevel) {
        this.producerOrgUnitLevel = producerOrgUnitLevel;
    }

    public String getProducerInstance() {
        return producerInstance;
    }

    public void setProducerInstance(String producerInstance) {
        this.producerInstance = producerInstance;
    }

    public FormFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(FormFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        actionName=null;
        producerInstance=null;
        consumerInstance=null;
        producerOrgUnitLevel=0;
        consumerOrgUnitLevel=0;
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
        
        return errors;
    }
}
