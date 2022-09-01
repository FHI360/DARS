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
public class SourceData implements Serializable
{
    private int recordId;
    private String dhisInstance;
    private String organizationUnit;
    private String dataElementName;
    private String categoryOptionComboName;
    private String organizationUnitType;
    private String dataset;
    private String startDate;
    private int value;
    private String organizationUnitGroup;
    private String organizationUnitOwnership;
    private String fundingBody;
    private String lastModifiedDate;

    public String getCategoryOptionComboName() {
        return categoryOptionComboName;
    }

    public void setCategoryOptionComboName(String categoryOptionComboName) {
        this.categoryOptionComboName = categoryOptionComboName;
    }

    public String getDataElementName() {
        return dataElementName;
    }

    public void setDataElementName(String dataElementName) {
        this.dataElementName = dataElementName;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getDhisInstance() {
        return dhisInstance;
    }

    public void setDhisInstance(String dhisInstance) {
        this.dhisInstance = dhisInstance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFundingBody() {
        return fundingBody;
    }

    public void setFundingBody(String fundingBody) {
        this.fundingBody = fundingBody;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public String getOrganizationUnitGroup() {
        return organizationUnitGroup;
    }

    public void setOrganizationUnitGroup(String organizationUnitGroup) {
        this.organizationUnitGroup = organizationUnitGroup;
    }

    public String getOrganizationUnitOwnership() {
        return organizationUnitOwnership;
    }

    public void setOrganizationUnitOwnership(String organizationUnitOwnership) {
        this.organizationUnitOwnership = organizationUnitOwnership;
    }

    public String getOrganizationUnitType() {
        return organizationUnitType;
    }

    public void setOrganizationUnitType(String organizationUnitType) {
        this.organizationUnitType = organizationUnitType;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
    
}
