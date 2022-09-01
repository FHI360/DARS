/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.darsdx.business.BusinessRule;
import com.darsdx.business.DhisInstance;
import com.darsdx.dao.BusinessRuleDao;
import com.darsdx.dao.BusinessRuleDaoImpl;
import com.darsdx.dao.DhisInstanceDao;
import com.darsdx.dao.DhisInstanceDaoImpl;
import com.darsdx.dao.DxCategoryCombinationDao;
import com.darsdx.dao.DxCategoryCombinationDaoImpl;
import com.darsdx.dao.DxDataElementDao;
import com.darsdx.dao.DxDataElementDaoImpl;
import com.darsdx.util.AppUtility;
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
public class DataElementAndCategoryMatchAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String BUSRULEPAGE = "busRulePage";
    private static final String PARAMPAGE="paramPage";

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
        DataElementAndCategoryMatchForm decatform=(DataElementAndCategoryMatchForm)form;
        DxCategoryCombinationDao catdao=new DxCategoryCombinationDaoImpl();
        DxDataElementDao dedao=new DxDataElementDaoImpl();
        
        
        String requiredAction=decatform.getActionName();
        String consumerInstance=decatform.getConsumerInstance();
        String producerInstance=decatform.getProducerInstance();
        String consumerDe=decatform.getConsumerDe();
        String consumerCatCombo=decatform.getConsumerCat();
        String producerDe=decatform.getProducerDe();
        String recordGrpId=decatform.getRecordGrpId();
        String[] producerCatCombos=decatform.getProducerCat();
        
        HttpSession session=request.getSession();
        DhisInstanceDao dinstdao=new DhisInstanceDaoImpl();
        List instanceList=dinstdao.getAllDhisInstances();
        List filteredList=new ArrayList();
        
        
        if(producerInstance ==null || producerInstance ==null)
        {
            if(instanceList !=null && !instanceList.isEmpty())
            {
                DhisInstance dhisInstance=(DhisInstance)instanceList.get(0);
                filteredList=dinstdao.filterInstance(instanceList, dhisInstance.getDhisId());
            }
        }
        
        List producerDeList=dedao.getDataElementByDhisInstance(producerInstance);
        List consumerDeList=dedao.getDataElementByDhisInstance(consumerInstance);
        List producerCategoryList=catdao.getCategoryCombinationByDhisInstance(producerInstance);
        List consumerCategoryList=catdao.getCategoryCombinationByDhisInstance(consumerInstance);
        decatform.setConsumerCatList(consumerCategoryList);
        decatform.setConsumerDeList(consumerDeList);
        decatform.setProducerDeList(producerDeList);
        
        DhisInstance pdhisInstance=(DhisInstance)dinstdao.getDhisInstanceById(producerInstance);
        DhisInstance cdhisInstance=(DhisInstance)dinstdao.getDhisInstanceById(consumerInstance);
        session.setAttribute("busrpinstance",pdhisInstance);
        session.setAttribute("busrcinstance",cdhisInstance);
        session.setAttribute("producerCategoryList", producerCategoryList);
        System.err.println("requiredAction is "+requiredAction+" producerInstance "+producerInstance+" consumerInstance "+consumerInstance);
        if(requiredAction==null)
        {
            session.setAttribute("busrproducerInstanceList",instanceList);
            session.setAttribute("busrconsumerInstanceList",filteredList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("producerChanged"))
        {
            filteredList=dinstdao.filterInstance(instanceList, producerInstance);
            session.setAttribute("busrproducerInstanceList",instanceList);
            session.setAttribute("busrconsumerInstanceList",filteredList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("consumerChanged"))
        {
            filteredList=dinstdao.filterInstance(instanceList, consumerInstance);
            session.setAttribute("busrproducerInstanceList",filteredList);
            session.setAttribute("busrconsumerInstanceList",instanceList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("next"))
        {
            return mapping.findForward(BUSRULEPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            AppUtility appUtil=new AppUtility();
            if(producerCatCombos !=null && producerCatCombos.length >0)
            {
                BusinessRuleDao brdao=new BusinessRuleDaoImpl();
                recordGrpId=brdao.getUniqueId();
                String lastModifiedDate=appUtil.getCurrentDate();
                
                BusinessRule bizrule=null;
                for(int i=0; i<producerCatCombos.length; i++)
                {
                    bizrule=new BusinessRule();
                    bizrule.setBusinessLogicId(null);
                    bizrule.setConsumerCatComboId(consumerCatCombo);
                    bizrule.setConsumerDeId(consumerDe);
                    bizrule.setConsumerInstanceId(consumerInstance);
                    bizrule.setProducerDeId(producerDe);
                    bizrule.setProducerCatComboId(producerCatCombos[i]);
                    bizrule.setProducerInstanceId(producerInstance);
                    bizrule.setRecordGrpId(appUtil.generateUniqueId(11));
                    bizrule.setRecordGrpId(recordGrpId);
                    bizrule.setLastModifiedDate(lastModifiedDate);
                    brdao.saveBusinessRule(bizrule);
                }
            }
            decatform.reset(mapping, request);
            return mapping.findForward(BUSRULEPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            if(producerCatCombos !=null && producerCatCombos.length >0)
            {
                AppUtility appUtil=new AppUtility();
                String lastModifiedDate=appUtil.getCurrentDate();
                BusinessRuleDao brdao=new BusinessRuleDaoImpl();
                BusinessRule bizrule=null;
                for(int i=0; i<producerCatCombos.length; i++)
                {
                    bizrule=new BusinessRule();
                    bizrule.setBusinessLogicId(null);
                    bizrule.setConsumerCatComboId(consumerCatCombo);
                    bizrule.setConsumerCatComboId(consumerDe);
                    bizrule.setConsumerInstanceId(consumerInstance);
                    bizrule.setProducerDeId(producerDe);
                    bizrule.setProducerCatComboId(producerCatCombos[i]);
                    bizrule.setProducerInstanceId(producerInstance);
                    bizrule.setRecordGrpId(recordGrpId);
                    bizrule.setLastModifiedDate(lastModifiedDate);
                    brdao.updateBusinessRule(bizrule);
                }
            }
            return mapping.findForward(BUSRULEPAGE);
        }
        return mapping.findForward(PARAMPAGE);
    }
}
