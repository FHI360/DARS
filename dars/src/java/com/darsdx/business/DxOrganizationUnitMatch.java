/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

import com.darsdx.dao.DaoUtil;
import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class DxOrganizationUnitMatch implements Serializable
{
    private int recordId;
    private String producerOrgUnitId;
    private String consumerOrgUnitId;
    private String producerOrgUnitName;
    private String consumerOrgUnitName;
    private String producerInstanceId;
    private String consumerInstanceId;
    private String matchDescription;
    private String lastModifiedDate;
    private int producerouvalidated;
    private int consumerouvalidated;
    private int totalValidationScore;
    private DxOrganizationUnit producerOrgUnit; 
    private DxOrganizationUnit consumerOrgUnit; 
    BusinessUtility butil=new BusinessUtility();
    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    public String getMatchDescription() {
        return matchDescription;
    }

    public void setMatchDescription(String matchDescription) {
        this.matchDescription = matchDescription;
    }

    public String getConsumerOrgUnitId() {
        return consumerOrgUnitId;
    }

    public void setConsumerOrgUnitId(String consumerOrgUnitId) {
        this.consumerOrgUnitId = consumerOrgUnitId;
    }

    public String getProducerOrgUnitId() {
        return producerOrgUnitId;
    }

    public void setProducerOrgUnitId(String producerOrgUnitId) {
        this.producerOrgUnitId = producerOrgUnitId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public DxOrganizationUnit getConsumerOrgUnit() {
        consumerOrgUnit=butil.getDxOrganizationUnit(consumerOrgUnitId,consumerInstanceId);
        return consumerOrgUnit;
    }

    public void setConsumerOrgUnit(DxOrganizationUnit consumerOrgUnit) {
        this.consumerOrgUnit = consumerOrgUnit;
    }

    public DxOrganizationUnit getProducerOrgUnit() 
    {
        producerOrgUnit=butil.getDxOrganizationUnit(producerOrgUnitId,producerInstanceId);
        return producerOrgUnit;
    }

    public void setProducerOrgUnit(DxOrganizationUnit producerOrgUnit) {
        this.producerOrgUnit = producerOrgUnit;
    }

    public String getConsumerInstanceId() {
        return consumerInstanceId;
    }

    public void setConsumerInstanceId(String consumerInstanceId) {
        this.consumerInstanceId = consumerInstanceId;
    }

    public String getProducerInstanceId() {
        return producerInstanceId;
    }

    public void setProducerInstanceId(String producerInstanceId) {
        this.producerInstanceId = producerInstanceId;
    }

    public String getConsumerOrgUnitName() {
        return consumerOrgUnitName;
    }

    public void setConsumerOrgUnitName(String consumerOrgUnitName) {
        this.consumerOrgUnitName = consumerOrgUnitName;
    }

    public String getProducerOrgUnitName() {
        return producerOrgUnitName;
    }

    public void setProducerOrgUnitName(String producerOrgUnitName) {
        this.producerOrgUnitName = producerOrgUnitName;
    }

    public int getConsumerouvalidated() {
        return consumerouvalidated;
    }

    public void setConsumerouvalidated(int consumerouvalidated) {
        this.consumerouvalidated = consumerouvalidated;
    }

    public int getProducerouvalidated() {
        return producerouvalidated;
    }

    public void setProducerouvalidated(int producerouvalidated) {
        this.producerouvalidated = producerouvalidated;
    }

    public int getTotalValidationScore() {
        return totalValidationScore;
    }

    public void setTotalValidationScore(int totalValidationScore) {
        this.totalValidationScore = totalValidationScore;
    }
    
}
