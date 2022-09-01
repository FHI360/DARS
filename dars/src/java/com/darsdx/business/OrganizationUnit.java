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
public class OrganizationUnit implements Serializable 
{
    private String orgunitId;
    private String parentId;
    private String dhisId;
    private String orgunitName;
    private String shortName;
    private String ouCode;
    private Date lastModifiedDate;
    private int oulevel;
    private String path;

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

    public int getOulevel() {
        return oulevel;
    }

    public void setOulevel(int oulevel) {
        this.oulevel = oulevel;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOuCode() {
        return ouCode;
    }

    public void setOuCode(String ouCode) {
        this.ouCode = ouCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
}
