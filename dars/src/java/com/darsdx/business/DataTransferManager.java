/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

import com.darsdx.pojo.DataTransferResourceManager;
import java.io.Serializable;
import java.lang.String;
import java.util.Date;

/**
 *
 * @author smomoh
 */
public class DataTransferManager implements Serializable
{
    private String uid;
    private String dataSource;
    private String dataSink;
    private String dataElementMapping;
    private String orgUnitMapping;
    private Date dateCreated;
    private Date lastModifiedDate;
    private String dataSourceName;
    private String dataSinkName;
    private String name;
    private  String frequency;
    private  Date startDate;
    private  Date endDate;
    
    DataTransferResourceManager dtrm=new DataTransferResourceManager();

    public String getDataElementMapping() {
        return dataElementMapping;
    }

    public void setDataElementMapping(String dataElementMapping) {
        this.dataElementMapping = dataElementMapping;
    }

    public String getDataSink() {
        return dataSink;
    }

    public void setDataSink(String dataSink) {
        this.dataSink = dataSink;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getOrgUnitMapping() {
        return orgUnitMapping;
    }

    public void setOrgUnitMapping(String orgUnitMapping) {
        this.orgUnitMapping = orgUnitMapping;
    }

    public String getDataSinkName() 
    {
        dataSinkName=dtrm.getSourceOrSinkName(dataSink);
        return dataSinkName;
    }

    public void setDataSinkName(String dataSinkName) {
        this.dataSinkName = dataSinkName;
    }

    public String getDataSourceName() 
    {
        dataSourceName=dtrm.getSourceOrSinkName(dataSource);
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
}
