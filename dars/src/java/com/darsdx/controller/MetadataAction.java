/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DhisInstance;
import com.darsdx.dao.DaoUtility;
import com.darsdx.dao.DhisInstanceDao;
import com.darsdx.dao.DhisInstanceDaoImpl;
import com.darsdx.pojo.CategoryOptionComboProcessor;
import com.darsdx.pojo.DHISPropertyManager;
import com.darsdx.pojo.DataElementProcessor;
import com.darsdx.pojo.DatasetProcessor;
import com.darsdx.pojo.OrganizationUnitProcessor;
import com.darsdx.pojo.UrlResourceManager;
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
public class MetadataAction extends org.apache.struts.action.Action {

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
        MetadataForm mdform=(MetadataForm)form;
        HttpSession session=request.getSession();
        //AppUtility appUtil=new AppUtility();
        String requiredAction=mdform.getActionName();
        String dhisId=mdform.getDhisId();
        String selectedTask=mdform.getSelectedTask();
        
        DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
        System.err.println("requiredAction is "+requiredAction);
        /*List dsList=dsdao.getAllDhisInstances();
        if(dsList !=null)
        {
            System.err.println("dsList.size() is "+dsList.size());
            session.setAttribute("DhisInstanceList", dsList);
        }*/
        getDhisInstanceList(session);
        //String 
        //String directoryPath="C:/WHO";
        if(requiredAction==null)
        {
            mdform.reset(mapping, request);
            //preapareChart(request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("apiurl"))
        {
            DhisInstance ds=dsdao.getDhisInstanceById(dhisId);
            mdform.setApiUrl("");
            if(ds !=null)
            {
                String seperator=DHISPropertyManager.getSeperator();
                String xmlExt=DHISPropertyManager.getXmlExt();
                String paging=DHISPropertyManager.getPaging();
                String links=DHISPropertyManager.getLinks();
                String firstQueryParamSeperator=DHISPropertyManager.getFirstQueryParamSeperator();
                String additionalQueryParamSeperator=DHISPropertyManager.getAdditionalQueryParamSeperator();
                String queryParameters=firstQueryParamSeperator+paging+additionalQueryParamSeperator+links;
                String resourceUrl="";
                if(selectedTask !=null && !selectedTask.equalsIgnoreCase("select"))
                {
                    if(selectedTask.equalsIgnoreCase("cc"))
                    resourceUrl=DHISPropertyManager.getCategoryCominationtUrl()+xmlExt;
                    else if(selectedTask.equalsIgnoreCase("de"))
                    resourceUrl=DHISPropertyManager.getDataElementUrl()+xmlExt;
                    else if(selectedTask.equalsIgnoreCase("ou"))
                    {
                        resourceUrl=DHISPropertyManager.getOrganizationUnitUrl()+xmlExt;
                        queryParameters=firstQueryParamSeperator+DHISPropertyManager.getOrganizationUnitFieldsUrl()+additionalQueryParamSeperator+paging+additionalQueryParamSeperator+links;
                    }
                    else if(selectedTask.equalsIgnoreCase("ougroup"))
                    resourceUrl=DHISPropertyManager.getOrganizationUnitGroupUrl()+xmlExt;
                    else if(selectedTask.equalsIgnoreCase("ouattribute"))
                    resourceUrl=DHISPropertyManager.getOrganizationUnitUrl()+"/orgUnitId";//+firstQueryParamSeperator+paging+additionalQueryParamSeperator+links;
                    else if(selectedTask.equalsIgnoreCase("dst"))
                    resourceUrl=DHISPropertyManager.getDatasetUrl()+xmlExt;//+firstQueryParamSeperator+paging+additionalQueryParamSeperator+links;
                    mdform.setApiUrl(ds.getDhisHomeUrl()+seperator+ds.getApiUrl()+resourceUrl+queryParameters);
                }
                
            }
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("pulldata"))
        {
            //System.err.println("selectedTask is "+selectedTask);
            DhisInstance ds=dsdao.getDhisInstanceById(dhisId);
            String apiUrl=mdform.getApiUrl();
            if(ds !=null)
            {
                //System.err.println("selectedTask 2 is "+selectedTask);
                if(selectedTask.equalsIgnoreCase("ouattribute"))
                {
                    String resourceUrl=null;
                    List ouIdlist=DaoUtility.getOrganizationUnitDaoInstance().getDistinctOrganizationUnitIdsByOuLevel(dhisId, 0);
                    String orgUnitId=null;
                    List list=null;
                    int count=0;
                        while(ouIdlist !=null && !ouIdlist.isEmpty())
                        {
                            orgUnitId=(String)ouIdlist.get(0);
                            resourceUrl=apiUrl.replace("orgUnitId", orgUnitId+".xml");
                            list=UrlResourceManager.getUrlResource(resourceUrl, ds.getUserName(), ds.getPassword());
                            OrganizationUnitProcessor.extractOrganizationUnitAttributesFromXml(list, dhisId);
                            ouIdlist=DaoUtility.getOrganizationUnitDaoInstance().getDistinctOrganizationUnitIdsByOuLevel(dhisId, 0);
                            count++;
                            System.err.println("count is "+count+" and ouIdlist size is "+ouIdlist.size());
                        }
                        //System.err.println("resourceUrl is "+resourceUrl);
                    //}
                }
                else
                {
                    System.err.println("selectedTask 3 is "+selectedTask);
                    List list=UrlResourceManager.getUrlResource(apiUrl, ds.getUserName(), ds.getPassword());
                    if(list !=null)
                    {

                        if(selectedTask.equalsIgnoreCase("cc"))
                        {
                            CategoryOptionComboProcessor.extractCategoryOptionCombinationFromXml(list, dhisId);
                        }
                        else if(selectedTask.equalsIgnoreCase("de"))
                        DataElementProcessor.extractDataElementFromXml(list, dhisId);
                        else if(selectedTask.equalsIgnoreCase("ou"))
                        OrganizationUnitProcessor.extractOrganizationUnitFromXml(list, dhisId);
                        else if(selectedTask.equalsIgnoreCase("ougroup"))
                        OrganizationUnitProcessor.extractOrganizationUnitGroupFromXml(list, dhisId);
                        else if(selectedTask.equalsIgnoreCase("dst"))
                        DatasetProcessor.extractDataSetFromXml(list, dhisId);
                    }
                }
            }
        }
        else if(requiredAction.equalsIgnoreCase("loadou"))
        {
            
        }
        else if(requiredAction.equalsIgnoreCase("loaddv"))
        {
            
        }
        return mapping.findForward(SUCCESS);
    }
    private void getDhisInstanceList(HttpSession session)
    {
        try
        {
            DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
            List list=dsdao.getDhisInstanceByConnectionType("dhis");//getAllDhisInstances();
            if(list==null)
            list=new ArrayList();
            session.setAttribute("DhisInstanceList", list);
            System.err.println("DhisInstanceList size is "+list.size());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void getApiConnections(HttpSession session)
    {
        try
        {
            DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
            List list=dsdao.getAllDhisInstances();
            if(list==null)
            list=new ArrayList();
            session.setAttribute("apiConnectionList", list);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
