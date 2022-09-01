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
public class CategoryOptionCombo implements Serializable
{
    private String categoryOptionComboId;
    private String dhisId;
    private String categoryOptionComboName;
    private Date lastModifiedDate;

    public String getCategoryOptionComboId() {
        return categoryOptionComboId;
    }

    public void setCategoryOptionComboId(String categoryOptionComboId) {
        this.categoryOptionComboId = categoryOptionComboId;
    }

    public String getCategoryOptionComboName() {
        return categoryOptionComboName;
    }

    public void setCategoryOptionComboName(String categoryOptionComboName) {
        this.categoryOptionComboName = categoryOptionComboName;
    }

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
}
