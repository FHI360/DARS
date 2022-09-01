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
public class DatavalueDownloadForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String dhisId;
    private String[] stateCodes;
    private int startYear;
    private int endYear;
    private String[] selectedMths;
    private String dataSetId;
    private String orgunitgroup;
    private String dvurl;
    private String startDate;
    private String endDate;
    
    public DatavalueDownloadForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(String dataSetId) {
        this.dataSetId = dataSetId;
    }

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public String[] getSelectedMths() {
        return selectedMths;
    }

    public void setSelectedMths(String[] selectedMths) {
        this.selectedMths = selectedMths;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public String[] getStateCodes() {
        return stateCodes;
    }

    public void setStateCodes(String[] stateCodes) {
        this.stateCodes = stateCodes;
    }

    public String getOrgunitgroup() {
        return orgunitgroup;
    }

    public void setOrgunitgroup(String orgunitgroup) {
        this.orgunitgroup = orgunitgroup;
    }

    public String getDvurl() {
        return dvurl;
    }

    public void setDvurl(String dvurl) {
        this.dvurl = dvurl;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
    startYear=2017;
    endYear=2019;
    actionName=null;
    stateCodes=null;
    selectedMths=null;
    orgunitgroup=null;
    dvurl=null;
    //dataSetId=null;
    //dhisId=null;
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
