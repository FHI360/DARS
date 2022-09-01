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
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class BusinessRuleReportForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String producerInstance;
    private String consumerInstance;
    private String[] disable;
    private String[] delete;
    private List producerInstanceList=new ArrayList();
    private List consumerInstanceList=new ArrayList();
    
    public BusinessRuleReportForm() {
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

    public String[] getDelete() {
        return delete;
    }

    public void setDelete(String[] delete) {
        this.delete = delete;
    }

    public String[] getDisable() {
        return disable;
    }

    public void setDisable(String[] disable) {
        this.disable = disable;
    }

    
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    producerInstance=null;
    consumerInstance=null;
    disable=null;
    delete=null;
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
