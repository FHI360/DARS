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
public class BusinessRule implements Serializable
{
    private int recordId;
    private String recordGrpId;
    private String consumerInstanceId;
    private String consumerDeId;
    private String consumerCatComboId;
    private String dataset;
    private String datasetId;
    private String producerInstanceId;
    private String producerDeId;
    private String producerCatComboId;
    private String businessLogicId;
    private String lastModifiedDate;
    private String businessLogicName;
    private String consumerInstanceName;
    private String consumerDeName;
    private String consumerCatComboName;
    private String datasetName;
    private String producerInstanceName;
    private String producerDeName;
    private String producerCatComboName;
    private String attributeOptionComboId;
    private String attributeOptionComboName;
    private String disable;
    private int cdevalidated;
    private int cccvalidated;
    private int pdevalidated;
    private int pccvalidated;
    private int validationScore;

    public String getBusinessLogicId() {
        return businessLogicId;
    }

    public void setBusinessLogicId(String businessLogicId) {
        this.businessLogicId = businessLogicId;
    }

    public String getBusinessLogicName() {
        return businessLogicName;
    }

    public void setBusinessLogicName(String businessLogicName) {
        this.businessLogicName = businessLogicName;
    }

    public String getConsumerCatComboId() {
        return consumerCatComboId;
    }

    public void setConsumerCatComboId(String consumerCatComboId) {
        this.consumerCatComboId = consumerCatComboId;
    }

    public String getConsumerCatComboName() {
        return consumerCatComboName;
    }

    public void setConsumerCatComboName(String consumerCatComboName) {
        this.consumerCatComboName = consumerCatComboName;
    }

    public String getConsumerDeId() {
        return consumerDeId;
    }

    public void setConsumerDeId(String consumerDeId) {
        this.consumerDeId = consumerDeId;
    }

    public String getConsumerDeName() {
        return consumerDeName;
    }

    public void setConsumerDeName(String consumerDeName) {
        this.consumerDeName = consumerDeName;
    }

    public String getConsumerInstanceId() {
        return consumerInstanceId;
    }

    public void setConsumerInstanceId(String consumerInstanceId) {
        this.consumerInstanceId = consumerInstanceId;
    }

    public String getConsumerInstanceName() {
        return consumerInstanceName;
    }

    public void setConsumerInstanceName(String consumerInstanceName) {
        this.consumerInstanceName = consumerInstanceName;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getProducerCatComboId() {
        return producerCatComboId;
    }

    public void setProducerCatComboId(String producerCatComboId) {
        this.producerCatComboId = producerCatComboId;
    }

    public String getProducerCatComboName() {
        return producerCatComboName;
    }

    public void setProducerCatComboName(String producerCatComboName) {
        this.producerCatComboName = producerCatComboName;
    }

    public String getProducerDeId() {
        return producerDeId;
    }

    public void setProducerDeId(String producerDeId) {
        this.producerDeId = producerDeId;
    }

    public String getProducerDeName() {
        return producerDeName;
    }

    public void setProducerDeName(String producerDeName) {
        this.producerDeName = producerDeName;
    }

    public String getProducerInstanceId() {
        return producerInstanceId;
    }

    public void setProducerInstanceId(String producerInstanceId) {
        this.producerInstanceId = producerInstanceId;
    }

    public String getProducerInstanceName() {
        return producerInstanceName;
    }

    public void setProducerInstanceName(String producerInstanceName) {
        this.producerInstanceName = producerInstanceName;
    }

    public String getRecordGrpId() {
        return recordGrpId;
    }

    public void setRecordGrpId(String recordGrpId) {
        this.recordGrpId = recordGrpId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }

    public int getCccvalidated() {
        return cccvalidated;
    }

    public void setCccvalidated(int cccvalidated) {
        this.cccvalidated = cccvalidated;
    }

    public int getCdevalidated() {
        return cdevalidated;
    }

    public void setCdevalidated(int cdevalidated) {
        this.cdevalidated = cdevalidated;
    }

    public int getPccvalidated() {
        return pccvalidated;
    }

    public void setPccvalidated(int pccvalidated) {
        this.pccvalidated = pccvalidated;
    }

    public int getPdevalidated() {
        return pdevalidated;
    }

    public void setPdevalidated(int pdevalidated) {
        this.pdevalidated = pdevalidated;
    }

    public int getValidationScore() {
        return validationScore;
    }

    public void setValidationScore(int validationScore) {
        this.validationScore = validationScore;
    }

    public String getAttributeOptionComboId() {
        return attributeOptionComboId;
    }

    public void setAttributeOptionComboId(String attributeOptionComboId) {
        this.attributeOptionComboId = attributeOptionComboId;
    }

    public String getAttributeOptionComboName() {
        return attributeOptionComboName;
    }

    public void setAttributeOptionComboName(String attributeOptionComboName) {
        this.attributeOptionComboName = attributeOptionComboName;
    }
    
}
