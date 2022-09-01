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
public class DhisInstance implements Serializable
{
    
    private String dhisHomeUrl;
    private String dhisName;
    private String userName;
    private String password;
    private String apiUrl;
    private String dhisId;
    private Date dateCreated;
    private Date lastModifiedDate;
    private String recordedBy;
    private String connectionType;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDhisHomeUrl() {
        return dhisHomeUrl;
    }

    public void setDhisHomeUrl(String dhisHomeUrl) {
        this.dhisHomeUrl = dhisHomeUrl;
    }

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public String getDhisName() {
        return dhisName;
    }

    public void setDhisName(String dhisName) {
        this.dhisName = dhisName;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

}
