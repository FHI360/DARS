/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.Task;
import com.darsdx.business.TaskManager;
import com.darsdx.dao.DhisInstanceDao;
import com.darsdx.dao.DhisInstanceDaoImpl;
import com.darsdx.util.AppUtility;
import com.darsdx.util.DatabaseUtility;
import com.darsdx.util.DateManager;
import com.darsdx.util.ExcelReader;
import com.darsdx.util.ExcelWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.write.WritableWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author smomoh
 */
public class DataUploadAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE="dataUploadPage";
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
        DataUploadForm duf=(DataUploadForm)form;
        HttpSession session=request.getSession();
        AppUtility appUtils=new AppUtility();
        DatabaseUtility dbUtils=new DatabaseUtility();
        String requiredAction=duf.getActionName();
        String dhisInstance=duf.getDhisInstance();
        FormFile uploadedFile=duf.getUploadedFile();
        String typeOfDataForUpload=duf.getTypeOfDataForUpload();
        String requestParam=request.getParameter("q");
        
        InputStream inputStream=null;
        int serialNumber=0;
        if(uploadedFile !=null)
        {
            System.err.println("uploadedFile is not null "+uploadedFile.getFileName());
            inputStream=uploadedFile.getInputStream();
        }
        String targetPage=PARAMPAGE;
        String msg=" ";
        if(requestParam !=null)
        requiredAction=requestParam;
        getDhisInstanceList(session);
        /*List instanceList=util.getDhisInstanceDaoInstance().getAllDhisInstances();
        if(instanceList !=null)
        duf.setInstanceList(instanceList);*/
        System.err.println("requiredAction in data upload action is "+requiredAction);
        if(requiredAction==null || requiredAction.equalsIgnoreCase("select"))
        {
            duf.reset(mapping, request);
            return mapping.findForward(targetPage);
        }
        else if(requiredAction.equalsIgnoreCase("businessRuleUploadPage"))
        {
            duf.reset(mapping, request);
            targetPage="businessRuleUploadPage";
            return mapping.findForward(targetPage);
        }
        else if(requiredAction.equalsIgnoreCase("sourceDataUploadPage"))
        {
            duf.reset(mapping, request);
            targetPage="dataUploadPage";
            return mapping.findForward(targetPage);
        }
        else if(requiredAction.equalsIgnoreCase("flatTableUploadPage"))
        {
            duf.reset(mapping, request);
            targetPage="flatTableUploadPage";
            return mapping.findForward(targetPage);
        }
        else if(requiredAction.equalsIgnoreCase("dbrExcelUpload"))
        {
            String taskId="dbrupl";
            String activity="Business rule upload";
            int numberSaved=0;
            if(TaskManager.getCurrentTask().isActive())
            {

            }
            else
            {
                serialNumber++;
                /*Process and return to main page*/
                ExcelReader rex=new ExcelReader();
                Task task=TaskManager.setTaskStatus(taskId,activity,"active",numberSaved);
                try
                {


                        if(inputStream !=null)
                        {
                            dbUtils.createDhisBusinessRulesTable();
                            //numberSaved=rex.readDhisBusinessRuleFromExcelTable(inputStream, "USG");
                        }

                }
                catch(Exception ex)
                {
                    task=TaskManager.setTaskStatus(taskId,activity,"error",numberSaved);
                }
                    task=TaskManager.setTaskStatus(taskId,activity,"complete",numberSaved);
                    TaskManager.setCurrentTask(task);
                }
                request.setAttribute("dbrmsg", msg);
                duf.reset(mapping, request);
                targetPage="businessRuleUploadPage";
            return mapping.findForward(targetPage);
        }
        else if(requiredAction.equalsIgnoreCase("sourceDataExcelUpload"))
        {
            String taskId="sdupl";
            int numberSaved=0;
            System.err.println("data upload action is sourceDataExcelUpload block");
            String activity="Source data upload";
            if(TaskManager.getCurrentTask().isActive())
            {

            }
            else
            {
                /*Process and return to main page*/
                //ExcelReader rex=new ReadExcel();
                Task task=TaskManager.setTaskStatus(taskId,activity,"active",numberSaved);
                /*Process and return to main page*/
                try
                {
                    ExcelReader exr=new ExcelReader();
                    dbUtils.createSourceDataTable();
                    exr.readSourceDataFromExcel(inputStream, dhisInstance);
                    //numberSaved=rex.read(inputStream);
                    targetPage=PARAMPAGE;   
                }
                catch(Exception ex)
                {
                    msg="Error loading data "+ex.getMessage();
                    task=TaskManager.setTaskStatus(taskId,activity,"error",numberSaved);
                }
                    task=TaskManager.setTaskStatus(taskId,activity,"complete",numberSaved);
                    TaskManager.setCurrentTask(task);
            }
            request.setAttribute("msg", msg);
            duf.reset(mapping, request);
            return mapping.findForward(targetPage);
        }
        else if(requiredAction.equalsIgnoreCase("flatTableExcelUpload"))
        {
            String taskId="fltupl";
            int numberSaved=0;
            String activity="Flat table upload";
            if(TaskManager.getCurrentTask().isActive())
            {

            }
            else
            {
                Task task=TaskManager.setTaskStatus(taskId,activity,"active",numberSaved);
                //Process and return to main page
                ExcelReader exr=new ExcelReader();
                try
                {
                    if(inputStream !=null)
                    {
                        dbUtils.createFinalFlatTable();
                        exr.readFinalFlatTableFromExcel(inputStream,dhisInstance);
                    }
                }
                catch(Exception ex)
                {
                    msg="Error loading flat table "+ex.getMessage();
                    task=TaskManager.setTaskStatus(taskId,activity,"error",numberSaved);
                }
                task=TaskManager.setTaskStatus(taskId,activity,"complete",numberSaved);
                TaskManager.setCurrentTask(task);
            }
            request.setAttribute("msg", msg);
            targetPage=PARAMPAGE;
            duf.reset(mapping, request);
            return mapping.findForward(targetPage);
        }
        else if(requiredAction.equalsIgnoreCase("IMISSourceExcelUpload"))
        {
            String fileName="FlatTableIdMismatch"+DateManager.getCurrentDateAndTime(DateManager.DB_DATE_FORMAT)+appUtils.generateUniqueId();
            OutputStream os=new FileOutputStream("C:/Darsdx/Transfer/"+fileName+".xls");//response.getOutputStream();
            String taskId="imisupl";
            int numberSaved=0;
            String activity="IMIS source upload";
            System.err.println("Inside IMISSourceExcelUpload block");
            if(TaskManager.getCurrentTask().isActive())
            {
                System.err.println("Task manager is active");
            }
            else
            {
                Task task=TaskManager.setTaskStatus(taskId,activity,"active",numberSaved);
                
                //response.setContentType("application/vnd.ms-excel");
                //response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
                //Process and return to main page
                ExcelReader exr=new ExcelReader();
                try
                {
                    System.err.println("Before inputStream block");
                    
                    if(inputStream==null && uploadedFile !=null)
                    {
                        System.err.println("inputStream is null ");
                        inputStream=uploadedFile.getInputStream();
                    }
                    if(inputStream !=null)
                    {
                        System.err.println("inputStream is not null");
                        ExcelWriter er=new ExcelWriter();
                        List list=exr.readAfyaNyotaDataToFinalFlatTable(inputStream,dhisInstance);
                        WritableWorkbook wworkbook=er.writeFinalFlatTableMatchingResultToExcel(os, list);
                        if(wworkbook !=null)
                        {
                            wworkbook.write();
                            wworkbook.close();
                        }
                        os.close();
                    }
                }
                catch(Exception ex)
                {
                    msg="Error loading flat table "+ex.getMessage();
                    task=TaskManager.setTaskStatus(taskId,activity,"error",numberSaved);
                }
                task=TaskManager.setTaskStatus(taskId,activity,"complete",numberSaved);
                TaskManager.setCurrentTask(task);
            }
            request.setAttribute("msg", msg);
            targetPage=PARAMPAGE;
            duf.reset(mapping, request);
            return mapping.findForward(targetPage);
        }
        duf.reset(mapping, request);
        return mapping.findForward(targetPage);
    }
    
    private void getDhisInstanceList(HttpSession session)
    {
        try
        {
            DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
            List list=dsdao.getAllDhisInstances();
            if(list==null)
            list=new ArrayList();
            session.setAttribute("DhisInstanceListForDataUpload", list);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
