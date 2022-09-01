/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.util.DateManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class TaskManagerForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String hiddenTaskId;
    private String dataSource;
    private String dataSink;
    private String name;
    private String deCatComboMapping;
    private String orgUnitMapping;
    private  String frequency;
    private  String startDate;
    private  String endDate;
    
    
    public TaskManagerForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDataSink() {
        return dataSink;
    }

    public void setDataSink(String dataSink) {
        this.dataSink = dataSink;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDeCatComboMapping() {
        return deCatComboMapping;
    }

    public void setDeCatComboMapping(String deCatComboMapping) {
        this.deCatComboMapping = deCatComboMapping;
    }

    public String getOrgUnitMapping() {
        return orgUnitMapping;
    }

    public void setOrgUnitMapping(String orgUnitMapping) {
        this.orgUnitMapping = orgUnitMapping;
    }

    public String getHiddenTaskId() {
        return hiddenTaskId;
    }

    public void setHiddenTaskId(String hiddenTaskId) {
        this.hiddenTaskId = hiddenTaskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    dataSource=null;
    dataSink=null;
    deCatComboMapping=null;
    orgUnitMapping=null;
    frequency=null;
    startDate=null;
    endDate=null;
    hiddenTaskId=null;
    name=null;
    startDate=DateManager.getDefaultStartDateForReports();
    endDate=DateManager.getDefaultEndDateForReports();
}
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(this.getActionName()==null)
        return errors;
        else if(this.getName()==null || this.getName().trim().length()==0)
        errors.add("name", new ActionMessage("errors.taskName.required"));
        else if(this.getDataSource()==null || this.getDataSource().trim().length()==0 || this.getDataSource().trim().equalsIgnoreCase("select"))
        errors.add("dataSource", new ActionMessage("errors.dataSource.required"));
        else if(this.getDataSink()==null || this.getDataSink().trim().length()==0 || this.getDataSink().trim().equalsIgnoreCase("select"))
        errors.add("dataSink", new ActionMessage("errors.dataSink.required"));
        else if(this.getDeCatComboMapping()==null || this.getDeCatComboMapping().trim().length()==0 || this.getDeCatComboMapping().trim().equalsIgnoreCase("select"))
        errors.add("deCatComboMapping", new ActionMessage("errors.deCatComboMapping.required")); 
        else if(this.getOrgUnitMapping()==null || this.getOrgUnitMapping().trim().length()==0 || this.getOrgUnitMapping().trim().equalsIgnoreCase("select"))
        errors.add("orgUnitMapping", new ActionMessage("errors.orgUnitMapping.required"));    
        return errors;
    }
}
