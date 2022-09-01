/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DatabaseConnection;
import com.darsdx.dao.DaoUtil;
import com.darsdx.util.DateManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class DatabaseConnectionAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
        DatabaseConnectionForm dbcf=(DatabaseConnectionForm)form;
        HttpSession session=request.getSession();
        DaoUtil util=new DaoUtil();
        String requiredAction=dbcf.getActionName();
        String reqParam=request.getParameter("p");
        String connectionId=null;
        enableDisableBtn(session,"false","true");
        if(reqParam !=null)
        {
            connectionId=request.getParameter("id");
            dbcf.setConnectionId(connectionId);
            requiredAction=reqParam; 
        }
        setDatabaseConnectionList(session);
        if(requiredAction==null)
        {
            dbcf.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("ed"))
        {
            //connectionId=dbcf.getConnectionId();
            dbcf.reset(mapping, request);
            dbcf.setConnectionId(connectionId);
            dbcf=getDatabaseConnectionForm(session,dbcf);  
            
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            util.getDatabaseConnectionDaoInstance().saveDatabaseConnection(getDatabaseConnection(dbcf,"default"));
            setDatabaseConnectionList(session);
            dbcf.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            util.getDatabaseConnectionDaoInstance().updateDatabaseConnection(getDatabaseConnection(dbcf,"default"));
            setDatabaseConnectionList(session);
        }
        else if(requiredAction.equalsIgnoreCase("de"))
        {
            DatabaseConnection dbconn=util.getDatabaseConnectionDaoInstance().getDatabaseConnection(connectionId);
            if(dbconn !=null)
            {
                util.getDatabaseConnectionDaoInstance().deleteDatabaseConnection(dbconn);
                setDatabaseConnectionList(session);
            } 
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            util.getDatabaseConnectionDaoInstance().deleteDatabaseConnection(getDatabaseConnection(dbcf,"default"));
            setDatabaseConnectionList(session);
        }
        dbcf.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
    private DatabaseConnectionForm getDatabaseConnectionForm(HttpSession session,DatabaseConnectionForm form)
    {
        try
        {
            String connectionId=form.getConnectionId();
            DaoUtil util=new DaoUtil();
            DatabaseConnection dbconn=util.getDatabaseConnectionDaoInstance().getDatabaseConnection(connectionId);
            if(dbconn !=null)
            {
                form.setConnectionName(dbconn.getConnectionName());
                form.setDatabaseType(dbconn.getDatabaseType());
                form.setDbName(dbconn.getDatabaseName());
                form.setDbUrl(dbconn.getDatabaseURL());
                form.setPortNumber(dbconn.getPortNumber());
                form.setHiddenPassword(dbconn.getPassword());
                form.setUserName(dbconn.getUserName());
                form.setConnectionId(dbconn.getId());
                enableDisableBtn(session,"true","false");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return form;
    }
    private DatabaseConnection getDatabaseConnection(DatabaseConnectionForm form,String userName)
    {
        DatabaseConnection dbconn=new DatabaseConnection();
        dbconn.setId(form.getConnectionId());
        dbconn.setConnectionName(form.getConnectionName());
        dbconn.setDatabaseName(form.getDbName());
        dbconn.setDatabaseType(form.getDatabaseType());
        dbconn.setDatabaseURL(form.getDbUrl());
        dbconn.setDateCreated(DateManager.getCurrentDateAsDateObject());
        dbconn.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
        dbconn.setPassword(form.getPassword());
        dbconn.setPortNumber(form.getPortNumber());
        dbconn.setRecordedBy(userName);
        dbconn.setUserName(form.getUserName());
        return dbconn;
    }
    private void setDatabaseConnectionList(HttpSession session)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            List list=util.getDatabaseConnectionDaoInstance().getAllDatabaseConnections();
            if(list==null)
            list=new ArrayList();
            session.setAttribute("dbConnections", list);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void enableDisableBtn(HttpSession session,String saveValue,String modifyValue)
    {
        session.setAttribute("dbcSaveDisabled", saveValue);
        session.setAttribute("dbcModifyDisabled", modifyValue);
    }
}
