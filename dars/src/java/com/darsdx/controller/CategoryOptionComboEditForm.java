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

/**
 *
 * @author smomoh
 */
public class CategoryOptionComboEditForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String dhisInstance;
    private String[] disable;
    private String[] delete;
    private List dhisInstanceList=new ArrayList();

    
    public CategoryOptionComboEditForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String[] getDelete() {
        return delete;
    }

    public void setDelete(String[] delete) {
        this.delete = delete;
    }

    public String getDhisInstance() {
        return dhisInstance;
    }

    public void setDhisInstance(String dhisInstance) {
        this.dhisInstance = dhisInstance;
    }

    public List getDhisInstanceList() {
        return dhisInstanceList;
    }

    public void setDhisInstanceList(List dhisInstanceList) {
        this.dhisInstanceList = dhisInstanceList;
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
    dhisInstance=null;
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
