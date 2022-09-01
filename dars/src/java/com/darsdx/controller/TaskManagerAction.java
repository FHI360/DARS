/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.ConnectionAttributes;
import com.darsdx.business.DataTransferManager;
import com.darsdx.business.DatabaseConnection;
import com.darsdx.business.DhisInstance;
import com.darsdx.business.DhisOperation;
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
public class TaskManagerAction extends org.apache.struts.action.Action {

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
        TaskManagerForm tmf=(TaskManagerForm)form;
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        String requiredAction =tmf.getActionName();
        setConnectionList(session);
        setTaskList(session);
        getOrgUnitMapping(session);
        getDeCatComboMapping(session);
        String reqParam=request.getParameter("p");
        String hiddenId=null;
        if(reqParam !=null)
        {
            hiddenId=request.getParameter("id");
            System.err.println("hiddenId is "+hiddenId);
            tmf.setHiddenTaskId(hiddenId);
            requiredAction=reqParam; 
        }
        enableDisableBtn(session,"false","true");
        if(requiredAction==null)
        {
            //DhisOperation dop=new DhisOperation();
            //String msg=dop.uploadDataToDhis2(null, "smomoh", "cHerry100");
            //System.err.println("Response message from dop.uploadDataToDhis2 in TaskManagerAction is "+msg);
            tmf.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("ed"))
        {
            tmf=getTaskManagerForm(tmf);
            enableDisableBtn(session,"true","false");
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            DaoUtil.getDataTransferManagerDaoInstance().saveDataTransferManager(getDataTransferManager(tmf));
            setTaskList(session);
            tmf.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            DaoUtil.getDataTransferManagerDaoInstance().updateDataTransferManager(getDataTransferManager(tmf));
            setTaskList(session);
            tmf.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("delete") || requiredAction.equalsIgnoreCase("de"))
        {
            DaoUtil.getDataTransferManagerDaoInstance().deleteDataTransferManager(getDataTransferManager(tmf));
            setTaskList(session);
            tmf.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }
    private void setTaskList(HttpSession session)
    {
        try
        {
            List list=DaoUtil.getDataTransferManagerDaoInstance().getAllDataTransferManagers();
            if(list ==null)
            list=new ArrayList();
            session.setAttribute("dataTransferTaskList", list);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private TaskManagerForm getTaskManagerForm(TaskManagerForm tmf)
    {
        try
        {
            DataTransferManager dtm=DaoUtil.getDataTransferManagerDaoInstance().getDataTransferManager(tmf.getHiddenTaskId());
            tmf.setDataSink(dtm.getDataSink());
            tmf.setDataSource(dtm.getDataSource());
            tmf.setName(dtm.getName());
            tmf.setDeCatComboMapping(dtm.getDataElementMapping());
            tmf.setHiddenTaskId(dtm.getUid());
            tmf.setOrgUnitMapping(dtm.getOrgUnitMapping());
            if(dtm.getFrequency() !=null && dtm.getFrequency().trim().length()>0)
            {
                tmf.setFrequency(dtm.getFrequency());
                tmf.setStartDate(DateManager.convertDateToString(dtm.getStartDate(), DateManager.MM_DD_YYYY_SLASH));
                tmf.setEndDate(DateManager.convertDateToString(dtm.getEndDate(), DateManager.MM_DD_YYYY_SLASH));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return tmf;
    }
    private DataTransferManager getDataTransferManager(TaskManagerForm tmf)
    {
        DataTransferManager dtm=new DataTransferManager();
        dtm.setDataElementMapping(tmf.getDeCatComboMapping());
        dtm.setDataSink(tmf.getDataSink());
        dtm.setDataSource(tmf.getDataSource());
        dtm.setDateCreated(DateManager.getCurrentDateAsDateObject());
        dtm.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
        dtm.setName(tmf.getName());
        dtm.setOrgUnitMapping(tmf.getOrgUnitMapping());
        dtm.setUid(tmf.getHiddenTaskId());
        dtm.setFrequency(tmf.getFrequency());
        dtm.setStartDate(DateManager.getDateInstance(DateManager.processMthDayYearToMysqlFormat(tmf.getStartDate())));
        dtm.setEndDate(DateManager.getDateInstance(DateManager.processMthDayYearToMysqlFormat(tmf.getEndDate())));
        if(dtm.getFrequency()==null)
        {
            dtm.setStartDate(DateManager.getDateInstance("1900-01-01"));
            dtm.setEndDate(DateManager.getDateInstance("1900-01-01"));
        }
        System.err.println("tmf.getHiddenTaskId() "+tmf.getHiddenTaskId());
        return dtm;
    }
    private void setConnectionList(HttpSession session)
    {
        try
        {
            ConnectionAttributes conatt=new ConnectionAttributes();
            List list=new ArrayList();
            List apiConnectionList=DaoUtil.getDhisInstanceDaoInstance().getAllDhisInstances();
            if(apiConnectionList !=null)
            {
                DhisInstance apiconn=null;
                for(int i=0; i<apiConnectionList.size(); i++)
                {
                    apiconn=(DhisInstance)apiConnectionList.get(i);
                    conatt=new ConnectionAttributes();
                    conatt.setConnectionId(apiconn.getDhisId());
                    conatt.setConnectionName(apiconn.getDhisName());
                    conatt.setConnectionType("dbc");
                    conatt.setConnectionUrl(apiconn.getDhisHomeUrl());
                    conatt.setRecordedBy(apiconn.getRecordedBy());
                    list.add(conatt);
                }
            }
            List dbConnectionList=DaoUtil.getDatabaseConnectionDaoInstance().getAllDatabaseConnections();
            if(dbConnectionList !=null)
            {
                DatabaseConnection dbconn=null;
                for(int i=0; i<dbConnectionList.size(); i++)
                {
                    
                    dbconn=(DatabaseConnection)dbConnectionList.get(i);
                    conatt=new ConnectionAttributes();
                    conatt.setConnectionId(dbconn.getId());
                    conatt.setConnectionName(dbconn.getConnectionName());
                    conatt.setConnectionType("api");
                    conatt.setConnectionUrl(dbconn.getDatabaseURL());
                    conatt.setRecordedBy(dbconn.getRecordedBy());
                    list.add(conatt);
                }
            }
            session.setAttribute("connectionsfortasks", list);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void getDeCatComboMapping(HttpSession session)
    {
        try
        {
            List deCatComboMappingList=DaoUtil.getBusinessRuleDaoInstance().getAllBusinessRules();
            if(deCatComboMappingList==null)
            deCatComboMappingList=new ArrayList();
            session.setAttribute("deCatComboMappingList", deCatComboMappingList);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void getOrgUnitMapping(HttpSession session)
    {
        try
        {
            List orgUnitMappingList=DaoUtil.getDxOrganizationUnitMatchDaoInstance().getAllDxOrganizationUnitMatch();
            if(orgUnitMappingList==null)
            orgUnitMappingList=new ArrayList();
            session.setAttribute("orgUnitMappingList", orgUnitMappingList);
            
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
