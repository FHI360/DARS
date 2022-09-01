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
public class DatavalueResource implements Serializable
{
    private String dhisId;
    private String url;
    private Date dateCreated;
    private Date lastModifiedDate;
    private int startMonth;
    private int startYear;
    private int endMonth;
    private int endYear;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
}
