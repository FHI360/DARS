/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DxCategoryCombination;
import com.darsdx.dao.DaoUtil;
import com.darsdx.dao.DhisInstanceDao;
import com.darsdx.dao.DhisInstanceDaoImpl;
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
public class CategoryOptionComboEditAction extends org.apache.struts.action.Action {

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
        DaoUtil util=new DaoUtil();
        CategoryOptionComboEditForm cceform=(CategoryOptionComboEditForm)form;
        HttpSession session=request.getSession();
        DhisInstanceDao dinstdao=new DhisInstanceDaoImpl();
        
        String requiredAction=cceform.getActionName();
        String dhisInstance=cceform.getDhisInstance();
        List instanceList=dinstdao.getAllDhisInstances();
        cceform.setDhisInstanceList(instanceList);
        
        if(requiredAction ==null)
        {
            cceform.reset(mapping, request);
                      
        }
        else if(requiredAction.equalsIgnoreCase("loadrecord"))
        {
            List ccList=util.getDxCategoryCombinationDaoInstance().getCategoryCombinationByDhisInstance(dhisInstance);
            request.setAttribute("catComboList", ccList);
            cceform.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("deleteOrdisable"))
        {
            String[] selectedForDelete=cceform.getDelete();
            String[] selectedForDisable=cceform.getDisable();
            DxCategoryCombination cc=null;
            if(selectedForDelete !=null)
            {
                for(int i=0; i<selectedForDelete.length; i++)
                {
                    cc=util.getDxCategoryCombinationDaoInstance().getCategoryCombination(Integer.parseInt(selectedForDelete[i]));
                    if(cc !=null)
                    util.getDxCategoryCombinationDaoInstance().deleteCategoryCombination(cc);
                }
            }
            if(selectedForDisable !=null)
            {
                for(int i=0; i<selectedForDisable.length; i++)
                {
                    
                }
            }
            List ccList=util.getDxCategoryCombinationDaoInstance().getCategoryCombinationByDhisInstance(dhisInstance);
            request.setAttribute("catComboList", ccList);
            cceform.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
       
        return mapping.findForward(SUCCESS);
    }
}
