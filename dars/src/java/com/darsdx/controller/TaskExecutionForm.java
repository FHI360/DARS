/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class TaskExecutionForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String hiddenTaskId;
    private  String frequency;
    private  String startDate;
    private  String endDate;
    private String[] periods;
    private int allowZeroValue;
    
    public TaskExecutionForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getHiddenTaskId() {
        return hiddenTaskId;
    }

    public void setHiddenTaskId(String hiddenTaskId) {
        this.hiddenTaskId = hiddenTaskId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String[] getPeriods() {
        return periods;
    }

    public void setPeriods(String[] periods) {
        this.periods = periods;
    }

    public int getAllowZeroValue() {
        return allowZeroValue;
    }

    public void setAllowZeroValue(int allowZeroValue) {
        this.allowZeroValue = allowZeroValue;
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
