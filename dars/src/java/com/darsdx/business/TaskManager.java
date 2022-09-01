/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

import com.darsdx.dao.SourceDataDao;
import com.darsdx.dao.SourceDataDaoImpl;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class TaskManager implements Serializable
{
    private static Task currentTask=new Task();
    private static String startTime;
    private static int taskValue=0;
    private List taskList=new ArrayList();
    private List taskNameList=new ArrayList();
    private static boolean dataUploadAvailable=true;
    private static boolean databaseLocked=false;
    private String statusMessage="System busy. Kindly try again later";

    public static Task getCurrentTask() 
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        
        setTaskValue(currentTask);
        currentTask.setTaskValue(getTaskValue());
        if(!currentTask.isComplete())
        currentTask.setEndTime(dateFormat.format(date));
        return currentTask; 
    }

    public static void setCurrentTask(Task currentTask) 
    {
        TaskManager.currentTask = currentTask;
    }
    
    public static Task setTaskStatus(String taskId,String taskName,String taskStatus,int taskValue)
    {
       DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
       Date date = new Date();
       Task task=new Task();
       if(taskValue==0)
       startTime=dateFormat.format(date);
       task.setStartTime(startTime);
       
       task.setTaskId(taskId);
       task.setTaskName(taskName);
       task.setTaskStatus(taskStatus);
       task.setTaskValue(taskValue);
       
       if(task.isComplete())
       task.setEndTime(dateFormat.format(date));
       //if(task.isActive())
       
       //else
       
       setCurrentTask(task);
       return task;
    }

    public List getTaskNameList() {
        return taskNameList;
    }

    public void setTaskNameList(List taskNameList) {
        this.taskNameList = taskNameList;
    }

    public static int getTaskValue() {
        return taskValue;
    }

    public static void setTaskValue(Task task) 
    {
        try
        {
           SourceDataDao sddao=new SourceDataDaoImpl();
           /*IntermediateDhisFlatTableDao idftdao=new IntermediateDhisFlatTableDaoImpl();
           DhisBusinessRulesDao dbrdao=new DhisBusinessRulesDaoImpl();
           DhisDataExportDao ddedao=new DhisDataExportDaoImpl();*/
           if(task !=null)
           {
               if(task.getTaskId() !=null && task.getTaskId().equalsIgnoreCase("sdupl"))
                taskValue = sddao.getNumberOfSourceDataSaved();
               /*else if(task.getTaskId() !=null && task.getTaskId().equalsIgnoreCase("dbrupl"))
                taskValue = dbrdao.getNumberOfRecordsInDhisBusinessRules();
               else if(task.getTaskId() !=null && task.getTaskId().equalsIgnoreCase("dbrapp"))
                taskValue = idftdao.getNumberOfRecordsInDhisFlatTable();
               else if(task.getTaskId() !=null && (task.getTaskId().equalsIgnoreCase("fltupl") || task.getTaskId().equalsIgnoreCase("ddeunq")))
                taskValue = ddedao.getNumberOfRecordsInDhisDataExport();*/
           }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    public static boolean isDataUploadAvailable() {
        return dataUploadAvailable;
    }

    public static void setDataUploadAvailable(boolean dataUploadAvailable) {
        TaskManager.dataUploadAvailable = dataUploadAvailable;
    }

    public static boolean isDatabaseLocked() {
        return databaseLocked;
    }

    public static void setDatabaseLocked(boolean databaseLocked) {
        TaskManager.databaseLocked = databaseLocked;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List getTaskList() {
        return taskList;
    }

    public void setTaskList(List taskList) {
        this.taskList = taskList;
    }
}

