/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class DxCategoryCombination implements Serializable
{
    private String catComboId;
    private String catComboName;
    private String lastModifiedDate;
    private String dhisInstance;
    private int recordId;
    private DhisInstance instanceObj;
    BusinessUtility butil=new BusinessUtility();

    public String getCatComboId() {
        return catComboId;
    }

    public void setCatComboId(String catComboId) {
        this.catComboId = catComboId;
    }

    public String getCatComboName() {
        return catComboName;
    }

    public void setCatComboName(String catComboName) {
        this.catComboName = catComboName;
    }

    public String getDhisInstance() {
        return dhisInstance;
    }

    public void setDhisInstance(String dhisInstance) {
        this.dhisInstance = dhisInstance;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public DhisInstance getInstanceObj() 
    {
        instanceObj=butil.getDhisInstance(dhisInstance);
        return instanceObj;
    }
    
}
