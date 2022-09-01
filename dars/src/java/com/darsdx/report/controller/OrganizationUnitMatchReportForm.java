/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.report.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitMatchReportForm extends org.apache.struts.action.ActionForm {
    private String actionName;
    private String producerInstance;
    private String consumerInstance;
    private List producerInstanceList=new ArrayList();
    private List consumerInstanceList=new ArrayList();
    
   public OrganizationUnitMatchReportForm() {
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

    public List getConsumerInstanceList() {
        return consumerInstanceList;
    }

    public void setConsumerInstanceList(List consumerInstanceList) {
        this.consumerInstanceList = consumerInstanceList;
    }

    public String getProducerInstance() {
        return producerInstance;
    }

    public void setProducerInstance(String producerInstance) {
        this.producerInstance = producerInstance;
    }

    public List getProducerInstanceList() {
        return producerInstanceList;
    }

    public void setProducerInstanceList(List producerInstanceList) {
        this.producerInstanceList = producerInstanceList;
    }
   
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    producerInstance=null;
    consumerInstance=null;
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
