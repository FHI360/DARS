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
public class DatabaseConnectionForm extends org.apache.struts.action.ActionForm 
{
    private String actionName;
    private String databaseType;
    private String connectionId;
    private String connectionName;
    private String dbUrl;
    private String dbName;
    private int portNumber;
    private String userName;
    private String password;
    private String hiddenPassword;
        
    public DatabaseConnectionForm() {
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

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getHiddenPassword() {
        return hiddenPassword;
    }

    public void setHiddenPassword(String hiddenPassword) {
        this.hiddenPassword = hiddenPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    databaseType=null;
    connectionId=null;
    connectionName=null;
    dbUrl=null;
    dbName=null;
    portNumber=0;
    userName=null;
    password=null;
    hiddenPassword=null;
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
        if(getConnectionName()==null || getConnectionName().trim().length()==0)
        errors.add("connectionName", new ActionMessage("error.connectionName.required"));
        else if(this.getDatabaseType()==null || getDatabaseType().trim().length()==0)
        errors.add("databaseType", new ActionMessage("error.databaseType.required"));
        else if(this.getDbName()==null || getDbName().trim().length()==0)
        errors.add("dbName", new ActionMessage("error.dbName.required"));
        else if(this.getDbUrl()==null || getDbUrl().trim().length()==0)
        errors.add("dbUrl", new ActionMessage("error.dbUrl.required"));
        return errors;
    }
}
