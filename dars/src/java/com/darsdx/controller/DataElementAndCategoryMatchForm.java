/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class DataElementAndCategoryMatchForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String consumerInstance;
    private String producerInstance;
    private String producerDe;
    private String consumerDe;
    private String[] producerCat;
    private String consumerCat;
    private String recordGrpId;
    
    private List producerDeList=new ArrayList();
    private List consumerDeList=new ArrayList();
    private List consumerCatList=new ArrayList();
    //private String consumerInstance;
    public DataElementAndCategoryMatchForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getConsumerInstance() {
        return consumerInstance;
    }

    public void setConsumerInstance(String consumerInstance) {
        this.consumerInstance = consumerInstance;
    }

    public String getProducerInstance() {
        return producerInstance;
    }

    public void setProducerInstance(String producerInstance) {
        this.producerInstance = producerInstance;
    }

    public String getConsumerCat() {
        return consumerCat;
    }

    public void setConsumerCat(String consumerCat) {
        this.consumerCat = consumerCat;
    }

    public String getConsumerDe() {
        return consumerDe;
    }

    public void setConsumerDe(String consumerDe) {
        this.consumerDe = consumerDe;
    }

    public String[] getProducerCat() {
        return producerCat;
    }

    public void setProducerCat(String[] producerCat) {
        this.producerCat = producerCat;
    }

    public String getProducerDe() {
        return producerDe;
    }

    public void setProducerDe(String producerDe) {
        this.producerDe = producerDe;
    }

    public List getConsumerCatList() {
        return consumerCatList;
    }

    public void setConsumerCatList(List consumerCatList) {
        this.consumerCatList = consumerCatList;
    }

    public List getConsumerDeList() {
        return consumerDeList;
    }

    public void setConsumerDeList(List consumerDeList) {
        this.consumerDeList = consumerDeList;
    }

    public List getProducerDeList() {
        return producerDeList;
    }

    public void setProducerDeList(List producerDeList) {
        this.producerDeList = producerDeList;
    }

    public String getRecordGrpId() {
        return recordGrpId;
    }

    public void setRecordGrpId(String recordGrpId) {
        this.recordGrpId = recordGrpId;
    }
    
@Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    actionName=null;
    producerCat=null;
    /*consumerInstance=null;
    producerInstance=null;
    producerDe=null;
    consumerDe=null;
    recordGrpId=null;*/
}
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        return errors;
    }
}
