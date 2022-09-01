/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DhisInstance;
import com.darsdx.business.DhisOperation;
import com.darsdx.business.OrganizationUnit;
import com.darsdx.dao.DaoUtility;
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
public class OrganizationUnitExportAction extends org.apache.struts.action.Action {

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
        OrganizationUnitExportForm ouef=(OrganizationUnitExportForm)form;
        HttpSession session=request.getSession();
        String requiredAction=ouef.getActionName();
        String connectionId=ouef.getConnectionId();
        String level1OuId=ouef.getOuLevel1Id();
        String level2OuId=ouef.getOuLevel2Id();
        String level3OuId=ouef.getOuLevel3Id();
        String level4OuId=ouef.getOuLevel4Id();
        String level5OuId=ouef.getOuLevel5Id();
        String level6OuId=ouef.getOuLevel6Id();
        List list=DaoUtility.getDhisInstanceDaoInstance().getAllDhisInstances();
        if(list==null)
        list=new ArrayList();
        session.setAttribute("dhisForOuExportList", list);
        List emptyList=new ArrayList();
        if(requiredAction==null)
        {
           ouef.reset(mapping, request);
           return mapping.findForward(SUCCESS); 
        }
        else if(requiredAction.equalsIgnoreCase("level1OuList"))
        {
           List level1OuList=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByOuLevel(connectionId, 1); 
           session.setAttribute("level1OuListForExport", level1OuList);
           session.setAttribute("level2OuListForExport", emptyList);
           session.setAttribute("level3OuListForExport", emptyList);
           session.setAttribute("level4OuListForExport", emptyList);
           session.setAttribute("level5OuListForExport", emptyList);
           session.setAttribute("level6OuListForExport", emptyList);
           session.setAttribute("ouListForExport", level1OuList);
           return mapping.findForward(SUCCESS); 
        }
        else if(requiredAction.equalsIgnoreCase("level2OuList"))
        {
           List level2OuList=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByParentId(connectionId,level1OuId); 
           session.setAttribute("level2OuListForExport", level2OuList);
           session.setAttribute("ouListForExport", level2OuList);
           
           session.setAttribute("level3OuListForExport", emptyList);
           session.setAttribute("level4OuListForExport", emptyList);
           session.setAttribute("level5OuListForExport", emptyList);
           session.setAttribute("level6OuListForExport", emptyList);
           
        }
        else if(requiredAction.equalsIgnoreCase("level3OuList"))
        {
           
               List level3OuList=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByParentId(connectionId,level2OuId); 
               session.setAttribute("level3OuListForExport", level3OuList);
               session.setAttribute("ouListForExport", level3OuList);
           
           session.setAttribute("level4OuListForExport", emptyList);
           session.setAttribute("level5OuListForExport", emptyList);
           session.setAttribute("level6OuListForExport", emptyList);
           
        }
        else if(requiredAction.equalsIgnoreCase("level4OuList"))
        {
         List level4OuList=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByParentId(connectionId,level3OuId); 
           session.setAttribute("level4OuListForExport", level4OuList);  
           session.setAttribute("level5OuListForExport", emptyList);
           session.setAttribute("level6OuListForExport", emptyList);
           session.setAttribute("ouListForExport", level4OuList);
        }
        else if(requiredAction.equalsIgnoreCase("level5OuList"))
        {
           List level5OuList=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByParentId(connectionId,level4OuId); 
           session.setAttribute("level5OuListForExport", level5OuList);
           session.setAttribute("level6OuListForExport", emptyList);
           session.setAttribute("ouListForExport", level5OuList);
        }
        else if(requiredAction.equalsIgnoreCase("level6OuList"))
        {
           List level6OuList=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByParentId(connectionId,level5OuId); 
           session.setAttribute("level6OuListForExport", level6OuList);
           session.setAttribute("ouListForExport", level6OuList);
        }
        else if(requiredAction.equalsIgnoreCase("createExport"))
        {
           DhisOperation dop=new DhisOperation();
           String[] selectOu=ouef.getSelectedOuForExport();
           String exportOption=ouef.getExportOption();
           if(exportOption==null)
           exportOption="all";
           List ouList=new ArrayList();
           List ouByParentList=new ArrayList();
           List ouParentList=null;
           if(selectOu !=null)
           {               
               for(int i=0; i<selectOu.length; i++)
               {
                   System.err.println("Ou Id is "+selectOu[i]);
                   ouParentList=DaoUtility.getOrganizationUnitDaoInstance().getParentOrganizationUnitsOnly(connectionId,selectOu[i]);
                   if(ouParentList !=null)
                   ouByParentList.addAll(ouParentList);
                   if(exportOption.equalsIgnoreCase("selectedOrgUnitsOnly"))
                   {
                       OrganizationUnit ou=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnit(connectionId,selectOu[i]);
                       if(ou !=null)
                       {
                          ouByParentList.add(ou); 
                       }
                   }
                   else if(exportOption.equalsIgnoreCase("immediateChildrenOnly"))
                   {
                       OrganizationUnit ou=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnit(connectionId,selectOu[i]);
                       if(ou !=null)
                       {
                            List immediateChildrenList=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByParentId(connectionId,ou.getParentId());
                            if(immediateChildrenList !=null)
                            ouByParentList.addAll(immediateChildrenList);
                       }
                   }
                   else //if(exportOption.equalsIgnoreCase("allDescebndants"))
                   {
                       List descendantList=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByOuPath(connectionId, selectOu[i]);
                       if(descendantList !=null)
                       ouByParentList.addAll(descendantList);
                   }
                   /*else
                   {
                       List descendantList=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByOuPath(connectionId, selectOu[i]);
                       if(descendantList !=null)
                       ouByParentList.addAll(descendantList);
                   }*/      
               }
               if(ouByParentList !=null)
               ouList.addAll(ouByParentList);
           }
           if(!ouList.isEmpty())
           {
               String exportFileName="DhisOrgUnitExport";
               DhisInstance dhisInstance=DaoUtility.getDhisInstanceDaoInstance().getDhisInstanceById(connectionId);
               if(dhisInstance !=null)
               exportFileName=dhisInstance.getDhisName()+"_OrgUnitExport";
               dop.createDhisOrganizationUnitExportFileInXml(ouList,"smomoh",exportFileName);
               System.err.println("ouList size is "+ouList.size());
               System.err.println("exportFileName is "+exportFileName);
           }
        }
        return mapping.findForward(SUCCESS);
    }
}
