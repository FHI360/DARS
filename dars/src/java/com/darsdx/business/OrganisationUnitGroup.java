/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author smomoh
 */
public class OrganisationUnitGroup implements Serializable
{
    private String orgunitGroupId;
    private String dhisId;
    private String orgunitGroupName;
    private Date lastModifiedDate;

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public String getOrgunitGroupId() {
        return orgunitGroupId;
    }

    public void setOrgunitGroupId(String orgunitGroupId) {
        this.orgunitGroupId = orgunitGroupId;
    }

    public String getOrgunitGroupName() {
        return orgunitGroupName;
    }

    public void setOrgunitGroupName(String orgunitGroupName) {
        this.orgunitGroupName = orgunitGroupName;
    }
    
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
}
