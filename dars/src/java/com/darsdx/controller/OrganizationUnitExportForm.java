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
public class OrganizationUnitExportForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String connectionId;
    private String ouLevel1Id;
    private String ouLevel2Id;
    private String ouLevel3Id;
    private String ouLevel4Id;
    private String ouLevel5Id;
    private String ouLevel6Id;
    private String[] selectedOuForExport;
    private String exportOption;
    
    public OrganizationUnitExportForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getOuLevel1Id() {
        return ouLevel1Id;
    }

    public void setOuLevel1Id(String ouLevel1Id) {
        this.ouLevel1Id = ouLevel1Id;
    }

    public String getOuLevel2Id() {
        return ouLevel2Id;
    }

    public void setOuLevel2Id(String ouLevel2Id) {
        this.ouLevel2Id = ouLevel2Id;
    }

    public String getOuLevel3Id() {
        return ouLevel3Id;
    }

    public void setOuLevel3Id(String ouLevel3Id) {
        this.ouLevel3Id = ouLevel3Id;
    }

    public String getOuLevel4Id() {
        return ouLevel4Id;
    }

    public void setOuLevel4Id(String ouLevel4Id) {
        this.ouLevel4Id = ouLevel4Id;
    }

    public String getOuLevel5Id() {
        return ouLevel5Id;
    }

    public void setOuLevel5Id(String ouLevel5Id) {
        this.ouLevel5Id = ouLevel5Id;
    }

    public String getOuLevel6Id() {
        return ouLevel6Id;
    }

    public void setOuLevel6Id(String ouLevel6Id) {
        this.ouLevel6Id = ouLevel6Id;
    }

    public String[] getSelectedOuForExport() {
        return selectedOuForExport;
    }

    public void setSelectedOuForExport(String[] selectedOuForExport) {
        this.selectedOuForExport = selectedOuForExport;
    }

    public String getExportOption() {
        return exportOption;
    }

    public void setExportOption(String exportOption) {
        this.exportOption = exportOption;
    }
    
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    selectedOuForExport=null;
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
