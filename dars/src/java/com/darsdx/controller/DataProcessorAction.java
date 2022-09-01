/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DataTransformationManager;
import com.darsdx.business.DhisInstance;
import com.darsdx.business.DhisOperation;
import com.darsdx.business.MatchDescriptor;
import com.darsdx.business.Task;
import com.darsdx.business.TaskManager;
import com.darsdx.dao.DaoUtil;
import com.darsdx.dao.DataValueDao;
import com.darsdx.dao.DataValueDaoImpl;
import com.darsdx.operationsmanagement.MetadataManager;
import com.darsdx.test.URLTest;
import com.darsdx.util.DatabaseUtility;
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
public class DataProcessorAction extends org.apache.struts.action.Action {

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
            throws Exception {
        DataProcessorForm dpf=(DataProcessorForm)form;
        DaoUtil util=new DaoUtil();
        DatabaseUtility dbUtils=new DatabaseUtility();
        String requiredAction=dpf.getActionName();
        String producerInstance=null;
        String consumerInstance=null;
        String brdesc=dpf.getBrdesc();
        
        if(brdesc !=null)
        {
            String[] dhisInstanceArray=brdesc.split("-");
            producerInstance=dhisInstanceArray[0];
            consumerInstance=dhisInstanceArray[1];
        }
        String businessRuleIndicator=dpf.getBusinessRuleIndicator();
        String uniqueRecordIndicator=dpf.getUniqueRecordIndicator();
        String metadataIdAssignment=dpf.getMetadataIdAssignment();
        String dhis2ExportIndicator=dpf.getDhis2ExportIndicator();
        String dhisDataUpload=dpf.getDhisDataUpload();
        String deleteAllRecords=dpf.getDeleteRecord();
        
        
        List ruleDescriptionList=new ArrayList();
        List rulesInstances=util.getBusinessRuleDaoInstance().getDistinctBusinessRuleInstances();
        if(rulesInstances !=null && !rulesInstances.isEmpty())
        {
            DhisInstance prodInstance=null;
            DhisInstance consInstance=null;
            for(Object obj:rulesInstances)
            {
                Object[] objArray=(Object[])obj;
                MatchDescriptor md=new MatchDescriptor();
                prodInstance=util.getDhisInstanceDaoInstance().getDhisInstanceById(objArray[0].toString());
                consInstance=util.getDhisInstanceDaoInstance().getDhisInstanceById(objArray[1].toString());
                md.setProducerInstance(prodInstance);
                md.setConsumerInstance(consInstance);
                ruleDescriptionList.add(md);
            }
            
        }
        request.setAttribute("ruleDescriptionList", ruleDescriptionList);
        //System.err.println("dhis2ExportIndicator is "+dhis2ExportIndicator);
        String target="dataProcessorHomePage";
        List dhisInstanceList=new ArrayList();
        List instanceList=util.getSourceDataDaoInstance().getDistinctDhisInstances();
        if(instanceList !=null)
        {
            DhisInstance dinst=null;
            for(Object s: instanceList)
            {
                String instanceId=(String)s;
                dinst=util.getDhisInstanceDaoInstance().getDhisInstanceById(instanceId);
                if(dinst !=null)
                dhisInstanceList.add(dinst);
            }
        }
        dpf.setInstanceList(dhisInstanceList);
        System.err.println("metadataIdAssignment is "+metadataIdAssignment);
        if(requiredAction==null)
        {
            //CurlProcessor.executeCurlCommand();
            //Curl.executeCommand();
            //AccessPasswordProtectedURLWithAuthenticator.connectToUrl();
            //HttpsURLConnectionExample.connectToHttpsUrl();
            //URLReader.readData();
            dpf.reset(mapping, request);
            return mapping.findForward(target);
        }
        else if(requiredAction.equalsIgnoreCase("process"))
        {
            System.err.println("Inside process");
            if(businessRuleIndicator !=null && businessRuleIndicator.equalsIgnoreCase("applyBusinessRules"))
            {
                int numberSaved=0;
                
                String activity="Applying business rules to data";
                String taskId="dbrapp";
                String msg=" ";
                System.err.println("About to apply business rules");
                if(TaskManager.getCurrentTask().isActive())
                {
                    System.err.println("TaskManager.getCurrentTask() is active");
                }
                else
                {
                    
                    Task task=TaskManager.setTaskStatus(taskId,activity,"active",numberSaved);
                    try
                    {
                        DataTransformationManager dtm=new DataTransformationManager();
                        if(deleteAllRecords !=null && deleteAllRecords.equalsIgnoreCase("deleteAll"))
                        dbUtils.createIntermediateFlatTable();
                        numberSaved=dtm.applyBusinessRule(producerInstance,consumerInstance);
                        System.err.println(numberSaved+" Business rules applied ");
                    }
                    catch(Exception ex)
                    {
                        msg="Error while applying business rules "+ex.getMessage();
                        System.err.println(msg);
                        task=TaskManager.setTaskStatus(taskId,activity,"error",numberSaved);
                    }
                    task=TaskManager.setTaskStatus(taskId,activity,"complete",numberSaved);
                    TaskManager.setCurrentTask(task);
                    request.setAttribute("msg", msg);
                }
            }
            if(uniqueRecordIndicator !=null && uniqueRecordIndicator.equalsIgnoreCase("createUniqueRecords"))
            {
                int numberSaved=0;
                String taskId="ddeunq";
                String activity="Generating unique records";
                String msg=" ";
                if(TaskManager.getCurrentTask().isActive())
                {

                }
                else
                {
                    Task task=TaskManager.setTaskStatus(taskId,activity,"active",numberSaved);
                    try
                    {
                        DataTransformationManager dtm=new DataTransformationManager();
                        if(deleteAllRecords !=null && deleteAllRecords.equalsIgnoreCase("deleteAll"))
                        dbUtils.createFinalFlatTable();
                        numberSaved=dtm.createUniqueRecords(producerInstance, consumerInstance);
                    }
                    catch(Exception ex)
                    {
                        msg="Error: unable to create unique records "+ex.getMessage();
                        task=TaskManager.setTaskStatus(taskId,activity,"error",numberSaved);
                    }
                    task=TaskManager.setTaskStatus(taskId,activity,"complete",numberSaved);
                    TaskManager.setCurrentTask(task);
                    request.setAttribute("msg", msg);
                }
            }
            if(metadataIdAssignment !=null && metadataIdAssignment.equalsIgnoreCase("metadataIdAssignment"))
            {
                System.err.println("about to assign Ids");
                int numberSaved=0;
                String taskId="mdmids";
                String activity="Applying metadata Ids";
                String msg=" ";
                if(TaskManager.getCurrentTask().isActive())
                {

                }
                else
                {
                    Task task=TaskManager.setTaskStatus(taskId,activity,"active",numberSaved);
                    try
                    {
                        MetadataManager mdm=new MetadataManager();
                        msg=mdm.assignMetadataId(consumerInstance);
                        System.err.println("Msg in assign metadata id is "+msg);
                    }
                    catch(Exception ex)
                    {
                        msg="Error: unable to assign ids "+ex.getMessage();
                        task=TaskManager.setTaskStatus(taskId,activity,"error",numberSaved);
                    }
                    task=TaskManager.setTaskStatus(taskId,activity,"complete",numberSaved);
                    TaskManager.setCurrentTask(task);
                    request.setAttribute("msg", msg);
                }
            }
            if(dhis2ExportIndicator !=null && dhis2ExportIndicator.equalsIgnoreCase("createDhisExportFiles"))
            {
                DhisOperation dop=new DhisOperation();
                System.err.println("inside dhis2ExportIndicator block "+dhis2ExportIndicator);
                int numberSaved=0;
                String taskId="exptfile";
                String activity="Generating dhis2 export files";
                String msg=" ";
                if(TaskManager.getCurrentTask().isActive())
                {

                }
                else
                {
                    System.err.println("Task manager is not active ");
                    Task task=TaskManager.setTaskStatus(taskId,activity,"active",numberSaved);
                    try
                    {
                        String userName="user";
                        DhisInstance dinst=util.getDhisInstanceDaoInstance().getDhisInstanceById(consumerInstance);
                        if(dinst !=null)
                        {
                            userName=dinst.getUserName();
                        }
                        //DataValueDao dvdao=new DataValueDaoImpl();
                        //DataTransformationManager dtm=new DataTransformationManager();
                        //List dataValueList=dvdao.getAllDataValues();
                        //dop.createDhisExportFileInXmlFromDataValueList(dataValueList, "smomoh", "dhisdatavaluesexport");
                        dop.createDhisExport(producerInstance,userName);
                        
                    }
                    catch(Exception ex)
                    {
                        msg="Error: unable to create unique records "+ex.getMessage();
                        task=TaskManager.setTaskStatus(taskId,activity,"error",numberSaved);
                    }
                    task=TaskManager.setTaskStatus(taskId,activity,"complete",numberSaved);
                    TaskManager.setCurrentTask(task);
                    request.setAttribute("msg", msg);
                }
            }
        }
        if(dhisDataUpload !=null && dhisDataUpload.equalsIgnoreCase("uploadToDhis"))
         {
                //System.err.println("inside dhis2ExportIndicator block "+dhis2ExportIndicator);
                String dhisOutput="";
                int numberSaved=0;
                String taskId="exptfile";
                String activity="Generating dhis2 export files";
                String msg=" ";
                if(TaskManager.getCurrentTask().isActive())
                {

                }
                else
                {
                    System.err.println("Task manager is not active ");
                    Task task=TaskManager.setTaskStatus(taskId,activity,"active",numberSaved);
                    try
                    {
                        DhisOperation dop=new DhisOperation();
                        msg=dop.uploadDataToDhis2("https://play.dhis2.org/demo", "admin", "district");
                        //ProcessBuilderExample.executeNativeCommand();
                        //FtpUrlUpload.executeFtpUrlExample();
                        dhisOutput=URLTest.testUrl();
                        System.err.println(dhisOutput);
                    }
                    catch(Exception ex)
                    {
                        msg="Error: unable to upload records to dhis "+ex.getMessage();
                        task=TaskManager.setTaskStatus(taskId,activity,"error",numberSaved);
                    }
                    task=TaskManager.setTaskStatus(taskId,activity,"complete",numberSaved);
                    TaskManager.setCurrentTask(task);
                    request.setAttribute("msg", msg);
                    //request.setAttribute("dhisMsg", dhisOutput);
                    //return mapping.findForward("dhisOutputPage");
                    
                }
            }
        dpf.reset(mapping, request);
        return mapping.findForward(target);
    }
}
