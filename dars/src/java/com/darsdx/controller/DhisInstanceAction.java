/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.util.AppUtility;
import com.darsdx.business.DhisInstance;
import com.darsdx.business.DhisOperation;
import com.darsdx.dao.DhisInstanceDao;
import com.darsdx.dao.DhisInstanceDaoImpl;
import com.darsdx.pojo.LamisResourceManager;
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
public class DhisInstanceAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE = "paramPage";
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
        AppUtility appUtil=new AppUtility();
        DhisInstanceForm dhisForm=(DhisInstanceForm)form;
        DhisInstanceDao dao=new DhisInstanceDaoImpl();
        HttpSession session=request.getSession();
        String requiredAction=dhisForm.getActionName();
        String reqParam=request.getParameter("p");
        String hostId=null;
        if(reqParam !=null)
        {
            hostId=request.getParameter("id");
            dhisForm.setDhisId(hostId);
            requiredAction=reqParam; 
        }
        
        getDhisInstanceList(session);
        
        enableDisableBtn(session,"false","true");
        if(requiredAction==null)
        {
            LamisResourceManager lrm=new LamisResourceManager();
            //lrm.moveLamisDataToDhis2(null,null,null,null);
            //LamisDataValueManager.getUrlResource(null, null, null);
            List list=new ArrayList();
            list.add("A");
            list.add("B");
            list.add("C");
            DhisOperation dop=new DhisOperation();
            //dop.createDhisOrganizationUnitExportFileInXml(list,"smomoh","orgunitExportTest");
            //String msg=dop.testDataUploadToDhis2("", "smomoh", "cHerry100");
            //request.setAttribute("ddumsg", msg);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            DhisInstance ds=getDhisInstance(dhisForm);
            if(ds !=null)
            {
                DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
                ds.setDhisId(null);
                dsdao.saveDhisInstance(ds);
            }
            getDhisInstanceList(session);
            //return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("ed"))
        {
            dhisForm=getDhisInstanceForm(dhisForm);
            enableDisableBtn(session,"true","false");
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            DhisInstance ds=getDhisInstance(dhisForm);
            if(ds !=null)
            {
                if(ds.getPassword()==null || ds.getPassword().trim().length()==0)
                ds.setPassword(dhisForm.getHiddenPassword());
                DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
                dsdao.updateDhisInstance(ds);
            }
            getDhisInstanceList(session);
            dhisForm.reset(mapping, request);
            //return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("de"))
        {
            DhisInstance ds=getDhisInstance(dhisForm);
            if(ds !=null)
            {
                DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
                dsdao.deleteDhisInstance(ds);
            }
            getDhisInstanceList(session);
            //return mapping.findForward(PARAMPAGE);
        }
        dhisForm.reset(mapping, request);
        return mapping.findForward(PARAMPAGE);
    }
    private void enableDisableBtn(HttpSession session,String saveValue,String modifyValue)
    {
        session.setAttribute("dsSaveDisabled", saveValue);
        session.setAttribute("dsModifyDisabled", modifyValue);
    }
    private DhisInstanceForm getDhisInstanceForm(DhisInstanceForm dhisForm)
    {
         
        try
        {
            if(dhisForm !=null)
            {
                DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
                DhisInstance ds=dsdao.getDhisInstanceById(dhisForm.getDhisId());
                if(ds !=null)
                {
                    dhisForm.setDhisId(ds.getDhisId());
                    dhisForm.setDhisName(ds.getDhisName());
                    dhisForm.setHiddenPassword(ds.getPassword());
                    dhisForm.setDhisHomeUrl(ds.getDhisHomeUrl());
                    dhisForm.setApiUrl(ds.getApiUrl());
                    dhisForm.setUserName(ds.getUserName());
                    dhisForm.setConnectionType(ds.getConnectionType());
                }
                //ds.setRecordedBy(dhisForm.get);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return dhisForm;
    }
    private DhisInstance getDhisInstance(DhisInstanceForm dhisForm)
    {
        DhisInstance ds=null;
        try
        {
            if(dhisForm !=null)
            {
                ds=new DhisInstance();
                ds.setDhisId(dhisForm.getDhisId());
                ds.setDhisName(dhisForm.getDhisName());
                ds.setDhisHomeUrl(dhisForm.getDhisHomeUrl());
                ds.setApiUrl(dhisForm.getApiUrl());
                if(dhisForm.getApiUrl()==null || dhisForm.getApiUrl().trim().length()<3)
                ds.setApiUrl("api");
                ds.setDateCreated(DateManager.getCurrentDateAsDateObject());
                ds.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
                ds.setPassword(dhisForm.getPassword());
                ds.setUserName(dhisForm.getUserName());
                if(ds.getDhisName()==null || ds.getDhisName().trim().length()==0)
                ds.setDhisName("xxxxxxxxxxx");
                ds.setConnectionType(dhisForm.getConnectionType());
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return ds;
    }
    private void getDhisInstanceList(HttpSession session)
    {
        try
        {
            DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
            List list=dsdao.getAllDhisInstances();
            if(list==null)
            list=new ArrayList();
            session.setAttribute("DhisInstanceList", list);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
