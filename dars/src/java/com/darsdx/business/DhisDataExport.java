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
public class DhisDataExport implements Serializable
{
    private String attributeOptionCombinationId;
    private String attributeOptionCombinationName;
    private String categoryCombinationId;
    private String dataElementId;
    private String indicatorId;
    private String organizationUnitId;
    private int value;
    private String month;
    private String year;
    
    public String getCategoryCombinationId() {
        return categoryCombinationId;
    }

    public void setCategoryCombinationId(String categoryCombinationId) {
        this.categoryCombinationId = categoryCombinationId;
    }

    public String getDataElementId() {
        return dataElementId;
    }

    public void setDataElementId(String dataElementId) {
        this.dataElementId = dataElementId;
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOrganizationUnitId() {
        return organizationUnitId;
    }

    public void setOrganizationUnitId(String organizationUnitId) {
        this.organizationUnitId = organizationUnitId;
    }

    public String getAttributeOptionCombinationId() {
        return attributeOptionCombinationId;
    }

    public void setAttributeOptionCombinationId(String attributeOptionCombinationId) {
        this.attributeOptionCombinationId = attributeOptionCombinationId;
    }

    public String getAttributeOptionCombinationName() {
        return attributeOptionCombinationName;
    }

    public void setAttributeOptionCombinationName(String attributeOptionCombinationName) {
        this.attributeOptionCombinationName = attributeOptionCombinationName;
    }

}
