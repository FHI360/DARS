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
public class DxOrganizationUnit implements Serializable
{
    private int recordId;
    private String orgunitId;
    private String parentId;
    private String orgunitName;
    private int level;
    private String lastModifiedDate;
    private String instanceName;
    private DhisInstance dhisInstance;
    private String ouCode;
    BusinessUtility butil=new BusinessUtility();
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getOrgunitId() {
        return orgunitId;
    }

    public void setOrgunitId(String orgunitId) {
        this.orgunitId = orgunitId;
    }

    public String getOrgunitName() {
        return orgunitName;
    }

    public void setOrgunitName(String orgunitName) {
        this.orgunitName = orgunitName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public DhisInstance getDhisInstance() 
    {
        dhisInstance=butil.getDhisInstance(instanceName);
        return dhisInstance;
    }

    public String getOuCode() {
        return ouCode;
    }

    public void setOuCode(String ouCode) {
        this.ouCode = ouCode;
    }
    
        
    @Override
    public String toString() {
        return getOrgunitName();
    }
}
