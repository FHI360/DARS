/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;

/**
 *
 * @author smomoh
 */
public class DataValue implements Serializable
{
    private int recordId;
    private String dhisId;
    private String datasetId;
    private String dataElementId;
    private String dataElementName;
    private String orgUnitId;
    private String orgUnitName;
    private String categoryOptionComboId;
    private String categoryOptionComboName;
    private Date startDate;
    private Date lastModifiedDate;
    private String lastUpdated;
    private String period;
    private String dvalue;
    private String attributeOptionCombo;
    private int uploadedToDhis2;

    public String getCategoryOptionComboId() {
        return categoryOptionComboId;
    }

    public void setCategoryOptionComboId(String categoryOptionComboId) {
        this.categoryOptionComboId = categoryOptionComboId;
    }

    public String getDataElementId() {
        return dataElementId;
    }

    public void setDataElementId(String dataElementId) {
        this.dataElementId = dataElementId;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getDvalue() {
        return dvalue;
    }

    public void setDvalue(String dvalue) {
        this.dvalue = dvalue;
    }

    public String getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAttributeOptionCombo() {
        return attributeOptionCombo;
    }

    public void setAttributeOptionCombo(String attributeOptionCombo) {
        this.attributeOptionCombo = attributeOptionCombo;
    }

    public String getDataElementName() {
        return dataElementName;
    }

    public void setDataElementName(String dataElementName) {
        this.dataElementName = dataElementName;
    }

    public String getOrgUnitName() {
        return orgUnitName;
    }

    public void setOrgUnitName(String orgUnitName) {
        this.orgUnitName = orgUnitName;
    }

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public String getCategoryOptionComboName() {
        return categoryOptionComboName;
    }

    public void setCategoryOptionComboName(String categoryOptionComboName) {
        this.categoryOptionComboName = categoryOptionComboName;
    }

    public int getUploadedToDhis2() {
        return uploadedToDhis2;
    }

    public void setUploadedToDhis2(int uploadedToDhis2) {
        this.uploadedToDhis2 = uploadedToDhis2;
    }
    
}
