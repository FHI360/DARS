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
public class IntermediateFlatTable implements Serializable
{
    private int recordId;
    private String consumerInstance;
    private String consumerOrganizationUnitId;
    private String consumerOrganizationUnitName;
    private String consumerDataElementId;
    private String consumerDataElementName;
    private String consumerCategoryId;
    private String consumerCategoryName;
    private String producerInstance;
    private String producerOrganizationUnit;
    private String producerDataElement;
    private String producerCategory;
    private String lastModifiedDate;
    private String orgUnitType;
    private String orgUnitGroup;
    private String logic;
    private String dataset;
    private String startDate;
    private int value;
    private String organizationUnitOwnership;
    private String fundingBody;

    public String getConsumerCategoryId() {
        return consumerCategoryId;
    }

    public void setConsumerCategoryId(String consumerCategoryId) {
        this.consumerCategoryId = consumerCategoryId;
    }

    public String getConsumerCategoryName() {
        return consumerCategoryName;
    }

    public void setConsumerCategoryName(String consumerCategoryName) {
        this.consumerCategoryName = consumerCategoryName;
    }

    public String getConsumerDataElementId() {
        return consumerDataElementId;
    }

    public void setConsumerDataElementId(String consumerDataElementId) {
        this.consumerDataElementId = consumerDataElementId;
    }

    public String getConsumerDataElementName() {
        return consumerDataElementName;
    }

    public void setConsumerDataElementName(String consumerDataElementName) {
        this.consumerDataElementName = consumerDataElementName;
    }

    public String getConsumerOrganizationUnitId() {
        return consumerOrganizationUnitId;
    }

    public void setConsumerOrganizationUnitId(String consumerOrganizationUnitId) {
        this.consumerOrganizationUnitId = consumerOrganizationUnitId;
    }

    public String getConsumerOrganizationUnitName() {
        return consumerOrganizationUnitName;
    }

    public void setConsumerOrganizationUnitName(String consumerOrganizationUnitName) {
        this.consumerOrganizationUnitName = consumerOrganizationUnitName;
    }

    public String getConsumerInstance() {
        return consumerInstance;
    }

    public void setConsumerInstance(String consumerInstance) {
        this.consumerInstance = consumerInstance;
    }

    public String getProducerCategory() {
        return producerCategory;
    }

    public void setProducerCategory(String producerCategory) {
        this.producerCategory = producerCategory;
    }

    public String getProducerDataElement() {
        return producerDataElement;
    }

    public void setProducerDataElement(String producerDataElement) {
        this.producerDataElement = producerDataElement;
    }

    public String getProducerInstance() {
        return producerInstance;
    }

    public void setProducerInstance(String producerInstance) {
        this.producerInstance = producerInstance;
    }

    public String getProducerOrganizationUnit() {
        return producerOrganizationUnit;
    }

    public void setProducerOrganizationUnit(String producerOrganizationUnit) {
        this.producerOrganizationUnit = producerOrganizationUnit;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getFundingBody() {
        return fundingBody;
    }

    public void setFundingBody(String fundingBody) {
        this.fundingBody = fundingBody;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String getOrgUnitGroup() {
        return orgUnitGroup;
    }

    public void setOrgUnitGroup(String orgUnitGroup) {
        this.orgUnitGroup = orgUnitGroup;
    }

    public String getOrgUnitType() {
        return orgUnitType;
    }

    public void setOrgUnitType(String orgUnitType) {
        this.orgUnitType = orgUnitType;
    }

    public String getOrganizationUnitOwnership() {
        return organizationUnitOwnership;
    }

    public void setOrganizationUnitOwnership(String organizationUnitOwnership) {
        this.organizationUnitOwnership = organizationUnitOwnership;
    }
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
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
}
