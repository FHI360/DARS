/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DataTransferManager;
import com.darsdx.business.DhisInstance;
import com.darsdx.business.DhisOperation;
import com.darsdx.dao.DaoUtil;
import com.darsdx.dao.DaoUtility;
import com.darsdx.pojo.LamisResourceManager;
import com.darsdx.util.DateManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class TaskExecutionAction extends org.apache.struts.action.Action {

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
        TaskExecutionForm tef=(TaskExecutionForm)form;
        DaoUtil util=new DaoUtil();
        String requiredAction =tef.getActionName();
        String reqParam=request.getParameter("p");
        boolean saveZeroValues=false;
        int zeroValuesAllowed=tef.getAllowZeroValue();
        if(zeroValuesAllowed==1)
        saveZeroValues=true;
        String hiddenId=null;
        if(reqParam !=null)
        {
            hiddenId=request.getParameter("id");
            System.err.println("hiddenId is "+hiddenId);
            requiredAction=reqParam; 
        }
        System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
            DataTransferManager dtm=DaoUtil.getDataTransferManagerDaoInstance().getDataTransferManager(hiddenId);
            tef.reset(mapping, request);
            
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("exec"))
        {
            DhisOperation dop=new DhisOperation();
            List DataValueList=DaoUtility.getDatavalueDaoInstance().getDataValuesByDhisId("lamisId");
            dop.createDhisExportFileInXmlFromDataValueList(DataValueList, "smomoh", "LamisDhisExport");
            DataTransferManager dtm=DaoUtil.getDataTransferManagerDaoInstance().getDataTransferManager(hiddenId);
            setPeriodList(dtm,request);
            tef.setHiddenTaskId(hiddenId);
            request.setAttribute("dtm", dtm);
            
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("execute"))
        {
            String[] periods=tef.getPeriods();
            hiddenId=tef.getHiddenTaskId();
            if(periods !=null)
            {
                DataTransferManager dtm=DaoUtil.getDataTransferManagerDaoInstance().getDataTransferManager(hiddenId);
                if(dtm !=null)
                {
                    String sinkId=dtm.getDataSink();
                    DhisInstance dinst=DaoUtil.getDhisInstanceDaoInstance().getDhisInstanceById(sinkId);
                    if(dinst !=null)
                    {
                        LamisResourceManager lrm=new LamisResourceManager();
                        System.err.println("dinst.getDhisHomeUrl() is "+dinst.getDhisHomeUrl());
                        String period=null;
                        
                        for(int i=0; i<periods.length; i++)
                        {
                            period=periods[i];
                            lrm.getDataFromLamis(period);
                            lrm.moveLamisDataToDhis2(dinst.getDhisHomeUrl(),dinst.getUserName(),dinst.getPassword(),period,saveZeroValues);
                        }
                        //lrm.moveLamisDataToDhis2(dinst.getDhisHomeUrl(),dinst.getUserName(),dinst.getPassword(),period);
                    }
                }
            }
            
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }
    private void setPeriodList(DataTransferManager dtm,HttpServletRequest request)
    {
        System.err.println("Inside setPeriodList");
        if(dtm !=null)
        {
            System.err.println("dtm is not null");
            if(dtm.getFrequency() !=null && !dtm.getFrequency().equalsIgnoreCase("manual"))
            {
                System.err.println("dtm.getFrequency() is not null");
                String strStartDate=DateManager.convertDateToString(dtm.getStartDate(),DateManager.DB_DATE_FORMAT);
                String strEndDate=DateManager.convertDateToString(dtm.getEndDate(),DateManager.DB_DATE_FORMAT);
                DateManager dm=new DateManager();
                if(strStartDate !=null && strEndDate !=null)
                {
                    System.err.println("strStartDate is not null");
                    List list=new ArrayList();
                    String[] strStartDateArray=strStartDate.split("-");
                    String[] strEndDateArray=strEndDate.split("-");
                    int startYear=Integer.parseInt(strStartDateArray[0]);
                    int endYear=Integer.parseInt(strEndDateArray[0]);
                    if(dtm.getFrequency().equalsIgnoreCase("daily"))
                    {
                        List dateList=dm.generateDailyDates(startYear, endYear);
                        if(dateList !=null)
                        list.addAll(dateList);
                    }
                    else if(dtm.getFrequency().equalsIgnoreCase("weekly"))
                    {
                        List dateList=dm.generateWeeklyDates(startYear, endYear);
                        if(dateList !=null)
                        list.addAll(dateList);
                    }
                    else if(dtm.getFrequency().equalsIgnoreCase("monthly"))
                    {
                        List dateList=dm.generateMonthlyDates(startYear, endYear);
                        if(dateList !=null)
                        list.addAll(dateList);
                    }
                    request.setAttribute("periodList", list);
                }
            }
        }           
    }
}
